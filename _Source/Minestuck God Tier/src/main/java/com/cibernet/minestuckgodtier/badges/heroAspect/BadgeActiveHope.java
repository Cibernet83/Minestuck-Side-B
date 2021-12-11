package com.cibernet.minestuckgodtier.badges.heroAspect;

import com.cibernet.minestuckgodtier.capabilities.GodKeyStates;
import com.cibernet.minestuckgodtier.capabilities.MSGTCapabilities;
import com.cibernet.minestuckgodtier.capabilities.api.IBadgeEffects;
import com.cibernet.minestuckgodtier.client.particles.MSGTParticles;
import com.cibernet.minestuckgodtier.entities.EntityHopeGolem;
import com.cibernet.minestuckgodtier.util.EnumRole;
import com.cibernet.minestuckgodtier.util.MSGTUtils;
import com.mraof.minestuck.util.EnumAspect;
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

		EntityLivingBase target = MSGTUtils.getTargetEntity(player);

		if(target instanceof EntityHopeGolem && ((EntityHopeGolem) target).getOwner() == player)
		{
			((EntityHopeGolem) target).setHopeTicks(((EntityHopeGolem) target).getHopeTicks() + Math.max(10-(int) (player.getHealth()/player.getMaxHealth()*10), 1)+10);

			badgeEffects.startPowerParticles(getClass(), MSGTParticles.ParticleType.AURA, EnumAspect.HOPE, 4);
			target.getCapability(MSGTCapabilities.BADGE_EFFECTS, null).oneshotPowerParticles(MSGTParticles.ParticleType.AURA, EnumAspect.HOPE, 10);

			if((player.ticksExisted % 10) == 0 && !player.isCreative())
				player.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel()-1);
		}

		else if(time <= 160)
		{
			badgeEffects.startPowerParticles(getClass(), MSGTParticles.ParticleType.AURA, EnumAspect.HOPE, (int) ((float) time / 320f * 20));

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
