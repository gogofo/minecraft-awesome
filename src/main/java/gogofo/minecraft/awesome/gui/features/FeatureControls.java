package gogofo.minecraft.awesome.gui.features;

import gogofo.minecraft.awesome.gui.AwesomeGui;
import gogofo.minecraft.awesome.gui.controls.ColorGuiButton;
import gogofo.minecraft.awesome.interfaces.IPositionedSidedInventory;
import gogofo.minecraft.awesome.inventory.SlotCategoryIdToColor;
import gogofo.minecraft.awesome.network.AwesomeControlSidesUpdateMessage;
import gogofo.minecraft.awesome.network.AwesomeNetworkHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.EnumFacing;

public class FeatureControls implements GuiFeature {

    private static final int CONTROLS_WIDTH = 100;
    private static final int CONTROLS_HEIGHT = 100;

    protected static final int GEAR_WIDTH = 24;
    protected static final int GEAR_HIGHT = 14;

    private static final int GEAR_BUTTON_ID = 0;
    private static final int FIRST_FACE_BUTTON_ID = 1;

    private ColorGuiButton gearButton;
    private ColorGuiButton background;
    private ColorGuiButton[] faceButtons;

    protected boolean controlActive = false;
    private AwesomeGui gui;
    private IPositionedSidedInventory inventory;

    public FeatureControls(AwesomeGui gui, IPositionedSidedInventory customInventory) {
        this.gui = gui;
        this.inventory = customInventory;

        faceButtons = new ColorGuiButton[EnumFacing.values().length];
    }

    public void onInitGui() {
        gearButton = new ColorGuiButton(GEAR_BUTTON_ID,
                                        gui.guiX() + gearXPos(),
                                        gui.guiY() + gearYPos(),
                                        GEAR_WIDTH, GEAR_HIGHT,
                                        "Ctrl");
        gui.addButton(gearButton);

        background = new ColorGuiButton(-1,
                                        gui.guiX() + gui.getXSize(),
                                        gui.guiY(),
                                        CONTROLS_WIDTH,
                                        CONTROLS_HEIGHT,
                                        "");
        background.enabled = false;
        background.setColor(0xFFC6C6C6);
        gui.addButton(background);

        initFaceButtons();
    }

    private void initFaceButtons() {
        for (EnumFacing face : EnumFacing.values()) {
            ColorGuiButton button = new ColorGuiButton(FIRST_FACE_BUTTON_ID + face.getIndex(),
                                                       gui.guiX() + gui.getXSize() + 8 + (38 * (face.getIndex() % 2)),
                                                       gui.guiY() + 8 + (24 * (int)Math.floor(face.getIndex() / 2)),
                                                       34, 20,
                                                       face.toString());

            faceButtons[face.getIndex()] = button;
            gui.addButton(button);
        }
    }

    public void onDrawBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        if (controlActive) {
            background.visible = true;

            for (EnumFacing face : EnumFacing.values()) {
                int[] slots = inventory.getSlotsForFace(face);
                int mainSlot = slots.length > 0 ? slots[0] : -1;

                ColorGuiButton button = faceButtons[face.getIndex()];
                button.setColor(SlotCategoryIdToColor.convert(mainSlot));
                button.visible = true;
            }
        } else {
            background.visible = false;

            for (EnumFacing face : EnumFacing.values()) {
                faceButtons[face.getIndex()].visible = false;
            }
        }
    }

    private int gearXPos() {
        return gui.getXSize() - GEAR_WIDTH - 8;
    }

    private int gearYPos() {
        return 8;
    }

    public void onActionPerformed(GuiButton button) {
        int buttonId = button.id;

        if (buttonId == GEAR_BUTTON_ID) {
            handleGearPressed();
        } else if (buttonId >= FIRST_FACE_BUTTON_ID &&
                buttonId < FIRST_FACE_BUTTON_ID + EnumFacing.values().length) {
            AwesomeControlSidesUpdateMessage message =
                    new AwesomeControlSidesUpdateMessage(inventory.getInventoryPos(),
                                                         EnumFacing.values()[buttonId - FIRST_FACE_BUTTON_ID]);
            AwesomeNetworkHandler.wrapper.sendToServer(message);
        }
    }

    private void handleGearPressed() {
        controlActive = !controlActive;
    }
}
