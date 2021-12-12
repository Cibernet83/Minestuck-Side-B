package com.mraof.minestuck.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class ItemModusVanilla extends Item
{
	
	public static final String[] NAMES = {"stack", "queue", "queuestack", "tree", "hashmap", "set"};

	// TODO: Expand these into their own items
	public ItemModusVanilla()
	{
		this.maxStackSize = 1;
		this.setHasSubtypes(true);
		this.setCreativeTab(TabMinestuck.instance);
		this.setUnlocalizedName("modusCard");
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		return getUnlocalizedName()+"."+ NAMES[stack.getItemDamage()];
	}
	
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
	{
		if(this.isInCreativeTab(tab))
			for(int i = 0; i < 6; i++)
				items.add(new ItemStack(this, 1, i));
	}
	
}