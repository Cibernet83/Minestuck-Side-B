package com.mraof.minestuck.item;

import com.mraof.minestuck.util.IRegistryItem;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;

public class MSItemBase extends Item implements IRegistryItem<Item>
{
	private final String regName;
	public static final ArrayList<IRegistryItem<Item>> items = new ArrayList<>();

	public MSItemBase(String name, CreativeTabs tab)
	{
		regName = IRegistryItem.unlocToReg(name);
		setUnlocalizedName(name);
		setCreativeTab(tab);
		items.add(this);
	}

	public MSItemBase(String name)
	{
		this(name, TabMinestuck.instance);
	}


	@Override
	public void register(IForgeRegistry<Item> registry)
	{
		setRegistryName(regName);
		registry.register(this);
	}
}
