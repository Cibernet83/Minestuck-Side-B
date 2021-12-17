package com.mraof.minestuck.badges.heroClass;

import com.mraof.minestuck.capabilities.MinestuckCapabilities;
import com.mraof.minestuck.capabilities.api.IBadgeEffects;
import com.mraof.minestuck.capabilities.caps.GodKeyStates;
import com.mraof.minestuck.client.particles.MinestuckParticles;
import com.mraof.minestuck.event.handler.GodTierEventHandler;
import com.mraof.minestuck.potions.MinestuckPotions;
import com.mraof.minestuck.util.*;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class BadgeMaid extends BadgeHeroClass
{
	public BadgeMaid() {
		super(EnumClass.MAID);
	}

    @Override
    public boolean onBadgeTick(World world, EntityPlayer player, IBadgeEffects badgeEffects, GodKeyStates.KeyState state, int time)
    {
	    if(state == GodKeyStates.KeyState.NONE || time > 40)
		    return false;

	    if(!player.isCreative() && player.getFoodStats().getFoodLevel() < 2)
	    {
		    player.sendStatusMessage(new TextComponentTranslation("status.tooExhausted"), true);
		    return false;
	    }

        EnumAspect aspect = MinestuckPlayerData.getTitle(IdentifierHandler.encode(player)).getHeroAspect();

		if(time == 39)
		{
			if(!player.isCreative() && player.getFoodStats().getFoodLevel() < 8)
			{
				player.sendStatusMessage(new TextComponentTranslation("status.tooExhausted"), true);
				return false;
			}

			for(EntityLivingBase target : world.getEntitiesWithinAABB(EntityLivingBase.class, player.getEntityBoundingBox().grow(5,1,5), (entity) -> entity != player))
			{
				if(!(target instanceof EntityPlayer))
				{
					target.getCapability(MinestuckCapabilities.BADGE_EFFECTS, null).oneshotPowerParticles(MinestuckParticles.ParticleType.AURA, aspect, 10);
					target.addPotionEffect(new PotionEffect(GodTierEventHandler.aspectEffects[aspect.ordinal()], 2400, 3));
					continue;
				}
				EnumAspect targetAspect = MinestuckPlayerData.getTitle(IdentifierHandler.encode((EntityPlayer) target)).getHeroAspect();

				if(targetAspect == EnumAspect.HOPE || targetAspect == EnumAspect.MIND || targetAspect == EnumAspect.VOID)
				{
					target.getCapability(MinestuckCapabilities.BADGE_EFFECTS, null).oneshotPowerParticles(MinestuckParticles.ParticleType.AURA, aspect, 10);
					target.addPotionEffect(new PotionEffect(MinestuckPotions.GOD_TIER_COMEBACK, 1200, 0));
					continue;
				}

				target.getCapability(MinestuckCapabilities.BADGE_EFFECTS, null).oneshotPowerParticles(MinestuckParticles.ParticleType.AURA, EnumClass.MAID, 10);
				target.addPotionEffect(new PotionEffect(GodTierEventHandler.aspectEffects[targetAspect.ordinal()], 1500, (int) ((MinestuckPlayerData.getData(player).echeladder.getRung() * GodTierEventHandler.aspectStrength[targetAspect.ordinal()]) + 3)));
			}
			if (!player.isCreative())
				player.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel() - 8);
		}


		EntityLivingBase target = MSGTUtils.getTargetEntity(player);
		if(time <= 36)
			badgeEffects.startPowerParticles(getClass(), MinestuckParticles.ParticleType.AURA, EnumClass.MAID, target == null ? 1 : 5);
		else
			badgeEffects.startPowerParticles(getClass(), MinestuckParticles.ParticleType.BURST, EnumClass.MAID, 20);
		if(state != GodKeyStates.KeyState.PRESS)
			return true;

		if(target != null)
		{
			if (!player.isCreative())
				player.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel() - 2);
			if(!(target instanceof EntityPlayer))
			{
				target.getCapability(MinestuckCapabilities.BADGE_EFFECTS, null).oneshotPowerParticles(MinestuckParticles.ParticleType.AURA, aspect, 10);
				target.addPotionEffect(new PotionEffect(GodTierEventHandler.aspectEffects[aspect.ordinal()], 2400, 3));
				return true;
			}
			EnumAspect targetAspect = MinestuckPlayerData.getTitle(IdentifierHandler.encode((EntityPlayer) target)).getHeroAspect();
			if(targetAspect == EnumAspect.HOPE || targetAspect == EnumAspect.MIND || targetAspect == EnumAspect.VOID)
			{
				target.getCapability(MinestuckCapabilities.BADGE_EFFECTS, null).oneshotPowerParticles(MinestuckParticles.ParticleType.AURA, aspect, 10);
				target.addPotionEffect(new PotionEffect(MinestuckPotions.GOD_TIER_COMEBACK, 1200, 0));
				return true;
			}
			target.getCapability(MinestuckCapabilities.BADGE_EFFECTS, null).oneshotPowerParticles(MinestuckParticles.ParticleType.AURA, EnumClass.MAID, 10);
			target.addPotionEffect(new PotionEffect(GodTierEventHandler.aspectEffects[targetAspect.ordinal()], 1500, (int) ((MinestuckPlayerData.getData(player).echeladder.getRung() * GodTierEventHandler.aspectStrength[targetAspect.ordinal()]) + 3)));
		}
		return true;
	}
}