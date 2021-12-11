package com.cibernet.minestuckgodtier.network;

import com.cibernet.minestuckgodtier.capabilities.MSGTCapabilities;
import com.cibernet.minestuckgodtier.util.MSGTUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;

import java.util.EnumSet;

public class PacketUpdateGTDataFromClient extends MSGTPacket
{
    NBTTagCompound nbt;

    @Override
    public MSGTPacket generatePacket(Object... args)
    {
        ByteBufUtils.writeTag(data, Minecraft.getMinecraft().player.getCapability(MSGTCapabilities.GOD_TIER_DATA, null).writeToNBT());
        return this;
    }

    @Override
    public MSGTPacket consumePacket(ByteBuf data)
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
        player.getCapability(MSGTCapabilities.GOD_TIER_DATA, null).readFromNBT(nbt);
    }

    @Override
    public EnumSet<Side> getSenderSide() {
        return EnumSet.of(Side.CLIENT);
    }
}
