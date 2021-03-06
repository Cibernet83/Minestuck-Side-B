package com.mraof.minestuck;

import com.mraof.minestuck.editmode.ServerEditHandler;
import com.mraof.minestuck.inventory.ContainerHandler;
import com.mraof.minestuck.util.Debug;
import com.mraof.minestuck.util.Echeladder;
import com.mraof.minestuck.world.MinestuckDimensionHandler;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.UserListOpsEntry;
import net.minecraft.world.GameType;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GLContext;

import java.io.File;

public class MinestuckConfig
{
	public static Configuration config;
	public static Side gameSide;

	@SideOnly(Side.CLIENT)
	public static int clientOverworldEditRange;
	@SideOnly(Side.CLIENT)
	public static int clientLandEditRange;
	@SideOnly(Side.CLIENT)
	public static int clientCardCost;
	@SideOnly(Side.CLIENT)
	public static int clientAlchemiterStacks;
	@SideOnly(Side.CLIENT)
	public static byte clientTreeAutobalance;
	@SideOnly(Side.CLIENT)
	public static byte echeladderAnimation;
	@SideOnly(Side.CLIENT)
	public static boolean clientGiveItems;
	@SideOnly(Side.CLIENT)
	public static boolean clientDisableGristWidget;
	@SideOnly(Side.CLIENT)
	public static boolean clientHardMode;
	@SideOnly(Side.CLIENT)
	public static boolean loginColorSelector;
	@SideOnly(Side.CLIENT)
	public static boolean dataCheckerAccess;
	@SideOnly(Side.CLIENT)
	public static boolean alchemyIcons;
	@SideOnly(Side.CLIENT)
	public static boolean preEntryEcheladder;

	public static boolean hardMode = false;
	public static boolean generateCruxiteOre;
	public static boolean generateUraniumOre;
	public static boolean privateComputers;
	public static boolean globalSession;
	public static boolean forceMaxSize = true;
	public static boolean giveItems;
	public static boolean specialCardRenderer;
	public static boolean dropItemsInCards;
	public static boolean entryCrater;
	public static boolean keepDimensionsLoaded;
	public static boolean adaptEntryBlockHeight;
	public static boolean allowSecondaryConnections;    //TODO make open server not an option after having a main connection (for when this is set to false)
	public static boolean disableGristWidget;
	public static boolean vanillaOreDrop;
	public static boolean echeladderProgress;
	public static boolean aspectEffects;
	public static boolean useUUID;
	public static boolean playerSelectedTitle;
	public static boolean canBreakGates;
	public static boolean disableGiclops;
	public static boolean showGristChanges;
	public static boolean stopSecondEntry;
	public static boolean gristRefund;
	public static boolean needComputer;
	public static boolean cruxtruderIntake;
	public static boolean skaianetCheck;
	public static int artifactRange;
	public static int overworldEditRange;
	public static int landEditRange;
	public static int initialModusSize;
	public static String[] defaultModusTypes;
	public static int modusMaxSize;
	public static int cardCost;
	public static int oreMultiplier;
	public static int alchemiterMaxStacks;
	/**
	 * 0: Make the player's new server player his/her old server player's server player
	 * 1: The player that lost his/her server player will have an idle main connection until someone without a client player connects to him/her.
	 * (Will try to put a better explanation somewhere else later)
	 */
	public static int escapeFailureMode;
	public static int preEntryRungLimit;
	public static int[] forbiddenDimensionsTpz;
	public static byte treeModusSetting;
	/**
	 * An option related to dropping the sylladex on death
	 * If 0: only captchalouged items are dropped. If 1: Both captchalouged items and cards are dropped. If 2: All items and cards.
	 */
	public static byte sylladexDropMode;
	public static byte dataCheckerPermission;
	public static int landAnimalSpawnAmount = 10;

	public static boolean[] deployConfigurations;

	//Secret configuration options
	public static boolean secretConfig = false;
	public static boolean disableCruxite = false;
	public static boolean disableUranium = false;
	public static int cruxiteVeinsPerChunk = 10;
	public static int uraniumVeinsPerChunk = 10;
	public static int baseCruxiteVeinSize = 6;
	public static int baseUraniumVeinSize = 3;
	public static int bonusCruxiteVeinSize = 3;
	public static int bonusUraniumVeinSize = 3;
	public static int cruxiteStratumMin = 0;
	public static int uraniumStratumMin = 0;
	public static int cruxiteStratumMax = 60;
	public static int uraniumStratumMax = 30;

	public static double zillystoneYields;
	public static double gristDropsMultiplier;
	public static int baseZillystoneLuck;
	public static boolean IDAlchemy;

	//Strife
	public static boolean restrictedStrife;
	public static boolean keepPortfolioOnDeath;
	public static int abstrataSwitcherRung;
	public static int strifeDeckMaxSize;
	public static int strifeCardMobDrops;
	public static double weaponAttackMultiplier;

	public static boolean localizedChat;
	public static boolean multiAspectUnlocks;

	static void loadConfigFile(File file, Side side)
	{
		gameSide = side;
		config = new Configuration(file, true);
		config.load();

		MinestuckDimensionHandler.skaiaProviderTypeId = config.get("IDs", "skaiaProviderTypeId", 2).setRequiresMcRestart(true).setLanguageKey("config.minestuck.skaiaProviderTypeId").getInt();
		MinestuckDimensionHandler.skaiaDimensionId = config.get("IDs", "skaiaDimensionId", 2).setRequiresMcRestart(true).setLanguageKey("config.minestuck.skaiaDimensionId").getInt();
		MinestuckDimensionHandler.landProviderTypeId = config.get("IDs", "landProviderTypeId", 3).setRequiresMcRestart(true).setLanguageKey("config.minestuck.landProviderTypeId").getInt();
		MinestuckDimensionHandler.landDimensionIdStart = config.get("IDs", "landDimensionIdStart", 3).setRequiresMcRestart(true).setLanguageKey("config.minestuck.landDimensionIdStart").getInt();
		MinestuckDimensionHandler.biomeIdStart = config.get("IDs", "biomeIdStart", 50).setRequiresMcRestart(true).setMinValue(40).setMaxValue(120).setLanguageKey("config.minestuck.biomeIdStart").getInt();

		keepDimensionsLoaded = config.get("General", "keepDimensionsLoaded", true, "").setLanguageKey("config.minestuck.keepDimensionsLoaded").setRequiresMcRestart(true).getBoolean();
		oreMultiplier = config.get("General", "oreMultiplier", 1, "Multiplies the cost for the 'contents' of an ore. Set to 0 to disable alchemizing ores.").setMinValue(0).setLanguageKey("config.minestuck.oreMultiplier").setRequiresMcRestart(true).getInt();

		//Debug.isDebugMode = config.get("General", "Print Debug Messages", true, "Whenether the game should print debug messages or not.").setShowInGui(false).getBoolean();

		loadBasicConfigOptions(false);

		config.save();
	}

	static void loadBasicConfigOptions(boolean worldRunning)
	{
		config.getCategory("IDs").setLanguageKey("config.minestuck.IDs");
		config.getCategory("Modus").setLanguageKey("config.minestuck.modus");

		if (!worldRunning)
		{
			ContainerHandler.windowIdStart = config.get("IDs", "specialWindowIdStart", -10).setLanguageKey("config.minestuck.windowIdStart").setRequiresWorldRestart(true).getInt();

			String setting = config.get("Modus", "forceAutobalance", "both", "This determines if auto-balance should be forced. 'both' if the player should choose, 'on' if forced at on, and 'off' if forced at off.", new String[]{"both", "off", "on"}).setRequiresWorldRestart(true).setLanguageKey("config.minestuck.forceAutobalance").getString();
			treeModusSetting = (byte) (setting.equals("both") ? 0 : setting.equals("on") ? 1 : 2);

			giveItems = config.get("General", "giveItems", false, "Setting this to true replaces editmode with the old Give Items button.").setLanguageKey("config.minestuck.giveItems").setRequiresWorldRestart(true).getBoolean();

			deployConfigurations = new boolean[1];
			deployConfigurations[0] = config.get("General", "deployCard", false, "Determines if a card with a captcha card punched on it should be added to the deploy list or not.").setLanguageKey("config.minestuck.deployCard").setRequiresWorldRestart(true).getBoolean();
			cardCost = config.get("General", "cardCost", 1, "An integer that determines how much a captchalouge card costs to alchemize").setMinValue(1).setLanguageKey("config.minestuck.cardCost").setRequiresWorldRestart(true).getInt();

			globalSession = config.get("General", "globalSession", false, "Whenether all connetions should be put into a single session or not.").setRequiresWorldRestart(true).setLanguageKey("config.minestuck.globalSession").getBoolean();
			generateCruxiteOre = config.get("General", "generateCruxiteOre", true, "If cruxite ore should be generated in the overworld.").setRequiresWorldRestart(true).setLanguageKey("config.minestuck.generateCruxiteOre").getBoolean();
			generateUraniumOre = config.get("General", "generateUraniumOre", true, "If uranium ore should be generated in the overworld.").setRequiresWorldRestart(true).setLanguageKey("config.minestuck.generateUraniumOre").getBoolean();
			overworldEditRange = config.get("General", "overworldEditRange", 15, "A number that determines how far away from the computer an editmode player may be before entry.", 3, 50).setRequiresWorldRestart(true).setLanguageKey("config.minestuck.overworldEditRange").getInt();
			landEditRange = config.get("General", "landEditRange", 30, "A number that determines how far away from the center of the brought land that an editmode player may be after entry.", 3, 50).setRequiresWorldRestart(true).setLanguageKey("config.minestuck.landEditRange").getInt();
			disableGristWidget = config.get("General", "disableGristWidget", false).setLanguageKey("config.minestuck.disableGristWidget").setRequiresWorldRestart(true).getBoolean();
			preEntryRungLimit = config.get("General", "preEntryRungLimit", 6, "The highest rung you can get before entering medium. Note that the first rung is indexed as 0, the second as 1 and so on.", 0, Echeladder.RUNG_COUNT - 1).setLanguageKey("config.minestuck.preEntryRungLimit").setRequiresWorldRestart(true).getInt();
			useUUID = config.get("General", "uuidIdentification", true, "If this is set to true, minestuck will use uuids to refer to players in the saved data. On false it will instead use the old method based on usernames.").setLanguageKey("config.minestuck.uuidIdentification").setRequiresWorldRestart(true).getBoolean();
		}

		initialModusSize = config.get("Modus", "initialModusSize", 5).setMinValue(0).setLanguageKey("config.minestuck.initialModusSize").getInt();
		defaultModusTypes = config.get("Modus", "defaultModusTypes", new String[]{Minestuck.MODID + ":stack", Minestuck.MODID + ":queue"},
				"An array with the possible modus types to be assigned. Written with mod-id and modus name, for example \"" + Minestuck.MODID + ":queue_stack\" or \"" + Minestuck.MODID + ":hashtable\"").setLanguageKey("config.minestuck.defaultModusType").getStringList();
		modusMaxSize = config.get("Modus", "modusMaxSize", 0, "The max size on a modus. Ignored if the value is 0.").setMinValue(0).setLanguageKey("config.minestuck.modusMaxSize").getInt();
		if (initialModusSize > modusMaxSize && modusMaxSize > 0)
			initialModusSize = modusMaxSize;
		String setting = config.get("Modus", "itemDropMode", "cardsAndItems", "Determines which items from the modus that are dropped on death. \"items\": Only the items are dropped. \"cardsAndItems\": Both items and cards are dropped. (So that you have at most initialModusSize amount of cards) \"all\": Everything is dropped, even the modus.", new String[]{"items", "cardsAndItems", "all"}).setLanguageKey("config.minestuck.itemDropMode").getString();
		if (setting.equals("items"))
			sylladexDropMode = 0;
		else if (setting.equals("cardsAndItems"))
			sylladexDropMode = 1;
		else sylladexDropMode = 2;
		dropItemsInCards = config.get("Modus", "dropItemsInCards", true, "When sylladexes are droppable, this option determines if items should be dropped inside of cards or items and cards as different stacks.").setLanguageKey("config.minestuck.dropItemsInCards").getBoolean();

		privateComputers = config.get("General", "privateComputers", true, "True if computers should only be able to be used by the owner.").setLanguageKey("config.minestuck.privateComputers").getBoolean();

		deployConfigurations = new boolean[2];
		deployConfigurations[0] = config.get("General", "deployCard", false, "Determines if a card with a captcha card punched on it should be added to the deploy list or not.").setLanguageKey("config.minestuck.deployCard").setRequiresWorldRestart(true).getBoolean();
		deployConfigurations[1] = config.get("General", "portableMachines", false, "Determines if the small portable machines should be included in the deploy list.").setLanguageKey("config.minestuck.portableMachines").setRequiresWorldRestart(true).getBoolean();
		cardCost = config.get("General", "cardCost", 1, "An integer that determines how much a captchalouge card costs to alchemize").setMinValue(1).setLanguageKey("config.minestuck.cardCost").setRequiresWorldRestart(true).getInt();

		generateCruxiteOre = config.get("General", "generateCruxiteOre", true, "If cruxite ore should be generated in the overworld.").setRequiresWorldRestart(true).setLanguageKey("config.minestuck.generateCruxiteOre").getBoolean();
		generateUraniumOre = config.get("General", "generateUraniumOre", false, "If uranium ore should be generated in the overworld.").setRequiresWorldRestart(true).setLanguageKey("config.minestuck.generateUraniumOre").getBoolean();
		globalSession = config.get("General", "globalSession", false, "Whenether all connetions should be put into a single session or not.").setLanguageKey("config.minestuck.globalSession").getBoolean();
		overworldEditRange = config.get("General", "overworldEditRange", 15, "A number that determines how far away from the computer an editmode player may be before entry.", 3, 50).setRequiresWorldRestart(true).setLanguageKey("config.minestuck.overworldEditRange").getInt();
		landEditRange = config.get("General", "landEditRange", 30, "A number that determines how far away from the center of the brought land that an editmode player may be after entry.", 3, 50).setRequiresWorldRestart(true).setLanguageKey("config.minestuck.landEditRange").getInt();
		artifactRange = config.get("General", "artifactRange", 30, "Radius of the land brought into the medium.", 3, 50).setLanguageKey("config.minestuck.artifactRange").getInt();

		entryCrater = config.get("General", "entryCrater", true, "Disable this to prevent craters from people entering the medium.").setLanguageKey("config.minestuck.entryCrater").getBoolean();
		adaptEntryBlockHeight = config.get("General", "adaptEntryBlockHeight", true, "Adapt the transferred height to make the top non-air block to be placed at y:128. Makes entry take slightly longer.").setLanguageKey("config.minestuck.adaptEntryBlockHeight").getBoolean();
		allowSecondaryConnections = config.get("General", "secondaryConnections", true, "Set this to true to allow so-called 'secondary connections' to be created.").setLanguageKey("config.minestuck.secondaryConnections").getBoolean();    //Server lists need to be updated if this gets changeable in-game
		vanillaOreDrop = config.get("General", "vanillaOreDrop", false, "If this is true, the custom vanilla ores will drop the standard vanilla ores when mined, instead of the custom type.").setLanguageKey("config.minestuck.vanillaOreDrop").getBoolean();
		echeladderProgress = config.get("General", "echeladderProgress", false, "If this is true, players will be able to see their progress towards the next rung. This is server side and will only be active in multiplayer if the server/Lan host has it activated.").setLanguageKey("config.minestuck.echeladderProgress").getBoolean();
		aspectEffects = config.get("General", "aspectEffects", true, "If this is true, players will gain certain potion effects once they reach a certain rung based on their aspect.").setLanguageKey("config.minestuck.aspectEffects").getBoolean();
		playerSelectedTitle = config.get("General", "playerSelectedTitle", false, "Enable this to let players select their own title. They will however not be able to select the Lord or Muse as class.").setLanguageKey("config.minestuck.playerSelectedTitle").getBoolean();
		canBreakGates = config.get("General", "canBreakGates", true, "Lets gates be destroyed by explosions. Turning this off will make gates use the same explosion resistance as bedrock.").setLanguageKey("config.minestuck.canBreakGates").getBoolean();
		disableGiclops = config.get("General", "disableGiclops", true, "Right now, the giclops pathfinding is currently causing huge amounts of lag due to their size. This option is a short-term solution that will disable giclops spawning and remove all existing giclopes.").setLanguageKey("config.minestuck.disableGiclops").getBoolean();
		showGristChanges = config.get("General", "showGristChanges", true, "If this is true, grist change messages will appear").setLanguageKey("config.minestuck.showGristChanges").getBoolean();
		forbiddenDimensionsTpz = config.get("General", "forbiddenDimensionsTpz", new int[0], "A list of dimension id's that you cannot travel to or from using transportalizers.").setLanguageKey("config.minestuck.forbiddenDimensionsTpz").getIntList();
		stopSecondEntry = config.get("General", "stopSecondEntry", false, "If this is true, players may only use an artifact once, even if they end up in the overworld again.").setLanguageKey("config.minestuck.stopSecondEntry").getBoolean();
		gristRefund = config.get("General", "gristRefund", false, "Enable this and players will get a (full) grist refund from breaking blocks in editmode.").setLanguageKey("config.minestuck.gristRefund").getBoolean();
		needComputer = config.get("General", "needComputer", false, "If this is true, players need to have a computer nearby to Enter").setLanguageKey("config.minestuck.needComputer").getBoolean();
		cruxtruderIntake = config.get("General", "cruxtruderIntake", false, "If enabled, the regular cruxtruder will require raw cruxite to function, which is inserted through the pipe.").setLanguageKey("config.minestuck.cruxtruderIntake").getBoolean();
		alchemiterMaxStacks = config.get("General", "alchemiterMaxStacks", 16, "The number of stacks that can be alchemized at the same time with the alchemiter.", 1, 999).setLanguageKey("config.minestuck.alchemiterMaxStacks").getInt();
		skaianetCheck = config.get("General", "skaianetCheck", true, "If enabled, will during certain moments perform a check on all connections and computers that are in use. Recommended to turn off if there is a need to improve performance, however skaianet-related bugs might appear when done so.").setLanguageKey("config.minestuck.skaianetCheck").getBoolean();

		if (config.hasKey("General", "hardMode"))
			hardMode = config.get("General", "hardMode", false).getBoolean();    //Not fully fleshed out yet

		if (config.hasKey("General", "secret"))
			secretConfig = config.get("General", "secret", false).getBoolean();
		if (secretConfig)
		{
			disableCruxite = config.get("Secret", "disableCruxite", false).getBoolean();
			disableUranium = config.get("Secret", "disableUranium", false).getBoolean();
			cruxiteVeinsPerChunk = config.get("Secret", "cruxiteVeinsPerChunk", 10).getInt();
			uraniumVeinsPerChunk = config.get("Secret", "uraniumVeinsPerChunk", 10).getInt();
			baseCruxiteVeinSize = config.get("Secret", "baseCruxiteVeinSize", 6).getInt();
			baseUraniumVeinSize = config.get("Secret", "baseUraniumVeinSize", 3).getInt();
			bonusCruxiteVeinSize = config.get("Secret", "bonusCruxiteVeinSize", 3).getInt();
			bonusUraniumVeinSize = config.get("Secret", "bonusUraniumVeinSize", 3).getInt();
			cruxiteStratumMin = config.get("Secret", "cruxiteStratumMin", 0).getInt();
			uraniumStratumMin = config.get("Secret", "uraniumStratumMin", 0).getInt();
			cruxiteStratumMax = config.get("Secret", "cruxiteStratumMax", 60).getInt();
			uraniumStratumMax = config.get("Secret", "uraniumStratumMax", 30).getInt();
		}

		setting = config.get("General", "dataCheckerPermission", "opsAndGamemode", "Determines who's allowed to access the data checker. \"none\": No one is allowed. \"ops\": only those with a command permission of level 2 or more may access the data ckecker. (for single player, that would be if cheats are turned on) \"gamemode\": Only players with the creative or spectator gamemode may view the data checker. \"opsAndGamemode\": Combination of \"ops\" and \"gamemode\". \"anyone\": No access restrictions are used.",
				new String[]{"none", "ops", "gamemode", "opsAndGamemode", "anyone"}).setLanguageKey("config.minestuck.dataCheckerPermission").getString();
		if (setting.equals("none")) dataCheckerPermission = 0;
		else if (setting.equals("ops")) dataCheckerPermission = 1;
		else if (setting.equals("gamemode")) dataCheckerPermission = 2;
		else if (setting.equals("anyone")) dataCheckerPermission = 4;
		else dataCheckerPermission = 3;

		landAnimalSpawnAmount = config.get("General", "landAnimalSpawnAmount", 10, "The maximum number of Land animals (eg frogs and rabbits) in a spawn cluster.", 4, 32).setLanguageKey("config.minestuck.landAnimalSpawnAmount").getInt();

		IDAlchemy = config.get("General", "IDAlchemy", true, "Enabling this makes the Totem Lathe and the Punch designix use Item IDs to determine a combination result if one doesn't exist already.").setLanguageKey("config.minestuck.general.IDAlchemy").getBoolean();
		zillystoneYields = config.get("General", "zillystoneYields", 0.1, "Determines how much luck affects the amount of Zillystone Shards get dropped by a Zillystone when chiseled.").setLanguageKey("config.minestuck.general.zillystoneYields").getDouble();
		baseZillystoneLuck = config.get("General", "baseZillystoneLuck", -2, "Determines a player's base luck when chiseling a block of Zillystone.").setLanguageKey("config.minestuck.general.baseZillystoneLuck").getInt();
		gristDropsMultiplier = config.get("General", "gristDropsMultiplier", 1, "Determines how much grist is dropped from Underlings.").setLanguageKey("config.minestuck.general.gristDropsMultiplier").getDouble();

		restrictedStrife = config.get("Strife", "restrictedStrife", false, "Prevents players from attacking without an allocated weapon in their main hand. It also restricts the use of certain items such as bows.").setLanguageKey("config.minestuck.strife.restrictedStrife").getBoolean();
		keepPortfolioOnDeath = config.get("Strife", "keepPortfolioOnDeath", false, "Determines whether the player drops their Strife Portfolio after dying or not.").setLanguageKey("config.minestuck.strife.keepPortfolioOnDeath").getBoolean();
		strifeCardMobDrops = config.get("Strife", "strifeCardMobDrops", 5, "Some mobs have a chance at dropping Strife Specibus Cards allocated to whatever item they're holding when killed by a player. This config determines how many cards each player can get from this method at most.").setLanguageKey("config.minestuck.strife.strifeCardMobDrops").getInt();
		strifeDeckMaxSize = config.get("Strife", "strifeDeckMaxSize", 20, "Determines the max amount of weapons that can fit inside a single Strife Deck, set this to -1 to remove the limit.").setMinValue(-1).setLanguageKey("config.minestuck.strife.strifeDeckMaxSize").getInt();
		abstrataSwitcherRung = config.get("Strife", "abstrataSwitcherRung", 17, "Determines the rung needed to unlock the Strife Specibus Quick Switcher. Set it to -1 to let all players use it, or " + Echeladder.RUNG_COUNT + " to completely disable it.").setMinValue(-1).setMaxValue(Echeladder.RUNG_COUNT).setLanguageKey("config.minestuck.strife.abstrataSwitcherRung").getInt();
		weaponAttackMultiplier = config.get("Strife", "weaponAttackMultiplier", 0.15, "Allows players to tweak how much damage Minestuck and Minestuck Universe weapons do as a percentage against entities that aren't Underlings.").setMinValue(0).setMaxValue(1).setLanguageKey("config.minestuck.strife.weaponAttackMultiplier").getDouble();

		localizedChat = config.get("General", "localizedChat", false, "Enabling this makes players only be able to receive chat messages from nearby players unless Gift of Gab is enabled.").setLanguageKey("config.minestuck.general.localizedChat").getBoolean();
		multiAspectUnlocks = config.get("General", "multiAspectUnlocks", true, "Enabling this makes certain badges require multiple kinds of Hero Stone Shards to unlock.").setLanguageKey("config.minestuck.general.multiAspectUnlocks").getBoolean();

		if (gameSide.isClient())    //Client sided config values
		{
			//specialCardRenderer = config.getBoolean("specialCardRenderer", "General", false, "Whenether to use the special render for cards or not.");
			if (specialCardRenderer && !GLContext.getCapabilities().GL_EXT_framebuffer_object)
			{
				specialCardRenderer = false;
				Debug.warn("The FBO extension is not available and is required for the advanced rendering of captchalouge cards.");
			}
			//cardResolution = config.getInt("General", "cardResolution", 1, 0, 5, "The resolution of the item inside of a card. The width/height is computed by '8*2^x', where 'x' is this config value.");
			loginColorSelector = config.get("General", "loginColorSelector", true, "Determines if the color selector should be displayed when entering a save file for the first time.").setLanguageKey("config.minestuck.loginColorSelector").getBoolean();
			alchemyIcons = config.get("General", "alchemyIcons", true, "Set this to true to replace grist names in alchemiter/grist widget with the grist icon.").setLanguageKey("config.minestuck.alchemyIcons").getBoolean();
			setting = config.get("General", "echeladderAnimationNew", "normal", "Allows control of standard speed for the echeladder rung \"animation\", or if it should have one in the first place.", new String[]{"nothing", "slow", "normal", "fast"}).setLanguageKey("config.minestuck.echeladderAnimation").getString();
			if (setting.equals("nothing")) echeladderAnimation = 0;
			else if (setting.equals("slow")) echeladderAnimation = 4;
			else if (setting.equals("fast")) echeladderAnimation = 1;
			else echeladderAnimation = 2;
		}
	}

	public static boolean getDataCheckerPermissionFor(EntityPlayerMP player)
	{
		if ((dataCheckerPermission & 3) != 0)
		{
			if ((dataCheckerPermission & 1) != 0)
			{
				MinecraftServer server = player.getServer();
				if (server.getPlayerList().canSendCommands(player.getGameProfile()))
				{
					UserListOpsEntry userlistopsentry = server.getPlayerList().getOppedPlayers().getEntry(player.getGameProfile());
					if ((userlistopsentry != null ? userlistopsentry.getPermissionLevel() : server.getOpPermissionLevel()) >= 2)
						return true;
				}
			}
			if ((dataCheckerPermission & 2) != 0)
			{
				GameType gameType = player.interactionManager.getGameType();
				if (ServerEditHandler.getData(player) != null)
					gameType = ServerEditHandler.getData(player).getDecoy().gameType;
				return !gameType.isSurvivalOrAdventure();
			}
			return false;
		}
		else return dataCheckerPermission != 0;
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event)
	{
		if (event.getModID().equals(Minestuck.class.getAnnotation(Mod.class).modid()))
		{
			loadBasicConfigOptions(event.isWorldRunning());

			config.save();

		}
	}

}
