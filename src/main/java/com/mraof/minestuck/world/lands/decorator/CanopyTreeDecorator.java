package com.mraof.minestuck.world.lands.decorator;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenCanopyTree;

import java.util.Random;

public class CanopyTreeDecorator extends TreeDecoratorBase
{
	protected int treeCount;
	WorldGenCanopyTree[] treeTypes;

	public CanopyTreeDecorator(int treeCount, Biome... biomes)
	{
		this(Blocks.LOG2.getDefaultState(), Blocks.LEAVES2.getDefaultState(), treeCount, biomes);
	}

	public CanopyTreeDecorator(IBlockState treeType, IBlockState leafType, int treeCount, Biome... biomes)
	{
		this(new IBlockState[]{treeType}, new IBlockState[]{leafType}, treeCount, biomes);
	}

	public CanopyTreeDecorator(IBlockState[] trees, IBlockState[] leaves, int treeCount, Biome... biomes)
	{
		super(biomes);
		this.treeCount = treeCount;
		this.treeTypes = new WorldGenCanopyTree[trees.length];
		for (int i = 0; i < trees.length; i++)
			treeTypes[i] = new WorldGenCanopyTree(false);
	}

	@Override
	public int getCount(Random random)
	{
		return random.nextInt(treeCount) + treeCount;
	}

	@Override
	protected WorldGenAbstractTree getTreeToGenerate(World world, BlockPos pos, Random rand)
	{
		return this.treeTypes[rand.nextInt(treeTypes.length)];
	}
}