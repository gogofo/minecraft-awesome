package gogofo.minecraft.awesome.tileentity;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityLiquidStorageContainer extends TileEntity {

    public static final int MAX_AMOUNT = 15;

    private Block containedSubstance = Blocks.AIR;
    private int containedAmount = 0;

    @Override
    public boolean hasFastRenderer() {
        return true;
    }

    public Block getContainedSubstance() {
        return containedSubstance;
    }

    public int getContainedAmount() {
        return containedAmount;
    }

    /**
     * Tries to place liquid in the container
     * @param substance The substance to place
     * @param amount The amount to place
     * @return The amount of substance actually placed
     */
    public int tryPlaceLiquid(Block substance, int amount) {
        if ((containedSubstance != Blocks.AIR && substance != containedSubstance) ||
                substance == null ||
                substance == Blocks.AIR ||
                amount <= 0) {
            return 0;
        }

        int actualAmount = amount;
        if (containedAmount + amount > MAX_AMOUNT) {
            actualAmount = MAX_AMOUNT - containedAmount;
        }

        containedSubstance = substance;
        containedAmount += actualAmount;

        markDirty();

        return actualAmount;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);

        compound.setInteger("contained_substance", Block.getIdFromBlock(containedSubstance));
        compound.setInteger("contained_amount", containedAmount);

        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        containedSubstance = Block.getBlockById(compound.getInteger("contained_substance"));
        containedAmount = compound.getInteger("contained_amount");
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
