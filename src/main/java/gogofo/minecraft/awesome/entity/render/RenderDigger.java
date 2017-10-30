package gogofo.minecraft.awesome.entity.render;

import gogofo.minecraft.awesome.entity.EntityDigger;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

@SideOnly(Side.CLIENT)
public class RenderDigger extends RenderBlock<EntityDigger> {

    public RenderDigger(RenderManager renderManagerIn)
    {
        super(renderManagerIn);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityDigger entity) {
        return new ResourceLocation("awesome", "textures/entities/digger.png");
    }

    public static class RenderFactory implements IRenderFactory<EntityDigger> {
        @Override
        public Render<? super EntityDigger> createRenderFor(RenderManager manager) {
            return new RenderDigger(manager);
        }
    }
}
