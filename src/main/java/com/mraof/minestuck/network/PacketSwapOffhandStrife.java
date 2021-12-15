package com.mraof.minestuck.network;

import com.mraof.minestuck.strife.StrifePortfolioHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;

import java.util.EnumSet;

public class PacketSwapOffhandStrife extends MinestuckPacket
{
	int specibusIndex;
	int weaponIndex;

	@Override
	public MinestuckPacket generatePacket(Object... args)
	{
		data.writeInt((int) args[0]);
		data.writeInt((int) args[1]);
		return this;
	}

	@Override
	public MinestuckPacket consumePacket(ByteBuf data)
	{
		specibusIndex = data.readInt();
		weaponIndex = data.readInt();
		return this;
	}

	@Override
	public void execute(EntityPlayer player)
	{
		StrifePortfolioHandler.swapOffhandWeapon(player, specibusIndex, weaponIndex);
		MinestuckChannelHandler.sendToPlayer(makePacket(Type.UPDATE_STRIFE, player, PacketUpdateStrifeData.UpdateType.PORTFOLIO), player);
	}

	@Override
	public EnumSet<Side> getSenderSide()
	{
		return EnumSet.of(Side.CLIENT);
	}
}
