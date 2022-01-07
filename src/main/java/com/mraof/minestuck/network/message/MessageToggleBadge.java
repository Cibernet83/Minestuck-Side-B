package com.mraof.minestuck.network.message;

import com.mraof.minestuck.badges.Badge;
import com.mraof.minestuck.badges.MinestuckBadges;
import com.mraof.minestuck.capabilities.MinestuckCapabilities;
import com.mraof.minestuck.capabilities.api.IGodTierData;
import com.mraof.minestuck.network.MinestuckMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;

public class MessageToggleBadge implements MinestuckMessage
{
	private Badge badge;
	private boolean sendMessage;

	private MessageToggleBadge() { }

	public MessageToggleBadge(Badge badge, boolean sendMessage)
	{
		this.badge = badge;
		this.sendMessage = sendMessage;
	}

	public MessageToggleBadge(Badge badge)
	{
		this(badge, false);
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		ByteBufUtils.writeRegistryEntry(buf, badge);
		buf.writeBoolean(sendMessage);
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		badge = ByteBufUtils.readRegistryEntry(buf, MinestuckBadges.REGISTRY);
		sendMessage = buf.readBoolean();
	}

	@Override
	public void execute(EntityPlayer player)
	{
		IGodTierData data = player.getCapability(MinestuckCapabilities.GOD_TIER_DATA, null);
		if(data.hasBadge(badge))
		{
			data.setBadgeEnabled(badge, !data.isBadgeEnabled(badge));
			data.update();

			if(sendMessage)
				player.sendStatusMessage(new TextComponentTranslation((!data.isBadgeEnabled(badge) ? "status.badgeDisabled" : "status.badgeEnabled"), badge.getDisplayComponent()), true);
		}
	}

	@Override
	public Side toSide()
	{
		return Side.SERVER;
	}
}
