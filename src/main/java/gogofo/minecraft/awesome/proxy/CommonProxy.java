package gogofo.minecraft.awesome.proxy;

import gogofo.minecraft.awesome.AwesomeMod;
import gogofo.minecraft.awesome.WorldGen.AwesomeWorldGenerator;
import gogofo.minecraft.awesome.gui.GuiHandler;
import gogofo.minecraft.awesome.init.Blocks;
import gogofo.minecraft.awesome.init.Items;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {
	public void registerGui() {
		NetworkRegistry.INSTANCE.registerGuiHandler(AwesomeMod.MODID, new GuiHandler());
	}
	
	public void registerRenders() {
		
	}

	public void registerWorldGenerators() {
		GameRegistry.registerWorldGenerator(new AwesomeWorldGenerator(), 0);
	}

	public void registerOreDictEntries() {
		Blocks.registerOreDictEntries();
		Items.registerOreDictEntries();
	}
}
