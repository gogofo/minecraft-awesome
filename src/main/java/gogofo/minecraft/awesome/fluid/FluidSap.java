package gogofo.minecraft.awesome.fluid;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class FluidSap extends Fluid {
    public FluidSap() {
        super("sap", new ResourceLocation("minecraft", "blocks/water_still"), new ResourceLocation("minecraft", "blocks/water_flow"));
    }

    @Override
    public int getColor() {
        return 0xFFE06224;
    }
}
