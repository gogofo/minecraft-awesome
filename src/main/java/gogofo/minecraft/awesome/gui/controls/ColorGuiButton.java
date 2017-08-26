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
	public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
		if (visible) {
			drawRect(this.x + 1, 
					this.y + 1, 
					this.x + width - 1, 
					this.y + height - 1, 
	    			color);
			
			drawBorder();
			
			drawCenteredString(mc.fontRenderer, 
					           this.displayString, 
					           this.x + this.width / 2, 
					           this.y + (this.height - 8) / 2, 
					           0xFFFFFFFF);
		}
	}
	
	private void drawBorder() {
		// Black line
		drawHorizontalLine(this.x + 2, this.x + width - 3, this.y, 0xFF000000);
		drawHorizontalLine(this.x + 3, this.x + width - 2, this.y + height, 0xFF000000);
		drawVerticalLine(this.x, this.y + 1, this.y + height - 2, 0xFF000000);
		drawVerticalLine(this.x + width, this.y + 2, this.y + height - 1, 0xFF000000);
		drawDot(this.x + 1, this.y + 1, 0xFF000000);
		drawDot(this.x + width - 2, this.y + 1, 0xFF000000);
		drawDot(this.x + width - 1, this.y + 2, 0xFF000000);
		drawDot(this.x + 1, this.y + height - 2, 0xFF000000);
		drawDot(this.x + 2, this.y + height - 1, 0xFF000000);
		drawDot(this.x + width - 1, this.y + height - 1, 0xFF000000);

		// White lines
		drawRect(this.x + 2, this.y + 1, this.x + width - 2, this.y + 2, 0xFFFFFFFF);
		drawRect(this.x + 1, this.y + 2, this.x + 2, this.y + height - 2, 0xFFFFFFFF);
		drawDot(this.x + 2, this.y + 2, 0xFFFFFFFF);
		
		// Grey corners
		drawDot(this.x + 2, this.y + height - 2, 0xFFC6C6C6);
		drawDot(this.x + width - 2, this.y + 2, 0xFFC6C6C6);
		
		// Dark grey lines
		drawRect(this.x + 3, this.y + height - 1, this.x + width - 1, this.y + height, 0xFF555555);
		drawRect(this.x + width, this.y + 3, this.x + width - 1, this.y + height - 1, 0xFF555555);
		drawDot(this.x + width - 2, this.y + height - 2, 0xFF555555);
	}
	
	private void drawDot(int x, int y, int color) {
		drawHorizontalLine(x, x, y, color);
	}
}
