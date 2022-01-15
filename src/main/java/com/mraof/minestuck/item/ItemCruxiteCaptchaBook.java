package com.mraof.minestuck.item;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ItemCruxiteCaptchaBook extends ItemCruxite
{
	public ItemCruxiteCaptchaBook(String name)
	{
		super(name);
		setMaxStackSize(1);
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey("Author"))
			tooltip.add(I18n.format("item.captchalogue_book.author", stack.getTagCompound().getString("Author")));
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
	{
	}
}
