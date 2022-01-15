package com.mraof.minestuck.network.skaianet;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.client.gui.GuiComputer;
import com.mraof.minestuck.client.gui.MinestuckGuiHandler;
import com.mraof.minestuck.network.MinestuckNetwork;
import com.mraof.minestuck.network.message.MessageSburbCloseRequest;
import com.mraof.minestuck.network.message.MessageSburbConnectRequest;
import com.mraof.minestuck.network.message.MessageSkaianetDataRequest;
import com.mraof.minestuck.tileentity.TileEntityComputer;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SideOnly(Side.CLIENT)
public class SkaiaClient
{

	public static int playerId;    //The id that this player is expected to have.
	//Variables
	private static Map<Integer, Map<Integer, String>> openServers = new HashMap<>();
	private static List<SburbConnection> connections = new ArrayList<>();
	private static Map<Integer, Boolean> serverWaiting = new HashMap<>();
	private static Map<Integer, Boolean> resumingClient = new HashMap<>();
	/**
	 * A map used to track chains of lands, to be used by the skybox render
	 */
	private static Map<Integer, List<Integer>> landChainMap = new HashMap<>();
	private static TileEntityComputer te = null;

	public static void clear()
	{
		openServers.clear();
		connections.clear();
		serverWaiting.clear();
		resumingClient.clear();
		landChainMap.clear();
		playerId = -1;
	}

	/**
	 * Called by a computer on interact. If it doesn't have the sufficient information,
	 * returns false and sends a request about that information.
	 *
	 * @param computer The computer. Will save this variable for later if it sends a request.
	 * @return If it currently has the necessary information.
	 */
	public static boolean requestData(TileEntityComputer computer)
	{
		boolean hasData = openServers.get(computer.ownerId) != null;
		if (!hasData)
		{
			MinestuckNetwork.sendToServer(new MessageSkaianetDataRequest(computer.ownerId));
			te = computer;
		}
		return hasData;
	}

	//Getters used by the computer
	public static int getAssociatedPartner(int playerId, boolean isClient)
	{
		for (SburbConnection c : connections)
			if (c.isMain)
				if (isClient && c.clientId == playerId)
					return c.serverId;
				else if (!isClient && c.serverId == playerId)
					return c.clientId;
		return -1;
	}

	public static Map<Integer, String> getAvailableServers(Integer playerId)
	{
		return openServers.get(playerId);
	}

	public static boolean enteredMedium(int player)
	{
		for (SburbConnection c : connections)
			if (c.isMain && c.clientId == player)
				return c.enteredGame;
		return false;
	}

	public static List<Integer> getLandChain(int id)
	{
		return landChainMap.get(id);
	}

	public static boolean isActive(int playerId, boolean isClient)
	{
		if (isClient)
			return getClientConnection(playerId) != null || resumingClient.get(playerId);
		else return serverWaiting.get(playerId);
	}

	public static SburbConnection getClientConnection(int client)
	{
		for (SburbConnection c : connections)
			if (c.isActive && c.clientId == client)
				return c;
		return null;
	}

	//Methods called from the actionPerformed method in the gui.

	/**
	 * If the color selection gui may be opened.
	 */
	public static boolean canSelect(int playerId)
	{
		if (playerId != SkaiaClient.playerId)
			return false;
		for (SburbConnection c : connections)
			if (playerId == c.clientId)
				return false;
		return true;
	}

	public static void sendConnectRequest(TileEntityComputer te, int otherPlayer, boolean isClient)    //Used for both connect, open server and resume
	{
		MinestuckNetwork.sendToServer(new MessageSburbConnectRequest(ComputerData.createData(te), otherPlayer, isClient));
	}

	public static void sendCloseRequest(TileEntityComputer te, int otherPlayer, boolean isClient)
	{
		MinestuckNetwork.sendToServer(new MessageSburbCloseRequest(te.ownerId, otherPlayer, isClient));
	}

	//Methods used by the SkaianetInfoPacket.
	public static SburbConnection getConnection(ByteBuf data)
	{
		SburbConnection c = new SburbConnection();

		c.isMain = data.readBoolean();
		if (c.isMain)
		{
			c.isActive = data.readBoolean();
			c.enteredGame = data.readBoolean();
		}
		c.clientId = data.readInt();
		c.clientName = ByteBufUtils.readUTF8String(data);
		c.serverId = data.readInt();
		c.serverName = ByteBufUtils.readUTF8String(data);

		return c;
	}

	public static void setLandChains(List<List<Integer>> landChains)
	{
		landChainMap.clear();
		for (List<Integer> list : landChains)
			for (int i : list)
				landChainMap.put(i, list);
	}

	public static void setSkaianetData(int playerId, boolean isClientResuming, boolean isServerResuming, Map<Integer, String> openServers, List<SburbConnection> connections)
	{
		if (SkaiaClient.playerId == -1)
			SkaiaClient.playerId = playerId;    //The first info packet is expected to be regarding the receiving player.

		SkaiaClient.resumingClient.put(playerId, isClientResuming);
		SkaiaClient.serverWaiting.put(playerId, isServerResuming);

		SkaiaClient.openServers.put(playerId, openServers);

		SkaiaClient.connections.removeIf((SburbConnection connection) -> connection.clientId == playerId || connection.serverId == playerId);
		SkaiaClient.connections.addAll(connections);

		GuiScreen gui = Minecraft.getMinecraft().currentScreen;
		if (gui instanceof GuiComputer)
			((GuiComputer) gui).updateGui();
		else if (te != null && te.ownerId == playerId)
		{
			if (!Minecraft.getMinecraft().player.isSneaking())
				Minecraft.getMinecraft().player.openGui(Minestuck.instance, MinestuckGuiHandler.GuiId.COMPUTER.ordinal(), te.getWorld(), te.getPos().getX(), te.getPos().getY(), te.getPos().getZ());
			te = null;
		}
	}
}
