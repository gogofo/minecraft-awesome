package gogofo.minecraft.awesome.item;

import gogofo.minecraft.awesome.colorize.ISingleColoredObject;
import net.minecraft.item.ItemSword;

public class ItemAwesomeSword extends ItemSword implements ISingleColoredObject {

    private final int color;

    public ItemAwesomeSword(ToolMaterial material, int color) {
        super(material);
        this.color = color;
    }

    @Override
    public int getColor() {
        return color;
    }
}
