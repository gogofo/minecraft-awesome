package gogofo.minecraft.awesome.block;

import gogofo.minecraft.awesome.gui.GuiEnum;
import gogofo.minecraft.awesome.tileentity.TileEntityFuser;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockFuser extends AwesomeBlockRunningMachine implements IElectricalBlock {

	public BlockFuser() {
		super(Material.IRON);
		
		this.setHardness(1.0f);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityFuser();
	}

	@Override
	protected int getGuiId() {
		return GuiEnum.FUSER.ordinal();
	}
}
