package gogofo.minecraft.awesome.gui;

import gogofo.minecraft.awesome.AwesomeMod;
import gogofo.minecraft.awesome.gui.features.GuiFeature;
import gogofo.minecraft.awesome.interfaces.IPositionedSidedInventory;
import gogofo.minecraft.awesome.inventory.AwesomeSlot;
import gogofo.minecraft.awesome.inventory.AwesomeSlotBig;
import gogofo.minecraft.awesome.inventory.SlotCategoryIdToColor;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;
import java.util.ArrayList;

public abstract class AwesomeGui extends GuiContainer {
	private static final int INBOX_START_Y = 131;
	private static final int INBOX_HIGHT = 83;
	private static final int TEXTURE_BACKGROUND_HEIGHT = 214;
	private static final int TEXTURE_EMPTY_BACKGROUND_HEIGHT = 120;
	public static final int GLASS_CONTAINER_WIDTH = 20;

	private ResourceLocation guiTextures;
    protected final InventoryPlayer playerInventory;
    protected final IPositionedSidedInventory customInventory;
    protected ArrayList<Component> components;
    protected ArrayList<GuiFeature> features;
    
    protected abstract void drawCustomGui();


    public AwesomeGui(Container container,
    				  InventoryPlayer playerInventory,
					  IPositionedSidedInventory customInventory)
    {
        super(container);
        this.playerInventory = playerInventory;
        this.customInventory = customInventory;
        this.components = new ArrayList<>();
        this.features = new ArrayList<>();
        
        guiTextures = new ResourceLocation(AwesomeMod.MODID + ":" + getTexturePath());
        
        this.ySize = getClearSectionHeight() + INBOX_HIGHT;
    }

    public AwesomeGui addFeature(GuiFeature feature) {
    	features.add(feature);
    	return this;
	}

	@Override
	public void initGui() {
		super.initGui();

		features.forEach(GuiFeature::onInitGui);
	}

	protected String getTexturePath() {
    	return "textures/gui/container/awesome_gui_basic.png";
    }

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);

		for (Component component : components) {
			if (component.mouseIsOver(mouseX - guiX(), mouseY - guiY())) {
				mouseOverComponentEvent(component, mouseX, mouseY);
			}
		}

		renderHoveredToolTip(mouseX, mouseY);
	}

	protected void mouseOverComponentEvent(Component component, int mouseX, int mouseY) {
	}

	@Override
	protected void renderHoveredToolTip(int p_191948_1_, int p_191948_2_) {
		super.renderHoveredToolTip(p_191948_1_, p_191948_2_);
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
        drawCustomGui();

		features.forEach(f -> f.onDrawBackgroundLayer(partialTicks, mouseX, mouseY));
    }

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		features.forEach(f -> f.onActionPerformed(button));
	}

	public int guiX() {
    	return (width - xSize) / 2;
    }
    
    public int guiY() {
    	return (height - ySize) / 2;
    }

	@Override
	public <T extends GuiButton> T addButton(T buttonIn) {
		return super.addButton(buttonIn);
	}

	private void drawEmptyBackground() {
    	GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		drawTexturedModalRect(guiX(), guiY(),
				0, 0,
				xSize, 4);


		int backgroundToDraw = ySize - 8;
		int curY = 4;

		while (backgroundToDraw > 0) {
			int actualDraw = Math.min(backgroundToDraw, TEXTURE_EMPTY_BACKGROUND_HEIGHT);
			drawTexturedModalRect(guiX(), guiY() + curY,
					0, 4,
					xSize, actualDraw);

			backgroundToDraw -= actualDraw;
			curY += actualDraw;
		}

		drawTexturedModalRect(guiX(), guiY() + ySize - 4,
				0, TEXTURE_BACKGROUND_HEIGHT - 4,
				xSize, 4);
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

    protected void drawGlassContainer(int x, int y, int height) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		drawTexturedModalRect(guiX() + x, guiY() + y,
							  176, 99,
							  GLASS_CONTAINER_WIDTH, 2);
		y += 2;

		height -= 4;

		while (height > 0) {
			int heightToDraw = Math.min(height, 16);
			drawTexturedModalRect(guiX() + x, guiY() + y,
								  176, 101,
								  GLASS_CONTAINER_WIDTH, heightToDraw);

			height -= heightToDraw;
			y += heightToDraw;
		}

		drawTexturedModalRect(guiX() + x, guiY() + y,
							  176, 117,
							  GLASS_CONTAINER_WIDTH, 2);
	}

	protected static class Component {
    	public int left;
		public int top;
		public int right;
		public int bottom;

		public Component(int left, int top, int right, int bottom) {
			this.left = left;
			this.right = right;
			this.top = top;
			this.bottom = bottom;
		}

		public boolean mouseIsOver(int mouseX, int mouseY) {
			return mouseX >= left && mouseX <= right && mouseY >= top && mouseY <= bottom;
		}
	}
}
