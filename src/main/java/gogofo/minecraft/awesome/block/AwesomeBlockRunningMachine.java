package gogofo.minecraft.awesome.block;

import gogofo.minecraft.awesome.PowerManager;
import gogofo.minecraft.awesome.tileentity.AwesomeTileEntityMachine;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public abstract class AwesomeBlockRunningMachine extends AwesomeBlockContainer {

	public static final PropertyBool RUNNING = PropertyBool.create("running");
	
	protected AwesomeBlockRunningMachine(Material materialIn) {
		super(materialIn);
	}

	@Override
	protected IBlockState fillDefaultStateProperties(IBlockState state) {
		return state.withProperty(RUNNING, false);
	}
	
	public boolean isRunning(IBlockState state) {
    	return state.getValue(RUNNING).equals(true);
    }
	
	public static void setState(boolean running, World worldIn, BlockPos pos)
    {
		IBlockState iblockstate = worldIn.getBlockState(pos);
        TileEntity tileentity = worldIn.getTileEntity(pos);

        worldIn.setBlockState(
        		pos, 
        		iblockstate.withProperty(RUNNING, running), 
        		3);

        if (tileentity != null)
        {
            tileentity.validate();
            worldIn.setTileEntity(pos, tileentity);
        }
    }
	
	/**
     * Convert the given metadata into a BlockState for this Block
     */
    public IBlockState getStateFromMeta(int meta)
    {
        boolean isRunning = (meta & 0x8) > 0;

        return super.getStateFromMeta(meta).withProperty(RUNNING,  isRunning);
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(IBlockState state)
    {
    	int isRunninggMask = 0;
    	if (isRunning(state)) {
    		isRunninggMask = 0x8;
    	}
    	
        return super.getMetaFromState(state) | isRunninggMask;
    }

    @Override
    protected BlockState createBlockState()
    {
        return new BlockState(this, new IProperty[] {FACING, RUNNING});
    }
    
    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
    	super.onBlockAdded(worldIn, pos, state);
    	
    	if (!worldIn.isRemote) {
	    	TileEntity te = worldIn.getTileEntity(pos);
	    	PowerManager.instance.registerMachine((AwesomeTileEntityMachine)te);
    	}
	}
    
    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
    	if (!worldIn.isRemote) {
	    	TileEntity te = worldIn.getTileEntity(pos);
	    	PowerManager.instance.unregisterMachine((AwesomeTileEntityMachine)te);
    	}
	    	
    	super.breakBlock(worldIn, pos, state);
    }
}
