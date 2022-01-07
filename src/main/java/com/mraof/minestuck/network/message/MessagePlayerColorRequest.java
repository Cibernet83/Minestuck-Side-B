package com.mraof.minestuck.network.message;

import com.mraof.minestuck.network.MinestuckMessage;
import com.mraof.minestuck.network.skaianet.SburbHandler;
import com.mraof.minestuck.util.MinestuckPlayerData;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.relauncher.Side;

public class MessagePlayerColorRequest implements MinestuckMessage
{
	private int color;

	private MessagePlayerColorRequest() { }

	public MessagePlayerColorRequest(int color)
	{
		this.color = color;
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(color);
	}
	
	@Override
	public void fromBytes(ByteBuf buf)
	{
		color = buf.readInt();
	}

	@Override
	public void execute(EntityPlayer player)
	{
		if(SburbHandler.canSelectColor((EntityPlayerMP) player))
			MinestuckPlayerData.getData(player).color = this.color;
	}
	
	@Override
	public Side toSide()
	{
		return Side.SERVER;
	}
	
}