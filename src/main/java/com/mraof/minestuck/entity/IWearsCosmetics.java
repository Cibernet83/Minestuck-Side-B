package com.mraof.minestuck.entity;

import net.minecraft.item.ItemStack;

public interface IWearsCosmetics
{
	ItemStack getHeadStack();
	void setHeadStack(ItemStack stack);
	int getCosmeticPickupDelay();
	void setCosmeticPickupDelay(int i);
	int shrinkPickupDelay();
}
