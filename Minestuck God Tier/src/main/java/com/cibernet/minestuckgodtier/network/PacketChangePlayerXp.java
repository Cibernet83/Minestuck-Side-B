package com.cibernet.minestuckgodtier.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;

import java.util.EnumSet;

public class PacketChangePlayerXp extends MSGTPacket
{
    int value;

    @Override
    public MSGTPacket generatePacket(Object... args)
    {
        data.writeInt((Integer) args[0]);
        return this;
    }

    @Override
    public MSGTPacket consumePacket(ByteBuf data)
    {
        value = data.readInt();
        return this;
    }

    @Override
    public void execute(EntityPlayer player)
    {
        player.experienceLevel+= value;
    }

    /** Decreases player's experience properly */
    protected static void decreaseExp(EntityPlayer player, int cost)
    {
        player.experienceLevel -= cost;

        if (player.experienceLevel < 0)
        {
            player.experienceLevel = 0;
            player.experience = 0.0F;
            player.experienceTotal = 0;
        }
    }

    @Override
    public EnumSet<Side> getSenderSide()
    {
        return EnumSet.allOf(Side.class);
    }
}
