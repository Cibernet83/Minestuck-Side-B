package com.mraof.minestuck.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

//TODO separate into independent items
public class ItemDisk extends MSItemBase
{
	
	private String[] subNames = { "Client", "Server" };
	
	public ItemDisk()
	{
		super("computerDisk");
		this.maxStackSize = 1;
		this.setHasSubtypes(true);
		this.setCreativeTab(TabMinestuck.instance);
	}
	
	@Override
	public String getUnlocalizedName(ItemStack itemstack)
	{
		return getUnlocalizedName() + "." + subNames[itemstack.getItemDamage()];
	}
	
	@Override
	public int getMetadata(int damageValue)
	{
		return damageValue;
	}
	
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
	{
		if(this.isInCreativeTab(tab))
			for(int i = 0; i < subNames.length; i++)
				items.add(new ItemStack(this, 1, i));
	}
}
