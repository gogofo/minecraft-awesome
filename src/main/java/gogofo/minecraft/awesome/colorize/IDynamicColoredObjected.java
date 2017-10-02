package gogofo.minecraft.awesome.colorize;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public interface IDynamicColoredObjected {
    int getColorForBlock(IBlockState state, IBlockAccess worldIn, BlockPos pos, int tintIndex);
    int getColorForItem(ItemStack stack, int tintIndex);
}
