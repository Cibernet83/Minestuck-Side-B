package com.cibernet.minestuckgodtier.network;

import com.cibernet.minestuckgodtier.badges.Badge;
import com.cibernet.minestuckgodtier.badges.MSGTBadges;
import com.cibernet.minestuckgodtier.badges.MasterBadge;
import com.cibernet.minestuckgodtier.capabilities.MSGTCapabilities;
import com.cibernet.minestuckgodtier.capabilities.api.IGodTierData;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;

import java.util.EnumSet;

public class PacketAttemptBadgeUnlock extends MSGTPacket
{
    Badge badge;

    @Override
    public MSGTPacket generatePacket(Object... args)
    {
        ByteBufUtils.writeUTF8String(data, ((Badge)args[0]).getRegistryName().toString());
        return this;
    }

    @Override
    public MSGTPacket consumePacket(ByteBuf data)
    {
        badge = MSGTBadges.REGISTRY.getValue(new ResourceLocation(ByteBufUtils.readUTF8String(data)));
        return this;
    }

    @Override
    public void execute(EntityPlayer player)
    {
        IGodTierData data = player.getCapability(MSGTCapabilities.GOD_TIER_DATA, null);

        if(((badge instanceof MasterBadge && data.getMasterBadge() == null) || data.getBadgesLeft() > 0) && !data.hasBadge(badge) && ((player.isCreative() && data.hasMasterControl()) || badge.canUnlock(player.world, player)))
            data.addBadge(badge, true);
    }

    @Override
    public EnumSet<Side> getSenderSide()
    {
        return EnumSet.of(Side.CLIENT);
    }
}
