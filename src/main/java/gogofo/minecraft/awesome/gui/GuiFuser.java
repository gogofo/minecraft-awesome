package gogofo.minecraft.awesome.gui;

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
    }

	@Override
	protected void drawCustomGui() {
		drawSlotsByCustomContainer(tileEntityFuser.createContainer(playerInventory, 
																   playerInventory.player));
        drawArrow(80, 38);
        drawFilledArrow(80, 38, tileEntityFuser.getCurrentFusePercent());
	}
}
