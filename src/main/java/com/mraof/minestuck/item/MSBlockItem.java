package com.mraof.minestuck.item;

import com.mraof.minestuck.util.IRegistryItem;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.registries.IForgeRegistry;

public class MSBlockItem extends ItemBlock implements IRegistryItem
{
	public MSBlockItem(Block block)
	{
		super(block);
		MSItemBase.items.add(this);
	}

	@Override
	public void register(IForgeRegistry registry)
	{
		setRegistryName(block.getRegistryName());
		registry.register(this);
	}
}
