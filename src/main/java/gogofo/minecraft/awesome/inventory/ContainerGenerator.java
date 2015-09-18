package gogofo.minecraft.awesome.inventory;

import gogofo.minecraft.awesome.tileentity.TileEntityGenerator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnaceFuel;
import net.minecraft.inventory.SlotFurnaceOutput;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerGenerator extends AwesomeContainer
{
    public ContainerGenerator(InventoryPlayer inventoryPlayer, IInventory generatorInventory)
    {
    	super(inventoryPlayer, generatorInventory);
    	
        this.addSlotToContainer(new SlotGeneratorFuel(generatorInventory, 0, 56, 38));
        this.addSlotToContainer(new AwesomeSlotBigOutput(generatorInventory, 1, 116, 38));
    }

	@Override
	protected int getCustomSlotCount() {
		return 2;
	}
}
