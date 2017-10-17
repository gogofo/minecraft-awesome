package gogofo.minecraft.awesome.gui.features;

import gogofo.minecraft.awesome.gui.AwesomeGui;
import gogofo.minecraft.awesome.gui.controls.ColorGuiButton;
import gogofo.minecraft.awesome.interfaces.IPositionedSidedInventory;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.EnumFacing;

public class FeatureUpgrades implements GuiFeature {

    private static final int CONTROLS_WIDTH = 100;
    private static final int CONTROLS_HEIGHT = 100;

    protected static final int GEAR_WIDTH = 24;
    protected static final int GEAR_HEIGHT = 14;

    private ColorGuiButton gearButton;
    private ColorGuiButton background;
    private ColorGuiButton[] faceButtons;

    protected boolean controlActive = false;
    private AwesomeGui gui;
    private IPositionedSidedInventory inventory;

    public FeatureUpgrades(AwesomeGui gui, IPositionedSidedInventory customInventory) {
        this.gui = gui;
        this.inventory = customInventory;

        faceButtons = new ColorGuiButton[EnumFacing.values().length];
    }

    public void onInitGui() {
        gearButton = new ColorGuiButton(gui.getNextButtonId(),
                                        gui.guiX() + gearXPos(),
                                        gui.guiY() + gearYPos(),
                                        GEAR_WIDTH, GEAR_HEIGHT,
                                        "BST");
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
    }

    public void onDrawBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        if (controlActive) {
            background.visible = true;
        } else {
            background.visible = false;
        }
    }

    private int gearXPos() {
        return gui.getXSize() - GEAR_WIDTH - 8;
    }

    private int gearYPos() {
        return 30;
    }

    public void onActionPerformed(GuiButton button) {
        int buttonId = button.id;

        if (buttonId == gearButton.id) {
            handleGearPressed();
        }
    }

    private void handleGearPressed() {
        controlActive = !controlActive;
    }
}
