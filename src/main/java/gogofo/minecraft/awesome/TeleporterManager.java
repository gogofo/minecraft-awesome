package gogofo.minecraft.awesome;

import java.util.ArrayList;
import java.util.HashMap;

import gogofo.minecraft.awesome.tileentity.TileEntityTeleporter;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

public class TeleporterManager {
	public static TeleporterManager instance = new TeleporterManager();
	
	private HashMap<Item, ArrayList<BlockPos>> teleportMap;// = new HashMap<Item, ArrayList<BlockPos>>();
	private AwesomeWorldData awesomeWorldData;
	
	private TeleporterManager() {
		World world = DimensionManager.getWorld(0);
		awesomeWorldData = AwesomeWorldData.get(world);
		teleportMap = awesomeWorldData.getTeleporterMap();
	}
	
	public void registerTeleporter(TileEntityTeleporter teleporter) {
		Item key = teleporter.getKey();
		
		if (!teleportMap.containsKey(key)) {
			teleportMap.put(key, new ArrayList());
		}
		
		if (teleportMap.get(key).contains(teleporter.getPos())) {
			return;
		}
		
		teleportMap.get(key).add(teleporter.getPos());
		
		awesomeWorldData.markDirty();
	}

	public void unregisterTeleporter(TileEntityTeleporter teleporter) {
		Item key = teleporter.getKey();
		
		if (!teleportMap.containsKey(key)) {
			return;
		}
		
		teleportMap.get(key).remove(teleporter.getPos());
		
		awesomeWorldData.markDirty();
	}

	public BlockPos getDestTeleport(TileEntityTeleporter teleporter) {
		Item key = teleporter.getKey();
		
		if (!teleportMap.containsKey(key)) {
			return null;
		}
		
		ArrayList<BlockPos> teleporters = teleportMap.get(key);
		
		if (teleporters.size() != 2) {
			return null;
		}
		
		if (teleporters.get(0).equals(teleporter.getPos())) {
			return teleporters.get(1);
		}
		
		if (teleporters.get(1).equals(teleporter.getPos())) {
			return teleporters.get(0);
		}
		
		return null;
	}
}
