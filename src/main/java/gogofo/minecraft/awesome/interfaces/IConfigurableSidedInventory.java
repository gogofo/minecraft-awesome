package gogofo.minecraft.awesome.interfaces;

import net.minecraft.inventory.ISidedInventory;
import net.minecraft.util.EnumFacing;

public interface IConfigurableSidedInventory extends ISidedInventory {
	public void addSlotToFace(Integer slot, EnumFacing face);
	public void removeSlotFromFace(Integer slot, EnumFacing face);
}
