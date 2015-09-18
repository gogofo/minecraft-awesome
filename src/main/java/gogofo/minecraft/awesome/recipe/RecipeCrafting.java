package gogofo.minecraft.awesome.recipe;

import gogofo.minecraft.awesome.init.Blocks;
import gogofo.minecraft.awesome.init.Items;
import net.minecraft.block.Block;
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
				   'A', net.minecraft.init.Items.iron_ingot,
				   'B', Items.conductive);

		GameRegistry.addRecipe(stack(Items.machine_core, 1), 
							   "AAA",
							   "ABA",
							   "AAA",
							   'A', net.minecraft.init.Items.iron_ingot,
							   'B', Items.conductive);
		
		GameRegistry.addShapelessRecipe(stack(Blocks.generator, 1),
									    net.minecraft.init.Blocks.furnace,
									    Items.machine_core);
		
		GameRegistry.addRecipe(stack(Blocks.charger, 1), 
							   "ABA",
							   "ACA",
							   "ADA",
							   'A', net.minecraft.init.Items.redstone,
							   'B', net.minecraft.init.Blocks.iron_block,
							   'C', Items.machine_core,
							   'D', net.minecraft.init.Blocks.redstone_torch);
		
		GameRegistry.addRecipe(stack(Blocks.fuser, 1),
							   "ABC",
							   "DEF",
							   "GHI",
							   'A', net.minecraft.init.Blocks.planks,
							   'B', net.minecraft.init.Blocks.coal_block,
							   'C', net.minecraft.init.Blocks.redstone_block,
							   'D', net.minecraft.init.Blocks.lapis_block,
							   'E', Items.machine_core,
							   'F', net.minecraft.init.Blocks.iron_block,
							   'G', net.minecraft.init.Blocks.gold_block,
							   'H', net.minecraft.init.Items.lava_bucket,
							   'I', net.minecraft.init.Blocks.diamond_block);
		
		GameRegistry.addRecipe(stack(Blocks.grinder, 1), 
							   "AAA",
							   "BCB",
							   "DDD",
							   'A', net.minecraft.init.Items.diamond_pickaxe,
							   'B', net.minecraft.init.Items.glowstone_dust,
							   'C', Items.machine_core,
							   'D', net.minecraft.init.Items.redstone);
		
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
				   'A', net.minecraft.init.Items.minecart,
				   'B', net.minecraft.init.Items.diamond,
				   'C', Items.machine_core,
				   'D', net.minecraft.init.Items.gold_ingot,
				   'F', Items.quartz_iron_ingot,
				   'E', net.minecraft.init.Items.redstone);
		
		GameRegistry.addRecipe(stack(Blocks.extractor, 1), 
				   "ABA",
				   "ACA",
				   "ADA",
				   'A', net.minecraft.init.Blocks.obsidian,
				   'B', net.minecraft.init.Items.diamond,
				   'C', Items.machine_core,
				   'D', net.minecraft.init.Blocks.piston);
	}
	
	private void registerPipes() {
		GameRegistry.addRecipe(stack(Blocks.pipe, 6), 
				   "AAA",
				   "BCB",
				   "AAA",
				   'A', net.minecraft.init.Blocks.cobblestone,
				   'B', net.minecraft.init.Blocks.glass,
				   'C', net.minecraft.init.Blocks.hopper);
		
		GameRegistry.addRecipe(stack(Blocks.suction_pipe, 6), 
				   "AAA",
				   "BCB",
				   "AAA",
				   'A', net.minecraft.init.Blocks.planks,
				   'B', net.minecraft.init.Blocks.glass,
				   'C', net.minecraft.init.Blocks.hopper);
		
		GameRegistry.addRecipe(stack(Blocks.sorting_pipe, 6), 
				   "AAA",
				   "BCB",
				   "AAA",
				   'A', net.minecraft.init.Blocks.glass,
				   'B', net.minecraft.init.Items.diamond,
				   'C', net.minecraft.init.Blocks.hopper);
	}
	
	private void registerTools() {
		GameRegistry.addRecipe(stack(Items.conductive, 3),
							   "   ",
							   "AAA",
							   "   ",
							   'A', net.minecraft.init.Items.iron_ingot);
		
		GameRegistry.addRecipe(stack(Items.tool_core, 1), 
							   " A ",
							   "ABA",
							   " A ",
							   'A', net.minecraft.init.Blocks.stone,
							   'B', Items.conductive);
		
		GameRegistry.addRecipe(stack(Items.multimeter, 1),
							   "  A",
							   "BCB",
							   "BDB",
							  	'A', Items.conductive,
							  	'B', net.minecraft.init.Items.iron_ingot,
							  	'C', net.minecraft.init.Items.compass,
							  	'D', Items.tool_core);
		
		GameRegistry.addRecipe(stack(Items.liquid_pump, 1),
							   "ABA",
							   " A ",
							   'A', net.minecraft.init.Items.iron_ingot,
							   'B', Items.tool_core);
		
		GameRegistry.addRecipe(stack(Items.liquid_container, 1),
							   "AAA",
							   "ABA",
							   "AAA",
							   'A', net.minecraft.init.Items.bucket,
							   'B', Items.tool_core);

		GameRegistry.addRecipe(stack(Items.chainsaw, 1),
				   "AAA",
				   "ABA",
				   "AAA",
				   'A', net.minecraft.init.Items.iron_axe,
				   'B', Items.tool_core);
	}
	
	private void registerStuff() {
		GameRegistry.addRecipe(stack(net.minecraft.init.Blocks.torch, 2),
							   "A",
							   "B",
							   'A', Items.burnt_residue,
							   'B', net.minecraft.init.Items.stick);
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
