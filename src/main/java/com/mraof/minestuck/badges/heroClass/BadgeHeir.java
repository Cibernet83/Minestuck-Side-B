package com.mraof.minestuck.badges.heroClass;

import com.mraof.minestuck.badges.MinestuckBadges;
import com.mraof.minestuck.capabilities.MinestuckCapabilities;
import com.mraof.minestuck.capabilities.api.IBadgeEffects;
import com.mraof.minestuck.capabilities.caps.GodKeyStates;
import com.mraof.minestuck.client.particles.MinestuckParticles;
import com.mraof.minestuck.event.handler.BadgeEventHandler;
import com.mraof.minestuck.util.*;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BadgeHeir extends BadgeHeroClass
{
	public BadgeHeir()
	{
		super(EnumClass.HEIR);
	}

	@Override
	public boolean onBadgeTick(World world, EntityPlayer player, IBadgeEffects badgeEffects, GodKeyStates.KeyState state, int time)
	{
		return false;
	}

	private static void doHeirThings(EntityPlayer target, EntityLivingBase trueSource, float amount)
	{
		if(target != null && target.getCapability(MinestuckCapabilities.GOD_TIER_DATA, null).isBadgeActive(MinestuckBadges.BADGE_HEIR))
		{
			if(amount == -1 || (target.world.rand.nextFloat() < Math.max(0.1f, amount/target.getHealth()*0.8f * (target.getLuck()/4f-0.2f))))
			{
				Title title = MinestuckPlayerData.getTitle(IdentifierHandler.encode(target));
				if(title != null)
				{
					if(title.getHeroAspect() == EnumAspect.HOPE)
					{
						trueSource.setFire(5);
						trueSource.setAir(0);
					}
					PotionEffect effect = BadgeEventHandler.NEGATIVE_EFFECTS.get(title.getHeroAspect());
					trueSource.addPotionEffect(new PotionEffect(effect.getPotion(), effect.getDuration(), effect.getAmplifier()));
					trueSource.getCapability(MinestuckCapabilities.BADGE_EFFECTS, null).oneshotPowerParticles(MinestuckParticles.ParticleType.AURA, EnumClass.HEIR, 5);
				}
			}
		}
	}

	// Bluh, these have the same functions but are different classes so they need separate checks
	@SubscribeEvent
	public static void onLivingDamage(LivingDamageEvent event)
	{
		if (event.getEntity().world.isRemote || !(event.getEntityLiving() instanceof EntityPlayer) || !(event.getSource().getTrueSource() instanceof EntityLivingBase))
			return;

		doHeirThings((EntityPlayer) event.getEntityLiving(), (EntityLivingBase) event.getSource().getTrueSource(), event.getAmount());
	}

	@SubscribeEvent
	public static void onLivingDeath(LivingDeathEvent event)
	{
		if (event.getEntity().world.isRemote || !(event.getEntityLiving() instanceof EntityPlayer) || !(event.getSource().getTrueSource() instanceof EntityLivingBase))
			return;

		doHeirThings((EntityPlayer) event.getEntityLiving(), (EntityLivingBase) event.getSource().getTrueSource(), -1);
	}
}
