package gogofo.minecraft.awesome.init;

import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;

public class Materials {
    public static ToolMaterial COPPER_TOOL;

    public static ArmorMaterial COPPER_ARMOR;

    static {
        COPPER_TOOL = EnumHelper.addToolMaterial("COPPER",
                ToolMaterial.IRON.getHarvestLevel(),
                ToolMaterial.IRON.getMaxUses(),
                ToolMaterial.IRON.getEfficiencyOnProperMaterial(),
                ToolMaterial.IRON.getDamageVsEntity(),
                ToolMaterial.IRON.getEnchantability());

        COPPER_ARMOR = EnumHelper.addArmorMaterial("COPPER", "awesome:generic", 15, new int[]{2, 5, 6, 2}, ArmorMaterial.IRON.getEnchantability(), ArmorMaterial.IRON.getSoundEvent(), ArmorMaterial.IRON.getToughness());
    }
}
