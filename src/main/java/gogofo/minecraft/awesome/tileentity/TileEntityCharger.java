package gogofo.minecraft.awesome.tileentity;

import gogofo.minecraft.awesome.block.BlockCharger;
import gogofo.minecraft.awesome.gui.GuiEnum;
import gogofo.minecraft.awesome.interfaces.IAwesomeChargable;
import gogofo.minecraft.awesome.inventory.ContainerCharger;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityCharger extends AwesomeTileEntityMachine {
	private final static int CHARGE_SPEED = 1;
	
	private boolean wasCharging = false;
	
	@Override
	public String getName() {
		return "Charger";
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		boolean itemIsSame = stack != null && stack.isItemEqual(this.itemStackArray[index]) && ItemStack.areItemStackTagsEqual(stack, this.itemStackArray[index]);
        this.itemStackArray[index] = stack;

        if (stack != null && stack.stackSize > this.getInventoryStackLimit())
        {
            stack.stackSize = this.getInventoryStackLimit();
        }

        if (index == 0 && !itemIsSame)
        {
            this.markDirty();
        }
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}
	
	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return index == 0 && stack.getItem() instanceof IAwesomeChargable;
	}

	@Override
	public int getField(int id) {
		return 0;
	}

	@Override
	public void setField(int id, int value) {
	}

	@Override
	public int getFieldCount() {
		return 0;
	}

	@Override
	public void clear() {
		for (int i = 0; i < this.itemStackArray.length; ++i)
        {
            this.itemStackArray[i] = null;
        }
	}
	
	public String getGuiID()
    {
        return GuiEnum.CHARGER.guiName();
    }

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
		return new ContainerCharger(playerInventory, this);
	}
	
	@Override
	public void electricUpdate() {
		boolean isDirty = false;
		
		if (isCharging()) {
			getChargedItem().charge(itemStackArray[0], CHARGE_SPEED);
		} 
		
		wasCharging = isCharging();
		markDirty();
	}
	
	private boolean isCharging() {
		IAwesomeChargable item = getChargedItem();
		
		return item != null && item.getCharge(itemStackArray[0]) < item.getMaxCharge();
	}
	
	private IAwesomeChargable getChargedItem() {
		ItemStack stack = itemStackArray[0];
		
		if (stack != null && stack.getItem() instanceof IAwesomeChargable) {
			return (IAwesomeChargable)stack.getItem();
		}
		
		return null;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		
		wasCharging = isCharging();
	}

	@Override
	protected int[] getSlotsTop() {
		return new int[] {0};
	}

	@Override
	protected int[] getSlotsBottom() {
		return new int[] {};
	}

	@Override
	protected int[] getSlotsSides() {
		return new int[] {};
	}

	@Override
	protected int getSlotCount() {
		return 1;
	}

	@Override
	public int powerGeneratedWhenWorking() {
		return 0;
	}

	@Override
	public int powerRequiredWhenWorking() {
		return 1;
	}

	@Override
	public int powerGeneratedWhenIdle() {
		return 0;
	}

	@Override
	public int powerRequiredWhenIdle() {
		return 0;
	}

	@Override
	public boolean isWorking() {
		return isCharging();
	}
}
