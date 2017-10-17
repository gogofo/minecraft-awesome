package gogofo.minecraft.awesome.gui;

import gogofo.minecraft.awesome.gui.features.FeatureControls;
import gogofo.minecraft.awesome.inventory.ContainerGrinder;
import gogofo.minecraft.awesome.tileentity.TileEntityGrinder;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiGrinder extends AwesomeGui {
	
	private final TileEntityGrinder tileEntityGrinder;
	private InventoryPlayer playerInventory;
	
    public GuiGrinder(InventoryPlayer playerInventory, 
    					TileEntityGrinder tileEntityGrinder)
    {
        super(new ContainerGrinder(playerInventory, tileEntityGrinder),
        	  playerInventory, 
        	  tileEntityGrinder);
        
        this.playerInventory = playerInventory;
        this.tileEntityGrinder = tileEntityGrinder;

		addFeature(new FeatureControls(this, tileEntityGrinder));
    }

	@Override
	protected void drawCustomGui() {
		drawSlotsByCustomContainer(tileEntityGrinder.createContainer(playerInventory, 
																	   playerInventory.player));
        drawArrow(80, 38);
        drawFilledArrow(80, 38, tileEntityGrinder.getCurrentGrindPercent());
	}
}
