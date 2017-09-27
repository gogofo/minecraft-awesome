package gogofo.minecraft.awesome.entity;

import gogofo.minecraft.awesome.init.Items;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static net.minecraft.block.Block.NULL_AABB;

public class EntityConstructor extends EntityMachineBlock {

    public EntityConstructor(World world) {
        super(world);
    }

    public EntityConstructor(World worldIn, BlockPos pos) {
        super(worldIn, pos);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
    }

    /**
     * Left click
     */
    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (world.isRemote) {
            return false;
        }

        if (!source.isCreativePlayer()) {
            dropItem(Items.burnt_residue, 1);
        }

        setDead();

        return false;
    }

    /**
     * Right click
     */
    @Override
    public boolean processInitialInteract(EntityPlayer player, EnumHand hand) {
        EnumFacing facing = player.getHorizontalFacing();

        tryMove(facing);

        return true;
    }

    @Override
    protected boolean canMove(EnumFacing direction) {
        BlockPos below_pos = getPosition().offset(direction).offset(EnumFacing.DOWN);

        return world.getBlockState(below_pos).getCollisionBoundingBox(world, below_pos) != NULL_AABB &&
                super.canMove(direction);
    }

    @Override
    protected void onElectricUpdate() {
        if (ticksExisted % 20 == 0) {
            BlockPos prevPos = getPosition();
            EnumFacing facing = getFacing();
            if (tryMove(facing)) {
                world.setBlockState(prevPos, Blocks.COBBLESTONE.getDefaultState());
            } else {
                setFacing(facing.rotateY());
            }
        }
    }
}
