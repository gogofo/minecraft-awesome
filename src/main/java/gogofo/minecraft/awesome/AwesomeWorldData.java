package gogofo.minecraft.awesome;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldSavedData;
import scala.collection.generic.BitOperations.Int;

public class AwesomeWorldData extends WorldSavedData {

	private static final String IDENTIFIER = "awesome";
	
	HashMap<Item, ArrayList<BlockPos>> teleportMap = new HashMap<Item, ArrayList<BlockPos>>();
	
	public AwesomeWorldData() {
		this(IDENTIFIER);
	}
	
	public AwesomeWorldData(String name) {
		super(name);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		NBTTagList teleportMapNBT = nbt.getTagList("teleportMap", 10);
		
		for (int i = 0; i < teleportMapNBT.tagCount(); i++) {
			NBTTagCompound keyNBT = teleportMapNBT.getCompoundTagAt(i);
			Item key = Item.getItemById(keyNBT.getInteger("keyItemId"));
			
			if (!teleportMap.containsKey(key)) {
				teleportMap.put(key, new ArrayList<BlockPos>());
			}
			
			NBTTagList posListNBT = keyNBT.getTagList("posList", 10);
			
			for (int j = 0; j < posListNBT.tagCount(); j++) {
				NBTTagCompound posTag = posListNBT.getCompoundTagAt(j);
				BlockPos pos = new BlockPos(posTag.getInteger("x"),
											posTag.getInteger("y"),
											posTag.getInteger("z"));
				
				if (!teleportMap.get(key).contains(pos)) {
					teleportMap.get(key).add(pos);
				}
			}
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		NBTTagList teleportMapNBT = new NBTTagList();
		for (Item key : teleportMap.keySet()) {
			NBTTagList posListNBT = new NBTTagList();
			
			for (BlockPos pos : teleportMap.get(key)) {
				NBTTagCompound posTag = new NBTTagCompound();
				posTag.setInteger("x", pos.getX());
				posTag.setInteger("y", pos.getY());
				posTag.setInteger("z", pos.getZ());
				
				posListNBT.appendTag(posTag);
			}
			
			NBTTagCompound keyNBT = new NBTTagCompound();
			keyNBT.setInteger("keyItemId", Item.getIdFromItem(key));
			keyNBT.setTag("posList", posListNBT);
			
			teleportMapNBT.appendTag(keyNBT);
		}
		
		nbt.setTag("teleportMap", teleportMapNBT);
		
		return nbt;
	}
	
	public static AwesomeWorldData get(World world) {
		AwesomeWorldData data = (AwesomeWorldData)world.loadData(AwesomeWorldData.class, IDENTIFIER);
		if (data == null) {
			data = new AwesomeWorldData();
			world.setData(IDENTIFIER, data);
		}
		return data;
	}

	public HashMap<Item, ArrayList<BlockPos>> getTeleporterMap() {
		return teleportMap;
	}
}
