package com.mraof.minestuck.network.message;

import com.mraof.minestuck.network.MinestuckMessage;
import com.mraof.minestuck.network.MinestuckNetwork;
import com.mraof.minestuck.strife.StrifePortfolioHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.relauncher.Side;

public class MessageStrifeRetrieveRequest implements MinestuckMessage
{
	private int index;
	private boolean isCard;
	private EnumHand hand;

	private MessageStrifeRetrieveRequest() { }

	public MessageStrifeRetrieveRequest(int index, boolean isCard, EnumHand hand)
	{
		this.index = index;
		this.isCard = isCard;
		this.hand = hand;
	}

	public MessageStrifeRetrieveRequest(int index, boolean isCard)
	{
		this(index, isCard, EnumHand.MAIN_HAND);
	}

	public MessageStrifeRetrieveRequest(int index)
	{
		this(index, true);
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(index);
		buf.writeBoolean(isCard);
		buf.writeBoolean(hand == EnumHand.MAIN_HAND);
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		index = buf.readInt();
		isCard = buf.readBoolean();
		hand = buf.readBoolean() ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND;
	}

	@Override
	public void execute(EntityPlayer player)
	{
		if(isCard)
			StrifePortfolioHandler.retrieveCard(player, index);
		else
			StrifePortfolioHandler.retrieveWeapon(player, index, hand);
		MinestuckNetwork.sendTo(new MessageStrifePortfolio(player), player);
	}

	@Override
	public Side toSide()
	{
		return Side.SERVER;
	}
}
