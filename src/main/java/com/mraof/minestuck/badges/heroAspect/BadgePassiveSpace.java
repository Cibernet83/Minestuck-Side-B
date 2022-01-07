package com.mraof.minestuck.badges.heroAspect;

import com.mraof.minestuck.capabilities.MinestuckCapabilities;
import com.mraof.minestuck.capabilities.api.IBadgeEffects;
import com.mraof.minestuck.capabilities.caps.GodKeyStates;
import com.mraof.minestuck.client.particles.MinestuckParticles;
import com.mraof.minestuck.network.MinestuckNetwork;
import com.mraof.minestuck.network.message.MessageSendParticle;
import com.mraof.minestuck.util.EnumAspect;
import com.mraof.minestuck.util.EnumRole;
import com.mraof.minestuck.util.MinestuckUtils;
import com.mraof.minestuck.util.Teleport;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class BadgePassiveSpace extends BadgeHeroAspect
{
	private static int RANGE = 20;

	public BadgePassiveSpace()
	{
		super(EnumAspect.SPACE, EnumRole.PASSIVE, EnumAspect.MIND);
	}

	@Override
	public boolean onBadgeTick(World world, EntityPlayer player, IBadgeEffects badgeEffects, GodKeyStates.KeyState state, int time)
	{
		if(player.isSneaking() && badgeEffects.hasWarpPoint() && badgeEffects.getWarpPointDim() == player.world.provider.getDimension())
			MinestuckNetwork.sendTo(new MessageSendParticle(MinestuckParticles.ParticleType.AURA, badgeEffects.getWarpPoint().x, badgeEffects.getWarpPoint().y, badgeEffects.getWarpPoint().z, 0x4BEC13, 3), player);

		if (state != GodKeyStates.KeyState.PRESS)
			return false;

		if (player.isSneaking())
		{
			if (!badgeEffects.hasWarpPoint())
			{
				badgeEffects.setWarpPoint(player.getPositionVector(), world.provider.getDimension());
				player.sendStatusMessage(new TextComponentTranslation("status.spatialWarp.setPoint", (int) player.posX, (int) player.posY, (int) player.posZ), true);
			}
			else
			{
				badgeEffects.unsetWarpPoint();
				player.sendStatusMessage(new TextComponentTranslation("status.spatialWarp.clearPoint"), true);
			}

			badgeEffects.startPowerParticles(getClass(), MinestuckParticles.ParticleType.BURST, EnumAspect.SPACE, badgeEffects.hasWarpPoint() ? 10 : 3);
		}
		else
		{
			if(!player.isCreative() && player.getFoodStats().getFoodLevel() < 6)
			{
				player.sendStatusMessage(new TextComponentTranslation("status.tooExhausted"), true);
				return false;
			}

			EntityLivingBase target = MinestuckUtils.getTargetEntity(player);
			if (target != null)
			{
				if (!badgeEffects.hasWarpPoint())
				{
					if (target.isRiding())
						target.dismountRidingEntity();

					for (int i = 0; i < 16; i++)
					{
						double attemptX = player.posX + (target.getRNG().nextDouble() - 0.5D) * (double) (RANGE * 2);
						double attemptY = MathHelper.clamp(player.posY + (target.getRNG().nextDouble() - 0.5D) * (double) (RANGE * 2), 0d, (double)(world.getActualHeight() - 1));
						double attemptZ = player.posZ + (target.getRNG().nextDouble() - 0.5D) * (double) (RANGE * 2);

						if (target.attemptTeleport(attemptX, attemptY, attemptZ))
						{
							target.getCapability(MinestuckCapabilities.BADGE_EFFECTS, null).oneshotPowerParticles(MinestuckParticles.ParticleType.BURST, EnumAspect.SPACE, 5);
							if (target instanceof EntityPlayer)
								MinestuckNetwork.sendToTrackingAndSelf(new MessageSendParticle(MinestuckParticles.ParticleType.BURST, attemptX, attemptY, attemptZ, 0x4BEC13, 5), (EntityPlayer) target);
							else
								MinestuckNetwork.sendToTracking(new MessageSendParticle(MinestuckParticles.ParticleType.BURST, attemptX, attemptY, attemptZ, 0x4BEC13, 5), target);
							break;
						}
					}
				}
				else
				{
					Vec3d warpPoint = badgeEffects.getWarpPoint();
					Teleport.teleportEntity(target, badgeEffects.getWarpPointDim(), null, warpPoint.x, warpPoint.y, warpPoint.z);

					target.getCapability(MinestuckCapabilities.BADGE_EFFECTS, null).oneshotPowerParticles(MinestuckParticles.ParticleType.BURST, EnumAspect.SPACE, 5);
					MinestuckNetwork.sendToDimension(new MessageSendParticle(MinestuckParticles.ParticleType.BURST, warpPoint.x, warpPoint.y, warpPoint.z, 0x4BEC13, 5), badgeEffects.getWarpPointDim()); // leaving this as dim bc dims
				}

				if (!player.isCreative())
					player.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel() - 6);
			}

			badgeEffects.startPowerParticles(getClass(), MinestuckParticles.ParticleType.BURST, EnumAspect.SPACE, target != null ? 10 : 3);
		}

		return true;
	}

}
