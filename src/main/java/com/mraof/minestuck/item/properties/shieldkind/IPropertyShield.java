package com.mraof.minestuck.item.properties.shieldkind;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

public interface IPropertyShield
{
	default void onHitWhileShielding(ItemStack stack, EntityLivingBase player, DamageSource source, float damage, boolean blocked)
	{

	}

	default boolean onShieldParry(ItemStack stack, EntityLivingBase player, DamageSource source, float damage)
	{
		return true;
	}

	default boolean isShielding(ItemStack stack, EntityLivingBase player)
	{
		return true;
	}
}
