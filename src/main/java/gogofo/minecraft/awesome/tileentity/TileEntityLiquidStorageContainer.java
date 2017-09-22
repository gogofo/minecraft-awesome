package gogofo.minecraft.awesome.tileentity;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;

public class TileEntityLiquidStorageContainer extends TileEntity {

    public static final int MAX_AMOUNT = 15;

    private Block containedSubstance = null;
    private int containedAmount = 0;

    @Override
    public boolean hasFastRenderer() {
        return true;
    }

    public Block getContainedSubstance() {
        return containedSubstance;
    }

    public int getContainedAmount() {
        return containedAmount;
    }

    /**
     * Tries to place liquid in the container
     * @param substance The substance to place
     * @param amount The amount to place
     * @return The amount of substance actually placed
     */
    public int tryPlaceLiquid(Block substance, int amount) {
        if ((containedSubstance != null && substance != containedSubstance) ||
                substance == null ||
                amount <= 0) {
            return 0;
        }

        int actualAmount = amount;
        if (containedAmount + amount > MAX_AMOUNT) {
            actualAmount = MAX_AMOUNT - containedAmount;
        }

        containedSubstance = substance;
        containedAmount += actualAmount;

        return actualAmount;
    }
}
