package gogofo.minecraft.awesome.entity.render;

import gogofo.minecraft.awesome.entity.EntityConstructor;
import gogofo.minecraft.awesome.models.ModelCube;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

@SideOnly(Side.CLIENT)
public class RenderConstructor extends RenderCube<EntityConstructor> {
    
    public RenderConstructor(RenderManager renderManagerIn)
    {
        super(renderManagerIn);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityConstructor entity) {
        return new ResourceLocation("textures/blocks/planks_oak.png");
    }

    public static class RenderFactory implements IRenderFactory<EntityConstructor> {
        @Override
        public Render<? super EntityConstructor> createRenderFor(RenderManager manager) {
            return new RenderConstructor(manager);
        }
    }
}
