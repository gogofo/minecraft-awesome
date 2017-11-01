package gogofo.minecraft.awesome;

import gogofo.minecraft.awesome.creativetabs.AwesomeCreativeTab;
import gogofo.minecraft.awesome.init.*;
import gogofo.minecraft.awesome.proxy.CommonProxy;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = AwesomeMod.MODID, name = AwesomeMod.NAME, version = AwesomeMod.VERSION)
public class AwesomeMod {
	public final static String MODID = "awesome";
	public final static String NAME = "awesome";
	public final static String VERSION = "0.64";
	
	public static final String CLIENT_PROXY_CLASS = "gogofo.minecraft.awesome.proxy.ClientProxy";
    public static final String SERVER_PROXY_CLASS = "gogofo.minecraft.awesome.proxy.CommonProxy";

    @Mod.Instance(AwesomeMod.MODID)
    public static AwesomeMod instance;

    @SidedProxy(clientSide = CLIENT_PROXY_CLASS, serverSide = SERVER_PROXY_CLASS)
	public static CommonProxy proxy;
    
    public static final AwesomeCreativeTab awesomeCreativeTab = new AwesomeCreativeTab(MODID);

    static {
        FluidRegistry.enableUniversalBucket();
    }
	
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        Ores.init();
        Fluids.init();
        Fluids.registerFluids();
        // Blocks are initiated first some items like the chainsaw needs them
        Blocks.init();
        Items.init();
        TileEntities.register();
    	Messages.init();
        Entities.registerEntities();
        proxy.registerEntityRenders();
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	proxy.registerGui();
    	proxy.registerRenders();
    	proxy.registerWorldGenerators();
    	proxy.registerOreDictEntries();

        Recipes.registerRecipes();
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
    }
}
