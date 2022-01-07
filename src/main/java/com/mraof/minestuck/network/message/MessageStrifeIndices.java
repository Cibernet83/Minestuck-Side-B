package com.mraof.minestuck.network.message;

import com.mraof.minestuck.capabilities.api.IStrifeData;
import com.mraof.minestuck.network.MessageUpdateStrifeToClientBase;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityLivingBase;

import java.util.function.Consumer;

public class MessageStrifeIndices extends MessageUpdateStrifeToClientBase
{
	public MessageStrifeIndices() { }

	public MessageStrifeIndices(EntityLivingBase entity)
	{
		super(entity);
	}

	@Override
	public Consumer<ByteBuf> getEncoder(IStrifeData cap)
	{
		return cap::writeSelectedIndices;
	}

	@Override
	public Consumer<ByteBuf> getDecoder(IStrifeData cap)
	{
		return cap::readSelectedIndices;
	}
}
