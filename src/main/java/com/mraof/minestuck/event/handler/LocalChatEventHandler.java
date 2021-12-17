package com.mraof.minestuck.event.handler;

import com.mraof.minestuck.MinestuckConfig;
import com.mraof.minestuck.badges.MinestuckBadges;
import com.mraof.minestuck.capabilities.MinestuckCapabilities;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.ChatType;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
	@SideOnly(Side.CLIENT)
	public static void onChatReceived(ClientChatReceivedEvent event)
	{
		if(MinestuckConfig.localizedChat && Minecraft.getMinecraft().player != null)
		{
			EntityPlayer player = Minecraft.getMinecraft().player;
			if(event.getType() == ChatType.SYSTEM && player.getCapability(MinestuckCapabilities.GOD_TIER_DATA, null) != null &&
					!(player.getCapability(MinestuckCapabilities.GOD_TIER_DATA, null).isBadgeActive(MinestuckBadges.GIFT_OF_GAB) || player.isCreative()))
			{
				event.setCanceled(true);
			}
		}
	}
}
