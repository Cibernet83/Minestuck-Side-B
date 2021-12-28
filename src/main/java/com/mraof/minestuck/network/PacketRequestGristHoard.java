package com.mraof.minestuck.network;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.client.gui.MinestuckGuiHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;

import java.util.EnumSet;

public class PacketRequestGristHoard extends MinestuckPacket
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
        player.openGui(Minestuck.instance, MinestuckGuiHandler.GuiId.HOARD_SELECTOR.ordinal(), player.world, player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ());
    }

    @Override
    public EnumSet<Side> getSenderSide()
    {
        return EnumSet.of(Side.SERVER);
    }
}
