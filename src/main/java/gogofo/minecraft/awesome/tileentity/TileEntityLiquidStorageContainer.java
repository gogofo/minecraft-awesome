package gogofo.minecraft.awesome.tileentity;

import gogofo.minecraft.awesome.interfaces.ILiquidContainer;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.Nullable;

public class TileEntityLiquidStorageContainer extends TileEntity implements ILiquidContainer {

    public static final int MAX_AMOUNT = 15000;

    private Block containedSubstance = Blocks.AIR;
    private int containedAmount = 0;

    @Override
    public boolean hasFastRenderer() {
        return true;
    }

    @Override
    public int tryPlaceLiquid(Block substance, int amount) {
        Block collaborativeSubstance = getCollaborativeSubstance();
        if ((collaborativeSubstance != Blocks.AIR && substance != Blocks.AIR && substance != collaborativeSubstance) ||
                substance == null ||
                amount <= 0) {
            return 0;
        }

        int amountToPlace = amount;
        TileEntity te = getEdgeContainer(EnumFacing.DOWN);

        while (amountToPlace > 0 && te instanceof TileEntityLiquidStorageContainer) {
            TileEntityLiquidStorageContainer container = (TileEntityLiquidStorageContainer) te;

            amountToPlace -= container.innerTryPlaceLiquid(substance, amountToPlace);
            te = world.getTileEntity(te.getPos().offset(EnumFacing.UP));
        }

        return amount - amountToPlace;
    }

    @Override
    public int tryTakeLiquid(Block substance, int amount) {
        Block collaborativeSubstance = getCollaborativeSubstance();
        if ((collaborativeSubstance != Blocks.AIR && substance != Blocks.AIR && substance != collaborativeSubstance) ||
                substance == null ||
                amount <= 0) {
            return 0;
        }

        int amountToTake = amount;
        TileEntity te = getEdgeContainer(EnumFacing.UP);

        while (amountToTake > 0 && te instanceof TileEntityLiquidStorageContainer) {
            TileEntityLiquidStorageContainer container = (TileEntityLiquidStorageContainer) te;

            amountToTake -= container.innerTryTakeLiquid(substance, amountToTake);
            te = world.getTileEntity(te.getPos().offset(EnumFacing.DOWN));
        }

        return amount - amountToTake;
    }

    @Override
    public Block getSubstance() {
        return getCollaborativeSubstance();
    }

    public Block getContainedSubstance() {
        return containedSubstance;
    }

    public int getContainedAmount() {
        return containedAmount;
    }

    private Block getCollaborativeSubstance() {
        if (containedSubstance != Blocks.AIR) {
            return containedSubstance;
        }

        TileEntity te = this;

        while (te instanceof TileEntityLiquidStorageContainer) {
            TileEntityLiquidStorageContainer container = (TileEntityLiquidStorageContainer) te;

            if (container.containedSubstance != Blocks.AIR) {
                return container.containedSubstance;
            }

            te = world.getTileEntity(te.getPos().offset(EnumFacing.DOWN));
        }

        return Blocks.AIR;
    }

    private TileEntity getEdgeContainer(EnumFacing facing) {
        TileEntity te = this;
        TileEntityLiquidStorageContainer container = null;

        while (te instanceof TileEntityLiquidStorageContainer) {
            container = (TileEntityLiquidStorageContainer) te;

            te = world.getTileEntity(te.getPos().offset(facing));
        }

        return container;
    }

    private int innerTryPlaceLiquid(Block substance, int amount) {
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
        notifyUpdate(pos);

        return actualAmount;
    }

    private int innerTryTakeLiquid(Block substance, int amount) {
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
        notifyUpdate(pos);

        return actualAmount;
    }

    private boolean isInputOk(Block substance, int amount) {
        return (containedSubstance == Blocks.AIR || substance == Blocks.AIR || substance == containedSubstance) &&
                substance != null &&
                amount > 0;
    }

    protected void notifyUpdate(BlockPos blockPos) {
        IBlockState state = world.getBlockState(blockPos);
        world.notifyBlockUpdate(blockPos, state, state, 3);
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

    public static Block getLiquidFromStack(ItemStack stack) {
        if (stack.getTagCompound() == null || !stack.getTagCompound().hasKey("BlockEntityTag")) {
            return Blocks.AIR;
        }

        NBTTagCompound compound = (NBTTagCompound) stack.getTagCompound().getTag("BlockEntityTag");

        if (!compound.hasKey("contained_substance")) {
            return Blocks.AIR;
        }

        return Block.getBlockById(compound.getInteger("contained_substance"));
    }

    public static int getAmountFromStack(ItemStack stack) {
        if (stack.getTagCompound() == null || !stack.getTagCompound().hasKey("BlockEntityTag")) {
            return 0;
        }

        NBTTagCompound compound = (NBTTagCompound) stack.getTagCompound().getTag("BlockEntityTag");

        if (!compound.hasKey("contained_amount")) {
            return 0;
        }

        return compound.getInteger("contained_amount");
    }

    @Nullable
    @Override
    public ITextComponent getDisplayName() {
        return new TextComponentString(String.format("%smB", getContainedAmount()));
    }
}
