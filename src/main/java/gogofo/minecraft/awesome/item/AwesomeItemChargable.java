package gogofo.minecraft.awesome.item;

import java.util.List;

import gogofo.minecraft.awesome.interfaces.IAwesomeChargable;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class AwesomeItemChargable extends Item implements IAwesomeChargable {
	public abstract int getMaxRequiredCharge(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side,float hitX, float hitY, float hitZ);
	public abstract int onChargeableItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side,float hitX, float hitY, float hitZ);
	public abstract int getMaxRequiredCharge(ItemStack stack, World worldIn, EntityPlayer playerIn);
	public abstract int onChargeableItemUse(ItemStack stack, World worldIn, EntityPlayer playerIn);
	
	public AwesomeItemChargable() {
		setMaxStackSize(1);
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack stack = playerIn.getHeldItem(hand);
		int maxRequiredCharge = getMaxRequiredCharge(stack, playerIn, worldIn, pos, facing, hitX, hitY, hitZ);
		int charge = getTagCharge(stack);
		if (charge >= maxRequiredCharge) {
			int usedCharge = onChargeableItemUse(stack, playerIn, worldIn, pos, facing, hitX, hitY, hitZ);
			charge -= usedCharge;
			setTagCharge(stack, charge);
		}
		
		return EnumActionResult.PASS;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack stack = playerIn.getHeldItem(handIn);
		int maxRequiredCharge = getMaxRequiredCharge(stack, worldIn, playerIn);
		int charge = getTagCharge(stack);
		if (charge >= maxRequiredCharge) {
			int usedCharge = onChargeableItemUse(stack, worldIn, playerIn);
			charge -= usedCharge;
			setTagCharge(stack, charge);
		}

		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
	}
	
	@Override
	public int getCharge(ItemStack stack) {
		return getTagCharge(stack);
	}
	
	@Override
	public int charge(ItemStack stack, int charge) {
		int newCharge = getTagCharge(stack);
		newCharge += charge;

		int maxCharge = getMaxCharge();
		if (newCharge > maxCharge) {
			charge -= newCharge - maxCharge;
			newCharge = maxCharge;
		}

		setTagCharge(stack, newCharge);

		return charge;
	}
	
	private void ensureHasTag(ItemStack stack) {
		if (stack.getTagCompound() == null) {
			stack.setTagCompound(new NBTTagCompound());
		}
		
		if (stack.getTagCompound().getTag("chargeable") == null) {
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setInteger("charge", 0);
			stack.getTagCompound().setTag("chargeable", nbt);
		}
	}
	
	private int getTagCharge(ItemStack stack) {
		if (hasTag(stack)) {
			return ((NBTTagCompound) stack.getTagCompound().getTag("chargeable")).getInteger("charge");
		} else {
			return 0;
		}
	}
	
	private void setTagCharge(ItemStack stack, int charge) {
		ensureHasTag(stack);
		((NBTTagCompound) stack.getTagCompound().getTag("chargeable")).setInteger("charge", charge);
	}

	private boolean hasTag(ItemStack stack) {
		if (stack.getTagCompound() == null) {
			return false;
		}

		if (stack.getTagCompound().getTag("chargeable") == null) {
			return false;
		}

		return true;
	}
	
	protected void reduceCharge(ItemStack stack, int charge) {
		int newCharge = getCharge(stack) - charge;
		
		if (newCharge < 0) {
			newCharge = 0;
		}
		
		setTagCharge(stack, newCharge);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(String.format("Charge %d", getTagCharge(stack)));
	}
	
	@Override
	public boolean isDamaged(ItemStack stack) {
		return hasTag(stack);
	}
	
	@Override
	public int getDamage(ItemStack stack) {
		return getMaxCharge() - getTagCharge(stack);
	}
	
	@Override
	public int getMaxDamage(ItemStack stack) {
		return getMaxCharge();
	}
}
