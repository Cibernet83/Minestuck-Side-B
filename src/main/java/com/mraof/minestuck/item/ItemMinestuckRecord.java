package com.mraof.minestuck.item;

import com.mraof.minestuck.util.IRegistryItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemRecord;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class ItemMinestuckRecord extends ItemRecord implements IRegistryItem<Item>
{
	private final String regName;

	public ItemMinestuckRecord(String recordName, SoundEvent soundIn)
	{
		super(recordName, soundIn);
		setCreativeTab(MinestuckTabs.minestuck);
		regName = "record_" + IRegistryItem.unlocToReg(recordName);
		MinestuckItems.items.add(this);
		MinestuckItems.records.add(this);
	}

	@Override
	public void register(IForgeRegistry<Item> registry)
	{
		setRegistryName(regName);
		registry.register(this);
	}
}