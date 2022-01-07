package com.mraof.minestuck.network.message;

import com.mraof.minestuck.capabilities.api.IStrifeData;
import com.mraof.minestuck.network.MessageUpdateStrifeToClientBase;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityLivingBase;

import java.util.function.Consumer;

public class MessageStrifePortfolio extends MessageUpdateStrifeToClientBase
{
	private int[] indices;

	private MessageStrifePortfolio() { }

	public MessageStrifePortfolio(EntityLivingBase entity, int... indices)
	{
		super(entity);
		this.indices = indices;
	}

	@Override
	public Consumer<ByteBuf> getEncoder(IStrifeData cap)
	{
		return (ByteBuf buf) -> cap.writePortfolio(buf, indices);
	}

	@Override
	public Consumer<ByteBuf> getDecoder(IStrifeData cap)
	{
		return cap::readPortfolio;
	}
}
