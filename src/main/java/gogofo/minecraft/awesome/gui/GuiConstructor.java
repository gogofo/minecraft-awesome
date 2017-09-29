package gogofo.minecraft.awesome.gui;

import gogofo.minecraft.awesome.entity.EntityConstructor;
import gogofo.minecraft.awesome.inventory.ContainerConstructor;
import gogofo.minecraft.awesome.inventory.ContainerFuser;
import gogofo.minecraft.awesome.tileentity.TileEntityFuser;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiConstructor extends GuiEntityMachineBlock {
    public GuiConstructor(InventoryPlayer playerInventory,
						  EntityConstructor entity)
	{
		super(playerInventory, entity);
    }
}
