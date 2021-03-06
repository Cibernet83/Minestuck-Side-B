package com.mraof.minestuck.potions;

import com.mraof.minestuck.Minestuck;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.UUID;

@Mod.EventBusSubscriber(modid = Minestuck.MODID)
public class PotionComeback extends MSPotionBase
{

	public static final UUID STRENGTH_UUID = UUID.randomUUID();

	protected PotionComeback(String name, boolean isBadEffectIn, int liquidColorIn)
	{
		super(name, isBadEffectIn, liquidColorIn);
		registerPotionAttributeModifier(SharedMonsterAttributes.ATTACK_DAMAGE, STRENGTH_UUID.toString(), 3, 0);
	}

	@SubscribeEvent
	public static void onLivingDamage(LivingHurtEvent event)
	{
		EntityLivingBase entity = event.getEntityLiving();
		if (entity.isPotionActive(MinestuckPotions.GOD_TIER_COMEBACK) && event.getSource() != DamageSource.OUT_OF_WORLD)
			event.setAmount(event.getAmount() * (float) (25 - ((entity.getActivePotionEffect(MinestuckPotions.GOD_TIER_COMEBACK).getAmplifier() + 1) * 5)) / 25f);
	}

	@Override
	public void performEffect(EntityLivingBase entityLivingBaseIn, int amplifier)
	{
		if (entityLivingBaseIn.getHealth() < entityLivingBaseIn.getMaxHealth())
			entityLivingBaseIn.heal(1.0F);
	}

	@Override
	public boolean isReady(int duration, int amplifier)
	{
		if (duration < 60)
			return false;
		int i = 20 >> amplifier;
		if (i > 0)
			return duration % i == 0;
		return true;
	}

	public double getAttributeModifierAmount(int amplifier, AttributeModifier modifier)
	{
		return 3 * (double) (amplifier + 1);
	}
}
