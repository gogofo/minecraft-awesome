package gogofo.minecraft.awesome.block;

import gogofo.minecraft.awesome.colorize.ISingleColoredObject;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockRenderLayer;

public class BlockOneColor extends Block implements ISingleColoredObject {
    private int color;

    public BlockOneColor(Material materialIn, int color) {
        super(materialIn);
        this.color = color;
    }

    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

    @Override
    public int getColor() {
        return color;
    }
}
