package gogofo.minecraft.awesome.item;

import gogofo.minecraft.awesome.colorize.ISingleColoredObject;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;

public class ItemAwesomeArmor extends ItemArmor implements ISingleColoredObject {

    private final int color;

    public ItemAwesomeArmor(ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn, int color) {
        super(materialIn, renderIndexIn, equipmentSlotIn);

        this.color = color;
    }

    @Override
    public int getColor(ItemStack stack) {
        return color;
    }

    @Override
    public boolean hasColor(ItemStack stack) {
        return true;
    }

    @Override
    public int getColor() {
        return color;
    }
}
