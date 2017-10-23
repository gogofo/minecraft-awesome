package gogofo.minecraft.awesome.init;

import gogofo.minecraft.awesome.AwesomeMod;
import gogofo.minecraft.awesome.entity.EntityConstructor;
import gogofo.minecraft.awesome.entity.EntityDigger;
import gogofo.minecraft.awesome.entity.render.RenderConstructor;
import gogofo.minecraft.awesome.entity.render.RenderDigger;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Entities {

    public static void registerEntities() {
        EntityRegistry.registerModEntity(new ResourceLocation(AwesomeMod.MODID, "constructor"), EntityConstructor.class, "constructor", 10, AwesomeMod.instance, 80, 1, true);
        EntityRegistry.registerModEntity(new ResourceLocation(AwesomeMod.MODID, "digger"), EntityDigger.class, "digger", 11, AwesomeMod.instance, 80, 1, true);
    }

    @SideOnly(Side.CLIENT)
    public static void registerRenders() {
        RenderingRegistry.registerEntityRenderingHandler(EntityConstructor.class, new RenderConstructor.RenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(EntityDigger.class, new RenderDigger.RenderFactory());
    }
}
