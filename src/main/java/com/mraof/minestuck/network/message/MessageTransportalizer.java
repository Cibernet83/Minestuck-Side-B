package com.mraof.minestuck.network.message;

import com.mraof.minestuck.network.MinestuckMessage;
import com.mraof.minestuck.tileentity.TileEntityTransportalizer;
import com.mraof.minestuck.util.Debug;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;

import java.util.EnumSet;

public class MessageTransportalizer extends MinestuckMessage
{
	int x;
	int y;
	int z;
	String destId;
	
	@Override
	public void generatePacket(Object... dat)
	{
		data.writeInt((int) dat[0]);
		data.writeInt((int) dat[1]);
		data.writeInt((int) dat[2]);
		if(dat.length > 2)
			data.writeBytes(((String) dat[3]).getBytes());

	}

	@Override
	public void consumePacket(ByteBuf data)
	{
		x = data.readInt();
		y = data.readInt();
		z = data.readInt();
		byte[] destBytes = new byte[4];
		//data.getBytes(0, destBytes, 0, 4);
		for(int i = 0; i < 4; i++)
			destBytes[i] = data.readByte();
		Debug.debugf("%d, %d, %d, %d", destBytes[0], destBytes[1], destBytes[2], destBytes[3]);
		destId = new String(destBytes);

	}

	@Override
	public void execute(EntityPlayer player)
	{
		TileEntityTransportalizer te = (TileEntityTransportalizer) player.world.getTileEntity(new BlockPos(x, y, z));
		if(te != null)
		{
			te.setDestId(destId);
		}
	}

	@Override
	public EnumSet<Side> getSenderSide()
	{
		return EnumSet.of(Side.CLIENT);
	}
}
