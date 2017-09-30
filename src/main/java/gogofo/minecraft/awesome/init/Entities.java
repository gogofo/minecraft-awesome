package gogofo.minecraft.awesome.init;

import gogofo.minecraft.awesome.AwesomeMod;
import gogofo.minecraft.awesome.entity.EntityConstructor;
import gogofo.minecraft.awesome.entity.render.RenderConstructor;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Entities {

    public static void registerEntities() {
        EntityRegistry.registerModEntity(new ResourceLocation(AwesomeMod.MODID, "constructor"), EntityConstructor.class, "constructor", 10, AwesomeMod.instance, 80, 1, true);
    }

    @SideOnly(Side.CLIENT)
    public static void registerRenders() {
        RenderingRegistry.registerEntityRenderingHandler(EntityConstructor.class, new RenderConstructor.RenderFactory());
    }
}
