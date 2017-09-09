package gogofo.minecraft.awesome.item;

import gogofo.minecraft.awesome.colorize.ISingleColoredObject;
import net.minecraft.item.Item;

public class ItemOneColored extends Item implements ISingleColoredObject {

    private int color;

    public ItemOneColored(int color) {

        this.color = color;
    }

    @Override
    public int getColor() {
        return color;
    }
}
