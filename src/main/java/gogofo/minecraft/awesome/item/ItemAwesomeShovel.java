package gogofo.minecraft.awesome.item;

import gogofo.minecraft.awesome.colorize.ISingleColoredObject;
import net.minecraft.item.ItemSpade;

public class ItemAwesomeShovel extends ItemSpade implements ISingleColoredObject {

    private final int color;

    public ItemAwesomeShovel(ToolMaterial material, int color) {
        super(material);
        this.color = color;
    }

    @Override
    public int getColor() {
        return color;
    }
}
