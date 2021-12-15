package com.mraof.minestuck.badges;

import com.mraof.minestuck.capabilities.MinestuckCapabilities;
import com.mraof.minestuck.capabilities.caps.GodTierData;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class BadgeLevel extends Badge
{
    public final int requiredLevel;

    public BadgeLevel(String unlocName, int requiredLevel)
    {
        setUnlocalizedName(unlocName);
        this.requiredLevel = requiredLevel;
    }

    @Override
    public String getReadRequirements()
    {
        return I18n.format("badge.level.read", requiredLevel);
    }

    @Override
    public boolean isReadable(World world, EntityPlayer player)
    {
        return player.getCapability(MinestuckCapabilities.GOD_TIER_DATA, null).getSkillLevel(GodTierData.SkillType.GENERAL) >= requiredLevel;
    }
}
