package com.mraof.minestuck.network.message;

import com.mraof.minestuck.badges.Badge;
import com.mraof.minestuck.badges.MasterBadge;
import com.mraof.minestuck.badges.MinestuckBadges;
import com.mraof.minestuck.capabilities.MinestuckCapabilities;
import com.mraof.minestuck.capabilities.api.IGodTierData;
import com.mraof.minestuck.network.MinestuckMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;

import java.util.EnumSet;

public class MessageAttemptBadgeUnlock extends MinestuckMessage
{
    Badge badge;

    @Override
    public void generatePacket(Object... args)
    {
        ByteBufUtils.writeUTF8String(data, ((Badge)args[0]).getRegistryName().toString());

    }

    @Override
    public void consumePacket(ByteBuf data)
    {
        badge = MinestuckBadges.REGISTRY.getValue(new ResourceLocation(ByteBufUtils.readUTF8String(data)));

    }

    @Override
    public void execute(EntityPlayer player)
    {
        IGodTierData data = player.getCapability(MinestuckCapabilities.GOD_TIER_DATA, null);

        if(((badge instanceof MasterBadge && data.getMasterBadge() == null) || data.getBadgesLeft() > 0) && !data.hasBadge(badge) && ((player.isCreative() && data.hasMasterControl()) || badge.canUnlock(player.world, player)))
            data.addBadge(badge, true);
    }

    @Override
    public EnumSet<Side> getSenderSide()
    {
        return EnumSet.of(Side.CLIENT);
    }
}
