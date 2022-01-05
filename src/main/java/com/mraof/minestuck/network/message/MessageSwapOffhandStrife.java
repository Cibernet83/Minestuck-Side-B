package com.mraof.minestuck.network.message;

import com.mraof.minestuck.network.MinestuckMessage;
import com.mraof.minestuck.network.MinestuckNetwork;
import com.mraof.minestuck.strife.StrifePortfolioHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;

import java.util.EnumSet;

public class MessageSwapOffhandStrife extends MinestuckMessage
{
	int specibusIndex;
	int weaponIndex;

	@Override
	public void generatePacket(Object... args)
	{
		data.writeInt((int) args[0]);
		data.writeInt((int) args[1]);

	}

	@Override
	public void consumePacket(ByteBuf data)
	{
		specibusIndex = data.readInt();
		weaponIndex = data.readInt();

	}

	@Override
	public void execute(EntityPlayer player)
	{
		StrifePortfolioHandler.swapOffhandWeapon(player, specibusIndex, weaponIndex);
		MinestuckNetwork.sendTo(makePacket(Type.UPDATE_STRIFE, player, MessageUpdateStrifeData.UpdateType.PORTFOLIO), player);
	}

	@Override
	public EnumSet<Side> getSenderSide()
	{
		return EnumSet.of(Side.CLIENT);
	}
}
