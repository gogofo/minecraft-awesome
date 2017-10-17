package gogofo.minecraft.awesome.gui;

import gogofo.minecraft.awesome.gui.features.FeatureControls;
import gogofo.minecraft.awesome.inventory.ContainerTeleporter;
import gogofo.minecraft.awesome.tileentity.TileEntityTeleporter;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiTeleporter extends AwesomeGui {
	
	private final TileEntityTeleporter tileEntity;
	private InventoryPlayer playerInventory;
	
    public GuiTeleporter(InventoryPlayer playerInventory, 
    				  	TileEntityTeleporter tileEntity)
    {
        super(new ContainerTeleporter(playerInventory, tileEntity),
        	  playerInventory, 
        	  tileEntity);
        
        this.playerInventory = playerInventory;
        this.tileEntity = tileEntity;

		addFeature(new FeatureControls(this, tileEntity));
    }

	@Override
	protected void drawCustomGui() {
		drawSlotsByCustomContainer(inventorySlots);
	}
}
