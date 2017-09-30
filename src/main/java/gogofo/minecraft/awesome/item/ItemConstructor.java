package gogofo.minecraft.awesome.item;

import gogofo.minecraft.awesome.entity.EntityConstructor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemConstructor extends Item {

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            BlockPos placement = pos.offset(EnumFacing.UP);
            if (worldIn.getBlockState(placement).getBlock() == Blocks.AIR) {
                EntityConstructor entity = new EntityConstructor(worldIn, placement);
                worldIn.spawnEntity(entity);

                if (!player.isCreative()) {
                    player.getHeldItem(hand).shrink(1);
                }
            }
        }

        return EnumActionResult.SUCCESS;
    }


}
