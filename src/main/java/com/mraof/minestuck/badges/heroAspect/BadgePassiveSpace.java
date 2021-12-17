package com.mraof.minestuck.badges.heroAspect;

import com.mraof.minestuck.capabilities.MinestuckCapabilities;
import com.mraof.minestuck.capabilities.api.IBadgeEffects;
import com.mraof.minestuck.capabilities.caps.GodKeyStates;
import com.mraof.minestuck.client.particles.MinestuckParticles;
import com.mraof.minestuck.network.MinestuckChannelHandler;
import com.mraof.minestuck.network.MinestuckPacket;
import com.mraof.minestuck.util.EnumAspect;
import com.mraof.minestuck.util.EnumRole;
import com.mraof.minestuck.util.MSGTUtils;
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
			MinestuckChannelHandler.sendToPlayer(MinestuckPacket.makePacket(MinestuckPacket.Type.SEND_PARTICLE, MinestuckParticles.ParticleType.AURA, 0x4BEC13, 3, badgeEffects.getWarpPoint().x, badgeEffects.getWarpPoint().y, badgeEffects.getWarpPoint().z), player);

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

			EntityLivingBase target = MSGTUtils.getTargetEntity(player);
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
							MinestuckChannelHandler.sendToTrackingAndSelf(MinestuckPacket.makePacket(MinestuckPacket.Type.SEND_PARTICLE, MinestuckParticles.ParticleType.BURST, 0x4BEC13, 5, attemptX, attemptY, attemptZ), target);
							break;
						}
					}
				}
				else
				{
					Vec3d warpPoint = badgeEffects.getWarpPoint();
					Teleport.teleportEntity(target, badgeEffects.getWarpPointDim(), null, warpPoint.x, warpPoint.y, warpPoint.z);

					target.getCapability(MinestuckCapabilities.BADGE_EFFECTS, null).oneshotPowerParticles(MinestuckParticles.ParticleType.BURST, EnumAspect.SPACE, 5);
					MinestuckChannelHandler.sendToDimension(MinestuckPacket.makePacket(MinestuckPacket.Type.SEND_PARTICLE, MinestuckParticles.ParticleType.BURST, 0x4BEC13, 5, warpPoint.x, warpPoint.y, warpPoint.z), badgeEffects.getWarpPointDim()); // leaving this as dim bc dims
				}

				if (!player.isCreative())
					player.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel() - 6);
			}

			badgeEffects.startPowerParticles(getClass(), MinestuckParticles.ParticleType.BURST, EnumAspect.SPACE, target != null ? 10 : 3);
		}

		return true;
	}

}
