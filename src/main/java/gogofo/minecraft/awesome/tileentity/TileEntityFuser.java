package gogofo.minecraft.awesome.tileentity;

import gogofo.minecraft.awesome.block.BlockFuser;
import gogofo.minecraft.awesome.gui.GuiEnum;
import gogofo.minecraft.awesome.init.Items;
import gogofo.minecraft.awesome.init.Recipes;
import gogofo.minecraft.awesome.inventory.ContainerFuser;
import gogofo.minecraft.awesome.recipe.RecipeFuser;
import gogofo.minecraft.awesome.recipe.RecipeFuser.Recipe;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;

public class TileEntityFuser extends AwesomeTileEntityMachine {
	public final static int REMAINING_FUSE_TIME_IDX = 0;
	public final static int CURRENT_RECP_FUSE_TIME_IDX = 1;
	public final static int CURRENT_RECP_FUSED_ITEM_1_IDX = 2;
	public final static int CURRENT_RECP_FUSED_ITEM_2_IDX = 3;
	
	private int remainingFuseTime;
	private int currentRecpFuseTime;
	private int currentRecpItem1;
	private int currentRecpItem2;
	
	@Override
	public String getName() {
		return "Fuser";
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		boolean itemIsSame = !stack.isEmpty() && stack.isItemEqual(this.itemStackArray[index]) && ItemStack.areItemStackTagsEqual(stack, this.itemStackArray[index]);
        this.itemStackArray[index] = stack;

        if (!stack.isEmpty() && stack.getCount() > this.getInventoryStackLimit())
        {
            stack.setCount(this.getInventoryStackLimit());
        }
        

        if (!itemIsSame)
        {
            this.markDirty();
        }
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return true;
	}

	@Override
	public int getField(int id) {
		switch (id) {
		case REMAINING_FUSE_TIME_IDX:
			return remainingFuseTime;
		case CURRENT_RECP_FUSE_TIME_IDX:
			return currentRecpFuseTime;
		case CURRENT_RECP_FUSED_ITEM_1_IDX:
			return currentRecpItem1;
		case CURRENT_RECP_FUSED_ITEM_2_IDX:
			return currentRecpItem2;
		}
		
		return 0;
	}

	@Override
	public void setField(int id, int value) {
		switch (id) {
		case REMAINING_FUSE_TIME_IDX:
			remainingFuseTime = value;
			break;
		case CURRENT_RECP_FUSE_TIME_IDX:
			currentRecpFuseTime = value;
			break;
		case CURRENT_RECP_FUSED_ITEM_1_IDX:
			currentRecpItem1 = value;
			break;
		case CURRENT_RECP_FUSED_ITEM_2_IDX:
			currentRecpItem2 = value;
			break;
		} 
	}

	@Override
	public int getFieldCount() {
		return 4;
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
        return GuiEnum.FUSER.guiName();
    }

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
		return new ContainerFuser(playerInventory, this);
	}
	
	private int getRecpFuseTime() {
		ItemStack stack1 = itemStackArray[0]; 
		ItemStack stack2 = itemStackArray[1];
		
		if (stack1.isEmpty() || stack2.isEmpty()) {
			return 0;
		}
		
		RecipeFuser.Recipe recipe = 
				Recipes.fuser.getRecipe(stack1.getItem(), stack2.getItem());
		
		if (recipe == null) {
			return 0;
		}
		
		return recipe.fuseTime;
	}

	@Override
	public void electricUpdate() {
		boolean isDirty = false;
		
		if (isFusing()) {
			remainingFuseTime -= 1;
			
			if (remainingFuseTime == 0) {
				generateFusedItem();
			}
		} 
		
		if (!isFusing() && canFuse()) {
			isDirty = true;
			
			Recipe recipe = getCurrRecipe();
			remainingFuseTime = currentRecpFuseTime = recipe.fuseTime;
			currentRecpItem1 = Item.getIdFromItem(recipe.item1);
			currentRecpItem2 = Item.getIdFromItem(recipe.item2);
			
			itemStackArray[0].shrink(1);
			itemStackArray[1].shrink(1);
		}
		
		if (isDirty) {
			markDirty();
		}
	}
	
	private void generateFusedItem() {
		ItemStack result = Recipes.fuser.getRecipe(Item.getItemById(currentRecpItem1), 
										  		   Item.getItemById(currentRecpItem2)).result;
		
		if (result == null) {
			return;
		}
		
		if (itemStackArray[2].isEmpty()) {
			itemStackArray[2] = result.copy();
			itemStackArray[2].setCount(0);
		}
		
		itemStackArray[2].grow(result.getCount());
		
		if (itemStackArray[2].getCount() > getInventoryStackLimit()) {
			itemStackArray[2].setCount(getInventoryStackLimit());
		}
	}
	
	public float getCurrentFusePercent() {
		if (currentRecpFuseTime == 0) {
			return 0;
		}
		
		return 1.0f - ((float)remainingFuseTime / (float)currentRecpFuseTime);
	}
	
	private RecipeFuser.Recipe getCurrRecipe() {
		if (itemStackArray[0].isEmpty() || itemStackArray[1].isEmpty()) {
			return null;
		}
		
		return Recipes.fuser.getRecipe(itemStackArray[0].getItem(), 
											 itemStackArray[1].getItem());
	}
	
	private boolean canFuse() {
		Recipe recipe = getCurrRecipe();
		
		return recipe != null &&
				(itemStackArray[2].isEmpty() ||
				(recipe.result.getItem() == itemStackArray[2].getItem() && 
				recipe.result.getMetadata() == itemStackArray[2].getMetadata() &&
				itemStackArray[2].getCount() < itemStackArray[2].getMaxStackSize()));
	}

	private boolean isFusing() {
		return remainingFuseTime > 0;
	}
	
	public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        this.remainingFuseTime = compound.getInteger("remainingFuseTime");
        this.currentRecpFuseTime = compound.getInteger("currentRecpFuseTime");
        this.currentRecpItem1 = compound.getInteger("currentRecpItem1");
        this.currentRecpItem2 = compound.getInteger("currentRecpItem2");
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setInteger("remainingFuseTime", remainingFuseTime);
        compound.setInteger("currentRecpFuseTime", currentRecpFuseTime);
        compound.setInteger("currentRecpItem1", currentRecpItem1);
        compound.setInteger("currentRecpItem2", currentRecpItem2);
        NBTTagList nbttaglist = new NBTTagList();
        
        return compound;
    }
    
    @Override
    protected Integer[] getDefaultSlotForFace(EnumFacing face) {
    	switch (face) {
		case UP:
			return new Integer[] {2};
		case DOWN:
			return new Integer[] {2};
		case NORTH:
			return new Integer[] {0};
		case SOUTH:
			return new Integer[] {1};
		case EAST:
			return new Integer[] {0};
		case WEST:
			return new Integer[] {1};
		default:
			return new Integer[] {};
		}
    }

	@Override
	protected int getSlotCount() {
		return 3;
	}
	
	@Override
	public int getCustomSlotCount() {
		return 3;
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
		return isFusing();
	}
}
