package com.cibernet.minestuckgodtier.badges.heroAspectUtil;

import com.cibernet.minestuckgodtier.capabilities.GodKeyStates;
import com.cibernet.minestuckgodtier.capabilities.MSGTCapabilities;
import com.cibernet.minestuckgodtier.capabilities.api.IBadgeEffects;
import com.cibernet.minestuckgodtier.client.particles.MSGTParticles;
import com.cibernet.minestuckgodtier.network.MSGTChannelHandler;
import com.cibernet.minestuckgodtier.network.MSGTPacket;
import com.cibernet.minestuckgodtier.potions.MSGTPotions;
import com.cibernet.minestuckuniverse.potions.MSUPotions;
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
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;

public class BadgeUtilBreath extends BadgeHeroAspectUtil
{

	public BadgeUtilBreath() {
		super(EnumAspect.BREATH, new ItemStack(Items.FEATHER, 200));
	}

	@Override
	public boolean canUse(World world, EntityPlayer player) {
		return !player.isPotionActive(MSUPotions.EARTHBOUND) && super.canUse(world, player) && !player.getCapability(MSGTCapabilities.BADGE_EFFECTS, null).isMindflayed();
	}

	@Override
	public boolean onBadgeTick(World world, EntityPlayer player, IBadgeEffects badgeEffects, GodKeyStates.KeyState state, int time)
	{
		if(state != GodKeyStates.KeyState.HELD)
		{
			badgeEffects.setDoingWimdyThing(false);
			return false;
		}

		if(!player.isCreative() && player.getFoodStats().getFoodLevel() < 1)
        {
	        badgeEffects.setDoingWimdyThing(false);
            player.sendStatusMessage(new TextComponentTranslation("status.tooExhausted"), true);
            return false;
        }

		badgeEffects.setDoingWimdyThing(true);

		badgeEffects.startPowerParticles(getClass(), MSGTParticles.ParticleType.AURA, EnumAspect.BREATH, 10);
		MSGTChannelHandler.sendToTrackingAndSelf(MSGTPacket.makePacket(MSGTPacket.Type.SEND_PARTICLE, MSGTParticles.ParticleType.AURA, 0x47E2FA, 5, player.posX, player.posY+1, player.posZ), player);
		MSGTChannelHandler.sendToTrackingAndSelf(MSGTPacket.makePacket(MSGTPacket.Type.SEND_PARTICLE, MSGTParticles.ParticleType.AURA, 0x4379E6, 5, player.posX, player.posY+1, player.posZ), player);

		if(time % 20 == 1 && !player.isCreative())
			player.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel()-1);

		return true;
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public static void onLivingRender(RenderLivingEvent.Pre event)
	{
		if(!(event.getEntity() instanceof EntityPlayer))
			return;

		if(event.getEntity().getCapability(MSGTCapabilities.BADGE_EFFECTS, null).isDoingWimdyThing())
			event.setCanceled(true);
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public static void renderHand(RenderHandEvent event)
	{
		if(Minecraft.getMinecraft().player.getCapability(MSGTCapabilities.BADGE_EFFECTS, null).isDoingWimdyThing())
			event.setCanceled(true);
	}

	@SubscribeEvent
	public static void onPlayerCollision(GetCollisionBoxesEvent event)
	{
		if(!(event.getEntity() instanceof EntityPlayer))
			return;

		if(event.getEntity().getCapability(MSGTCapabilities.BADGE_EFFECTS, null).isDoingWimdyThing())
			for(AxisAlignedBB aabb : new ArrayList<>(event.getCollisionBoxesList()))
				if(Math.abs(aabb.maxX - aabb.minX) < 1 || Math.abs(aabb.maxY - aabb.minY) < 1 || Math.abs(aabb.maxZ - aabb.minZ) < 1)
					event.getCollisionBoxesList().remove(aabb);
	}

	@SubscribeEvent
	public static void onPlayerTick(TickEvent.PlayerTickEvent event)
	{
		if(event.player.getCapability(MSGTCapabilities.BADGE_EFFECTS, null).isDoingWimdyThing())
			event.player.distanceWalkedModified = 0;
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void onPlayerPushOutOfBlocks(PlayerSPPushOutOfBlocksEvent event)
	{
		if(event.getEntityPlayer() == null)
			return;

		if(event.getEntity().getCapability(MSGTCapabilities.BADGE_EFFECTS, null).isDoingWimdyThing())
			event.getEntityPlayer().moveRelative(0, -MathHelper.sin(event.getEntityPlayer().rotationPitch * 0.017453292F), MathHelper.cos(event.getEntityPlayer().rotationPitch * 0.017453292F), 0.4f);
	}
}
