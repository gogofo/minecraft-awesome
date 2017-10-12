package gogofo.minecraft.awesome.utils;

import net.minecraft.client.renderer.GlStateManager;

public class RenderUtils {

    public static void renderCube(float posX, float negX, float posY, float negY, float posZ, float negZ) {
        // Top face
        GlStateManager.glVertex3f(posX, posY, negZ);
        GlStateManager.glVertex3f(negX, posY, negZ);
        GlStateManager.glVertex3f(negX, posY, posZ);
        GlStateManager.glVertex3f(posX, posY, posZ);

        // Bottom face
        GlStateManager.glVertex3f(posX, negY, posZ);
        GlStateManager.glVertex3f(negX, negY, posZ);
        GlStateManager.glVertex3f(negX, negY, negZ);
        GlStateManager.glVertex3f(posX, negY, negZ);

        // Front face
        GlStateManager.glVertex3f(posX, posY, posZ);
        GlStateManager.glVertex3f(negX, posY, posZ);
        GlStateManager.glVertex3f(negX, negY, posZ);
        GlStateManager.glVertex3f(posX, negY, posZ);

        // Back face
        GlStateManager.glVertex3f(posX, negY, negZ);
        GlStateManager.glVertex3f(negX, negY, negZ);
        GlStateManager.glVertex3f(negX, posY, negZ);
        GlStateManager.glVertex3f(posX, posY, negZ);

        // Left face
        GlStateManager.glVertex3f(negX, posY, posZ);
        GlStateManager.glVertex3f(negX, posY, negZ);
        GlStateManager.glVertex3f(negX, negY, negZ);
        GlStateManager.glVertex3f(negX, negY,  posZ);

        // Right face
        GlStateManager.glVertex3f(posX, posY, negZ);
        GlStateManager.glVertex3f(posX, posY, posZ);
        GlStateManager.glVertex3f(posX, negY, posZ);
        GlStateManager.glVertex3f(posX, negY, negZ);
    }
}
