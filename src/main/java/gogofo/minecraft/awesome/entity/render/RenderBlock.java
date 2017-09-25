package gogofo.minecraft.awesome.entity.render;

import gogofo.minecraft.awesome.models.ModelBlock;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;

public abstract class RenderBlock<T extends Entity> extends Render<T> {

    private ModelBlock model = new ModelBlock();

    protected RenderBlock(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        this.bindEntityTexture(entity);

        model.render(entity, partialTicks, 0, 0, 0, 0, 0.0625f);

        GlStateManager.popMatrix();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }
}
