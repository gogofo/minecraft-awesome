package gogofo.minecraft.awesome.inventory;

import gogofo.minecraft.awesome.gui.GuiEntityMachineBlock;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;

public class ContainerDigger extends ContainerEntityMachineBlock {
    public ContainerDigger(InventoryPlayer inventoryPlayer, IInventory inventory) {
    	super(inventoryPlayer, inventory);

    	int DIGGER_INVENTORY_START = GuiEntityMachineBlock.CLEAR_HEIGHT - 3*18 - 18;

        addSlotGrid(super.getCustomSlotCount(), 8, DIGGER_INVENTORY_START, 3, 9);
    }

    @Override
    protected int getCustomSlotCount() {
        return super.getCustomSlotCount() + 27;
    }
}
