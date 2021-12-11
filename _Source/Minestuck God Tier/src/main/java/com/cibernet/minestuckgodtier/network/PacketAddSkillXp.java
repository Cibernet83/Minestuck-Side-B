package com.cibernet.minestuckgodtier.network;

import com.cibernet.minestuckgodtier.capabilities.GodTierData;
import com.cibernet.minestuckgodtier.capabilities.MSGTCapabilities;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;

import java.util.EnumSet;
import java.util.UUID;

public class PacketAddSkillXp extends MSGTPacket
{
    GodTierData.SkillType skillType;
    int                   amount;
    UUID                  playerUUID;

    @Override
    public MSGTPacket generatePacket(Object... args)
    {
        data.writeInt(((GodTierData.SkillType)args[0]).ordinal());
        data.writeInt(args.length > 2 ? (Integer) args[2] : 1);
        ByteBufUtils.writeUTF8String(data, ((EntityPlayer)args[1]).getUniqueID().toString());
        return this;
    }

    @Override
    public MSGTPacket consumePacket(ByteBuf data)
    {
        skillType = GodTierData.SkillType.values()[data.readInt()];
        amount = data.readInt();
        playerUUID = UUID.fromString(ByteBufUtils.readUTF8String(data));
        return this;
    }

    @Override
    public void execute(EntityPlayer player)
    {
        if(player.getUniqueID().equals(playerUUID))
        {
            int actualAmount = player.isCreative() ? amount : Math.min(player.experienceLevel, amount);
            player.getCapability(MSGTCapabilities.GOD_TIER_DATA, null).increaseXp(skillType, actualAmount);
            if(!player.isCreative())
                player.experienceLevel -= actualAmount;
        }
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
        return EnumSet.of(Side.CLIENT);
    }
}
