package gogofo.minecraft.awesome.interfaces;

import net.minecraft.item.ItemStack;

public interface IAwesomeChargable {
	public int getCharge(ItemStack stack);
	public int getMaxCharge();
	public void charge(ItemStack stack, int charge);
}
