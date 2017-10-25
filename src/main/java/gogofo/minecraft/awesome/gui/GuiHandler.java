package gogofo.minecraft.awesome.gui;

import gogofo.minecraft.awesome.entity.EntityConstructor;
import gogofo.minecraft.awesome.entity.EntityDigger;
import gogofo.minecraft.awesome.inventory.*;
import gogofo.minecraft.awesome.tileentity.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import java.util.List;

public class GuiHandler implements IGuiHandler
{

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, 
          World world, int x, int y, int z) 
    { 
    	if (ID >= GuiEnum.values().length) {
    		return null;
    	}

        TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));

        if (tileEntity != null) {
			switch (GuiEnum.values()[ID]) {
				case FUSER:
					return new ContainerFuser(player.inventory, (AwesomeTileEntityMachine) tileEntity);
				case GENERATOR:
					return new ContainerGenerator(player.inventory, (AwesomeTileEntityMachine) tileEntity);
				case CHARGER:
					return new ContainerCharger(player.inventory, (AwesomeTileEntityMachine) tileEntity);
				case GRINDER:
					return new ContainerGrinder(player.inventory, (AwesomeTileEntityMachine) tileEntity);
				case ELECTRIC_FURNACE:
					return new ContainerElectricFurnace(player.inventory, (AwesomeTileEntityMachine) tileEntity);
				case TELEPORTER:
					return new ContainerTeleporter(player.inventory, (AwesomeTileEntityMachine) tileEntity);
				case SORTING_PIPE:
					return new ContainerSortingPipe(player.inventory, (IInventory) tileEntity);
				case EXTRACTOR:
					return new ContainerExtractor(player.inventory, (AwesomeTileEntityMachine) tileEntity);
				default:
					break;
			}
		}

		List<Entity> entities = world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(x, y, z, x + 1, y + 1, z + 1));
        if (entities.size() == 1) {
        	Entity entity = entities.get(0);

        	switch (GuiEnum.values()[ID]) {
				case E_CONSTRUCTOR:
					if (entity instanceof EntityConstructor) {
						return new ContainerConstructor(player.inventory, (IInventory) entity);
					}
					break;
				case E_DIGGER:
					if (entity instanceof EntityDigger) {
						return new ContainerDigger(player.inventory, (EntityDigger) entity);
					}
					break;
				default:
					break;
			}
		}

		return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, 
          World world, int x, int y, int z)
    {
    	if (ID >= GuiEnum.values().length) {
    		return null;
    	}
    	
        TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));

        if (tileEntity != null) {
			switch (GuiEnum.values()[ID]) {
				case FUSER:
					return new GuiFuser(player.inventory, (TileEntityFuser) tileEntity);
				case GENERATOR:
					return new GuiGenerator(player.inventory, (TileEntityGenerator) tileEntity);
				case CHARGER:
					return new GuiCharger(player.inventory, (TileEntityCharger) tileEntity);
				case GRINDER:
					return new GuiGrinder(player.inventory, (TileEntityGrinder) tileEntity);
				case ELECTRIC_FURNACE:
					return new GuiElectricFurnace(player.inventory, (TileEntityElectricFurnace) tileEntity);
				case TELEPORTER:
					return new GuiTeleporter(player.inventory, (TileEntityTeleporter) tileEntity);
				case SORTING_PIPE:
					return new GuiSortingPipe(player.inventory, (TileEntitySortingPipe) tileEntity);
				case EXTRACTOR:
					return new GuiExtractor(player.inventory, (TileEntityExtractor) tileEntity);
				default:
					break;
			}
		}

		List<Entity> entities = world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(x, y, z, x + 1, y + 1, z + 1));
		if (entities.size() == 1) {
			Entity entity = entities.get(0);

			switch (GuiEnum.values()[ID]) {
				case E_CONSTRUCTOR:
					if (entity instanceof EntityConstructor) {
						return new GuiConstructor(player.inventory, (EntityConstructor) entity);
					}
					break;
				case E_DIGGER:
					if (entity instanceof EntityDigger) {
						return new GuiDigger(player.inventory, (EntityDigger) entity);
					}
					break;
				default:
					break;
			}
		}

        return null;
    }
}
