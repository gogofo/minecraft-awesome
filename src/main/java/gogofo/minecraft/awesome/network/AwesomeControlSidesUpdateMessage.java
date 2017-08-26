package gogofo.minecraft.awesome.network;

import gogofo.minecraft.awesome.inventory.SlotCategoryIdToColor;
import gogofo.minecraft.awesome.tileentity.AwesomeTileEntityContainer;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class AwesomeControlSidesUpdateMessage implements IMessage {

	public BlockPos pos;
	public EnumFacing face;
	
	public AwesomeControlSidesUpdateMessage() {
	}
	
	public AwesomeControlSidesUpdateMessage(BlockPos pos, EnumFacing face) {
		this.pos = pos;
		this.face = face;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		pos = BlockPos.fromLong(buf.readLong());
		face = EnumFacing.VALUES[buf.readInt()];
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeLong(pos.toLong());
		buf.writeInt(face.getIndex());
	}
	
	public static class MessageHandler implements IMessageHandler<AwesomeControlSidesUpdateMessage, IMessage> {

		@Override
		public IMessage onMessage(AwesomeControlSidesUpdateMessage message, MessageContext ctx) {
			World world = ctx.getServerHandler().player.world;
			TileEntity tileEntity = world.getTileEntity(message.pos);
			
			if (tileEntity == null || 
					!(tileEntity instanceof AwesomeTileEntityContainer)) {
				return null;
			}
			
			AwesomeTileEntityContainer aTileEntity = (AwesomeTileEntityContainer)tileEntity;
			EnumFacing face = message.face;
			int[] slots = aTileEntity.getSlotsForFace(face);
			int mainSlot = slots.length > 0 ? slots[0] : -1; // Assuming that only 1 slot is assigned to a face ATM
			int nextSlot = (mainSlot + 1) % (aTileEntity.getCustomSlotCount() + 1);
			
			aTileEntity.removeSlotFromFace(mainSlot, face);
			
			if (nextSlot < aTileEntity.getCustomSlotCount()) {
				aTileEntity.addSlotToFace(nextSlot, face);
			}
			
			ctx.getServerHandler().player
						.getServerWorld()
						.getPlayerChunkMap()
						.markBlockForUpdate(aTileEntity.getPos());
			
			return null;
		}
		
	}
}
