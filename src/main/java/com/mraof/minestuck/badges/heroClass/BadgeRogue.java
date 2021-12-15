package com.mraof.minestuck.badges.heroClass;

import com.mraof.minestuck.capabilities.MinestuckCapabilities;
import com.mraof.minestuck.capabilities.api.IBadgeEffects;
import com.mraof.minestuck.capabilities.caps.GodKeyStates;
import com.mraof.minestuck.client.particles.MSGTParticles;
import com.mraof.minestuck.util.EnumClass;
import com.mraof.minestuck.util.MSGTUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import java.util.ArrayList;

public class BadgeRogue extends BadgeHeroClass
{
	public BadgeRogue()
	{
		super(EnumClass.ROGUE);
	}

	@Override
	public boolean onBadgeTick(World world, EntityPlayer player, IBadgeEffects badgeEffects, GodKeyStates.KeyState state, int time)
	{
		if(state == GodKeyStates.KeyState.NONE || time > 60)
			return false;

		if(!player.isCreative() && player.getFoodStats().getFoodLevel() < 4)
		{
			player.sendStatusMessage(new TextComponentTranslation("status.tooExhausted"), true);
			return false;
		}

		ArrayList<PotionEffect> appliedPotions = new ArrayList<>();

		for(PotionEffect effect : player.getActivePotionEffects())
			appliedPotions.add(new PotionEffect(effect.getPotion(), Math.min(effect.getDuration(), 6000), effect.getAmplifier(), effect.getIsAmbient(), true));

		if(time > 59)
		{
			if(!player.isCreative() && player.getFoodStats().getFoodLevel() < 8)
			{
				player.sendStatusMessage(new TextComponentTranslation("status.tooExhausted"), true);
				return false;
			}

			for(EntityLivingBase target : world.getEntitiesWithinAABB(EntityLivingBase.class, player.getEntityBoundingBox().grow(5,1,5), (entity) -> entity != player))
				for(PotionEffect effect : appliedPotions)
				{
					target.addPotionEffect(effect);
					target.getCapability(MinestuckCapabilities.BADGE_EFFECTS, null).oneshotPowerParticles(MSGTParticles.ParticleType.AURA, EnumClass.ROGUE, 3);
				}
			if (!player.isCreative())
				player.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel() - 8);
		}

		EntityLivingBase target = MSGTUtils.getTargetEntity(player);
		if(time < 57)
			badgeEffects.oneshotPowerParticles(MSGTParticles.ParticleType.AURA, EnumClass.ROGUE, target != null ? 5 : 1);
		else
			badgeEffects.oneshotPowerParticles(MSGTParticles.ParticleType.BURST, EnumClass.ROGUE, 20);
		if(!state.equals(GodKeyStates.KeyState.PRESS))
			return true;

		if(target != null)
		{
			for(PotionEffect effect : appliedPotions)
				target.addPotionEffect(effect);
			target.getCapability(MinestuckCapabilities.BADGE_EFFECTS, null).oneshotPowerParticles(MSGTParticles.ParticleType.AURA, EnumClass.ROGUE, 3);
			if (!player.isCreative())
				player.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel() - 4);
		}

		return true;
	}
}
