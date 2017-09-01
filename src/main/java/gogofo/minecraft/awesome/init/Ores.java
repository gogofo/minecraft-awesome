package gogofo.minecraft.awesome.init;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

import java.util.ArrayList;

public class Ores {
    private static ArrayList<Ore> ores = new ArrayList<>();

    public static void init() {
        ores.add(new Ore("copper", 0xFFDA8A67, true, true, true, 2));
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
    }
}
