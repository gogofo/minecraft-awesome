package gogofo.minecraft.awesome.tileentity;

import gogofo.minecraft.awesome.gui.GuiEnum;
import gogofo.minecraft.awesome.init.Recipes;
import gogofo.minecraft.awesome.inventory.ContainerGrinder;
import gogofo.minecraft.awesome.recipe.RecipeGrinder;
import gogofo.minecraft.awesome.recipe.RecipeGrinder.Recipe;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;

public class TileEntityGrinder extends AwesomeTileEntityMachine {
	public final static int REMAINING_GRIND_TIME_IDX = 0;
	public final static int CURRENT_RECP_GRIND_TIME_IDX = 1;
	public final static int CURRENT_GROUND_ITEM_IDX = 2;
	
	private int remainingGrindTime;
	private int currentRecpGrindTime;
	private int currentGroundItem;
	
	@Override
	public String getName() {
		return "Grinder";
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
		return index == 0 && Recipes.grinder.getRecipe(stack.getItem()) != null;
	}

	@Override
	public int getField(int id) {
		switch (id) {
		case REMAINING_GRIND_TIME_IDX:
			return remainingGrindTime;
		case CURRENT_RECP_GRIND_TIME_IDX:
			return currentRecpGrindTime;
		case CURRENT_GROUND_ITEM_IDX:
			return currentGroundItem;
		}
		
		return 0;
	}

	@Override
	public void setField(int id, int value) {
		switch (id) {
		case REMAINING_GRIND_TIME_IDX:
			remainingGrindTime = value;
			break;
		case CURRENT_RECP_GRIND_TIME_IDX:
			currentRecpGrindTime = value;
			break;
		case CURRENT_GROUND_ITEM_IDX:
			currentGroundItem = value;
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
        return GuiEnum.GRINDER.guiName();
    }

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
		return new ContainerGrinder(playerInventory, this);
	}
	
	private int getRecpGrindTime() {
		ItemStack stack = itemStackArray[0]; 
		
		if (stack == null) {
			return 0;
		}
		
		RecipeGrinder.Recipe recipe = 
				Recipes.grinder.getRecipe(stack.getItem());
		
		if (recipe == null) {
			return 0;
		}
		
		return recipe.grindTime;
	}

	@Override
	public void electricUpdate() {
		boolean isDirty = false;
		
		if (isGrinding()) {
			remainingGrindTime -= 1;
			
			if (remainingGrindTime == 0) {
				generateGroundItem();
			}
		} 
		
		if (!isGrinding() && canGrind()) {
			isDirty = true;
			
			Recipe recipe = getCurrRecipe();
			remainingGrindTime = currentRecpGrindTime = recipe.grindTime;
			currentGroundItem = Item.getIdFromItem(recipe.input);
			
			itemStackArray[0].stackSize -= 1;
			
			if (itemStackArray[0].stackSize == 0) {
				itemStackArray[0] = null;
			}
		}
		
		if (isDirty) {
			markDirty();
		}
	}
	
	private void generateGroundItem() {
		Recipe recipe = Recipes.grinder.getRecipe(Item.getItemById(currentGroundItem));
		if (recipe == null) {
			return;
		}
		
		ItemStack result = recipe.result;
		
		if (result == null) {
			return;
		}
		
		if (itemStackArray[1] == null) {
			itemStackArray[1] = new ItemStack(result.getItem(), 0);
		}
		
		if (itemStackArray[1].stackSize < getInventoryStackLimit()) {
			itemStackArray[1].stackSize += result.stackSize;
		}
	}
	
	public float getCurrentGrindPercent() {
		if (currentRecpGrindTime == 0) {
			return 0;
		}
		
		return 1.0f - ((float)remainingGrindTime / (float)currentRecpGrindTime);
	}
	
	private RecipeGrinder.Recipe getCurrRecipe() {
		if (itemStackArray[0] == null) {
			return null;
		}
		
		return Recipes.grinder.getRecipe(itemStackArray[0].getItem());
	}
	
	private boolean canGrind() {
		Recipe recipe = getCurrRecipe();
		
		return recipe != null &&
				(itemStackArray[1] == null ||
				(recipe.result.getItem() == itemStackArray[1].getItem() &&
				itemStackArray[1].stackSize < itemStackArray[1].getMaxStackSize()));
	}

	private boolean isGrinding() {
		return remainingGrindTime > 0;
	}
	
	public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        this.remainingGrindTime = compound.getInteger("remainingGrindTime");
        this.currentRecpGrindTime = compound.getInteger("currentRecpGrindTime");
        this.currentGroundItem = compound.getInteger("currentGroundItem");
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setInteger("remainingGrindTime", remainingGrindTime);
        compound.setInteger("currentRecpGrindTime", currentRecpGrindTime);
        compound.setInteger("currentGroundItem", currentGroundItem);
        NBTTagList nbttaglist = new NBTTagList();
        
        return compound;
    }
    
    @Override
	public int[] getSlotsForFace(EnumFacing side) {
		switch (side) {
		case UP:
			return new int[] {0};
		case DOWN:
			return new int[] {1};
		case NORTH:
			return new int[] {};
		case SOUTH:
			return new int[] {};
		case EAST:
			return new int[] {};
		case WEST:
			return new int[] {};
		default:
			return new int[] {};
		}
	}

	@Override
	protected int getSlotCount() {
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
		return isGrinding();
	}
}
