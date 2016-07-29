package gogofo.minecraft.awesome.recipe;

import java.util.HashMap;

import gogofo.minecraft.awesome.init.Items;
import gogofo.minecraft.awesome.recipe.RecipeGrinder.Recipe;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class RecipeExtractor {
	private HashMap<Item, Recipe> recipes = new HashMap<Item, Recipe>();
	
	private final Item mobEssenceItems[] = new Item[] {
			net.minecraft.init.Items.CHICKEN,
			net.minecraft.init.Items.COOKED_CHICKEN,
			net.minecraft.init.Items.BEEF,
			net.minecraft.init.Items.COOKED_BEEF,
			net.minecraft.init.Items.PORKCHOP,
			net.minecraft.init.Items.COOKED_PORKCHOP,
			net.minecraft.init.Items.FISH,
			net.minecraft.init.Items.COOKED_FISH,
			net.minecraft.init.Items.MUTTON,
			net.minecraft.init.Items.COOKED_MUTTON,
			net.minecraft.init.Items.RABBIT,
			net.minecraft.init.Items.COOKED_RABBIT,
			net.minecraft.init.Items.ROTTEN_FLESH,
			net.minecraft.init.Items.BONE,
			net.minecraft.init.Items.GUNPOWDER,
			net.minecraft.init.Items.STRING,
			net.minecraft.init.Items.EGG,
			net.minecraft.init.Items.ENDER_PEARL,
			net.minecraft.init.Items.FEATHER,
			net.minecraft.init.Items.SPIDER_EYE,
			net.minecraft.init.Items.LEATHER,
			net.minecraft.init.Items.RABBIT_HIDE
	};
	
	public RecipeExtractor() {
		for (Item item : mobEssenceItems) {
			addRecipe(new Recipe(item, 
								 stack(Items.mob_essence, 1), 
								 50));
		}
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
		public int extractTime;
		
		public Recipe(Item input, ItemStack result, int extractTime) {
			this.input = input;
			this.result = result;
			this.extractTime = extractTime;
		}
		
		public Recipe(Block input, ItemStack result, int extractTime) {
			this.input = Item.getItemFromBlock(input);
			this.result = result;
			this.extractTime = extractTime;
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
