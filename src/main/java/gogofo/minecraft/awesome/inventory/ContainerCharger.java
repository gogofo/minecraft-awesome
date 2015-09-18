package gogofo.minecraft.awesome.inventory;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;

public class ContainerCharger extends AwesomeContainer
{
    public ContainerCharger(InventoryPlayer inventoryPlayer, IInventory customInventory)
    {
    	super(inventoryPlayer, customInventory);
    	
        this.addSlotToContainer(new SlotCharger(customInventory, 0, 50, 38));
    }

	@Override
	protected int getCustomSlotCount() {
		return 1;
	}
}
