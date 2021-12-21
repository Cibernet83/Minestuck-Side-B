package com.mraof.minestuck.item;

import com.mraof.minestuck.inventory.captchalouge.Modus;

public class ItemModus extends MSItemBase
{
	private final Modus modus;

	public ItemModus(Modus modus)
	{
		super(modus.getUnlocalizedName() + "Modus", MinestuckTabs.minestuck, 1, false);
		this.modus = modus;
		MinestuckItems.modi.put(modus, this);
	}

	public Modus getModus()
	{
		return modus;
	}
}
