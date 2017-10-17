package gogofo.minecraft.awesome.gui;

import gogofo.minecraft.awesome.gui.features.FeatureControls;
import gogofo.minecraft.awesome.gui.features.FeatureUpgrades;
import gogofo.minecraft.awesome.inventory.ContainerFuser;
import gogofo.minecraft.awesome.tileentity.TileEntityFuser;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiFuser extends AwesomeGui {
	private final TileEntityFuser tileEntityFuser;
	private InventoryPlayer playerInventory;
	
    public GuiFuser(InventoryPlayer playerInventory, 
    				TileEntityFuser tileEntityFuser)
    {
        super(new ContainerFuser(playerInventory, tileEntityFuser),
        	  playerInventory, 
        	  tileEntityFuser);
        
        this.playerInventory = playerInventory;
        this.tileEntityFuser = tileEntityFuser;

		addFeature(new FeatureControls(this, tileEntityFuser));
		addFeature(new FeatureUpgrades(this, inventorySlots));
    }

	@Override
	protected void drawCustomGui() {
		drawSlotsByCustomContainer(inventorySlots);
        drawArrow(80, 38);
        drawFilledArrow(80, 38, tileEntityFuser.getCurrentFusePercent());
	}
}
