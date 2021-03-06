package gogofo.minecraft.awesome.tileentity;

import gogofo.minecraft.awesome.gui.GuiEnum;
import gogofo.minecraft.awesome.init.Items;
import gogofo.minecraft.awesome.init.Recipes;
import gogofo.minecraft.awesome.inventory.ContainerExtractor;
import gogofo.minecraft.awesome.recipe.RecipeExtractor;
import gogofo.minecraft.awesome.recipe.RecipeExtractor.Recipe;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;

public class TileEntityExtractor extends AwesomeTileEntityMachine {
	public final static int REMAINING_EXTRACTION_TIME_IDX = 0;
	public final static int CURRENT_RECP_EXTRACTION_TIME_IDX = 1;
	public final static int CURRENT_EXTRACTED_ITEM_IDX = 2;
	public static final float SPEED_BOOST_INCREMENTS = 0.25f;

	private int remainingExtractionTime;
	private int currentRecpExtractionTime;
	private int currentExtractedItem;
	
	@Override
	public String getName() {
		return "Extractor";
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return index == 0 && Recipes.extractor.getRecipe(stack.getItem()) != null;
	}

	@Override
	public int getField(int id) {
		switch (id) {
		case REMAINING_EXTRACTION_TIME_IDX:
			return remainingExtractionTime;
		case CURRENT_RECP_EXTRACTION_TIME_IDX:
			return currentRecpExtractionTime;
		case CURRENT_EXTRACTED_ITEM_IDX:
			return currentExtractedItem;
		}
		
		return 0;
	}

	@Override
	public void setField(int id, int value) {
		switch (id) {
		case REMAINING_EXTRACTION_TIME_IDX:
			remainingExtractionTime = value;
			break;
		case CURRENT_RECP_EXTRACTION_TIME_IDX:
			currentRecpExtractionTime = value;
			break;
		case CURRENT_EXTRACTED_ITEM_IDX:
			currentExtractedItem = value;
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
            this.itemStackArray[i] = ItemStack.EMPTY;
        }
	}
	
	public String getGuiID()
    {
        return GuiEnum.EXTRACTOR.guiName();
    }

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
		return new ContainerExtractor(playerInventory, this);
	}
	
	private int getRecpExtractionTime() {
		ItemStack stack = itemStackArray[0]; 
		
		if (stack.isEmpty()) {
			return 0;
		}
		
		RecipeExtractor.Recipe recipe = 
				Recipes.extractor.getRecipe(stack.getItem());
		
		if (recipe == null) {
			return 0;
		}
		
		return recipe.extractTime;
	}

	@Override
	public void electricUpdate() {
		boolean isDirty = false;
		
		if (isExtracting()) {
			float speedBoost = getUpgradeAmount(Items.machine_upgrade_speed) * SPEED_BOOST_INCREMENTS;
			remainingExtractionTime -= 1 + speedBoost;
			
			if (remainingExtractionTime <= 0) {
				generateExtractedItem();
			}
		} 
		
		if (!isExtracting() && canExtract()) {
			isDirty = true;
			
			Recipe recipe = getCurrRecipe();
			remainingExtractionTime = currentRecpExtractionTime = recipe.extractTime;
			currentExtractedItem = Item.getIdFromItem(recipe.input);
			
			itemStackArray[0].shrink(1);
			
			if (itemStackArray[0].isEmpty()) {
				itemStackArray[0] = ItemStack.EMPTY;
			}
		}
		
		if (isDirty) {
			markDirty();
		}
	}
	
	private void generateExtractedItem() {
		Recipe recipe = Recipes.extractor.getRecipe(Item.getItemById(currentExtractedItem));
		if (recipe == null) {
			return;
		}
		
		ItemStack result = recipe.result;
		
		if (result == null) {
			return;
		}
		
		if (itemStackArray[1].isEmpty()) {
			itemStackArray[1] = new ItemStack(result.getItem(), 0);
		}
		
		if (itemStackArray[1].getCount() < getInventoryStackLimit()) {
			itemStackArray[1].grow(result.getCount());
		}
	}
	
	public float getCurrentExtractPercent() {
		if (currentRecpExtractionTime == 0) {
			return 0;
		}
		
		return 1.0f - ((float)remainingExtractionTime / (float)currentRecpExtractionTime);
	}
	
	private RecipeExtractor.Recipe getCurrRecipe() {
		if (itemStackArray[0].isEmpty()) {
			return null;
		}
		
		return Recipes.extractor.getRecipe(itemStackArray[0].getItem());
	}
	
	private boolean canExtract() {
		Recipe recipe = getCurrRecipe();
		
		return recipe != null &&
				(itemStackArray[1].isEmpty() ||
				(recipe.result.getItem() == itemStackArray[1].getItem() &&
				itemStackArray[1].getCount() < itemStackArray[1].getMaxStackSize()));
	}

	private boolean isExtracting() {
		return remainingExtractionTime > 0;
	}
	
	public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        this.remainingExtractionTime = compound.getInteger("remainingExtractionTime");
        this.currentRecpExtractionTime = compound.getInteger("currentRecpExtractionTime");
        this.currentExtractedItem = compound.getInteger("currentExtractedItem");
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setInteger("remainingExtractionTime", remainingExtractionTime);
        compound.setInteger("currentRecpExtractionTime", currentRecpExtractionTime);
        compound.setInteger("currentExtractedItem", currentExtractedItem);
        NBTTagList nbttaglist = new NBTTagList();
        
        return compound;
    }
    
    @Override
    public Integer[] getDefaultSlotForFace(EnumFacing face) {
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
		return isExtracting();
	}
}
