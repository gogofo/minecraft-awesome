package gogofo.minecraft.awesome.item;

import gogofo.minecraft.awesome.entity.EntityBlock;
import gogofo.minecraft.awesome.entity.EntityConstructor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemConstructor extends ItemEntityBlockSpawner {
    @Override
    protected EntityBlock createEntityToSpawn(World worldIn, BlockPos placement, EntityPlayer player) {
        EntityConstructor entity = new EntityConstructor(worldIn, placement);
        entity.setFacing(player.getHorizontalFacing().getOpposite());
        return entity;
    }
}
