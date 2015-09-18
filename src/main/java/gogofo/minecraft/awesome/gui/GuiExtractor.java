package gogofo.minecraft.awesome.gui;

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
    }

	@Override
	protected void drawCustomGui() {
		drawSlotsByCustomContainer(tileEntity.createContainer(playerInventory, 
															  playerInventory.player));
        drawArrow(80, 38);
        drawFilledArrow(80, 38, tileEntity.getCurrentExtractPercent());
	}
}
