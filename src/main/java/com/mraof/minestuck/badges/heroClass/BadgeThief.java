package com.mraof.minestuck.badges.heroClass;

import com.mraof.minestuck.capabilities.MinestuckCapabilities;
import com.mraof.minestuck.capabilities.api.IBadgeEffects;
import com.mraof.minestuck.capabilities.caps.GodKeyStates;
import com.mraof.minestuck.client.particles.MinestuckParticles;
import com.mraof.minestuck.event.handler.GodTierEventHandler;
import com.mraof.minestuck.network.skaianet.SburbConnection;
import com.mraof.minestuck.network.skaianet.SkaianetHandler;
import com.mraof.minestuck.potions.MinestuckPotions;
import com.mraof.minestuck.util.EnumClass;
import com.mraof.minestuck.util.IdentifierHandler;
import com.mraof.minestuck.util.MinestuckUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import java.util.Collection;
import java.util.Collections;

public class BadgeThief extends BadgeHeroClass
{
	public BadgeThief()
	{
		super(EnumClass.THIEF);
	}

	@Override
	public boolean onBadgeTick(World world, EntityPlayer player, IBadgeEffects badgeEffects, GodKeyStates.KeyState state, int time)
	{
		if (state == GodKeyStates.KeyState.NONE)
			return false;

		if (!player.isCreative() && player.getFoodStats().getFoodLevel() < 8)
		{
			if (state.equals(GodKeyStates.KeyState.HELD))
				player.sendStatusMessage(new TextComponentTranslation("status.tooExhausted"), true);
			return false;
		}

		if (state != GodKeyStates.KeyState.RELEASED)
		{
			badgeEffects.startPowerParticles(getClass(), MinestuckParticles.ParticleType.AURA, EnumClass.THIEF, time > 20 ? 5 : 1);
			return true;
		}

		if (time < 20)
			return false;

		EntityLivingBase target = MinestuckUtils.getTargetEntity(player);

		if (!(target instanceof EntityPlayer))
			return false;

		badgeEffects.startPowerParticles(getClass(), MinestuckParticles.ParticleType.AURA, EnumClass.THIEF, 20);
		target.getCapability(MinestuckCapabilities.BADGE_EFFECTS, null).oneshotPowerParticles(MinestuckParticles.ParticleType.AURA, EnumClass.THIEF, 20);
		PotionEffect effect = new PotionEffect(MinestuckPotions.GOD_TIER_LOCK, 2400, 0);
		effect.setCurativeItems(Collections.emptyList());
		target.addPotionEffect(effect);

		IdentifierHandler.PlayerIdentifier targetPid = IdentifierHandler.encode((EntityPlayer) target);
		SburbConnection c = SkaianetHandler.getMainConnection(targetPid, true);

		if (c != null && c.enteredGame())
		{
			Collection<PotionEffect> effectsToSteal = GodTierEventHandler.getAspectEffects((EntityPlayer) target).values();

			for (PotionEffect potion : effectsToSteal)
				if (target.isPotionActive(potion.getPotion()))
					player.addPotionEffect(new PotionEffect(potion.getPotion(), 2400, potion.getAmplifier(), false, true));
		}

		if (!player.isCreative() && !world.isRemote)
			player.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel() - 8);

		return true;
	}
}
