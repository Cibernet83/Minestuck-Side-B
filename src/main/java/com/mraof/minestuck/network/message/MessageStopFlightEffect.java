package com.mraof.minestuck.network.message;

import com.mraof.minestuck.network.MinestuckMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;

public class MessageStopFlightEffect implements MinestuckMessage
{
	private boolean isBadEffect;

	private MessageStopFlightEffect() { }

	public MessageStopFlightEffect(boolean isBadEffect)
	{
		this.isBadEffect = isBadEffect;
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeBoolean(isBadEffect);
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		isBadEffect = buf.readBoolean();
	}

	@Override
	public void execute(EntityPlayer player)
	{
		if(isBadEffect && player.isCreative())
			player.capabilities.allowFlying = true;
		if(!isBadEffect && !player.isCreative())
		{
			player.capabilities.allowFlying = false;
			player.capabilities.isFlying = false;
		}
	}

	@Override
	public Side toSide()
	{
		return Side.CLIENT;
	}
}
