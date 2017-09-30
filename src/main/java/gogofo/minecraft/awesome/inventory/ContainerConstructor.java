package gogofo.minecraft.awesome.inventory;

import gogofo.minecraft.awesome.gui.GuiEntityMachineBlock;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class ContainerConstructor extends ContainerEntityMachineBlock {
    public ContainerConstructor(InventoryPlayer inventoryPlayer, IInventory inventory) {
    	super(inventoryPlayer, inventory);

        for (int j = 0; j < 3; ++j) {
            for (int k = 0; k < 9; ++k) {
                this.addSlotToContainer(new Slot(inventory, super.getCustomSlotCount() + k + j * 9, 8 + k * 18, 18 + j * 18));
            }
        }
    }

    @Override
    protected int getCustomSlotCount() {
        return super.getCustomSlotCount() + 27;
    }
}
