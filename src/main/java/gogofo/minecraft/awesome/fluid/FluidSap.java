package gogofo.minecraft.awesome.fluid;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class FluidSap extends Fluid {
    public FluidSap() {
        super("sap", new ResourceLocation("awesome", "blocks/fluid_still"), new ResourceLocation("awesome", "blocks/fluid_flow"));
    }

    @Override
    public int getColor() {
        return 0xFFE5B23C;
    }
}
