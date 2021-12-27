package com.mraof.minestuck.network;

import com.mraof.minestuck.util.MinestuckUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;

import java.util.EnumSet;

public class PacketStopBuildInhibitEffect extends MinestuckPacket
{

    @Override
    public void generatePacket(Object... args)
    {

    }

    @Override
    public void consumePacket(ByteBuf data)
    {

    }

    @Override
    public void execute(EntityPlayer player)
    {
        if(!player.isCreative())
            player.capabilities.allowEdit = !MinestuckUtils.getPlayerGameType(player).hasLimitedInteractions();
    }

    @Override
    public EnumSet<Side> getSenderSide()
    {
        return EnumSet.of(Side.SERVER);
    }
}
