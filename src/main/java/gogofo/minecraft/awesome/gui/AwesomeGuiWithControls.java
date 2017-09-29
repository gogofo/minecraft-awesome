package gogofo.minecraft.awesome.gui;

import java.io.IOException;

import gogofo.minecraft.awesome.gui.controls.ColorGuiButton;
import gogofo.minecraft.awesome.interfaces.IPositionedSidedInventory;
import gogofo.minecraft.awesome.inventory.SlotCategoryIdToColor;
import gogofo.minecraft.awesome.network.AwesomeControlSidesUpdateMessage;
import gogofo.minecraft.awesome.network.AwesomeNetworkHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.EnumFacing;

public abstract class AwesomeGuiWithControls extends AwesomeGui {

	private static final int CONTROLS_WIDTH = 100;
	private static final int CONTROLS_HEIGHT = 100;
	
	protected static final int GEAR_WIDTH = 24;
	protected static final int GEAR_HIGHT = 14;
	
	private static final int GEAR_BUTTON_ID = 0;
	private static final int FIRST_FACE_BUTTON_ID = 1;
	
	private ColorGuiButton gearButton;
	private ColorGuiButton background;
	private ColorGuiButton[] faceButtons;
	
	protected boolean controlActive = false;
	private IPositionedSidedInventory inventory;
	
	public AwesomeGuiWithControls(Container container, 
								  InventoryPlayer playerInventory,
								  IPositionedSidedInventory customInventory) {
		super(container, playerInventory, customInventory);
		
		this.inventory = customInventory;
		
		faceButtons = new ColorGuiButton[EnumFacing.values().length];
	}
	
	@Override
    public void initGui() {
    	super.initGui();
    	
    	gearButton = new ColorGuiButton(GEAR_BUTTON_ID, 
    							   		guiX() + gearXPos(), 
							   			guiY() + gearYPos(), 
							   			GEAR_WIDTH, GEAR_HIGHT, 
    							   		"Ctrl");
    	buttonList.add(gearButton);
    	
    	background = new ColorGuiButton(-1, 
    								    guiX() + xSize, 
    								    guiY(), 
    								    CONTROLS_WIDTH, 
    								    CONTROLS_HEIGHT, 
    								    "");
    	background.enabled = false;
    	background.setColor(0xFFC6C6C6);
    	buttonList.add(background);
    	
    	initFaceButtons();
    }
	
	private void initFaceButtons() {
		for (EnumFacing face : EnumFacing.values()) {
			ColorGuiButton button = new ColorGuiButton(FIRST_FACE_BUTTON_ID + face.getIndex(), 
					   guiX() + xSize + 8 + (38 * (face.getIndex() % 2)), 
					   guiY() + 8 + (24 * (int)Math.floor(face.getIndex() / 2)), 
					   34, 20, 
					   face.toString());
			
			faceButtons[face.getIndex()] = button;
			buttonList.add(button);
		}
	}
	
	/**
     * Args : renderPartialTicks, mouseX, mouseY
     */
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, 
          int mouseX, int mouseY)
    {
        super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
        
        if (controlActive) {
        	background.visible = true;
	        
	        for (EnumFacing face : EnumFacing.values()) {
	        	int[] slots = inventory.getSlotsForFace(face);
		        int mainSlot = slots.length > 0 ? slots[0] : -1;
		        
		        ColorGuiButton button = faceButtons[face.getIndex()];
		        button.setColor(SlotCategoryIdToColor.convert(mainSlot));
		        button.visible = true;
        	}
        } else {
        	background.visible = false;
        	
        	for (EnumFacing face : EnumFacing.values()) {
        		faceButtons[face.getIndex()].visible = false;
        	}
        }
    }
    
    private int gearXPos() {
    	return xSize - GEAR_WIDTH - 8;
    }
    
    private int gearYPos() {
    	return 8;
    }
    
    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
    	int buttonId = button.id;
    	
    	if (buttonId == GEAR_BUTTON_ID) {
    		handleGearPressed();
    	} else if (buttonId >= FIRST_FACE_BUTTON_ID && 
    			   buttonId < FIRST_FACE_BUTTON_ID + EnumFacing.values().length) {
    		AwesomeControlSidesUpdateMessage message = 
					new AwesomeControlSidesUpdateMessage(inventory.getPos(),
														 EnumFacing.values()[buttonId - FIRST_FACE_BUTTON_ID]);
			AwesomeNetworkHandler.wrapper.sendToServer(message);
    	}
    }

	private void handleGearPressed() {
		controlActive = !controlActive;
	}
}
