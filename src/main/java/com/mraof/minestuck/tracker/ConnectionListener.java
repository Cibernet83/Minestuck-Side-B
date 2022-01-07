package com.mraof.minestuck.tracker;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.network.MinestuckNetwork;
import com.mraof.minestuck.network.message.MessageLandRegister;
import com.mraof.minestuck.util.Debug;
import com.mraof.minestuck.world.MinestuckDimensionHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraftforge.fml.common.network.handshake.NetworkDispatcher;

@Mod.EventBusSubscriber(modid = Minestuck.MODID)
public class ConnectionListener
{

	@SubscribeEvent
	public static void onServerConnectionCreated(FMLNetworkEvent.ServerConnectionFromClientEvent event)
	{
		if (!event.isLocal())
			MinestuckNetwork.sendToDispatcher(new MessageLandRegister(), event.getManager().channel().attr(NetworkDispatcher.FML_DISPATCHER).get());
	}

	@SubscribeEvent
	public static void onClientConnectionClosed(FMLNetworkEvent.ClientDisconnectionFromServerEvent event)
	{
		Debug.debug("Disconnecting from server. Unregistering land dimensions...");
		if (!Minestuck.isServerRunning)
			MinestuckDimensionHandler.unregisterDimensions();
	}
}
