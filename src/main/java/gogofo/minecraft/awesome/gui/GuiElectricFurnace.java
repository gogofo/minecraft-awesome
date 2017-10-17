package gogofo.minecraft.awesome.gui;

import gogofo.minecraft.awesome.gui.features.FeatureControls;
import gogofo.minecraft.awesome.gui.features.FeatureUpgrades;
import gogofo.minecraft.awesome.inventory.ContainerElectricFurnace;
import gogofo.minecraft.awesome.tileentity.TileEntityElectricFurnace;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiElectricFurnace extends AwesomeGui {
	
	private final TileEntityElectricFurnace tileEntityElectricFurnace;
	private InventoryPlayer playerInventory;
	
    public GuiElectricFurnace(InventoryPlayer playerInventory, 
							  TileEntityElectricFurnace tileEntityElectricFurnace)
    {
        super(new ContainerElectricFurnace(playerInventory, tileEntityElectricFurnace),
        	  playerInventory, 
        	  tileEntityElectricFurnace);
        
        this.playerInventory = playerInventory;
        this.tileEntityElectricFurnace = tileEntityElectricFurnace;

		addFeature(new FeatureControls(this, tileEntityElectricFurnace));
		addFeature(new FeatureUpgrades(this, inventorySlots));
    }

	@Override
	protected void drawCustomGui() {
		drawSlotsByCustomContainer(inventorySlots);
        drawArrow(80, 38);
        drawFilledArrow(80, 38, tileEntityElectricFurnace.getCurrentCookedPercent());
	}
}
