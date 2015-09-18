package gogofo.minecraft.awesome.recipe;

import java.util.HashMap;

import gogofo.minecraft.awesome.init.Items;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class RecipeFuser {
	private HashMap<Item, HashMap<Item, Recipe>> recipes = 
								new HashMap<Item, HashMap<Item, Recipe>>();
	
	public RecipeFuser() {
		addRecipe(new Recipe(net.minecraft.init.Items.melon, 
							 net.minecraft.init.Items.melon, 
							 Items.rich_melon,
							 20));
		
		addRecipe(new Recipe(Items.iron_dust, 
							 net.minecraft.init.Items.glowstone_dust, 
							 Items.gold_dust,
							 100));
		
		addRecipe(new Recipe(net.minecraft.init.Items.iron_ingot, 
							 net.minecraft.init.Items.quartz, 
							 Items.quartz_iron_ingot,
							 100));
		
		addSpawnEggs();
	}
	
	
	private void addSpawnEggs() {
		addSpawnEggRecipe(net.minecraft.init.Items.bone, new EntitySkeleton(null));
		addSpawnEggRecipe(net.minecraft.init.Items.rotten_flesh, new EntityZombie(null));
		addSpawnEggRecipe(net.minecraft.init.Items.gunpowder, new EntityCreeper(null));
		addSpawnEggRecipe(net.minecraft.init.Items.spider_eye, new EntitySpider(null));
	}
	
	private void addSpawnEggRecipe(Item item, Entity entity) {
		addRecipe(new Recipe(Items.mob_essence, 
							 item,
							 spawnEgg(entity),
							 100));
	}


	public void addRecipe(Recipe recp) {
		addRecipe(recp.item1, recp.item2, recp);
		addRecipe(recp.item2, recp.item1, recp);
	}
	
	private void addRecipe(Item item1, Item item2, Recipe recp) {
		if (!recipes.containsKey(item1)) {
			recipes.put(item1, new HashMap<Item, Recipe>());
		}
		
		if (!recipes.containsKey(item2)) {
			recipes.put(item2, new HashMap<Item, Recipe>());
		}
		
		recipes.get(item1).put(item2, recp);
		recipes.get(item2).put(item1, recp);
	}
	
	public Recipe getRecipe(Item item1, Item item2) {
		if (!recipes.containsKey(item1)) {
			return null;
		}
		
		return recipes.get(item1).get(item2);
	}
	
	public class Recipe {
		public Item item1;
		public Item item2;
		public ItemStack result;
		public int fuseTime;
		
		public Recipe(Item item1, Item item2, ItemStack result, int fuseTime) {
			this.item1 = item1;
			this.item2 = item2;
			this.result = result;
			this.fuseTime = fuseTime;
		}
		
		public Recipe(Item item1, Item item2, Item result, int fuseTime) {
			this(item1, item2, stack(result, 1), fuseTime);
		}
	}
	
	private ItemStack stack(Item item, int size) {
		ItemStack stack = new ItemStack(item);
		stack.stackSize = size;
		return stack;
	}
	
	private ItemStack spawnEgg(Entity entity) {
		int id = EntityList.getEntityID(entity);
		ItemStack egg = new ItemStack(net.minecraft.init.Items.spawn_egg, 2, id);
		return egg;
	}
}
