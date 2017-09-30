package gogofo.minecraft.awesome.entity.render;

import gogofo.minecraft.awesome.entity.EntityBlock;
import gogofo.minecraft.awesome.models.ModelBlock;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;

public abstract class RenderBlock<T extends EntityBlock> extends Render<T> {

    private ModelBlock model = new ModelBlock();

    protected RenderBlock(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x+0.5, y+0.5, z+0.5);
        GlStateManager.rotate(180, 0, 0, 1);
        rotateAccordingToFacing(entity);
        GlStateManager.translate(-0.5, -0.5, -0.5);
        this.bindEntityTexture(entity);

        model.render(entity, partialTicks, 0, 0, 0, 0, 0.0625f);

        GlStateManager.popMatrix();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    private void rotateAccordingToFacing(T entity) {
        EnumFacing facing = entity.getFacing();
        switch (facing) {
            case SOUTH:
                GlStateManager.rotate(180, 0, 1, 0);
                break;
            case WEST:
                GlStateManager.rotate(-90, 0, 1, 0);
                break;
            case EAST:
                GlStateManager.rotate(90, 0, 1, 0);
                break;
            default:
                break;
        }
    }
}
