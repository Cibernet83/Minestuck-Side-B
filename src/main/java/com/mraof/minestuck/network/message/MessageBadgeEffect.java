package com.mraof.minestuck.network.message;

import com.mraof.minestuck.capabilities.MinestuckCapabilities;
import com.mraof.minestuck.network.ISerializableDataType;
import com.mraof.minestuck.network.MinestuckMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;

import java.util.Arrays;

public class MessageBadgeEffect implements MinestuckMessage
{
	private int entityId;
	private String key;
	private ISerializableDataType value;

	private MessageBadgeEffect() { }

	public MessageBadgeEffect(Entity entity, String key, ISerializableDataType value)
	{
		this.entityId = entity.getEntityId();
		this.key = key;
		this.value = value;
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(entityId);
		ByteBufUtils.writeUTF8String(buf, key);
		if (value != null)
		{
			buf.writeInt(Arrays.asList(ISerializableDataType.REGISTRY.keySet().toArray()).indexOf(value.getClass()));
			value.serialize(buf);
		}
		else
			buf.writeInt(-1);
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		entityId = buf.readInt();
		key = ByteBufUtils.readUTF8String(buf);
		int classIndex = buf.readInt();
		if (classIndex > 0)
			value = ISerializableDataType.deserialize(buf, (Class) ISerializableDataType.REGISTRY.keySet().toArray()[classIndex]);
	}

	@Override
	public void execute(EntityPlayer player)
	{
		if (value != null)
			value.initialize(player.world);
		player.world.getEntityByID(entityId).getCapability(MinestuckCapabilities.BADGE_EFFECTS, null).receive(key, value);
	}

	@Override
	public Side toSide()
	{
		return Side.CLIENT;
	}
}
