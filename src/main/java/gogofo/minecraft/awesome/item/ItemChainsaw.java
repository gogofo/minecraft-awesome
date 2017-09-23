package gogofo.minecraft.awesome.item;

import java.util.Set;

import com.google.common.collect.Sets;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemChainsaw extends AwesomeItemChargable {
	@Override
	public int getMaxRequiredCharge(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos,
			EnumFacing side, float hitX, float hitY, float hitZ) {
		return 1;
	}

	@Override
	public int onChargeableItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side,
			float hitX, float hitY, float hitZ) {
		return 0;
	}

	@Override
	public int getMaxCharge() {
		return 300;
	}

	@Override
	public int getMaxRequiredCharge(ItemStack stack, World worldIn, EntityPlayer playerIn) {
		return 0;
	}

	@Override
	public int onChargeableItemUse(ItemStack stack, World worldIn, EntityPlayer playerIn) {
		return 0;
	}
	
    private static final Set LOGS = Sets.newHashSet(Blocks.LOG, Blocks.LOG2, gogofo.minecraft.awesome.init.Blocks.dead_wood);
	
    @Override
	public float getStrVsBlock(ItemStack stack, IBlockState blockState)
    {
		if (getCharge(stack) == 0) {
			return 0;
		}
		
        return blockState.getMaterial() != Material.WOOD && blockState.getMaterial() != Material.PLANTS && blockState.getMaterial() != Material.VINE ? super.getStrVsBlock(stack, blockState) : 6.0f;
    }
	
	@Override
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState blockState, BlockPos pos, EntityLivingBase playerIn) {
		final int chargeCost = 1;
		
		if (worldIn.isRemote) {
			return false;
		}
		
		if (getCharge(stack) < chargeCost) {
			return false;
		}
		
		if (!LOGS.contains(blockState.getBlock())) {
			return false;
		}
		
		reduceCharge(stack, chargeCost);
		
		cutTree(worldIn, pos.up());
		
		return false;
	}

	private void cutTree(World world, BlockPos pos) {
		IBlockState blockState = world.getBlockState(pos);
		
		if (blockState == null) {
			return;
		}
		
		if (!LOGS.contains(blockState.getBlock())) {
			return;
		}
		
		world.destroyBlock(pos, true);
		
		cutTree(world, pos.up());
	}
}
