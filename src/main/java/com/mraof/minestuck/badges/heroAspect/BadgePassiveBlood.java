package com.mraof.minestuck.badges.heroAspect;

import com.mraof.minestuck.capabilities.MinestuckCapabilities;
import com.mraof.minestuck.capabilities.api.IBadgeEffects;
import com.mraof.minestuck.capabilities.caps.GodKeyStates;
import com.mraof.minestuck.client.particles.MinestuckParticles;
import com.mraof.minestuck.util.EnumAspect;
import com.mraof.minestuck.util.EnumRole;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class BadgePassiveBlood extends BadgeHeroAspect
{
	protected static final int RADIUS = 16;

	public BadgePassiveBlood()
	{
		super(EnumAspect.BLOOD, EnumRole.PASSIVE, EnumAspect.HOPE);
	}

	@Override
	public boolean onBadgeTick(World world, EntityPlayer player, IBadgeEffects badgeEffects, GodKeyStates.KeyState state, int time)
	{
		if (state == GodKeyStates.KeyState.NONE || time > 25)
			return false;

		if (!player.isCreative() && player.getFoodStats().getFoodLevel() < 6)
		{
			player.sendStatusMessage(new TextComponentTranslation("status.tooExhausted"), true);
			return false;
		}

		if (time > 20)
			badgeEffects.startPowerParticles(getClass(), MinestuckParticles.ParticleType.BURST, EnumAspect.BLOOD, 20);
		else
			badgeEffects.startPowerParticles(getClass(), MinestuckParticles.ParticleType.AURA, EnumAspect.BLOOD, 10);

		if (time >= 25)
		{
			for (EntityLivingBase target : world.getEntitiesWithinAABB(EntityLivingBase.class, player.getEntityBoundingBox().grow(RADIUS), (entity) -> entity != player))
			{
				target.getCapability(MinestuckCapabilities.BADGE_EFFECTS, null).oneshotPowerParticles(MinestuckParticles.ParticleType.AURA, EnumAspect.BLOOD, 10);

				// Amplifies Effect if present
				if (target.isPotionActive(MobEffects.STRENGTH))
				{
					if (target.getActivePotionEffect(MobEffects.STRENGTH).getAmplifier() < 5)
						target.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 800, target.getActivePotionEffect(MobEffects.STRENGTH).getAmplifier() + 1));
				}
				else target.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 800, 1));
				if (!target.isPotionActive(MobEffects.ABSORPTION))
					target.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 800, 1));
			}
			if (!player.isCreative() && !world.isRemote)
				player.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel() - 6);
		}

		return true;
	}
}
