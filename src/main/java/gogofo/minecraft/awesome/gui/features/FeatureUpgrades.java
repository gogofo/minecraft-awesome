package gogofo.minecraft.awesome.gui.features;

import gogofo.minecraft.awesome.gui.AwesomeGui;
import gogofo.minecraft.awesome.gui.controls.ColorGuiButton;
import gogofo.minecraft.awesome.inventory.MachineUpgradeSlot;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

public class FeatureUpgrades implements GuiFeature {

    public static final int UPGRADES_WIDTH = 100;
    public static final int UPGRADES_HEIGHT = 100;

    protected static final int GEAR_WIDTH = 24;
    protected static final int GEAR_HEIGHT = 14;

    private static final int UPGRADE_SLOTS_PER_ROW = (UPGRADES_WIDTH - 16) / 20;

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
            gui.drawWindow(gui.guiX() + gui.getXSize(), gui.guiY(), UPGRADES_WIDTH, UPGRADES_HEIGHT);

            int upgradeSlotCount = 0;
            for (Slot inventorySlot : container.inventorySlots) {
                if (inventorySlot instanceof MachineUpgradeSlot) {
                    ((MachineUpgradeSlot) inventorySlot).setVisible(true);
                    inventorySlot.xPos = gui.getXSize() + 8 + 20 * (upgradeSlotCount % UPGRADE_SLOTS_PER_ROW);
                    inventorySlot.yPos = 8 + 20 * (upgradeSlotCount / UPGRADE_SLOTS_PER_ROW);
                    upgradeSlotCount += 1;
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
