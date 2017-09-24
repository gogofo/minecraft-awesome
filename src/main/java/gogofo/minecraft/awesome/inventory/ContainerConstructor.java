package gogofo.minecraft.awesome.inventory;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;

public class ContainerConstructor extends AwesomeContainer {
    public ContainerConstructor(InventoryPlayer inventoryPlayer, IInventory generatorInventory)
    {
    	super(inventoryPlayer, generatorInventory);
    }

	@Override
	protected int getCustomSlotCount() {
		return 0;
	}
}
