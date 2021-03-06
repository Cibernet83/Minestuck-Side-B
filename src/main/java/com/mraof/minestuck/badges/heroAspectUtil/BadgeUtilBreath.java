package com.mraof.minestuck.badges.heroAspectUtil;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.capabilities.MinestuckCapabilities;
import com.mraof.minestuck.capabilities.api.IBadgeEffects;
import com.mraof.minestuck.capabilities.caps.GodKeyStates;
import com.mraof.minestuck.client.particles.MinestuckParticles;
import com.mraof.minestuck.network.MinestuckNetwork;
import com.mraof.minestuck.network.message.MessageSendParticle;
import com.mraof.minestuck.potions.MinestuckPotions;
import com.mraof.minestuck.util.EnumAspect;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.client.event.PlayerSPPushOutOfBlocksEvent;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.world.GetCollisionBoxesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;

@Mod.EventBusSubscriber(modid = Minestuck.MODID)
public class BadgeUtilBreath extends BadgeHeroAspectUtil
{

	public BadgeUtilBreath()
	{
		super(EnumAspect.BREATH, new ItemStack(Items.FEATHER, 200));
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public static void onLivingRender(RenderLivingEvent.Pre event)
	{
		if (!(event.getEntity() instanceof EntityPlayer))
			return;

		if (event.getEntity().getCapability(MinestuckCapabilities.BADGE_EFFECTS, null).isDoingWimdyThing())
			event.setCanceled(true);
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public static void renderHand(RenderHandEvent event)
	{
		if (Minecraft.getMinecraft().player.getCapability(MinestuckCapabilities.BADGE_EFFECTS, null).isDoingWimdyThing())
			event.setCanceled(true);
	}

	@SubscribeEvent
	public static void onPlayerCollision(GetCollisionBoxesEvent event)
	{
		if (!(event.getEntity() instanceof EntityPlayer))
			return;

		if (event.getEntity().getCapability(MinestuckCapabilities.BADGE_EFFECTS, null).isDoingWimdyThing())
			for (AxisAlignedBB aabb : new ArrayList<>(event.getCollisionBoxesList()))
				if (Math.abs(aabb.maxX - aabb.minX) < 1 || Math.abs(aabb.maxY - aabb.minY) < 1 || Math.abs(aabb.maxZ - aabb.minZ) < 1)
					event.getCollisionBoxesList().remove(aabb);
	}

	@SubscribeEvent
	public static void onPlayerTick(TickEvent.PlayerTickEvent event)
	{
		if (event.player.getCapability(MinestuckCapabilities.BADGE_EFFECTS, null).isDoingWimdyThing())
			event.player.distanceWalkedModified = 0;
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void onPlayerPushOutOfBlocks(PlayerSPPushOutOfBlocksEvent event)
	{
		if (event.getEntityPlayer() == null)
			return;

		if (event.getEntity().getCapability(MinestuckCapabilities.BADGE_EFFECTS, null).isDoingWimdyThing())
			event.getEntityPlayer().moveRelative(0, -MathHelper.sin(event.getEntityPlayer().rotationPitch * 0.017453292F), MathHelper.cos(event.getEntityPlayer().rotationPitch * 0.017453292F), 0.4f);
	}

	@Override
	public boolean canUse(World world, EntityPlayer player)
	{
		return !player.isPotionActive(MinestuckPotions.EARTHBOUND) && super.canUse(world, player) && !player.getCapability(MinestuckCapabilities.BADGE_EFFECTS, null).isMindflayed();
	}

	@Override
	public boolean onBadgeTick(World world, EntityPlayer player, IBadgeEffects badgeEffects, GodKeyStates.KeyState state, int time)
	{
		if (state != GodKeyStates.KeyState.HELD)
		{
			badgeEffects.setDoingWimdyThing(false);
			return false;
		}

		if (!player.isCreative() && player.getFoodStats().getFoodLevel() < 1)
		{
			badgeEffects.setDoingWimdyThing(false);
			player.sendStatusMessage(new TextComponentTranslation("status.tooExhausted"), true);
			return false;
		}

		badgeEffects.setDoingWimdyThing(true);

		badgeEffects.startPowerParticles(getClass(), MinestuckParticles.ParticleType.AURA, EnumAspect.BREATH, 10);
		MinestuckNetwork.sendToTrackingAndSelf(new MessageSendParticle(MinestuckParticles.ParticleType.AURA, player.posX, player.posY + 1, player.posZ, 0x47E2FA, 5), player);
		MinestuckNetwork.sendToTrackingAndSelf(new MessageSendParticle(MinestuckParticles.ParticleType.AURA, player.posX, player.posY + 1, player.posZ, 0x4379E6, 5), player);

		if (time % 20 == 1 && !player.isCreative())
			player.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel() - 1);

		return true;
	}
}
