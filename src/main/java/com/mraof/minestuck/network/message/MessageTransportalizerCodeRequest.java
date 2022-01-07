package com.mraof.minestuck.network.message;

import com.mraof.minestuck.network.MinestuckMessage;
import com.mraof.minestuck.tileentity.TileEntityTransportalizer;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;

public class MessageTransportalizerCodeRequest implements MinestuckMessage
{
	private BlockPos pos;
	private String destId;

	private MessageTransportalizerCodeRequest() { }

	public MessageTransportalizerCodeRequest(TileEntityTransportalizer transportalizer, String destId)
	{
		this.pos = transportalizer.getPos();
		this.destId = destId;
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(pos.getX());
		buf.writeInt(pos.getY());
		buf.writeInt(pos.getZ());
		ByteBufUtils.writeUTF8String(buf, destId);
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
		destId = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void execute(EntityPlayer player)
	{
		TileEntityTransportalizer te = (TileEntityTransportalizer) player.world.getTileEntity(pos);
		if(te != null)
			te.setDestId(destId);
	}

	@Override
	public Side toSide()
	{
		return Side.SERVER;
	}
}
