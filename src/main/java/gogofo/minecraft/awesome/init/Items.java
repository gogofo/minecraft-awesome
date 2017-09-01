package gogofo.minecraft.awesome.init;

import java.util.ArrayList;

import gogofo.minecraft.awesome.AwesomeMod;
import gogofo.minecraft.awesome.item.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class Items {
	
	public static Item burnt_residue;
	public static Item multimeter;
	public static Item liquid_pump;
	public static Item liquid_container;
	public static Item machine_core;
	public static Item tool_core;
	public static Item conductive;
	public static Item chainsaw;
	public static Item drill;
	
	public static Item iron_dust;
	public static Item gold_dust;
	
	public static Item mob_essence;
	
	public static Item quartz_iron_ingot;
	
	// Food
	public static Item rich_melon;
	
	private static ArrayList<Item> items = new ArrayList<Item>();
	
	public static void init() {
		burnt_residue = registryItem(new Item(), "burnt_residue");
		items.add(burnt_residue);
		
		multimeter =  registryItem(new ItemMultimeter(), "multimeter");
		items.add(multimeter);
		
		liquid_pump = registryItem(new ItemLiquidPump(), "liquid_pump");
		items.add(liquid_pump);
		
		liquid_container = registryItem(new ItemLiquidContainer(), "liquid_container");
		items.add(liquid_container);
		
		machine_core = registryItem(new Item(), "machine_core");
		items.add(machine_core);
		
		tool_core = registryItem(new Item(), "tool_core");
		items.add(tool_core);
		
		conductive = registryItem(new Item(), "conductive");
		items.add(conductive);
		
		chainsaw = registryItem(new ItemChainsaw(), "chainsaw");
		items.add(chainsaw);
		
		drill = registryItem(new ItemDrill(), "drill");
		items.add(drill);
		
		// Dusts
		iron_dust = registryItem(new ItemOneColored(0xFFDEDEDE), "iron_dust");
		items.add(iron_dust);
		
		gold_dust = registryItem(new ItemOneColored(0xFFE721), "gold_dust");
		items.add(gold_dust);
		
		// Ingots
		quartz_iron_ingot = registryItem(new Item(), "quartz_iron_ingot");
		items.add(quartz_iron_ingot);
		
		// Food
		rich_melon = registryItem(new ItemFood(2, 0.9F, false), "rich_melon");
		items.add(rich_melon);
		
		// Extractions
		
		mob_essence = registryItem(new Item(), "mob_essence");
		items.add(mob_essence);
	}
	
	public static void registerRenders() {
		for (Item item : items) {
			registerRender(item);
		}
	}
	
	private static void registerRender(Item item) {
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 
																				0, 
																				new ModelResourceLocation(AwesomeMod.MODID + ":" + item.getUnlocalizedName().substring(5), 
																										  "Inventory"));

		if (item instanceof ItemOneColored) {
			Minecraft.getMinecraft().getItemColors().registerItemColorHandler((ItemOneColored)item, item);
		}
	}
	
	private static Item registryItem(Item item, String name) {
		return item.setUnlocalizedName(name).setRegistryName(name).setCreativeTab(AwesomeMod.awesomeCreativeTab);
	}
	
	@Mod.EventBusSubscriber(modid = AwesomeMod.MODID)
	public static class RegistrationHandler {
		@SubscribeEvent
		public static void registerBlocks(final RegistryEvent.Register<Item> event) {
			final IForgeRegistry<Item> registry = event.getRegistry();
			
			for (Item item : items) {
				registry.register(item);
			}
		}
	}
}
