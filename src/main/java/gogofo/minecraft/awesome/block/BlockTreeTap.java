package gogofo.minecraft.awesome.block;

import gogofo.minecraft.awesome.colorize.ISingleColoredObject;
import gogofo.minecraft.awesome.init.Ores;
import gogofo.minecraft.awesome.tileentity.TileEntityLiquidStorageContainer;
import gogofo.minecraft.awesome.tileentity.TileEntityTreeTap;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class BlockTreeTap extends Block implements ITileEntityProvider, ISingleColoredObject {
    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

    public BlockTreeTap() {
        super(Material.IRON);
        setHardness(1.0f);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityTreeTap();
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public int getColor() {
        Ores.Ore copper = Ores.getByName("copper");
        return copper != null ? copper.getColor() : 0x000000;
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY,
                                            float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        return this.getDefaultState().withProperty(FACING, facing);
    }

    @Override
    public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side) {
        if (!super.canPlaceBlockOnSide(worldIn, pos, side)) {
            return false;
        }

        return isValidOnSide(worldIn, pos, side);

    }

    public boolean isValidOnSide(World worldIn, BlockPos pos, EnumFacing side) {
        if (side == EnumFacing.UP || side == EnumFacing.DOWN) {
            return false;
        }

        BlockPos woodPos = pos.offset(side.getOpposite());
        Block woodBlock = worldIn.getBlockState(woodPos).getBlock();
        boolean isOnWood = woodBlock == Blocks.LOG ||
                woodBlock == Blocks.LOG2 ||
                woodBlock == gogofo.minecraft.awesome.init.Blocks.dead_wood;

        if (!isOnWood) {
            return false;
        }

        for (EnumFacing facing : EnumFacing.HORIZONTALS) {
            if (facing != side &&
                    worldIn.getBlockState(woodPos.offset(facing)).getBlock() == this) {
                return false;
            }
        }

        return true;

    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        EnumFacing enumfacing = EnumFacing.getFront(meta & 0x7);

        return this.getDefaultState().withProperty(FACING, enumfacing);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(FACING).getIndex();
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING);
    }
}
