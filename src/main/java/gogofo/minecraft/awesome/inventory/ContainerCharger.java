package gogofo.minecraft.awesome.inventory;

import gogofo.minecraft.awesome.tileentity.AwesomeTileEntityMachine;
import net.minecraft.entity.player.InventoryPlayer;

public class ContainerCharger extends AwesomeMachineContainer
{
    public ContainerCharger(InventoryPlayer inventoryPlayer, AwesomeTileEntityMachine customInventory)
    {
    	super(inventoryPlayer, customInventory);
    	
        this.addSlotToContainer(new SlotCharger(customInventory, 0, 50, 38));
    }
}
