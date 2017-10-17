package gogofo.minecraft.awesome.inventory;

import gogofo.minecraft.awesome.tileentity.AwesomeTileEntityMachine;
import net.minecraft.entity.player.InventoryPlayer;

public class ContainerGenerator extends AwesomeMachineContainer
{
    public ContainerGenerator(InventoryPlayer inventoryPlayer, AwesomeTileEntityMachine generatorInventory)
    {
    	super(inventoryPlayer, generatorInventory);
    	
        this.addSlotToContainer(new SlotGeneratorFuel(generatorInventory, 0, 56, 38));
        this.addSlotToContainer(new AwesomeSlotBigOutput(generatorInventory, 1, 116, 38));
    }
}
