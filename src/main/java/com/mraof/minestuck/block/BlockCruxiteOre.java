package com.mraof.minestuck.block;

import com.mraof.minestuck.item.MinestuckItems;
import com.mraof.minestuck.util.MinestuckRandom;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;

import java.util.Random;

public class BlockCruxiteOre extends MSBlockCustomOre
{
	public BlockCruxiteOre()
	{
		super("cruxiteOre");
	}

	@Override
	public int getExpDrop(IBlockState state, IBlockAccess world, BlockPos pos, int fortune)
	{
		return MathHelper.getInt(MinestuckRandom.getRandom(), 2, 5);
	}	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return MinestuckItems.rawCruxite;
	}

	@Override
	public int quantityDropped(Random random)
	{
		return 2 + random.nextInt(4);
	}

	@Override
	public int quantityDroppedWithBonus(int par1, Random par2Random)
	{
		if (par1 > 0)
		{
			int j = par2Random.nextInt(par1 + 2) - 1;

			if (j < 0)
			{
				j = 0;
			}

			return this.quantityDropped(par2Random) * (j + 1);
		}
		else
			return this.quantityDropped(par2Random);
	}


}