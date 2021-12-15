package com.mraof.minestuck.network;

import com.mraof.minestuck.util.MSUUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;

import java.util.EnumSet;

public class PacketStopBuildInhibitEffect extends MinestuckPacket
{

    @Override
    public MinestuckPacket generatePacket(Object... args)
    {
        return this;
    }

    @Override
    public MinestuckPacket consumePacket(ByteBuf data)
    {
        return this;
    }

    @Override
    public void execute(EntityPlayer player)
    {
        if(!player.isCreative())
            player.capabilities.allowEdit = !MSUUtils.getPlayerGameType(player).hasLimitedInteractions();
    }

    @Override
    public EnumSet<Side> getSenderSide()
    {
        return EnumSet.of(Side.SERVER);
    }
}
