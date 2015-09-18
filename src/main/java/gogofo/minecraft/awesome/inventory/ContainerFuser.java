package gogofo.minecraft.awesome.inventory;

import gogofo.minecraft.awesome.tileentity.TileEntityFuser;
import gogofo.minecraft.awesome.tileentity.TileEntityGenerator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerFuser extends AwesomeContainer {
    public ContainerFuser(InventoryPlayer inventoryPlayer, IInventory generatorInventory)
    {
    	super(inventoryPlayer, generatorInventory);
    	
        this.addSlotToContainer(new Slot(generatorInventory, 0, 56, 20));
        this.addSlotToContainer(new Slot(generatorInventory, 1, 56, 45));
        this.addSlotToContainer(new AwesomeSlotBigOutput(generatorInventory, 2, 116, 38));
    }

	@Override
	protected int getCustomSlotCount() {
		return 3;
	}
}
