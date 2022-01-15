package com.mraof.minestuck.badges.heroClass;

import com.mraof.minestuck.capabilities.MinestuckCapabilities;
import com.mraof.minestuck.capabilities.api.IBadgeEffects;
import com.mraof.minestuck.capabilities.caps.GodKeyStates;
import com.mraof.minestuck.client.particles.MinestuckParticles;
import com.mraof.minestuck.event.handler.BadgeEventHandler;
import com.mraof.minestuck.event.handler.GodTierEventHandler;
import com.mraof.minestuck.util.*;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collection;

public class BadgeBard extends BadgeHeroClass
{
	public BadgeBard()
	{
		super(EnumClass.BARD);
	}

	@Override
	public boolean onBadgeTick(World world, EntityPlayer player, IBadgeEffects badgeEffects, GodKeyStates.KeyState state, int time)
	{
		if (!state.equals(GodKeyStates.KeyState.PRESS))
			return false;

		if (!player.isCreative() && player.getFoodStats().getFoodLevel() < 6)
		{
			player.sendStatusMessage(new TextComponentTranslation("status.tooExhausted"), true);
			return false;
		}

		Title title = MinestuckPlayerData.getTitle(IdentifierHandler.encode(player));
		for (EntityLivingBase target : world.getEntitiesWithinAABB(EntityLivingBase.class, player.getEntityBoundingBox().grow(10, 1, 10), (entity) -> entity != player))
		{
			if (world.rand.nextBoolean())
			{
				PotionEffect effect = BadgeEventHandler.NEGATIVE_EFFECTS.get(title == null ? EnumAspect.RAGE : title.getHeroAspect());
				target.addPotionEffect(new PotionEffect(effect.getPotion(), effect.getDuration() * 2, effect.getAmplifier() * 2));
			}
			else
			{

				Collection<PotionEffect> effects = title == null ? new ArrayList<PotionEffect>()
				{{add(new PotionEffect(MobEffects.STRENGTH, 300, 4));}} :
														   GodTierEventHandler.getAspectEffects(player).values();
				for (PotionEffect p : effects)
					target.addPotionEffect(new PotionEffect(p.getPotion(), 1200, 9));

				target.getCapability(MinestuckCapabilities.BADGE_EFFECTS, null).oneshotPowerParticles(MinestuckParticles.ParticleType.AURA, EnumClass.MUSE, 10);
			}
		}
		if (!player.isCreative())
			player.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel() - 6);

		badgeEffects.startPowerParticles(getClass(), MinestuckParticles.ParticleType.BURST, EnumClass.BARD, 20);

		return true;
	}
}