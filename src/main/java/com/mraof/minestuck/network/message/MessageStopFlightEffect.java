package com.mraof.minestuck.network.message;

import com.mraof.minestuck.network.MinestuckMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;

import java.util.EnumSet;

public class MessageStopFlightEffect extends MinestuckMessage
{
    boolean isBadEffect;

    @Override
    public void generatePacket(Object... args)
    {
        data.writeBoolean((Boolean) args[0]);

    }

    @Override
    public void consumePacket(ByteBuf data)
    {
        isBadEffect = data.readBoolean();

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
