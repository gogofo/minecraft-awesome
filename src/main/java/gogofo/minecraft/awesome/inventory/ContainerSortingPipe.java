package gogofo.minecraft.awesome.inventory;

import gogofo.minecraft.awesome.gui.GuiSortingPipe;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class ContainerSortingPipe extends AwesomeContainer
{
    public ContainerSortingPipe(InventoryPlayer inventoryPlayer, IInventory customInventory)
    {
    	super(inventoryPlayer, customInventory, GuiSortingPipe.CLEAR_HEIGHT);
    	
    	for (int j = 0; j < 6; ++j) {
            for (int k = 0; k < 9; ++k) {
                this.addSlotToContainer(new Slot(customInventory, 27 + k + j * 9, 8 + k * 18, 18 + j * 18));
            }
        }
    }

	@Override
	protected int getCustomSlotCount() {
		return 54;
	}
}
