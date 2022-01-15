package com.mraof.minestuck.util;

import com.mraof.minestuck.captchalogue.captchalogueable.ICaptchalogueable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ModusStorage
{
	public static ICaptchalogueable getStoredItem(ItemStack stack)
	{
		NBTTagCompound nbt = getOrCreateTag(stack);
		if (!nbt.hasKey("StoredItem") || !(nbt.getTag("StoredItem") instanceof NBTTagCompound))
			return null;

		return ICaptchalogueable.readFromNBT(nbt.getCompoundTag("StoredItem"));
	}

	public static NBTTagCompound getOrCreateTag(ItemStack stack)
	{
		NBTTagCompound nbt = new NBTTagCompound();
		if (!stack.hasTagCompound())
			stack.setTagCompound(nbt);
		else nbt = stack.getTagCompound();
		return nbt;
	}

	public static ItemStack storeItem(ItemStack stack, ICaptchalogueable store)
	{
		getOrCreateTag(stack).setTag("StoredItem", ICaptchalogueable.writeToNBT(store));
		return stack;
	}
}
