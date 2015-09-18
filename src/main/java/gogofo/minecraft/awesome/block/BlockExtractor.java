package gogofo.minecraft.awesome.block;

import gogofo.minecraft.awesome.gui.GuiEnum;
import gogofo.minecraft.awesome.tileentity.TileEntityExtractor;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockExtractor extends AwesomeBlockRunningMachine implements IElectricalBlock {

	public BlockExtractor() {
		super(Material.iron);
		
		this.setHardness(1.0f);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityExtractor();
	}

	@Override
	protected int getGuiId() {
		return GuiEnum.EXTRACTOR.ordinal();
	}
}
