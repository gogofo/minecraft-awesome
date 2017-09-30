package gogofo.minecraft.awesome.recipe;

import gogofo.minecraft.awesome.init.Items;
import gogofo.minecraft.awesome.init.Ores;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;

public class RecipeFuser {
	private ArrayList<Recipe> recipes = new ArrayList<>();
	
	public RecipeFuser() {
		addRecipe(new Recipe(Items.rich_melon,
							 20,
							 net.minecraft.init.Items.MELON,
							 net.minecraft.init.Items.MELON));

		addRecipe(new Recipe(Items.gold_dust,
							 100,
							 Items.iron_dust,
							 net.minecraft.init.Items.GLOWSTONE_DUST));

		addRecipe(new Recipe(Items.quartz_iron_ingot,
							 100,
							 net.minecraft.init.Items.IRON_INGOT,
							 net.minecraft.init.Items.QUARTZ));

		addRecipe(new Recipe(Ores.bronze.getIngot(),
							 100,
							 Ores.copper.getIngot(),
							 Ores.tin.getIngot(),
							 Ores.tin.getIngot(),
							 Ores.tin.getIngot()));
		
		addSpawnEggs();
	}
	
	
	private void addSpawnEggs() {
		addSpawnEggRecipe(net.minecraft.init.Items.BONE, new EntitySkeleton(null));
		addSpawnEggRecipe(net.minecraft.init.Items.ROTTEN_FLESH, new EntityZombie(null));
		addSpawnEggRecipe(net.minecraft.init.Items.GUNPOWDER, new EntityCreeper(null));
		addSpawnEggRecipe(net.minecraft.init.Items.SPIDER_EYE, new EntitySpider(null));
	}
	
	private void addSpawnEggRecipe(Item item, Entity entity) {
		addRecipe(new Recipe(spawnEgg(entity),
							 100,
							 Items.mob_essence,
							 item));
	}
	
	public void addRecipe(Recipe recp) {
		recipes.add(recp);
	}
	
	public Recipe getRecipe(Item[] items) {
		Recipe attempt = new Recipe(items);

		for (Recipe recipe : recipes) {
			if (recipe.matchAttempt(attempt)) {
				return recipe;
			}
		}

		return null;
	}
	
	public class Recipe {
		public Item[] items;
		public ItemStack result;
		public int fuseTime;
		
		public Recipe(ItemStack result, int fuseTime, Item... items) {
			this.items = items;
			this.result = result;
			this.fuseTime = fuseTime;
		}
		
		public Recipe(Item result, int fuseTime, Item... items) {
			this(stack(result, 1), fuseTime, items);
		}

		public Recipe(Item[] items) {
			this.items = items;
		}

		public boolean matchAttempt(Recipe attempt) {
			if (attempt == this) {
				return true;
			}

			if (attempt.items.length != items.length) {
				return false;
			}

			boolean[] used = new boolean[attempt.items.length];
			for (Item required : items) {
				boolean found = false;

				for (int i = 0; i < attempt.items.length; i++) {
					if (!used[i] && attempt.items[i] == required) {
						found = true;
						used[i] = true;
						break;
					}
				}

				if (!found) {
					return false;
				}
			}

			return true;
		}
	}
	
	private ItemStack stack(Item item, int size) {
		ItemStack stack = new ItemStack(item);
		stack.setCount(size);
		return stack;
	}
	
	private ItemStack spawnEgg(Entity entity) {
		int id = EntityList.getID(entity.getClass());
		ItemStack egg = new ItemStack(net.minecraft.init.Items.SPAWN_EGG, 2);
		NBTTagCompound tagCompound = new NBTTagCompound();
		NBTTagCompound entityTag = new NBTTagCompound();
		entityTag.setString("id", entity.getName());
		tagCompound.setTag("EntityTag", entityTag);
		egg.setTagCompound(tagCompound);
		return egg;
	}
}
