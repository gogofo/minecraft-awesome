package gogofo.minecraft.awesome.proxy;

import gogofo.minecraft.awesome.init.Blocks;
import gogofo.minecraft.awesome.init.Items;

public class ClientProxy extends CommonProxy {
	@Override
	public void registerRenders() {
		Items.registerRenders();
		Blocks.registerRenders();
	}

	@Override
	public void renderFluids() {
		Blocks.renderFluids();
	}
}
