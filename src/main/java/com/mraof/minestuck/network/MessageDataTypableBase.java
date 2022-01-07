package com.mraof.minestuck.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

import java.util.LinkedHashMap;

public abstract class MessageDataTypableBase implements MinestuckMessage
{
	private LinkedHashMap<String, Class<? extends ISerializableDataType>> dataTypes = new LinkedHashMap<>();
	private LinkedHashMap<String, ISerializableDataType> data = new LinkedHashMap<>();

	protected void registerType(String string, Class<? extends ISerializableDataType> dataType)
	{
		dataTypes.put(string, dataType);
		data.put(string, null);
	}

	protected Object get(String string)
	{
		if (!data.containsKey(string))
			throw new IllegalArgumentException("No MessageDataTypable key " + string);
		return data.get(string).getValue();
	}

	protected void put(String string, ISerializableDataType dataType)
	{
		if (!data.containsKey(string))
			throw new IllegalArgumentException("No MessageDataTypable key " + string);
		data.put(string, dataType);
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		data.forEach((String key, ISerializableDataType dataType) -> {
			dataType.serialize(buf);
		});
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		dataTypes.forEach((String key, Class<? extends ISerializableDataType> dataType) -> {
			data.put(key, ISerializableDataType.deserialize(buf, dataType));
		});
	}

	@Override
	public void execute(EntityPlayer player)
	{
		data.forEach((String key, ISerializableDataType dataType) -> dataType.initialize(player.world));
		executeData(player);
	}

	public abstract void executeData(EntityPlayer player);
}
