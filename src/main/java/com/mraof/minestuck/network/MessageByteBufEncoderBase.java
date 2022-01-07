package com.mraof.minestuck.network;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.function.Consumer;

public abstract class MessageByteBufEncoderBase implements MinestuckMessage
{
	private ByteBuf data;

	public MessageByteBufEncoderBase()
	{
		this.data = Unpooled.buffer();
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		data = buf.copy();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeBytes(data);
		data.release();
	}

	public void encode(Consumer<ByteBuf> encoder)
	{
		encoder.accept(data);
	}

	public void decode(Consumer<ByteBuf> decoder)
	{
		decoder.accept(data);
		data.release();
	}
}
