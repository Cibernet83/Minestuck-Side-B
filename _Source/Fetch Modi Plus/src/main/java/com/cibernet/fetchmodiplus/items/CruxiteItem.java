package com.cibernet.fetchmodiplus.items;

import com.mraof.minestuck.item.TabMinestuck;

public class CruxiteItem extends BaseItem
{
	
	public CruxiteItem(String name)
	{
		super(name);
		this.setHasSubtypes(true);
		setCreativeTab(TabMinestuck.instance);
	}
}
