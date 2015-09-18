package gogofo.minecraft.awesome.block;

import java.util.Random;

import gogofo.minecraft.awesome.init.Blocks;
import gogofo.minecraft.awesome.tileentity.TileEntitySortingPipe;
import gogofo.minecraft.awesome.tileentity.TileEntitySuctionPipe;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockSuctionPipe extends BlockPipe {
	
	@Override
	protected boolean canConnectTo(Block block) {
		return super.canConnectTo(block) && !(block instanceof BlockSuctionPipe);
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntitySuctionPipe();
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Item.getItemFromBlock(Blocks.suction_pipe);
	}
}
