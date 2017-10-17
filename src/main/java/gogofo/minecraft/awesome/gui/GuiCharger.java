package gogofo.minecraft.awesome.gui;

import gogofo.minecraft.awesome.gui.features.FeatureControls;
import gogofo.minecraft.awesome.gui.features.FeatureUpgrades;
import gogofo.minecraft.awesome.inventory.ContainerCharger;
import gogofo.minecraft.awesome.tileentity.TileEntityCharger;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiCharger extends AwesomeGui {
	
	private final TileEntityCharger tileEntity;
	private InventoryPlayer playerInventory;
	
    public GuiCharger(InventoryPlayer playerInventory, 
    				  TileEntityCharger tileEntity)
    {
        super(new ContainerCharger(playerInventory, tileEntity),
        	  playerInventory, 
        	  tileEntity);
        
        this.playerInventory = playerInventory;
        this.tileEntity = tileEntity;

        addFeature(new FeatureControls(this, tileEntity));
        addFeature(new FeatureUpgrades(this, inventorySlots));
    }

	@Override
	protected void drawCustomGui() {
		drawSlotsByCustomContainer(inventorySlots);
	}
}
