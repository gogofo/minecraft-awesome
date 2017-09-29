package gogofo.minecraft.awesome.inventory;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;

public class ContainerEntityMachineBlock extends AwesomeContainer {

    public ContainerEntityMachineBlock(InventoryPlayer inventoryPlayer, IInventory inventory)
    {
        super(inventoryPlayer, inventory);

        this.addSlotToContainer(new AwesomeSlot(inventory, 0, 56, 20));
    }

    @Override
    protected int getCustomSlotCount() {
        return 1;
    }
}
