package gogofo.minecraft.awesome.inventory;

import gogofo.minecraft.awesome.tileentity.AwesomeTileEntityMachine;
import net.minecraft.entity.player.InventoryPlayer;

public abstract class AwesomeMachineContainer extends AwesomeContainer {
    public AwesomeMachineContainer(InventoryPlayer inventoryPlayer, AwesomeTileEntityMachine customInventory) {
        super(inventoryPlayer, customInventory);

        for (int i = 0; i < getTileEntity().getUpgradeCount(); i++) {
            addSlotToContainer(new MachineUpgradeSlot(customInventory, getCustomSlotCount() + i, 0, 0));
        }
    }

    protected AwesomeTileEntityMachine getTileEntity() {
        return (AwesomeTileEntityMachine) inventory;
    }

    @Override
    protected int getCustomSlotCount() {
        return getTileEntity().getCustomSlotCount();
    }
}
