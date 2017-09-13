package gogofo.minecraft.awesome.init;

import gogofo.minecraft.awesome.AwesomeMod;
import gogofo.minecraft.awesome.block.*;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;

public class Blocks {

	public static Block generator;
	public static Block electric_wire;
	public static Block fuser;
	public static Block charger;
	public static Block grinder;
	public static Block electric_furnace;
	public static Block teleport_portal;
	public static Block teleporter;
	public static Block pipe;
	public static Block suction_pipe;
	public static Block sorting_pipe;
	public static Block extractor;
	public static BlockOil oil;
	public static Block salt_block;
	
	private static ArrayList<Block> blocks = new ArrayList<>();
	private static ArrayList<ItemBlock> itemBlocks = new ArrayList<>();

	public static void init() {
		generator = registryBlock(new BlockGenerator(), "generator");
		blocks.add(generator);
		
		electric_wire = registryBlock(new BlockElectricWire(), "electric_wire");
		blocks.add(electric_wire);
		
		fuser = registryBlock(new BlockFuser(), "fuser");
		blocks.add(fuser);
		
		charger = registryBlock(new BlockCharger(), "charger");
		blocks.add(charger);
		
		grinder = registryBlock(new BlockGrinder(), "grinder");
		blocks.add(grinder);
		
		electric_furnace = registryBlock(new BlockElectricFurnace(), "electric_furnace");
		blocks.add(electric_furnace);
		
		teleport_portal = registryBlock(new BlockTeleportPortal(), "teleport_portal");
		blocks.add(teleport_portal);
		
		teleporter = registryBlock(new BlockTeleporter(), "teleporter");
		blocks.add(teleporter);
		
		pipe = registryBlock(new BlockPipe(), "pipe");
		blocks.add(pipe);

		suction_pipe = registryBlock(new BlockSuctionPipe(), "suction_pipe");
		blocks.add(suction_pipe);
		
		sorting_pipe = registryBlock(new BlockSortingPipe(), "sorting_pipe");
		blocks.add(sorting_pipe);
		
		extractor = registryBlock(new BlockExtractor(), "extractor");
		blocks.add(extractor);

		oil = (BlockOil) registryBlock(new BlockOil(), "oil");
		blocks.add(oil);

		salt_block = registryBlock(new BlockSaltBlock(), "salt_block");
		blocks.add(salt_block);

		for (Ores.Ore ore : Ores.getOres()) {
			if (ore.isHasBlock()) {
				Block oreBlock = registryBlock(new BlockGenericOre(ore.getColor()), ore.getName() + "_ore");
				blocks.add(oreBlock);
				ore.setBlock(oreBlock);
			}
		}
		
		for (Block block : blocks) {
			ItemBlock itemBlock = new ItemBlock(block);
			itemBlock.setRegistryName(block.getRegistryName());
			itemBlocks.add(itemBlock);
		}
	}

	public static void registerRenders() {
		for (ItemBlock itemBlock : itemBlocks) {
			RendersRegisterer.registerRender(itemBlock);
		}

		for (Block block : blocks) {
			RendersRegisterer.registerColorProviderIfNeeded(block);
		}
	}

	public static void renderFluids() {
		oil.render();
	}
	
	private static Block registryBlock(Block block, String name) {
		return block.setUnlocalizedName(name).setRegistryName(name).setCreativeTab(AwesomeMod.awesomeCreativeTab);
	}

	public static void registerOreDictEntries() {
		for (Ores.Ore ore : Ores.getOres()) {
			if (ore.isHasBlock()) {
				OreDictionary.registerOre(ore.getDictName("ore"), ore.getBlock());
			}
		}
	}

	@Mod.EventBusSubscriber(modid = AwesomeMod.MODID)
	public static class RegistrationHandler {
		@SubscribeEvent
		public static void registerBlocks(final RegistryEvent.Register<Block> event) {
			final IForgeRegistry<Block> registry = event.getRegistry();

			for (Block block : blocks) {
				registry.register(block);
			}
		}
		
		@SubscribeEvent
		public static void registerItemBlocks(final RegistryEvent.Register<Item> event) {
			final IForgeRegistry<Item> registry = event.getRegistry();
			
			for (ItemBlock itemBlock : itemBlocks) {
				registry.register(itemBlock);
			}
		}
	}
}
