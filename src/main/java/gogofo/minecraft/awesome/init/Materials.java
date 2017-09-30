package gogofo.minecraft.awesome.init;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;

public class Materials {
    public static ToolMaterial BRONZE_TOOL;
    public static ToolMaterial PLATINUM_TOOL;

    public static ArmorMaterial BRONZE_ARMOR;
    public static ArmorMaterial PLATINUM_ARMOR;

    public static Material OIL = (new MaterialLiquid(MapColor.WATER));
    public static Material SAP = (new MaterialLiquid(MapColor.WATER));

    static {
        BRONZE_TOOL = EnumHelper.addToolMaterial("BRONZE",
                                                 ToolMaterial.IRON.getHarvestLevel(),
                                                 200, // Less than iron
                                                 ToolMaterial.IRON.getEfficiencyOnProperMaterial(),
                                                 ToolMaterial.IRON.getDamageVsEntity(),
                                                 ToolMaterial.IRON.getEnchantability());

        PLATINUM_TOOL = EnumHelper.addToolMaterial("PLATINUM",
                                                   ToolMaterial.IRON.getHarvestLevel(),
                                                   500, // Double from iron
                                                   ToolMaterial.IRON.getEfficiencyOnProperMaterial(),
                                                   ToolMaterial.IRON.getDamageVsEntity(),
                                                   ToolMaterial.IRON.getEnchantability());

        BRONZE_ARMOR = EnumHelper.addArmorMaterial("BRONZE",
                                                   "awesome:generic",
                                                   10, // Less than iron
                                                   new int[]{2, 5, 6, 2},
                                                   ArmorMaterial.IRON.getEnchantability(),
                                                   ArmorMaterial.IRON.getSoundEvent(),
                                                   ArmorMaterial.IRON.getToughness());

        PLATINUM_ARMOR = EnumHelper.addArmorMaterial("PLATINUM",
                                                     "awesome:generic",
                                                     20, // More than iron, less than diamond
                                                     new int[]{2, 5, 6, 2},
                                                     10, // More than iron, like diamond
                                                     ArmorMaterial.IRON.getSoundEvent(),
                                                     0.5F); // More than iron, less than diamond
    }
}
