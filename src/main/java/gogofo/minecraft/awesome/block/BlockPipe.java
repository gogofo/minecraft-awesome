package gogofo.minecraft.awesome.block;

import java.util.List;
import java.util.Random;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;

import gogofo.minecraft.awesome.PowerManager;
import gogofo.minecraft.awesome.colorize.ISingleColoredObject;
import gogofo.minecraft.awesome.init.Blocks;
import gogofo.minecraft.awesome.init.Items;
import gogofo.minecraft.awesome.tileentity.AwesomeTileEntityContainer;
import gogofo.minecraft.awesome.tileentity.AwesomeTileEntityMachine;
import gogofo.minecraft.awesome.tileentity.TileEntityElectricWire;
import gogofo.minecraft.awesome.tileentity.TileEntityPipe;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockPipe extends BlockContainer implements ITileEntityProvider, ISingleColoredObject {
	
	public static final PropertyBool UP = PropertyBool.create("up");
	public static final PropertyBool DOWN = PropertyBool.create("down");
	public static final PropertyBool NORTH = PropertyBool.create("north");
	public static final PropertyBool SOUTH = PropertyBool.create("south");
	public static final PropertyBool EAST = PropertyBool.create("east");
	public static final PropertyBool WEST = PropertyBool.create("west");

	public BlockPipe() {
		super(Material.ROCK);
		
		this.setHardness(0.5f);
		this.setDefaultState(stateWithConnections(this.blockState.getBaseState(), false, false, false, false, false, false));
	}
	
	private IBlockState stateWithConnections(IBlockState state,
											 boolean up,
											 boolean down,
											 boolean north,
											 boolean south,
											 boolean east,
											 boolean west) {
		return state.withProperty(UP, up).
				withProperty(DOWN, down).
				withProperty(NORTH, north).
				withProperty(SOUTH, south).
				withProperty(EAST, east).
				withProperty(WEST,  west);
	}
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Item.getItemFromBlock(Blocks.pipe);
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState blockState) {
		return false;
	}
	
	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}
	
	@Override
	public boolean isFullCube(IBlockState blockState) {
		return false;
	}
	
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		return getStateForPos(state, worldIn, pos);
	}
	
	private IBlockState getStateForPos(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		boolean up = canConnectTo(worldIn, pos.up());
		boolean down = canConnectTo(worldIn, pos.down());
		boolean north = canConnectTo(worldIn, pos.north());
		boolean south = canConnectTo(worldIn, pos.south());
		boolean east = canConnectTo(worldIn, pos.east());
		boolean west = canConnectTo(worldIn, pos.west());
	
		return stateWithConnections(state, up, down, north, south, east, west);
	}
	
	public boolean canConnectTo(IBlockAccess world, BlockPos pos) {
		return canConnectTo(world.getBlockState(pos).getBlock());
	}
	
	protected boolean canConnectTo(Block block) {
		return block instanceof BlockContainer;
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return 0;
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return super.getStateFromMeta(meta);
	}
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {UP, DOWN, NORTH, SOUTH, EAST, WEST});
    }

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityPipe();
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		state = this.getActualState(state, source, pos);
		return getBoundingBoxByState(pos, state);
	}
	
	private AxisAlignedBB getBoundingBoxByState(BlockPos pos, IBlockState state) {
		final float minConnected = 0.0f;
		final float maxConnected = 1.0f;
		final float minDisconnected = 3/16f;
		final float maxDisconnected = 13/16f;
		final float minCube = minDisconnected;
		final float maxCube = maxDisconnected;
		
		float minX = minCube;
		float minY = minCube;
		float minZ = minDisconnected;
		float maxX = maxCube;
		float maxY = maxCube;
		float maxZ = maxDisconnected;
		
		int connections = 0;
		
		boolean up = state.getValue(UP).equals(true);
		boolean down = state.getValue(DOWN).equals(true);
		boolean north = state.getValue(NORTH).equals(true);
		boolean south = state.getValue(SOUTH).equals(true);
		boolean east = state.getValue(EAST).equals(true);
		boolean west = state.getValue(WEST).equals(true);
		
		if (up || down || north || south || east || west) {
			minX = minCube;
			minY = minCube;
			minZ = minCube;
			maxX = maxCube;
			maxY = maxCube;
			maxZ = maxCube;
		}
		
		if (up) {
			maxY = maxConnected;
			connections++;
		}
		
		if (down) {
			minY = minConnected;
			connections++;
		}
		
		if (north) {
			minZ = minConnected;
			connections++;
		}
		
		if (south) {
			maxZ = maxConnected;
			connections++;
		}
		
		if (east) {
			maxX = maxConnected;
			connections++;
		}
		
		if (west) {
			minX = minConnected;
			connections++;
		}
		
		// Handle disconnected state at pipes connected only in 1 place
		if (connections == 1) {
			if (up) {
				minY = minDisconnected;
			} else if (down) {
				maxY = maxDisconnected;
			} else if (north) {
				maxZ = maxDisconnected;
			} else if (south) {
				minZ = minDisconnected;
			} else if (east) {
				minX = minDisconnected;
			} else if (west) {
				maxX = maxDisconnected;
			}
		}
		
		return new AxisAlignedBB(minX, minY, minZ, maxX, maxY, maxZ);
	}
	
	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox,
			List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean p_185477_7_) {
	}
	
	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		super.onBlockAdded(worldIn, pos, state);
//		
//		if (!worldIn.isRemote) {
//			PowerManager.instance.registerWire(pos);
//		}
	}
    
    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
//    	if (!worldIn.isRemote) {
//    		PowerManager.instance.unregisterWire(pos);
//    	}
    	
    	TileEntity tileentity = worldIn.getTileEntity(pos);

        if (tileentity instanceof IInventory)
        {
            InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory)tileentity);
        }

        super.breakBlock(worldIn, pos, state);
    }
    
    @Override
    public boolean canProvidePower(IBlockState state) {
    	return true;
    }
    @Override
    public int getWeakPower(IBlockState blockState, IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
		TileEntity te = worldIn.getTileEntity(pos);
    	
    	if (te == null || !(te instanceof AwesomeTileEntityContainer)) {
    		return 0;
    	}

    	return ((AwesomeTileEntityContainer)te).hasItems() ? 1 : 0;
    }
    
    @Override
    public int getStrongPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
    	return 0;
    }

	@Override
	public int getColor() {
		return 0xFF1F1F1F;
	}
}
