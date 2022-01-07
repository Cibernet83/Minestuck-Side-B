package com.mraof.minestuck.badges.heroAspect;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.badges.MinestuckBadges;
import com.mraof.minestuck.capabilities.MinestuckCapabilities;
import com.mraof.minestuck.capabilities.api.IBadgeEffects;
import com.mraof.minestuck.capabilities.api.IGodTierData;
import com.mraof.minestuck.capabilities.caps.GodKeyStates;
import com.mraof.minestuck.capabilities.caps.GodTierData;
import com.mraof.minestuck.client.particles.MinestuckParticles;
import com.mraof.minestuck.potions.MinestuckPotions;
import com.mraof.minestuck.util.EnumAspect;
import com.mraof.minestuck.util.EnumRole;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Collections;

@Mod.EventBusSubscriber(modid = Minestuck.MODID)
public class BadgeActiveBlood extends BadgeHeroAspect
{
	public BadgeActiveBlood()
	{
		super(EnumAspect.BLOOD, EnumRole.ACTIVE, EnumAspect.RAGE);
	}

	@SubscribeEvent
	public static void onLivingDamage(LivingDamageEvent event)
	{
		if (event.getEntity().world.isRemote)
			return;

		EntityPlayer sourcePlayer = event.getSource().getTrueSource() instanceof EntityPlayer ? (EntityPlayer) event.getSource().getTrueSource() : null;
		IGodTierData sourceData = sourcePlayer != null ? sourcePlayer.getCapability(MinestuckCapabilities.GOD_TIER_DATA, null) : null;

		if (event.getEntityLiving() != null && sourcePlayer != null && sourcePlayer.getCapability(MinestuckCapabilities.GOD_TIER_DATA, null).isBadgeActive(MinestuckBadges.BADGE_ACTIVE_BLOOD))
		{
			if (sourcePlayer.world.rand.nextFloat() < Math.min(0.8f, (sourcePlayer.getLuck() / 25f)))
			{
				event.getEntityLiving().getCapability(MinestuckCapabilities.BADGE_EFFECTS, null).oneshotPowerParticles(MinestuckParticles.ParticleType.AURA, EnumAspect.BLOOD, 5);
				PotionEffect effect = new PotionEffect(MinestuckPotions.BLEEDING, (int) ((int) sourcePlayer.getHealth() / sourcePlayer.getMaxHealth() * 600), (int) Math.min(5, sourceData.getSkillLevel(GodTierData.SkillType.ATTACK) / 5f));
				effect.setCurativeItems(Collections.emptyList());
				event.getEntityLiving().addPotionEffect(effect);
			}
		}
	}

	@Override
	public boolean onBadgeTick(World world, EntityPlayer player, IBadgeEffects badgeEffects, GodKeyStates.KeyState state, int time)
	{
		return false;
	}
}
