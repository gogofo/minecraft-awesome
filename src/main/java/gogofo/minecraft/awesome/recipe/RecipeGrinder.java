package gogofo.minecraft.awesome.recipe;

import java.util.HashMap;

import gogofo.minecraft.awesome.init.Items;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class RecipeGrinder {
	private HashMap<Item, Recipe> recipes = new HashMap<Item, Recipe>();
	
	public RecipeGrinder() {
		addRecipe(new Recipe(net.minecraft.init.Items.IRON_INGOT, 
							 stack(Items.iron_dust, 1), 
							 50));
		
		addRecipe(new Recipe(net.minecraft.init.Items.GOLD_INGOT, 
							 stack(Items.gold_dust, 1), 
							 50));
		
		addRecipe(new Recipe(net.minecraft.init.Blocks.IRON_ORE, 
							 stack(Items.iron_dust, 2), 
							 100));
		
		addRecipe(new Recipe(net.minecraft.init.Blocks.GOLD_ORE, 
							 stack(Items.gold_dust, 2), 
							 100));
		
		addRecipe(new Recipe(net.minecraft.init.Blocks.COBBLESTONE, 
				 stack(net.minecraft.init.Blocks.SAND, 1), 
				 50));
		
		addRecipe(new Recipe(net.minecraft.init.Blocks.STONE, 
				 stack(net.minecraft.init.Blocks.SAND, 1), 
				 50));
	}
	
	public void addRecipe(Recipe recipe) {
		recipes.put(recipe.input, recipe);
	}
	
	public Recipe getRecipe(Item item) {
		return recipes.get(item);
	}
	
	public class Recipe {
		public Item input;
		public ItemStack result;
		public int grindTime;
		
		public Recipe(Item input, ItemStack result, int grindTime) {
			this.input = input;
			this.result = result;
			this.grindTime = grindTime;
		}
		
		public Recipe(Block input, ItemStack result, int grindTime) {
			this.input = Item.getItemFromBlock(input);
			this.result = result;
			this.grindTime = grindTime;
		}
	}
	
	private ItemStack stack(Item item, int size) {
		ItemStack stack = new ItemStack(item);
		stack.stackSize = size;
		return stack;
	}
	
	private ItemStack stack(Block block, int size) {
		ItemStack stack = new ItemStack(block);
		stack.stackSize = size;
		return stack;
	}
}
