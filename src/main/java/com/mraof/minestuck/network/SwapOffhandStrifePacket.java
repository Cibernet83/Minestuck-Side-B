package com.mraof.minestuck.network;

import com.mraof.minestuck.strife.StrifePortfolioHandler;
import com.mraof.minestuck.MinestuckConfig;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;

import java.util.EnumSet;

public class SwapOffhandStrifePacket extends MSUPacket
{
	int specibusIndex;
	int weaponIndex;

	@Override
	public MSUPacket generatePacket(Object... args)
	{
		data.writeInt((int) args[0]);
		data.writeInt((int) args[1]);
		return this;
	}

	@Override
	public MSUPacket consumePacket(ByteBuf data)
	{
		specibusIndex = data.readInt();
		weaponIndex = data.readInt();
		return this;
	}

	@Override
	public void execute(EntityPlayer player)
	{
		if(!MinestuckConfig.combatOverhaul)
			return;

		StrifePortfolioHandler.swapOffhandWeapon(player, specibusIndex, weaponIndex);
		MSUChannelHandler.sendToPlayer(makePacket(Type.UPDATE_STRIFE, player, UpdateStrifeDataPacket.UpdateType.PORTFOLIO), player);
	}

	@Override
	public EnumSet<Side> getSenderSide()
	{
		return EnumSet.of(Side.CLIENT);
	}
}
