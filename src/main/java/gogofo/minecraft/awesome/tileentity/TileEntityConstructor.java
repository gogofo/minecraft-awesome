package gogofo.minecraft.awesome.tileentity;

import gogofo.minecraft.awesome.gui.GuiEnum;
import gogofo.minecraft.awesome.inventory.ContainerConstructor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

public class TileEntityConstructor extends AwesomeTileEntityMachine {
	public final static int OIL_AMOUNT_IDX = 0;
	public final static int REMAINING_REST_TIME_IDX = 1;

	private int oilAmount;
	private int remainingRestTime;

	@Override
	public String getName() {
		return "Constructor";
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return true;
	}

	@Override
	public int getField(int id) {
		switch (id) {
		case OIL_AMOUNT_IDX:
			return oilAmount;
		case REMAINING_REST_TIME_IDX:
			return remainingRestTime;
		}

		return 0;
	}

	@Override
	public void setField(int id, int value) {
		switch (id) {
		case OIL_AMOUNT_IDX:
			oilAmount = value;
			break;
		case REMAINING_REST_TIME_IDX:
			remainingRestTime = value;
			break;
		}
	}

	@Override
	public int getFieldCount() {
		return 2;
	}

	public String getGuiID()
    {
        return GuiEnum.CONSTRUCTOR.guiName();
    }

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
		return new ContainerConstructor(playerInventory, this);
	}

	@Override
	public void electricUpdate() {
		boolean isDirty = false;

		// TODO:

		if (isDirty) {
			markDirty();
		}
	}
	
	public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        oilAmount = compound.getInteger("oilAmount");
        remainingRestTime = compound.getInteger("remainingRestTime");
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setInteger("oilAmount", oilAmount);
        compound.setInteger("remainingRestTime", remainingRestTime);
        
        return compound;
    }
    
    @Override
    protected Integer[] getDefaultSlotForFace(EnumFacing face) {
    	switch (face) {
		case UP:
			return new Integer[] {};
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
		return 0;
	}
	
	@Override
	public int getCustomSlotCount() {
		return 0;
	}

	@Override
	public int powerGeneratedWhenWorking() {
		return 0;
	}

	@Override
	public int powerRequiredWhenWorking() {
		return 0;
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
		return hasPower();
	}
}
