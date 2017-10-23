package gogofo.minecraft.awesome.gui;

import gogofo.minecraft.awesome.entity.EntityDigger;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiDigger extends GuiEntityMachineBlock {
    public GuiDigger(InventoryPlayer playerInventory,
                     EntityDigger entity)
	{
		super(playerInventory, entity);
    }
}
