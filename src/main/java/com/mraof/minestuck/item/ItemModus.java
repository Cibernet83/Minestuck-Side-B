package com.mraof.minestuck.item;

public class ItemModus extends MSItemBase
{
	public ItemModus(String name)
	{
		super(name, MinestuckTabs.fetchModi, 1, false);
		MinestuckItems.modi.add(this);
	}
}
