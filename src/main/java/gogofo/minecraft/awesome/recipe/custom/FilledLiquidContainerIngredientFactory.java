package gogofo.minecraft.awesome.recipe.custom;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import gogofo.minecraft.awesome.init.Items;
import gogofo.minecraft.awesome.item.ItemLiquidContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.JsonUtils;
import net.minecraftforge.common.crafting.IIngredientFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.fluids.*;

@SuppressWarnings("unused")
public class FilledLiquidContainerIngredientFactory implements IIngredientFactory {

    @Override
    public Ingredient parse(final JsonContext context, final JsonObject json) {
        final String fluidName = JsonUtils.getString(json, "fluid");
        final Fluid fluid = FluidRegistry.getFluid(fluidName);

        if (fluid == null) {
            throw new JsonSyntaxException("Unknown fluid '" + fluidName + "'");
        }

        ItemStack stack = new ItemStack(Items.liquid_container);
        ItemLiquidContainer.setLiquidType(stack, fluid.getBlock());

        return new IngredientLiquidContainer(stack);
    }
}
