package gogofo.minecraft.awesome.init;

import gogofo.minecraft.awesome.network.AwesomeControlSidesUpdateMessage;
import gogofo.minecraft.awesome.network.AwesomeNetworkHandler;
import net.minecraftforge.fml.relauncher.Side;

public class Messages {
	public static void init() {
		int discriminator = 0;
		
		AwesomeNetworkHandler.wrapper.registerMessage(AwesomeControlSidesUpdateMessage.MessageHandler.class, 
													  AwesomeControlSidesUpdateMessage.class, 
													  discriminator++, 
													  Side.SERVER);
	}
}
