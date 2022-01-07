package com.mraof.minestuck.block;

import com.mraof.minestuck.item.MinestuckItems;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

import java.util.Random;

public class BlockDesertBush extends BlockDesertFlora
{
	public BlockDesertBush(String name)
	{
		super(name);
	}

	@Override
	public int quantityDropped(Random random)
	{
		return 3 + random.nextInt(3);
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return MinestuckItems.desertFruit;
	}
}
