package gogofo.minecraft.awesome.item;

import gogofo.minecraft.awesome.colorize.ISingleColoredObject;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemBattery extends AwesomeItemChargable implements ISingleColoredObject {

    private int maxCharge = 500;
    private int chargeSpeed = 10;
    private int color = 0xFFFFFF;

    public ItemBattery() {
        super();

        setMaxStackSize(16);
    }

    @Override
    public int getMaxRequiredCharge(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
        return 0;
    }

    @Override
    public int onChargeableItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
        return 0;
    }

    @Override
    public int getMaxRequiredCharge(ItemStack stack, World worldIn, EntityPlayer playerIn) {
        return getChargeSpeed();
    }

    @Override
    public int onChargeableItemUse(ItemStack stack, World worldIn, EntityPlayer playerIn) {
        ItemStack heldItem = playerIn.getHeldItem(EnumHand.OFF_HAND);
        if (heldItem.isEmpty() || !(heldItem.getItem() instanceof AwesomeItemChargable)) {
            return 0;
        }

        AwesomeItemChargable chargeable = (AwesomeItemChargable)heldItem.getItem();
        int amountToCharge = getChargeSpeed();

        return chargeable.charge(heldItem, amountToCharge);
    }

    @Override
    public int getMaxCharge() {
        return maxCharge;
    }

    protected int getChargeSpeed() {
        return chargeSpeed;
    }

    @Override
    public int getColor() {
        return color;
    }

    public ItemBattery setMaxCharge(int maxCharge) {
        this.maxCharge = maxCharge;
        return this;
    }

    public ItemBattery setChargeSpeed(int chargeSpeed) {
        this.chargeSpeed = chargeSpeed;
        return this;
    }

    public ItemBattery setColor(int color) {
        this.color = color;
        return this;
    }
}
