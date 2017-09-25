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
public class RenderConstructor extends Render<EntityConstructor> {

    private ModelCube model = new ModelCube();

    public RenderConstructor(RenderManager renderManagerIn)
    {
        super(renderManagerIn);
    }

    @Override
    public void doRender(EntityConstructor entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        this.bindEntityTexture(entity);

        model.render(entity, partialTicks, 0, 0, 0, 0, 0.0625f);

        GlStateManager.popMatrix();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
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
