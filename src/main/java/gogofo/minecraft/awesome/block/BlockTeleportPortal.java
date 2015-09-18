package gogofo.minecraft.awesome.block;

import java.util.Random;

import gogofo.minecraft.awesome.tileentity.TileEntityTeleportPortal;
import net.minecraft.block.BlockBreakable;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockTeleportPortal extends BlockBreakable implements ITileEntityProvider {
    public static final PropertyEnum AXIS = PropertyEnum.create("axis", EnumFacing.Axis.class, new EnumFacing.Axis[] {EnumFacing.Axis.X, EnumFacing.Axis.Z});

	public BlockTeleportPortal() {
		super(Material.portal, false);
        this.setDefaultState(this.blockState.getBaseState().withProperty(AXIS, EnumFacing.Axis.X));
	}
	
	public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state)
    {
        return null;
    }
	
	public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos)
    {
//        EnumFacing.Axis axis = (EnumFacing.Axis)worldIn.getBlockState(pos).getValue(AXIS);
//        float f = 0.125F;
//        float f1 = 0.125F;
//
//        if (axis == EnumFacing.Axis.X)
//        {
//            f = 0.5F;
//        }
//
//        if (axis == EnumFacing.Axis.Z)
//        {
//            f1 = 0.5F;
//        }
//
//        this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f1, 0.5F + f, 1.0F, 0.5F + f1);
        this.setBlockBounds(0, 0, 0, 0, 0, 0);
    }

    public boolean isFullCube()
    {
        return false;
    }
    
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side)
    {
        EnumFacing.Axis axis = null;
        IBlockState iblockstate = worldIn.getBlockState(pos);

        if (worldIn.getBlockState(pos).getBlock() == this)
        {
            axis = (EnumFacing.Axis)iblockstate.getValue(AXIS);

            if (axis == null)
            {
                return false;
            }

            if (axis == EnumFacing.Axis.Z && side != EnumFacing.EAST && side != EnumFacing.WEST)
            {
                return false;
            }

            if (axis == EnumFacing.Axis.X && side != EnumFacing.SOUTH && side != EnumFacing.NORTH)
            {
                return false;
            }
        }

        boolean flag = worldIn.getBlockState(pos.west()).getBlock() == this && worldIn.getBlockState(pos.west(2)).getBlock() != this;
        boolean flag1 = worldIn.getBlockState(pos.east()).getBlock() == this && worldIn.getBlockState(pos.east(2)).getBlock() != this;
        boolean flag2 = worldIn.getBlockState(pos.north()).getBlock() == this && worldIn.getBlockState(pos.north(2)).getBlock() != this;
        boolean flag3 = worldIn.getBlockState(pos.south()).getBlock() == this && worldIn.getBlockState(pos.south(2)).getBlock() != this;
        boolean flag4 = flag || flag1 || axis == EnumFacing.Axis.X;
        boolean flag5 = flag2 || flag3 || axis == EnumFacing.Axis.Z;
        return flag4 && side == EnumFacing.WEST ? true : (flag4 && side == EnumFacing.EAST ? true : (flag5 && side == EnumFacing.NORTH ? true : flag5 && side == EnumFacing.SOUTH));
    }
    
    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int quantityDropped(Random random)
    {
        return 0;
    }

    /**
     * Called When an Entity Collided with the Block
     */
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
    {
    	if (!(entityIn instanceof EntityLivingBase)) {
    		return;
    	}
    	
        TileEntity te = worldIn.getTileEntity(pos);
        
        if (te == null || !(te instanceof TileEntityTeleportPortal)) {
        	return;
        }
        
        TileEntityTeleportPortal tep = (TileEntityTeleportPortal)te;
        tep.teleportIfPossible((EntityLivingBase)entityIn, state);
    }
    
    public static int getMetaForAxis(EnumFacing.Axis axis)
    {
        return axis == EnumFacing.Axis.X ? 1 : (axis == EnumFacing.Axis.Z ? 2 : 0);
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(IBlockState state)
    {
        return getMetaForAxis((EnumFacing.Axis)state.getValue(AXIS));
    }
    
    /**
     * Convert the given metadata into a BlockState for this Block
     */
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(AXIS, (meta & 3) == 2 ? EnumFacing.Axis.Z : EnumFacing.Axis.X);
    }

    @SideOnly(Side.CLIENT)
    public EnumWorldBlockLayer getBlockLayer()
    {
        return EnumWorldBlockLayer.TRANSLUCENT;
    }

    @SideOnly(Side.CLIENT)
    public Item getItem(World worldIn, BlockPos pos)
    {
        return null;
    }

    protected BlockState createBlockState()
    {
        return new BlockState(this, new IProperty[] {AXIS});
    }

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityTeleportPortal();
	}
}
