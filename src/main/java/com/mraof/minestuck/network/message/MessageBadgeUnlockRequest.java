package com.mraof.minestuck.network.message;

import com.mraof.minestuck.badges.Badge;
import com.mraof.minestuck.badges.MasterBadge;
import com.mraof.minestuck.badges.MinestuckBadges;
import com.mraof.minestuck.capabilities.MinestuckCapabilities;
import com.mraof.minestuck.capabilities.api.IGodTierData;
import com.mraof.minestuck.network.MinestuckMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;

public class MessageBadgeUnlockRequest implements MinestuckMessage
{
	private Badge badge;

	public MessageBadgeUnlockRequest() { }

	public MessageBadgeUnlockRequest(Badge badge)
	{
		this.badge = badge;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		badge = ByteBufUtils.readRegistryEntry(buf, MinestuckBadges.REGISTRY);
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		ByteBufUtils.writeRegistryEntry(buf, badge);
	}

	@Override
	public void execute(EntityPlayer player)
	{
		IGodTierData data = player.getCapability(MinestuckCapabilities.GOD_TIER_DATA, null);

		if (((badge instanceof MasterBadge && data.getMasterBadge() == null) || data.getBadgesLeft() > 0) && !data.hasBadge(badge) && ((player.isCreative() && data.hasMasterControl()) || badge.canUnlock(player.world, player)))
			data.addBadge(badge, true);
	}

	@Override
	public Side toSide()
	{
		return Side.SERVER;
	}
}
