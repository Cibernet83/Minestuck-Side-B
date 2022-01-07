package com.mraof.minestuck.network.message;

import com.mraof.minestuck.network.MinestuckMessage;
import com.mraof.minestuck.util.MinestuckPlayerData;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;

public class MessageBoondollars implements MinestuckMessage
{
	public long boondollars;

	private MessageBoondollars() { }

	public MessageBoondollars(long boondollars)
	{
		this.boondollars = boondollars;
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeLong(boondollars);
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		boondollars = buf.readLong();
	}
	
	@Override
	public void execute(EntityPlayer player)
	{
		MinestuckPlayerData.clientData.boondollars = boondollars;
	}
	
	@Override
	public Side toSide()
	{
		return Side.CLIENT;
	}
}