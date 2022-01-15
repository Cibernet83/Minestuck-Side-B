package com.mraof.minestuck.tracker;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.MinestuckConfig;
import com.mraof.minestuck.alchemy.GristSet;
import com.mraof.minestuck.alchemy.MinestuckGrist;
import com.mraof.minestuck.editmode.ServerEditHandler;
import com.mraof.minestuck.network.MinestuckMessage;
import com.mraof.minestuck.network.MinestuckNetwork;
import com.mraof.minestuck.network.message.*;
import com.mraof.minestuck.network.skaianet.SburbConnection;
import com.mraof.minestuck.network.skaianet.SkaianetHandler;
import com.mraof.minestuck.util.*;
import com.mraof.minestuck.util.IdentifierHandler.PlayerIdentifier;
import com.mraof.minestuck.world.MinestuckDimensionHandler;
import com.mraof.minestuck.world.lands.LandAspectRegistry;
import com.mraof.minestuck.world.lands.gen.ChunkProviderLands;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.HashSet;
import java.util.Set;

@Mod.EventBusSubscriber(modid = Minestuck.MODID)
public class MinestuckPlayerTracker
{
	public static Set<String> dataCheckerPermission = new HashSet<String>();

	@SubscribeEvent
	public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event)
	{
		EntityPlayerMP player = (EntityPlayerMP) event.player;
		Debug.debug(player.getName() + " joined the game. Sending packets.");
		MinecraftServer server = player.getServer();
		if (!server.isDedicatedServer() && IdentifierHandler.host == null)
			IdentifierHandler.host = event.player.getName();

		IdentifierHandler.playerLoggedIn(player);
		PlayerIdentifier identifier = IdentifierHandler.encode(player);

		sendConfigPacket(player, true);
		sendConfigPacket(player, false);

		SkaianetHandler.playerConnected(player);
		boolean firstTime = false;
		if (MinestuckPlayerData.getGristSet(identifier) == null)
		{
			Debug.debugf("Grist set is null for player %s. Handling it as first time in this world.", player.getName());
			MinestuckPlayerData.setGrist(identifier, new GristSet(MinestuckGrist.build, 20));
			firstTime = true;
		}

		MinestuckPlayerData.getData(identifier).echeladder.updateEcheladderBonuses(player);

		updateGristCache(identifier);
		updateTitle(player);
		updateEcheladder(player, true);
		MinestuckNetwork.sendTo(new MessageBoondollars(MinestuckPlayerData.getData(identifier).boondollars), player);
		ServerEditHandler.onPlayerLoggedIn(player);

		if (firstTime && !player.isSpectator())
			MinestuckNetwork.sendTo(new MessagePlayerColor(), player);
		else
			MinestuckNetwork.sendTo(new MessagePlayerColor(MinestuckPlayerData.getData(player).color), player);

		if (UpdateChecker.outOfDate)
			player.sendMessage(new TextComponentString("New version of minestuck: " + UpdateChecker.latestVersion + "\nChanges: " + UpdateChecker.updateChanges));
	}

	/**
	 * Uses an "encoded" username as parameter.
	 */
	public static void updateGristCache(PlayerIdentifier player)
	{
		GristSet gristSet = MinestuckPlayerData.getGristSet(player);

		//The player
		EntityPlayerMP playerMP = player.getPlayer();
		if (playerMP != null)
			MinestuckNetwork.sendTo(new MessageGristCache(gristSet, false), playerMP);

		//The editing player, if there is any.
		SburbConnection c = SkaianetHandler.getClientConnection(player);
		if (c != null && ServerEditHandler.getData(c) != null)
		{
			EntityPlayerMP editor = ServerEditHandler.getData(c).getEditor();
			MinestuckNetwork.sendTo(new MessageGristCache(gristSet, true), editor);
		}
	}

	public static void updateTitle(EntityPlayer player)
	{
		PlayerIdentifier identifier = IdentifierHandler.encode(player);
		Title newTitle = MinestuckPlayerData.getTitle(identifier);
		if (newTitle == null)
			return;
		MinestuckNetwork.sendTo(new MessageTitle(newTitle), player);
	}

	public static void updateEcheladder(EntityPlayer player, boolean jump)
	{
		Echeladder echeladder = MinestuckPlayerData.getData(player).echeladder;
		MinestuckNetwork.sendTo(new MessageEcheladder(echeladder.getRung(), MinestuckConfig.echeladderProgress ? echeladder.getProgress() : 0F, jump), player);
	}

	public static void sendConfigPacket(EntityPlayerMP player, boolean mode)
	{
		MinestuckMessage packet;
		if (mode)
			MinestuckNetwork.sendTo(new MessageConfigInitial(), player);
		else
		{
			boolean permission = MinestuckConfig.getDataCheckerPermissionFor(player);
			MinestuckNetwork.sendTo(new MessageConfig(permission), player);
			if (permission)
				dataCheckerPermission.add(player.getName());
			else
				dataCheckerPermission.remove(player.getName());
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGH)    //Editmode players need to be reset before nei handles the event
	public static void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event)
	{
		ServerEditHandler.onPlayerExit(event.player);
		dataCheckerPermission.remove(event.player.getName());
	}

	@SubscribeEvent(priority = EventPriority.LOWEST, receiveCanceled = false)
	public static void onPlayerDrops(PlayerDropsEvent event)
	{
		if (!event.getEntityPlayer().world.isRemote)
		{
			SylladexUtils.dropSylladexOnDeath(event.getEntityPlayer());
		}
	}

	@SubscribeEvent
	public static void onPlayerTick(TickEvent.PlayerTickEvent event)
	{
		if (event.side.isServer() && event.phase == TickEvent.Phase.END && event.player instanceof EntityPlayerMP)
		{
			EntityPlayerMP player = (EntityPlayerMP) event.player;
			if (shouldUpdateConfigurations(player))
				sendConfigPacket(player, false);
		}
	}

	private static boolean shouldUpdateConfigurations(EntityPlayerMP player)
	{
		//TODO check for changed configs and change setRequiresWorldRestart status for those config options
		boolean permission = MinestuckConfig.getDataCheckerPermissionFor(player);
		return permission != dataCheckerPermission.contains(player.getName());

	}

	@SubscribeEvent
	public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event)
	{
		MinestuckPlayerData.getData(event.player).echeladder.updateEcheladderBonuses(event.player);
	}

	public static void updateLands()
	{
		updateLands(null);
	}

	public static void updateLands(EntityPlayer player)
	{
		Debug.debugf("Sending land packets to %s.", player == null ? "all players" : player.getName());
		if (player == null)
			MinestuckNetwork.sendToAll(new MessageLandRegister());
		else
			MinestuckNetwork.sendTo(new MessageLandRegister(), player);
	}

	public static void sendLandEntryMessage(EntityPlayer player)
	{
		if (MinestuckDimensionHandler.isLandDimension(player.dimension))
		{
			LandAspectRegistry.AspectCombination aspects = MinestuckDimensionHandler.getAspects(player.dimension);
			ChunkProviderLands chunkProvider = (ChunkProviderLands) player.world.provider.createChunkGenerator();
			ITextComponent aspect1 = new TextComponentTranslation("land." + aspects.aspectTerrain.getNames()[chunkProvider.nameIndex1]);
			ITextComponent aspect2 = new TextComponentTranslation("land." + aspects.aspectTitle.getNames()[chunkProvider.nameIndex2]);
			ITextComponent toSend;
			if (chunkProvider.nameOrder)
				toSend = new TextComponentTranslation("land.message.entry", aspect1, aspect2);
			else toSend = new TextComponentTranslation("land.message.entry", aspect2, aspect1);
			player.sendMessage(toSend);
		}
	}

}
