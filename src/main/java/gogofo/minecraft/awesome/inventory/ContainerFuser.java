package gogofo.minecraft.awesome.inventory;

import gogofo.minecraft.awesome.tileentity.AwesomeTileEntityMachine;
import net.minecraft.entity.player.InventoryPlayer;

public class ContainerFuser extends AwesomeMachineContainer {
    public ContainerFuser(InventoryPlayer inventoryPlayer, AwesomeTileEntityMachine inventory)
    {
    	super(inventoryPlayer, inventory);

        addAwesomeSlotGrid(0, 22, 17, 3, 3);

        this.addSlotToContainer(new AwesomeSlotBigOutput(inventory, 9, 116, 38));
    }
}
