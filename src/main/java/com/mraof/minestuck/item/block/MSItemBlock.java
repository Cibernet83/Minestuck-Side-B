package com.mraof.minestuck.item.block;

import com.mraof.minestuck.item.IRegistryItem;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.registries.IForgeRegistry;

public class MSItemBlock extends ItemBlock implements IRegistryItem
{
	public MSItemBlock(Block block, int stackSize)
	{
		super(block);
		setMaxStackSize(stackSize);
	}

	public MSItemBlock(Block block)
	{
		this(block, 64);
	}

	@Override
	public void register(IForgeRegistry<Item> registry)
	{
		setRegistryName(block.getRegistryName());
		registry.register(this);
	}
}
