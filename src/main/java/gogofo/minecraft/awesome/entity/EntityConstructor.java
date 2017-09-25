package gogofo.minecraft.awesome.entity;

import gogofo.minecraft.awesome.init.Items;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

import static net.minecraft.block.Block.NULL_AABB;

public class EntityConstructor extends Entity {

    private static final DataParameter<Integer> FACING = EntityDataManager.createKey(EntityConstructor.class, DataSerializers.VARINT);

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
        this.dataManager.register(FACING, EnumFacing.NORTH.ordinal());
    }

    public EnumFacing getFacing() {
        return EnumFacing.values()[this.dataManager.get(FACING)];
    }

    public void setFacing(EnumFacing facing) {
        this.dataManager.set(FACING, facing.ordinal());
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound) {
        if (compound.hasKey("facing")) {
            setFacing(EnumFacing.values()[compound.getInteger("facing")]);
        }
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound) {
        compound.setInteger("facing", getFacing().ordinal());
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

    private boolean tryMove(EnumFacing direction) {
        BlockPos newPos = getPosition().offset(direction);
        BlockPos below_pos = newPos.offset(EnumFacing.DOWN);

        List<AxisAlignedBB> collisionBoxes =
                world.getCollisionBoxes(this,
                        getCollisionBoundingBox().expand(direction.getFrontOffsetX(),
                                direction.getFrontOffsetY(),
                                direction.getFrontOffsetZ()));
        if (!collisionBoxes.isEmpty() ||
                world.getBlockState(below_pos).getCollisionBoundingBox(world, below_pos) == NULL_AABB) {
            return false;
        }

        moveToBlockPosAndAngles(newPos, rotationYaw, rotationPitch);

        return true;
    }

    @Override
    public void onUpdate() {
        if (world.isRemote) {
            return;
        }

        super.onUpdate();

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
