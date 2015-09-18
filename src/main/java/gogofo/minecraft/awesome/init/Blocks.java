package gogofo.minecraft.awesome.init;

import java.util.ArrayList;

import gogofo.minecraft.awesome.AwesomeMod;
import gogofo.minecraft.awesome.block.BlockCharger;
import gogofo.minecraft.awesome.block.BlockElectricFurnace;
import gogofo.minecraft.awesome.block.BlockElectricWire;
import gogofo.minecraft.awesome.block.BlockExtractor;
import gogofo.minecraft.awesome.block.BlockFuser;
import gogofo.minecraft.awesome.block.BlockGenerator;
import gogofo.minecraft.awesome.block.BlockGrinder;
import gogofo.minecraft.awesome.block.BlockPipe;
import gogofo.minecraft.awesome.block.BlockSortingPipe;
import gogofo.minecraft.awesome.block.BlockSuctionPipe;
import gogofo.minecraft.awesome.block.BlockTeleportPortal;
import gogofo.minecraft.awesome.block.BlockTeleporter;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

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
	
	private static ArrayList<Block> blocks = new ArrayList<Block>();

	public static void init() {
		generator = new BlockGenerator().setUnlocalizedName("generator");
		blocks.add(generator);
		
		electric_wire = new BlockElectricWire().setUnlocalizedName("electric_wire");
		blocks.add(electric_wire);
		
		fuser = new BlockFuser().setUnlocalizedName("fuser");
		blocks.add(fuser);
		
		charger = new BlockCharger().setUnlocalizedName("charger");
		blocks.add(charger);
		
		grinder = new BlockGrinder().setUnlocalizedName("grinder");
		blocks.add(grinder);
		
		electric_furnace = new BlockElectricFurnace().setUnlocalizedName("electric_furnace");
		blocks.add(electric_furnace);
		
		teleport_portal = new BlockTeleportPortal().setUnlocalizedName("teleport_portal");
		blocks.add(teleport_portal);
		
		teleporter = new BlockTeleporter().setUnlocalizedName("teleporter");
		blocks.add(teleporter);
		
		pipe = new BlockPipe().setUnlocalizedName("pipe");
		blocks.add(pipe);

		suction_pipe = new BlockSuctionPipe().setUnlocalizedName("suction_pipe");
		blocks.add(suction_pipe);
		
		sorting_pipe = new BlockSortingPipe().setUnlocalizedName("sorting_pipe");
		blocks.add(sorting_pipe);
		
		extractor = new BlockExtractor().setUnlocalizedName("extractor");
		blocks.add(extractor);
	}
	
	public static void register() {
		for (Block block : blocks) {
			registerBlock(block);
		}
	}
	
	private static void registerBlock(Block block) {
		GameRegistry.registerBlock(block, block.getUnlocalizedName().substring(5));
	}
	
	public static void registerRenders() {
		for (Block block : blocks) {
			registerRender(block);
		}
	}
	
	private static void registerRender(Block block) {
		Item item = Item.getItemFromBlock(block);
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 
				0, 
				new ModelResourceLocation(AwesomeMod.MODID + ":" + item.getUnlocalizedName().substring(5), 
										  "inventory"));
	}
}
