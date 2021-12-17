package com.mraof.minestuck.badges.heroClass;

import com.mraof.minestuck.capabilities.caps.GodKeyStates;
import com.mraof.minestuck.capabilities.api.IBadgeEffects;
import com.mraof.minestuck.client.particles.MinestuckParticles;
import com.mraof.minestuck.util.MSGTUtils;
import com.mraof.minestuck.util.EnumClass;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class BadgeKnight extends BadgeHeroClass
{
	public BadgeKnight() {
		super(EnumClass.KNIGHT);
	}

	@Override
	public boolean onBadgeTick(World world, EntityPlayer player, IBadgeEffects badgeEffects, GodKeyStates.KeyState state, int time)
	{
		if(!player.isCreative() && player.getFoodStats().getFoodLevel() < 2)
		{
			player.sendStatusMessage(new TextComponentTranslation("status.tooExhausted"), true);
			return false;
		}

		if(state == GodKeyStates.KeyState.NONE || time >= 45)
			return false;

		if(time == 40)
		{
			if(!player.isCreative() && player.getFoodStats().getFoodLevel() < 6)
			{
				player.sendStatusMessage(new TextComponentTranslation("status.tooExhausted"), true);
				return false;
			}

			for(EntityLivingBase target : world.getEntitiesWithinAABB(EntityLivingBase.class, player.getEntityBoundingBox().grow(5,1,5), (entity) -> true))
				target.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 2400, 2));
			if (!player.isCreative())
				player.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel() - 6);
		}

		if(time > 36)
			badgeEffects.oneshotPowerParticles(MinestuckParticles.ParticleType.BURST, EnumClass.KNIGHT, 20);

		EntityLivingBase target = MSGTUtils.getTargetEntity(player);
		if(time <= 40)
			badgeEffects.startPowerParticles(getClass(), MinestuckParticles.ParticleType.AURA, EnumClass.KNIGHT, target == null ? 1 : 5);

		if(state == GodKeyStates.KeyState.PRESS && target instanceof EntityPlayer)
		{
			target.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 2400, 2));
			player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 2400, 2));
			if (!player.isCreative())
				player.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel() - 2);
		}

		return true;
	}
}