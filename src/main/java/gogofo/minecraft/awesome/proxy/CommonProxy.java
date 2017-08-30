package gogofo.minecraft.awesome.proxy;

import gogofo.minecraft.awesome.AwesomeMod;
import gogofo.minecraft.awesome.AwesomeWorldGenerator;
import gogofo.minecraft.awesome.gui.GuiHandler;
import gogofo.minecraft.awesome.init.Blocks;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {
	public void registerGui() {
		NetworkRegistry.INSTANCE.registerGuiHandler(AwesomeMod.MODID, new GuiHandler());
	}
	
	public void registerRenders() {
		
	}

	public void renderFluids() {
		Blocks.renderFluids();
	}

	public void registerWorldGenerators() {
		GameRegistry.registerWorldGenerator(new AwesomeWorldGenerator(), 0);
	}
}
