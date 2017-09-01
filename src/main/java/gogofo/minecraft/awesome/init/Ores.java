package gogofo.minecraft.awesome.init;

import java.util.ArrayList;

public class Ores {
    private static ArrayList<Ore> ores = new ArrayList<>();

    public static void init() {
        ores.add(new Ore("copper", 0xFFFF00FF, true, true, true));
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

        public Ore(String name, int color, boolean hasBlock, boolean hasIngot, boolean hasDust) {
            this.name = name;
            this.color = color;
            this.hasBlock = hasBlock;
            this.hasIngot = hasIngot;
            this.hasDust = hasDust;
        }

        public String getName() {
            return name;
        }

        public String getDictName() {
            return name.substring(0, 1).toUpperCase() + name.substring(1);
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
    }
}
