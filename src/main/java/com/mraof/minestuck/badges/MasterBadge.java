package com.mraof.minestuck.badges;

import com.mraof.minestuck.capabilities.MinestuckCapabilities;
import com.mraof.minestuck.network.MinestuckNetwork;
import com.mraof.minestuck.network.message.MessageAddXp;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MasterBadge extends BadgeLevel
{
	private final int requiredXp;
	private final float baseStat;
	private final float limit;

	public MasterBadge(String unlocName, int gtLevel, int requiredXp, float baseStat, float limit)
	{
		super(unlocName, gtLevel);
		this.requiredXp = requiredXp;
		this.baseStat = baseStat;
		this.limit = limit;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getDisplayTooltip()
	{
		return I18n.format(getUnlocalizedName() + ".tooltip", getStatNumber(Minecraft.getMinecraft().player), (MinestuckBadges.MASTER_BADGE_WISE.getStatNumber(Minecraft.getMinecraft().player) / 2));
	}

	public float getStatNumber(EntityPlayer player)
	{
		return Math.max(0, Math.min(limit + (player.getCapability(MinestuckCapabilities.GOD_TIER_DATA, null).isBadgeActive(MinestuckBadges.BADGE_OVERLORD) ? 20 : 0), (player.getLuck() + 1) * baseStat));
	}

	@Override
	public String getUnlockRequirements()
	{
		return I18n.format("badge.class.unlock", requiredXp);
	}

	@Override
	public boolean canUnlock(World world, EntityPlayer player)
	{
		if (player.experienceLevel >= requiredXp)
		{
			player.experienceLevel -= requiredLevel;
			MinestuckNetwork.sendTo(new MessageAddXp(-requiredLevel), player);
			return true;
		}
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ResourceLocation getTextureLocation()
	{
		return new ResourceLocation(getRegistryName().getResourceDomain(), "textures/gui/badges/" + getRegistryName().getResourcePath() +
																				   (Minecraft.getMinecraft().player.getCapability(MinestuckCapabilities.GOD_TIER_DATA, null).isBadgeActive(MinestuckBadges.BADGE_OVERLORD) ? "_overlord" : "") + ".png");
	}
}
