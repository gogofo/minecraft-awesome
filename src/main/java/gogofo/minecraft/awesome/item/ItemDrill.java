package gogofo.minecraft.awesome.item;

import java.util.Set;

import javax.annotation.Nullable;

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
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
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
	
	private ItemStack findTorch(EntityPlayer player)
    {
        if (this.isTorch(player.getHeldItem(EnumHand.OFF_HAND)))
        {
            return player.getHeldItem(EnumHand.OFF_HAND);
        }
        else if (this.isTorch(player.getHeldItem(EnumHand.MAIN_HAND)))
        {
            return player.getHeldItem(EnumHand.MAIN_HAND);
        }
        else
        {
            for (int i = 0; i < player.inventory.getSizeInventory(); ++i)
            {
                ItemStack itemstack = player.inventory.getStackInSlot(i);

                if (this.isTorch(itemstack))
                {
                    return itemstack;
                }
            }

            return null;
        }
    }
	
	private boolean isTorch(@Nullable ItemStack stack)
    {
        return stack != null && Item.getItemFromBlock(Blocks.TORCH) == stack.getItem();
    }

	@Override
	public int onChargeableItemUse(ItemStack stack, World worldIn, EntityPlayer playerIn) {
		RayTraceResult rayTraceResult = this.rayTrace(worldIn, playerIn, true);

        if (rayTraceResult == null) {
            return 0;
        }
    
        if (rayTraceResult.typeOfHit == RayTraceResult.Type.BLOCK) {
    		BlockPos blockpos = rayTraceResult.getBlockPos().offset(rayTraceResult.sideHit);
    		
    		if (worldIn.isAirBlock(blockpos) && Blocks.TORCH.canPlaceBlockAt(worldIn, blockpos)) {
    			ItemStack torchStack = findTorch(playerIn);
    			if (torchStack != null && torchStack.stackSize > 0) {
    				--torchStack.stackSize;

                    if (torchStack.stackSize == 0)
                    {
                    	playerIn.inventory.deleteStack(torchStack);
                    }
                    
    				IBlockState state = Blocks.TORCH.getDefaultState().withProperty(BlockTorch.FACING, rayTraceResult.sideHit);
    				worldIn.setBlockState(blockpos, state);
    			}
    		}
        }
		
		
		return 0;
	}
	
	@Override
	public float getStrVsBlock(ItemStack stack, IBlockState blockState)
    {
		if (getCharge(stack) == 0) {
			return 0;
		}
		
        return Math.max(Items.DIAMOND_PICKAXE.getStrVsBlock(stack, blockState),
				 		Items.DIAMOND_SHOVEL.getStrVsBlock(stack, blockState));
    }
	
	@Override
	public boolean canHarvestBlock(IBlockState blockState) {
		return Items.DIAMOND_PICKAXE.canHarvestBlock(blockState) || 
			   Items.DIAMOND_SHOVEL.canHarvestBlock(blockState);
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
		
		reduceCharge(stack, chargeCost);
		
		return true;
	}
}
