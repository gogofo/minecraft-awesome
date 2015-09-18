package gogofo.minecraft.awesome.gui;

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
    }

	@Override
	protected void drawCustomGui() {
		drawSlotsByCustomContainer(tileEntityElectricFurnace.createContainer(playerInventory, 
																	   		 playerInventory.player));
        drawArrow(80, 38);
        drawFilledArrow(80, 38, tileEntityElectricFurnace.getCurrentCookedPercent());
	}
}
