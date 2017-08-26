package gogofo.minecraft.awesome.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class AwesomeContainer extends Container {
	private static final int INVENTORY_SLOTS = 36;
	
	protected final IInventory inventory;
	
	private int fields[];
	private int nextSlotCategoryId = 0;
	
	protected abstract int getCustomSlotCount();
	
	public AwesomeContainer(InventoryPlayer inventoryPlayer, IInventory customInventory)
    {
		this(inventoryPlayer, customInventory, 84);
    }
	
	public AwesomeContainer(InventoryPlayer inventoryPlayer, IInventory customInventory, int inboxStartY)
    {
        inventory = customInventory;
        
        fields = new int[inventory.getFieldCount()];
        
        // add player inventory slots
        int i;
        for (i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                addSlotToContainer(new Slot(inventoryPlayer, j+i*9+9, 
                8+j*18, inboxStartY+i*18));
            }
        }

        // add hotbar slots
        for (i = 0; i < 9; ++i)
        {
            addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 
            		inboxStartY + 3*18 + 4));
        }
    }
	
	
	// TODO: Does this need replacing?
//	@Override
//    public void addCraftingToCrafters(ICrafting listener)
//    {
//        super.addCraftingToCrafters(listener);
//        listener.func_175173_a(this, inventory);
//    }
	
	/**
     * Looks for changes made in the container, sends them to every listener.
     */
    @Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (int i = 0; i < listeners.size(); ++i)
        {
            IContainerListener listner = listeners.get(i);

            for (int j = 0; j < inventory.getFieldCount(); j++) {
            	int value = inventory.getField(j);
	            if (getField(j) != value)
	            {
	            	listner.sendWindowProperty(this, j, value);
	            }
	            
	            setField(j, value);
            }
        }
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data)
    {
        inventory.setField(id, data);
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn)
    {
        return inventory.isUsableByPlayer(playerIn);
    }
    
    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
    	ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = (Slot)this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index < getCustomSlotCount())
            {
                if (!this.mergeItemStack(itemstack1, getCustomSlotCount(), this.inventorySlots.size(), true))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 0, getCustomSlotCount(), false))
            {
                return ItemStack.EMPTY;
            }

            if (itemstack1.getCount() == 0)
            {
                slot.putStack(ItemStack.EMPTY);
            }
            else
            {
                slot.onSlotChanged();
            }
        }

        return itemstack;
    }
    
    private int getField(int i) {
    	return fields[i];
    }
    
    private void setField(int i, int value) {
    	fields[i] = value;
    }
    
    @Override
    protected Slot addSlotToContainer(Slot slotIn) {
    	if (slotIn instanceof AwesomeSlot) {
    		((AwesomeSlot) slotIn).setCategoryId(nextSlotCategoryId);
    		nextSlotCategoryId += 1;
    	}
    	
    	return super.addSlotToContainer(slotIn);
    }
}
