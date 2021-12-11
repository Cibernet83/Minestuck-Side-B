package com.cibernet.fetchmodiplus.items;

import com.cibernet.fetchmodiplus.registries.FMPItems;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class BaseItem extends Item
{
	public BaseItem(String name)
	{
		setUnlocalizedName(name);
		setRegistryName(name);
		
		FMPItems.items.add(this);
	}
	
	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		super.addInformation(stack, worldIn, tooltip, flagIn);
		if(I18n.canTranslate(getUnlocalizedName()+".tooltip"))
			tooltip.add(I18n.translateToLocal(getUnlocalizedName()+".tooltip"));
		
	}
	
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
	
	public static void dropItem(ItemStack stack, EntityLivingBase entity)
	{
	
	}
}
