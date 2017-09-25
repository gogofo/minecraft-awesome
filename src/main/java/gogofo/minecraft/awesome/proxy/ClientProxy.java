package gogofo.minecraft.awesome.proxy;

import gogofo.minecraft.awesome.init.Blocks;
import gogofo.minecraft.awesome.init.Entities;
import gogofo.minecraft.awesome.init.Items;
import gogofo.minecraft.awesome.init.RendersRegisterer;

public class ClientProxy extends CommonProxy {
	@Override
	public void registerRenders() {
		Items.registerRenders();
		Blocks.registerRenders();
		RendersRegisterer.registerTileEntityRenderers();
	}

	@Override
	public void renderFluids() {
		Blocks.renderFluids();
	}

	@Override
	public void registerEntityRenders() {
		Entities.registerRenders();
	}
}
