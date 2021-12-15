package com.mraof.minestuck.badges.heroClass;

import com.mraof.minestuck.capabilities.MinestuckCapabilities;
import com.mraof.minestuck.capabilities.api.IBadgeEffects;
import com.mraof.minestuck.capabilities.caps.GodKeyStates;
import com.mraof.minestuck.client.particles.MSGTParticles;
import com.mraof.minestuck.potions.MinestuckPotions;
import com.mraof.minestuck.util.EnumClass;
import com.mraof.minestuck.util.MSGTUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import java.util.Collections;

public class BadgeWitch extends BadgeHeroClass
{
	public BadgeWitch()
	{
		super(EnumClass.WITCH);
	}

	@Override
	public boolean onBadgeTick(World world, EntityPlayer player, IBadgeEffects badgeEffects, GodKeyStates.KeyState state, int time)
	{
		if (state == GodKeyStates.KeyState.NONE)
			return false;

		if (!player.isCreative() && player.getFoodStats().getFoodLevel() < 8) {
			if (state == GodKeyStates.KeyState.HELD)
				player.sendStatusMessage(new TextComponentTranslation("status.tooExhausted"), true);
			return false;
		}

		if (state != GodKeyStates.KeyState.RELEASED)
		{
			badgeEffects.startPowerParticles(getClass(), MSGTParticles.ParticleType.AURA, EnumClass.WITCH, time > 20 ? 5 : 1);
			return true;
		}

		if(time < 20)
			return false;

		EntityLivingBase target = MSGTUtils.getTargetEntity(player);

		if (target == null)
			return false;

		target.getCapability(MinestuckCapabilities.BADGE_EFFECTS, null).oneshotPowerParticles(MSGTParticles.ParticleType.AURA, EnumClass.WITCH, 20);
		PotionEffect effect = new PotionEffect(MinestuckPotions.GOD_TIER_LOCK, 800, 1);
		effect.setCurativeItems(Collections.emptyList());
		target.addPotionEffect(effect);
		if (!player.isCreative())
			player.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel() - 8);

		return false;
	}
}
