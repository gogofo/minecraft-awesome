package gogofo.minecraft.awesome.recipe.custom;

import gogofo.minecraft.awesome.item.ItemLiquidContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

import javax.annotation.Nullable;

public class IngredientLiquidContainer extends Ingredient {
    private final ItemStack stack;

    protected IngredientLiquidContainer(ItemStack stack)
    {
        super(stack);
        this.stack = stack;
    }

    @Override
    public boolean apply(@Nullable ItemStack input) {
        return input != null &&
                this.stack.getItem() == input.getItem() &&
                ItemLiquidContainer.getLiquidType(stack) == ItemLiquidContainer.getLiquidType(input);

    }
}
