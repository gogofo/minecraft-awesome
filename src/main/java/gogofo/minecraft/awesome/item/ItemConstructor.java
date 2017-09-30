package gogofo.minecraft.awesome.item;

import gogofo.minecraft.awesome.entity.EntityConstructor;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemConstructor extends ItemEntitySpawner {
    @Override
    protected Entity createEntityToSpawn(World worldIn, BlockPos placement) {
        return new EntityConstructor(worldIn, placement);
    }
}
