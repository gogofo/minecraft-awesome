package gogofo.minecraft.awesome.init;

import gogofo.minecraft.awesome.tileentity.*;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileEntities {
	public static void register() {
		GameRegistry.registerTileEntity(TileEntityGenerator.class, "awesomeGenerator");
		GameRegistry.registerTileEntity(TileEntityElectricWire.class, "awesomeElectricWire");
		GameRegistry.registerTileEntity(TileEntityFuser.class, "awesomeFuser");
		GameRegistry.registerTileEntity(TileEntityCharger.class, "awesomeCharger");
		GameRegistry.registerTileEntity(TileEntityGrinder.class, "awesomeGrinder");
		GameRegistry.registerTileEntity(TileEntityElectricFurnace.class, "awesomeElectricFurnace");
		GameRegistry.registerTileEntity(TileEntityTeleportPortal.class, "awesomeTileEntityTeleportPortal");
		GameRegistry.registerTileEntity(TileEntityTeleporter.class, "awesomeTileEntityTeleporter");
		GameRegistry.registerTileEntity(TileEntityPipe.class, "awesomeTileEntityPipe");
		GameRegistry.registerTileEntity(TileEntitySuctionPipe.class, "awesomeTileEntitySuctionPipe");
		GameRegistry.registerTileEntity(TileEntitySortingPipe.class, "awesomeTileEntitySortingPipe");
		GameRegistry.registerTileEntity(TileEntityExtractor.class, "awesomeTileEntityExtractor");
		GameRegistry.registerTileEntity(TileEntityLiquidStorageContainer.class, "awesomeTileEntityLiquidStorageContainer");
		GameRegistry.registerTileEntity(TileEntityTreeTap.class, "awesomeTileEntityTreeTap");
	}
}
