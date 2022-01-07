package com.mraof.minestuck.network.message;

import com.mraof.minestuck.capabilities.api.IStrifeData;
import com.mraof.minestuck.network.MessageUpdateStrifeToClientBase;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityLivingBase;

import java.util.function.Consumer;

public class MessageStrifeDroppedCards extends MessageUpdateStrifeToClientBase
{
	private MessageStrifeDroppedCards() { }

	public MessageStrifeDroppedCards(EntityLivingBase entity)
	{
		super(entity);
	}

	@Override
	public Consumer<ByteBuf> getEncoder(IStrifeData cap)
	{
		return cap::writeDroppedCards;
	}

	@Override
	public Consumer<ByteBuf> getDecoder(IStrifeData cap)
	{
		return cap::readDroppedCards;
	}
}
