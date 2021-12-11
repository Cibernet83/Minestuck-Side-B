package com.cibernet.minestuckgodtier.events.handlers;

import com.cibernet.minestuckgodtier.MSGTConfig;
import com.cibernet.minestuckgodtier.badges.MSGTBadges;
import com.cibernet.minestuckgodtier.capabilities.MSGTCapabilities;
import com.cibernet.minestuckgodtier.network.MSGTChannelHandler;
import com.cibernet.minestuckgodtier.network.MSGTPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class LocalChatEventHandler
{
	@SubscribeEvent
	public static void onChat(ServerChatEvent event)
	{
		if(!MSGTConfig.localizedChat)
			return;

		World world = event.getPlayer().world;
		EntityPlayer player = world.getPlayerEntityByUUID(event.getPlayer().getCommandSenderEntity().getUniqueID());
		boolean canSenderGab = player.getCapability(MSGTCapabilities.GOD_TIER_DATA, null) != null && player.getCapability(MSGTCapabilities.GOD_TIER_DATA, null).isBadgeActive(MSGTBadges.GIFT_OF_GAB);

		for(EntityPlayer receiver : world.getMinecraftServer().getPlayerList().getPlayers())
		{
			boolean canReceiverGab = receiver.getCapability(MSGTCapabilities.GOD_TIER_DATA, null) != null && receiver.getCapability(MSGTCapabilities.GOD_TIER_DATA, null).isBadgeActive(MSGTBadges.GIFT_OF_GAB);

			if(receiver == player || (canSenderGab && canReceiverGab) || (!canSenderGab && player.getDistanceSq(receiver) < 128 && player.world.provider.getDimension() == receiver.world.provider.getDimension()))
				receiver.sendStatusMessage(event.getComponent(), false);
		}

		event.setCanceled(true);
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public static void onChatReceived(ClientChatReceivedEvent event)
	{
		if(MSGTConfig.localizedChat && Minecraft.getMinecraft().player != null)
		{
			EntityPlayer player = Minecraft.getMinecraft().player;
			if(event.getType() == ChatType.SYSTEM && player.getCapability(MSGTCapabilities.GOD_TIER_DATA, null) != null &&
					!(player.getCapability(MSGTCapabilities.GOD_TIER_DATA, null).isBadgeActive(MSGTBadges.GIFT_OF_GAB) || player.isCreative()))
			{
				event.setCanceled(true);
			}
		}
	}

	@SubscribeEvent
	public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event)
	{
		MSGTChannelHandler.sendToPlayer(MSGTPacket.makePacket(MSGTPacket.Type.UPDATE_CONFIG), event.player);
	}
}
