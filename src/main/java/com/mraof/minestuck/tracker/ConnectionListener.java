package com.mraof.minestuck.tracker;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.network.MinestuckNetwork;
import com.mraof.minestuck.network.MinestuckMessage;
import com.mraof.minestuck.network.MinestuckMessage.Type;
import com.mraof.minestuck.util.Debug;
import com.mraof.minestuck.world.MinestuckDimensionHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraftforge.fml.common.network.FMLOutboundHandler;
import net.minecraftforge.fml.common.network.handshake.NetworkDispatcher;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(modid = Minestuck.MODID)
public class ConnectionListener
{
	
	@SubscribeEvent
	public static void onServerConnectionCreated(FMLNetworkEvent.ServerConnectionFromClientEvent event)
	{
		MinestuckMessage packet = MinestuckMessage.makePacket(Type.LANDREGISTER);
		
		MinestuckNetwork.channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.DISPATCHER);
		MinestuckNetwork.channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(event.getManager().channel().attr(NetworkDispatcher.FML_DISPATCHER).get());
		MinestuckNetwork.channels.get(Side.SERVER).writeOutbound(packet);
	}
	
	@SubscribeEvent
	public static void onClientConnectionClosed(FMLNetworkEvent.ClientDisconnectionFromServerEvent event)
	{
		Debug.debug("Disconnecting from server. Unregistering land dimensions...");
		if(!Minestuck.isServerRunning)
			MinestuckDimensionHandler.unregisterDimensions();
	}
	
}
