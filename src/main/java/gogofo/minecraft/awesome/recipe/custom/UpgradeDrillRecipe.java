package gogofo.minecraft.awesome.recipe.custom;

import com.google.gson.JsonObject;

import net.minecraft.block.Block;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.CraftingHelper.ShapedPrimer;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class 
UpgradeDrillRecipe extends ShapedOreRecipe {

	public UpgradeDrillRecipe(ResourceLocation group, ItemStack result, ShapedPrimer primer) {
		super(group, result, primer);
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting var1) {
		ItemStack output = super.getCraftingResult(var1);
		output.addEnchantment(Enchantments.EFFICIENCY, 5);
		output.addEnchantment(Enchantments.FORTUNE, 3);
		return output;
	}
	
	public static class Factory implements IRecipeFactory {

		@Override
		public IRecipe parse(JsonContext context, JsonObject json) {
			final String group = JsonUtils.getString(json, "group", "");
			final CraftingHelper.ShapedPrimer primer = RecipeUtil.parseShaped(context, json);
			final ItemStack result = CraftingHelper.getItemStack(JsonUtils.getJsonObject(json, "result"), context);

			return new UpgradeDrillRecipe(group.isEmpty() ? null : new ResourceLocation(group), result, primer);
		}
		
	}
}
