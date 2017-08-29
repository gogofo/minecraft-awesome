package gogofo.minecraft.awesome.fluid;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class FluidOil extends Fluid {
    public FluidOil() {
        super("oil", new ResourceLocation("minecraft", "blocks/lava_still"), new ResourceLocation("minecraft", "blocks/lava_flow"));
    }

    @Override
    public int getColor() {
        return 0xF0000000;
    }
}
