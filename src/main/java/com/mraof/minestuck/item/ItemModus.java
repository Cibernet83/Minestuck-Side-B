package com.mraof.minestuck.item;

import com.mraof.minestuck.item.TabMinestuck;
import com.mraof.minestuck.util.ModusStorage;
import net.minecraft.item.Item;

public class ItemModus extends Item
{
	public ItemModus(String name)
	{
		setUnlocalizedName(name);
		setMaxStackSize(1);
		setCreativeTab(TabMinestuck.instance);
	}
}
