package gogofo.minecraft.awesome.inventory;

import gogofo.minecraft.awesome.tileentity.AwesomeTileEntityMachine;
import net.minecraft.entity.player.InventoryPlayer;

public class ContainerElectricFurnace extends AwesomeMachineContainer
{
    public ContainerElectricFurnace(InventoryPlayer inventoryPlayer, AwesomeTileEntityMachine customInventory)
    {
    	super(inventoryPlayer, customInventory);
    	
        this.addSlotToContainer(new AwesomeSlot(customInventory, 0, 56, 38));
        this.addSlotToContainer(new AwesomeSlotBigOutput(customInventory, 1, 116, 38));
    }
}
