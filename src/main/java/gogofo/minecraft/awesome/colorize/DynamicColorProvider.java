package gogofo.minecraft.awesome.colorize;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

@SideOnly(Side.CLIENT)
public class DynamicColorProvider implements IBlockColor, IItemColor {

    private final IDynamicColoredObjected dynamicColoredObjected;

    public DynamicColorProvider(IDynamicColoredObjected dynamicColoredObjected) {
        this.dynamicColoredObjected = dynamicColoredObjected;
    }

    @Override
    public int colorMultiplier(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex) {
        return dynamicColoredObjected.getColorForBlock(state, worldIn, pos, tintIndex);
    }

    @Override
    public int getColorFromItemstack(ItemStack stack, int tintIndex) {
        return dynamicColoredObjected.getColorForItem(stack, tintIndex);
    }
}
