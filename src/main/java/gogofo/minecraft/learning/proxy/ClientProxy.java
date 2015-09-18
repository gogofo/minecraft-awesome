package gogofo.minecraft.learning.proxy;

import gogofo.minecraft.learning.init.LearningBlocks;
import gogofo.minecraft.learning.init.LearningItems;

public class ClientProxy extends CommonProxy {
	@Override
	public void registerRenders() {
		LearningItems.registerRenders();
		LearningBlocks.registerRenders();
	}
}
