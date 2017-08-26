package gogofo.minecraft.awesome.tileentity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.ToIntFunction;

import gogofo.minecraft.awesome.interfaces.IConfigurableSidedInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.WorldServer;
import net.minecraftforge.client.model.b3d.B3DModel.Face;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class AwesomeTileEntityContainer extends TileEntityLockable implements IConfigurableSidedInventory {
	protected ItemStack[] itemStackArray;
	private ArrayList<ArrayList<Integer>> slotsForFace;
	
	protected abstract Integer[] getDefaultSlotForFace(EnumFacing face);
	protected abstract int getSlotCount();
	public abstract int getCustomSlotCount();
	
	public AwesomeTileEntityContainer() {
		itemStackArray = new ItemStack[getSlotCount()];
		Arrays.fill(itemStackArray, ItemStack.EMPTY);
		
		initSlotsFotFace(true);
	}
	
	private void initSlotsFotFace(boolean defaults) {
		slotsForFace = new ArrayList<ArrayList<Integer>>();
		
		for (EnumFacing face : EnumFacing.values()) {
			slotsForFace.add(new ArrayList<Integer>());
			
			if (defaults) {
				slotsForFace.get(face.getIndex()).addAll(Arrays.asList(getDefaultSlotForFace(face)));
			}
		}
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
    public ItemStack decrStackSize(int index, int count)
    {
        if (!itemStackArray[index].isEmpty())
        {
            ItemStack itemstack;

            if (itemStackArray[index].getCount() <= count)
            {
                itemstack = itemStackArray[index];
                itemStackArray[index] = ItemStack.EMPTY;
                this.markDirty();
                return itemstack;
            }
            else
            {
                itemstack = itemStackArray[index].splitStack(count);

                if (itemStackArray[index].isEmpty())
                {
                    itemStackArray[index] = ItemStack.EMPTY;
                }

                this.markDirty();
                return itemstack;
            }
        }
        else
        {
            return ItemStack.EMPTY;
        }
    }
	
	public ItemStack removeStackFromSlot(int index) {
		if (!itemStackArray[index].isEmpty())
		{
	        ItemStack itemstack = itemStackArray[index];
	        itemStackArray[index] = ItemStack.EMPTY;
	        return itemstack;
		}
		else
		{
			return ItemStack.EMPTY;
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
            	
                itemStackArray[b0] = new ItemStack(
                      nbtTagCompound);
            }
        }
        
        if (compound.hasKey("SlotFacing")) {
	        NBTTagCompound nbtTagCompound = compound.getCompoundTag("SlotFacing");
	        initSlotsFotFace(false);
	        
	        for (EnumFacing face : EnumFacing.values()) {
	        	int[] slots = nbtTagCompound.getIntArray(face.toString());
	        	
	        	for (int slot : slots) {
	        		addSlotToFace(slot, face);
	        	}
	        }
        } else {
        	initSlotsFotFace(true);
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < itemStackArray.length; ++i)
        {
            if (!itemStackArray[i].isEmpty())
            {
                NBTTagCompound nbtTagCompound = new NBTTagCompound();
                nbtTagCompound.setByte("Slot", (byte)i);
                itemStackArray[i].writeToNBT(nbtTagCompound);
                nbttaglist.appendTag(nbtTagCompound);
            }
        }

        compound.setTag("Items", nbttaglist);
        
        
        NBTTagCompound nbtTagCompound = new NBTTagCompound();
        
        for (EnumFacing face : EnumFacing.values()) {
            nbtTagCompound.setIntArray(face.toString(), getSlotsForFace(face));
        }
        
        compound.setTag("SlotFacing", nbtTagCompound);
        
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
    public boolean isUsableByPlayer(EntityPlayer playerIn)
    {
        return world.getTileEntity(pos) != this ? false : 
              playerIn.getDistanceSq(pos.getX()+0.5D, pos.getY()+0.5D, 
              pos.getZ()+0.5D) <= 64.0D;
    }
    
    @Override
    public void openInventory(EntityPlayer playerIn) {}

    @Override
    public void closeInventory(EntityPlayer playerIn) {}
    
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
            itemStackArray[i] = ItemStack.EMPTY;
        }
    }
    
    @Override
	public boolean hasCustomName() {
		return false;
	}
    
    public boolean hasItems() {
    	for (ItemStack stack : itemStackArray) {
    		if (!stack.isEmpty()) {
    			return true;
    		}
    	}
    	
    	return false;
    }
    
    @Override
    public int[] getSlotsForFace(EnumFacing side) {
		return slotsForFace.get(side.getIndex()).stream().mapToInt(new ToIntFunction<Integer>() {
			@Override
			public int applyAsInt(Integer i) {
				return i;
			}
		}).toArray();
    }
    
    public void addSlotToFace(Integer slot, EnumFacing face) {
    	slotsForFace.get(face.getIndex()).add(slot);
    	markDirty();
    }
    
    public void removeSlotFromFace(Integer slot, EnumFacing face) {
    	slotsForFace.get(face.getIndex()).remove(slot);
    	markDirty();
    }
    
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
    	NBTTagCompound nbttagcompound = this.writeToNBT(new NBTTagCompound());
        return new SPacketUpdateTileEntity(this.pos, getBlockMetadata(), nbttagcompound);
    }
    
    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
    	readFromNBT(pkt.getNbtCompound());
    }
    
    @Override
    public NBTTagCompound getUpdateTag() {
    	return writeToNBT(new NBTTagCompound());
    }
    
    @Override
    public boolean isEmpty() {
    	for (int i = 0; i < itemStackArray.length; ++i)
        {
            if (!itemStackArray[i].isEmpty())
            {
            	return false;
            }
        }
    	
    	return true;
    }
}
