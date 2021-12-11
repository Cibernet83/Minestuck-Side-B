package com.cibernet.fetchmodiplus.network;

import com.mraof.minestuck.client.ClientProxy;
import com.mraof.minestuck.util.Debug;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.INetHandler;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraftforge.fml.common.network.FMLEmbeddedChannel;
import net.minecraftforge.fml.common.network.FMLIndexedMessageToMessageCodec;
import net.minecraftforge.fml.common.network.FMLOutboundHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;

import java.util.EnumMap;

public class FMPChannelHandler extends FMLIndexedMessageToMessageCodec<FMPPacket>
{
	
	public static FMPChannelHandler instance = new FMPChannelHandler();
	public static EnumMap<Side, FMLEmbeddedChannel> channels;
	public FMPChannelHandler() {
		for(FMPPacket.Type type : FMPPacket.Type.values())
			addDiscriminator(type.ordinal(), type.packetType);
	}
	
	@Override
	public void encodeInto(ChannelHandlerContext ctx, FMPPacket msg, ByteBuf target) throws Exception
	{
		target.writeBytes(msg.data);
		Debug.debug("Sending packet "+msg.toString()+" with size "+msg.data.writerIndex());
	}
	
	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf source, FMPPacket msg)
	{
		Debug.debug("Received packet "+msg.toString()+" with size "+source.readableBytes());
		msg.consumePacket(source);
	}
	
	private static class FMPPacketHandler extends SimpleChannelInboundHandler<FMPPacket>
	{
		private final Side side;
		private FMPPacketHandler(Side side)
		{
			this.side = side;
		}
		@Override
		protected void channelRead0(ChannelHandlerContext ctx, FMPPacket msg) throws Exception
		{
			switch (side)
			{
				case CLIENT:
					
					ClientProxy.addScheduledTask(() -> msg.execute(ClientProxy.getClientPlayer()));
					
					break;
				case SERVER:
					INetHandler netHandler = ctx.channel().attr(NetworkRegistry.NET_HANDLER).get();
					EntityPlayerMP player = ((NetHandlerPlayServer) netHandler).player;
					player.getServerWorld().addScheduledTask(() -> msg.execute(player));
					break;
			}
		}
	}
	
	public static void setupChannel()
	{
		if(channels == null)
		{
			channels = NetworkRegistry.INSTANCE.newChannel("FetchModiPlus", FMPChannelHandler.instance);
			String targetName = channels.get(Side.CLIENT).findChannelHandlerNameForType(FMPChannelHandler.class);
			channels.get(Side.CLIENT).pipeline().addAfter(targetName, "FMPChannelHandler", new FMPChannelHandler.FMPPacketHandler(Side.CLIENT));
			targetName = channels.get(Side.SERVER).findChannelHandlerNameForType(FMPChannelHandler.class);	//Not sure if this is necessary
			channels.get(Side.SERVER).pipeline().addAfter(targetName, "FMPChannelHandler", new FMPChannelHandler.FMPPacketHandler(Side.SERVER));
		}
	}
	
	public static void sendToServer(FMPPacket packet)
	{
		channels.get(Side.CLIENT).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.TOSERVER);
		channels.get(Side.CLIENT).writeOutbound(packet);
	}
	
	public static void sendToPlayer(FMPPacket packet, EntityPlayer player)
	{
		channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.PLAYER);
		channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(player);
		channels.get(Side.SERVER).writeOutbound(packet);
	}
	
	public static void sendToAllPlayers(FMPPacket packet)
	{
		channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALL);
		channels.get(Side.SERVER).writeOutbound(packet);
	}
}
