package com.mraof.minestuck.badges.heroAspect;

import com.mraof.minestuck.capabilities.MinestuckCapabilities;
import com.mraof.minestuck.capabilities.api.IBadgeEffects;
import com.mraof.minestuck.capabilities.caps.GodKeyStates;
import com.mraof.minestuck.client.particles.MinestuckParticles;
import com.mraof.minestuck.entity.ai.EntityAIMindflayerTarget;
import com.mraof.minestuck.network.MinestuckChannelHandler;
import com.mraof.minestuck.network.MinestuckPacket;
import com.mraof.minestuck.potions.MinestuckPotions;
import com.mraof.minestuck.util.EnumAspect;
import com.mraof.minestuck.util.EnumRole;
import com.mraof.minestuck.util.MSGTUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovementInput;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Mouse;

public class BadgeActiveMind extends BadgeHeroAspect
{
	public BadgeActiveMind()
	{
		super(EnumAspect.MIND, EnumRole.ACTIVE);
	}

	@Override
	public boolean onBadgeTick(World world, EntityPlayer player, IBadgeEffects badgeEffects, GodKeyStates.KeyState state, int time)
	{
		EntityLivingBase mfTarget = player.getCapability(MinestuckCapabilities.BADGE_EFFECTS, null).getMindflayerEntity();

		if (state == GodKeyStates.KeyState.PRESS)
		{
			if (mfTarget == null)
				mfTarget = setTarget(player);
			else
				mfTarget = unsetTarget(mfTarget);

			player.getCapability(MinestuckCapabilities.BADGE_EFFECTS, null).setMindflayerEntity(mfTarget);

			badgeEffects.oneshotPowerParticles(MinestuckParticles.ParticleType.BURST, EnumAspect.MIND, mfTarget != null ? 5 : 2);
		}

		if (mfTarget == null)
			return false;

		if(!player.isCreative() && player.getFoodStats().getFoodLevel() < 1)
		{
			player.sendStatusMessage(new TextComponentTranslation("status.tooExhausted"), true);
			player.getCapability(MinestuckCapabilities.BADGE_EFFECTS, null).setMindflayerEntity(mfTarget = unsetTarget(mfTarget));
			return false;
		}

		if (!player.isCreative() && time % 40 == 0)
			player.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel() - 1);

		badgeEffects.startPowerParticles(getClass(), MinestuckParticles.ParticleType.AURA, EnumAspect.MIND, 2);

		return true;
	}

	private static EntityLivingBase setTarget(EntityPlayer player)
	{
		EntityLivingBase mfTarget = MSGTUtils.getTargetEntity(player);

		if(mfTarget == null || mfTarget.isPotionActive(MinestuckPotions.MIND_FORTITUDE))
			return null;

		if (mfTarget instanceof EntityCreature)
		{
			EntityCreature target = (EntityCreature) mfTarget;
			target.tasks.addTask(2, new EntityAIMindflayerTarget(target, 1f));
		}
		else if(mfTarget instanceof EntityPlayer)
		{
			mfTarget.getCapability(MinestuckCapabilities.BADGE_EFFECTS, null).setMindflayedBy(player);
			MinestuckChannelHandler.sendToPlayer(MinestuckPacket.makePacket(MinestuckPacket.Type.SET_CURRENT_ITEM, player.inventory.currentItem), (EntityPlayer) mfTarget);
		}
		return mfTarget;
	}

	private static EntityLivingBase unsetTarget(EntityLivingBase target)
	{
		if (target instanceof EntityCreature)
			for (EntityAITasks.EntityAITaskEntry entry : ((EntityCreature) target).tasks.taskEntries)
				if (entry.action instanceof EntityAIMindflayerTarget)
					((EntityCreature) target).tasks.removeTask(entry.action);
				else;
		else
		{
			IBadgeEffects capability = target.getCapability(MinestuckCapabilities.BADGE_EFFECTS, null); // cast for safety reasons
			capability.unsetMovement();
			if(target instanceof EntityPlayer)
				capability.setMindflayedBy(null);
		}

		return null;
	}

	@SubscribeEvent
	public static void onLivingUpdate(LivingEvent.LivingUpdateEvent event)
	{
		if(event.getEntityLiving().isPotionActive(MinestuckPotions.MIND_FORTITUDE) && event.getEntityLiving().getCapability(MinestuckCapabilities.BADGE_EFFECTS, null).isMindflayed())
			event.getEntityLiving().getCapability(MinestuckCapabilities.BADGE_EFFECTS, null).getMindflayedBy().getCapability(MinestuckCapabilities.BADGE_EFFECTS, null).setMindflayerEntity(unsetTarget(event.getEntityLiving()));
	}

	@SubscribeEvent
	public static void onLoggedOut(PlayerEvent.PlayerLoggedOutEvent event)
	{
		IBadgeEffects cap = event.player.getCapability(MinestuckCapabilities.BADGE_EFFECTS, null);
		EntityLivingBase mfTarget = cap.getMindflayerEntity();
		if(mfTarget != null)
			cap.setMindflayerEntity(unsetTarget(mfTarget));

		EntityLivingBase mfBy = cap.getMindflayedBy();
		if(mfBy != null)
			mfBy.getCapability(MinestuckCapabilities.BADGE_EFFECTS, null).setMindflayerEntity(unsetTarget(event.player));
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public static void onMouse(MouseEvent event)
	{
		if (Minecraft.getMinecraft().inGameHasFocus && Minecraft.getMinecraft().player != null && Minecraft.getMinecraft().player.getCapability(MinestuckCapabilities.BADGE_EFFECTS, null).isMindflayed())
			Mouse.setCursorPosition(0,0);
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public static void onRenderTick(TickEvent.RenderTickEvent event)
	{
		EntityPlayer player = Minecraft.getMinecraft().player;

		if(player == null)
			return;

		IBadgeEffects cap = player.getCapability(MinestuckCapabilities.BADGE_EFFECTS, null);

		if(cap.isMindflayed())
		{
			EntityLivingBase mindflayedBy = cap.getMindflayedBy();
			player.turn((mindflayedBy.rotationYaw-player.rotationYaw)*2f, (player.rotationPitch-mindflayedBy.rotationPitch)*2f);
		}
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public static void onInputUpdate(InputUpdateEvent event)
	{
		MovementInput input = event.getMovementInput();
		IBadgeEffects capability = event.getEntityPlayer().getCapability(MinestuckCapabilities.BADGE_EFFECTS, null);

		EntityLivingBase mfTarget = capability.getMindflayerEntity();
		if (mfTarget != null)
		{
			Vec3d movement = new Vec3d(input.moveStrafe, 0, input.moveForward).rotateYaw(-event.getEntityPlayer().rotationYawHead * 0.017453292f);

			MinestuckChannelHandler.sendToServer(MinestuckPacket.makePacket(MinestuckPacket.Type.MINDFLAYER_MOVEMENT_INPUT, (float) movement.x, (float) movement.z, input.jump, input.sneak, event.getEntityPlayer().inventory.currentItem));

			input.moveStrafe = 0;
			input.moveForward = 0;
			input.jump = false;
			input.sneak = false;
		}

		boolean hasMovement = capability.hasMovement();
		if (hasMovement || capability.isMindflayed())
		{
			Vec3d movement = new Vec3d(0,0,0);

			if(hasMovement)
				movement = new Vec3d(capability.getMoveStrafe(), 0, capability.getMoveForward()).rotateYaw(event.getEntityPlayer().rotationYawHead * 0.017453292f);

			input.moveStrafe = hasMovement ? (float) movement.x : 0;
			input.moveForward = hasMovement ? (float) movement.z : 0;
			input.jump = hasMovement && capability.getJump();
			input.sneak = hasMovement && capability.getSneak();
		}

	}

	public static class IsMindflayed {}
}
