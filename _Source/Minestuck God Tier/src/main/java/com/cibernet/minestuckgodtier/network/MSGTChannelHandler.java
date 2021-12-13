package com.cibernet.minestuckgodtier.network;

import com.cibernet.minestuckgodtier.network.MSGTPacket.Type;
import com.mraof.minestuck.client.ClientProxy;
import com.mraof.minestuck.util.Debug;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.INetHandler;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.FMLEmbeddedChannel;
import net.minecraftforge.fml.common.network.FMLIndexedMessageToMessageCodec;
import net.minecraftforge.fml.common.network.FMLOutboundHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;

import java.util.EnumMap;

public class MSGTChannelHandler extends FMLIndexedMessageToMessageCodec<MSGTPacket>
{

    public static MSGTChannelHandler instance = new MSGTChannelHandler();
    public static EnumMap<Side, FMLEmbeddedChannel> channels;
    public MSGTChannelHandler()
    {
        for(Type type : Type.values())
        addDiscriminator(type.ordinal(), type.packetType);
    }

    @Override
    public void encodeInto(ChannelHandlerContext ctx, MSGTPacket msg, ByteBuf target)
    {
        target.writeBytes(msg.data.copy());
        Debug.debug("Sending packet "+msg.toString()+" with size "+msg.data.writerIndex());
    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf source, MSGTPacket msg)
    {
        Debug.debug("Received packet "+msg.toString()+" with size "+source.readableBytes());
        msg.consumePacket(source);
    }

    private static class MinestuckPacketHandler extends SimpleChannelInboundHandler<MSGTPacket>
    {
        private final Side side;
        private MinestuckPacketHandler(Side side)
        {
            this.side = side;
        }
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, MSGTPacket msg) throws Exception
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
            channels = NetworkRegistry.INSTANCE.newChannel("MinestuckGodTier", MSGTChannelHandler.instance);
            String targetName = channels.get(Side.CLIENT).findChannelHandlerNameForType(MSGTChannelHandler.class);
            channels.get(Side.CLIENT).pipeline().addAfter(targetName, "MSGTPacketHandler", new MinestuckPacketHandler(Side.CLIENT));
            targetName = channels.get(Side.SERVER).findChannelHandlerNameForType(MSGTChannelHandler.class);	//Not sure if this is necessary
            channels.get(Side.SERVER).pipeline().addAfter(targetName, "MSGTPacketHandler", new MinestuckPacketHandler(Side.SERVER));
        }
    }

    public static void sendToServer(MSGTPacket packet)
    {
        channels.get(Side.CLIENT).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.TOSERVER);
        channels.get(Side.CLIENT).writeOutbound(packet);
    }

    public static void sendToPlayer(MSGTPacket packet, EntityPlayer player)
    {
        channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.PLAYER);
        channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(player);
        channels.get(Side.SERVER).writeOutbound(packet);
    }

    public static void sendToDimension(MSGTPacket packet, int dimId)
    {
        channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.DIMENSION);
        channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(dimId);
        channels.get(Side.SERVER).writeOutbound(packet);
    }

    public static void sendToAllPlayers(MSGTPacket packet)
    {
        channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALL);
        channels.get(Side.SERVER).writeOutbound(packet);
    }

    public static void sendToTracking(MSGTPacket packet, Entity trackedEntity)
    {
        for (EntityPlayer trackingPlayer : ((WorldServer) trackedEntity.world).getEntityTracker().getTrackingPlayers(trackedEntity))
            sendToPlayer(packet, trackingPlayer);
    }

    public static void sendToTrackingAndSelf(MSGTPacket packet, Entity trackedEntity)
    {
        sendToTracking(packet, trackedEntity);
        if (trackedEntity instanceof EntityPlayer)
            sendToPlayer(packet, (EntityPlayer) trackedEntity);
    }
}
