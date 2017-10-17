package gogofo.minecraft.awesome.gui;

import gogofo.minecraft.awesome.inventory.ContainerSortingPipe;
import gogofo.minecraft.awesome.tileentity.TileEntitySortingPipe;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiSortingPipe extends AwesomeGui {
	public static final int CLEAR_HEIGHT = 140;
	
	private final TileEntitySortingPipe tileEntity;
	private InventoryPlayer playerInventory;
	
    public GuiSortingPipe(InventoryPlayer playerInventory, 
    					  TileEntitySortingPipe tileEntity)
    {
        super(new ContainerSortingPipe(playerInventory, tileEntity),
        	  playerInventory, 
        	  tileEntity);
        
        this.playerInventory = playerInventory;
        this.tileEntity = tileEntity;
    }

	@Override
	protected void drawCustomGui() {
		drawSlotsByCustomContainer(inventorySlots);
	}
	
	@Override
	protected String getTexturePath() {
		return "textures/gui/container/awesome_gui_sorting.png";
	}
	
	@Override
	protected int getClearSectionHeight() {
    	return CLEAR_HEIGHT;
    }
	
	@Override
	protected int getInboxStartY() {
		return CLEAR_HEIGHT;
	}
}
