package gogofo.minecraft.awesome.block;

import gogofo.minecraft.awesome.init.Items;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

import java.util.Random;

public class BlockSaltBlock extends Block {
    public BlockSaltBlock() {
        super(Material.SAND);
        setHardness(0.5F);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Items.salt;
    }

    @Override
    public int quantityDropped(Random random) {
        return random.nextInt(4);
    }
}
