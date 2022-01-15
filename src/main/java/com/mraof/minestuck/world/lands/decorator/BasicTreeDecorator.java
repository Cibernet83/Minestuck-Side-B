package com.mraof.minestuck.world.lands.decorator;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenTrees;

import java.util.Random;

public class BasicTreeDecorator extends TreeDecoratorBase
{
	protected int treeCount;
	WorldGenTrees[] treeTypes;

	public BasicTreeDecorator(int treeCount, Biome... biomes)
	{
		this(Blocks.LOG.getDefaultState(), Blocks.LEAVES.getDefaultState(), treeCount, biomes);
	}

	public BasicTreeDecorator(IBlockState treeType, IBlockState leafType, int treeCount, Biome... biomes)
	{
		this(new IBlockState[]{treeType}, new IBlockState[]{leafType}, treeCount, biomes);
	}

	public BasicTreeDecorator(IBlockState[] trees, IBlockState[] leaves, int treeCount, Biome... biomes)
	{
		super(biomes);
		this.treeCount = treeCount;
		this.treeTypes = new WorldGenTrees[trees.length];
		for (int i = 0; i < trees.length; i++)
			treeTypes[i] = new WorldGenTrees(false, 5, trees[i], leaves[i], false);
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