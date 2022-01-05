package com.mraof.minestuck.network.message;

import com.mraof.minestuck.client.particles.MinestuckParticles;
import com.mraof.minestuck.network.MinestuckMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;

import java.util.EnumSet;

public class MessageSendParticle extends MinestuckMessage
{
    MinestuckParticles.ParticleType type;
    double x;
    double y;
    double z;
    int color;
    int count;

    @Override
    public void generatePacket(Object... args)
    {
        data.writeInt(((MinestuckParticles.ParticleType)args[0]).ordinal());
        data.writeInt((Integer) args[1]);
        data.writeInt((Integer) args[2]);

        if(args[3] instanceof Entity)
        {
            data.writeDouble(((Entity)args[3]).posX);
            data.writeDouble(((Entity)args[3]).posY);
            data.writeDouble(((Entity)args[3]).posZ);
        }
        else if(args[3] instanceof BlockPos)
        {
            BlockPos pos = ((BlockPos)args[3]);
            data.writeDouble(pos.getX());
            data.writeDouble(pos.getY());
            data.writeDouble(pos.getZ());
        }
        else
        {
            data.writeDouble((Double) args[3]);
            data.writeDouble((Double) args[4]);
            data.writeDouble((Double) args[5]);
        }


    }

    @Override
    public void consumePacket(ByteBuf data)
    {
        type = MinestuckParticles.ParticleType.values()[data.readInt()];
        color = data.readInt();
        count = data.readInt();

        x = data.readDouble();
        y = data.readDouble();
        z = data.readDouble();


    }

    @Override
    public void execute(EntityPlayer player)
    {
        switch (type)
        {
            case AURA:
                MinestuckParticles.spawnAuraParticles(player.world, x, y, z, color, count);
                break;
            case BURST:
                MinestuckParticles.spawnBurstParticles(player.world, x, y, z, color, count);
                break;
        }
    }

    @Override
    public EnumSet<Side> getSenderSide() {
        return EnumSet.of(Side.SERVER);
    }

}
