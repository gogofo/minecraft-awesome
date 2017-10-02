package gogofo.minecraft.awesome.init;

import gogofo.minecraft.awesome.AwesomeMod;
import gogofo.minecraft.awesome.colorize.DynamicColorProvider;
import gogofo.minecraft.awesome.colorize.IDynamicColoredObjected;
import gogofo.minecraft.awesome.colorize.ISingleColoredObject;
import gogofo.minecraft.awesome.colorize.SingleColorProvider;
import gogofo.minecraft.awesome.tileentity.TileEntityLiquidStorageContainer;
import gogofo.minecraft.awesome.tileentity.TileEntityPipe;
import gogofo.minecraft.awesome.tileentity.renderer.TileEntityRendererLiquidStorageContainer;
import gogofo.minecraft.awesome.tileentity.renderer.TileEntityRendererPipe;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class RendersRegisterer {
    public static void registerRender(ItemBlock itemBlock) {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(itemBlock,
                0,
                new ModelResourceLocation(itemBlock.getRegistryName(), "inventory"));

        if (itemBlock.getBlock() instanceof ISingleColoredObject) {
            Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new SingleColorProvider((ISingleColoredObject)itemBlock.getBlock()), itemBlock);
        }

        if (itemBlock.getBlock() instanceof IDynamicColoredObjected) {
            Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new DynamicColorProvider((IDynamicColoredObjected) itemBlock.getBlock()), itemBlock);
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

        if (item instanceof IDynamicColoredObjected) {
            Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new DynamicColorProvider((IDynamicColoredObjected) item), item);
        }
    }

    public static void registerColorProviderIfNeeded(Block block) {
        if (block instanceof ISingleColoredObject) {
            Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(new SingleColorProvider((ISingleColoredObject)block), block);
        }

        if (block instanceof IDynamicColoredObjected) {
            Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new DynamicColorProvider((IDynamicColoredObjected) block), block);
        }
    }

    public static void registerTileEntityRenderers() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPipe.class, new TileEntityRendererPipe());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLiquidStorageContainer.class, new TileEntityRendererLiquidStorageContainer());
    }
}
