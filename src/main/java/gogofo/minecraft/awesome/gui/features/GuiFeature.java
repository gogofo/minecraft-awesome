package gogofo.minecraft.awesome.gui.features;

import net.minecraft.client.gui.GuiButton;

public interface GuiFeature {
    void onInitGui();
    void onDrawBackgroundLayer(float partialTicks, int mouseX, int mouseY);
    void onActionPerformed(GuiButton button);
}
