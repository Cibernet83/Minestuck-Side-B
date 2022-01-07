package com.mraof.minestuck.network.message;

import com.mraof.minestuck.network.MinestuckMessage;
import com.mraof.minestuck.network.skaianet.SkaianetHandler;
import com.mraof.minestuck.util.IdentifierHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;

public class MessageSkaianetDataRequest implements MinestuckMessage
{
	private int playerId;

	private MessageSkaianetDataRequest() { }

	public MessageSkaianetDataRequest(int playerId)
	{
		this.playerId = playerId;
	}
	
	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(playerId);
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		playerId = buf.readInt();
	}

	@Override
	public void execute(EntityPlayer player)
	{
		SkaianetHandler.requestInfo(player, IdentifierHandler.getById(this.playerId));
	}

	@Override
	public Side toSide() {
		return Side.SERVER;
	}

}
