package gogofo.minecraft.awesome.gui.controls;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;

public class ColorGuiButton extends GuiButton {
	
	private int color;

	public ColorGuiButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
		super(buttonId, x, y, widthIn, heightIn, buttonText);
	}
	
	public void setColor(int color) {
		this.color = color;
	}

	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY) {
		if (visible) {
			drawRect(xPosition + 1, 
	    			yPosition + 1, 
	    			xPosition + width - 1, 
	    			yPosition + height - 1, 
	    			color);
			
			drawBorder();
			
			drawCenteredString(mc.fontRendererObj, 
					           this.displayString, 
					           this.xPosition + this.width / 2, 
					           this.yPosition + (this.height - 8) / 2, 
					           0xFFFFFFFF);
		}
	}
	
	private void drawBorder() {
		// Black line
		drawHorizontalLine(xPosition + 2, xPosition + width - 3, yPosition, 0xFF000000);
		drawHorizontalLine(xPosition + 3, xPosition + width - 2, yPosition + height, 0xFF000000);
		drawVerticalLine(xPosition, yPosition + 1, yPosition + height - 2, 0xFF000000);
		drawVerticalLine(xPosition + width, yPosition + 2, yPosition + height - 1, 0xFF000000);
		drawDot(xPosition + 1, yPosition + 1, 0xFF000000);
		drawDot(xPosition + width - 2, yPosition + 1, 0xFF000000);
		drawDot(xPosition + width - 1, yPosition + 2, 0xFF000000);
		drawDot(xPosition + 1, yPosition + height - 2, 0xFF000000);
		drawDot(xPosition + 2, yPosition + height - 1, 0xFF000000);
		drawDot(xPosition + width - 1, yPosition + height - 1, 0xFF000000);

		// White lines
		drawRect(xPosition + 2, yPosition + 1, xPosition + width - 2, yPosition + 2, 0xFFFFFFFF);
		drawRect(xPosition + 1, yPosition + 2, xPosition + 2, yPosition + height - 2, 0xFFFFFFFF);
		drawDot(xPosition + 2, yPosition + 2, 0xFFFFFFFF);
		
		// Grey corners
		drawDot(xPosition + 2, yPosition + height - 2, 0xFFC6C6C6);
		drawDot(xPosition + width - 2, yPosition + 2, 0xFFC6C6C6);
		
		// Dark grey lines
		drawRect(xPosition + 3, yPosition + height - 1, xPosition + width - 1, yPosition + height, 0xFF555555);
		drawRect(xPosition + width, yPosition + 3, xPosition + width - 1, yPosition + height - 1, 0xFF555555);
		drawDot(xPosition + width - 2, yPosition + height - 2, 0xFF555555);
	}
	
	private void drawDot(int x, int y, int color) {
		drawHorizontalLine(x, x, y, color);
	}
}
