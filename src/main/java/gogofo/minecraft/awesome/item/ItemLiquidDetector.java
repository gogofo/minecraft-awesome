package gogofo.minecraft.awesome.item;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class ItemLiquidDetector extends AwesomeItemChargable {
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
	public int getMaxCharge() {
		return 1000;
	}

	@Override
	public int getMaxRequiredCharge(ItemStack stack, World worldIn, EntityPlayer playerIn) {
		return 1;
	}

	@Override
	public int onChargeableItemUse(ItemStack stack, World worldIn, EntityPlayer playerIn) {
		BlockPos playerPos = playerIn.getPosition();

		int liquidSum = 0;

		for (int x = -8; x <= 8; x++) {
			for (int z = -8; z <= 8; z++) {
				liquidSum += sumLiquidBelowPos(worldIn, playerPos.add(x, 0, z));
			}
		}

		if (!worldIn.isRemote) {
			playerIn.sendMessage(new TextComponentString(String.format("Liquid value: %d", liquidSum)));
		}

		return 1;
	}

	private int sumLiquidBelowPos(World world, BlockPos pos) {
		int liquidSum = 0;

		while (pos.getY() > 0) {
			liquidSum += getValueForBlock(world.getBlockState(pos).getBlock());
			pos = pos.offset(EnumFacing.DOWN);
		}

		return liquidSum;
	}

	private int getValueForBlock(Block block) {
		if (block == Blocks.WATER) {
			return 1;
		} else if (block == Blocks.LAVA) {
			return 3;
		} else if (block == gogofo.minecraft.awesome.init.Blocks.oil) {
			return 5;
		}

		return 0;
	}
}
