package gogofo.minecraft.awesome.item;

import gogofo.minecraft.awesome.entity.EntityBlock;
import gogofo.minecraft.awesome.entity.EntityDigger;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemDigger extends ItemEntityBlockSpawner {
    @Override
    protected EntityBlock createEntityToSpawn(World worldIn, BlockPos placement, EntityPlayer player) {
        EntityDigger entity = new EntityDigger(worldIn, placement);
        entity.setFacing(player.getHorizontalFacing().getOpposite());
        return entity;
    }
}
