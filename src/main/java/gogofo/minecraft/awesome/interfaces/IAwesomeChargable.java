package gogofo.minecraft.awesome.interfaces;

import net.minecraft.item.ItemStack;

public interface IAwesomeChargable {
	int getCharge(ItemStack stack);
	int getMaxCharge();

	/**
	 * Charge the item
	 * @param stack stack to charge
	 * @param charge amount to charge with
	 * @return the actual amount charged
	 */
	int charge(ItemStack stack, int charge);
}
