package com.mraof.minestuck.potions;

import com.mraof.minestuck.util.MSGTUtils;
import com.mraof.minestuck.capabilities.MinestuckCapabilities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class PotionTimeStop extends PotionMouseSensitivityAdjusterBase
{
	private static final Random rand = new Random(); // used to keep spawning particles

	protected PotionTimeStop(String name, boolean isBadEffectIn, int liquidColorIn)
	{
		super(name, isBadEffectIn, liquidColorIn);
	}

	@Override
	public void performEffect(EntityLivingBase entityLivingBaseIn, int amplifier)
	{
		if (entityLivingBaseIn.world.isRemote)
			setMouseSensitivity(entityLivingBaseIn);
	}

	@Override
	public boolean isReady(int duration, int amplifier)
	{
		return true; // should always do this so it has priority
	}

	@Override
	protected float getNewMouseSensitivity(float currentSensitivity)
	{
		return -1f/3f;
	}

	@SubscribeEvent
	public static void onLivingUpdate(LivingEvent.LivingUpdateEvent event)
	{
		EntityLivingBase entity = event.getEntityLiving();
		if (entity.world.isRemote ?
				entity.getCapability(MinestuckCapabilities.BADGE_EFFECTS, null).isTimeStopped() :
				entity.isPotionActive(MinestuckPotions.TIME_STOP)) // Usually I would use capabilities, but we need this instead to update it
		{
			if (!entity.world.isRemote || MSGTUtils.isClientPlayer(entity))
				event.setCanceled(true);

			if (entity.hurtTime > 0)
				--entity.hurtTime;
			if (entity.hurtResistantTime > 0 && !(entity instanceof EntityPlayerMP))
				--entity.hurtResistantTime;

			if (entity.world.isRemote)
			{
				int color = MinestuckPotions.TIME_STOP.getLiquidColor();
				if ((entity.isInvisible() ? rand.nextInt(15) == 0 : rand.nextBoolean()) && color > 0)
				{
					double d0 = (double) (color >> 16 & 255) / 255.0D;
					double d1 = (double) (color >> 8 & 255) / 255.0D;
					double d2 = (double) (color >> 0 & 255) / 255.0D;
					entity.world.spawnParticle(EnumParticleTypes.SPELL_MOB, entity.posX + (rand.nextDouble() - 0.5D) * (double) entity.width, entity.posY + rand.nextDouble() * (double) entity.height, entity.posZ + (rand.nextDouble() - 0.5D) * (double) entity.width, d0, d1, d2);
				}
			}
			
			if (!entity.world.isRemote || (MSGTUtils.isClientPlayer(entity) && entity.isPotionActive(MinestuckPotions.TIME_STOP)))
			{
				PotionEffect potionEffect = entity.getActivePotionEffect(MinestuckPotions.TIME_STOP);
				if (!potionEffect.onUpdate(entity) && !entity.world.isRemote)
					if (!net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.entity.living.PotionEvent.PotionExpiryEvent(entity, potionEffect)))
						entity.removePotionEffect(MinestuckPotions.TIME_STOP);
			}

		}
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public static void onGuiOpen(GuiOpenEvent event)
	{
		// I hate that I have to put this here but the assignment screws with SideOnly
		Class<? extends GuiScreen>[] TIME_STOP_SAFE_GUIS = new Class[] { GuiMainMenu.class, GuiIngameMenu.class, GuiGameOver.class, GuiDisconnected.class, GuiErrorScreen.class, GuiMemoryErrorScreen.class, GuiConfirmOpenLink.class, GuiMultiplayer.class };

		if (Minecraft.getMinecraft().player != null && Minecraft.getMinecraft().player.getCapability(MinestuckCapabilities.BADGE_EFFECTS, null).isTimeStopped())
		{
			if (event.getGui() == null)
				return;
			for (Class<? extends GuiScreen> safeGui : TIME_STOP_SAFE_GUIS)
				if (safeGui.isAssignableFrom(event.getGui().getClass()))
					return;
			if (event.getGui() instanceof GuiChat && Minecraft.getMinecraft().player.canUseCommand(2, ""))
				return;
			event.setCanceled(true);
		}
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public static void onMouse(MouseEvent event)
	{
		if (Minecraft.getMinecraft().player != null && Minecraft.getMinecraft().player.getCapability(MinestuckCapabilities.BADGE_EFFECTS, null).isTimeStopped())
			event.setCanceled(true);
		// Should probably stop number keys + drop from happening
	}

	@Override
	public void applyAttributesModifiersToEntity(EntityLivingBase entityLivingBaseIn, AbstractAttributeMap attributeMapIn, int amplifier)
	{
		super.applyAttributesModifiersToEntity(entityLivingBaseIn, attributeMapIn, amplifier);
		entityLivingBaseIn.getCapability(MinestuckCapabilities.BADGE_EFFECTS, null).setTimeStopped(true);
	}

	@Override
	public void removeAttributesModifiersFromEntity(EntityLivingBase entityLivingBaseIn, AbstractAttributeMap attributeMapIn, int amplifier)
	{
		super.removeAttributesModifiersFromEntity(entityLivingBaseIn, attributeMapIn, amplifier);
		entityLivingBaseIn.getCapability(MinestuckCapabilities.BADGE_EFFECTS, null).setTimeStopped(false);
	}
}
