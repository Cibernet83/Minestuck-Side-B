package com.mraof.minestuck.network;

import io.netty.buffer.ByteBuf;

import java.util.EnumSet;

import com.mraof.minestuck.network.skaianet.SburbHandler;
import com.mraof.minestuck.util.MinestuckPlayerData;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.relauncher.Side;

public class PacketSelection extends MinestuckPacket
{
	public static final byte COLOR = 0;
	
	public byte type;
	public int color;
	
	@Override
	public void generatePacket(Object... data)
	{
		byte type = (Byte) data[0];
		this.data.writeByte(type);
		if(type == COLOR)
			this.data.writeInt((Integer) data[1]);
		

	}
	
	@Override
	public void consumePacket(ByteBuf data)
	{
		this.type = data.readByte();
		if(this.type == COLOR)
			this.color = data.readInt();
		

	}
	
	@Override
	public void execute(EntityPlayer player)
	{
		if(this.type == COLOR)
		{
			if(SburbHandler.canSelectColor((EntityPlayerMP) player))
				MinestuckPlayerData.getData(player).color = this.color;
		}
	}
	
	@Override
	public EnumSet<Side> getSenderSide()
	{
		return EnumSet.of(Side.CLIENT);
	}
	
}