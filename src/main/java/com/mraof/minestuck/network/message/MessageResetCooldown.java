package com.mraof.minestuck.network.message;

import com.mraof.minestuck.network.MinestuckMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;

import java.util.EnumSet;

public class MessageResetCooldown extends MinestuckMessage
{
	@Override
	public void generatePacket(Object... var1)
	{

	}

	@Override
	public void consumePacket(ByteBuf var1)
	{

	}

	@Override
	public void execute(EntityPlayer player)
	{
		player.resetCooldown();
	}

	@Override
	public EnumSet<Side> getSenderSide() {
		return EnumSet.of(Side.SERVER);
	}
}
