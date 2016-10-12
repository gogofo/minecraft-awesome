package gogofo.minecraft.awesome.init;

import java.util.ArrayList;

import com.sun.org.apache.xpath.internal.operations.Mult;

import gogofo.minecraft.awesome.AwesomeMod;
import gogofo.minecraft.awesome.item.ItemChainsaw;
import gogofo.minecraft.awesome.item.ItemDrill;
import gogofo.minecraft.awesome.item.ItemLiquidContainer;
import gogofo.minecraft.awesome.item.ItemLiquidPump;
import gogofo.minecraft.awesome.item.ItemMultimeter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraftforge.fml.common.registry.GameRegistry;

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
		burnt_residue = new Item().setUnlocalizedName("burnt_residue");
		items.add(burnt_residue);
		
		multimeter =  new ItemMultimeter().setUnlocalizedName("multimeter");
		items.add(multimeter);
		
		liquid_pump = new ItemLiquidPump().setUnlocalizedName("liquid_pump");
		items.add(liquid_pump);
		
		liquid_container = new ItemLiquidContainer().setUnlocalizedName("liquid_container");
		items.add(liquid_container);
		
		machine_core = new Item().setUnlocalizedName("machine_core");
		items.add(machine_core);
		
		tool_core = new Item().setUnlocalizedName("tool_core");
		items.add(tool_core);
		
		conductive = new Item().setUnlocalizedName("conductive");
		items.add(conductive);
		
		chainsaw = new ItemChainsaw().setUnlocalizedName("chainsaw");
		items.add(chainsaw);
		
		drill = new ItemDrill().setUnlocalizedName("drill");
		items.add(drill);
		
		// Dusts
		iron_dust = new Item().setUnlocalizedName("iron_dust");
		items.add(iron_dust);
		
		gold_dust = new Item().setUnlocalizedName("gold_dust");
		items.add(gold_dust);
		
		// Ingots
		quartz_iron_ingot = new Item().setUnlocalizedName("quartz_iron_ingot");
		items.add(quartz_iron_ingot);
		
		// Food
		rich_melon = new ItemFood(2, 0.9F, false).setUnlocalizedName("rich_melon");
		items.add(rich_melon);
		
		// Extractions
		
		mob_essence = new Item().setUnlocalizedName("mob_essence");
		items.add(mob_essence);
	}
	
	public static void register() {
		for (Item item : items) {
			GameRegistry.registerItem(item, item.getUnlocalizedName().substring(5));
			item.setCreativeTab(AwesomeMod.awesomeCreativeTab);
		}
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
	}
}
