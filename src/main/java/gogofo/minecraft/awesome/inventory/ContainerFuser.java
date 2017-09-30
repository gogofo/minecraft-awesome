package gogofo.minecraft.awesome.inventory;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;

public class ContainerFuser extends AwesomeContainer {
    public ContainerFuser(InventoryPlayer inventoryPlayer, IInventory inventory)
    {
    	super(inventoryPlayer, inventory);

        addAwesomeSlotGrid(0, 22, 17, 3, 3);

        this.addSlotToContainer(new AwesomeSlotBigOutput(inventory, 9, 116, 38));
    }

	@Override
	protected int getCustomSlotCount() {
		return 10;
	}
}
