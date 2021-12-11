package com.cibernet.minestuckgodtier.network;

import com.cibernet.minestuckgodtier.badges.Badge;
import com.cibernet.minestuckgodtier.badges.MSGTBadges;
import com.cibernet.minestuckgodtier.capabilities.MSGTCapabilities;
import com.cibernet.minestuckgodtier.capabilities.api.IGodTierData;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;

import java.util.EnumSet;

public class PacketToggleBadge extends MSGTPacket
{
    Badge badge;
    boolean sendMessage;

    @Override
    public MSGTPacket generatePacket(Object... args)
    {
        ByteBufUtils.writeUTF8String(data, ((Badge)args[0]).getRegistryName().toString());

        data.writeBoolean(args.length > 1 && (Boolean) args[1]);

        return this;
    }

    @Override
    public MSGTPacket consumePacket(ByteBuf data)
    {
        badge = MSGTBadges.REGISTRY.getValue(new ResourceLocation(ByteBufUtils.readUTF8String(data)));
        sendMessage = data.readBoolean();
        return this;
    }

    @Override
    public void execute(EntityPlayer player)
    {
        IGodTierData data = player.getCapability(MSGTCapabilities.GOD_TIER_DATA, null);
        if(data.hasBadge(badge))
        {
            data.setBadgeEnabled(badge, !data.isBadgeEnabled(badge));
            data.update();

            if(sendMessage)
                player.sendStatusMessage(new TextComponentTranslation((!data.isBadgeEnabled(badge) ? "status.badgeDisabled" : "status.badgeEnabled"), badge.getDisplayComponent()), true);
        }
    }

    @Override
    public EnumSet<Side> getSenderSide()
    {
        return EnumSet.of(Side.CLIENT);
    }
}
