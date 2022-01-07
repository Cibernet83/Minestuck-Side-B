package com.mraof.minestuck.network.message;

import com.mraof.minestuck.network.MinestuckMessage;
import com.mraof.minestuck.network.skaianet.SburbConnection;
import com.mraof.minestuck.network.skaianet.SkaiaClient;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageSkaianetData implements MinestuckMessage
{
	private int playerId;
	private boolean isClientResuming, isServerResuming;
	private Map<Integer, String> openServers;
	private List<SburbConnection> connections;

	public MessageSkaianetData() { }

	public MessageSkaianetData(int playerId, boolean isClientResuming, boolean isServerResuming, Map<Integer, String> openServers, List<SburbConnection> connections)
	{
		this.playerId = playerId;
		this.isClientResuming = isClientResuming;
		this.isServerResuming = isServerResuming;
		this.openServers = openServers;
		this.connections = connections;
	}
	
	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(playerId);
		buf.writeBoolean(isClientResuming);
		buf.writeBoolean(isServerResuming);
		buf.writeInt(openServers.size());
		openServers.forEach((Integer id, String username) -> {
			buf.writeInt(id);
			ByteBufUtils.writeUTF8String(buf, username);
		});
		for(SburbConnection connection : connections)
			connection.writeBytes(buf);
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.playerId = buf.readInt();
		isClientResuming = buf.readBoolean();
		isServerResuming = buf.readBoolean();
		openServers = new HashMap<>();
		int size = buf.readInt();
		for (int i = 0; i < size; i++)
			openServers.put(buf.readInt(), ByteBufUtils.readUTF8String(buf));
		connections = new ArrayList<>();
		while (buf.readableBytes() > 0)
			try
			{
				connections.add(SkaiaClient.getConnection(buf));
			}
			catch (IllegalStateException e)
			{
				e.printStackTrace();
			}
	}

	@Override
	public void execute(EntityPlayer player)
	{
		SkaiaClient.setSkaianetData(playerId, isClientResuming, isServerResuming, openServers, connections);
	}

	@Override
	public Side toSide() {
		return Side.CLIENT;
	}
}
