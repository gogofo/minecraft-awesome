package gogofo.minecraft.awesome.TileEntitySpecialRenderer;

import gogofo.minecraft.awesome.tileentity.TileEntityLiquidStorageContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import org.lwjgl.opengl.GL11;

public class TesrLiquidStorageContainer extends TileEntitySpecialRenderer<TileEntityLiquidStorageContainer> {

    @Override
    public void render(TileEntityLiquidStorageContainer te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        super.render(te, x, y, z, partialTicks, destroyStage, alpha);

        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);

        GlStateManager.color(0x255, 0x255, 0x255);
        GlStateManager.disableTexture2D();
        GlStateManager.disableCull();
        GlStateManager.glBegin(GL11.GL_QUADS);

        GlStateManager.glVertex3f(0, 0, 0);
        GlStateManager.glVertex3f(1, 0, 0);
        GlStateManager.glVertex3f(1, 1, 0);
        GlStateManager.glVertex3f(0, 1, 0);

        GlStateManager.glVertex3f(0, 0, 1);
        GlStateManager.glVertex3f(1, 0, 1);
        GlStateManager.glVertex3f(1, 1, 1);
        GlStateManager.glVertex3f(0, 1, 1);

        GlStateManager.glVertex3f(0, 0, 0);
        GlStateManager.glVertex3f(1, 0, 0);
        GlStateManager.glVertex3f(1, 0, 1);
        GlStateManager.glVertex3f(0, 0, 1);

        GlStateManager.glVertex3f(0, 1, 0);
        GlStateManager.glVertex3f(1, 1, 0);
        GlStateManager.glVertex3f(1, 1, 1);
        GlStateManager.glVertex3f(0, 1, 1);

        GlStateManager.glVertex3f(0, 0, 0);
        GlStateManager.glVertex3f(0, 0, 1);
        GlStateManager.glVertex3f(0, 1, 1);
        GlStateManager.glVertex3f(0, 1, 0);

        GlStateManager.glVertex3f(1, 0, 0);
        GlStateManager.glVertex3f(1, 0, 1);
        GlStateManager.glVertex3f(1, 1, 1);
        GlStateManager.glVertex3f(1, 1, 0);

        GlStateManager.glEnd();

        GlStateManager.popMatrix();
    }
}
