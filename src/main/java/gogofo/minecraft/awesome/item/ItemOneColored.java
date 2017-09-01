package gogofo.minecraft.awesome.item;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemOneColored extends Item implements IItemColor {

    private int color;

    public ItemOneColored(int color) {

        this.color = color;
    }

    @Override
    public int getColorFromItemstack(ItemStack stack, int tintIndex) {
        return color;
    }
}
