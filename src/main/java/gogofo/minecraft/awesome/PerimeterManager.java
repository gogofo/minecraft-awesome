package gogofo.minecraft.awesome;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

import java.util.HashMap;

public class PerimeterManager {
    public static PerimeterManager instance = new PerimeterManager();

    private HashMap<BlockPos, Integer> perimeterGrid = new HashMap<>();

    public void addToGrid(BlockPos marker1, BlockPos marker2, EnumFacing direction) {
        marker1 = cleanPos(marker1);
        marker2 = cleanPos(marker2);

        BlockPos pos = marker1;
        while (!pos.equals(marker2)) {
            addPerimeter(pos);
            pos = pos.offset(direction);
        }
    }

    public void removeFromGrid(BlockPos marker1, BlockPos marker2, EnumFacing direction) {
        marker1 = cleanPos(marker1);
        marker2 = cleanPos(marker2);

        BlockPos pos = marker1;
        while (!pos.equals(marker2)) {
            removePerimeter(pos);
            pos = pos.offset(direction);
        }
    }

    public boolean hasPerimeter(BlockPos pos) {
        pos = cleanPos(pos);
        
        return perimeterGrid.containsKey(pos);
    }

    private void addPerimeter(BlockPos pos) {
        if (perimeterGrid.containsKey(pos)) {
            perimeterGrid.put(pos, perimeterGrid.get(pos) + 1);
        } else {
            perimeterGrid.put(pos, 0);
        }
    }

    private void removePerimeter(BlockPos pos) {
        if (!perimeterGrid.containsKey(pos)) {
            return;
        }

        int newPerimeterAmount = perimeterGrid.get(pos) - 1;

        if (newPerimeterAmount > 0) {
            perimeterGrid.put(pos, newPerimeterAmount);
        } else {
            perimeterGrid.remove(pos);
        }
    }

    private BlockPos cleanPos(BlockPos pos) {
        return pos.down(pos.getY());
    }
}
