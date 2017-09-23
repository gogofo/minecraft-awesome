package gogofo.minecraft.awesome.init;

import gogofo.minecraft.awesome.fluid.FluidOil;
import gogofo.minecraft.awesome.fluid.FluidSap;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import java.util.ArrayList;

public class Fluids {

    public static FluidOil oil;
    public static FluidSap sap;

    private static ArrayList<Fluid> fluids = new ArrayList<>();

    public static void init() {
        oil = new FluidOil();
        fluids.add(oil);

        sap = new FluidSap();
        fluids.add(sap);
    }

    public static void registerFluids() {
        for (Fluid fluid : fluids) {
            FluidRegistry.registerFluid(fluid);
            FluidRegistry.addBucketForFluid(fluid);
        }
    }
}
