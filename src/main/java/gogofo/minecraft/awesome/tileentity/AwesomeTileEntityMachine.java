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
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class AwesomeTileEntityMachine extends AwesomeTileEntityContainer implements ISidedInventory, IUpdatePlayerListBox {
	private ChatComponentText displayName;
	
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
		displayName =  new ChatComponentText(getName());
	}
    
    public boolean hasPower() {
    	return PowerManager.instance.getPower(pos) > 0;
    }
    
    @Override
    public void update() {
    	if (worldObj.isRemote) {
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
    		
    		AwesomeBlockRunningMachine.setState(isActuallyWorking(), worldObj, pos);
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
}
