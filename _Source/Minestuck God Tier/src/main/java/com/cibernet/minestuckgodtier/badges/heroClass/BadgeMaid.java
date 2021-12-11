package com.cibernet.minestuckgodtier.badges.heroClass;

import com.cibernet.minestuckgodtier.capabilities.GodKeyStates;
import com.cibernet.minestuckgodtier.capabilities.MSGTCapabilities;
import com.cibernet.minestuckgodtier.capabilities.api.IBadgeEffects;
import com.cibernet.minestuckgodtier.client.particles.MSGTParticles;
import com.cibernet.minestuckgodtier.events.handlers.GTEventHandler;
import com.cibernet.minestuckgodtier.potions.MSGTPotions;
import com.cibernet.minestuckgodtier.util.MSGTUtils;
import com.mraof.minestuck.util.EnumAspect;
import com.mraof.minestuck.util.EnumClass;
import com.mraof.minestuck.util.IdentifierHandler;
import com.mraof.minestuck.util.MinestuckPlayerData;
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
					target.getCapability(MSGTCapabilities.BADGE_EFFECTS, null).oneshotPowerParticles(MSGTParticles.ParticleType.AURA, aspect, 10);
					target.addPotionEffect(new PotionEffect(GTEventHandler.aspectEffects[aspect.ordinal()], 2400, 3));
					continue;
				}
				EnumAspect targetAspect = MinestuckPlayerData.getTitle(IdentifierHandler.encode((EntityPlayer) target)).getHeroAspect();

				if(targetAspect == EnumAspect.HOPE || targetAspect == EnumAspect.MIND || targetAspect == EnumAspect.VOID)
				{
					target.getCapability(MSGTCapabilities.BADGE_EFFECTS, null).oneshotPowerParticles(MSGTParticles.ParticleType.AURA, aspect, 10);
					target.addPotionEffect(new PotionEffect(MSGTPotions.GOD_TIER_COMEBACK, 1200, 0));
					continue;
				}

				target.getCapability(MSGTCapabilities.BADGE_EFFECTS, null).oneshotPowerParticles(MSGTParticles.ParticleType.AURA, EnumClass.MAID, 10);
				target.addPotionEffect(new PotionEffect(GTEventHandler.aspectEffects[targetAspect.ordinal()], 1500, (int) ((MinestuckPlayerData.getData(player).echeladder.getRung() * GTEventHandler.aspectStrength[targetAspect.ordinal()]) + 3)));
			}
			if (!player.isCreative())
				player.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel() - 8);
		}


		EntityLivingBase target = MSGTUtils.getTargetEntity(player);
		if(time <= 36)
			badgeEffects.startPowerParticles(getClass(), MSGTParticles.ParticleType.AURA, EnumClass.MAID, target == null ? 1 : 5);
		else
			badgeEffects.startPowerParticles(getClass(), MSGTParticles.ParticleType.BURST, EnumClass.MAID, 20);
		if(state != GodKeyStates.KeyState.PRESS)
			return true;

		if(target != null)
		{
			if (!player.isCreative())
				player.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel() - 2);
			if(!(target instanceof EntityPlayer))
			{
				target.getCapability(MSGTCapabilities.BADGE_EFFECTS, null).oneshotPowerParticles(MSGTParticles.ParticleType.AURA, aspect, 10);
				target.addPotionEffect(new PotionEffect(GTEventHandler.aspectEffects[aspect.ordinal()], 2400, 3));
				return true;
			}
			EnumAspect targetAspect = MinestuckPlayerData.getTitle(IdentifierHandler.encode((EntityPlayer) target)).getHeroAspect();
			if(targetAspect == EnumAspect.HOPE || targetAspect == EnumAspect.MIND || targetAspect == EnumAspect.VOID)
			{
				target.getCapability(MSGTCapabilities.BADGE_EFFECTS, null).oneshotPowerParticles(MSGTParticles.ParticleType.AURA, aspect, 10);
				target.addPotionEffect(new PotionEffect(MSGTPotions.GOD_TIER_COMEBACK, 1200, 0));
				return true;
			}
			target.getCapability(MSGTCapabilities.BADGE_EFFECTS, null).oneshotPowerParticles(MSGTParticles.ParticleType.AURA, EnumClass.MAID, 10);
			target.addPotionEffect(new PotionEffect(GTEventHandler.aspectEffects[targetAspect.ordinal()], 1500, (int) ((MinestuckPlayerData.getData(player).echeladder.getRung() * GTEventHandler.aspectStrength[targetAspect.ordinal()]) + 3)));
		}
		return true;
	}
}