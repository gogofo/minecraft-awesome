package gogofo.minecraft.awesome.proxy;

import gogofo.minecraft.awesome.gui.GuiHandler;
import gogofo.minecraft.awesome.init.Blocks;
import gogofo.minecraft.awesome.init.Items;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class ClientProxy extends CommonProxy {
	@Override
	public void registerRenders() {
		Items.registerRenders();
		Blocks.registerRenders();
	}

}
