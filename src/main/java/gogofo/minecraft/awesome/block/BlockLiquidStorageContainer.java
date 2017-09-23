package gogofo.minecraft.awesome.block;

import gogofo.minecraft.awesome.colorize.ISingleColoredObject;
import gogofo.minecraft.awesome.init.Fluids;
import gogofo.minecraft.awesome.tileentity.TileEntityLiquidStorageContainer;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.*;

import javax.annotation.Nullable;

public class BlockLiquidStorageContainer extends Block implements ITileEntityProvider, ISingleColoredObject {

    public BlockLiquidStorageContainer() {
        super(Material.IRON);
        setHardness(1.0f);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityLiquidStorageContainer();
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
        return 0x000000;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        TileEntityLiquidStorageContainer te = (TileEntityLiquidStorageContainer) worldIn.getTileEntity(pos);
        if (te == null) {
            return false;
        }

        ItemStack heldStack = playerIn.getHeldItem(hand);
        Item heldItem = heldStack.getItem();

        if (heldItem instanceof ItemBucket || heldItem instanceof UniversalBucket) {
            return onBlockRightClickedWithBucket(playerIn, hand, te, heldStack, heldItem);
        }

        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
    }

    private boolean onBlockRightClickedWithBucket(EntityPlayer playerIn, EnumHand hand, TileEntityLiquidStorageContainer te, ItemStack heldStack, Item heldItem) {
        if (heldItem == Items.BUCKET) {
            Block substance = te.getSubstance();
            int liquidTaken = te.tryTakeLiquid(substance, 1);
            if (liquidTaken > 0) {
                Fluid fluid = FluidRegistry.lookupFluidForBlock(substance);
                playerIn.setHeldItem(hand, FluidUtil.getFilledBucket(new FluidStack(fluid, Fluid.BUCKET_VOLUME)));
            }

            return true;
        } else {
            FluidStack fluidStack = FluidUtil.getFluidContained(heldStack);
            int placedLiquid = te.tryPlaceLiquid(fluidStack.getFluid().getBlock(), 1);
            if (placedLiquid > 0) {
                playerIn.setHeldItem(hand, new ItemStack(Items.BUCKET));
            }

            return true;
        }
    }
}
