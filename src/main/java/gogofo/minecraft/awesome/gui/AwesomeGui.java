package gogofo.minecraft.awesome.gui;

import gogofo.minecraft.awesome.AwesomeMod;
import gogofo.minecraft.awesome.interfaces.IConfigurableSidedInventory;
import gogofo.minecraft.awesome.inventory.AwesomeSlot;
import gogofo.minecraft.awesome.inventory.AwesomeSlotBig;
import gogofo.minecraft.awesome.inventory.ContainerGenerator;
import gogofo.minecraft.awesome.inventory.SlotCategoryIdToColor;
import net.minecraft.client.gui.GuiButton;
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
    protected final InventoryPlayer playerInventory;
    protected final IConfigurableSidedInventory customInventory;   
    
    protected abstract void drawCustomGui();
    

    public AwesomeGui(Container container,
    				  InventoryPlayer playerInventory, 
    				  IConfigurableSidedInventory customInventory)
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
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
		renderHoveredToolTip(mouseX, mouseY);
	}

	@Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        String title = customInventory.getDisplayName().getUnformattedText();
        fontRenderer.drawString(title, xSize/2-fontRenderer
              .getStringWidth(title)/2, 6, 4210752);
        
        fontRenderer.drawString(playerInventory.getDisplayName()
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
    
    protected int guiX() {
    	return (width - xSize) / 2;
    }
    
    protected int guiY() {
    	return (height - ySize) / 2;
    }
    
    private void drawEmptyBackground() {
    	GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    	drawTexturedModalRect(guiX(), guiY(), 
				  0, 0, 
			  	  xSize, ySize);
    }
    
    private void drawInventory() {
    	GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
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
    
    protected void drawSlot(int x, int y, int color) {
    	GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    	drawTexturedModalRect(guiX() + x, guiY() + y, 
				  176, 0, 
				  20, 20);
    	
    	drawRect(guiX() + x + 2, 
    			guiY() + y + 2, 
    			guiX() + x + 18, 
    			guiY() + y + 18, 
    			color);
    }
    
    protected void drawSlotsByCustomContainer(Container container) {
    	GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    	
    	int slots = container.getInventory().size();
    	
    	for (int i = 0; i < slots; i++) {
    		Slot slot = container.getSlot(i);
    		
    		if (slot instanceof AwesomeSlotBig) {
    			int color = SlotCategoryIdToColor.convert(((AwesomeSlotBig) slot).getCategoryId());
    			drawBigSlot(slot.xPos-6, slot.yPos-6, color);
    		} else if (slot instanceof AwesomeSlot) {
    			int color = SlotCategoryIdToColor.convert(((AwesomeSlot) slot).getCategoryId());
    			drawSlot(slot.xPos-2, slot.yPos-2, color);
    		} else {
    			drawSlot(slot.xPos-2, slot.yPos-2, 0);
    		}
    	}
    }
    
    protected void drawBigSlot(int x, int y, int color) {
    	GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    	drawTexturedModalRect(guiX() + x, guiY() + y, 
				  176, 20, 
				  28, 28);
    	
    	drawRect(guiX() + x + 2, 
    			guiY() + y + 2, 
    			guiX() + x + 26, 
    			guiY() + y + 26, 
    			color);
    }
    
    protected void drawArrow(int x, int y) {
    	GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    	drawTexturedModalRect(guiX() + x, guiY() + y, 
				  176, 48, 
				  22, 15);
    }
    
    protected void drawFilledArrow(int x, int y) {
    	GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    	drawFilledArrow(x, y, 1.0f);
    }
    
    protected void drawFilledArrow(int x, int y, float percent) {
    	GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    	drawTexturedModalRect(guiX() + x, guiY() + y, 
				  176, 63,
				  Math.round(22*percent), 15);
    }
    
    protected void drawHorizontalLine(int x, int y, int len) {
    	GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    	
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
    	GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    	
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
    	GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    	
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
    	GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    	
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
