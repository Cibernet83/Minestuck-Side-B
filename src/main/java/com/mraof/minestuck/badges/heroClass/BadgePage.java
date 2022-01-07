package com.mraof.minestuck.badges.heroClass;

import com.mraof.minestuck.capabilities.api.IBadgeEffects;
import com.mraof.minestuck.capabilities.caps.GodKeyStates;
import com.mraof.minestuck.util.EnumClass;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class BadgePage extends BadgeHeroClass
{
	public BadgePage()
	{
		super(EnumClass.PAGE, 8, 80);
	}

	@Override
	public boolean onBadgeTick(World world, EntityPlayer player, IBadgeEffects badgeEffects, GodKeyStates.KeyState state, int time)
	{
		return false;
	}
}
