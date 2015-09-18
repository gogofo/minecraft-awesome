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
			net.minecraft.init.Items.chicken,
			net.minecraft.init.Items.cooked_chicken,
			net.minecraft.init.Items.beef,
			net.minecraft.init.Items.cooked_beef,
			net.minecraft.init.Items.porkchop,
			net.minecraft.init.Items.cooked_porkchop,
			net.minecraft.init.Items.fish,
			net.minecraft.init.Items.cooked_fish,
			net.minecraft.init.Items.mutton,
			net.minecraft.init.Items.cooked_mutton,
			net.minecraft.init.Items.rabbit,
			net.minecraft.init.Items.cooked_rabbit,
			net.minecraft.init.Items.rotten_flesh,
			net.minecraft.init.Items.bone,
			net.minecraft.init.Items.gunpowder,
			net.minecraft.init.Items.string,
			net.minecraft.init.Items.egg,
			net.minecraft.init.Items.ender_pearl,
			net.minecraft.init.Items.feather,
			net.minecraft.init.Items.spider_eye,
			net.minecraft.init.Items.leather,
			net.minecraft.init.Items.rabbit_hide
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
