package com.cibernet.fetchmodiplus.items;

import com.mraof.minestuck.item.MinestuckItems;
import com.mraof.minestuck.item.TabMinestuck;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import scala.actors.threadpool.Arrays;

import java.util.ArrayList;
import java.util.List;

public class ModusItem extends BaseItem
{
	public static final List<Item> fetchModi = new ArrayList<>();
	
	public ModusItem(String name)
	{
		super(name);
		setMaxStackSize(1);
		this.setCreativeTab(TabMinestuck.instance);
		
		fetchModi.add(this);
	}
}
