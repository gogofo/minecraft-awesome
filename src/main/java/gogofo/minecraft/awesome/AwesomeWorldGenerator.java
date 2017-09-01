package gogofo.minecraft.awesome;

import gogofo.minecraft.awesome.init.Blocks;
import gogofo.minecraft.awesome.init.Ores;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenLakes;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class AwesomeWorldGenerator implements IWorldGenerator {
    private WorldGenerator gen_oil;
    private HashMap<Ores.Ore, WorldGenerator> gen_ores = new HashMap<>();

    public AwesomeWorldGenerator() {
        gen_oil = new WorldGenLakes(Blocks.oil);

        for (Ores.Ore ore : Ores.getOres()) {
            for (Ores.Ore.GenerationConfig config : ore.getGenerationConfigs()) {
                WorldGenerator gen = new WorldGenMinable(ore.getBlock().getDefaultState(), config.getMaxOrePerSpawn());
                gen_ores.put(ore, gen);
            }
        }
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        switch (world.provider.getDimension()) {
            case 0: //Overworld
                runGenerator(gen_oil, world, random, chunkX, chunkZ, 30, 1, 0, 50, 8);

                for (Ores.Ore ore : Ores.getOres()) {
                    for (Ores.Ore.GenerationConfig config : ore.getGenerationConfigs()) {
                        runGenerator(gen_ores.get(ore), world, random, chunkX, chunkZ, config.getChanceToSpawn(), config.getMaxSpawns(), config.getMinHeight(), config.getMaxHeight(), 0);
                    }
                }

                break;
            case -1: //Nether

                break;
            case 1: //End

                break;
        }
    }

    private void runGenerator(WorldGenerator generator, World world, Random rand, int chunk_X, int chunk_Z, int percentToSpawn, int maxSpawns, int minHeight, int maxHeight, int offset) {
        if (minHeight < 0 || maxHeight > 256 || minHeight > maxHeight)
            throw new IllegalArgumentException("Illegal Height Arguments for WorldGenerator");

        if (percentToSpawn < rand.nextInt(100)) {
            return;
        }

        int heightDiff = maxHeight - minHeight + 1;
        for (int i = 0; i < maxSpawns; i ++) {
            int x = chunk_X * 16 + rand.nextInt(16);
            int y = minHeight + rand.nextInt(heightDiff);
            int z = chunk_Z * 16 + rand.nextInt(16);
            generator.generate(world, rand, new BlockPos(x + offset, y + offset, z + offset));
        }
    }
}
