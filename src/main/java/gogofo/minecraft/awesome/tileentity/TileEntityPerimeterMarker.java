package gogofo.minecraft.awesome.tileentity;

import gogofo.minecraft.awesome.PerimeterManager;
import gogofo.minecraft.awesome.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

public class TileEntityPerimeterMarker extends TileEntity implements ITickable {

    public static final int LASER_MAX_LENGTH = 64;
    private int eastMatchDistance = 0;
    private int southMatchDistance = 0;

    @Override
    public void update() {
        int newEastMatchDistance = 0;
        int newSouthMatchDistance = 0;

        RayTraceResult rayTraceResult = world.rayTraceBlocks(new Vec3d(pos.getX() + 1, pos.getY() + 0.5, pos.getZ() + 0.5), new Vec3d(pos.getX() + LASER_MAX_LENGTH, pos.getY() + 0.5, pos.getZ() + 0.5), false, true, false);
        if (rayTraceResult != null && world.getBlockState(rayTraceResult.getBlockPos()).getBlock() == Blocks.perimeter_marker) {
            newEastMatchDistance = rayTraceResult.getBlockPos().getX() - pos.getX();
        }

        rayTraceResult = world.rayTraceBlocks(new Vec3d(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 1), new Vec3d(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + LASER_MAX_LENGTH), false, true, false);
        if (rayTraceResult != null && world.getBlockState(rayTraceResult.getBlockPos()).getBlock() == Blocks.perimeter_marker) {
            newSouthMatchDistance = rayTraceResult.getBlockPos().getZ() - pos.getZ();
        }

        updatePerimeterGridAsNeeded(eastMatchDistance, newEastMatchDistance, EnumFacing.EAST);
        updatePerimeterGridAsNeeded(southMatchDistance, newSouthMatchDistance, EnumFacing.SOUTH);

        eastMatchDistance = newEastMatchDistance;
        southMatchDistance = newSouthMatchDistance;
    }

    public void handleBreak() {
        updatePerimeterGridAsNeeded(eastMatchDistance, 0, EnumFacing.EAST);
        updatePerimeterGridAsNeeded(southMatchDistance, 0, EnumFacing.SOUTH);
    }

    private void updatePerimeterGridAsNeeded(int oldDistance, int newDistance, EnumFacing direction) {
        if (world.isRemote) {
            return;
        }

        if (oldDistance == newDistance) {
            return;
        }

        if (oldDistance > 0) {
            PerimeterManager.instance.removeFromGrid(pos, pos.offset(direction, oldDistance), direction);
        }

        if (newDistance > 0) {
            PerimeterManager.instance.addToGrid(pos, pos.offset(direction, newDistance), direction);
        }
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return INFINITE_EXTENT_AABB;
    }

    @Override
    public boolean shouldRenderInPass(int pass) {
        return super.shouldRenderInPass(pass);
    }

    public int getEastMatchDistance() {
        return eastMatchDistance;
    }

    public int getSouthMatchDistance() {
        return southMatchDistance;
    }
}
