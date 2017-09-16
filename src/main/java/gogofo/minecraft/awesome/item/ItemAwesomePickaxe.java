package gogofo.minecraft.awesome.item;

import gogofo.minecraft.awesome.colorize.ISingleColoredObject;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSword;

public class ItemAwesomePickaxe extends ItemPickaxe implements ISingleColoredObject {

    private final int color;

    public ItemAwesomePickaxe(ToolMaterial material, int color) {
        super(material);
        this.color = color;
    }

    @Override
    public int getColor() {
        return color;
    }
}
