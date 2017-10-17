package gogofo.minecraft.awesome.inventory;

import gogofo.minecraft.awesome.tileentity.AwesomeTileEntityMachine;
import net.minecraft.entity.player.InventoryPlayer;

public class ContainerTeleporter extends AwesomeMachineContainer
{
    public ContainerTeleporter(InventoryPlayer inventoryPlayer, AwesomeTileEntityMachine customInventory)
    {
    	super(inventoryPlayer, customInventory);
    	
        this.addSlotToContainer(new AwesomeSlot(customInventory, 0, 50, 38));
    }
}
