package com.mraof.minestuck.network.message;

import com.mraof.minestuck.capabilities.MinestuckCapabilities;
import com.mraof.minestuck.network.MinestuckMessage;
import com.mraof.minestuck.util.MinestuckUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;

public class MessageGodTierData implements MinestuckMessage
{
    private NBTTagCompound nbt;

    private MessageGodTierData() { }

    public MessageGodTierData(EntityPlayer player)
    {
        nbt = player.getCapability(MinestuckCapabilities.GOD_TIER_DATA, null).writeToNBT();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        ByteBufUtils.writeTag(buf, nbt);
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        nbt = ByteBufUtils.readTag(buf);
    }

    @Override
    public void execute(EntityPlayer player)
    {
        if(nbt.hasKey("Reset"))
        {
            nbt.removeTag("Reset");
            MinestuckUtils.onResetGodTier(player);
        }
        player.getCapability(MinestuckCapabilities.GOD_TIER_DATA, null).readFromNBT(nbt);
    }

    @Override
    public Side toSide() {
        return Side.CLIENT;
    }
}
