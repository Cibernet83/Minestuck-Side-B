package com.cibernet.minestuckgodtier.badges.heroAspect;

import com.cibernet.minestuckgodtier.badges.MSGTBadges;
import com.cibernet.minestuckgodtier.capabilities.GodKeyStates;
import com.cibernet.minestuckgodtier.capabilities.GodTierData;
import com.cibernet.minestuckgodtier.capabilities.MSGTCapabilities;
import com.cibernet.minestuckgodtier.capabilities.api.IBadgeEffects;
import com.cibernet.minestuckgodtier.capabilities.api.IGodTierData;
import com.cibernet.minestuckgodtier.client.particles.MSGTParticles;
import com.cibernet.minestuckgodtier.potions.MSGTPotions;
import com.cibernet.minestuckgodtier.util.EnumRole;
import com.mraof.minestuck.util.EnumAspect;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Collections;

public class BadgeActiveBlood extends BadgeHeroAspect
{
	public BadgeActiveBlood() {
		super(EnumAspect.BLOOD, EnumRole.ACTIVE, EnumAspect.RAGE);
	}

	@Override
	public boolean onBadgeTick(World world, EntityPlayer player, IBadgeEffects badgeEffects, GodKeyStates.KeyState state, int time) {
		return false;
	}

	@SubscribeEvent
	public static void onLivingDamage(LivingDamageEvent event)
	{
		if (event.getEntity().world.isRemote)
			return;

		EntityPlayer sourcePlayer = event.getSource().getTrueSource() instanceof EntityPlayer ? (EntityPlayer) event.getSource().getTrueSource() : null;
		IGodTierData sourceData = sourcePlayer != null ? sourcePlayer.getCapability(MSGTCapabilities.GOD_TIER_DATA, null) : null;

		if(event.getEntityLiving() != null && sourcePlayer != null && sourcePlayer.getCapability(MSGTCapabilities.GOD_TIER_DATA, null).isBadgeActive(MSGTBadges.BADGE_ACTIVE_BLOOD))
		{
			if(sourcePlayer.world.rand.nextFloat() < Math.min(0.8f, (sourcePlayer.getLuck()/25f)))
			{
				event.getEntityLiving().getCapability(MSGTCapabilities.BADGE_EFFECTS, null).oneshotPowerParticles(MSGTParticles.ParticleType.AURA, EnumAspect.BLOOD, 5);
				PotionEffect effect = new PotionEffect(MSGTPotions.BLEEDING, (int) ((int) sourcePlayer.getHealth()/sourcePlayer.getMaxHealth()*600), (int) Math.min(5, sourceData.getSkillLevel(GodTierData.SkillType.ATTACK)/5f));
				effect.setCurativeItems(Collections.emptyList());
				event.getEntityLiving().addPotionEffect(effect);
			}
		}
	}
}
