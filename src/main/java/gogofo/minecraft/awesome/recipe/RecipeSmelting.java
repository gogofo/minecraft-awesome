package gogofo.minecraft.awesome.recipe;

import gogofo.minecraft.awesome.init.Items;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class RecipeSmelting {
	public void registerRecipes() {
		GameRegistry.addSmelting(Items.iron_dust, 
								 stack(net.minecraft.init.Items.IRON_INGOT, 1), 
								 10f);
		
		GameRegistry.addSmelting(Items.gold_dust, 
								 stack(net.minecraft.init.Items.GOLD_INGOT, 1), 
								 20f);
	}
	
	private ItemStack stack(Item item, int size) {
		ItemStack stack = new ItemStack(item);
		stack.setCount(size);
		return stack;
	}
	
	private ItemStack stack(Block block, int size) {
		ItemStack stack = new ItemStack(block);
		stack.setCount(size);
		return stack;
	}
}
