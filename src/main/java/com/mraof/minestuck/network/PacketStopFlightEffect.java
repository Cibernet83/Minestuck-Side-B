package com.mraof.minestuck.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;

import java.util.EnumSet;

public class PacketStopFlightEffect extends MinestuckPacket
{
    boolean isBadEffect;

    @Override
    public MinestuckPacket generatePacket(Object... args)
    {
        data.writeBoolean((Boolean) args[0]);
        return this;
    }

    @Override
    public MinestuckPacket consumePacket(ByteBuf data)
    {
        isBadEffect = data.readBoolean();
        return this;
    }

    @Override
    public void execute(EntityPlayer player)
    {
        if(isBadEffect && player.isCreative())
            player.capabilities.allowFlying = true;
        if(!isBadEffect && !player.isCreative())
        {
            player.capabilities.allowFlying = false;
            player.capabilities.isFlying = false;
        }
    }

    @Override
    public EnumSet<Side> getSenderSide()
    {
        return EnumSet.of(Side.SERVER);
    }
}