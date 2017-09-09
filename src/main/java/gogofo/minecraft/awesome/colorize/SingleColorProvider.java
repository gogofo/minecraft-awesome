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
public class SingleColorProvider implements IBlockColor, IItemColor {

    private final ISingleColoredObject singleColoredObject;

    public SingleColorProvider(ISingleColoredObject singleColoredObject) {
        this.singleColoredObject = singleColoredObject;
    }

    @Override
    public int colorMultiplier(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex) {
        return singleColoredObject.getColor();
    }

    @Override
    public int getColorFromItemstack(ItemStack stack, int tintIndex) {
        return singleColoredObject.getColor();
    }
}
