package gogofo.minecraft.awesome.tileentity;

import gogofo.minecraft.awesome.PowerManager;
import gogofo.minecraft.awesome.block.BlockGenerator;
import gogofo.minecraft.awesome.gui.GuiEnum;
import gogofo.minecraft.awesome.init.Items;
import gogofo.minecraft.awesome.inventory.ContainerGenerator;
import gogofo.minecraft.awesome.item.ItemLiquidContainer;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumFacing;

public class TileEntityGenerator extends AwesomeTileEntityMachine {

	public final static int REMAINING_BURN_TIME_IDX = 0;
	public final static int CURRENT_ITEM_BURN_TIME_IDX = 1;
	
	private int remainingBurnTime;
	private int currentItemBurnTime;
	
	public TileEntityGenerator() {
	}
	
	@Override
	public String getName() {
		return "Generator";
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		boolean itemIsSame = !stack.isEmpty() && stack.isItemEqual(this.itemStackArray[index]) && ItemStack.areItemStackTagsEqual(stack, this.itemStackArray[index]);
        this.itemStackArray[index] = stack;

        if (!stack.isEmpty() && stack.getCount() > this.getInventoryStackLimit())
        {
            stack.setCount(this.getInventoryStackLimit());
        }

        if (index == 0 && !itemIsSame)
        {
            this.markDirty();
        }
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return index == 0 && isItemFuel(stack);
	}

	@Override
	public int getField(int id) {
		switch (id) {
		case REMAINING_BURN_TIME_IDX:
			return remainingBurnTime;
		case CURRENT_ITEM_BURN_TIME_IDX:
			return currentItemBurnTime;
		}
		
		return 0;
	}

	@Override
	public void setField(int id, int value) {
		switch (id) {
		case REMAINING_BURN_TIME_IDX:
			remainingBurnTime = value;
			break;
		case CURRENT_ITEM_BURN_TIME_IDX:
			currentItemBurnTime = value;
			break;
		} 
	}

	@Override
	public int getFieldCount() {
		return 2;
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
        return GuiEnum.GENERATOR.guiName();
    }

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
		return new ContainerGenerator(playerInventory, this);
	}

	public static boolean isItemFuel(ItemStack stack) {
		return getItemBurnTime(stack) > 0;
	}
	
	public static int getItemBurnTime(ItemStack stack) {
		if (stack.isEmpty()) {
			return 0;
		}
		
		if (stack.getItem() == Items.liquid_container && 
			ItemLiquidContainer.getLiquidFill(stack) > 0 &&
			isLava(ItemLiquidContainer.getLiquidType(stack))) {
			return 1000000;
		}
		
		return TileEntityFurnace.getItemBurnTime(stack) * 10;
	}

	private static boolean isLava(Block liquidType) {
		return liquidType == net.minecraft.init.Blocks.FLOWING_LAVA ||
				liquidType == net.minecraft.init.Blocks.LAVA;
	}

	@Override
	public void electricUpdate() {
		boolean isDirty = false;
		
		if (isBurning()) {
			remainingBurnTime -= 1;
			
			if (remainingBurnTime == 0) {
				generateResidue();
			}
		} 
		
		if (!isBurning() && canBurn()) {
			isDirty = true;
			
			remainingBurnTime = currentItemBurnTime = getItemBurnTime(itemStackArray[0]);
			
			if (itemStackArray[0].getItem() == Items.liquid_container) {
				int newFill = ItemLiquidContainer.getLiquidFill(itemStackArray[0]) - 1;
				ItemLiquidContainer.setLiquidFill(itemStackArray[0], newFill);
			} else {
				itemStackArray[0].shrink(1);
			}
			
			if (itemStackArray[0].isEmpty()) {
				itemStackArray[0] = ItemStack.EMPTY;
			}
		}
		
		if (isDirty) {
			markDirty();
		}
	}
	
	public void generateResidue() {
		if (itemStackArray[1].isEmpty()) {
			itemStackArray[1] = new ItemStack(Items.burnt_residue, 0);
		}
		
		if (itemStackArray[1].getCount() < getInventoryStackLimit()) {
			itemStackArray[1].grow(1);
		}
	}
	
	public float getCurrentBurntPercent() {
		if (currentItemBurnTime == 0) {
			return 0;
		}
		
		return 1.0f - ((float)remainingBurnTime / (float)currentItemBurnTime);
	}
	
	private boolean canBurn() {
		return !itemStackArray[0].isEmpty() && 
				isItemFuel(itemStackArray[0]) && 
				(itemStackArray[1].isEmpty() || 
				itemStackArray[1].getCount() < getInventoryStackLimit());
	}
	
	private boolean isBurning() {
		return remainingBurnTime > 0;
	}
	
	public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        this.remainingBurnTime = compound.getShort("RemainingBurnTime");
        this.currentItemBurnTime = getItemBurnTime(itemStackArray[0]);
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setShort("RemainingBurnTime", (short)remainingBurnTime);
        NBTTagList nbttaglist = new NBTTagList();
        
        return compound;
    }

    @Override
    protected Integer[] getDefaultSlotForFace(EnumFacing face) {
    	switch (face) {
		case UP:
			return new Integer[] {0};
		case DOWN:
			return new Integer[] {1};
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
		return 2;
	}
	
	@Override
	public int getCustomSlotCount() {
		return 2;
	}

	@Override
	public int powerGeneratedWhenWorking() {
		return 10;
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
		return isBurning();
	}
}
