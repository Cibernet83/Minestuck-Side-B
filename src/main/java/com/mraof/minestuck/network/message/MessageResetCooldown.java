package com.mraof.minestuck.network.message;

import com.mraof.minestuck.network.MinestuckMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;

public class MessageResetCooldown implements MinestuckMessage
{
	public MessageResetCooldown() { }

	@Override
	public void fromBytes(ByteBuf buf) { }

	@Override
	public void toBytes(ByteBuf buf) { }

	@Override
	public void execute(EntityPlayer player)
	{
		player.resetCooldown();
	}

	@Override
	public Side toSide()
	{
		return Side.CLIENT;
	}
}
