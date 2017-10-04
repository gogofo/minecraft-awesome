package gogofo.minecraft.awesome.entity;

import gogofo.minecraft.awesome.AwesomeMod;
import gogofo.minecraft.awesome.gui.GuiEnum;
import gogofo.minecraft.awesome.init.Blocks;
import gogofo.minecraft.awesome.interfaces.IConfigurableSidedInventory;
import gogofo.minecraft.awesome.interfaces.ILiquidContainer;
import gogofo.minecraft.awesome.item.ItemBattery;
import gogofo.minecraft.awesome.item.ItemLiquidContainer;
import gogofo.minecraft.awesome.utils.InventoryUtils;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class EntityMachineBlock extends EntityBlock implements IInteractionObject, IConfigurableSidedInventory, ILiquidContainer {

    private static final DataParameter<Integer> OIL_AMOUNT = EntityDataManager.createKey(EntityMachineBlock.class, DataSerializers.VARINT);
    public static final int FIELD_ID_OIL_AMOUNT = 0;

    protected ItemStack[] itemStackArray;
    protected ArrayList<ArrayList<Integer>> slotsForFace;

    public EntityMachineBlock(World worldIn) {
        super(worldIn);
        init();
    }

    public EntityMachineBlock(World worldIn, BlockPos pos) {
        super(worldIn, pos);
        init();
    }

    private void init() {
        itemStackArray = new ItemStack[getSlotCount()];
        slotsForFace = new ArrayList<>();

        Arrays.fill(itemStackArray, ItemStack.EMPTY);
        InventoryUtils.initSlotsForFace(slotsForFace, this, true);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        dataManager.register(OIL_AMOUNT, 0);
    }

    public int getOilAmount() {
        return dataManager.get(OIL_AMOUNT);
    }

    public void setOilAmount(int amount) {
        dataManager.set(OIL_AMOUNT, amount);
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (world.isRemote) {
            return false;
        }

        if (!source.isCreativePlayer()) {
            Item droppedItem = getDroppedItem();
            if (droppedItem != null) {

                NBTTagCompound compound = new NBTTagCompound();
                saveToNBT(compound, true);

                ItemStack stack = new ItemStack(droppedItem);
                stack.setTagCompound(compound);

                entityDropItem(stack, 1);
            }
        }

        InventoryHelper.dropInventoryItems(world, getPosition(), this);

        setDead();

        return false;
    }

    @Override
    public boolean processInitialInteract(EntityPlayer player, EnumHand hand) {
        ItemStack heldStack = player.getHeldItem(hand);
        if (heldStack.getItem() instanceof ItemLiquidContainer) {
            ((ItemLiquidContainer)heldStack.getItem()).interactWithLiquidContainer(player, heldStack, this);
            return true;
        }

        if (player.isSneaking()) {
            return false;
        }

        if (!world.isRemote) {
            BlockPos pos = getPosition();
            player.openGui(AwesomeMod.MODID,
                    getGui().ordinal(),
                    world,
                    pos.getX(),
                    pos.getY(),
                    pos.getZ());
        }

        return true;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (world.isRemote) {
            return;
        }

        ItemStack stackInSlot = getStackInSlot(0);

        if (!(stackInSlot.getItem() instanceof ItemBattery) ||
                !((ItemBattery)stackInSlot.getItem()).hasCharge(stackInSlot)) {
            return;
        }

        int oilAmount = getOilAmount();

        if (oilAmount == 0) {
            return;
        }

        int[] usage = onElectricUpdate();

        ((ItemBattery)stackInSlot.getItem()).reduceCharge(stackInSlot, usage[0]);
        setOilAmount(oilAmount - usage[1]);
        markDirty();
    }

    @Override
    public String getGuiID() {
        return getGui().guiName();
    }

    @Override
    public void saveToNBT(NBTTagCompound compound, boolean isForItem) {
        super.saveToNBT(compound, isForItem);

        if (!isForItem) {
            InventoryUtils.writeToNBT(itemStackArray, this, compound);
        }

        compound.setInteger("oilAmount", getOilAmount());
    }

    @Override
    public void loadFromNBT(NBTTagCompound compound) {
        super.loadFromNBT(compound);

        InventoryUtils.readFromNBT(itemStackArray, slotsForFace, this, compound);

        if (compound.hasKey("oilAmount")) {
            setOilAmount(compound.getInteger("oilAmount"));
        }
    }

    //<editor-fold desc="Inventory">
    @Override
    public int getSizeInventory() {
        return itemStackArray.length;
    }

    @Override
    public boolean isEmpty() {
        return InventoryUtils.isEmpty(itemStackArray);
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return itemStackArray[index];
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        return InventoryUtils.decrStackSize(itemStackArray, index, count, this);
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        return InventoryUtils.removeStackFromSlot(itemStackArray, index);
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        InventoryUtils.setInventorySlotForContents(itemStackArray, index, stack, this);
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public void markDirty() {
        // TODO: What to do?
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer player) {
        return player.getDistanceSq(getPosition()) <= 64.0D;
    }

    @Override
    public void openInventory(EntityPlayer player) {
    }

    @Override
    public void closeInventory(EntityPlayer player) {
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        if (index == 0) {
            return stack.getItem() instanceof ItemBattery;
        }

        return true;
    }

    @Override
    public void clear() {
        InventoryUtils.clear(itemStackArray);
    }

    @Override
    public int[] getSlotsForFace(EnumFacing side) {
        return slotsForFace.get(side.getIndex()).stream().mapToInt(i -> i).toArray();
    }

    @Override
    public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction)
    {
        return isItemValidForSlot(index, itemStackIn);
    }

    @Override
    public boolean canExtractItem(int parSlotIndex, ItemStack parStack, EnumFacing parFacing)
    {
        return true;
    }

    @Override
    public BlockPos getInventoryPos() {
        return getPosition();
    }

    @Override
    public void addSlotToFace(Integer slot, EnumFacing face) {
        slotsForFace.get(face.getIndex()).add(slot);
        markDirty();
    }

    @Override
    public void removeSlotFromFace(Integer slot, EnumFacing face) {
        slotsForFace.get(face.getIndex()).remove(slot);
        markDirty();
    }

    protected int getSlotCount() {
        return 1;
    }

    @Override
    public int getField(int id) {
        switch (id) {
            case FIELD_ID_OIL_AMOUNT:
                return getOilAmount();
        }

        return 0;
    }

    @Override
    public void setField(int id, int value) {
        switch (id) {
            case FIELD_ID_OIL_AMOUNT:
                setOilAmount(value);
                break;
        }
    }

    @Override
    public int getFieldCount() {
        return 1;
    }

    @Override
    public Integer[] getDefaultSlotForFace(EnumFacing face) {
        return new Integer[] {0};
    }
    //</editor-fold>

    //<editor-fold desc="LiquidContainer">
    public int tryPlaceLiquid(Block substance, int amount) {
        if (substance != Blocks.oil) {
            return 0;
        }

        int addedAmount = amount;
        int oilCapacity = getOilCapacity();
        int curAmount = getOilAmount();

        if (curAmount + addedAmount > oilCapacity) {
            addedAmount = oilCapacity - curAmount;
        }

        setOilAmount(curAmount + addedAmount);
        markDirty();

        return addedAmount;
    }

    public int tryTakeLiquid(Block substance, int amount) {
        if (substance != Blocks.oil) {
            return 0;
        }

        int takenAmount = amount;
        int curAmount = getOilAmount();

        if (takenAmount > curAmount) {
            takenAmount = curAmount;
        }

        setOilAmount(curAmount - takenAmount);
        markDirty();

        return takenAmount;
    }

    public Block getSubstance() {
        return Blocks.oil;
    }
    //</editor-fold>

    protected abstract int[] onElectricUpdate();
    protected abstract GuiEnum getGui();
    protected abstract Item getDroppedItem();
    public abstract int getOilCapacity();
}
