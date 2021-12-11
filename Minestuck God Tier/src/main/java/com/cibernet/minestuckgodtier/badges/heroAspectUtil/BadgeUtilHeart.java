package com.cibernet.minestuckgodtier.badges.heroAspectUtil;

import com.cibernet.minestuckgodtier.capabilities.GodKeyStates;
import com.cibernet.minestuckgodtier.capabilities.MSGTCapabilities;
import com.cibernet.minestuckgodtier.capabilities.api.IBadgeEffects;
import com.cibernet.minestuckgodtier.client.particles.MSGTParticles;
import com.cibernet.minestuckgodtier.potions.MSGTPotions;
import com.cibernet.minestuckgodtier.util.MSGTUtils;
import com.cibernet.minestuckgodtier.util.SoulData;
import com.mraof.minestuck.util.EnumAspect;
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
			badgeEffects.startPowerParticles(getClass(), MSGTParticles.ParticleType.AURA, EnumAspect.HEART, 2);
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

		PotionEffect effect = new PotionEffect(MSGTPotions.GOD_TIER_LOCK, 100);
		effect.setCurativeItems(Collections.emptyList());
		target.addPotionEffect(effect);

		if (!player.isCreative())
			player.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel() - 8);

		badgeEffects.startPowerParticles(getClass(), MSGTParticles.ParticleType.AURA, EnumAspect.HEART, 4);
		target.getCapability(MSGTCapabilities.BADGE_EFFECTS, null).oneshotPowerParticles(MSGTParticles.ParticleType.AURA, EnumAspect.HEART, 2);

		return true;
	}
}
