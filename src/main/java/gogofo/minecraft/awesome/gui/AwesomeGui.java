package gogofo.minecraft.awesome.gui;

import gogofo.minecraft.awesome.AwesomeMod;
import gogofo.minecraft.awesome.inventory.AwesomeSlotBig;
import gogofo.minecraft.awesome.inventory.ContainerGenerator;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;

public abstract class AwesomeGui extends GuiContainer {
	private static final int INBOX_START_Y = 131;
	private static final int INBOX_HIGHT = 83;
	
	private ResourceLocation guiTextures;
    private final InventoryPlayer playerInventory;
    private final IInventory customInventory;
    
    
    protected abstract void drawCustomGui();
    

    public AwesomeGui(Container container,
    				  InventoryPlayer playerInventory, 
    				  IInventory customInventory)
    {
        super(container);
        this.playerInventory = playerInventory;
        this.customInventory = customInventory;
        
        guiTextures = new ResourceLocation(AwesomeMod.MODID + ":" + getTexturePath());
        
        this.ySize = getClearSectionHeight() + INBOX_HIGHT;
    }
    
    protected String getTexturePath() {
    	return "textures/gui/container/awesome_gui_basic.png";
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        String title = customInventory.getDisplayName().getUnformattedText();
        fontRendererObj.drawString(title, xSize/2-fontRendererObj
              .getStringWidth(title)/2, 6, 4210752);
        
        fontRendererObj.drawString(playerInventory.getDisplayName()
              .getUnformattedText(), 8, ySize - 96 + 2, 4210752);
    }

    /**
     * Args : renderPartialTicks, mouseX, mouseY
     */
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, 
          int mouseX, int mouseY)
    {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bindTexture(guiTextures);
        
        drawEmptyBackground();
        drawInventory();
        drawCustomGui();
    }
    
    private int guiX() {
    	return (width - xSize) / 2;
    }
    
    private int guiY() {
    	return (height - ySize) / 2;
    }
    
    
    private void drawEmptyBackground() {
    	drawTexturedModalRect(guiX(), guiY(), 
				  0, 0, 
			  	  xSize, ySize - INBOX_HIGHT);
    }
    
    private void drawInventory() {
    	drawTexturedModalRect(guiX(), guiY() + (ySize-INBOX_HIGHT), 
				  0, getInboxStartY(), 
			  	  xSize, INBOX_HIGHT);
    }
    
    protected int getInboxStartY() {
    	return INBOX_START_Y;
    }
    
    protected int getClearSectionHeight() {
    	return 166-INBOX_HIGHT;
    }
    
    protected void drawSlot(int x, int y) {
    	drawTexturedModalRect(guiX() + x, guiY() + y, 
				  176, 0, 
				  20, 20);
    }
    
    protected void drawSlotsByCustomContainer(Container container) {
    	int slots = container.getInventory().size();
    	
    	for (int i = 0; i < slots; i++) {
    		Slot slot = container.getSlot(i);
    		
    		if (slot instanceof AwesomeSlotBig) {
    			drawBigSlot(slot.xDisplayPosition-6, slot.yDisplayPosition-6);
    		} else {
    			drawSlot(slot.xDisplayPosition-2, slot.yDisplayPosition-2);
    		}
    	}
    }
    
    protected void drawBigSlot(int x, int y) {
    	drawTexturedModalRect(guiX() + x, guiY() + y, 
				  176, 20, 
				  28, 28);
    }
    
    protected void drawArrow(int x, int y) {
    	drawTexturedModalRect(guiX() + x, guiY() + y, 
				  176, 48, 
				  22, 15);
    }
    
    protected void drawFilledArrow(int x, int y) {
    	drawFilledArrow(x, y, 1.0f);
    }
    
    protected void drawFilledArrow(int x, int y, float percent) {
    	drawTexturedModalRect(guiX() + x, guiY() + y, 
				  176, 63,
				  Math.round(22*percent), 15);
    }
    
    protected void drawHorizontalLine(int x, int y, int len) {
    	final int MAX_LEN = 28;
  
    	int offset = 0;
    	
    	while (len > 0) {
	    	drawTexturedModalRect(guiX() + x + offset, guiY() + y, 
					  176, 78, 
					  Math.min(len, MAX_LEN), 3);
	    	
	    	len -= MAX_LEN;
	    	offset += MAX_LEN;
    	}
    }
    
    protected void drawFilledHorizontalLine(int x, int y, int len) {
    	final int MAX_LEN = 28;
  	  
    	int offset = 0;
    	
    	while (len > 0) {
	    	drawTexturedModalRect(guiX() + x + offset, guiY() + y, 
					  176, 81, 
					  Math.min(len, MAX_LEN), 3);
	    	
	    	len -= MAX_LEN;
	    	offset += MAX_LEN;
    	}
    }
    
    protected void drawVerticalLine(int x, int y, int len) {
    	final int MAX_LEN = 29;
  	  
    	int offset = 0;
    	
    	while (len > 0) {
	    	drawTexturedModalRect(guiX() + x, guiY() + y + offset, 
	    			  198, 48, 
					  3, Math.min(len, MAX_LEN));
	    	
	    	len -= MAX_LEN;
	    	offset += MAX_LEN;
    	}
    }
    
	protected void drawFilledVerticalLine(int x, int y, int len) {
		final int MAX_LEN = 29;
  	  
    	int offset = 0;
    	
    	while (len > 0) {
	    	drawTexturedModalRect(guiX() + x, guiY() + y + offset, 
	    			  201, 48, 
					  3, Math.min(len, MAX_LEN));
	    	
	    	len -= MAX_LEN;
	    	offset += MAX_LEN;
    	}
    }
}
