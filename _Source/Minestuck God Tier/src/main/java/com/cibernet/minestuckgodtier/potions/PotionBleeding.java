package com.cibernet.minestuckgodtier.potions;

import com.cibernet.minestuckgodtier.MinestuckGodTier;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;

public class PotionBleeding extends MSGTPotionBase
{
	public static final DamageSource DAMAGE_SOURCE = new DamageSource(MinestuckGodTier.MODID+".bleeding").setDamageBypassesArmor();

	protected PotionBleeding(boolean isBadEffectIn, int liquidColorIn, String name)
	{
		super(isBadEffectIn, liquidColorIn, name);
	}

	@Override
	public boolean isReady(int duration, int amplifier)
	{
		int timeBetweenHits = 40;

		return duration % timeBetweenHits == 0;
	}

	@Override
	public void performEffect(EntityLivingBase entityLivingBaseIn, int amplifier)
	{
		entityLivingBaseIn.attackEntityFrom(DAMAGE_SOURCE, amplifier+1);
	}
}
