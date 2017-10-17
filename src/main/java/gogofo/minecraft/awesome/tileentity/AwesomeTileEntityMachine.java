package gogofo.minecraft.awesome.tileentity;

import gogofo.minecraft.awesome.PowerManager;
import gogofo.minecraft.awesome.block.AwesomeBlockRunningMachine;
import gogofo.minecraft.awesome.block.IElectricalBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class AwesomeTileEntityMachine extends AwesomeTileEntityContainer implements ITickable {
	private TextComponentString displayName;
	
	private int powerSupplied = 0;
	private boolean isWorkingTmpFlag = true;
	private boolean isFirstUpdate = true;
	private boolean wasWorking;
	
	protected abstract void electricUpdate();
	public abstract int powerGeneratedWhenWorking();
	public abstract int powerRequiredWhenWorking();
	public abstract int powerGeneratedWhenIdle();
	public abstract int powerRequiredWhenIdle();
	public abstract boolean isWorking();
	
	public AwesomeTileEntityMachine() {
		displayName =  new TextComponentString(getName());
	}
    
    public boolean hasPower() {
    	return PowerManager.instance.getPower(pos) > 0;
    }
    
    @Override
    public void update() {
    	if (world.isRemote) {
    		return;
    	}
    	
    	if (isFirstUpdate) {
    		isFirstUpdate = false;
    		
        	PowerManager.instance.registerMachine(this);
        	wasWorking = isActuallyWorking();
    	} 
    	
    	if (hasPower() || powerRequiredWhenWorking() == 0) {
    		electricUpdate();
    	} else {
    		electricPowerlessUpdate();
    	}
    	
    	if (wasWorking != (isActuallyWorking())) {
    		isWorkingTmpFlag = wasWorking;
    		PowerManager.instance.unregisterMachine(this);
    		isWorkingTmpFlag = !wasWorking;
    		PowerManager.instance.registerMachine(this);
    		
    		isWorkingTmpFlag = true;
    		
    		AwesomeBlockRunningMachine.setState(isActuallyWorking(), world, pos);
    		markDirty();
    		
    		wasWorking = isActuallyWorking();
    	}
    }
    
    public void electricPowerlessUpdate() {
    }
    
    private boolean isActuallyWorking() {
    	return isWorking() && (hasPower() || powerRequiredWhenWorking() == 0);
    }
    
	public int powerGenerated() {
		if (isWorking() && isWorkingTmpFlag) {
			return powerGeneratedWhenWorking();
		} else {
			return powerGeneratedWhenIdle();
		}
	}

	public int powerRequired() {
		if (isWorking() && isWorkingTmpFlag) {
			return powerRequiredWhenWorking();
		} else {
			return powerRequiredWhenIdle();
		}
	}

	@Override
	public int getUpgradeCount() {
		return 4;
	}
}
