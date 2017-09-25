package gogofo.minecraft.awesome.entity.render;

import gogofo.minecraft.awesome.models.ModelCube;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;

public abstract class RenderCube<T extends Entity> extends Render<T> {

    private ModelCube model = new ModelCube();

    protected RenderCube(RenderManager renderManager) {
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
