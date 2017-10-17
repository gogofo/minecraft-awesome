package gogofo.minecraft.awesome.tileentity;

import gogofo.minecraft.awesome.gui.GuiEnum;
import gogofo.minecraft.awesome.init.Items;
import gogofo.minecraft.awesome.interfaces.IAwesomeChargable;
import gogofo.minecraft.awesome.inventory.ContainerCharger;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

public class TileEntityCharger extends AwesomeTileEntityMachine {
	private final static int BASE_CHARGE_SPEED = 1;
	
	private boolean wasCharging = false;
	private int chargeSpeed = BASE_CHARGE_SPEED;
	
	@Override
	public String getName() {
		return "Charger";
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
            this.itemStackArray[i] = ItemStack.EMPTY;
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
		if (isCharging()) {
			getChargedItem().charge(itemStackArray[0], chargeSpeed);
		} 
		
		wasCharging = isCharging();
		markDirty();
	}

	@Override
	protected void onUpgradeAdded(Item upgrade) {
		if (upgrade == Items.machine_upgrade_speed) {
			chargeSpeed += BASE_CHARGE_SPEED;
		}
	}

	@Override
	protected void onUpgradeRemoved(Item upgrade) {
		if (upgrade == Items.machine_upgrade_speed) {
			chargeSpeed -= BASE_CHARGE_SPEED;
		}
	}

	private boolean isCharging() {
		IAwesomeChargable item = getChargedItem();
		
		return item != null && item.getCharge(itemStackArray[0]) < item.getMaxCharge();
	}
	
	private IAwesomeChargable getChargedItem() {
		ItemStack stack = itemStackArray[0];
		
		if (!stack.isEmpty() && stack.getItem() instanceof IAwesomeChargable) {
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
	public Integer[] getDefaultSlotForFace(EnumFacing face) {
		switch (face) {
		case UP:
			return new Integer[] {0};
		case DOWN:
			return new Integer[] {};
		case NORTH:
			return new Integer[] {};
		case SOUTH:
			return new Integer[] {};
		case EAST:
			return new Integer[] {};
		case WEST:
			return new Integer[] {};
		default:
			return new Integer[] {};
		}
	}

	@Override
	protected int getSlotCount() {
		return 1;
	}
	
	@Override
	public int getCustomSlotCount() {
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
