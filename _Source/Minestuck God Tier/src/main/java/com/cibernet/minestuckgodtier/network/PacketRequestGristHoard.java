package com.cibernet.minestuckgodtier.network;

import com.cibernet.minestuckgodtier.MinestuckGodTier;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;

import java.util.EnumSet;

import static com.cibernet.minestuckgodtier.client.gui.MSGTGuiHandler.HOARD_SELECTOR;

public class PacketRequestGristHoard extends MSGTPacket
{
    @Override
    public MSGTPacket generatePacket(Object... args)
    {
        return this;
    }

    @Override
    public MSGTPacket consumePacket(ByteBuf data)
    {
        return this;
    }

    @Override
    public void execute(EntityPlayer player)
    {
        player.openGui(MinestuckGodTier.instance, HOARD_SELECTOR, player.world, player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ());
    }

    @Override
    public EnumSet<Side> getSenderSide()
    {
        return EnumSet.of(Side.SERVER);
    }
}
