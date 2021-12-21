package com.mraof.minestuck.network;

import com.mraof.minestuck.capabilities.MinestuckCapabilities;
import com.mraof.minestuck.util.MSGTUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;

import java.util.EnumSet;
import java.util.UUID;

public class PacketUpdateGTDataFromServer extends MinestuckPacket
{
    NBTTagCompound nbt;
    UUID uuid;

    @Override
    public void generatePacket(Object... args)
    {
        ByteBufUtils.writeTag(data, ((EntityPlayer) args[0]).getCapability(MinestuckCapabilities.GOD_TIER_DATA, null).writeToNBT());
        ByteBufUtils.writeUTF8String(data, ((EntityPlayer) args[0]).getUniqueID().toString());

    }

    @Override
    public void consumePacket(ByteBuf data)
    {
        nbt = ByteBufUtils.readTag(data);
        uuid = UUID.fromString(ByteBufUtils.readUTF8String(data));

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
        return EnumSet.of(Side.SERVER);
    }
}
