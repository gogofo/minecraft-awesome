package gogofo.minecraft.awesome.item;

import gogofo.minecraft.awesome.colorize.ISingleColoredObject;
import net.minecraft.item.ItemHoe;

public class ItemAwesomeHoe extends ItemHoe implements ISingleColoredObject {

    private final int color;

    public ItemAwesomeHoe(ToolMaterial material, int color) {
        super(material);
        this.color = color;
    }

    @Override
    public int getColor() {
        return color;
    }
}
