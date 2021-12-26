package com.mraof.minestuck.entity;

import net.minecraft.item.ItemStack;

public interface IWearsCosmetics
{
	void setHeadStack(ItemStack stack);
	ItemStack getHeadStack();

	void setCosmeticPickupDelay(int i);
	int getCosmeticPickupDelay();
	int shrinkPickupDelay();
}
