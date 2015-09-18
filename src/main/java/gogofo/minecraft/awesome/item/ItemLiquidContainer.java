package gogofo.minecraft.awesome.item;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class ItemLiquidContainer extends Item {
	public ItemLiquidContainer() {
		setMaxStackSize(1);
	}
	
	@Override
	public int getMaxDamage() {
		return getMaxLiquid();
	}
	
	@Override
	public int getDamage(ItemStack stack) {
		return getMaxDamage() - getLiquidFill(stack);
	}
	
	@Override
	public boolean isDamaged(ItemStack stack) {
		return true;
	}
	
	public int getMaxLiquid() {
		return 20;
	}
	
	public void incLiquid(ItemStack stack, int amount) {
		setLiquidFill(stack, getLiquidFill(stack) + amount);
	}
	
	public void decLiquid(ItemStack stack, int amount) {
		setLiquidFill(stack, getLiquidFill(stack) - amount);
	}
	
	public static void setLiquidType(ItemStack stack, Block block) {
		ensureHasTag(stack);
		
		((NBTTagCompound)stack.getTagCompound().getTag("liquid_container")).setInteger("material", Block.getIdFromBlock(block));
	}
	
	public static Block getLiquidType(ItemStack stack) {
		ensureHasTag(stack);
		
		return Block.getBlockById(((NBTTagCompound)stack.getTagCompound().getTag("liquid_container")).getInteger("material"));
	}
	
	public static void setLiquidFill(ItemStack stack, int amount) {
		ensureHasTag(stack);
		
		((NBTTagCompound)stack.getTagCompound().getTag("liquid_container")).setInteger("fill", amount);
		
		if (amount == 0) {
			setLiquidType(stack, Blocks.air);
		}
	}
	
	public static int getLiquidFill(ItemStack stack) {
		ensureHasTag(stack);
		
		return ((NBTTagCompound)stack.getTagCompound().getTag("liquid_container")).getInteger("fill");
	}
	
	private static void ensureHasTag(ItemStack stack) {
		if (stack.getTagCompound() == null) {
			stack.setTagCompound(new NBTTagCompound());
		}
		
		if (stack.getTagCompound().getTag("liquid_container") == null) {
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setInteger("material", Block.getIdFromBlock(Blocks.air));
			nbt.setInteger("fill", 0);
			stack.getTagCompound().setTag("liquid_container", nbt);
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced) {
		if (getLiquidType(stack) != Blocks.air) {
			tooltip.add(String.format("Liquid Type: %s", getLiquidType(stack).getLocalizedName()));
			tooltip.add(String.format("Amount: %d", getLiquidFill(stack)));
		}
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		if (getLiquidType(stack) != Blocks.air) {
			return String.format("%s (%s)", super.getItemStackDisplayName(stack), getLiquidType(stack).getLocalizedName());
		} else {
			return super.getItemStackDisplayName(stack);
		}
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World worldIn, EntityPlayer playerIn) {
		if (playerIn.isSneaking()) {
			if (getLiquidFill(stack) > 0) {
				setLiquidFill(stack, 0);
				return stack;
			}
		}
		
		MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(worldIn, playerIn, false);

        if (movingobjectposition == null)
        {
            return stack;
        }
        else
        {
            if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
            {
            	BlockPos blockpos = movingobjectposition.getBlockPos();
            	
            	if (getLiquidFill(stack) > 0) {
            		BlockPos sideBlockPos = blockpos.offset(movingobjectposition.sideHit);
            		if (tryPlaceContainedLiquid(stack, worldIn, sideBlockPos)) {
            			decLiquid(stack, 1);
            		}
            	}
            }
        }
        
        return stack;
	}
	
	public boolean tryPlaceContainedLiquid(ItemStack stack, World worldIn, BlockPos pos)
    {
        if (getLiquidType(stack) == Blocks.air)
        {
            return false;
        }
        else
        {
            Material material = worldIn.getBlockState(pos).getBlock().getMaterial();
            boolean flag = !material.isSolid();

            if (!worldIn.isAirBlock(pos) && !flag)
            {
                return false;
            }
            else
            {
                if (worldIn.provider.doesWaterVaporize() && getLiquidType(stack) == Blocks.flowing_water)
                {
                    int i = pos.getX();
                    int j = pos.getY();
                    int k = pos.getZ();
                    worldIn.playSoundEffect((double)((float)i + 0.5F), (double)((float)j + 0.5F), (double)((float)k + 0.5F), "random.fizz", 0.5F, 2.6F + (worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) * 0.8F);

                    for (int l = 0; l < 8; ++l)
                    {
                        worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, (double)i + Math.random(), (double)j + Math.random(), (double)k + Math.random(), 0.0D, 0.0D, 0.0D, new int[0]);
                    }
                }
                else
                {
                    if (!worldIn.isRemote && flag && !material.isLiquid())
                    {
                        worldIn.destroyBlock(pos, true);
                    }

                    worldIn.setBlockState(pos, getLiquidType(stack).getDefaultState(), 3);
                }

                return true;
            }
        }
    }
}
