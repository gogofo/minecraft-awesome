package gogofo.minecraft.awesome.block;

import gogofo.minecraft.awesome.gui.GuiEnum;
import gogofo.minecraft.awesome.tileentity.TileEntityConstructor;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockConstructor extends AwesomeBlockRunningMachine implements IElectricalBlock {

	public BlockConstructor() {
		super(Material.IRON);
		
		this.setHardness(1.0f);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityConstructor();
	}

	@Override
	protected int getGuiId() {
		return GuiEnum.CONSTRUCTOR.ordinal();
	}
}
