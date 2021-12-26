package com.mraof.minestuck.item;

import com.mraof.minestuck.modus.Modus;
import net.minecraft.item.Item;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;

public class ItemModus extends MSItemBase
{
	private final Modus modus;

	public ItemModus(Modus modus)
	{
		super(modus.getUnlocalizedName() + "Modus", MinestuckTabs.fetchModi, 1, false);
		this.modus = modus;
		MinestuckItems.modi.put(modus, this);
	}

	public Modus getModus()
	{
		return modus;
	}

	@Override
	public void register(IForgeRegistry<Item> registry)
	{
		super.register(registry);
		OreDictionary.registerOre("modus", this); // OreDict gets *really* mad when you register the item as an ore before the item itself :flushed:
	}
}
