package gogofo.minecraft.awesome.inventory;

import gogofo.minecraft.awesome.interfaces.IMachineUpgrade;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class MachineUpgradeSlot extends Slot {

	private boolean isVisible = false;

	public MachineUpgradeSlot(IInventory inventoryIn,
                              int index,
                              int xPosition,
                              int yPosition) {
		super(inventoryIn, index, xPosition, yPosition);
	}

	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean visible) {
		isVisible = visible;
	}

	@Override
	public boolean isEnabled() {
		return isVisible;
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		return stack.getItem() instanceof IMachineUpgrade;
	}

	@Override
	public int getSlotStackLimit() {
		return 1;
	}
}
