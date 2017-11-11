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
	
	private static final int[] up_slots = {PIPE_SLOT_COUNT, PIPE_SLOT_COUNT + 1, PIPE_SLOT_COUNT + 2, PIPE_SLOT_COUNT + 3, PIPE_SLOT_COUNT + 4, PIPE_SLOT_COUNT + 5, PIPE_SLOT_COUNT + 6, PIPE_SLOT_COUNT + 7, PIPE_SLOT_COUNT + 8};
	private static final int[] down_slots = {PIPE_SLOT_COUNT + 9, PIPE_SLOT_COUNT + 10, PIPE_SLOT_COUNT + 11, PIPE_SLOT_COUNT + 12, PIPE_SLOT_COUNT + 13, PIPE_SLOT_COUNT + 14, PIPE_SLOT_COUNT + 15, PIPE_SLOT_COUNT + 16, PIPE_SLOT_COUNT + 17};
	private static final int[] north_slots = {PIPE_SLOT_COUNT + 18, PIPE_SLOT_COUNT + 19, PIPE_SLOT_COUNT + 20, PIPE_SLOT_COUNT + 21, PIPE_SLOT_COUNT + 22, PIPE_SLOT_COUNT + 23, PIPE_SLOT_COUNT + 24, PIPE_SLOT_COUNT + 25, PIPE_SLOT_COUNT + 26};
	private static final int[] south_slots = {PIPE_SLOT_COUNT + 27, PIPE_SLOT_COUNT + 28, PIPE_SLOT_COUNT + 29, PIPE_SLOT_COUNT + 30, PIPE_SLOT_COUNT + 31, PIPE_SLOT_COUNT + 32, PIPE_SLOT_COUNT + 33, PIPE_SLOT_COUNT + 34, PIPE_SLOT_COUNT + 35};
	private static final int[] east_slots = {PIPE_SLOT_COUNT + 36, PIPE_SLOT_COUNT + 37, PIPE_SLOT_COUNT + 38, PIPE_SLOT_COUNT + 39, PIPE_SLOT_COUNT + 40, PIPE_SLOT_COUNT + 40, PIPE_SLOT_COUNT + 42, PIPE_SLOT_COUNT + 43, PIPE_SLOT_COUNT + 44};
	private static final int[] west_slots = {PIPE_SLOT_COUNT + 45, PIPE_SLOT_COUNT + 46, PIPE_SLOT_COUNT + 47, PIPE_SLOT_COUNT + 48, PIPE_SLOT_COUNT + 49, PIPE_SLOT_COUNT + 50, PIPE_SLOT_COUNT + 51, PIPE_SLOT_COUNT + 52, PIPE_SLOT_COUNT + 53};
	
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
		return super.getSlotCount() + 54;
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
			if (!sortStack.isEmpty() && sortStack.isItemEqualIgnoreDurability(stack) && compareEntityTag(sortStack, stack)) {
				canTransfer = true;
				break;
			}
		}
		
		return canTransfer && super.canTransferTo(stack, facing, true);
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
    	for (int i = 0; i < super.getSlotCount(); i++) {
    		ItemStack stack = itemStackArray[i];
    		if (!stack.isEmpty() && stack.getCount() > 0) {
    			return true;
    		}
    	}
    	
    	return false;
    }
}
