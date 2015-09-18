package gogofo.minecraft.awesome.block;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import gogofo.minecraft.awesome.AwesomeMod;
import gogofo.minecraft.awesome.gui.GuiEnum;
import gogofo.minecraft.awesome.init.Blocks;
import gogofo.minecraft.awesome.tileentity.TileEntityGenerator;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockGenerator extends AwesomeBlockRunningMachine implements IElectricalBlock {

	public BlockGenerator() {
		super(Material.iron);
		
		this.setHardness(1.0f);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityGenerator();
	}
	
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(Blocks.generator);
    }
	
	@Override
	protected int getGuiId() {
		return GuiEnum.GENERATOR.ordinal();
	}

    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        if (this.isRunning(state))
        {
            EnumFacing enumfacing = (EnumFacing)state.getValue(FACING);
            double d0 = (double)pos.getX() + 0.5D;
            double d1 = (double)pos.getY() + rand.nextDouble() * 10.0D / 16.0D + 0.2D;
            double d2 = (double)pos.getZ() + 0.5D;
            double d3 = 0.52D;
            double d4 = rand.nextDouble() * 0.6D - 0.3D;

            switch (BlockGenerator.SwitchEnumFacing.FACING_LOOKUP[enumfacing.ordinal()])
            {
                case 1:
                    worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 - d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D, new int[0]);
                    worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 - d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D, new int[0]);
                    break;
                case 2:
                    worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D, new int[0]);
                    worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D, new int[0]);
                    break;
                case 3:
                    worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1, d2 - d3, 0.0D, 0.0D, 0.0D, new int[0]);
                    worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + d4, d1, d2 - d3, 0.0D, 0.0D, 0.0D, new int[0]);
                    break;
                case 4:
                    worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1, d2 + d3, 0.0D, 0.0D, 0.0D, new int[0]);
                    worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + d4, d1, d2 + d3, 0.0D, 0.0D, 0.0D, new int[0]);
            }
        }
    }
    
    @SideOnly(Side.CLIENT)
    static final class SwitchEnumFacing
	{
	    static final int[] FACING_LOOKUP = new int[EnumFacing.values().length];
	    private static final String __OBFID = "CL_00002111";
	
	    static
	    {
	        try
	        {
	            FACING_LOOKUP[EnumFacing.WEST.ordinal()] = 1;
	        }
	        catch (NoSuchFieldError var4)
	        {
	            ;
	        }
	
	        try
	        {
	            FACING_LOOKUP[EnumFacing.EAST.ordinal()] = 2;
	        }
	        catch (NoSuchFieldError var3)
	        {
	            ;
	        }
	
	        try
	        {
	            FACING_LOOKUP[EnumFacing.NORTH.ordinal()] = 3;
	        }
	        catch (NoSuchFieldError var2)
	        {
	            ;
	        }
	
	        try
	        {
	            FACING_LOOKUP[EnumFacing.SOUTH.ordinal()] = 4;
	        }
	        catch (NoSuchFieldError var1)
	        {
	            ;
	        }
	    }
	}
}
