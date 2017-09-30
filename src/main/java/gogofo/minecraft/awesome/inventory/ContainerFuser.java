package gogofo.minecraft.awesome.inventory;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;

public class ContainerFuser extends AwesomeContainer {
    public ContainerFuser(InventoryPlayer inventoryPlayer, IInventory generatorInventory)
    {
    	super(inventoryPlayer, generatorInventory);
    	
        this.addSlotToContainer(new AwesomeSlot(generatorInventory, 0, 56, 20));
        this.addSlotToContainer(new AwesomeSlot(generatorInventory, 1, 56, 45));
        this.addSlotToContainer(new AwesomeSlotBigOutput(generatorInventory, 2, 116, 38));
    }

	@Override
	protected int getCustomSlotCount() {
		return 3;
	}
}
