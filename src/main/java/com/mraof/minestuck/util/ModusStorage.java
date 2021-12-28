package com.mraof.minestuck.util;

import com.mraof.minestuck.captchalogue.captchalogueable.CaptchalogueableItemStack;
import com.mraof.minestuck.captchalogue.captchalogueable.ICaptchalogueable;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ModusStorage
{
	public static ICaptchalogueable getStoredItem(ItemStack stack)
	{
		NBTTagCompound nbt = getOrCreateTag(stack);
		if(!nbt.hasKey("StoredItem") || !(nbt.getTag("StoredItem") instanceof NBTTagCompound))
			return null;
		
		return ICaptchalogueable.readFromNBT(nbt.getCompoundTag("StoredItem"));
	}
	
	public static ItemStack storeItem(ItemStack stack, ICaptchalogueable store)
	{
		getOrCreateTag(stack).setTag("StoredItem", ICaptchalogueable.writeToNBT(store));
		return stack;
	}
	
	public static NBTTagCompound getOrCreateTag(ItemStack stack)
	{
		NBTTagCompound nbt = new NBTTagCompound();
		if(!stack.hasTagCompound())
			stack.setTagCompound(nbt);
		else nbt = stack.getTagCompound();
		return nbt;
	}
}
