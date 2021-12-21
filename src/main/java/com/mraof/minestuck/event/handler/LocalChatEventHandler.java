package com.mraof.minestuck.event.handler;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.MinestuckConfig;
import com.mraof.minestuck.badges.MinestuckBadges;
import com.mraof.minestuck.capabilities.MinestuckCapabilities;
import net.minecraft.command.CommandBase;
import net.minecraft.command.server.CommandEmote;
import net.minecraft.command.server.CommandMessage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Minestuck.MODID)
public class LocalChatEventHandler
{
	@SubscribeEvent
	public static void onChat(ServerChatEvent event)
	{
		if(!MinestuckConfig.localizedChat)
			return;

		World world = event.getPlayer().world;
		EntityPlayer player = world.getPlayerEntityByUUID(event.getPlayer().getCommandSenderEntity().getUniqueID());
		boolean canSenderGab = player.getCapability(MinestuckCapabilities.GOD_TIER_DATA, null) != null && player.getCapability(MinestuckCapabilities.GOD_TIER_DATA, null).isBadgeActive(MinestuckBadges.GIFT_OF_GAB);

		for(EntityPlayer receiver : world.getMinecraftServer().getPlayerList().getPlayers())
		{
			boolean canReceiverGab = receiver.getCapability(MinestuckCapabilities.GOD_TIER_DATA, null) != null && receiver.getCapability(MinestuckCapabilities.GOD_TIER_DATA, null).isBadgeActive(MinestuckBadges.GIFT_OF_GAB);

			if(receiver == player || (canSenderGab && canReceiverGab) || (!canSenderGab && player.getDistanceSq(receiver) < 128 && player.world.provider.getDimension() == receiver.world.provider.getDimension()))
				receiver.sendStatusMessage(event.getComponent(), false);
		}

		event.setCanceled(true);
	}


	@SubscribeEvent
	public static void onCommandSent(CommandEvent event)
	{
		if(!MinestuckConfig.localizedChat)
			return;

		World world = event.getSender().getEntityWorld();
		EntityPlayer player = world.getPlayerEntityByUUID(event.getSender().getCommandSenderEntity().getUniqueID());
		boolean canSenderGab = player.getCapability(MinestuckCapabilities.GOD_TIER_DATA, null) != null && player.getCapability(MinestuckCapabilities.GOD_TIER_DATA, null).isBadgeActive(MinestuckBadges.GIFT_OF_GAB);

		if(!canSenderGab && event.getCommand() instanceof CommandEmote)
		{
			TextComponentTranslation msg = new TextComponentTranslation("commands.generic.permission");
			msg.getStyle().setColor(TextFormatting.RED);
			event.getSender().sendMessage(msg);
			event.setCanceled(true);
		}
		else if(event.getCommand() instanceof CommandMessage)
		{
			EntityPlayer receiver;

			try { receiver = CommandBase.getPlayer(event.getSender().getServer(), event.getSender(), event.getParameters()[0]); }
			catch (Throwable throwable) { return; }


			boolean canReceiverGab = receiver.getCapability(MinestuckCapabilities.GOD_TIER_DATA, null) != null && receiver.getCapability(MinestuckCapabilities.GOD_TIER_DATA, null).isBadgeActive(MinestuckBadges.GIFT_OF_GAB);

			if(!(receiver == player || (canSenderGab && canReceiverGab) || (!canSenderGab && player.getDistanceSq(receiver) < 128 && player.world.provider.getDimension() == receiver.world.provider.getDimension())))
			{
				TextComponentTranslation msg = new TextComponentTranslation("commands.localizedChat.outOfReach", player.getDisplayName());
				msg.getStyle().setColor(TextFormatting.RED);
				event.getSender().sendMessage(msg);
				event.setCanceled(true);
			}
		}
	}
}
