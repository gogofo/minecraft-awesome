package gogofo.minecraft.awesome.gui.features;

import gogofo.minecraft.awesome.gui.AwesomeGui;
import gogofo.minecraft.awesome.gui.controls.ColorGuiButton;
import gogofo.minecraft.awesome.inventory.MachineUpgradeSlot;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

public class FeatureUpgrades implements GuiFeature {

    private static final int CONTROLS_WIDTH = 100;
    private static final int CONTROLS_HEIGHT = 100;

    protected static final int GEAR_WIDTH = 24;
    protected static final int GEAR_HEIGHT = 14;

    private ColorGuiButton gearButton;

    protected boolean controlActive = false;
    private AwesomeGui gui;
    private Container container;

    public FeatureUpgrades(AwesomeGui gui, Container container) {
        this.gui = gui;
        this.container = container;
    }

    public void onInitGui() {
        gearButton = new ColorGuiButton(gui.getNextButtonId(),
                                        gui.guiX() + gearXPos(),
                                        gui.guiY() + gearYPos(),
                                        GEAR_WIDTH, GEAR_HEIGHT,
                                        "BST");
        gui.addButton(gearButton);
    }

    public void onDrawBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        if (controlActive) {
            gui.drawWindow(gui.guiX() + gui.getXSize(), gui.guiY(), CONTROLS_WIDTH, CONTROLS_HEIGHT);

            for (Slot inventorySlot : container.inventorySlots) {
                if (inventorySlot instanceof MachineUpgradeSlot) {
                    ((MachineUpgradeSlot) inventorySlot).setVisible(true);
                }
            }
        } else {
            for (Slot inventorySlot : container.inventorySlots) {
                if (inventorySlot instanceof MachineUpgradeSlot) {
                    ((MachineUpgradeSlot) inventorySlot).setVisible(false);
                }
            }
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
