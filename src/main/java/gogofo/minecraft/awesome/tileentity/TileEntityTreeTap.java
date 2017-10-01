package gogofo.minecraft.awesome.tileentity;

import gogofo.minecraft.awesome.block.BlockTreeTap;
import gogofo.minecraft.awesome.init.Blocks;
import gogofo.minecraft.awesome.interfaces.ILiquidContainer;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.Fluid;

import java.util.Random;

public class TileEntityTreeTap extends TileEntity implements ITickable {

    private static final int MIN_CONSUMABLE_SAP_LIMIT = 5000;
    private static final int MAX_CONSUMABLE_SAP_LIMIT = 10000;
    private static final int MIN_TICKS_FOR_CONSUME = 200;
    private static final int MAX_TICKS_FOR_CONSUME = 600;
    public static final int MIN_CONSUMPTION = Fluid.BUCKET_VOLUME / 5;
    public static final int MAX_CONSUMPTION = Fluid.BUCKET_VOLUME;
    private static final Random RANDOM = new Random();

    private int consumedSap;
    private int pendingSap;
    private int maxConsumableSap;
    private int nextConsume;

    public TileEntityTreeTap() {
        consumedSap = 0;
        pendingSap = 0;
        maxConsumableSap = MIN_CONSUMABLE_SAP_LIMIT + RANDOM.nextInt(MAX_CONSUMABLE_SAP_LIMIT - MIN_CONSUMABLE_SAP_LIMIT);
        setNextConsume();
    }

    @Override
    public void update() {
        if (world.isRemote) {
            return;
        }

        if (!validatePosition()) {
            world.destroyBlock(pos, true);
            return;
        }

        if (!canConsume()) {
            return;
        }

        nextConsume -= 1;
        if (nextConsume == 0) {
            setNextConsume();
            consumeSap();
        }

        if (consumedSap == maxConsumableSap) {
            replaceTreeWithDeadTree();
            return;
        }

        markDirty();
    }

    private boolean canConsume() {
        EnumFacing facing = (EnumFacing) world.getBlockState(pos).getProperties().get(BlockTreeTap.FACING);
        return world.getBlockState(pos.offset(facing.getOpposite())).getBlock() != Blocks.dead_wood;
    }

    private boolean validatePosition() {
        EnumFacing facing = (EnumFacing) world.getBlockState(pos).getProperties().get(BlockTreeTap.FACING);
        return ((BlockTreeTap)Blocks.tree_tap).isValidOnSide(world, pos, facing);
    }

    private void replaceTreeWithDeadTree() {
        EnumFacing facing = (EnumFacing) world.getBlockState(pos).getProperties().get(BlockTreeTap.FACING);
        world.setBlockState(pos.offset(facing.getOpposite()), Blocks.dead_wood.getDefaultState());
    }

    private void consumeSap() {
        int amount = MIN_CONSUMPTION + RANDOM.nextInt(MAX_CONSUMPTION - MIN_CONSUMPTION);
        consumedSap += amount;
        pendingSap += amount;

        BlockPos firstBlockBelowTap = getFirstBlockBelowTap();

        if (attemptToFillContainer(firstBlockBelowTap)) {
            return;
        }

        attemptToDropSap(firstBlockBelowTap);
    }

    private boolean attemptToFillContainer(BlockPos firstBlockBelowTap) {
        TileEntity te = world.getTileEntity(firstBlockBelowTap);

        if (te instanceof ILiquidContainer) {
            ILiquidContainer container = (ILiquidContainer) te;
            Block substance = container.getSubstance();

            if (substance == Blocks.sap || substance == net.minecraft.init.Blocks.AIR) {
                int sapPlaced = container.tryPlaceLiquid(Blocks.sap, pendingSap);

                if (sapPlaced > 0) {
                    pendingSap -= sapPlaced;
                    return true;
                }
            }
        }

        return false;
    }

    private boolean attemptToDropSap(BlockPos firstBlockBelowTap) {
        if (pendingSap < Fluid.BUCKET_VOLUME) {
            return false;
        }

        BlockPos airBlockPos = getFirstAirBetweenPosAndTap(firstBlockBelowTap);

        if (airBlockPos == null) {
            return false;
        }

        world.setBlockState(airBlockPos, Blocks.sap.getDefaultState());
        pendingSap -= Fluid.BUCKET_VOLUME;

        return true;
    }

    private BlockPos getFirstBlockBelowTap() {
        BlockPos pos = getPos().offset(EnumFacing.DOWN);

        while (world.getBlockState(pos).getBlock() == net.minecraft.init.Blocks.AIR) {
            pos = pos.offset(EnumFacing.DOWN);
        }

        return pos;
    }

    private BlockPos getFirstAirBetweenPosAndTap(BlockPos pos) {
        pos = pos.offset(EnumFacing.UP);

        while (world.getBlockState(pos).getBlock() != net.minecraft.init.Blocks.AIR &&
                world.getBlockState(pos).getBlock() != Blocks.tree_tap) {
            pos = pos.offset(EnumFacing.UP);
        }

        if (world.getBlockState(pos).getBlock() == net.minecraft.init.Blocks.AIR) {
            return pos;
        }

        return null;
    }

    private void setNextConsume() {
        nextConsume = MIN_TICKS_FOR_CONSUME + RANDOM.nextInt(MAX_TICKS_FOR_CONSUME - MIN_TICKS_FOR_CONSUME);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);

        compound.setInteger("consumed_sap", consumedSap);
        compound.setInteger("pending_sap", pendingSap);
        compound.setInteger("max_consumable_sap", maxConsumableSap);
        compound.setInteger("next_consume", nextConsume);

        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        consumedSap = compound.getInteger("consumed_sap");
        pendingSap = compound.getInteger("pending_sap");
        maxConsumableSap = compound.getInteger("max_consumable_sap");
        nextConsume = compound.getInteger("next_consume");
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound nbttagcompound = this.writeToNBT(new NBTTagCompound());
        return new SPacketUpdateTileEntity(this.pos, getBlockMetadata(), nbttagcompound);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        readFromNBT(pkt.getNbtCompound());
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        return writeToNBT(new NBTTagCompound());
    }
}
