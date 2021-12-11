package com.cibernet.minestuckgodtier.badges.heroAspect;

import com.cibernet.minestuckgodtier.capabilities.GodKeyStates;
import com.cibernet.minestuckgodtier.capabilities.api.IBadgeEffects;
import com.cibernet.minestuckgodtier.client.particles.MSGTParticles;
import com.cibernet.minestuckgodtier.network.MSGTChannelHandler;
import com.cibernet.minestuckgodtier.network.MSGTPacket;
import com.cibernet.minestuckgodtier.util.EnumRole;
import com.cibernet.minestuckgodtier.util.MSGTUtils;
import com.mraof.minestuck.util.EnumAspect;
import com.mraof.minestuck.util.Teleport;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class BadgeActiveSpace extends BadgeHeroAspect
{
	public BadgeActiveSpace()
	{
		super(EnumAspect.SPACE, EnumRole.ACTIVE, EnumAspect.BREATH);
	}

	@Override
	public boolean onBadgeTick(World world, EntityPlayer player, IBadgeEffects badgeEffects, GodKeyStates.KeyState state, int time)
	{
		if(state != GodKeyStates.KeyState.PRESS || time != 0)
			return false;

		if(!player.isCreative() && player.getFoodStats().getFoodLevel() < 1)
		{
			player.sendStatusMessage(new TextComponentTranslation("status.tooExhausted"), true);
			return false;
		}

		int reach = player.getFoodStats().getFoodLevel() * 2;
		RayTraceResult target = MSGTUtils.getMouseOver(player, reach);

		if(target == null)
		{
			player.sendStatusMessage(new TextComponentTranslation("status.badgeError"), true);
			return false;
		}

		BlockPos pos = target.entityHit == null ? target.getBlockPos() == null ? player.getPosition() : target.getBlockPos() : target.entityHit.getPosition();

		if(target.entityHit != null)
		{
			player.rotationYaw = target.entityHit.getRotationYawHead();
			player.prevRotationYaw = target.entityHit.getRotationYawHead();

			pos = pos.offset(EnumFacing.fromAngle(target.entityHit.getRotationYawHead()).getOpposite(), (int)Math.ceil(target.entityHit.width/2d));
		}
		if(target.sideHit != null)
			pos = pos.offset(target.sideHit, target.sideHit == EnumFacing.DOWN ? 2 : 1);

		double distance = player.getDistance(pos.getX(), pos.getY(), pos.getZ());

		if(!player.isCreative())
			player.getFoodStats().setFoodLevel((int) Math.max(0, player.getFoodStats().getFoodLevel()-Math.floor(distance)));


		badgeEffects.startPowerParticles(getClass(), MSGTParticles.ParticleType.AURA, EnumAspect.SPACE, 10);
		MSGTChannelHandler.sendToTrackingAndSelf(MSGTPacket.makePacket(MSGTPacket.Type.SEND_PARTICLE, MSGTParticles.ParticleType.AURA, 0x4BEC13, 10, player.posX, player.posY, player.posZ), player);

		Teleport.localTeleport(player, null, pos.getX()+0.5, pos.getY(), pos.getZ()+0.5);

		return true;
	}
}
