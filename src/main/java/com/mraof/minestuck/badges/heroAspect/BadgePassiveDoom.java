package com.mraof.minestuck.badges.heroAspect;

import com.mraof.minestuck.capabilities.MinestuckCapabilities;
import com.mraof.minestuck.capabilities.api.IBadgeEffects;
import com.mraof.minestuck.capabilities.caps.GodKeyStates;
import com.mraof.minestuck.client.particles.MinestuckParticles;
import com.mraof.minestuck.potions.MinestuckPotions;
import com.mraof.minestuck.util.EnumAspect;
import com.mraof.minestuck.util.EnumRole;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class BadgePassiveDoom extends BadgeHeroAspect
{
	public BadgePassiveDoom()
	{
		super(EnumAspect.DOOM, EnumRole.PASSIVE, EnumAspect.BLOOD);
	}

	protected static final int RADIUS = 16;

	@Override
	public boolean onBadgeTick(World world, EntityPlayer player, IBadgeEffects badgeEffects, GodKeyStates.KeyState state, int time)
	{
		if(state == GodKeyStates.KeyState.NONE || time >= 26)
			return false;

		if(!player.isCreative() && player.getFoodStats().getFoodLevel() < 8)
		{
			player.sendStatusMessage(new TextComponentTranslation("status.tooExhausted"), true);
			return false;
		}

		if(time > 20)
			badgeEffects.startPowerParticles(getClass(), MinestuckParticles.ParticleType.BURST, EnumAspect.DOOM, 20);
		else
			badgeEffects.startPowerParticles(getClass(), MinestuckParticles.ParticleType.AURA, EnumAspect.DOOM, 10);

		if(time >= 25)
		{
			for(EntityLivingBase target : world.getEntitiesWithinAABB(EntityLivingBase.class, player.getEntityBoundingBox().grow(RADIUS), p -> !p.equals(player)))
			{
				if(!(target instanceof EntityPlayer || target instanceof IMob))
					continue;

				target.getCapability(MinestuckCapabilities.BADGE_EFFECTS, null).oneshotPowerParticles(MinestuckParticles.ParticleType.AURA, EnumAspect.DOOM, 10);

				PotionEffect effect = new PotionEffect(MinestuckPotions.DECAY, player.isOnSameTeam(target) ? 180 : 400, 1);
				target.addPotionEffect(effect);
			}
			if (!player.isCreative() && !world.isRemote)
				player.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel() - 8);
		}

		return true;
	}
}
