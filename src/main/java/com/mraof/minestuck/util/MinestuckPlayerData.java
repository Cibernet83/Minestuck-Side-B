package com.mraof.minestuck.util;

import com.mraof.minestuck.alchemy.Grist;
import com.mraof.minestuck.alchemy.GristSet;
import com.mraof.minestuck.alchemy.MinestuckGrist;
import com.mraof.minestuck.editmode.ClientEditHandler;
import com.mraof.minestuck.network.MinestuckNetwork;
import com.mraof.minestuck.network.message.MessageBoondollars;
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

	public static void setGristCache(GristSet gristSet, boolean targetGrist)
	{
		if (targetGrist)
			sessionClientGrist = gristSet;
		else
			clientData.gristCache = gristSet;
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
			MinestuckNetwork.sendTo(new MessageBoondollars(data.boondollars), player);
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
							.addGrist(MinestuckGrist.amber, array[0])
							.addGrist(MinestuckGrist.amethyst, array[1])
							.addGrist(MinestuckGrist.artifact, array[2])
							.addGrist(MinestuckGrist.build, array[3])
							.addGrist(MinestuckGrist.caulk, array[4])
							.addGrist(MinestuckGrist.chalk, array[5])
							.addGrist(MinestuckGrist.cobalt, array[6])
							.addGrist(MinestuckGrist.diamond, array[7])
							.addGrist(MinestuckGrist.garnet, array[8])
							.addGrist(MinestuckGrist.gold, array[9])
							.addGrist(MinestuckGrist.iodine, array[10])
							.addGrist(MinestuckGrist.marble, array[11])
							.addGrist(MinestuckGrist.mercury, array[12])
							.addGrist(MinestuckGrist.quartz, array[13])
							.addGrist(MinestuckGrist.ruby, array[14])
							.addGrist(MinestuckGrist.rust, array[15])
							.addGrist(MinestuckGrist.shale, array[16])
							.addGrist(MinestuckGrist.sulfur, array[17])
							.addGrist(MinestuckGrist.tar, array[18])
							.addGrist(MinestuckGrist.uranium, array[19])
							.addGrist(MinestuckGrist.zillium, array[20]);
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
				this.title = new Title(EnumClass.values()[nbt.getByte("titleClass")], EnumAspect.values()[nbt.getByte("titleAspect")]);
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
			nbt.setInteger("color", this.color);
			nbt.setLong("boondollars", boondollars);
			nbt.setBoolean("effectToggle", effectToggle);
			
			echeladder.saveEcheladder(nbt);
			return nbt;
		}

	}

}
