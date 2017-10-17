package gogofo.minecraft.awesome.gui;

import gogofo.minecraft.awesome.gui.features.FeatureControls;
import gogofo.minecraft.awesome.gui.features.FeatureUpgrades;
import gogofo.minecraft.awesome.inventory.ContainerGrinder;
import gogofo.minecraft.awesome.tileentity.TileEntityExtractor;
import gogofo.minecraft.awesome.tileentity.TileEntityGrinder;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiExtractor extends AwesomeGui {
	
	private final TileEntityExtractor tileEntity;
	private InventoryPlayer playerInventory;
	
    public GuiExtractor(InventoryPlayer playerInventory, 
    					TileEntityExtractor tileEntity)
    {
        super(new ContainerGrinder(playerInventory, tileEntity),
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
        drawArrow(80, 38);
        drawFilledArrow(80, 38, tileEntity.getCurrentExtractPercent());
	}
}
