package com.cibernet.minestuckgodtier;

import com.cibernet.Minestuck.MSUConfig;
import com.cibernet.Minestuck.Minestuck;
import com.mraof.minestuck.util.Echeladder;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.File;

public class MSGTConfig
{
	public static Configuration config;
	private static Side side;

	public static boolean localizedChat;
	public static boolean multiAspectUnlocks;


	public static void load(File file, Side sideIn)
	{
		MinecraftForge.EVENT_BUS.register(MSUConfig.class);

		side = sideIn;
		config = new Configuration(file, true);

		config.load();

		loadConfigOptions(false);

		config.save();
	}

	private static void loadConfigOptions(boolean isRunning)
	{
		localizedChat = config.get("General", "localizedChat", false, "Enabling this makes players only be able to receive chat messages from nearby players unless Gift of Gab is enabled.")
				.setLanguageKey("config.minestuckgodtier.general.localizedChat").getBoolean();
		multiAspectUnlocks = config.get("General", "multiAspectUnlocks", true, "Enabling this makes certain badges require multiple kinds of Hero Stone Shards to unlock.")
				.setLanguageKey("config.minestuckgodtier.general.multiAspectUnlocks").getBoolean();


	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event)
	{
		if (event.getModID().equals(Minestuck.MODID))
		{
			//loadConfigOptions(event.isWorldRunning());
			//config.save();
		}

	}

	public static void writeToBuffer(ByteBuf data)
	{
		data.writeBoolean(localizedChat);
		data.writeBoolean(multiAspectUnlocks);
	}

	public static void readFromBuffer(ByteBuf data)
	{
		localizedChat = data.readBoolean();
		multiAspectUnlocks = data.readBoolean();
	}
}
