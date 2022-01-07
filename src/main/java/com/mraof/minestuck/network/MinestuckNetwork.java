package com.mraof.minestuck.network;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.client.ClientProxy;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraftforge.fml.common.network.FMLEmbeddedChannel;
import net.minecraftforge.fml.common.network.FMLOutboundHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.handshake.NetworkDispatcher;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleIndexedCodec;
import net.minecraftforge.fml.relauncher.Side;

import java.io.IOException;
import java.util.EnumMap;

public class MinestuckNetwork
{
	private static EnumMap<Side, FMLEmbeddedChannel> channels;
	private static SimpleIndexedCodec packetCodec = new SimpleIndexedCodec();

	public static void setupChannel()
	{
		channels = NetworkRegistry.INSTANCE.newChannel(Minestuck.MODID, packetCodec);
		addHandler(Side.SERVER);
		addHandler(Side.CLIENT);

		ImmutableSet<ClassPath.ClassInfo> messageTypes;
		try
		{
			messageTypes = ClassPath.from(MinestuckNetwork.class.getClassLoader()).getTopLevelClassesRecursive("com.mraof.minestuck.network.message");
		}
		catch (IOException e)
		{
			throw new RuntimeException("Error retrieving message types", e);
		}
		int id = 0;
		for (ClassPath.ClassInfo classInfo : messageTypes)
			if (classInfo.getSimpleName().startsWith("Message"))
			{
				Class<? extends MinestuckMessage> clazz = (Class<? extends MinestuckMessage>) classInfo.load();
				try
				{
					clazz.getDeclaredConstructor();
				}
				catch (NoSuchMethodException e)
				{
					throw new RuntimeException("All network messages are required to have a default constructor but " + clazz + " does not.", e);
				}
				catch (Throwable e)
				{
					throw new RuntimeException("Caught exception loading message " + clazz, e);
				}
				packetCodec.addDiscriminator(id++, clazz);
			}
	}

	private static void addHandler(Side side)
	{
		String targetName = channels.get(side).findChannelHandlerNameForType(SimpleIndexedCodec.class);
		channels.get(side).pipeline().addAfter(targetName, "MinestuckMessageHandler", new MinestuckMessageHandler(side));
	}

	public static void sendToAll(MinestuckMessage message)
	{
		checkSide(message, Side.CLIENT);
		channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALL);
		channels.get(Side.SERVER).writeOutbound(message);
	}

	private static void checkSide(MinestuckMessage message, Side side)
	{
		if (message.toSide() != side)
			throw new RuntimeException("Message " + message + " intended for the " + message.toSide() + " tried to be sent to the " + side);
	}

	public static void sendToTrackingAndSelf(MinestuckMessage message, EntityPlayer player)
	{
		sendToTracking(message, player);
		sendTo(message, player);
	}

	public static void sendTo(MinestuckMessage message, EntityPlayer player)
	{
		checkSide(message, Side.CLIENT);
		channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.PLAYER);
		channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(player);
		channels.get(Side.SERVER).writeOutbound(message);
	}

	public static void sendToTracking(MinestuckMessage message, Entity entity)
	{
		checkSide(message, Side.CLIENT);
		channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.TRACKING_ENTITY);
		channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(entity);
		channels.get(Side.SERVER).writeOutbound(message);
	}

	public static void sendToDimension(MinestuckMessage message, int dimensionId)
	{
		checkSide(message, Side.CLIENT);
		channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.DIMENSION);
		channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(dimensionId);
		channels.get(Side.SERVER).writeOutbound(message);
	}

	public static void sendToDispatcher(MinestuckMessage message, NetworkDispatcher dispatcher)
	{
		checkSide(message, Side.CLIENT);
		MinestuckNetwork.channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.DISPATCHER);
		MinestuckNetwork.channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(dispatcher);
		MinestuckNetwork.channels.get(Side.SERVER).writeOutbound(message);
	}

	public static void sendToServer(MinestuckMessage message)
	{
		checkSide(message, Side.SERVER);
		channels.get(Side.CLIENT).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.TOSERVER);
		channels.get(Side.CLIENT).writeOutbound(message);
	}

	public void sendToAllAround(MinestuckMessage message, NetworkRegistry.TargetPoint point)
	{
		checkSide(message, Side.CLIENT);
		channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALLAROUNDPOINT);
		channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(point);
		channels.get(Side.SERVER).writeOutbound(message);
	}

	public void sendToAllTracking(MinestuckMessage message, NetworkRegistry.TargetPoint point)
	{
		checkSide(message, Side.CLIENT);
		channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.TRACKING_POINT);
		channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(point);
		channels.get(Side.SERVER).writeOutbound(message);
	}

	private static class MinestuckMessageHandler extends SimpleChannelInboundHandler<MinestuckMessage>
	{
		private final Side side;

		private MinestuckMessageHandler(Side side)
		{
			this.side = side;
		}

		@Override
		protected void channelRead0(ChannelHandlerContext ctx, MinestuckMessage msg)
		{
			switch (side)
			{
				case CLIENT:
					ClientProxy.addScheduledTask(() -> msg.execute(ClientProxy.getClientPlayerEntity()));
					break;
				case SERVER:
					NetHandlerPlayServer netHandler = (NetHandlerPlayServer) ctx.channel().attr(NetworkRegistry.NET_HANDLER).get();
					netHandler.player.getServerWorld().addScheduledTask(() -> msg.execute(netHandler.player));
					break;
			}
		}
	}
}
