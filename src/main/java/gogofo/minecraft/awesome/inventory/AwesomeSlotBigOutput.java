package gogofo.minecraft.awesome.inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class AwesomeSlotBigOutput extends AwesomeSlotBig {

	public AwesomeSlotBigOutput(IInventory inventoryIn, int index, int xPosition, int yPosition) {
		super(inventoryIn, index, xPosition, yPosition);
	}

	public boolean isItemValid(ItemStack stack)
    {
        return false;
    }
}
