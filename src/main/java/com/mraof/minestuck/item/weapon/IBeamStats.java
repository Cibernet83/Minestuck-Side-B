package com.mraof.minestuck.item.weapon;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public interface IBeamStats
{
	float getBeamRadius(ItemStack stack);
	int getBeamHurtTime(ItemStack stack);
	void setCustomBeamTexture();
	ResourceLocation getBeamTexture();
	void setBeamTexture(String name);
}
