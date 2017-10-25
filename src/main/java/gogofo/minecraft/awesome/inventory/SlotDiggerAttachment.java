package gogofo.minecraft.awesome.inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;

public class SlotDiggerAttachment extends Slot {

	public SlotDiggerAttachment(IInventory inventoryIn, int index, int xPosition, int yPosition) {
		super(inventoryIn, index, xPosition, yPosition);
	}

    public boolean isItemValid(ItemStack stack)
    {
    	return stack.getItem() instanceof ItemTool;
    }
}
