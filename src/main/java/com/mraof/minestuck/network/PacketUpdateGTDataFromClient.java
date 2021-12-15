package com.mraof.minestuck.network;

import com.mraof.minestuck.capabilities.MinestuckCapabilities;
import com.mraof.minestuck.util.MSGTUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;

import java.util.EnumSet;

public class PacketUpdateGTDataFromClient extends MinestuckPacket
{
    NBTTagCompound nbt;

    @Override
    public MinestuckPacket generatePacket(Object... args)
    {
        ByteBufUtils.writeTag(data, Minecraft.getMinecraft().player.getCapability(MinestuckCapabilities.GOD_TIER_DATA, null).writeToNBT());
        return this;
    }

    @Override
    public MinestuckPacket consumePacket(ByteBuf data)
    {
        nbt = ByteBufUtils.readTag(data);
        return this;
    }

    @Override
    public void execute(EntityPlayer player)
    {
        if(nbt.hasKey("Reset"))
        {
            nbt.removeTag("Reset");
            MSGTUtils.onResetGodTier(player);
        }
        player.getCapability(MinestuckCapabilities.GOD_TIER_DATA, null).readFromNBT(nbt);
    }

    @Override
    public EnumSet<Side> getSenderSide() {
        return EnumSet.of(Side.CLIENT);
    }
}
