package com.mraof.minestuck.util;

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
	public static ItemStack getStoredItem(ItemStack stack)
	{
		NBTTagCompound nbt = getOrCreateTag(stack);
		if(!nbt.hasKey("StoredItem") || !(nbt.getTag("StoredItem") instanceof NBTTagCompound))
			return ItemStack.EMPTY;
		
		return new ItemStack((NBTTagCompound) nbt.getTag("StoredItem"));
	}
	
	public static ItemStack storeItem(ItemStack stack, ItemStack store)
	{
		NBTTagCompound itemNbt = new NBTTagCompound();
		store.writeToNBT(itemNbt);
		getOrCreateTag(stack).setTag("StoredItem", itemNbt);
		
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
