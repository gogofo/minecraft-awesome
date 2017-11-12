package gogofo.minecraft.awesome.tileentity.renderer;

import gogofo.minecraft.awesome.tileentity.TileEntityPipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;

public class TileEntityRendererPipe extends TileEntitySpecialRenderer<TileEntityPipe> {

    @Override
    public void render(TileEntityPipe te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        if (!te.isTransparent()) {
            return;
        }

        for (int i = 0; i < te.getTransferSlotCount(); i++) {
            ItemStack stack = te.getStackInSlot(i);
            if (!stack.isEmpty()) {
                drawStack(stack, x, y, z, te);
            }
        }
    }

    private void drawStack(ItemStack stack, double x, double y, double z, TileEntityPipe te) {
        EntityItem item = new EntityItem(Minecraft.getMinecraft().world, 0, 0, 0, stack);
        item.hoverStart = 0;

        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        GlStateManager.translate(0.5, 0.125, 0.45);
        GlStateManager.rotate((180 / TileEntityPipe.TRANSFER_COOLDOWN_MIN * te.getStackCooldown(stack)) % 360, 0, 1, 0);
        Minecraft.getMinecraft().getRenderManager().doRenderEntity(item, 0, 0, 0, 0, 0, true);
        GlStateManager.popMatrix();
    }
}
