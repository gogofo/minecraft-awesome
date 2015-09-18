package gogofo.minecraft.learning.blocks;

import java.util.List;
import java.util.Random;

import gogofo.minecraft.learning.init.LearningItems;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;

public class TestBlock extends Block {
	public TestBlock() {
		super(Material.cloth);
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return LearningItems.test_item;
	}
	
	@Override
	public int quantityDropped(IBlockState state, int fortune, Random random) {
		return 5;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public EnumWorldBlockLayer getBlockLayer() {
		return EnumWorldBlockLayer.CUTOUT;
	}
	
	@Override
	public boolean isFullCube() {
		return false;
	}
}