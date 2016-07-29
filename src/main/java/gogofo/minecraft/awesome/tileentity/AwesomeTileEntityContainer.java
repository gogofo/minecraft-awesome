package gogofo.minecraft.awesome.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class AwesomeTileEntityContainer extends TileEntityLockable implements ISidedInventory {
	private int[] slotsTop;
	private int[] slotsBottom;
	private int[] slotsSides;
	protected ItemStack[] itemStackArray;
	
	protected abstract int[] getSlotsTop();
	protected abstract int[] getSlotsBottom();
	protected abstract int[] getSlotsSides();
	protected abstract int getSlotCount();
	
	public AwesomeTileEntityContainer() {
		slotsTop = getSlotsTop();
		slotsBottom = getSlotsBottom();
		slotsSides = getSlotsSides();
		
		itemStackArray = new ItemStack[getSlotCount()];
	}
	
	@Override
    public int getSizeInventory()
    {
        return itemStackArray.length;
    }
	
	@Override
    public ItemStack getStackInSlot(int index)
    {
        return itemStackArray[index];
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
    public ItemStack decrStackSize(int index, int count)
    {
        if (itemStackArray[index] != null)
        {
            ItemStack itemstack;

            if (itemStackArray[index].stackSize <= count)
            {
                itemstack = itemStackArray[index];
                itemStackArray[index] = null;
                this.markDirty();
                return itemstack;
            }
            else
            {
                itemstack = itemStackArray[index].splitStack(count);

                if (itemStackArray[index].stackSize == 0)
                {
                    itemStackArray[index] = null;
                }

                this.markDirty();
                return itemstack;
            }
        }
        else
        {
            return null;
        }
    }
	
	public ItemStack removeStackFromSlot(int index) {
		if (itemStackArray[index] != null)
		{
	        ItemStack itemstack = itemStackArray[index];
	        itemStackArray[index] = null;
	        return itemstack;
		}
		else
		{
			return null;
		}
	}
    
    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        NBTTagList nbttaglist = compound.getTagList("Items", 10);
        itemStackArray = new ItemStack[getSizeInventory()];

        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            NBTTagCompound nbtTagCompound = nbttaglist.getCompoundTagAt(i);
            byte b0 = nbtTagCompound.getByte("Slot");

            if (b0 >= 0 && b0 < itemStackArray.length)
            {
                itemStackArray[b0] = ItemStack.loadItemStackFromNBT(
                      nbtTagCompound);
            }
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < itemStackArray.length; ++i)
        {
            if (itemStackArray[i] != null)
            {
                NBTTagCompound nbtTagCompound = new NBTTagCompound();
                nbtTagCompound.setByte("Slot", (byte)i);
                itemStackArray[i].writeToNBT(nbtTagCompound);
                nbttaglist.appendTag(nbtTagCompound);
            }
        }

        compound.setTag("Items", nbttaglist);
        
        return compound;
    }
    
    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }
    
 // this function indicates whether container texture should be drawn
    @SideOnly(Side.CLIENT)
    public static boolean func_174903_a(IInventory parIInventory)
    {
        return true ;
    }
    
    @Override
    public boolean isUseableByPlayer(EntityPlayer playerIn)
    {
        return worldObj.getTileEntity(pos) != this ? false : 
              playerIn.getDistanceSq(pos.getX()+0.5D, pos.getY()+0.5D, 
              pos.getZ()+0.5D) <= 64.0D;
    }
    
    @Override
    public void openInventory(EntityPlayer playerIn) {}

    @Override
    public void closeInventory(EntityPlayer playerIn) {}
    
    @Override
    public int[] getSlotsForFace(EnumFacing side)
    {
        return side == EnumFacing.DOWN ? slotsBottom : 
              (side == EnumFacing.UP ? slotsTop : slotsSides);
    }
    
    @Override
    public boolean canInsertItem(int index, ItemStack itemStackIn, 
          EnumFacing direction)
    {
        return isItemValidForSlot(index, itemStackIn);
    }

    @Override
    public boolean canExtractItem(int parSlotIndex, ItemStack parStack, 
          EnumFacing parFacing)
    {
        return true;
    }
    
    @Override
    public void clear()
    {
        for (int i = 0; i < itemStackArray.length; ++i)
        {
            itemStackArray[i] = null;
        }
    }
    
    @Override
	public boolean hasCustomName() {
		return false;
	}
    
    public boolean hasItems() {
    	for (ItemStack stack : itemStackArray) {
    		if (stack != null && stack.stackSize > 0) {
    			return true;
    		}
    	}
    	
    	return false;
    }
}
