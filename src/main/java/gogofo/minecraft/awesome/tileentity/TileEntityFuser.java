package gogofo.minecraft.awesome.tileentity;

import gogofo.minecraft.awesome.gui.GuiEnum;
import gogofo.minecraft.awesome.init.Recipes;
import gogofo.minecraft.awesome.inventory.ContainerFuser;
import gogofo.minecraft.awesome.recipe.RecipeFuser;
import gogofo.minecraft.awesome.recipe.RecipeFuser.Recipe;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

import java.util.Arrays;

public class TileEntityFuser extends AwesomeTileEntityMachine {
	public final static int REMAINING_FUSE_TIME_IDX = 0;
	public final static int CURRENT_RECP_FUSE_TIME_IDX = 1;
	public final static int CURRENT_RECP_FUSED_ITEM_1_IDX = 2;
	public final static int CURRENT_RECP_FUSED_ITEM_2_IDX = 3;
	public final static int CURRENT_RECP_FUSED_ITEM_3_IDX = 4;
	public final static int CURRENT_RECP_FUSED_ITEM_4_IDX = 5;
	public final static int CURRENT_RECP_FUSED_ITEM_5_IDX = 6;
	public final static int CURRENT_RECP_FUSED_ITEM_6_IDX = 7;
	public final static int CURRENT_RECP_FUSED_ITEM_7_IDX = 8;
	public final static int CURRENT_RECP_FUSED_ITEM_8_IDX = 9;
	public final static int CURRENT_RECP_FUSED_ITEM_9_IDX = 10;

	public static final int RESULT_SLOT = 9;

	private int remainingFuseTime;
	private int currentRecpFuseTime;
	private int[] currentRecpItems = new int[10];
	
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
			case CURRENT_RECP_FUSED_ITEM_2_IDX:
			case CURRENT_RECP_FUSED_ITEM_3_IDX:
			case CURRENT_RECP_FUSED_ITEM_4_IDX:
			case CURRENT_RECP_FUSED_ITEM_5_IDX:
			case CURRENT_RECP_FUSED_ITEM_6_IDX:
			case CURRENT_RECP_FUSED_ITEM_7_IDX:
			case CURRENT_RECP_FUSED_ITEM_8_IDX:
			case CURRENT_RECP_FUSED_ITEM_9_IDX:
				return currentRecpItems[id - CURRENT_RECP_FUSED_ITEM_1_IDX];
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
			case CURRENT_RECP_FUSED_ITEM_2_IDX:
			case CURRENT_RECP_FUSED_ITEM_3_IDX:
			case CURRENT_RECP_FUSED_ITEM_4_IDX:
			case CURRENT_RECP_FUSED_ITEM_5_IDX:
			case CURRENT_RECP_FUSED_ITEM_6_IDX:
			case CURRENT_RECP_FUSED_ITEM_7_IDX:
			case CURRENT_RECP_FUSED_ITEM_8_IDX:
			case CURRENT_RECP_FUSED_ITEM_9_IDX:
				currentRecpItems[id - CURRENT_RECP_FUSED_ITEM_1_IDX] = value;
				break;
		} 
	}

	@Override
	public int getFieldCount() {
		return 11;
	}
	
	public String getGuiID()
    {
        return GuiEnum.FUSER.guiName();
    }

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
		return new ContainerFuser(playerInventory, this);
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

			Arrays.fill(currentRecpItems, 0);
			for (int i = 0; i < recipe.items.length; i++) {
				currentRecpItems[i] = Item.getIdFromItem(recipe.items[i]);
			}

			Arrays.stream(itemStackArray)
					.filter(stack -> stack != itemStackArray[RESULT_SLOT])
					.forEach(stack -> stack.shrink(1));
		}
		
		if (isDirty) {
			markDirty();
		}
	}
	
	private void generateFusedItem() {
		Item[] items = Arrays.stream(currentRecpItems)
				.filter(id -> id >= 0)
				.mapToObj(Item::getItemById)
				.filter(item -> item != Items.AIR)
				.toArray(Item[]::new);

		ItemStack result = Recipes.fuser.getRecipe(items).result;
		
		if (result == null) {
			return;
		}
		
		if (itemStackArray[RESULT_SLOT].isEmpty()) {
			itemStackArray[RESULT_SLOT] = result.copy();
			itemStackArray[RESULT_SLOT].setCount(0);
		}
		
		itemStackArray[RESULT_SLOT].grow(result.getCount());
		
		if (itemStackArray[RESULT_SLOT].getCount() > getInventoryStackLimit()) {
			itemStackArray[RESULT_SLOT].setCount(getInventoryStackLimit());
		}
	}
	
	public float getCurrentFusePercent() {
		if (currentRecpFuseTime == 0) {
			return 0;
		}
		
		return 1.0f - ((float)remainingFuseTime / (float)currentRecpFuseTime);
	}
	
	private RecipeFuser.Recipe getCurrRecipe() {
		Item[] items = Arrays.stream(itemStackArray)
				.filter(stack -> !stack.isEmpty() && stack != itemStackArray[RESULT_SLOT])
				.map(ItemStack::getItem)
				.toArray(Item[]::new);

		if (items.length == 0) {
			return null;
		}

		return Recipes.fuser.getRecipe(items);
	}
	
	private boolean canFuse() {
		Recipe recipe = getCurrRecipe();
		
		return recipe != null &&
				(itemStackArray[RESULT_SLOT].isEmpty() ||
				(recipe.result.getItem() == itemStackArray[RESULT_SLOT].getItem() &&
				recipe.result.getMetadata() == itemStackArray[RESULT_SLOT].getMetadata() &&
				itemStackArray[RESULT_SLOT].getCount() < itemStackArray[RESULT_SLOT].getMaxStackSize()));
	}

	private boolean isFusing() {
		return remainingFuseTime > 0;
	}
	
	public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        this.remainingFuseTime = compound.getInteger("remainingFuseTime");
        this.currentRecpFuseTime = compound.getInteger("currentRecpFuseTime");

        if (compound.hasKey("currentRecpItems")) {
			this.currentRecpItems = compound.getIntArray("currentRecpItems");
		}
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setInteger("remainingFuseTime", remainingFuseTime);
        compound.setInteger("currentRecpFuseTime", currentRecpFuseTime);
        compound.setIntArray("currentRecpItems", currentRecpItems);
        
        return compound;
    }
    
    @Override
	public Integer[] getDefaultSlotForFace(EnumFacing face) {
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
		return 10;
	}
	
	@Override
	public int getCustomSlotCount() {
		return 10;
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
