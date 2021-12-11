package com.cibernet.minestuckgodtier.network;

import com.cibernet.minestuckgodtier.capabilities.MSGTCapabilities;
import com.mraof.minestuck.alchemy.GristType;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;

import java.util.EnumSet;

public class PacketSendGristHoard extends MSGTPacket
{
    GristType type;

    @Override
    public MSGTPacket generatePacket(Object... args)
    {
        ByteBufUtils.writeUTF8String(data, ((GristType)args[0]).getRegistryName().toString());
        return this;
    }

    @Override
    public MSGTPacket consumePacket(ByteBuf data)
    {
        type = GristType.getTypeFromString(ByteBufUtils.readUTF8String(data));
        return this;
    }

    @Override
    public void execute(EntityPlayer player)
    {
        player.getCapability(MSGTCapabilities.GOD_TIER_DATA, null).setGristHoard(type);
    }

    @Override
    public EnumSet<Side> getSenderSide()
    {
        return EnumSet.of(Side.CLIENT);
    }
}
