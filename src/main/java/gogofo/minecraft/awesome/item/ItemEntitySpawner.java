package gogofo.minecraft.awesome.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class ItemEntitySpawner extends Item {

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            BlockPos placement = pos.offset(EnumFacing.UP);
            if (worldIn.getBlockState(placement).getBlock() == Blocks.AIR) {
                Entity entity = createEntityToSpawn(worldIn, placement, player);
                worldIn.spawnEntity(entity);
                worldIn.playSound(null, placement, SoundEvents.BLOCK_METAL_PLACE, SoundCategory.BLOCKS, 0.3F, 0.8f);

                if (!player.isCreative()) {
                    player.getHeldItem(hand).shrink(1);
                }
            }
        }

        return EnumActionResult.SUCCESS;
    }

    protected abstract Entity createEntityToSpawn(World worldIn, BlockPos placement, EntityPlayer player);
}
