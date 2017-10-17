package gogofo.minecraft.awesome.inventory;

import gogofo.minecraft.awesome.tileentity.AwesomeTileEntityMachine;
import net.minecraft.entity.player.InventoryPlayer;

public abstract class AwesomeMachineContainer extends AwesomeContainer {
    public AwesomeMachineContainer(InventoryPlayer inventoryPlayer, AwesomeTileEntityMachine customInventory) {
        super(inventoryPlayer, customInventory);
    }

    public AwesomeMachineContainer(InventoryPlayer inventoryPlayer, AwesomeTileEntityMachine customInventory, int inboxStartY) {
        super(inventoryPlayer, customInventory, inboxStartY);
    }

    protected AwesomeTileEntityMachine getTileEntity() {
        return (AwesomeTileEntityMachine) inventory;
    }

    @Override
    protected int getCustomSlotCount() {
        return getTileEntity().getCustomSlotCount();
    }
}
