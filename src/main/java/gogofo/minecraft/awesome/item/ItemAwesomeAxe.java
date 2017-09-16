package gogofo.minecraft.awesome.item;

import gogofo.minecraft.awesome.colorize.ISingleColoredObject;
import net.minecraft.item.ItemAxe;

public class ItemAwesomeAxe extends ItemAxe implements ISingleColoredObject {

    private final int color;

    public ItemAwesomeAxe(ToolMaterial material, int color) {
        super(material, 8.0F, -3.1F);
        this.color = color;
    }

    @Override
    public int getColor() {
        return color;
    }
}
