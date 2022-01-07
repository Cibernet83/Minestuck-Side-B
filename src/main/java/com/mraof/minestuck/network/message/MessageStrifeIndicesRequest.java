package com.mraof.minestuck.network.message;

import com.mraof.minestuck.capabilities.MinestuckCapabilities;
import com.mraof.minestuck.network.MessageEntityEncoderBase;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.relauncher.Side;

import java.util.function.Consumer;

public class MessageStrifeIndicesRequest extends MessageEntityEncoderBase
{
	public MessageStrifeIndicesRequest() { }

	public MessageStrifeIndicesRequest(EntityLivingBase entity)
	{
		super(entity);
	}

	@Override
	public Consumer<ByteBuf> getEncoder(EntityLivingBase entity)
	{
		return entity.getCapability(MinestuckCapabilities.STRIFE_DATA, null)::writeSelectedIndices;
	}

	@Override
	public Consumer<ByteBuf> getDecoder(EntityLivingBase entity)
	{
		return entity.getCapability(MinestuckCapabilities.STRIFE_DATA, null)::readSelectedIndices;
	}

	@Override
	public Side toSide()
	{
		return Side.SERVER;
	}
}
