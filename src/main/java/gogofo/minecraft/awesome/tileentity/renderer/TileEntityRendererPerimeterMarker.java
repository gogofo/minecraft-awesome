package gogofo.minecraft.awesome.tileentity.renderer;

import gogofo.minecraft.awesome.tileentity.TileEntityPerimeterMarker;
import gogofo.minecraft.awesome.utils.RenderUtils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import org.lwjgl.opengl.GL11;

public class TileEntityRendererPerimeterMarker extends TileEntitySpecialRenderer<TileEntityPerimeterMarker> {

    private final static float LASER_SIZE = 0.05f;

    @Override
    public void render(TileEntityPerimeterMarker te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {

        float north = 0.63f;
        float west = 0.63f;
        float south = 0.62f + te.getSouthMatchDistance();
        float east = 0.62f + te.getEastMatchDistance();

        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        GlStateManager.disableCull();
        GlStateManager.disableTexture2D();
        GlStateManager.disableLighting();
        GlStateManager.disableBlend();
        GlStateManager.glBegin(GL11.GL_QUADS);
        GlStateManager.color(1.0f, 0f, 0f, 0.2f);

        if (te.getEastMatchDistance() > 0) {
            RenderUtils.renderCube(east, west, 0.63f + (LASER_SIZE / 2), 0.63f - (LASER_SIZE / 2), 0.5f + (LASER_SIZE / 2), 0.5f - (LASER_SIZE / 2));
        }

        if (te.getSouthMatchDistance() > 0) {
            RenderUtils.renderCube(0.5f + (LASER_SIZE / 2), 0.5f - (LASER_SIZE / 2), 0.63f + (LASER_SIZE / 2), 0.63f - (LASER_SIZE / 2), south, north);
        }

        GlStateManager.glEnd();

        GlStateManager.popMatrix();
    }

    @Override
    public boolean isGlobalRenderer(TileEntityPerimeterMarker te) {
        return true;
    }
}
