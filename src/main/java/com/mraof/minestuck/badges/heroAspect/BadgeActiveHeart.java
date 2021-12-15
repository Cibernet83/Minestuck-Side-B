package com.mraof.minestuck.badges.heroAspect;

import com.mraof.minestuck.badges.MinestuckBadges;
import com.mraof.minestuck.capabilities.MinestuckCapabilities;
import com.mraof.minestuck.capabilities.api.IBadgeEffects;
import com.mraof.minestuck.capabilities.api.IGodTierData;
import com.mraof.minestuck.capabilities.caps.GodKeyStates;
import com.mraof.minestuck.client.particles.MSGTParticles;
import com.mraof.minestuck.damage.CritDamageSource;
import com.mraof.minestuck.util.EnumAspect;
import com.mraof.minestuck.util.EnumRole;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BadgeActiveHeart extends BadgeHeroAspect
{
	public static final DamageSource DAMAGE_SOURCE = new CritDamageSource("perseveringSpirit").setGodproof().setDamageBypassesArmor();

	public BadgeActiveHeart () {
		super(EnumAspect.HEART, EnumRole.ACTIVE, EnumAspect.LIFE);
	}

	@Override
	public boolean onBadgeTick(World world, EntityPlayer player, IBadgeEffects badgeEffects, GodKeyStates.KeyState state, int time)
	{
		if(state != GodKeyStates.KeyState.HELD || time > 200)
			return false;

		if(!player.isCreative() && player.getFoodStats().getFoodLevel() < 1)
		{
			player.sendStatusMessage(new TextComponentTranslation("status.tooExhausted"), true);
			return false;
		}

		if(time == 200)
		{
			player.attackEntityFrom(DAMAGE_SOURCE, 20);
			return false;
		}

		player.setSprinting(false);

		if(!player.isCreative() && time % 30 == 0)
			player.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel() - 1);

		badgeEffects.startPowerParticles(getClass(), MSGTParticles.ParticleType.AURA, EnumAspect.HEART, 4);

		return true;
	}

	@SubscribeEvent
	public static void onLivingDamage(LivingDamageEvent event)
	{
		if (event.getEntity().world.isRemote)
			return;

		EntityPlayer targetPlayer = event.getEntityLiving() instanceof EntityPlayer ? (EntityPlayer) event.getEntityLiving() : null;
		IGodTierData targetData   = targetPlayer != null ? targetPlayer.getCapability(MinestuckCapabilities.GOD_TIER_DATA, null) : null;

		if (targetPlayer != null && targetData.isBadgeActive(MinestuckBadges.BADGE_ACTIVE_HEART) && targetPlayer.getCapability(MinestuckCapabilities.GOD_KEY_STATES, null).getKeyState(GodKeyStates.Key.ASPECT) == GodKeyStates.KeyState.HELD && targetPlayer.getCapability(MinestuckCapabilities.GOD_KEY_STATES, null).getKeyTime(GodKeyStates.Key.ASPECT) <= 200) {
			if (targetPlayer.getFoodStats().getFoodLevel() > event.getAmount())
			{
				targetPlayer.getCapability(MinestuckCapabilities.BADGE_EFFECTS, null).oneshotPowerParticles(MSGTParticles.ParticleType.AURA, EnumAspect.HEART, (int) event.getAmount());

				targetPlayer.heal(event.getAmount());
				targetPlayer.getFoodStats().setFoodLevel(targetPlayer.getFoodStats().getFoodLevel() - (int)event.getAmount());
				event.setAmount(0);
			}
			else if (targetPlayer.getFoodStats().getFoodLevel() > 1)
			{
				targetPlayer.getCapability(MinestuckCapabilities.BADGE_EFFECTS, null).oneshotPowerParticles(MSGTParticles.ParticleType.AURA, EnumAspect.HEART, targetPlayer.getFoodStats().getFoodLevel());

				targetPlayer.heal(targetPlayer.getFoodStats().getFoodLevel());
				targetPlayer.getFoodStats().setFoodLevel(0);
				event.setAmount(event.getAmount() - targetPlayer.getFoodStats().getFoodLevel());
			}
			else
			{
				targetPlayer.sendStatusMessage(new TextComponentTranslation("status.tooExhausted"), true);
			}
		}
	}
}
