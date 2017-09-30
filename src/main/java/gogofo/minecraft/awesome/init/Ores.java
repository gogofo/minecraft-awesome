package gogofo.minecraft.awesome.init;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;

import java.util.ArrayList;

public class Ores {
    private static ArrayList<Ore> ores = new ArrayList<>();

    public static Ore copper;
    public static Ore tin;
    public static Ore uranium;
    public static Ore platinum;
    public static Ore aluminium;
    public static Ore bronze;

    public static void init() {
        copper = new Ore("copper", 0xDA8A67, true, true, true, 2)
                .setToolsConfig(new Ore.ToolsConfig(true, true, true, Materials.COPPER_TOOL, Materials.COPPER_ARMOR))
                .addGenerationConfig(new Ore.GenerationConfig(0, 200, 100, 25, 10));
        ores.add(copper); // cd7f32

        tin = new Ore("tin", 0xF9D8D8, true, true, true, 2)
                .addGenerationConfig(new Ore.GenerationConfig(0, 200, 100, 25, 10));
        ores.add(tin);

        uranium = new Ore("uranium", 0x32CD32, true, true, true, 1)
                .addGenerationConfig(new Ore.GenerationConfig(0, 40, 100, 1, 3));
        ores.add(uranium);

        platinum = new Ore("platinum", 0xE5E4E2, true, true, true, 2)
                .setToolsConfig(new Ore.ToolsConfig(true, true, true, Materials.PLATINUM_TOOL, Materials.PLATINUM_ARMOR))
                .addGenerationConfig(new Ore.GenerationConfig(0, 54, 100, 8, 6));
        ores.add(platinum);

        aluminium = new Ore("aluminium", 0xD2D3F9, true, true, true, 2)
                .addGenerationConfig(new Ore.GenerationConfig(0, 54, 100, 8, 6));
        ores.add(aluminium);

        bronze = new Ore("bronze", 0xCD7F32, false, true, true, 0);
        ores.add(bronze);
    }

    public static ArrayList<Ore> getOres() {
        return ores;
    }

    public static class Ore {
        private String name;
        private int color;
        private boolean hasBlock;
        private boolean hasIngot;
        private boolean hasDust;
        private int grindsToAmount;
        private ArrayList<GenerationConfig> generationConfigs;
        private ToolsConfig toolsConfig;

        private Block block;
        private Item Ingot;
        private Item dust;
        private Item sword;
        private Item pickaxe;
        private Item axe;
        private Item hoe;
        private Item shovel;
        private Item chestplate;
        private Item helmet;
        private Item leggings;
        private Item boots;

        public Ore(String name, int color, boolean hasBlock, boolean hasIngot, boolean hasDust, int grindsToAmount) {
            this.name = name;
            this.color = color;
            this.hasBlock = hasBlock;
            this.hasIngot = hasIngot;
            this.hasDust = hasDust;
            this.grindsToAmount = grindsToAmount;
            this.generationConfigs = new ArrayList<>();
            this.toolsConfig = null;
        }

        Ore addGenerationConfig(GenerationConfig config) {
            generationConfigs.add(config);
            return this;
        }

        Ore setToolsConfig(ToolsConfig toolsConfig) {
            this.toolsConfig = toolsConfig;
            return this;
        }

        public String getName() {
            return name;
        }

        public String getDictName(String prefix) {
            return prefix + name.substring(0, 1).toUpperCase() + name.substring(1);
        }

        public int getColor() {
            return color;
        }

        public boolean isHasBlock() {
            return hasBlock;
        }

        public boolean isHasIngot() {
            return hasIngot;
        }

        public boolean isHasDust() {
            return hasDust;
        }

        public int getGrindsToAmount() {
            return grindsToAmount;
        }

        public Block getBlock() {
            return block;
        }

        public void setBlock(Block block) {
            this.block = block;
        }

        public Item getIngot() {
            return Ingot;
        }

        public void setIngot(Item ingot) {
            Ingot = ingot;
        }

        public Item getDust() {
            return dust;
        }

        public void setDust(Item dust) {
            this.dust = dust;
        }

        public ArrayList<GenerationConfig> getGenerationConfigs() {
            return generationConfigs;
        }

        public ToolsConfig getToolsConfig() {
            return toolsConfig;
        }

        public Item getSword() {
            return sword;
        }

        public void setSword(Item sword) {
            this.sword = sword;
        }

        public Item getPickaxe() {
            return pickaxe;
        }

        public void setPickaxe(Item pickaxe) {
            this.pickaxe = pickaxe;
        }

        public Item getAxe() {
            return axe;
        }

        public void setAxe(Item axe) {
            this.axe = axe;
        }

        public Item getHoe() {
            return hoe;
        }

        public void setHoe(Item hoe) {
            this.hoe = hoe;
        }

        public Item getShovel() {
            return shovel;
        }

        public void setShovel(Item shovel) {
            this.shovel = shovel;
        }

        public Item getChestplate() {
            return chestplate;
        }

        public void setChestplate(Item chestplate) {
            this.chestplate = chestplate;
        }

        public Item getHelmet() {
            return helmet;
        }

        public void setHelmet(Item helmet) {
            this.helmet = helmet;
        }

        public Item getLeggings() {
            return leggings;
        }

        public void setLeggings(Item leggings) {
            this.leggings = leggings;
        }

        public Item getBoots() {
            return boots;
        }

        public void setBoots(Item boots) {
            this.boots = boots;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Ore ore = (Ore) o;

            return name != null ? name.equals(ore.name) : ore.name == null;
        }

        @Override
        public int hashCode() {
            return name != null ? name.hashCode() : 0;
        }

        public static class GenerationConfig {

            private int minHeight;
            private int maxHeight;
            private int chanceToSpawn;
            private int maxSpawns;
            private int maxOrePerSpawn;

            public GenerationConfig(int minHeight, int maxHeight, int chanceToSpawn, int maxSpawns, int maxOrePerSpawn) {
                this.minHeight = minHeight;
                this.maxHeight = maxHeight;
                this.chanceToSpawn = chanceToSpawn;
                this.maxSpawns = maxSpawns;
                this.maxOrePerSpawn = maxOrePerSpawn;
            }

            public int getMinHeight() {
                return minHeight;
            }

            public int getMaxHeight() {
                return maxHeight;
            }

            public int getChanceToSpawn() {
                return chanceToSpawn;
            }

            public int getMaxSpawns() {
                return maxSpawns;
            }

            public int getMaxOrePerSpawn() {
                return maxOrePerSpawn;
            }
        }

        public static class ToolsConfig {
            private boolean hasSword;
            private boolean hasWorkingTools;
            private boolean hasArmors;
            private Item.ToolMaterial toolMaterial;
            private ItemArmor.ArmorMaterial armorMaterial;

            public ToolsConfig(boolean hasSword, boolean hasWorkingTools, boolean hasArmors, Item.ToolMaterial toolMaterial, ItemArmor.ArmorMaterial armorMaterial) {
                this.hasSword = hasSword;
                this.hasWorkingTools = hasWorkingTools;
                this.hasArmors = hasArmors;
                this.toolMaterial = toolMaterial;
                this.armorMaterial = armorMaterial;
            }

            public boolean isHasSword() {
                return hasSword;
            }

            public boolean isHasWorkingTools() {
                return hasWorkingTools;
            }

            public boolean isHasArmors() {
                return hasArmors;
            }

            public Item.ToolMaterial getToolMaterial() {
                return toolMaterial;
            }

            public ItemArmor.ArmorMaterial getArmorMaterial() {
                return armorMaterial;
            }
        }
    }
}
