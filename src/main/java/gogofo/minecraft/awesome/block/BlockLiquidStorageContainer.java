package gogofo.minecraft.awesome.block;

import gogofo.minecraft.awesome.colorize.ISingleColoredObject;
import gogofo.minecraft.awesome.init.Ores;
import gogofo.minecraft.awesome.tileentity.TileEntityLiquidStorageContainer;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.*;

import javax.annotation.Nullable;
import java.util.List;

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
        return Ores.bronze.getColor();
    }

    //<editor-fold desc="'Hacks' to make getDrops still have a TileEntity">
    @Override
    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest)
    {
        if (willHarvest) return true; //If it will harvest, delay deletion of the block until after getDrops
        return super.removedByPlayer(state, world, pos, player, false);
    }

    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack tool)
    {
        super.harvestBlock(world, player, pos, state, te, tool);
        world.setBlockToAir(pos);
    }
    //</editor-fold>

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        TileEntity tileEntity = world.getTileEntity(pos);
        TileEntityLiquidStorageContainer te = (TileEntityLiquidStorageContainer) tileEntity;
        if (te == null) {
            return;
        }

        ItemStack itemStack = new ItemStack(this);
        NBTTagCompound nbttagcompound = te.writeToNBT(new NBTTagCompound());

        if (!nbttagcompound.hasNoTags())
        {
            // BlockEntityTag is a special tag that transfers to the later created TileEntity
            itemStack.setTagInfo("BlockEntityTag", nbttagcompound);

            if (te.getContainedSubstance() != Blocks.AIR) {
                itemStack.setStackDisplayName(getLocalizedName() + " (" + te.getContainedSubstance().getLocalizedName() + ")");
            }
        }
        drops.add(itemStack);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced) {
        Block liquid = TileEntityLiquidStorageContainer.getLiquidFromStack(stack);
        if (liquid != Blocks.AIR) {
            tooltip.add(String.format("Liquid Type: %s", liquid.getLocalizedName()));
            tooltip.add(String.format("Amount: %d", TileEntityLiquidStorageContainer.getAmountFromStack(stack)));
        }
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
            int liquidTaken = te.tryTakeLiquid(substance, Fluid.BUCKET_VOLUME);
            if (liquidTaken == Fluid.BUCKET_VOLUME) {
                heldStack.shrink(1);

                Fluid fluid = FluidRegistry.lookupFluidForBlock(substance);
                ItemStack filledBucket = FluidUtil.getFilledBucket(new FluidStack(fluid, Fluid.BUCKET_VOLUME));

                if (heldStack.isEmpty())
                {
                    playerIn.setHeldItem(hand, filledBucket);
                }
                else if (!playerIn.inventory.addItemStackToInventory(filledBucket))
                {
                    playerIn.dropItem(filledBucket, false);
                }
            } else {
                te.tryPlaceLiquid(substance, liquidTaken);
            }

            return true;
        } else {
            FluidStack fluidStack = FluidUtil.getFluidContained(heldStack);
            int placedLiquid = te.tryPlaceLiquid(fluidStack.getFluid().getBlock(), Fluid.BUCKET_VOLUME);
            if (placedLiquid > 0) {
                playerIn.setHeldItem(hand, new ItemStack(Items.BUCKET));
            }

            return true;
        }
    }
}
