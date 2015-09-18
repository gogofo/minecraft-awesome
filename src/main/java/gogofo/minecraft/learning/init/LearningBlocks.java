package gogofo.minecraft.learning.init;

import gogofo.minecraft.learning.Reference;
import gogofo.minecraft.learning.blocks.TestBlock;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class LearningBlocks {
	
	public static Block test_block;
	
	public static void init() {
		test_block = new TestBlock().setUnlocalizedName("test_block");
	}
	
	public static void register() {
		GameRegistry.registerBlock(test_block, test_block.getUnlocalizedName().substring(5));
	}
	
	public static void registerRenders() {
		registerRender(test_block);
	}
	
	private static void registerRender(Block block) {
		Item item = Item.getItemFromBlock(block);
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 
				0, 
				new ModelResourceLocation(Reference.MODID + ":" + item.getUnlocalizedName().substring(5), 
										  "Inventory"));
	}
}
