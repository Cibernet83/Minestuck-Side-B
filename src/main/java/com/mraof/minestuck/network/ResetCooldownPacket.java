package com.mraof.minestuck.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;

import java.util.EnumSet;

public class ResetCooldownPacket extends MinestuckPacket
{
	@Override
	public MinestuckPacket generatePacket(Object... var1)
	{
		return this;
	}

	@Override
	public MinestuckPacket consumePacket(ByteBuf var1)
	{
		return this;
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