package gogofo.minecraft.awesome.item;

import gogofo.minecraft.awesome.colorize.ISingleColoredObject;
import gogofo.minecraft.awesome.init.Ores;
import gogofo.minecraft.awesome.interfaces.IWrenchable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class ItemWrench extends Item implements ISingleColoredObject {

    public ItemWrench() {
        setMaxDamage(100);
    }

    @Override
    public int getColor() {
        return Ores.copper.getColor();
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);

        RayTraceResult rayTraceResult = this.rayTrace(worldIn, playerIn, true);

        if (rayTraceResult == null) {
            return new ActionResult<>(EnumActionResult.FAIL, stack);
        }

        if (rayTraceResult.typeOfHit == RayTraceResult.Type.BLOCK) {
            TileEntity tileEntity = worldIn.getTileEntity(rayTraceResult.getBlockPos());
            if (tileEntity instanceof IWrenchable) {
                ((IWrenchable)tileEntity).onWrenchRightClicked(playerIn, stack);

                stack.damageItem(1, playerIn);
            }
        }

        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }
}
