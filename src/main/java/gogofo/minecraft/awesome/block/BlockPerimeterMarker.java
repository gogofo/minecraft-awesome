package gogofo.minecraft.awesome.block;

import gogofo.minecraft.awesome.init.Blocks;
import gogofo.minecraft.awesome.tileentity.TileEntityPerimeterMarker;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class BlockPerimeterMarker extends Block implements ITileEntityProvider {

    public BlockPerimeterMarker() {
        super(Material.CIRCUITS);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityPerimeterMarker();
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(Blocks.perimeter_marker);
    }

    @Override
    public boolean isOpaqueCube(IBlockState blockState) {
        return false;
    }

    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

    @Override
    public boolean isFullCube(IBlockState blockState) {
        return false;
    }

    @Override
    public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side) {
        if (!super.canPlaceBlockOnSide(worldIn, pos, side)) {
            return false;
        }

        return side == EnumFacing.UP;
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        if (pos.offset(EnumFacing.DOWN).equals(fromPos) && worldIn.getBlockState(fromPos).getBlock().isReplaceable(worldIn, fromPos)) {
                dropBlockAsItem(worldIn, pos, state, 0);
                worldIn.setBlockToAir(pos);
        }
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return new AxisAlignedBB(6f/16f, 0.0D, 6f/16f, 10f/16f, 12f/16f, 10f/16f);
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        TileEntityPerimeterMarker te = (TileEntityPerimeterMarker) worldIn.getTileEntity(pos);

        if (te != null) {
            te.handleBreak();
        }

        super.breakBlock(worldIn, pos, state);
    }
}
