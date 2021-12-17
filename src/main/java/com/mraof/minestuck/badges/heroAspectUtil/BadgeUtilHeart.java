package com.mraof.minestuck.badges.heroAspectUtil;

import com.mraof.minestuck.capabilities.MinestuckCapabilities;
import com.mraof.minestuck.capabilities.api.IBadgeEffects;
import com.mraof.minestuck.capabilities.caps.GodKeyStates;
import com.mraof.minestuck.client.particles.MinestuckParticles;
import com.mraof.minestuck.potions.MinestuckPotions;
import com.mraof.minestuck.util.EnumAspect;
import com.mraof.minestuck.util.MSGTUtils;
import com.mraof.minestuck.util.SoulData;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import java.util.Collections;

public class BadgeUtilHeart extends BadgeHeroAspectUtil
{
	public BadgeUtilHeart()
	{
		super(EnumAspect.HEART, new ItemStack(Items.ENDER_EYE, 200));
	}

	@Override
	public boolean onBadgeTick(World world, EntityPlayer player, IBadgeEffects badgeEffects, GodKeyStates.KeyState state, int time)
	{
		if (state != GodKeyStates.KeyState.HELD)
			return false;

		if (time < 60)
		{
			badgeEffects.startPowerParticles(getClass(), MinestuckParticles.ParticleType.AURA, EnumAspect.HEART, 2);
			return true;
		}

		EntityLivingBase target = MSGTUtils.getTargetEntity(player);
		if (time != 60 || !(target instanceof EntityPlayer))
			return false;

		if (!player.isCreative() && player.getFoodStats().getFoodLevel() < 8)
		{
			player.sendStatusMessage(new TextComponentTranslation("status.tooExhausted"), true);
			return false;
		}

		SoulData targetSoulData = new SoulData(target);
		SoulData playerSoulData = new SoulData(player);

		targetSoulData.apply(player);
		playerSoulData.apply(target);

		PotionEffect effect = new PotionEffect(MinestuckPotions.GOD_TIER_LOCK, 100);
		effect.setCurativeItems(Collections.emptyList());
		target.addPotionEffect(effect);

		if (!player.isCreative())
			player.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel() - 8);

		badgeEffects.startPowerParticles(getClass(), MinestuckParticles.ParticleType.AURA, EnumAspect.HEART, 4);
		target.getCapability(MinestuckCapabilities.BADGE_EFFECTS, null).oneshotPowerParticles(MinestuckParticles.ParticleType.AURA, EnumAspect.HEART, 2);

		return true;
	}
}
