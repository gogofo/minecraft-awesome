package gogofo.minecraft.awesome.inventory;

import gogofo.minecraft.awesome.init.Recipes;
import gogofo.minecraft.awesome.interfaces.IAwesomeChargable;
import gogofo.minecraft.awesome.tileentity.TileEntityGenerator;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

public class SlotGrinder extends AwesomeSlot {

	public SlotGrinder(IInventory inventoryIn, int index, int xPosition, int yPosition) {
		super(inventoryIn, index, xPosition, yPosition);
	}

	/**
     * Check if the stack is a valid item for this slot. Always true beside for the armor slots.
     */
    public boolean isItemValid(ItemStack stack)
    {
    	return Recipes.grinder.getRecipe(stack.getItem()) != null;
    }
}
