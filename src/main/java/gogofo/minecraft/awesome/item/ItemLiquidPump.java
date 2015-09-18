package gogofo.minecraft.awesome.item;

import java.util.ArrayList;

import gogofo.minecraft.awesome.init.Items;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
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
		MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(worldIn, playerIn, true);

        if (movingobjectposition == null)
        {
            return 0;
        }
        else
        {
            if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
            {
                BlockPos blockpos = movingobjectposition.getBlockPos();

                IBlockState iblockstate = worldIn.getBlockState(blockpos);
                Material material = iblockstate.getBlock().getMaterial();

                if (material.isLiquid()) {
                	if ((Integer)iblockstate.getValue(BlockLiquid.LEVEL) == 0) {
                		if (harvestBlock(worldIn, playerIn, blockpos)) {
                			return 10;
                		}
                	} 
                } else {
                	blockpos = blockpos.offset(movingobjectposition.sideHit);
                	iblockstate = worldIn.getBlockState(blockpos);
                	material = iblockstate.getBlock().getMaterial();
                	
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
	
	private boolean harvestBlock(World worldIn, EntityPlayer playerIn, BlockPos pos) {
		if (pos == null) {
			return false;
		}
		
		IBlockState iblockstate = worldIn.getBlockState(pos);
		if (iblockstate == null) {
			return false;
		}
		
		Material material = iblockstate.getBlock().getMaterial();
		
		if (playerIn.inventory.hasItem(Items.liquid_container)) {
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
				iblockstate.getBlock().getMaterial() != material) {
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
				
				if (liquidType.getMaterial() == material) {
					return stack;
				}
				
				if (selectedContainer == null && liquidType.getMaterial() == Material.air) {
					selectedContainer = stack;
				}
			}
		}
		
		return selectedContainer;
	}
}
