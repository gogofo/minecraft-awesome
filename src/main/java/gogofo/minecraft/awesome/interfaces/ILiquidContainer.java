package gogofo.minecraft.awesome.interfaces;

import net.minecraft.block.Block;

public interface ILiquidContainer {
    /**
     * Tries to place liquid in the container
     * @param substance The substance to place
     * @param amount The amount to place
     * @return The amount of substance actually placed
     */
    int tryPlaceLiquid(Block substance, int amount);

    /**
     * Tries to take liquid from the container
     * @param substance The substance to take
     * @param amount The amount to take
     * @return The amount of substance actually taken
     */
    int tryTakeLiquid(Block substance, int amount);

    Block getSubstance();
}
