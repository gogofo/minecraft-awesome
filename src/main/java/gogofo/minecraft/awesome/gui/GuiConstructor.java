package gogofo.minecraft.awesome.gui;

import gogofo.minecraft.awesome.entity.EntityConstructor;
import gogofo.minecraft.awesome.inventory.ContainerConstructor;
import gogofo.minecraft.awesome.inventory.ContainerFuser;
import gogofo.minecraft.awesome.tileentity.TileEntityFuser;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiConstructor extends AwesomeGui {
	private final EntityConstructor entityConstructor;
	private InventoryPlayer playerInventory;

    public GuiConstructor(InventoryPlayer playerInventory,
						  EntityConstructor entityConstructor)
	{
		super(new ContainerConstructor(playerInventory, entityConstructor),
				playerInventory,
				entityConstructor);
        
        this.playerInventory = playerInventory;
        this.entityConstructor = entityConstructor;
    }

	@Override
	protected void drawCustomGui() {
		drawSlotsByCustomContainer(entityConstructor.createContainer(playerInventory, playerInventory.player));

		drawGlassContainer(80, 30, 50);
	}
}
