package gogofo.minecraft.awesome.tileentity;

import gogofo.minecraft.awesome.interfaces.IConfigurableSidedInventory;
import gogofo.minecraft.awesome.utils.InventoryUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class AwesomeTileEntityContainer extends TileEntityLockable implements IConfigurableSidedInventory {
	protected ItemStack[] itemStackArray;
	private ArrayList<ArrayList<Integer>> slotsForFace;

	protected abstract int getSlotCount();
	public abstract int getCustomSlotCount();
	
	public AwesomeTileEntityContainer() {
		itemStackArray = new ItemStack[getSlotCount()];
		Arrays.fill(itemStackArray, ItemStack.EMPTY);
        slotsForFace = new ArrayList<>();

        InventoryUtils.initSlotsForFace(slotsForFace, this, true);
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
	    InventoryUtils.setInventorySlotForContents(itemStackArray, index, stack, this);
	}
	
	@Override
    public ItemStack decrStackSize(int index, int count) {
	    return InventoryUtils.decrStackSize(itemStackArray, index, count, this);
    }
	
	public ItemStack removeStackFromSlot(int index) {
	    return InventoryUtils.removeStackFromSlot(itemStackArray, index);
	}
    
    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        InventoryUtils.readFromNBT(itemStackArray, slotsForFace, this, compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        return InventoryUtils.writeToNBT(itemStackArray, this, compound);
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
        return world.getTileEntity(pos) == this && playerIn.getDistanceSq(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) <= 64.0D;
    }
    
    @Override
    public void openInventory(EntityPlayer playerIn) {}

    @Override
    public void closeInventory(EntityPlayer playerIn) {}
    
    @Override
    public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction)
    {
        return isItemValidForSlot(index, itemStackIn);
    }

    @Override
    public boolean canExtractItem(int parSlotIndex, ItemStack parStack, EnumFacing parFacing)
    {
        return true;
    }
    
    @Override
    public void clear()
    {
        InventoryUtils.clear(itemStackArray);
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
		return slotsForFace.get(side.getIndex()).stream().mapToInt(i -> i).toArray();
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
        return InventoryUtils.isEmpty(itemStackArray);
    }

    @Override
    public BlockPos getInventoryPos() {
        return getPos();
    }
}
