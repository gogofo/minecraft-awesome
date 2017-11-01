package gogofo.minecraft.awesome.block;

import net.minecraft.block.material.Material;

public class BlockGenericOre extends BlockOneColor {

    public BlockGenericOre(int color) {
        super(Material.ROCK, color);

        setHardness(3.0F);
        setHarvestLevel("pickaxe", 1);
    }
}
