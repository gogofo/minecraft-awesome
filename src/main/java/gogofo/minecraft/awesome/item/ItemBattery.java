package gogofo.minecraft.awesome.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemBattery extends AwesomeItemChargable {
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
        return 100;
    }

    protected int getChargeSpeed() {
        return 10;
    }
}
