package gogofo.minecraft.awesome.init;

import gogofo.minecraft.awesome.fluid.FluidOil;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import java.util.ArrayList;

public class Fluids {

    public static FluidOil oil;

    private static ArrayList<Fluid> fluids = new ArrayList<>();

    public static void init() {
        oil = new FluidOil();
        fluids.add(oil);
    }

    public static void registerFluids() {
        for (Fluid fluid : fluids) {
            FluidRegistry.registerFluid(fluid);
            FluidRegistry.addBucketForFluid(fluid);
        }
    }
}
