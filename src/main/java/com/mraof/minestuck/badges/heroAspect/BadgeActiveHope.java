package com.mraof.minestuck.badges.heroAspect;

import com.mraof.minestuck.capabilities.MinestuckCapabilities;
import com.mraof.minestuck.capabilities.api.IBadgeEffects;
import com.mraof.minestuck.capabilities.caps.GodKeyStates;
import com.mraof.minestuck.client.particles.MinestuckParticles;
import com.mraof.minestuck.entity.EntityHopeGolem;
import com.mraof.minestuck.util.EnumAspect;
import com.mraof.minestuck.util.EnumRole;
import com.mraof.minestuck.util.MinestuckUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class BadgeActiveHope extends BadgeHeroAspect
{
	public BadgeActiveHope()
	{
		super(EnumAspect.HOPE, EnumRole.ACTIVE, EnumAspect.SPACE);
	}

	@Override
	public boolean onBadgeTick(World world, EntityPlayer player, IBadgeEffects badgeEffects, GodKeyStates.KeyState state, int time)
	{
		if(state != GodKeyStates.KeyState.HELD)
			return false;

		if(!player.isCreative() && player.getFoodStats().getFoodLevel() < 1)
		{
			player.sendStatusMessage(new TextComponentTranslation("status.tooExhausted"), true);
			return false;
		}

		EntityLivingBase target = MinestuckUtils.getTargetEntity(player);

		if(target instanceof EntityHopeGolem && ((EntityHopeGolem) target).getOwner() == player)
		{
			((EntityHopeGolem) target).setHopeTicks(((EntityHopeGolem) target).getHopeTicks() + Math.max(10-(int) (player.getHealth()/player.getMaxHealth()*10), 1)+10);

			badgeEffects.startPowerParticles(getClass(), MinestuckParticles.ParticleType.AURA, EnumAspect.HOPE, 4);
			target.getCapability(MinestuckCapabilities.BADGE_EFFECTS, null).oneshotPowerParticles(MinestuckParticles.ParticleType.AURA, EnumAspect.HOPE, 10);

			if((player.ticksExisted % 10) == 0 && !player.isCreative())
				player.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel()-1);
		}

		else if(time <= 160)
		{
			badgeEffects.startPowerParticles(getClass(), MinestuckParticles.ParticleType.AURA, EnumAspect.HOPE, (int) ((float) time / 320f * 20));

			if (time == 160)
			{
				EntityHopeGolem golem = new EntityHopeGolem(world);
				golem.setPosition(player.posX + world.rand.nextDouble() * 10 - 5d, player.posY, player.posZ + world.rand.nextDouble() * 10 - 5d);
				golem.setCreatedBy(player);
				golem.getLookHelper().setLookPosition(player.posX, player.posY + (double) player.getEyeHeight(), player.posZ, (float) golem.getHorizontalFaceSpeed(), (float) golem.getVerticalFaceSpeed());
				world.spawnEntity(golem);
			}
			if ((player.ticksExisted % 10) == 0 && !player.isCreative())
				player.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel() - 1);
		}

		return true;
	}
}
