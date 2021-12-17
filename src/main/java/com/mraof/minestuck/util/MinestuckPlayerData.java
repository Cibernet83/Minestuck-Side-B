package com.mraof.minestuck.util;

import com.mraof.minestuck.alchemy.GristSet;
import com.mraof.minestuck.alchemy.Grist;
import com.mraof.minestuck.alchemy.MinestuckGrists;
import com.mraof.minestuck.editmode.ClientEditHandler;
import com.mraof.minestuck.inventory.captchalouge.ISylladex;
import com.mraof.minestuck.network.PacketGristCache;
import com.mraof.minestuck.network.MinestuckChannelHandler;
import com.mraof.minestuck.network.MinestuckPacket;
import com.mraof.minestuck.network.PacketPlayerData;
import com.mraof.minestuck.network.skaianet.SburbConnection;
import com.mraof.minestuck.network.skaianet.SkaianetHandler;
import com.mraof.minestuck.util.IdentifierHandler.PlayerIdentifier;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MinestuckPlayerData
{
	@SideOnly(Side.CLIENT)
	public static PlayerData clientData;
	@SideOnly(Side.CLIENT)
	private static GristSet sessionClientGrist;

	private static Map<PlayerIdentifier, PlayerData> dataMap = new HashMap<>();

	public static void onPacketRecived(PacketGristCache packet)
	{
		if (packet.targetGrist)
			sessionClientGrist = packet.values;
		else
			clientData.gristCache = packet.values;
	}

	//Server sided

	public static GristSet getClientGrist()
	{
		return ClientEditHandler.isActive() ? sessionClientGrist : clientData.gristCache;
	}

	public static GristSet getGristSet(PlayerIdentifier player)
	{
		return getData(player).gristCache;
	}

	public static void setGrist(PlayerIdentifier player, GristSet set)
	{
		getData(player).gristCache = set;
	}

	public static Title getTitle(PlayerIdentifier player)
	{
		return getData(player).title;
	}
	
	public static boolean getEffectToggle(PlayerIdentifier player)
	{
		return getData(player).effectToggle;
	}
	
	public static void setEffectToggle(PlayerIdentifier player, boolean toggle)
	{
		getData(player).effectToggle = toggle;
	}
	
	public static void writeToNBT(NBTTagCompound nbt)
	{
		NBTTagList list = new NBTTagList();
		for (PlayerData data : dataMap.values())
			list.appendTag(data.writeToNBT());

		nbt.setTag("playerData", list);
	}

	public static void readFromNBT(NBTTagCompound nbt)
	{
		dataMap.clear();
		if (nbt == null)
			return;

		NBTTagList list = nbt.getTagList("playerData", 10);
		for (int i = 0; i < list.tagCount(); i++)
		{
			NBTTagCompound dataCompound = list.getCompoundTagAt(i);
			PlayerData data = new PlayerData();
			data.readFromNBT(dataCompound);
			dataMap.put(data.player, data);
		}
	}

	public static void setTitle(PlayerIdentifier player, Title newTitle)
	{
		if (getData(player).title == null)
			getData(player).title = newTitle;
	}

	public static PlayerData getData(EntityPlayer player)
	{
		return getData(IdentifierHandler.encode(player));
	}

	public static PlayerData getData(PlayerIdentifier player)
	{
		Objects.requireNonNull(player);
		if (!dataMap.containsKey(player))
		{
			PlayerData data = new PlayerData();
			data.player = player;
			data.echeladder = new Echeladder(player);
			dataMap.put(player, data);
		}
		return dataMap.get(player);
	}

	public static GristSet getGristSet(EntityPlayer player)
	{
		if (player.world.isRemote)
			return getClientGrist();
		else return getGristSet(IdentifierHandler.encode(player));
	}
	
	public static boolean addBoondollars(EntityPlayer player, long boons)
	{
		return addBoondollars(IdentifierHandler.encode(player), boons);
	}
	
	public static boolean addBoondollars(PlayerIdentifier id, long boons)
	{
		PlayerData data = MinestuckPlayerData.getData(id);
		if(data.boondollars + boons < 0)
			return false;
		data.boondollars += boons;
		
		EntityPlayer player = id.getPlayer();
		if(player != null)
			MinestuckChannelHandler.sendToPlayer(MinestuckPacket.makePacket(MinestuckPacket.Type.PLAYER_DATA, PacketPlayerData.BOONDOLLAR, data.boondollars), player);
		return true;
	}

	public static boolean hasEntered(EntityLivingBase entity)
	{
		if (!(entity instanceof EntityPlayer))
			return false;

		IdentifierHandler.PlayerIdentifier targetIdentifier = IdentifierHandler.encode((EntityPlayer) entity);
		SburbConnection tC = SkaianetHandler.getMainConnection(targetIdentifier, true);

		return tC != null && tC.enteredGame();
	}
	
	public static class PlayerData
	{

		public PlayerIdentifier player;
		public Title title;
		public GristSet gristCache;
		public ISylladex sylladex;
		public boolean givenModus;
		public int color = -1;
		public long boondollars;
		public Echeladder echeladder;
		public boolean effectToggle = true;
		
		private void readFromNBT(NBTTagCompound nbt)
		{
			this.player = IdentifierHandler.load(nbt, "player");
			if (nbt.hasKey("grist"))
			{
				//old format
				if (nbt.getTagId("grist") == 11)
				{
					int[] array = nbt.getIntArray("grist");
					this.gristCache = new GristSet()
							.addGrist(MinestuckGrists.amber, array[0])
							.addGrist(MinestuckGrists.amethyst, array[1])
							.addGrist(MinestuckGrists.artifact, array[2])
							.addGrist(MinestuckGrists.build, array[3])
							.addGrist(MinestuckGrists.caulk, array[4])
							.addGrist(MinestuckGrists.chalk, array[5])
							.addGrist(MinestuckGrists.cobalt, array[6])
							.addGrist(MinestuckGrists.diamond, array[7])
							.addGrist(MinestuckGrists.garnet, array[8])
							.addGrist(MinestuckGrists.gold, array[9])
							.addGrist(MinestuckGrists.iodine, array[10])
							.addGrist(MinestuckGrists.marble, array[11])
							.addGrist(MinestuckGrists.mercury, array[12])
							.addGrist(MinestuckGrists.quartz, array[13])
							.addGrist(MinestuckGrists.ruby, array[14])
							.addGrist(MinestuckGrists.rust, array[15])
							.addGrist(MinestuckGrists.shale, array[16])
							.addGrist(MinestuckGrists.sulfur, array[17])
							.addGrist(MinestuckGrists.tar, array[18])
							.addGrist(MinestuckGrists.uranium, array[19])
							.addGrist(MinestuckGrists.zillium, array[20]);
				}
				else
				{
					this.gristCache = new GristSet();
					for (NBTBase nbtBase : nbt.getTagList("grist", 10))
					{
						NBTTagCompound gristTag = (NBTTagCompound) nbtBase;
						Grist type = Grist.getTypeFromString(gristTag.getString("id"));
						if(type != null)
							this.gristCache.setGrist(type, gristTag.getInteger("amount"));
					}
				}
			}
			if (nbt.hasKey("titleClass"))
				this.title = new Title(EnumClass.getClassFromInt(nbt.getByte("titleClass")), EnumAspect.getAspectFromInt(nbt.getByte("titleAspect")));
			if (nbt.hasKey("modus"))
			{
				this.sylladex = SylladexUtils.readFromNBT(nbt.getCompoundTag("modus"));
				givenModus = true;
			}
			else givenModus = nbt.getBoolean("givenModus");
			if (nbt.hasKey("color"))
				this.color = nbt.getInteger("color");
			boondollars = nbt.getLong("boondollars");
			effectToggle = nbt.getBoolean("effectToggle");
			
			echeladder = new Echeladder(player);
			echeladder.loadEcheladder(nbt);
		}

		private NBTTagCompound writeToNBT()
		{
			NBTTagCompound nbt = new NBTTagCompound();
			player.saveToNBT(nbt, "player");
			if (this.gristCache != null)
			{
				NBTTagList list = new NBTTagList();
				for (Grist type : Grist.values())
				{
					NBTTagCompound gristTag = new NBTTagCompound();
					gristTag.setString("id", String.valueOf(type.getRegistryName()));
					gristTag.setInteger("amount", this.gristCache.getGrist(type));
					list.appendTag(gristTag);
				}
				nbt.setTag("grist", list);
			}
			if (this.title != null)
			{
				nbt.setByte("titleClass", (byte) this.title.getHeroClass().ordinal());
				nbt.setByte("titleAspect", (byte) this.title.getHeroAspect().ordinal());
			}
			if (this.sylladex != null)
				nbt.setTag("modus", SylladexUtils.writeToNBT(sylladex));
			else nbt.setBoolean("givenModus", givenModus);
			nbt.setInteger("color", this.color);
			nbt.setLong("boondollars", boondollars);
			nbt.setBoolean("effectToggle", effectToggle);
			
			echeladder.saveEcheladder(nbt);
			return nbt;
		}

	}

}
