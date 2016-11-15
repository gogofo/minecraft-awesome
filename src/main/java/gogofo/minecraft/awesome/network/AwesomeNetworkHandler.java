package gogofo.minecraft.awesome.network;

import gogofo.minecraft.awesome.AwesomeMod;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

public class AwesomeNetworkHandler {
	public static final SimpleNetworkWrapper wrapper = 
			NetworkRegistry.INSTANCE.newSimpleChannel(AwesomeMod.MODID);
}
