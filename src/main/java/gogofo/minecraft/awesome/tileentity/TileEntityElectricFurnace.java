package gogofo.minecraft.awesome.tileentity;

import gogofo.minecraft.awesome.PowerManager;
import gogofo.minecraft.awesome.gui.GuiEnum;
import gogofo.minecraft.awesome.init.Items;
import gogofo.minecraft.awesome.inventory.ContainerElectricFurnace;
import gogofo.minecraft.awesome.item.ItemLiquidContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumFacing;

public class TileEntityElectricFurnace extends AwesomeTileEntityMachine {

	public final static int REMAINING_COOK_TIME_IDX = 0;
	public final static int CURRENT_ITEM_COOK_TIME_IDX = 1;
	public final static int CURRENT_COOK_RESULT = 2;
	
	private int remainingCookTime;
	private int currentItemCookTime;
	private int currentCookResult;
	
	public TileEntityElectricFurnace() {
	}
	
	@Override
	public String getName() {
		return "Electric Furnace";
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
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return index == 0 && isItemCookable(stack);
	}

	@Override
	public int getField(int id) {
		switch (id) {
		case REMAINING_COOK_TIME_IDX:
			return remainingCookTime;
		case CURRENT_ITEM_COOK_TIME_IDX:
			return currentItemCookTime;
		case CURRENT_COOK_RESULT:
			return currentCookResult;
		}
		
		return 0;
	}

	@Override
	public void setField(int id, int value) {
		switch (id) {
		case REMAINING_COOK_TIME_IDX:
			remainingCookTime = value;
			break;
		case CURRENT_ITEM_COOK_TIME_IDX:
			currentItemCookTime = value;
			break;
		case CURRENT_COOK_RESULT:
			currentCookResult = value;
			break;
		} 
	}

	@Override
	public int getFieldCount() {
		return 3;
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
        return GuiEnum.ELECTRIC_FURNACE.guiName();
    }

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
		return new ContainerElectricFurnace(playerInventory, this);
	}
	
	public static boolean isItemCookable(ItemStack stack) {
		if (stack == null) {
			return false;
		}
		
		return FurnaceRecipes.instance().getSmeltingResult(stack) != null;
	}
	
	public static int getItemCookTime(ItemStack stack) {
		if (stack == null) {
			return 0;
		}
		
		return 150;
	}

	@Override
	public void electricUpdate() {
		boolean isDirty = false;
		
		if (isCooking()) {
			remainingCookTime -= 1;
			
			if (remainingCookTime == 0) {
				cookItem();
			}
		} 
		
		if (!isCooking() && canCook()) {
			isDirty = true;
			
			remainingCookTime = currentItemCookTime = getItemCookTime(itemStackArray[0]);
			currentCookResult = Item.getIdFromItem(FurnaceRecipes.instance().getSmeltingResult(itemStackArray[0]).getItem());
			
			itemStackArray[0].stackSize -= 1;
			
			if (itemStackArray[0].stackSize == 0) {
				itemStackArray[0] = null;
			}
		}
		
		if (isDirty) {
			markDirty();
		}
	}
	
	public void cookItem() {
		if (itemStackArray[1] == null) {
			itemStackArray[1] = new ItemStack(Item.getItemById(currentCookResult), 0);
		}
		
		// Hack to solve missing cooking item, should try to remove in later version
		if (itemStackArray[1].getItem() == null) {
			return;
		}
		
		if (itemStackArray[1].stackSize < getInventoryStackLimit()) {
			itemStackArray[1].stackSize += 1;
		}
	}
	
	public float getCurrentCookedPercent() {
		if (currentItemCookTime == 0) {
			return 0;
		}
		
		return 1.0f - ((float)remainingCookTime / (float)currentItemCookTime);
	}
	
	private boolean canCook() {
		if (itemStackArray[0] == null) {
			return false;
		}
		
		ItemStack result = FurnaceRecipes.instance().getSmeltingResult(itemStackArray[0]);
		return result != null &&
				(itemStackArray[1] == null || 
				(itemStackArray[1].stackSize < getInventoryStackLimit() &&
				itemStackArray[1].getItem() == result.getItem()));
	}
	
	private boolean isCooking() {
		return remainingCookTime > 0;
	}
	
	public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        this.remainingCookTime = compound.getInteger("RemainingCookTime");
        this.currentItemCookTime = compound.getInteger("currentItemCookTime");
        this.currentCookResult = compound.getInteger("currentCookResult");
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setInteger("RemainingCookTime", remainingCookTime);
        compound.setInteger("currentItemCookTime", currentItemCookTime);
        compound.setInteger("currentCookResult", currentCookResult);
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
		return isCooking();
	}
}
