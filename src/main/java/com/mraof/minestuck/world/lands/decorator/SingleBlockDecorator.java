package com.mraof.minestuck.world.lands.decorator;

import com.mraof.minestuck.world.lands.gen.ChunkProviderLands;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public abstract class SingleBlockDecorator extends BiomeSpecificDecorator
{


	@Override
	public BlockPos generate(World world, Random random, BlockPos pos, ChunkProviderLands provider)
	{
		pos = world.getHeight(pos);

		if (canPlace(pos, world))
			world.setBlockState(pos, pickBlock(random), 2);

		return null;
	}

	public abstract IBlockState pickBlock(Random random);

	public abstract boolean canPlace(BlockPos pos, World world);

	@Override
	public float getPriority()
	{
		return 0.5F;
	}
}