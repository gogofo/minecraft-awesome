package gogofo.minecraft.awesome.init;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

import java.util.ArrayList;

public class Ores {
    private static ArrayList<Ore> ores = new ArrayList<>();

    public static void init() {
        Ore ore = new Ore("copper", 0xFFDA8A67, true, true, true, 2)
                .addGenerationConfig(new Ore.GenerationConfig(0, 200, 100, 35, 8));
        ores.add(ore);

        ore = new Ore("tin", 0xFFD8D8D8, true, true, true, 2)
                .addGenerationConfig(new Ore.GenerationConfig(0, 200, 100, 35, 8));
        ores.add(ore);
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

        private Block block;
        private Item Ingot;
        private Item dust;

        public Ore(String name, int color, boolean hasBlock, boolean hasIngot, boolean hasDust, int grindsToAmount) {
            this.name = name;
            this.color = color;
            this.hasBlock = hasBlock;
            this.hasIngot = hasIngot;
            this.hasDust = hasDust;
            this.grindsToAmount = grindsToAmount;
            this.generationConfigs = new ArrayList<>();
        }

        public Ore addGenerationConfig(GenerationConfig config) {
            generationConfigs.add(config);
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
    }
}
