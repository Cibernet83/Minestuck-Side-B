package com.mraof.minestuck.network.skaianet;

import com.mraof.minestuck.editmode.DeployList;
import com.mraof.minestuck.util.Debug;
import com.mraof.minestuck.util.IdentifierHandler;
import com.mraof.minestuck.util.IdentifierHandler.PlayerIdentifier;
import com.mraof.minestuck.world.MinestuckDimensionHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SburbConnection
{
	
	ComputerData client;
	/**
	 * Identifier for the client player. Beware that this might be null if connection.client isn't null
	 * It is recommended to use connection.getClientName() instead if possible
	 */
	PlayerIdentifier clientIdentifier;
	ComputerData server;
	/**
	 * Identifier for the server player. Beware that this might be null if connection.server isn't null
	 * It is recommended to use connection.getServerName() instead if possible
	 */
	PlayerIdentifier serverIdentifier;
	
	/**
	 * Display name used by computer guis
	 */
	@SideOnly(Side.CLIENT)
	String clientName, serverName;
	/**
	 * Id for identifying players clientside
	 */
	@SideOnly(Side.CLIENT)
	int clientId, serverId;
	
	boolean isActive;
	boolean isMain;
	boolean enteredGame;
	boolean canSplit;
	int clientHomeLand;
	int artifactType;
	/**
	 * If the client will have frog breeding as quest, the array will be extended and the new positions will hold the gear.
	 */
	boolean[] givenItemList = new boolean[DeployList.getEntryCount()];
	NBTTagList unregisteredItems = new NBTTagList();
	
	//Only used by the edit handler
	public int centerX, centerZ;
	public NBTTagList inventory;
	
	//Non-saved variables used by the edit handler
	public double posX, posZ;
	public boolean useCoordinates;
	
	SburbConnection()
	{
		this.canSplit = true;
		this.isActive = true;
	}
	
	public PlayerIdentifier getClientIdentifier()
	{
		if(clientIdentifier == null)
			return client.owner;
		else return clientIdentifier;
	}
	
	public PlayerIdentifier getServerIdentifier()
	{
		if(serverIdentifier == null)
			return server.owner;
		else return serverIdentifier;
	}
	
	public ComputerData getClientData() {return client;}
	public ComputerData getServerData() {return server;}
	public boolean enteredGame(){return enteredGame;}
	public boolean isMain(){return isMain;}
	public int getClientDimension() {return clientHomeLand;}
	public boolean[] givenItems(){return givenItemList;}
	@SideOnly(Side.CLIENT)
	public String getClientDisplayName() {return clientName;}
	@SideOnly(Side.CLIENT)
	public String getServerDisplayName() {return serverName;}
	@SideOnly(Side.CLIENT)
	public int getClientId() {return clientId;}
	@SideOnly(Side.CLIENT)
	public int getServerId() {return serverId;}
	
	public void writeBytes(ByteBuf data)
	{
		data.writeBoolean(isMain);
		if(isMain){
			data.writeBoolean(isActive);
			data.writeBoolean(enteredGame);
		}
		data.writeInt(getClientIdentifier().getId());
		ByteBufUtils.writeUTF8String(data, getClientIdentifier().getUsername());
		data.writeInt(getServerIdentifier().getId());
		ByteBufUtils.writeUTF8String(data, getServerIdentifier().getUsername());
	}

	public NBTTagCompound write()
	{
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setBoolean("isMain", isMain);
		if(inventory != null)
			nbt.setTag("inventory", inventory);
		if(isMain)
		{
			nbt.setBoolean("isActive", isActive);
			nbt.setBoolean("enteredGame", enteredGame);
			nbt.setBoolean("canSplit", canSplit);
			NBTTagList list = unregisteredItems.copy();
			String[] deployNames = DeployList.getNameList();
			for(int i = 0; i < givenItemList.length; i++)
			{
				if(givenItemList[i])
					list.appendTag(new NBTTagString(deployNames[i]));
			}
			
			nbt.setTag("givenItems", list);
			if(enteredGame)
			{
				nbt.setInteger("clientLand", clientHomeLand);
			}
		}
		if(isActive)
		{
			nbt.setTag("client", client.write());
			nbt.setTag("server", server.write());
		}
		else
		{
			getClientIdentifier().saveToNBT(nbt, "client");
			getServerIdentifier().saveToNBT(nbt, "server");
		}
		nbt.setInteger("artifact", artifactType);
		return nbt;
	}

	public void read(NBTTagCompound nbt)
	{
		isMain = nbt.getBoolean("isMain");
		if(nbt.hasKey("inventory"))
			inventory = (NBTTagList) nbt.getTag("inventory");
		if(isMain)
		{
			isActive = nbt.getBoolean("isActive");
			enteredGame = nbt.getBoolean("enteredGame");
			
			if(nbt.hasKey("canSplit"))
				canSplit = nbt.getBoolean("canSplit");
			NBTTagList list = nbt.getTagList("givenItems", 8);
			for(int i = 0; i < list.tagCount(); i++)
			{
				String name = list.getStringTagAt(i);
				int ordinal = DeployList.getOrdinal(name);
				if(ordinal == -1)
					unregisteredItems.appendTag(new NBTTagString(name));
				else givenItemList[ordinal] = true;
			}
		}
		if(isActive)
		{
			client = new ComputerData();
			client.read(nbt.getCompoundTag("client"));
			server = new ComputerData();
			server.read(nbt.getCompoundTag("server"));
		}
		else
		{
			clientIdentifier = IdentifierHandler.load(nbt, "client");
			serverIdentifier = IdentifierHandler.load(nbt, "server");
		}
		if(enteredGame)
		{
			clientHomeLand = nbt.getInteger("clientLand");
			if(MinestuckDimensionHandler.isLandDimension(clientHomeLand))
			{
				BlockPos spawn = MinestuckDimensionHandler.getSpawn(clientHomeLand);
				if(spawn != null)
				{
					centerX = spawn.getX();
					centerZ = spawn.getZ();
				} else
				{
					Debug.errorf("While loading skaianet, the dimension %d was registered as a land dimension, but without having a spawn point. This should not happen!", clientHomeLand);
					centerX = centerZ = 0;
				}
			} else
			{
				Debug.errorf("The connection between %s and %s had a home dimension %d that isn't a land dimension. For safety measures, the connection will be loaded as if the player had not yet entered.", getClientIdentifier().getUsername(), getServerIdentifier().getUsername(), clientHomeLand);
				enteredGame = false;
			}
		}
		artifactType = nbt.getInteger("artifact");
	}
	
}
