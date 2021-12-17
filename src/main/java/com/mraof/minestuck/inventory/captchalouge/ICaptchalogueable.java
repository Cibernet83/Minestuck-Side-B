package com.mraof.minestuck.inventory.captchalouge;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public interface ICaptchalogueable
{
	void grow(ICaptchalogueable other);
	boolean isEmpty();
	boolean isCompatibleWith(ICaptchalogueable other);
	Object getObject();
	void eject(ISylladex from, EntityPlayer player);
	ItemStack captchalogueIntoCardItem();
	NBTTagCompound writeToNBT();
	void readFromNBT(NBTTagCompound nbt);
}
