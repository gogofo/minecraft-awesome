package gogofo.minecraft.awesome.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public abstract class EntityBlock extends Entity {

    private static final DataParameter<Integer> FACING = EntityDataManager.createKey(EntityBlock.class, DataSerializers.VARINT);

    public EntityBlock(World worldIn) {
        super(worldIn);
        setSize(1, 1);
    }

    public EntityBlock(World worldIn, BlockPos pos) {
        super(worldIn);
        setPosition(pos.getX(), pos.getY(), pos.getZ());
    }

    @Override
    protected void entityInit() {
        this.dataManager.register(FACING, EnumFacing.NORTH.ordinal());
    }

    public EnumFacing getFacing() {
        return EnumFacing.values()[this.dataManager.get(FACING)];
    }

    public void setFacing(EnumFacing facing) {
        this.dataManager.set(FACING, facing.ordinal());
    }

    @Override
    protected final void writeEntityToNBT(NBTTagCompound compound) {
        saveToNBT(compound, false);
    }

    @Override
    protected final void readEntityFromNBT(NBTTagCompound compound) {
        loadFromNBT(compound);
    }

    public void saveToNBT(NBTTagCompound compound, boolean isForItem) {
        if (isForItem) {
            return;
        }

        compound.setInteger("facing", getFacing().ordinal());
    }

    public void loadFromNBT(NBTTagCompound compound) {
        if (compound.hasKey("facing")) {
            setFacing(EnumFacing.values()[compound.getInteger("facing")]);
        }
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox() {
        return getEntityBoundingBox();
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBox(Entity entityIn) {
        return entityIn.canBePushed() ? entityIn.getEntityBoundingBox() : null;
    }

    @Override
    public void setPosition(double x, double y, double z) {
        this.posX = x;
        this.posY = y;
        this.posZ = z;
        this.setEntityBoundingBox(new AxisAlignedBB(x, y, z, x + width, y + height, z + width));
    }

    @Override
    public void moveToBlockPosAndAngles(BlockPos pos, float rotationYawIn, float rotationPitchIn)
    {
        this.setLocationAndAngles((double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), rotationYawIn, rotationPitchIn);
    }

    @Override
    public BlockPos getPosition() {
        return new BlockPos(this.posX, this.posY, this.posZ);
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    protected boolean tryMove(EnumFacing direction) {
        if (!canMove(direction)) {
            return false;
        }

        moveToBlockPosAndAngles(getPosition().offset(direction), rotationYaw, rotationPitch);

        return true;
    }

    protected boolean canMove(EnumFacing direction) {
        List<AxisAlignedBB> collisionBoxes =
                world.getCollisionBoxes(this,
                        getCollisionBoundingBox().expand(direction.getFrontOffsetX(),
                                direction.getFrontOffsetY(),
                                direction.getFrontOffsetZ()));

        return collisionBoxes.isEmpty();
    }
}
