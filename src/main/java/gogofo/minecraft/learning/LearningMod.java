package gogofo.minecraft.learning;

import gogofo.minecraft.learning.init.LearningBlocks;
import gogofo.minecraft.learning.init.LearningItems;
import gogofo.minecraft.learning.proxy.CommonProxy;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenPlains;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;

//int id = EntityList.getEntityID(new EntityPig(worldObj));
//ItemStack egg = new ItemStack(net.minecraft.init.Items.spawn_egg, 1, id);

//@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class LearningMod
{   
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static CommonProxy proxy;
	
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	LearningBlocks.init();
    	LearningBlocks.register();
    	LearningItems.init();
    	LearningItems.register();
    	int id = EntityRegistry.findGlobalUniqueEntityId();
    	EntityRegistry.registerGlobalEntityID(EntityMutantCow.class, "my_cow", id, 1, 1);
    	EntityRegistry.registerModEntity(EntityMutantCow.class, "my_cow", id, Reference.MODID, 80, 3, false);	
    	EntityRegistry.addSpawn(EntityMutantCow.class, 0, 0, 5, EnumCreatureType.CREATURE, new BiomeGenBase[] { BiomeGenBase.plains});
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	proxy.registerRenders();
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
    }
}