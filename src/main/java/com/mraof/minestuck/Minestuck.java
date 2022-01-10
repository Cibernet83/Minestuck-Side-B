package com.mraof.minestuck;

import com.mraof.minestuck.alchemy.AlchemyRecipes;
import com.mraof.minestuck.command.*;
import com.mraof.minestuck.editmode.DeployList;
import com.mraof.minestuck.editmode.ServerEditHandler;
import com.mraof.minestuck.event.CommonEventHandler;
import com.mraof.minestuck.modSupport.crafttweaker.CraftTweakerSupport;
import com.mraof.minestuck.tileentity.TileEntityTransportalizer;
import com.mraof.minestuck.tracker.MinestuckPlayerTracker;
import com.mraof.minestuck.util.Debug;
import com.mraof.minestuck.util.IdentifierHandler;
import com.mraof.minestuck.util.SylladexUtils;
import com.mraof.minestuck.world.MinestuckDimensionHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;

import java.util.Random;

import static com.mraof.minestuck.Minestuck.*;

@Mod(modid = MODID, name = NAME, version = VERSION, guiFactory = "com.mraof.minestuck.client.gui.MinestuckGuiFactory", acceptedMinecraftVersions = "[1.12,1.12.2]")
public class Minestuck
{
	public static final String NAME = "Minestuck";
	public static final String MODID = "minestuck";
	public static final String VERSION = "@VERSION@";
	public static final double startTime = System.currentTimeMillis() / 1000d; // Yes I'm being very stupid, for render effects
	/**
	 * True only if the minecraft application is client-sided
	 */
	public static boolean isClientRunning;
	/**
	 * True if the minecraft application is server-sided, or if there is an integrated server running
	 */
	public static volatile boolean isServerRunning;
	// The instance of your mod that Forge uses.
	@Instance(MODID)
	public static Minestuck instance;
	@SidedProxy(clientSide = "com.mraof.minestuck.client.ClientProxy", serverSide = "com.mraof.minestuck.CommonProxy")
	public static CommonProxy proxy;
	public static long worldSeed = 0;    //TODO proper usage of seed when generating titles, land aspects, and land dimension data
	public static boolean isMekanismLoaded;
	public static boolean isCyclicLoaded;
	public static boolean isBOPLoaded;
	public static boolean isChiselLoaded;
	public static boolean isVampirismLoaded;
	public static boolean isMysticalWorldLoaded;
	public static boolean isIndustrialForegoingLoaded;
	public static boolean isThaumLoaded;
	public static boolean isBotaniaLoaded;
	public static boolean isSplatcraftLodaded;
	public static boolean isCarryOnLoaded;
	public static boolean isVcLoaded;
	public static boolean isFutureMcLoaded;
	public static boolean isArsenalLoaded;
	public static boolean isTrophySlotsLoaded;
	public static boolean isLocksLoaded;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		isClientRunning = event.getSide().isClient();

		Debug.logger = event.getModLog();

		Minestuck.isMekanismLoaded = Loader.isModLoaded("mekanism");
		Minestuck.isCyclicLoaded = Loader.isModLoaded("cyclicmagic");
		Minestuck.isChiselLoaded = Loader.isModLoaded("chisel");
		Minestuck.isBOPLoaded = Loader.isModLoaded("biomesoplenty");
		Minestuck.isVampirismLoaded = Loader.isModLoaded("vampirism");
		Minestuck.isMysticalWorldLoaded = Loader.isModLoaded("mysticalworld");
		Minestuck.isIndustrialForegoingLoaded = Loader.isModLoaded("industrialforegoing");
		Minestuck.isThaumLoaded = Loader.isModLoaded("thaumcraft");
		Minestuck.isBotaniaLoaded = Loader.isModLoaded("botania");
		Minestuck.isSplatcraftLodaded = Loader.isModLoaded("splatcraft");
		Minestuck.isCarryOnLoaded = Loader.isModLoaded("carryon");
		Minestuck.isVcLoaded = Loader.isModLoaded("variedcommodities");
		Minestuck.isFutureMcLoaded = Loader.isModLoaded("futuremc");
		Minestuck.isArsenalLoaded = Loader.isModLoaded("minestuckarsenal");
		Minestuck.isTrophySlotsLoaded = Loader.isModLoaded("trophyslots");
		Minestuck.isLocksLoaded = Loader.isModLoaded("locks");

		Debug.logger = event.getModLog();

		MinestuckConfig.loadConfigFile(event.getSuggestedConfigurationFile(), event.getSide());

		//(new UpdateChecker()).start();

		proxy.preInit();
	}

	@EventHandler
	public void load(FMLInitializationEvent event)
	{
		//Register textures and renders
		proxy.init();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		if (Loader.isModLoaded("crafttweaker"))
			CraftTweakerSupport.applyRecipes();

		AlchemyRecipes.registerAutomaticRecipes();
	}

	@EventHandler
	public void serverAboutToStart(FMLServerAboutToStartEvent event)
	{
		isServerRunning = true;
		TileEntityTransportalizer.transportalizers.clear();
		DeployList.applyConfigValues(MinestuckConfig.deployConfigurations);
	}

	@EventHandler
	public void serverStarting(FMLServerStartingEvent event)
	{
		if (!event.getServer().isDedicatedServer() && Minestuck.class.getAnnotation(Mod.class).version().startsWith("@"))
			event.getServer().setOnlineMode(false);    //Makes it possible to use LAN in a development environment

		if (!event.getServer().isServerInOnlineMode() && MinestuckConfig.useUUID)
			Debug.warn("Because uuids might not be consistent in an offline environment, it is not recommended to use uuids for minestuck. You should disable uuidIdentification in the minestuck config.");
		if (event.getServer().isServerInOnlineMode() && !MinestuckConfig.useUUID)
			Debug.warn("Because users may change their usernames, it is normally recommended to use uuids for minestuck. You should enable uuidIdentification in the minestuck config.");

		event.registerServerCommand(new CommandCheckLand());
		event.registerServerCommand(new CommandGrist());
		event.registerServerCommand(new CommandGristSend());
		event.registerServerCommand(new CommandTransportalizer());
		event.registerServerCommand(new CommandSburbSession());
		event.registerServerCommand(new CommandSburbServer());
		event.registerServerCommand(new CommandSetRung());
		event.registerServerCommand(new CommandConsortReply());
		event.registerServerCommand(new CommandToStructure());
		event.registerServerCommand(new CommandPorkhollow());
		event.registerServerCommand(new CommandLandDebug());
		event.registerServerCommand(new CommandGodTier());
		event.registerServerCommand(new CommandGlobalSay());

		worldSeed = event.getServer().worlds[0].getSeed();
		CommonEventHandler.lastDay = event.getServer().worlds[0].getWorldTime() / 24000L;
		SylladexUtils.rand = new Random();
	}

	@EventHandler
	public void serverStarted(FMLServerStartedEvent event)
	{
		proxy.serverStarted();
	}

	@EventHandler
	public void serverStopping(FMLServerStoppingEvent event)
	{
		ServerEditHandler.onServerStopping();
	}

	@EventHandler
	public void serverClosed(FMLServerStoppedEvent event)
	{
		MinestuckDimensionHandler.unregisterDimensions();
		isServerRunning = !isClientRunning;
		MinestuckPlayerTracker.dataCheckerPermission.clear();
		IdentifierHandler.clear();
	}
}
