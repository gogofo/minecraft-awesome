package gogofo.minecraft.awesome.block;

import java.util.Random;

import gogofo.minecraft.awesome.AwesomeMod;
import gogofo.minecraft.awesome.gui.GuiEnum;
import gogofo.minecraft.awesome.init.Blocks;
import gogofo.minecraft.awesome.tileentity.TileEntitySortingPipe;
import gogofo.minecraft.awesome.tileentity.TileEntitySuctionPipe;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockSortingPipe extends BlockPipe {
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntitySortingPipe();
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumFacing side, float hitX, float hitY, float hitZ) {
		
		if (!worldIn.isRemote) {
			playerIn.openGui(AwesomeMod.MODID, 
                    GuiEnum.SORTING_PIPE.ordinal(), 
                    worldIn, 
                    pos.getX(), 
                    pos.getY(), 
                    pos.getZ()); 
		}
		
		return true;
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Item.getItemFromBlock(Blocks.sorting_pipe);
	}
}
