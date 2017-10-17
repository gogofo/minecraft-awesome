package gogofo.minecraft.awesome.inventory;

import gogofo.minecraft.awesome.tileentity.AwesomeTileEntityMachine;
import net.minecraft.entity.player.InventoryPlayer;

public class ContainerGrinder extends AwesomeMachineContainer
{
    public ContainerGrinder(InventoryPlayer inventoryPlayer, AwesomeTileEntityMachine customInventory)
    {
    	super(inventoryPlayer, customInventory);
    	
        this.addSlotToContainer(new SlotGrinder(customInventory, 0, 56, 38));
        this.addSlotToContainer(new AwesomeSlotBigOutput(customInventory, 1, 116, 38));
    }
}
