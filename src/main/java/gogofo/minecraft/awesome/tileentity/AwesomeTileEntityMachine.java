package gogofo.minecraft.awesome.tileentity;

import gogofo.minecraft.awesome.PowerManager;
import gogofo.minecraft.awesome.block.AwesomeBlockRunningMachine;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.util.text.TextComponentString;

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

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);

		initUpgrades();
	}

	private void initUpgrades() {
		for (int i = getSlotCount(); i < getSlotCount() + getUpgradeCount(); i++) {
			if (!itemStackArray[i].isEmpty()) {
				onUpgradeAdded(itemStackArray[i].getItem());
			}
		}
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

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		if (index >= getSlotCount() && index < getSlotCount() + getUpgradeCount()) {
			if (itemStackArray[index].getItem() != stack.getItem()) {
				onUpgradeAdded(stack.getItem());
			}
		}

		super.setInventorySlotContents(index, stack);
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		if (index >= getSlotCount() && index < getSlotCount() + getUpgradeCount()) {
			onUpgradeRemoved(itemStackArray[index].getItem());
		}

		return super.decrStackSize(index, count);
	}

	protected void onUpgradeAdded(Item upgrade) {
	}

	protected void onUpgradeRemoved(Item upgrade) {
	}
}
