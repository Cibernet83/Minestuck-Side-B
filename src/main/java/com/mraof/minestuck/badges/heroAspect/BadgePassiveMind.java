package com.mraof.minestuck.badges.heroAspect;

import com.mraof.minestuck.capabilities.MinestuckCapabilities;
import com.mraof.minestuck.capabilities.api.IBadgeEffects;
import com.mraof.minestuck.capabilities.caps.GodKeyStates;
import com.mraof.minestuck.client.particles.MinestuckParticles;
import com.mraof.minestuck.potions.MinestuckPotions;
import com.mraof.minestuck.util.EnumAspect;
import com.mraof.minestuck.util.EnumRole;
import com.mraof.minestuck.util.MinestuckUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import java.util.Collections;

public class BadgePassiveMind extends BadgeHeroAspect
{
	protected static final int ENERGY_USE = 9;

	public BadgePassiveMind()
	{
		super(EnumAspect.MIND, EnumRole.PASSIVE, EnumAspect.HOPE);
	}

	@Override
	public boolean onBadgeTick(World world, EntityPlayer player, IBadgeEffects badgeEffects, GodKeyStates.KeyState state, int time)
	{
		if (state != GodKeyStates.KeyState.PRESS)
			return false;

		if (!player.isCreative() && player.getFoodStats().getFoodLevel() < ENERGY_USE)
		{
			player.sendStatusMessage(new TextComponentTranslation("status.tooExhausted"), true);
			return false;
		}

		EntityLivingBase target = MinestuckUtils.getTargetEntity(player);
		badgeEffects.startPowerParticles(getClass(), MinestuckParticles.ParticleType.AURA, EnumAspect.MIND, 5);

		if (target != null)
		{
			PotionEffect effect = new PotionEffect(MinestuckPotions.MIND_CONFUSION, 400, 0);
			effect.setCurativeItems(Collections.emptyList());
			target.addPotionEffect(effect);
			if (!player.isCreative() && !world.isRemote)
				player.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel() - ENERGY_USE);
			target.getCapability(MinestuckCapabilities.BADGE_EFFECTS, null).oneshotPowerParticles(MinestuckParticles.ParticleType.AURA, EnumAspect.MIND, 10);
		}

		return true;
	}
}
