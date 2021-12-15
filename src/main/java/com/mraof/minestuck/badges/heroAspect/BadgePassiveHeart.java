package com.mraof.minestuck.badges.heroAspect;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.capabilities.MinestuckCapabilities;
import com.mraof.minestuck.capabilities.api.IBadgeEffects;
import com.mraof.minestuck.capabilities.caps.GodKeyStates;
import com.mraof.minestuck.client.particles.MSGTParticles;
import com.mraof.minestuck.util.EnumAspect;
import com.mraof.minestuck.util.EnumRole;
import com.mraof.minestuck.util.MSGTUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class BadgePassiveHeart extends BadgeHeroAspect
{
	public static final DamageSource DAMAGE_SOURCE = new DamageSource(Minestuck.MODID+".fragmentedSoul").setDamageBypassesArmor();

	public BadgePassiveHeart()
	{
		super(EnumAspect.HEART, EnumRole.PASSIVE, EnumAspect.BLOOD);
	}

	@Override
	public boolean onBadgeTick(World world, EntityPlayer player, IBadgeEffects badgeEffects, GodKeyStates.KeyState state, int time)
	{
		if(!state.equals(GodKeyStates.KeyState.HELD))
			return false;

		if (time % 10 != 0) // for damage cooldown
			return false;

		EntityLivingBase target = MSGTUtils.getTargetEntity(player);
		if (target != null)
		{
			player.attackEntityFrom(DAMAGE_SOURCE, 8);
			if (target.getHealth() >= target.getMaxHealth())
			{
				// Each amplifier increases health by 4
				if (target.isPotionActive(MobEffects.HEALTH_BOOST))
					target.addPotionEffect(new PotionEffect(MobEffects.HEALTH_BOOST, 12000, target.getActivePotionEffect(MobEffects.HEALTH_BOOST).getAmplifier() + 1));
				else
					target.addPotionEffect(new PotionEffect(MobEffects.HEALTH_BOOST, 12000));
				target.heal(target.getMaxHealth());
			}
			else
				target.heal(4);

			badgeEffects.startPowerParticles(getClass(), MSGTParticles.ParticleType.AURA, EnumAspect.HEART, 4);
			target.getCapability(MinestuckCapabilities.BADGE_EFFECTS, null).oneshotPowerParticles(MSGTParticles.ParticleType.AURA, EnumAspect.HEART, 2);
		}
		else
			badgeEffects.startPowerParticles(getClass(), MSGTParticles.ParticleType.AURA, EnumAspect.HEART, 2);

		return true;
	}
}
