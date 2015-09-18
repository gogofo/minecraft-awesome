package gogofo.minecraft.awesome;

import gogofo.minecraft.awesome.init.Blocks;
import gogofo.minecraft.awesome.init.Items;
import gogofo.minecraft.awesome.init.Recipes;
import gogofo.minecraft.awesome.init.TileEntities;
import gogofo.minecraft.awesome.proxy.CommonProxy;
import gogofo.minecraft.awesome.recipe.RecipeFuser.Recipe;
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
	public final static String VERSION = "0.18";
	
	public static final String CLIENT_PROXY_CLASS = "gogofo.minecraft.awesome.proxy.ClientProxy";
    public static final String SERVER_PROXY_CLASS = "gogofo.minecraft.awesome.proxy.CommonProxy";
    
    @SidedProxy(clientSide = CLIENT_PROXY_CLASS, serverSide = SERVER_PROXY_CLASS)
	public static CommonProxy proxy;
	
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	Items.init();
    	Items.register();
    	Blocks.init();
    	Blocks.register();
    	TileEntities.register();
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	Recipes.registerRecipes();
    	
    	proxy.registerGui();
    	proxy.registerRenders();
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
    }
}
