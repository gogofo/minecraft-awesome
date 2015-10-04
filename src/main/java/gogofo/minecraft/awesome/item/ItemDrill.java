package gogofo.minecraft.awesome.item;

import java.util.Set;

import com.google.common.collect.Sets;

import net.minecraft.block.Block;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class ItemDrill extends AwesomeItemChargable {
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
		return 1000;
	}

	@Override
	public int getMaxRequiredCharge(ItemStack stack, World worldIn, EntityPlayer playerIn) {
		return 0;
	}

	@Override
	public int onChargeableItemUse(ItemStack stack, World worldIn, EntityPlayer playerIn) {
		MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(worldIn, playerIn, true);

        if (movingobjectposition == null) {
            return 0;
        }
    
        if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
    		BlockPos blockpos = movingobjectposition.getBlockPos().offset(movingobjectposition.sideHit);
    		
    		if (worldIn.isAirBlock(blockpos) && Blocks.torch.canPlaceBlockAt(worldIn, blockpos)) {
    			if (playerIn.inventory.consumeInventoryItem(Item.getItemFromBlock(Blocks.torch))) {
    				IBlockState state = Blocks.torch.getDefaultState().withProperty(BlockTorch.FACING, movingobjectposition.sideHit);
    				worldIn.setBlockState(blockpos, state);
    			}
    		}
        }
		
		
		return 0;
	}
	
	public float getStrVsBlock(ItemStack stack, Block block)
    {
		if (getCharge(stack) == 0) {
			return 0;
		}
		
        return Math.max(Items.diamond_pickaxe.getStrVsBlock(stack, block),
				 		Items.diamond_shovel.getStrVsBlock(stack, block));
    }
	
	@Override
	public boolean canHarvestBlock(Block blockIn) {
		return Items.diamond_pickaxe.canHarvestBlock(blockIn) || 
			   Items.diamond_shovel.canHarvestBlock(blockIn);
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
		
		reduceCharge(stack, chargeCost);
		
		return true;
	}
}
