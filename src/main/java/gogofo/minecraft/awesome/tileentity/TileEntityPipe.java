package gogofo.minecraft.awesome.tileentity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import gogofo.minecraft.awesome.block.BlockPipe;
import gogofo.minecraft.awesome.block.BlockSuctionPipe;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;

public class TileEntityPipe extends AwesomeTileEntityContainer implements ITickable {
	public static final int TRANSFER_COOLDOWN = 8;

	public final static int IS_TRANSPARENT_IDX = 0;
	
	private static final BlockPipe refBlockPipe = new BlockPipe();

	private boolean isTransparent = false;

	@Override
	public void update() {
		if (world.isRemote) {
    		return;
    	}
		
		for (int i = 0; i < getTransferSlotCount(); i++) {
			ItemStack stack = super.getStackInSlot(i);
			if (!stack.isEmpty()) {
				decStackCooldown(stack);
				
				if (getStackCooldown(stack) <= 0) {
					transferStack(stack, i);
				}
			}
		}
	}
	
	private void transferStack(ItemStack stack, int index) {
		BlockPipe refBlock = getRefPipeBlock();
		
		EnumFacing[] randomFacing = EnumFacing.VALUES.clone();
		Collections.shuffle(Arrays.asList(randomFacing));
		
		ArrayList<EnumFacing> checkOrder = new ArrayList<EnumFacing>();
		
		int nonPipeIndex = 0;
		for (EnumFacing facing : randomFacing) {
			BlockPos dest = getPos().offset(facing);
			
			if (world.getBlockState(dest).getBlock() instanceof BlockPipe) {
				checkOrder.add(facing);
			} else {
				checkOrder.add(nonPipeIndex, facing);
				nonPipeIndex += 1;
			}
		}
		
		for (EnumFacing facing : checkOrder) {
			BlockPos dest = getPos().offset(facing);
			
			if (!canTransferTo(stack, facing)) {
				continue;
			}
			
			if (tryToTransfer(refBlock, stack, index, dest)) {
				return;
			}
		}
		
		for (BlockPos secondaryDest : getSecondaryDestsWithoutChecks(stack)) {
			if (tryToTransfer(refBlock, stack, index, secondaryDest)) {
				return;
			}
		}
	}
	
	protected boolean canTransferTo(ItemStack stack, EnumFacing facing) {
		return canTransferTo(stack, facing, false);
	}
	
	protected boolean canTransferTo(ItemStack stack, EnumFacing facing, boolean allowOrigin) {
		BlockPos origin = getStackOrigin(stack);
		BlockPos dest = getPos().offset(facing);
		
		if (world.getBlockState(dest).getBlock() instanceof BlockSuctionPipe) {
			return false;
		}
		
		if (!allowOrigin && origin.equals(dest)) {
			return false;
		}
		
		return true;
	}
	
	protected ArrayList<BlockPos> getSecondaryDestsWithoutChecks(ItemStack stack) {
		ArrayList<BlockPos> dests = new ArrayList<BlockPos>();
		
		BlockPos origin = getStackOrigin(stack);
		
		if (!(world.getBlockState(origin).getBlock() instanceof BlockSuctionPipe)) {
			dests.add(getStackOrigin(stack));
		}
		
		return dests;
	}
	
	protected int[] getSlotIndexesForInventoryFacing(IInventory inventory, EnumFacing facing) {
		int slots[];
		
		if (inventory instanceof ISidedInventory) {
			ISidedInventory sidedInventory = (ISidedInventory)inventory;
			slots = sidedInventory.getSlotsForFace(facing.getOpposite());
		} else {
			slots = new int[inventory.getSizeInventory()];
			for (int i = 0; i < inventory.getSizeInventory(); i++) {
				slots[i] = i;
			}
		}
		
		return slots;
	}
	
	protected EnumFacing facingForCloseBlock(BlockPos pos) {
		if (getPos().up().equals(pos)) {
			return EnumFacing.UP;
		} else if (getPos().down().equals(pos)) {
			return EnumFacing.DOWN;
		} else if (getPos().north().equals(pos)) {
			return EnumFacing.NORTH;
		} else if (getPos().south().equals(pos)) {
			return EnumFacing.SOUTH;
		} else if (getPos().east().equals(pos)) {
			return EnumFacing.EAST;
		} else if (getPos().west().equals(pos)) {
			return EnumFacing.WEST;
		} else {
			return null;
		}
	}
	
	private boolean tryToTransfer(BlockPipe refPipeBlock, ItemStack sentStack, int sentSlot, BlockPos pos) {
		EnumFacing facing = facingForCloseBlock(pos);
		if (facing == null) {
			return false;
		}
		
		if (!refPipeBlock.canConnectTo(world, pos)) {
			return false;
		}
		
		IInventory inventory = getInventoryAt(pos);
		if (inventory == null) {
			return false;
		}
		
		for (int i : getSlotIndexesForInventoryFacing(inventory, facing)) {
			if (!inventory.isItemValidForSlot(i, sentStack)) {
				continue;
			}
			
			ItemStack stack = inventory.getStackInSlot(i);
			if (stack.isEmpty()) {
				inventory.setInventorySlotContents(i, 
												   createTransfferedItem(sentStack, 
														   				 getPos(),
														   				 world.getBlockState(pos).getBlock() instanceof BlockPipe));
				decrStackSize(sentSlot, 1);
				markDirty();
				notifyUpdate(getPos());
				notifyUpdate(pos);
				
				return true;
			} else if (stack.getItem() == sentStack.getItem() && 
					   stack.isItemEqual(sentStack) &&
					   stack.getMetadata() == sentStack.getMetadata() &&
					   stack.getCount() < inventory.getInventoryStackLimit() &&
					   stack.getCount() < stack.getMaxStackSize()) {
				stack.grow(1);
				inventory.setInventorySlotContents(i, stack);
				decrStackSize(sentSlot, 1);
				markDirty();
				notifyUpdate(getPos());
				notifyUpdate(pos);

				return true;
			}
		}
		
		return false;
	}

	private void decStackCooldown(ItemStack stack) {
		int cooldown = getStackCooldown(stack) - 1;
		
		setStackCooldown(stack, cooldown);
	}
	
	private NBTTagCompound getTagCompound(ItemStack stack) {
		if (stack.getTagCompound() == null) {
			stack.setTagCompound(new NBTTagCompound());
		}
		
		if (stack.getTagCompound().getTag("pipe") == null) {
			stack.getTagCompound().setTag("pipe", new NBTTagCompound());
		}
		
		return ((NBTTagCompound)stack.getTagCompound().getTag("pipe"));
	}
	
	private void clearTagCompound(ItemStack stack) {
		if (stack.getTagCompound() == null) {
			return;
		}
		
		stack.getTagCompound().removeTag("pipe");
	}
	
	private int getStackCooldown(ItemStack stack) {
		return getTagCompound(stack).getInteger("transferCooldown");
	}
	
	private void setStackCooldown(ItemStack stack, int cooldown) {
		getTagCompound(stack).setInteger("transferCooldown", cooldown);
	}
	
	private BlockPos getStackOrigin(ItemStack stack) {
		return new BlockPos(getTagCompound(stack).getInteger("origin-x"),
							getTagCompound(stack).getInteger("origin-y"),
							getTagCompound(stack).getInteger("origin-z"));
	}
	
	private void setStackOrigin(ItemStack stack, BlockPos origin) {
		getTagCompound(stack).setInteger("origin-x", origin.getX());
		getTagCompound(stack).setInteger("origin-y", origin.getY());
		getTagCompound(stack).setInteger("origin-z", origin.getZ());
	}
	
	private void setOiginalTagCompoundIfNotSet(ItemStack stack, ItemStack original) {
		if (!getTagCompound(stack).getBoolean("has-original-tag")) {
			getTagCompound(stack).setBoolean("has-original-tag", true);
			
			NBTTagCompound tag = original.getTagCompound();
			if (tag != null) {
				getTagCompound(stack).setTag("original-tag", tag);
			}
		} 
	}
	
	private NBTTagCompound getOiginalTagCompound(ItemStack stack) {
		return (NBTTagCompound)getTagCompound(stack).getTag("original-tag");
	}
	
	protected int getEmptySlotIndex() {
		for (int i = 0; i < getTransferSlotCount(); i++) {
			if (super.getStackInSlot(i).isEmpty()) {
				return i;
			}
		}
		
		return -1;
	}

	public int getTransferSlotCount() {
		return 27;
	}
	
	protected IInventory getInventoryAt(BlockPos pos) {
		TileEntity te = world.getTileEntity(pos);
		
		if (!(te instanceof IInventory)) {
			return null;
		}
		
		return (IInventory)te;
	}
	
	protected ItemStack createTransfferedItem(ItemStack original, BlockPos origin, boolean isForPipeBlock) {	
		ItemStack transferredItem = new ItemStack(original.getItem(), 1, original.getMetadata());
		 if (original.getTagCompound() != null) {
            transferredItem.setTagCompound((NBTTagCompound)original.getTagCompound().copy());
        }
		 
		setOiginalTagCompoundIfNotSet(transferredItem, original);
		 
		if (isForPipeBlock) {
			setStackCooldown(transferredItem, TRANSFER_COOLDOWN);
			setStackOrigin(transferredItem, origin);
		} else {
			NBTTagCompound tag = getOiginalTagCompound(transferredItem);
			if (tag != null) {
				transferredItem.setTagCompound((NBTTagCompound)tag.copy());
			} else {
				transferredItem.setTagCompound(null);
			}
		}
		
		return transferredItem;
	}
	
	protected BlockPipe getRefPipeBlock() {
		return refBlockPipe;
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return true;
	}

	@Override
	public int getField(int id) {
		switch (id) {
			case IS_TRANSPARENT_IDX:
				return isTransparent ? 1 : 0;
		}

		return 0;
	}

	@Override
	public void setField(int id, int value) {
		switch (id) {
			case IS_TRANSPARENT_IDX:
				isTransparent = value == 1;
		}
	}

	@Override
	public int getFieldCount() {
		return 1;
	}

	@Override
	public String getName() {
		return "Pipe";
	}

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
		return new ContainerChest(playerInventory, this, playerIn);
	}

	@Override
	public String getGuiID() {
		return "minecraft:chest";
	}

	protected Integer[] getSlotsGeneric(int count) {
		Integer [] slots = new Integer[count];
		for (int i = 0; i < count; i++) {
			slots[i] = i;
		}
		return slots;
	}
	
	@Override
	protected Integer[] getDefaultSlotForFace(EnumFacing face) {
		switch (face) {
		case UP:
			return getSlotsGeneric(27);
		case DOWN:
			return getSlotsGeneric(27);
		case NORTH:
			return getSlotsGeneric(27);
		case SOUTH:
			return getSlotsGeneric(27);
		case EAST:
			return getSlotsGeneric(27);
		case WEST:
			return getSlotsGeneric(27);
		default:
			return new Integer[] {};
		}
	}

	@Override
	protected int getSlotCount() {
		return 27;
	}
	
	@Override
	public int getCustomSlotCount() {
		return 0;
	}
	
	@Override
    public int getInventoryStackLimit()
    {
        return 1;
    }
	
	@Override
    public ItemStack getStackInSlot(int index)
    {
		if (itemStackArray[index].isEmpty()) {
			return ItemStack.EMPTY;
		}
		
		ItemStack stack = new ItemStack(itemStackArray[index].getItem(), 
										itemStackArray[index].getCount(), 
										itemStackArray[index].getMetadata());
        return stack;
    }

	public boolean isTransparent() {
		return isTransparent;
	}

	public void setTransparent(boolean isTransparent) {
		this.isTransparent = isTransparent;
		BlockPipe.setState(isTransparent, world, pos);
		markDirty();
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);

		isTransparent = compound.getBoolean("isTransparent");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setBoolean("isTransparent", isTransparent);

		return compound;
	}

	protected void notifyUpdate(BlockPos blockPos) {
		IBlockState state = world.getBlockState(blockPos);
		world.notifyBlockUpdate(blockPos, state, state, 3);
	}
}
