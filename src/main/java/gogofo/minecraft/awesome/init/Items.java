package gogofo.minecraft.awesome.init;

import java.util.ArrayList;

import gogofo.minecraft.awesome.AwesomeMod;
import gogofo.minecraft.awesome.item.*;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;
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
	public static Item wrench;
	public static Item tin_plating;
	public static Item battery;
	
	public static Item iron_dust;
	public static Item gold_dust;
	
	public static Item mob_essence;
	
	public static Item quartz_iron_ingot;
	
	// Food
	public static Item rich_melon;
	public static Item salt;
	
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

		wrench = registryItem(new ItemWrench(), "wrench");
		items.add(wrench);

		tin_plating = registryItem(new Item(), "tin_plating");
		items.add(tin_plating);

		battery = registryItem(new ItemBattery(), "battery");
		items.add(battery);
		
		// Dusts
		iron_dust = registryItem(new ItemOneColored(0xFFDEDEDE), "iron_dust");
		items.add(iron_dust);
		
		gold_dust = registryItem(new ItemOneColored(0xFFE721), "gold_dust");
		items.add(gold_dust);

		for (Ores.Ore ore : Ores.getOres()) {
			if (ore.isHasDust()) {
				Item dust = registryItem(new ItemOneColored(ore.getColor()), ore.getName() + "_dust");
				items.add(dust);
				ore.setDust(dust);
			}
		}
		
		// Ingots
		quartz_iron_ingot = registryItem(new Item(), "quartz_iron_ingot");
		items.add(quartz_iron_ingot);

		for (Ores.Ore ore : Ores.getOres()) {
			if (ore.isHasIngot()) {
				Item ingot = registryItem(new ItemOneColored(ore.getColor()), ore.getName() + "_ingot");
				items.add(ingot);
				ore.setIngot(ingot);
			}
		}
		
		// Food
		rich_melon = registryItem(new ItemFood(2, 0.9F, false), "rich_melon");
		items.add(rich_melon);

		salt = registryItem(new ItemFood(-1, -0.5F, false), "salt");
		items.add(salt);
		
		// Extractions
		
		mob_essence = registryItem(new Item(), "mob_essence");
		items.add(mob_essence);

		// Swords, tools and armors
		for (Ores.Ore ore : Ores.getOres()) {
			Ores.Ore.ToolsConfig config = ore.getToolsConfig();
			if (config == null) {
				continue;
			}

			if (config.isHasSword()) {
				Item sword = registryItem(new ItemAwesomeSword(config.getToolMaterial(), ore.getColor()), ore.getName() + "_sword");
				items.add(sword);
				ore.setSword(sword);
			}

			if (config.isHasWorkingTools()) {
				Item pickaxe = registryItem(new ItemAwesomePickaxe(config.getToolMaterial(), ore.getColor()), ore.getName() + "_pickaxe");
				items.add(pickaxe);
				ore.setPickaxe(pickaxe);

				Item axe = registryItem(new ItemAwesomeAxe(config.getToolMaterial(), ore.getColor()), ore.getName() + "_axe");
				items.add(axe);
				ore.setAxe(axe);

				Item hoe = registryItem(new ItemAwesomeHoe(config.getToolMaterial(), ore.getColor()), ore.getName() + "_hoe");
				items.add(hoe);
				ore.setHoe(hoe);

				Item shovel = registryItem(new ItemAwesomeShovel(config.getToolMaterial(), ore.getColor()), ore.getName() + "_shovel");
				items.add(shovel);
				ore.setShovel(shovel);
			}

			if (config.isHasArmors()) {
				Item chestplate = registryItem(new ItemAwesomeArmor(Materials.COPPER_ARMOR, 2, EntityEquipmentSlot.CHEST, ore.getColor()), ore.getName() + "_chestplate");
				items.add(chestplate);
				ore.setChestplate(chestplate);

				Item helmet = registryItem(new ItemAwesomeArmor(Materials.COPPER_ARMOR, 2, EntityEquipmentSlot.HEAD, ore.getColor()), ore.getName() + "_helmet");
				items.add(helmet);
				ore.setHelmet(helmet);

				Item leggings = registryItem(new ItemAwesomeArmor(Materials.COPPER_ARMOR, 2, EntityEquipmentSlot.LEGS, ore.getColor()), ore.getName() + "_leggings");
				items.add(leggings);
				ore.setLeggings(leggings);

				Item boots = registryItem(new ItemAwesomeArmor(Materials.COPPER_ARMOR, 2, EntityEquipmentSlot.FEET, ore.getColor()), ore.getName() + "_boots");
				items.add(boots);
				ore.setBoots(boots);
			}
		}
	}
	
	public static void registerRenders() {
		for (Item item : items) {
			RendersRegisterer.registerRender(item);
		}
	}
	
	private static Item registryItem(Item item, String name) {
		return item.setUnlocalizedName(name).setRegistryName(name).setCreativeTab(AwesomeMod.awesomeCreativeTab);
	}

	public static void registerOreDictEntries() {
		for (Ores.Ore ore : Ores.getOres()) {
			if (ore.isHasDust()) {
				OreDictionary.registerOre(ore.getDictName("dust"), ore.getDust());
			}

			if (ore.isHasIngot()) {
				OreDictionary.registerOre(ore.getDictName("ingot"), ore.getIngot());
			}
		}
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
