package gogofo.minecraft.awesome.interfaces;

import net.minecraft.util.EnumFacing;

public interface IConfigurableSidedInventory extends IPositionedSidedInventory {
    void addSlotToFace(Integer slot, EnumFacing face);
    void removeSlotFromFace(Integer slot, EnumFacing face);
}
