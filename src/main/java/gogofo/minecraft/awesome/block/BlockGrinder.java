package gogofo.minecraft.awesome.block;

import gogofo.minecraft.awesome.gui.GuiEnum;
import gogofo.minecraft.awesome.tileentity.TileEntityFuser;
import gogofo.minecraft.awesome.tileentity.TileEntityGrinder;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockGrinder extends AwesomeBlockRunningMachine implements IElectricalBlock {

	public BlockGrinder() {
		super(Material.iron);
		
		this.setHardness(1.0f);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityGrinder();
	}

	@Override
	protected int getGuiId() {
		return GuiEnum.GRINDER.ordinal();
	}
}
