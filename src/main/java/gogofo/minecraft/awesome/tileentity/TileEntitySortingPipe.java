package gogofo.minecraft.awesome.tileentity;

import java.util.ArrayList;

import gogofo.minecraft.awesome.block.BlockPipe;
import gogofo.minecraft.awesome.block.BlockSortingPipe;
import gogofo.minecraft.awesome.block.BlockSuctionPipe;
import gogofo.minecraft.awesome.gui.GuiEnum;
import gogofo.minecraft.awesome.gui.GuiSortingPipe;
import gogofo.minecraft.awesome.inventory.ContainerSortingPipe;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class TileEntitySortingPipe extends TileEntityPipe {
	
	private static final int[] up_slots = {27, 28, 29, 30, 31, 32, 33, 34, 35};
	private static final int[] down_slots = {36, 37, 38, 39, 40, 41, 42, 43, 44};
	private static final int[] north_slots = {45, 46, 47, 48, 49, 50, 51, 52, 53};
	private static final int[] south_slots = {54, 55, 56, 57, 58, 59, 60, 61, 62};
	private static final int[] east_slots = {63,64, 65, 66, 67, 68, 69, 70, 71};
	private static final int[] west_slots = {72, 73, 74, 75, 76, 77, 78, 79, 80};
	
	@Override
	protected BlockPipe getTmpPipeBlock() {
		return new BlockSortingPipe();
	}
	
	@Override
	public String getGuiID() {
		return GuiEnum.SORTING_PIPE.guiName();
	}
	
	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
		return new ContainerSortingPipe(playerInventory, this);
	}
	
	@Override
	protected int getSlotCount() {
		return 27 + 54;
	}
	
	@Override
	public String getName() {
		return "Sorting Pipe";
	}
	
	@Override
	protected boolean canTransferTo(ItemStack stack, EnumFacing facing) {
		boolean canTransfer = false;
		
		int[] slots = getTrasferSlotsForFace(facing);
		
		for (int i : slots) {
			ItemStack sortStack = getStackInSlot(i);
			if (sortStack != null && sortStack.getItem().equals(stack.getItem())) {
				canTransfer = true;
				break;
			}
		}
		
		return canTransfer && super.canTransferTo(stack, facing);
	}
	
	@Override
	protected ArrayList<BlockPos> getSecondaryDestsWithoutChecks(ItemStack stack) {
		ArrayList<BlockPos> dests = super.getSecondaryDestsWithoutChecks(stack);
		
		for (EnumFacing facing : EnumFacing.VALUES) {
			if (super.canTransferTo(stack, facing) && 
					canTransferAll(getTrasferSlotsForFace(facing))) {
				dests.add(0, getPos().offset(facing));
			}
		}
		
		return dests;
	}
	
	private boolean canTransferAll(int [] slots) {
		ItemStack stack1 = getStackInSlot(slots[0]);
		ItemStack stack2 = getStackInSlot(slots[1]);
		
		return stack1 != null && stack2 != null && stack1.getItem().equals(stack2.getItem());
	}
	
	private int[] getTrasferSlotsForFace(EnumFacing face) {
		if (face == EnumFacing.UP) {
			return up_slots;
		}
		
		if (face == EnumFacing.DOWN) {
			return down_slots;
		}
		
		if (face == EnumFacing.NORTH) {
			return north_slots;
		}
		
		if (face == EnumFacing.SOUTH) {
			return south_slots;
		}
		
		if (face == EnumFacing.EAST) {
			return east_slots;
		}
		
		if (face == EnumFacing.WEST) {
			return west_slots;
		}
		
		return null;
	}
}
