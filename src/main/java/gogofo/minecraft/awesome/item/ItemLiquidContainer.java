package gogofo.minecraft.awesome.item;

import java.util.List;

import gogofo.minecraft.awesome.interfaces.ILiquidContainer;
import gogofo.minecraft.awesome.tileentity.TileEntityLiquidStorageContainer;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
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
			setLiquidType(stack, Blocks.AIR);
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
			nbt.setInteger("material", Block.getIdFromBlock(Blocks.AIR));
			nbt.setInteger("fill", 0);
			stack.getTagCompound().setTag("liquid_container", nbt);
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if (getLiquidType(stack) != Blocks.AIR) {
			tooltip.add(String.format("Liquid Type: %s", getLiquidType(stack).getLocalizedName()));
			tooltip.add(String.format("Amount: %d", getLiquidFill(stack)));
		}
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		if (getLiquidType(stack) != Blocks.AIR) {
			return String.format("%s (%s)", super.getItemStackDisplayName(stack), getLiquidType(stack).getLocalizedName());
		} else {
			return super.getItemStackDisplayName(stack);
		}
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack stack = playerIn.getHeldItem(handIn);

		RayTraceResult rayTraceResult = this.rayTrace(worldIn, playerIn, false);

        if (rayTraceResult == null)
        {
        	return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
        }
        else
        {
            if (rayTraceResult.typeOfHit == RayTraceResult.Type.BLOCK)
            {
            	BlockPos blockpos = rayTraceResult.getBlockPos();
				onBlockHit(worldIn, playerIn, stack, rayTraceResult, blockpos);
			}
        }
        
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
	}

	private void onBlockHit(World worldIn, EntityPlayer playerIn, ItemStack stack, RayTraceResult rayTraceResult, BlockPos blockpos) {
		TileEntity tileEntity = worldIn.getTileEntity(blockpos);
		if (tileEntity instanceof ILiquidContainer) {
			ILiquidContainer container = (ILiquidContainer) tileEntity;

			interactWithLiquidContainer(playerIn, stack, container);
		} else if (getLiquidFill(stack) > 0) {
			BlockPos sideBlockPos = blockpos.offset(rayTraceResult.sideHit);
			if (tryPlaceContainedLiquid(stack, worldIn, sideBlockPos)) {
				decLiquid(stack, 1);
			}
		}
	}

	public void interactWithLiquidContainer(EntityPlayer playerIn, ItemStack stack, ILiquidContainer container) {
		if (!playerIn.isSneaking()) {
            if (getLiquidFill(stack) > 0) {
                int liquidPlaced = container.tryPlaceLiquid(getLiquidType(stack), 1);
                decLiquid(stack, liquidPlaced);
            }
        } else if (getLiquidFill(stack) < getMaxLiquid()){
            Block liquidType = getLiquidType(stack);
            if (liquidType == Blocks.AIR) {
                liquidType = container.getSubstance();
            }

            int liquidTaken = container.tryTakeLiquid(liquidType, 1);

            if (liquidTaken > 0) {
                incLiquid(stack, liquidTaken);
                setLiquidType(stack, liquidType);
            }
        }
	}

	public boolean tryPlaceContainedLiquid(ItemStack stack, World worldIn, BlockPos pos)
    {
        if (getLiquidType(stack) == Blocks.AIR)
        {
            return false;
        }
        else
        {
            Material material = worldIn.getBlockState(pos).getMaterial();
            boolean flag = !material.isSolid();

            if (!worldIn.isAirBlock(pos) && !flag)
            {
                return false;
            }
            else
            {
                if (worldIn.provider.doesWaterVaporize() && getLiquidType(stack) == Blocks.FLOWING_WATER)
                {
                    int i = pos.getX();
                    int j = pos.getY();
                    int k = pos.getZ();
                    worldIn.playSound((double)((float)i + 0.5F), (double)((float)j + 0.5F), (double)((float)k + 0.5F), SoundEvents.BLOCK_WATERLILY_PLACE, SoundCategory.BLOCKS, 0.5F, 2.6F + (worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) * 0.8F, false);

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
