package com.mraof.minestuck.network.message;

import com.mraof.minestuck.alchemy.Grist;
import com.mraof.minestuck.capabilities.MinestuckCapabilities;
import com.mraof.minestuck.network.MinestuckMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;

import java.util.EnumSet;

public class MessageSendGristHoard extends MinestuckMessage
{
    Grist type;

    @Override
    public void generatePacket(Object... args)
    {
        ByteBufUtils.writeUTF8String(data, ((Grist)args[0]).getRegistryName().toString());

    }

    @Override
    public void consumePacket(ByteBuf data)
    {
        type = Grist.getTypeFromString(ByteBufUtils.readUTF8String(data));

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
