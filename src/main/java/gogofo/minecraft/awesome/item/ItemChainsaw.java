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
import net.minecraft.util.BlockPos;
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
	
    private static final Set LOGS = Sets.newHashSet(new Block[] {Blocks.log, Blocks.log2});
	
	public float getStrVsBlock(ItemStack stack, Block block)
    {
		if (getCharge(stack) == 0) {
			return 0;
		}
		
        return block.getMaterial() != Material.wood && block.getMaterial() != Material.plants && block.getMaterial() != Material.vine ? super.getStrVsBlock(stack, block) : 6.0f;
    }
	
	@Override
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, Block blockIn, BlockPos pos,
			EntityLivingBase playerIn) {
		final int chargeCost = 1;
		
		if (worldIn.isRemote) {
			return false;
		}
		
		if (getCharge(stack) < chargeCost) {
			return false;
		}
		
		if (!LOGS.contains(blockIn)) {
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
