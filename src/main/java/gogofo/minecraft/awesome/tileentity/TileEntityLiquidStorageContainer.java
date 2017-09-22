package gogofo.minecraft.awesome.tileentity;

import gogofo.minecraft.awesome.interfaces.ILiquidContainer;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityLiquidStorageContainer extends TileEntity implements ILiquidContainer {

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

    @Override
    public int tryPlaceLiquid(Block substance, int amount) {
        if (!isInputOk(substance, amount) || substance == Blocks.AIR) {
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
    public int tryTakeLiquid(Block substance, int amount) {
        if (!isInputOk(substance, amount) || containedSubstance == Blocks.AIR) {
            return 0;
        }

        int actualAmount = amount;
        if (amount > containedAmount) {
            actualAmount = containedAmount;
        }

        containedAmount -= actualAmount;

        if (containedAmount == 0) {
            containedSubstance = Blocks.AIR;
        }

        markDirty();

        return actualAmount;
    }

    @Override
    public Block getSubstance() {
        return containedSubstance;
    }

    private boolean isInputOk(Block substance, int amount) {
        return (containedSubstance == Blocks.AIR || substance == Blocks.AIR || substance == containedSubstance) &&
                substance != null &&
                amount > 0;
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
