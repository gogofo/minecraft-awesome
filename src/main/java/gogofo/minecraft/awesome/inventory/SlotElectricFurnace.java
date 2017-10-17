package gogofo.minecraft.awesome.inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

public class SlotElectricFurnace extends AwesomeSlot {

	public SlotElectricFurnace(IInventory inventoryIn, int index, int xPosition, int yPosition) {
		super(inventoryIn, index, xPosition, yPosition);
	}

	/**
     * Check if the stack is a valid item for this slot. Always true beside for the armor slots.
     */
    public boolean isItemValid(ItemStack stack)
    {
    	return !FurnaceRecipes.instance().getSmeltingResult(stack).isEmpty();
    }
}
