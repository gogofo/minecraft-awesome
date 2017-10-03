package gogofo.minecraft.awesome.item;

import gogofo.minecraft.awesome.colorize.IDynamicColoredObjected;
import gogofo.minecraft.awesome.init.Ores;
import gogofo.minecraft.awesome.interfaces.ILiquidContainer;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;
import java.util.List;

public class ItemLiquidContainer extends Item implements IDynamicColoredObjected{
	public ItemLiquidContainer() {
		setMaxStackSize(1);
		setContainerItem(this);
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
		return getLiquidFill(stack) > 0;
	}

	@Override
	public ItemStack getContainerItem(ItemStack itemStack) {
		if (getLiquidFill(itemStack) >= Fluid.BUCKET_VOLUME) {
			decLiquid(itemStack, Fluid.BUCKET_VOLUME);
		}

		NBTTagCompound compound = new NBTTagCompound();
		itemStack.writeToNBT(compound);
		
		return new ItemStack(compound);
	}

	public int getMaxLiquid() {
		return 5000;
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
			tooltip.add(String.format("Amount: %dmB", getLiquidFill(stack)));
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
		} else if (getLiquidFill(stack) >= Fluid.BUCKET_VOLUME) {
			BlockPos sideBlockPos = blockpos.offset(rayTraceResult.sideHit);
			if (tryPlaceContainedLiquid(stack, worldIn, sideBlockPos)) {
				decLiquid(stack, Fluid.BUCKET_VOLUME);
			}
		}
	}

	public void interactWithLiquidContainer(EntityPlayer playerIn, ItemStack stack, ILiquidContainer container) {
		if (!playerIn.isSneaking()) {
			int amountToPlace = Math.min(getLiquidFill(stack), Fluid.BUCKET_VOLUME);
			int liquidPlaced = container.tryPlaceLiquid(getLiquidType(stack), amountToPlace);
			decLiquid(stack, liquidPlaced);
        } else if (getLiquidFill(stack) < getMaxLiquid()){
            Block liquidType = getLiquidType(stack);
            if (liquidType == Blocks.AIR) {
                liquidType = container.getSubstance();
            }

            int amountToTake = Math.min(getMaxLiquid() - getLiquidFill(stack), Fluid.BUCKET_VOLUME);
            int liquidTaken = container.tryTakeLiquid(liquidType, amountToTake);

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

	@Override
	public int getColorForBlock(IBlockState state, IBlockAccess worldIn, BlockPos pos, int tintIndex) {
		return -1;
	}

	@Override
	public int getColorForItem(ItemStack stack, int tintIndex) {
		if (tintIndex == 1) {
			Fluid fluid = FluidRegistry.lookupFluidForBlock(getLiquidType(stack));
			if (fluid == null) {
				return 0x9c6c2e;
			}

			if (fluid.getBlock() == Blocks.WATER) {
				return 0x2B3CF4;
			} else if (fluid.getBlock() == Blocks.LAVA) {
				return 0xC74209;
			}

			return fluid.getColor();
		} else {
			return -1;
		}
	}
}
