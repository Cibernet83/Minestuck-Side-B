package com.mraof.minestuck.util;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.mraof.minestuck.capabilities.MinestuckCapabilities;
import com.mraof.minestuck.capabilities.api.IGodTierData;
import com.mraof.minestuck.network.MinestuckChannelHandler;
import com.mraof.minestuck.network.MinestuckPacket;
import com.mraof.minestuck.network.PacketPlayerData;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class MSGTUtils
{

	public static final int TARGET_REACH = 10;

	public static Vec3d getRGBVecFromHex(int hex)
	{
		float r = (float) ((hex & 16711680) >> 16) / 255f;
		float g = (float) ((hex & 65280) >> 8) / 255f;
		float b = (float) ((hex & 255) >> 0) / 255f;

		return new Vec3d(r, g, b);
	}

	public static RayTraceResult rayTraceBlocks(Entity entity, double blockReachDistance)
	{
		Vec3d vec3d = entity.getPositionEyes(1);
		Vec3d vec3d1 = entity.getLook(1);
		Vec3d vec3d2 = vec3d.addVector(vec3d1.x * blockReachDistance, vec3d1.y * blockReachDistance, vec3d1.z * blockReachDistance);
		return entity.world.rayTraceBlocks(vec3d, vec3d2, false, false, true);
	}

	public static RayTraceResult getMouseOver(EntityPlayer player, double blockReachDistance)
	{
		return getMouseOver(player.world, player, blockReachDistance);
	}

	public static RayTraceResult getMouseOver(World world, EntityPlayer player, double blockReachDistance)
	{
		if (world != null)
		{
			Entity pointedEntity = null;
			RayTraceResult objectMouseOver = rayTraceBlocks(player, blockReachDistance);
			Vec3d eyePos = player.getPositionEyes(1);
			double blockHitDistance = blockReachDistance;

			if (objectMouseOver != null)
				blockHitDistance = objectMouseOver.hitVec.distanceTo(eyePos);

			Vec3d look = player.getLook(1.0F);
			Vec3d lookPos = eyePos.addVector(look.x * blockReachDistance, look.y * blockReachDistance, look.z * blockReachDistance);
			Vec3d hitPos = null;
			List<Entity> entities = world.getEntitiesInAABBexcluding(player, player.getEntityBoundingBox().expand(look.x * blockReachDistance, look.y * blockReachDistance, look.z * blockReachDistance).grow(1.0D, 1.0D, 1.0D), Predicates.and(EntitySelectors.NOT_SPECTATING, (Predicate<Entity>) p_apply_1_ -> p_apply_1_ != null && p_apply_1_.canBeCollidedWith()));
			double entityHitDistance = blockHitDistance;

			for (Entity entity : entities) {
				AxisAlignedBB entityAABB = entity.getEntityBoundingBox().grow((double) entity.getCollisionBorderSize());
				RayTraceResult entityResult = entityAABB.calculateIntercept(eyePos, lookPos);

				if (entityAABB.contains(eyePos)) {
					if (entityHitDistance >= 0.0D) {
						pointedEntity = entity;
						hitPos = entityResult == null ? eyePos : entityResult.hitVec;
						entityHitDistance = 0.0D;
					}
				}
				else if (entityResult != null) {
					double eyeToHitDistance = eyePos.distanceTo(entityResult.hitVec);

					if (eyeToHitDistance < entityHitDistance || entityHitDistance == 0.0D) {
						if (entity.getLowestRidingEntity() == player.getLowestRidingEntity() && !entity.canRiderInteract()) {
							if (entityHitDistance == 0.0D) {
								pointedEntity = entity;
								hitPos = entityResult.hitVec;
							}
						} else {
							pointedEntity = entity;
							hitPos = entityResult.hitVec;
							entityHitDistance = eyeToHitDistance;
						}
					}
				}
			}

			if (pointedEntity != null && (entityHitDistance < blockHitDistance || objectMouseOver == null))
			{
				objectMouseOver = new RayTraceResult(pointedEntity, hitPos);
			}

			return objectMouseOver;
		}
		return null;
	}

	public static EntityLivingBase getTargetEntity(EntityPlayer player)
	{
		return getTargetEntity(player, 10);
	}

	public static EntityLivingBase getTargetEntity(EntityPlayer player, double blockReachDistance)
	{
		RayTraceResult rayTraceResult = getMouseOver(player, blockReachDistance);
		return (rayTraceResult != null && rayTraceResult.entityHit instanceof EntityLivingBase) ? (EntityLivingBase) rayTraceResult.entityHit : null;
	}

	public static BlockPos getTargetBlock(EntityPlayer player)
	{
		return getTargetBlock(player, 10);
	}

	public static BlockPos getTargetBlock(EntityPlayer player, double blockReachDistance)
	{
		RayTraceResult rayTraceResult = getMouseOver(player, blockReachDistance);
		return (rayTraceResult != null && rayTraceResult.typeOfHit == RayTraceResult.Type.BLOCK) ? rayTraceResult.getBlockPos() : null;
	}


	@SideOnly(Side.CLIENT)
	public static boolean isClientPlayer(Entity entity)
	{
		return Minecraft.getMinecraft().player != null && Minecraft.getMinecraft().player.equals(entity);
	}

	public static boolean resetGodTier(EntityPlayer player)
	{
		if(!(player instanceof FakePlayer))
		{
			if(!player.world.isRemote)
			{
				IGodTierData data = player.getCapability(MinestuckCapabilities.GOD_TIER_DATA, null);
				data.resetBadges();
				data.resetSkills(true);
				MinestuckPlayerData.getData(player).echeladder.setProgressEnabled(true);
				data.markForReset();
				data.update();
			}
		}
		return false;
	}

	public static boolean changeTitle(EntityPlayer player, EnumClass heroClass, EnumAspect aspect)
	{
		if(!(player instanceof FakePlayer))
		{
			MinestuckPlayerData.getData(player).title = new Title(heroClass, aspect);
			MinestuckChannelHandler.sendToPlayer(MinestuckPacket.makePacket(MinestuckPacket.Type.PLAYER_DATA, PacketPlayerData.TITLE, heroClass, aspect), player);
			IGodTierData data = player.getCapability(MinestuckCapabilities.GOD_TIER_DATA, null);
			if(!data.hasMasterControl())
				data.resetTitleBadges(true);
			return true;
		}
		return false;
	}

	public static void onResetGodTier(EntityPlayer player)
	{
		player.capabilities.allowFlying = player.isCreative() || player.isSpectator();
		if(!player.capabilities.allowFlying)
			player.capabilities.isFlying = false;
	}
}
