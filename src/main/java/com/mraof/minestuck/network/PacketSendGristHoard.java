package com.mraof.minestuck.network;

import com.mraof.minestuck.alchemy.Grist;
import com.mraof.minestuck.capabilities.MinestuckCapabilities;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;

import java.util.EnumSet;

public class PacketSendGristHoard extends MinestuckPacket
{
    Grist type;

    @Override
    public MinestuckPacket generatePacket(Object... args)
    {
        ByteBufUtils.writeUTF8String(data, ((Grist)args[0]).getRegistryName().toString());
        return this;
    }

    @Override
    public MinestuckPacket consumePacket(ByteBuf data)
    {
        type = Grist.getTypeFromString(ByteBufUtils.readUTF8String(data));
        return this;
    }

    @Override
    public void execute(EntityPlayer player)
    {
        player.getCapability(MinestuckCapabilities.GOD_TIER_DATA, null).setGristHoard(type);
    }

    @Override
    public EnumSet<Side> getSenderSide()
    {
        return EnumSet.of(Side.CLIENT);
    }
}
