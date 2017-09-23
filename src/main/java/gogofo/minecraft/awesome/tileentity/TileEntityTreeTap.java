package gogofo.minecraft.awesome.tileentity;

import gogofo.minecraft.awesome.block.BlockTreeTap;
import gogofo.minecraft.awesome.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;

import java.util.Random;

public class TileEntityTreeTap extends TileEntity implements ITickable {

    private static final int MIN_CONSUMABLE_SAP_LIMIT = 5;
    private static final int MAX_CONSUMABLE_SAP_LIMIT = 10;
    private static final int MIN_TICKS_FOR_CONSUME = 300;
    private static final int MAX_TICKS_FOR_CONSUME = 1000;
    private static final Random RANDOM = new Random();

    private int consumedSap;
    private int maxConsumableSap;
    private int nextConsume;

    public TileEntityTreeTap() {
        consumedSap = 0;
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

        if (consumedSap == maxConsumableSap) {
            return;
        }

        nextConsume -= 1;
        if (nextConsume == 0) {
            setNextConsume();
            consumeSap();
        }

        markDirty();
    }

    private boolean validatePosition() {
        EnumFacing facing = (EnumFacing) world.getBlockState(pos).getProperties().get(BlockTreeTap.FACING);
        return ((BlockTreeTap)Blocks.tree_tap).isValidOnSide(world, pos, facing);
    }

    private void consumeSap() {
        consumedSap += 1;
    }

    private void setNextConsume() {
        nextConsume = MIN_TICKS_FOR_CONSUME + RANDOM.nextInt(MAX_TICKS_FOR_CONSUME - MIN_TICKS_FOR_CONSUME);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);

        compound.setInteger("consumed_sap", consumedSap);
        compound.setInteger("max_consumable_sap", maxConsumableSap);
        compound.setInteger("next_consume", nextConsume);

        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        consumedSap =  compound.getInteger("consumed_sap");
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
