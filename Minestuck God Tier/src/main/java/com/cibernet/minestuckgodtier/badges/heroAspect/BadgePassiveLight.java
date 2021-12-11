package com.cibernet.minestuckgodtier.badges.heroAspect;

import com.cibernet.minestuckgodtier.capabilities.GodKeyStates;
import com.cibernet.minestuckgodtier.capabilities.MSGTCapabilities;
import com.cibernet.minestuckgodtier.capabilities.api.IBadgeEffects;
import com.cibernet.minestuckgodtier.client.particles.MSGTParticles;
import com.cibernet.minestuckgodtier.util.EnumRole;
import com.mraof.minestuck.util.EnumAspect;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import java.util.Collections;

public class BadgePassiveLight extends BadgeHeroAspect
{
	public BadgePassiveLight()
	{
		super(EnumAspect.LIGHT, EnumRole.PASSIVE, EnumAspect.MIND);
	}

	protected static final int RADIUS = 64;

	@Override
	public boolean onBadgeTick(World world, EntityPlayer player, IBadgeEffects badgeEffects, GodKeyStates.KeyState state, int time)
	{
		if(state == GodKeyStates.KeyState.PRESS)
		{
			if(!player.isCreative() && player.getFoodStats().getFoodLevel() < 4)
			{
				player.sendStatusMessage(new TextComponentTranslation("status.tooExhausted"), true);
				return false;
			}

			badgeEffects.startPowerParticles(getClass(), MSGTParticles.ParticleType.BURST, EnumAspect.LIGHT, 10);

			for(EntityLivingBase target : world.getEntitiesWithinAABB(EntityLivingBase.class, player.getEntityBoundingBox().grow(RADIUS), (entity) -> (entity instanceof EntityPlayer)))
			{
				target.getCapability(MSGTCapabilities.BADGE_EFFECTS, null).oneshotPowerParticles(MSGTParticles.ParticleType.AURA, EnumAspect.LIGHT, 10);
				PotionEffect effect = new PotionEffect(MobEffects.GLOWING, 600, 0);
				effect.setCurativeItems(Collections.emptyList());
				target.addPotionEffect(effect);
			}

			if (!player.isCreative())
				player.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel() - 4);
		}

		if(state == GodKeyStates.KeyState.NONE || time >= 19)
			return false;

		if(!player.isCreative() && player.getFoodStats().getFoodLevel() < 4)
		{
			player.sendStatusMessage(new TextComponentTranslation("status.tooExhausted"), true);
			return false;
		}

		if(time > 15)
			badgeEffects.startPowerParticles(getClass(), MSGTParticles.ParticleType.BURST, EnumAspect.LIGHT, 20);
		else
			badgeEffects.startPowerParticles(getClass(), MSGTParticles.ParticleType.AURA, EnumAspect.LIGHT, 10);

		if(time >= 18)
		{
			for(EntityLivingBase target : world.getEntitiesWithinAABB(EntityLivingBase.class, player.getEntityBoundingBox().grow(RADIUS), (entity) -> entity != player))
			{
				target.getCapability(MSGTCapabilities.BADGE_EFFECTS, null).oneshotPowerParticles(MSGTParticles.ParticleType.AURA, EnumAspect.LIGHT, 10);
				PotionEffect effect = new PotionEffect(MobEffects.GLOWING, 600, 0);
				effect.setCurativeItems(Collections.emptyList());
				target.addPotionEffect(effect);
			}
			if (!player.isCreative())
				player.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel() - 4);
		}

		return true;
	}
}
