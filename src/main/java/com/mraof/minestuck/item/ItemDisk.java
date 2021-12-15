package com.mraof.minestuck.item;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.model.ModelLoader;

//TODO separate into independent items
public class ItemDisk extends MSItemBase
{
	
	private String[] subNames = { "client", "server" };
	
	public ItemDisk()
	{
		super("disk");
		this.maxStackSize = 1;
		this.setHasSubtypes(true);
		this.setCreativeTab(MinestuckTabs.minestuck);
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

	@Override
	public void registerModel()
	{
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName() + "_client", "inventory"));
		ModelLoader.setCustomModelResourceLocation(this, 1, new ModelResourceLocation(getRegistryName() + "_server", "inventory"));
	}
}
