package gogofo.minecraft.awesome.recipe.custom;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import gogofo.minecraft.awesome.init.Items;
import gogofo.minecraft.awesome.item.ItemLiquidContainer;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.JsonUtils;
import net.minecraftforge.common.crafting.IIngredientFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.fluids.*;

import java.util.Objects;

@SuppressWarnings("unused")
public class FilledLiquidContainerIngredientFactory implements IIngredientFactory {

    @Override
    public Ingredient parse(final JsonContext context, final JsonObject json) {
        final String fluidName = JsonUtils.getString(json, "fluid");
        Block fluidBlock = Blocks.AIR;

        if (!fluidName.equals("air")) {
            final Fluid fluid = FluidRegistry.getFluid(fluidName);

            if (fluid == null) {
                throw new JsonSyntaxException("Unknown fluid '" + fluidName + "'");
            }

            fluidBlock = fluid.getBlock();
        }

        ItemStack stack = new ItemStack(Items.liquid_container);
        ItemLiquidContainer.setLiquidType(stack, fluidBlock);

        return new IngredientLiquidContainer(stack);
    }
}
