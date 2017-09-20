package gogofo.minecraft.awesome.TileEntitySpecialRenderer;

import gogofo.minecraft.awesome.tileentity.TileEntityLiquidStorageContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

import java.util.Random;

public class TesrLiquidStorageContainer extends TileEntitySpecialRenderer<TileEntityLiquidStorageContainer> {

    private static final EntityItem ITEM = new EntityItem(Minecraft.getMinecraft().world, 0, 0, 0, new ItemStack(Items.POTATO, 1));

    @Override
    public void render(TileEntityLiquidStorageContainer te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        super.render(te, x, y, z, partialTicks, destroyStage, alpha);

//        GlStateManager.pushMatrix();
//        GlStateManager.translate(x, y, z);
//
//        GlStateManager.color(0x255, 0x255, 0x255, 0x128);
//        GlStateManager.disableTexture2D();
//        GlStateManager.disableCull();
//        GlStateManager.glBegin(GL11.GL_QUADS);
//
//        GlStateManager.glVertex3f(0, 0, 0);
//        GlStateManager.glVertex3f(1, 0, 0);
//        GlStateManager.glVertex3f(1, 1, 0);
//        GlStateManager.glVertex3f(0, 1, 0);
//
//        GlStateManager.glVertex3f(0, 0, 1);
//        GlStateManager.glVertex3f(1, 0, 1);
//        GlStateManager.glVertex3f(1, 1, 1);
//        GlStateManager.glVertex3f(0, 1, 1);
//
//        GlStateManager.glVertex3f(0, 0, 0);
//        GlStateManager.glVertex3f(1, 0, 0);
//        GlStateManager.glVertex3f(1, 0, 1);
//        GlStateManager.glVertex3f(0, 0, 1);
//
//        GlStateManager.glVertex3f(0, 1, 0);
//        GlStateManager.glVertex3f(1, 1, 0);
//        GlStateManager.glVertex3f(1, 1, 1);
//        GlStateManager.glVertex3f(0, 1, 1);
//
//        GlStateManager.glVertex3f(0, 0, 0);
//        GlStateManager.glVertex3f(0, 0, 1);
//        GlStateManager.glVertex3f(0, 1, 1);
//        GlStateManager.glVertex3f(0, 1, 0);
//
//        GlStateManager.glVertex3f(1, 0, 0);
//        GlStateManager.glVertex3f(1, 0, 1);
//        GlStateManager.glVertex3f(1, 1, 1);
//        GlStateManager.glVertex3f(1, 1, 0);
//
//        GlStateManager.glEnd();
//
//        GlStateManager.popMatrix();

        ITEM.hoverStart = 0F;

//        GlStateManager.pushMatrix();
//
//        GlStateManager.translate(x, y, z);
//        GlStateManager.translate(1F, -1.0F, 1.0F);
//        Minecraft.getMinecraft().getRenderManager().doRenderEntity(ITEM, x, y, z, 0F, 0F, false);
//
//        GlStateManager.popMatrix();


        GlStateManager.pushMatrix();
//        GlStateManager.translate(x, y+1.0f, z);
//        GlStateManager.translate(0.5, 0, 0.5);
//        GlStateManager.translate(-0.5, 0, -0.5);
//        GlStateManager.translate(0.5, -0.1875, 0);
        GlStateManager.translate(x, y, z);
        GlStateManager.translate(0.5, 0.25, 0.5);
        Minecraft.getMinecraft().getRenderManager().doRenderEntity(ITEM, 0, 0, 0, 0, 0, true);
        GlStateManager.popMatrix();
    }
}
