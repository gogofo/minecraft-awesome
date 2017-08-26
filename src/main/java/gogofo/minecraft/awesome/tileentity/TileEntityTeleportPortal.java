package gogofo.minecraft.awesome.tileentity;

import gogofo.minecraft.awesome.TeleporterManager;
import gogofo.minecraft.awesome.block.BlockTeleportPortal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumFacing;

public class TileEntityTeleportPortal extends TileEntity {
	
	private BlockPos teleporterPos;
	
	public void setTeleporter(BlockPos teleporterPos) {
		this.teleporterPos = teleporterPos;
	}
	
	@Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        
        teleporterPos = new BlockPos(compound.getInteger("teleporter_x"),
					        		 compound.getInteger("teleporter_y"),
					        		 compound.getInteger("teleporter_z"));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setInteger("teleporter_x", teleporterPos.getX());
        compound.setInteger("teleporter_y", teleporterPos.getY());
        compound.setInteger("teleporter_z", teleporterPos.getZ());
        
        return compound;
    }
    
    private TileEntityTeleporter getTeleporter() {
    	TileEntity te = world.getTileEntity(teleporterPos);
        
        if (te == null || !(te instanceof TileEntityTeleporter)) {
        	return null;
        }
        
        return (TileEntityTeleporter)te;
    }

	public void teleportIfPossible(EntityLivingBase entityIn, IBlockState state) {
		if (entityIn != null && entityIn.world != null && !entityIn.world.isRemote) {
			if (entityIn.isInvisible()) {	
				entityIn.addPotionEffect(new PotionEffect(MobEffects.INVISIBILITY, 10));
				return;
			}
			
			TileEntityTeleporter teleporter = getTeleporter();
			
			if (teleporter == null) {
				return;
			}
			
			BlockPos destTeleport = TeleporterManager.instance.getDestTeleport(teleporter);
			
			if (destTeleport == null) {
				return;
			}
			
			EnumFacing.Axis localAxis = (EnumFacing.Axis)state.getValue(BlockTeleportPortal.AXIS);
			EnumFacing.Axis remoteAxis = (EnumFacing.Axis)world.getBlockState(destTeleport.up()).getValue(BlockTeleportPortal.AXIS);
			
			int x = 0;
			int y = 1;
			int z = 0;
			
			destTeleport = destTeleport.add(x, y, z);
			
			BlockPos prevPos = new BlockPos(entityIn.lastTickPosX, entityIn.lastTickPosY, entityIn.lastTickPosZ);
			if (world.getTileEntity(prevPos) instanceof TileEntityTeleportPortal) {
				return;
			}
			
			entityIn.addPotionEffect(new PotionEffect(MobEffects.INVISIBILITY, 10));
			entityIn.setPositionAndUpdate(destTeleport.getX(), 
										  destTeleport.getY(), 
										  destTeleport.getZ());
		}
	}
}
