package gogofo.minecraft.awesome.init;

import gogofo.minecraft.awesome.recipe.RecipeExtractor;
import gogofo.minecraft.awesome.recipe.RecipeFuser;
import gogofo.minecraft.awesome.recipe.RecipeGrinder;
import gogofo.minecraft.awesome.recipe.RecipeSmelting;

public class Recipes {
	public final static RecipeFuser fuser = new RecipeFuser();
	public final static RecipeGrinder grinder = new RecipeGrinder();
	public final static RecipeSmelting smelting = new RecipeSmelting();
	public final static RecipeExtractor extractor = new RecipeExtractor();
	
	public static void registerRecipes() {
		smelting.registerRecipes();
	}
}
