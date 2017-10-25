package gogofo.minecraft.awesome.interfaces;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public interface SlotCreator {
    Slot createSlot(IInventory inventory, int index, int x, int y);
}
