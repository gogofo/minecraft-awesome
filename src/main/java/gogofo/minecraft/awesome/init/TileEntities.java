package gogofo.minecraft.awesome.init;

import gogofo.minecraft.awesome.tileentity.TileEntityCharger;
import gogofo.minecraft.awesome.tileentity.TileEntityElectricFurnace;
import gogofo.minecraft.awesome.tileentity.TileEntityElectricWire;
import gogofo.minecraft.awesome.tileentity.TileEntityExtractor;
import gogofo.minecraft.awesome.tileentity.TileEntityFuser;
import gogofo.minecraft.awesome.tileentity.TileEntityGenerator;
import gogofo.minecraft.awesome.tileentity.TileEntityGrinder;
import gogofo.minecraft.awesome.tileentity.TileEntityPipe;
import gogofo.minecraft.awesome.tileentity.TileEntitySortingPipe;
import gogofo.minecraft.awesome.tileentity.TileEntitySuctionPipe;
import gogofo.minecraft.awesome.tileentity.TileEntityTeleportPortal;
import gogofo.minecraft.awesome.tileentity.TileEntityTeleporter;
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
	}
}
