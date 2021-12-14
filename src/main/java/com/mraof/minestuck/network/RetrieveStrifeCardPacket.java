package com.mraof.minestuck.network;

import com.mraof.minestuck.strife.StrifePortfolioHandler;
import com.mraof.minestuck.MinestuckConfig;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.relauncher.Side;

import java.util.EnumSet;

public class RetrieveStrifeCardPacket extends MinestuckPacket
{
	int index;
	boolean isCard;
	EnumHand hand;

	@Override
	public MinestuckPacket generatePacket(Object... args)
	{
		data.writeInt((int) args[0]);
		data.writeBoolean(args.length <= 1 || (boolean)args[1]);
		data.writeBoolean(args.length <= 2 || args[2] == EnumHand.MAIN_HAND);
		return this;
	}

	@Override
	public MinestuckPacket consumePacket(ByteBuf data)
	{
		index = data.readInt();
		isCard = data.readBoolean();
		hand = data.readBoolean() ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND;
		return this;
	}

	@Override
	public void execute(EntityPlayer player)
	{
		if(isCard)
			StrifePortfolioHandler.retrieveCard(player, index);
		else StrifePortfolioHandler.retrieveWeapon(player, index, hand);
		MinestuckChannelHandler.sendToPlayer(makePacket(Type.UPDATE_STRIFE, player, UpdateStrifeDataPacket.UpdateType.PORTFOLIO), player);
	}

	@Override
	public EnumSet<Side> getSenderSide()
	{
		return EnumSet.of(Side.CLIENT);
	}
}