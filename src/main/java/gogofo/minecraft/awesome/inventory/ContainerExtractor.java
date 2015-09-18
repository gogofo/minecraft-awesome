package gogofo.minecraft.awesome.inventory;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;

public class ContainerExtractor extends AwesomeContainer
{
    public ContainerExtractor(InventoryPlayer inventoryPlayer, IInventory customInventory)
    {
    	super(inventoryPlayer, customInventory);
    	
        this.addSlotToContainer(new SlotExtractor(customInventory, 0, 56, 38));
        this.addSlotToContainer(new AwesomeSlotBigOutput(customInventory, 1, 116, 38));
    }

	@Override
	protected int getCustomSlotCount() {
		return 2;
	}
}
