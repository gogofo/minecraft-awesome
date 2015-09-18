package gogofo.minecraft.awesome.inventory;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class ContainerTeleporter extends AwesomeContainer
{
    public ContainerTeleporter(InventoryPlayer inventoryPlayer, IInventory customInventory)
    {
    	super(inventoryPlayer, customInventory);
    	
        this.addSlotToContainer(new Slot(customInventory, 0, 50, 38));
    }

	@Override
	protected int getCustomSlotCount() {
		return 1;
	}
}
