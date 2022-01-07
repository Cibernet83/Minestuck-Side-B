package com.mraof.minestuck.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

import java.util.function.Consumer;

public abstract class MessageEntityEncoderBase extends MessageByteBufEncoderBase
{
	private int entityId;

	public MessageEntityEncoderBase() { }

	public MessageEntityEncoderBase(EntityLivingBase entity)
	{
		this.entityId = entity.getEntityId();
		encode(getEncoder(entity));
	}

	public abstract Consumer<ByteBuf> getEncoder(EntityLivingBase entity);
	public abstract Consumer<ByteBuf> getDecoder(EntityLivingBase entity);

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(entityId);
		super.toBytes(buf);
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		entityId = buf.readInt();
		super.fromBytes(buf);
	}

	@Override
	public void execute(EntityPlayer player)
	{
		EntityLivingBase entity = (EntityLivingBase) player.world.getEntityByID(entityId);
		decode(getDecoder(entity));
	}
}
