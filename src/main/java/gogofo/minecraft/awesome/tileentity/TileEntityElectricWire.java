package gogofo.minecraft.awesome.tileentity;

import gogofo.minecraft.awesome.PowerManager;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;

public class TileEntityElectricWire extends TileEntity implements IUpdatePlayerListBox {
	private boolean isFirstUpdate = true;
	
	@Override
	public void update() {
		if (worldObj.isRemote) {
    		return;
    	}
    	
    	if (isFirstUpdate) {
    		isFirstUpdate = false;
    		
        	PowerManager.instance.registerWire(pos);
    	} 
	}
	
}
