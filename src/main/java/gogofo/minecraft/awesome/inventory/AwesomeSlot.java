package gogofo.minecraft.awesome.inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class AwesomeSlot extends Slot {
	
	private int categoryId = -1;
	
	public AwesomeSlot(IInventory inventoryIn, 
					   int index, 
					   int xPosition, 
					   int yPosition) {
		super(inventoryIn, index, xPosition, yPosition);
	}
	
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	
	public int getCategoryId() {
		return categoryId;
	}
}
