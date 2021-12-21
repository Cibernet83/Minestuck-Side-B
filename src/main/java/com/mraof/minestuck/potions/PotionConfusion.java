package com.mraof.minestuck.potions;

import com.mraof.minestuck.Minestuck;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MovementInput;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber(modid = Minestuck.MODID)
public class PotionConfusion extends PotionMouseSensitivityAdjusterBase
{
	protected PotionConfusion(String name, boolean isBadEffectIn, int liquidColorIn) {
		super(name, isBadEffectIn, liquidColorIn);
	}

	@Override
	public void performEffect(EntityLivingBase entityLivingBaseIn, int amplifier)
	{

	}

	@Override
	protected float getNewMouseSensitivity(float currentSensitivity)
	{
		return -currentSensitivity - 2f/3f;
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public static void onGuiOpen(GuiOpenEvent event)
	{
		if (Minecraft.getMinecraft().player != null && Minecraft.getMinecraft().player.isPotionActive(MinestuckPotions.MIND_CONFUSION) && event.getGui() instanceof GuiOptions)
				event.setCanceled(true);
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public static void onInputUpdate(InputUpdateEvent event)
	{
		if (event.getEntityLiving().isPotionActive(MinestuckPotions.MIND_CONFUSION))
		{
			MovementInput input = event.getMovementInput();

			input.moveForward *= -1;
			input.moveStrafe *= -1;

			boolean wasJumpDown = input.jump;
			input.jump = input.sneak;
			input.sneak = wasJumpDown;
		}
	}
}
