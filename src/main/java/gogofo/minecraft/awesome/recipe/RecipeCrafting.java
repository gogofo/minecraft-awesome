package gogofo.minecraft.awesome.recipe;

import gogofo.minecraft.awesome.init.Blocks;
import gogofo.minecraft.awesome.init.Items;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class RecipeCrafting {
	public void registerRecipes() {
		registerMachines();
		registerPipes();
		registerTools();
		registerStuff();
	}
	
	private void registerMachines() {
		GameRegistry.addRecipe(stack(Blocks.electric_wire, 6), 
				   "AAA",
				   "BBB",
				   "AAA",
				   'A', net.minecraft.init.Items.IRON_INGOT,
				   'B', Items.conductive);

		GameRegistry.addRecipe(stack(Items.machine_core, 1), 
							   "AAA",
							   "ABA",
							   "AAA",
							   'A', net.minecraft.init.Items.IRON_INGOT,
							   'B', Items.conductive);
		
		GameRegistry.addShapelessRecipe(stack(Blocks.generator, 1),
									    net.minecraft.init.Blocks.FURNACE,
									    Items.machine_core);
		
		GameRegistry.addRecipe(stack(Blocks.charger, 1), 
							   "ABA",
							   "ACA",
							   "ADA",
							   'A', net.minecraft.init.Items.REDSTONE,
							   'B', net.minecraft.init.Blocks.IRON_BLOCK,
							   'C', Items.machine_core,
							   'D', net.minecraft.init.Blocks.REDSTONE_TORCH);
		
		GameRegistry.addRecipe(stack(Blocks.fuser, 1),
							   "ABC",
							   "DEF",
							   "GHI",
							   'A', net.minecraft.init.Blocks.PLANKS,
							   'B', net.minecraft.init.Blocks.COAL_BLOCK,
							   'C', net.minecraft.init.Blocks.REDSTONE_BLOCK,
							   'D', net.minecraft.init.Blocks.LAPIS_BLOCK,
							   'E', Items.machine_core,
							   'F', net.minecraft.init.Blocks.IRON_BLOCK,
							   'G', net.minecraft.init.Blocks.GOLD_BLOCK,
							   'H', net.minecraft.init.Items.LAVA_BUCKET,
							   'I', net.minecraft.init.Blocks.DIAMOND_BLOCK);
		
		GameRegistry.addRecipe(stack(Blocks.grinder, 1), 
							   "AAA",
							   "BCB",
							   "DDD",
							   'A', net.minecraft.init.Items.DIAMOND_PICKAXE,
							   'B', net.minecraft.init.Items.GLOWSTONE_DUST,
							   'C', Items.machine_core,
							   'D', net.minecraft.init.Items.REDSTONE);
		
		GameRegistry.addRecipe(stack(Blocks.electric_furnace, 1), 
							   "AAA",
							   "ABA",
							   "AAA",
							   'A', Items.quartz_iron_ingot,
							   'B', Items.machine_core);
		
		GameRegistry.addRecipe(stack(Blocks.teleporter, 1), 
				   "AAA",
				   "BCD",
				   "FEF",
				   'A', net.minecraft.init.Items.MINECART,
				   'B', net.minecraft.init.Items.DIAMOND,
				   'C', Items.machine_core,
				   'D', net.minecraft.init.Items.GOLD_INGOT,
				   'F', Items.quartz_iron_ingot,
				   'E', net.minecraft.init.Items.REDSTONE);
		
		GameRegistry.addRecipe(stack(Blocks.extractor, 1), 
				   "ABA",
				   "ACA",
				   "ADA",
				   'A', net.minecraft.init.Blocks.OBSIDIAN,
				   'B', net.minecraft.init.Items.DIAMOND,
				   'C', Items.machine_core,
				   'D', net.minecraft.init.Blocks.PISTON);
	}
	
	private void registerPipes() {
		GameRegistry.addRecipe(stack(Blocks.pipe, 6), 
				   "AAA",
				   "BCB",
				   "AAA",
				   'A', net.minecraft.init.Blocks.COBBLESTONE,
				   'B', net.minecraft.init.Blocks.GLASS,
				   'C', net.minecraft.init.Blocks.HOPPER);
		
		GameRegistry.addRecipe(stack(Blocks.suction_pipe, 6), 
				   "AAA",
				   "BCB",
				   "AAA",
				   'A', net.minecraft.init.Blocks.PLANKS,
				   'B', net.minecraft.init.Blocks.GLASS,
				   'C', net.minecraft.init.Blocks.HOPPER);
		
		GameRegistry.addRecipe(stack(Blocks.sorting_pipe, 6), 
				   "AAA",
				   "BCB",
				   "AAA",
				   'A', net.minecraft.init.Blocks.GLASS,
				   'B', net.minecraft.init.Items.DIAMOND,
				   'C', net.minecraft.init.Blocks.HOPPER);
	}
	
	private void registerTools() {
		GameRegistry.addRecipe(stack(Items.conductive, 3),
							   "   ",
							   "AAA",
							   "   ",
							   'A', net.minecraft.init.Items.IRON_INGOT);
		
		GameRegistry.addRecipe(stack(Items.tool_core, 1), 
							   " A ",
							   "ABA",
							   " A ",
							   'A', net.minecraft.init.Blocks.STONE,
							   'B', Items.conductive);
		
		GameRegistry.addRecipe(stack(Items.multimeter, 1),
							   "  A",
							   "BCB",
							   "BDB",
							  	'A', Items.conductive,
							  	'B', net.minecraft.init.Items.IRON_INGOT,
							  	'C', net.minecraft.init.Items.COMPASS,
							  	'D', Items.tool_core);
		
		GameRegistry.addRecipe(stack(Items.liquid_pump, 1),
							   "ABA",
							   " A ",
							   'A', net.minecraft.init.Items.IRON_INGOT,
							   'B', Items.tool_core);
		
		GameRegistry.addRecipe(stack(Items.liquid_container, 1),
							   "AAA",
							   "ABA",
							   "AAA",
							   'A', net.minecraft.init.Items.BUCKET,
							   'B', Items.tool_core);

		GameRegistry.addRecipe(stack(Items.chainsaw, 1),
				   "AAA",
				   "ABA",
				   "AAA",
				   'A', net.minecraft.init.Items.IRON_AXE,
				   'B', Items.tool_core);
		
		GameRegistry.addRecipe(stack(Items.drill, 1),
				   "AAA",
				   "ABA",
				   "AAA",
				   'A', net.minecraft.init.Items.IRON_PICKAXE,
				   'B', Items.tool_core);
		
		ItemStack enchantedDrill = stack(Items.drill, 1);
		enchantedDrill.addEnchantment(Enchantments.EFFICIENCY, 5);
		enchantedDrill.addEnchantment(Enchantments.FORTUNE, 3);
		GameRegistry.addRecipe(enchantedDrill,
				   "AAA",
				   "ABA",
				   "ACA",
				   'A', net.minecraft.init.Items.DIAMOND,
				   'B', Items.drill,
				   'C', net.minecraft.init.Blocks.LAPIS_BLOCK);
	}
	
	private void registerStuff() {
		GameRegistry.addRecipe(stack(net.minecraft.init.Blocks.TORCH, 2),
							   "A",
							   "B",
							   'A', Items.burnt_residue,
							   'B', net.minecraft.init.Items.STICK);
	}
	
	private ItemStack stack(Item item, int size) {
		ItemStack stack = new ItemStack(item);
		stack.stackSize = size;
		return stack;
	}
	
	private ItemStack stack(Block block, int size) {
		ItemStack stack = new ItemStack(block);
		stack.stackSize = size;
		return stack;
	}
}
