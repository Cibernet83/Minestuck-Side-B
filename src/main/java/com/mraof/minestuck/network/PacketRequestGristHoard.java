package com.mraof.minestuck.network;

import com.mraof.minestuck.Minestuck;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;

import java.util.EnumSet;

import static com.mraof.minestuck.client.gui.MSGTGuiHandler.HOARD_SELECTOR;

public class PacketRequestGristHoard extends MinestuckPacket
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
        player.openGui(Minestuck.instance, HOARD_SELECTOR, player.world, player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ());
    }

    @Override
    public EnumSet<Side> getSenderSide()
    {
        return EnumSet.of(Side.SERVER);
    }
}
