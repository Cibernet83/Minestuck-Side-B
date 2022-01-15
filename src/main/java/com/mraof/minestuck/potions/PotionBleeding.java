package com.mraof.minestuck.potions;

import com.mraof.minestuck.Minestuck;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;

public class PotionBleeding extends MSPotionBase
{
	public static final DamageSource DAMAGE_SOURCE = new DamageSource(Minestuck.MODID + ".bleeding").setDamageBypassesArmor();

	protected PotionBleeding(String name, boolean isBadEffectIn, int liquidColorIn)
	{
		super(name, isBadEffectIn, liquidColorIn);
	}

	@Override
	public void performEffect(EntityLivingBase entityLivingBaseIn, int amplifier)
	{
		entityLivingBaseIn.attackEntityFrom(DAMAGE_SOURCE, amplifier + 1);
	}

	@Override
	public boolean isReady(int duration, int amplifier)
	{
		int timeBetweenHits = 40;

		return duration % timeBetweenHits == 0;
	}
}
