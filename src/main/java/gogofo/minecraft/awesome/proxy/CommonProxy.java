package gogofo.minecraft.awesome.proxy;

import gogofo.minecraft.awesome.AwesomeMod;
import gogofo.minecraft.awesome.gui.GuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class CommonProxy {
	public void registerGui() {
		NetworkRegistry.INSTANCE.registerGuiHandler(AwesomeMod.MODID, new GuiHandler());
	}
	
	public void registerRenders() {
		
	}
}
