package com.mraof.minestuck.item;

import com.mraof.minestuck.util.IRegistryItem;
import com.sun.org.apache.bcel.internal.generic.IREM;
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
		setCreativeTab(TabMinestuck.instance);
		regName = "record_" + IRegistryItem.unlocToReg(recordName);
		MSItemBase.items.add(this);
	}

	@Override
	public void register(IForgeRegistry<Item> registry)
	{
		setRegistryName(regName);
		registry.register(this);
	}
}