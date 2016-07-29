package gogofo.minecraft.awesome.item;

import java.util.ArrayList;

import javax.annotation.Nullable;

import gogofo.minecraft.awesome.init.Items;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemLiquidPump extends AwesomeItemChargable {

	@Override
	public int getMaxCharge() {
		return 1000;
	}

	@Override
	public int getMaxRequiredCharge(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos,
			EnumFacing side, float hitX, float hitY, float hitZ) {
		return 0;
	}

	@Override
	public int onChargeableItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side,
			float hitX, float hitY, float hitZ) {
       return 0;
	}

	@Override
	public int getMaxRequiredCharge(ItemStack stack, World worldIn, EntityPlayer playerIn) {
		return 10;
	}

	@Override
	public int onChargeableItemUse(ItemStack stack, World worldIn, EntityPlayer playerIn) {
		RayTraceResult rayTraceResult = this.rayTrace(worldIn, playerIn, true);

        if (rayTraceResult == null)
        {
            return 0;
        }
        else
        {
            if (rayTraceResult.typeOfHit == RayTraceResult.Type.BLOCK)
            {
                BlockPos blockpos = rayTraceResult.getBlockPos();

                IBlockState iblockstate = worldIn.getBlockState(blockpos);
                Material material = iblockstate.getMaterial();

                if (material.isLiquid()) {
                	if ((Integer)iblockstate.getValue(BlockLiquid.LEVEL) == 0) {
                		if (harvestBlock(worldIn, playerIn, blockpos)) {
                			return 10;
                		}
                	} 
                } else {
                	blockpos = blockpos.offset(rayTraceResult.sideHit);
                	iblockstate = worldIn.getBlockState(blockpos);
                	material = iblockstate.getMaterial();
                	
                	if (material.isLiquid()) {
                		blockpos = traceSource(new ArrayList<BlockPos>(),
                							   worldIn, 
                							   blockpos,
                							   material);
                		
                		if (harvestBlock(worldIn, playerIn, blockpos)) {
                			return 10;
                		}
                	}
                }
            }

            return 0;
        }
	}
	
	private ItemStack findLiquidContainer(EntityPlayer player)
    {
        if (this.isLiquidContainer(player.getHeldItem(EnumHand.OFF_HAND)))
        {
            return player.getHeldItem(EnumHand.OFF_HAND);
        }
        else if (this.isLiquidContainer(player.getHeldItem(EnumHand.MAIN_HAND)))
        {
            return player.getHeldItem(EnumHand.MAIN_HAND);
        }
        else
        {
            for (int i = 0; i < player.inventory.getSizeInventory(); ++i)
            {
                ItemStack itemstack = player.inventory.getStackInSlot(i);

                if (this.isLiquidContainer(itemstack))
                {
                    return itemstack;
                }
            }

            return null;
        }
    }
	
	private boolean isLiquidContainer(@Nullable ItemStack stack)
    {
        return stack != null && stack.getItem() instanceof ItemLiquidContainer;
    }
	
	private boolean harvestBlock(World worldIn, EntityPlayer playerIn, BlockPos pos) {
		if (pos == null) {
			return false;
		}
		
		IBlockState iblockstate = worldIn.getBlockState(pos);
		if (iblockstate == null) {
			return false;
		}
		
		Material material = iblockstate.getMaterial();
		
		if (findLiquidContainer(playerIn) != null) {
			ItemStack container = selectContainer(playerIn, material);
			
			if (container != null) {
    			ItemLiquidContainer.setLiquidType(container, iblockstate.getBlock());
    			((ItemLiquidContainer)container.getItem()).incLiquid(container, 1);
    			
    			if (!worldIn.isRemote) {
    				worldIn.setBlockToAir(pos);
				}
    			
    			return true;
			}
		}
		
		return false;
	}
	
	private BlockPos traceSource(ArrayList<BlockPos> visited, World worldIn, BlockPos pos, Material material) {
		if (visited.contains(pos)) {
			return null;
		}
		
		visited.add(pos);
		
		IBlockState iblockstate = worldIn.getBlockState(pos);
		if (iblockstate == null || 
				!(iblockstate.getBlock() instanceof BlockLiquid) ||
				iblockstate.getMaterial() != material) {
			return null;
		}
		
		if ((Integer)iblockstate.getValue(BlockLiquid.LEVEL) == 0) {
			return pos;
		}
		
		for (EnumFacing facing : EnumFacing.VALUES) {
			BlockPos curPos = traceSource(visited, worldIn, pos.offset(facing), material);
			
			if (curPos != null) {
				return curPos;
			}
		}
		
		return null;
	}

	public ItemStack selectContainer(EntityPlayer player, Material material) {
		ItemStack selectedContainer = null;
		
		for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
			ItemStack stack = player.inventory.getStackInSlot(i);
			
			if (stack != null && stack.getItem() instanceof ItemLiquidContainer) {
				if (ItemLiquidContainer.getLiquidFill(stack) >= ((ItemLiquidContainer)stack.getItem()).getMaxLiquid()) {
					continue;
				}
				
				Block liquidType = ItemLiquidContainer.getLiquidType(stack);
				
				if (liquidType.getDefaultState().getMaterial() == material) {
					return stack;
				}
				
				if (selectedContainer == null && liquidType.getDefaultState().getMaterial() == Material.AIR) {
					selectedContainer = stack;
				}
			}
		}
		
		return selectedContainer;
	}
}
