package com.mraof.minestuck.network;

import com.mraof.minestuck.util.SylladexUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;

import java.util.EnumSet;

public class PacketSylladexEmptyRequest extends MinestuckPacket
{
	@Override
	public void generatePacket(Object... args){}

	@Override
	public void consumePacket(ByteBuf data){}

	@Override
	public void execute(EntityPlayer player)
	{
		SylladexUtils.getSylladex(player).ejectAll(false, true);
		MinestuckChannelHandler.sendToPlayer(MinestuckPacket.makePacket(Type.UPDATE_SYLLADEX, player), player);
	}

	@Override
	public EnumSet<Side> getSenderSide()
	{
		return EnumSet.of(Side.CLIENT);
	}
}
