package gogofo.minecraft.awesome;

import gogofo.minecraft.awesome.creativetabs.AwesomeCreativeTab;
import gogofo.minecraft.awesome.init.*;
import gogofo.minecraft.awesome.proxy.CommonProxy;
import gogofo.minecraft.awesome.recipe.RecipeFuser.Recipe;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = AwesomeMod.MODID, name = AwesomeMod.NAME, version = AwesomeMod.VERSION)
public class AwesomeMod {
	public final static String MODID = "awesome";
	public final static String NAME = "awesome";
	public final static String VERSION = "0.38";
	
	public static final String CLIENT_PROXY_CLASS = "gogofo.minecraft.awesome.proxy.ClientProxy";
    public static final String SERVER_PROXY_CLASS = "gogofo.minecraft.awesome.proxy.CommonProxy";
    
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
    	Items.init();
    	Blocks.init();
        TileEntities.register();
    	Messages.init();
    	proxy.renderFluids();
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	proxy.registerGui();
    	proxy.registerRenders();
    	proxy.registerWorldGenerators();

        Recipes.registerRecipes();
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
    }
}
