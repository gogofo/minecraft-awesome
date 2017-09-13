package gogofo.minecraft.awesome.init;

import gogofo.minecraft.awesome.AwesomeMod;
import gogofo.minecraft.awesome.colorize.ISingleColoredObject;
import gogofo.minecraft.awesome.colorize.SingleColorProvider;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public class RendersRegisterer {
    public static void registerRender(ItemBlock itemBlock) {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(itemBlock,
                0,
                new ModelResourceLocation(itemBlock.getRegistryName(), "inventory"));

        if (itemBlock.getBlock() instanceof ISingleColoredObject) {
            Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new SingleColorProvider((ISingleColoredObject)itemBlock.getBlock()), itemBlock);
        }
    }

    public static void registerRender(Item item) {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item,
                0,
                new ModelResourceLocation(AwesomeMod.MODID + ":" + item.getUnlocalizedName().substring(5),
                        "Inventory"));

        if (item instanceof ISingleColoredObject) {
            Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new SingleColorProvider((ISingleColoredObject)item), item);
        }
    }

    public static void registerColorProviderIfNeeded(Block block) {
        if (block instanceof ISingleColoredObject) {
            Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(new SingleColorProvider((ISingleColoredObject)block), block);
        }
    }
}