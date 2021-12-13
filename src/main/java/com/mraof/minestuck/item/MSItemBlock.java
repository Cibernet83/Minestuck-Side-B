package com.mraof.minestuck.item;

import com.mraof.minestuck.util.IRegistryItem;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.registries.IForgeRegistry;

public class MSItemBlock extends ItemBlock implements IRegistryItem<Item>
{
	public MSItemBlock(Block block)
	{
		super(block);
		MSItemBase.items.add(this);
	}

	@Override
	public void register(IForgeRegistry<Item> registry)
	{
		setRegistryName(block.getRegistryName());
		registry.register(this);
	}
}
