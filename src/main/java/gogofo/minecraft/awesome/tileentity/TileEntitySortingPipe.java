package gogofo.minecraft.awesome.tileentity;

import java.util.ArrayList;
import java.util.Collections;

import gogofo.minecraft.awesome.block.BlockPipe;
import gogofo.minecraft.awesome.block.BlockSortingPipe;
import gogofo.minecraft.awesome.gui.GuiEnum;
import gogofo.minecraft.awesome.inventory.ContainerSortingPipe;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumFacing;

public class TileEntitySortingPipe extends TileEntityPipe {
	
	private static final BlockPipe refBlockPipe = new BlockSortingPipe();
	
	private static final int[] up_slots = {27, 28, 29, 30, 31, 32, 33, 34, 35};
	private static final int[] down_slots = {36, 37, 38, 39, 40, 41, 42, 43, 44};
	private static final int[] north_slots = {45, 46, 47, 48, 49, 50, 51, 52, 53};
	private static final int[] south_slots = {54, 55, 56, 57, 58, 59, 60, 61, 62};
	private static final int[] east_slots = {63,64, 65, 66, 67, 68, 69, 70, 71};
	private static final int[] west_slots = {72, 73, 74, 75, 76, 77, 78, 79, 80};
	
	@Override
	protected BlockPipe getRefPipeBlock() {
		return refBlockPipe;
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
	protected boolean canTransferTo(ItemStack stack, EnumFacing facing, boolean allowOrigin) {
		boolean canTransfer = false;
		
		int[] slots = getTrasferSlotsForFace(facing);
		
		for (int i : slots) {
			ItemStack sortStack = getStackInSlot(i);
			if (!sortStack.isEmpty() && sortStack.getItem().equals(stack.getItem())) {
				canTransfer = true;
				break;
			}
		}
		
		return canTransfer && super.canTransferTo(stack, facing, allowOrigin);
	}
	
	@Override
	protected ArrayList<BlockPos> getSecondaryDestsWithoutChecks(ItemStack stack, boolean attemptedPrimaryTransfer) {
		ArrayList<BlockPos> baseSecondaryDests = super.getSecondaryDestsWithoutChecks(stack, attemptedPrimaryTransfer);

		// Do not go to all routes if there is a primary one we want to go to
		if (attemptedPrimaryTransfer) {
			return baseSecondaryDests;
		}

		ArrayList<BlockPos> dests = new ArrayList<>();
		
		for (EnumFacing facing : EnumFacing.VALUES) {
			if (super.canTransferTo(stack, facing, false) && 
					canTransferAll(getTrasferSlotsForFace(facing))) {
				dests.add(0, getPos().offset(facing));
			}
		}
		
		// Make sure the dests change if there are few with the same priority
		Collections.shuffle(dests);
		
		ArrayList<BlockPos> priorityDest = new ArrayList<>();
		
		for (BlockPos pos : dests) {
			if (canTransferTo(stack, facingForCloseBlock(pos), true)) {
				priorityDest.add(pos);
			}
		}
		
		dests.removeAll(priorityDest);
		dests.addAll(0, priorityDest);
		dests.addAll(baseSecondaryDests);
		
		return dests;
	}
	
	private boolean canTransferAll(int [] slots) {
		ItemStack stack1 = getStackInSlot(slots[0]);
		ItemStack stack2 = getStackInSlot(slots[1]);
		
		return !stack1.isEmpty() && !stack2.isEmpty() && stack1.getItem().equals(stack2.getItem());
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
	
	public boolean hasItems() {
    	for (int i = 0; i < 27; i++) {
    		ItemStack stack = itemStackArray[i];
    		if (stack.isEmpty() && stack.getCount() > 0) {
    			return true;
    		}
    	}
    	
    	return false;
    }
}
