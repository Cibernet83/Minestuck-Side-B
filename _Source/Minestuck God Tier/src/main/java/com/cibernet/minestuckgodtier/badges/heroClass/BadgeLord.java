package com.cibernet.minestuckgodtier.badges.heroClass;

import com.cibernet.minestuckgodtier.badges.MSGTBadges;
import com.cibernet.minestuckgodtier.capabilities.GodKeyStates;
import com.cibernet.minestuckgodtier.capabilities.MSGTCapabilities;
import com.cibernet.minestuckgodtier.capabilities.api.IBadgeEffects;
import com.cibernet.minestuckgodtier.client.particles.MSGTParticles;
import com.cibernet.minestuckgodtier.events.handlers.BadgeEventHandler;
import com.mraof.minestuck.potions.MSUPotions;
import com.mraof.minestuck.util.*;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class BadgeLord extends BadgeHeroClass
{
	public BadgeLord() {
		super(EnumClass.LORD, 7, 80);
	}

	@Override
	public boolean onBadgeTick(World world, EntityPlayer player, IBadgeEffects badgeEffects, GodKeyStates.KeyState state, int time)
	{
		Title title = MinestuckPlayerData.getTitle(IdentifierHandler.encode(player));
		boolean isOverlord = player.getCapability(MSGTCapabilities.GOD_TIER_DATA, null).isBadgeActive(MSGTBadges.BADGE_OVERLORD);

		int chargeTime = isOverlord ? 9 : 18;
		int energy = isOverlord ? 6 : 12;

		if(title == null || state == GodKeyStates.KeyState.NONE || time > chargeTime)
			return false;

		if(!player.isCreative() && player.getFoodStats().getFoodLevel() < energy)
		{
			player.sendStatusMessage(new TextComponentTranslation("status.tooExhausted"), true);
			return false;
		}

		badgeEffects.startPowerParticles(getClass(), time > chargeTime-3 ? MSGTParticles.ParticleType.BURST : MSGTParticles.ParticleType.AURA, EnumClass.LORD, 20);

		if(time >= chargeTime)
		{
			for(EntityLivingBase target : world.getEntitiesWithinAABB(EntityLivingBase.class, player.getEntityBoundingBox().grow(48), (entity) -> entity != player))
			{
				if(target instanceof EntityPlayer && target.getDistance(player) >= 6)
				{
					int targetKarma = target.getCapability(MSGTCapabilities.GOD_TIER_DATA, null).getTotalKarma();
					if(targetKarma != 0 && Math.signum(targetKarma) == Math.signum(player.getCapability(MSGTCapabilities.GOD_TIER_DATA, null).getTotalKarma()))
						continue;
				}
				else if(!(target instanceof IMob)) continue;



				PotionEffect effect = BadgeEventHandler.NEGATIVE_EFFECTS.get(title.getHeroAspect());

				if(isOverlord && (title.getHeroAspect() == EnumAspect.MIND || title.getHeroAspect() == EnumAspect.SPACE))
				{
					target.addPotionEffect(new PotionEffect(title.getHeroAspect() == EnumAspect.MIND ? MobEffects.NAUSEA : MobEffects.BLINDNESS, 200, 0));
					target.addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 100, 1));
					target.addPotionEffect(new PotionEffect(MSUPotions.EARTHBOUND, 100, 0));
				}

				target.addPotionEffect(new PotionEffect(effect.getPotion(), effect.getDuration()*2 * (isOverlord ? 2 : 1), (int) (effect.getAmplifier()*1.5f * (isOverlord ? 2 : 1))));
				target.getCapability(MSGTCapabilities.BADGE_EFFECTS, null).oneshotPowerParticles(MSGTParticles.ParticleType.AURA, EnumClass.LORD, 10);
			}
			if (!player.isCreative())
				player.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel() - energy);
		}

		return true;
	}
}