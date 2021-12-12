package com.mraof.minestuck.item;

import com.mraof.minestuck.item.TabMinestuck;
import com.mraof.minestuck.util.ModusStorage;
import net.minecraft.item.Item;

public class ItemCruxite extends Item
{
	public ItemCruxite(String name)
	{
		setUnlocalizedName(name);
		setHasSubtypes(true);
		setCreativeTab(TabMinestuck.instance);
	}
}
