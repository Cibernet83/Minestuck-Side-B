package com.mraof.minestuck.potions;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;

import java.util.ArrayList;
import java.util.Arrays;

public class PotionCounter extends MSPotionBase
{
	private final ArrayList<Potion> counteredPotions = new ArrayList<>();

	protected PotionCounter(String name, boolean isBadEffectIn, int liquidColorIn, Potion... counters)
	{
		super(name, isBadEffectIn, liquidColorIn);
		counteredPotions.addAll(Arrays.asList(counters));
	}

	@Override
	public void performEffect(EntityLivingBase entity, int amplifier)
	{
		for(Potion key : counteredPotions)
			if(entity.getActivePotionEffect(key) != null)
				entity.removePotionEffect(key);
	}

	@Override
	public boolean isReady(int duration, int amplifier) {
		return true;
	}
}
