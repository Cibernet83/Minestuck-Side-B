package com.mraof.minestuck.world.lands.decorator;

import com.mraof.minestuck.world.gen.feature.WorldGenRainbowTree;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import java.util.Random;

public class WorldgenTreeDecorator extends TreeDecoratorBase
{
	private int tC;
	private WorldGenAbstractTree tree = new WorldGenRainbowTree(false);
	private float priority = 0.6F;

	public WorldgenTreeDecorator(int treeCount, WorldGenAbstractTree tree, float priority, Biome... biomes)
	{
		this(treeCount, tree, biomes);
		this.priority = priority;
	}

	public WorldgenTreeDecorator(int treeCount, WorldGenAbstractTree tree, Biome... biomes)
	{
		super(biomes);
		this.tree = tree;
		tC = treeCount;
	}

	@Override
	protected WorldGenAbstractTree getTreeToGenerate(World world, BlockPos pos, Random rand)
	{
		return tree;
	}

	@Override
	public float getPriority()
	{
		return priority;
	}

	@Override
	public int getCount(Random random)
	{
		return tC;
	}
}
