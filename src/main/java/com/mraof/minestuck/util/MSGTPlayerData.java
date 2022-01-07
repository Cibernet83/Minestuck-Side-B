package com.mraof.minestuck.util;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.capabilities.MinestuckCapabilities;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = Minestuck.MODID)
@Deprecated
public class MSGTPlayerData // TODO: caps ugh
{
	private static Map<IdentifierHandler.PlayerIdentifier, NBTTagCompound> dataMap = new HashMap<>();

	// Backwards compatibility
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void onWorldSave(WorldEvent.Save event)
	{
		if (event.getWorld().provider.getDimension() != 0)    //Only save one time each world-save instead of one per dimension each world-save.
			return;

		File dataFile = event.getWorld().getSaveHandler().getMapFileFromName("GodTierData");
		if (dataFile != null)
		{
			if (dataFile.exists() && dataMap.isEmpty())
				dataFile.delete();

			else if (!dataMap.isEmpty())
			{
				NBTTagCompound nbt = new NBTTagCompound();
				writeToNBT(nbt);

				try
				{
					CompressedStreamTools.writeCompressed(nbt, new FileOutputStream(dataFile));
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	private static NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		NBTTagList list = new NBTTagList();
		for (NBTBase tag : dataMap.values())
			list.appendTag(tag);

		nbt.setTag("playerData", list);

		return nbt;
	}

	// Backwards compatibility
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void onWorldLoad(WorldEvent.Load event)
	{
		if (event.getWorld().provider.getDimension() != 0 || event.getWorld().isRemote)
			return;

		ISaveHandler saveHandler = event.getWorld().getSaveHandler();
		File dataFile = saveHandler.getMapFileFromName("GodTierData");
		NBTTagCompound nbt = new NBTTagCompound();

		if (dataFile != null && dataFile.exists())
		{
			try
			{
				nbt = CompressedStreamTools.readCompressed(new FileInputStream(dataFile));
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}

		readFromNBT(nbt);
	}

	private static void readFromNBT(NBTTagCompound nbt)
	{
		dataMap.clear();
		if (nbt != null)
			for (NBTBase tag : nbt.getTagList("playerData", 10))
				dataMap.put(((NBTTagCompound) tag).hasKey("username") ? IdentifierHandler.load(tag, "username") : IdentifierHandler.load(tag, "player"), (NBTTagCompound) tag);
	}

	@SubscribeEvent
	public static void onEntityJoinWorld(EntityJoinWorldEvent event)
	{
		if (event.getWorld().isRemote || !(event.getEntity() instanceof EntityPlayer) || dataMap.isEmpty())
			return;

		IdentifierHandler.PlayerIdentifier identifier = IdentifierHandler.encode((EntityPlayer) event.getEntity());
		if (dataMap.containsKey(identifier))
		{
			event.getEntity().getCapability(MinestuckCapabilities.GOD_TIER_DATA, null).readFromNBT(dataMap.get(identifier));
			dataMap.remove(identifier);
		}
	}
}
