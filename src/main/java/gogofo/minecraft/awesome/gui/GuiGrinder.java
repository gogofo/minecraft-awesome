package gogofo.minecraft.awesome.gui;

import gogofo.minecraft.awesome.AwesomeMod;
import gogofo.minecraft.awesome.inventory.ContainerFuser;
import gogofo.minecraft.awesome.inventory.ContainerGrinder;
import gogofo.minecraft.awesome.tileentity.TileEntityGrinder;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

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
    }

	@Override
	protected void drawCustomGui() {
		drawSlotsByCustomContainer(tileEntityGrinder.createContainer(playerInventory, 
																	   playerInventory.player));
        drawArrow(80, 38);
        drawFilledArrow(80, 38, tileEntityGrinder.getCurrentGrindPercent());
	}
}
