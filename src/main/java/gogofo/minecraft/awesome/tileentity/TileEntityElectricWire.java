package gogofo.minecraft.awesome.tileentity;

import gogofo.minecraft.awesome.PowerManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TileEntityElectricWire extends TileEntity implements ITickable {
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
