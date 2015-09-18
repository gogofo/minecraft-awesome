package gogofo.minecraft.awesome.block;

import gogofo.minecraft.awesome.gui.GuiEnum;
import gogofo.minecraft.awesome.tileentity.TileEntityTeleporter;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockTeleporter extends AwesomeBlockRunningMachine implements IElectricalBlock {

	public BlockTeleporter() {
		super(Material.iron);
		
		this.setHardness(1.0f);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityTeleporter();
	}

	@Override
	protected int getGuiId() {
		return GuiEnum.TELEPORTER.ordinal();
	}
	
	@Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
    	if (!worldIn.isRemote) {
	    	TileEntity te = worldIn.getTileEntity(pos);
	    	((TileEntityTeleporter)te).destroyPortal();
    	}
	    	
    	super.breakBlock(worldIn, pos, state);
    }
}
