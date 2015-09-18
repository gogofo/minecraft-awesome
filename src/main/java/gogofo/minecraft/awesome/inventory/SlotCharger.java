package gogofo.minecraft.awesome.inventory;

import gogofo.minecraft.awesome.interfaces.IAwesomeChargable;
import gogofo.minecraft.awesome.tileentity.TileEntityGenerator;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

public class SlotCharger extends Slot {

	public SlotCharger(IInventory inventoryIn, int index, int xPosition, int yPosition) {
		super(inventoryIn, index, xPosition, yPosition);
	}

	/**
     * Check if the stack is a valid item for this slot. Always true beside for the armor slots.
     */
    public boolean isItemValid(ItemStack stack)
    {
    	return stack.getItem() instanceof IAwesomeChargable;
    }
}
