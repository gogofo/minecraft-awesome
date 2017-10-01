package gogofo.minecraft.awesome.interfaces;

import net.minecraft.inventory.ISidedInventory;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public interface IPositionedSidedInventory extends ISidedInventory {
    Integer[] getDefaultSlotForFace(EnumFacing face);
    BlockPos getInventoryPos();
}
