package gogofo.minecraft.awesome.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

import static net.minecraft.block.Block.NULL_AABB;

public class EntityConstructor extends Entity {

    public EntityConstructor(World world) {
        super(world);
        setSize(1, 1);
    }

    public EntityConstructor(World worldIn, BlockPos pos) {
        super(worldIn);
        setPosition(pos.getX(), pos.getY(), pos.getZ());
    }

    @Override
    protected void entityInit() {

    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound) {

    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound) {

    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox() {
        return getEntityBoundingBox();
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBox(Entity entityIn) {
        return getEntityBoundingBox();
    }

    @Override
    public void setPosition(double x, double y, double z) {
        this.posX = x;
        this.posY = y;
        this.posZ = z;
        this.setEntityBoundingBox(new AxisAlignedBB(x, y, z, x + width, y + height, z + width));
    }

    public void moveToBlockPosAndAngles(BlockPos pos, float rotationYawIn, float rotationPitchIn)
    {
        this.setLocationAndAngles((double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), rotationYawIn, rotationPitchIn);
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    /**
     * Left click
     */
    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        return super.attackEntityFrom(source, amount);
    }

    /**
     * Right click
     */
    @Override
    public boolean processInitialInteract(EntityPlayer player, EnumHand hand) {
        EnumFacing facing = player.getHorizontalFacing();
        BlockPos new_pos = getPosition().offset(facing);
        BlockPos below_pos = new_pos.offset(EnumFacing.DOWN);
        
        if (world.getBlockState(new_pos).getBlock().isReplaceable(world, new_pos) &&
                world.getBlockState(below_pos).getCollisionBoundingBox(world, below_pos) != NULL_AABB) {
            moveToBlockPosAndAngles(new_pos, rotationYaw, rotationPitch);
        }

        return true;
    }
}
