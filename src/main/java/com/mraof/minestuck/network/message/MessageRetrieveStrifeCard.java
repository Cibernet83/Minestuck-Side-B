package com.mraof.minestuck.network.message;

import com.mraof.minestuck.network.MinestuckMessage;
import com.mraof.minestuck.network.MinestuckNetwork;
import com.mraof.minestuck.strife.StrifePortfolioHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.relauncher.Side;

import java.util.EnumSet;

public class MessageRetrieveStrifeCard extends MinestuckMessage
{
	int index;
	boolean isCard;
	EnumHand hand;

	@Override
	public void generatePacket(Object... args)
	{
		data.writeInt((int) args[0]);
		data.writeBoolean(args.length <= 1 || (boolean)args[1]);
		data.writeBoolean(args.length <= 2 || args[2] == EnumHand.MAIN_HAND);

	}

	@Override
	public void consumePacket(ByteBuf data)
	{
		index = data.readInt();
		isCard = data.readBoolean();
		hand = data.readBoolean() ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND;

	}

	@Override
	public void execute(EntityPlayer player)
	{
		if(isCard)
			StrifePortfolioHandler.retrieveCard(player, index);
		else StrifePortfolioHandler.retrieveWeapon(player, index, hand);
		MinestuckNetwork.sendTo(makePacket(Type.UPDATE_STRIFE, player, MessageUpdateStrifeData.UpdateType.PORTFOLIO), player);
	}

	@Override
	public EnumSet<Side> getSenderSide()
	{
		return EnumSet.of(Side.CLIENT);
	}
}
