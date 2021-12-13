package com.mraof.minestuck.item;

import com.mraof.minestuck.MinestuckConfig;
import com.mraof.minestuck.block.MinestuckBlocks;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class TabsMinestuck
{
	public static final CreativeTabs minestuck = new CreativeTabs("minestuck")
	{
		@Override
		public ItemStack getTabIconItem()
		{
			return new ItemStack(MinestuckBlocks.genericObject);
		}
	};
	public static final CreativeTabs weapons = new CreativeTabs("minestuckWeapons")
	{
		@Override
		public ItemStack getTabIconItem() {
			return  new ItemStack(MinestuckUniverseItems.batteryBeamBlade);
		}
	};
}
