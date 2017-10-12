package gogofo.minecraft.awesome.tileentity;

import gogofo.minecraft.awesome.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

public class TileEntityPerimeterMarker extends TileEntity implements ITickable {

    public static final int LASER_MAX_LENGTH = 16;
    private int eastMatchDistance = 0;
    private int southMatchDistance = 0;

    @Override
    public void update() {
        eastMatchDistance = pos.getX();
        southMatchDistance = pos.getZ();

        RayTraceResult rayTraceResult = world.rayTraceBlocks(new Vec3d(pos.getX() + 1, pos.getY() + 0.5, pos.getZ() + 0.5), new Vec3d(pos.getX() + LASER_MAX_LENGTH, pos.getY() + 0.5, pos.getZ() + 0.5), false, true, false);
        if (rayTraceResult != null && world.getBlockState(rayTraceResult.getBlockPos()).getBlock() == Blocks.perimeter_marker) {
            eastMatchDistance = rayTraceResult.getBlockPos().getX() - pos.getX();
        }

        rayTraceResult = world.rayTraceBlocks(new Vec3d(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 1), new Vec3d(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + LASER_MAX_LENGTH), false, true, false);
        if (rayTraceResult != null && world.getBlockState(rayTraceResult.getBlockPos()).getBlock() == Blocks.perimeter_marker) {
            southMatchDistance = rayTraceResult.getBlockPos().getZ() - pos.getZ();
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
