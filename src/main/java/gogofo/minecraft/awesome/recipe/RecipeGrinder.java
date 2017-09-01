package gogofo.minecraft.awesome.recipe;

import java.util.HashMap;

import gogofo.minecraft.awesome.init.Items;
import gogofo.minecraft.awesome.init.Ores;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;

public class RecipeGrinder {
	private HashMap<Item, Recipe> recipes = new HashMap<Item, Recipe>();

	public void registerRecipes() {
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

		for (Ores.Ore ore : Ores.getOres()) {
			if (ore.isHasDust() && ore.getGrindsToAmount() > 0) {
				NonNullList<ItemStack> ores = OreDictionary.getOres(ore.getDictName("ore"));

				for (ItemStack is : ores) {
					addRecipe(new Recipe(is.getItem(),
							stack(ore.getDust(), ore.getGrindsToAmount()),
							100));
				}

				NonNullList<ItemStack> ingots = OreDictionary.getOres(ore.getDictName("ingot"));

				for (ItemStack is : ingots) {
					addRecipe(new Recipe(is.getItem(),
							stack(ore.getDust(), 1),
							100));
				}
			}
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
		stack.setCount(size);
		return stack;
	}
	
	private ItemStack stack(Block block, int size) {
		ItemStack stack = new ItemStack(block);
		stack.setCount(size);
		return stack;
	}
}
