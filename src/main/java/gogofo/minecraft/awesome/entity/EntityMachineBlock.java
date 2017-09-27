package gogofo.minecraft.awesome.entity;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class EntityMachineBlock extends EntityBlock {

    public EntityMachineBlock(World worldIn) {
        super(worldIn);
    }

    public EntityMachineBlock(World worldIn, BlockPos pos) {
        super(worldIn, pos);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (world.isRemote) {
            return;
        }

        onElectricUpdate();
    }

    protected abstract void onElectricUpdate();
}
