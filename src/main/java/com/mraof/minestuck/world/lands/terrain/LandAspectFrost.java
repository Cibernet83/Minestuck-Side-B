package com.mraof.minestuck.world.lands.terrain;

import com.mraof.minestuck.block.MSBlockLeavesVariant;
import com.mraof.minestuck.block.MSBlockLogVariant;
import com.mraof.minestuck.block.MinestuckBlocks;
import com.mraof.minestuck.entity.consort.EnumConsort;
import com.mraof.minestuck.world.biome.MinestuckBiomes;
import com.mraof.minestuck.world.lands.decorator.*;
import com.mraof.minestuck.world.lands.structure.blocks.StructureBlockRegistry;
import net.minecraft.block.*;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;

public class LandAspectFrost extends TerrainLandAspect
{
	static Vec3d fogColor = new Vec3d(0.5D, 0.6D, 0.98D);

	@Override
	public void registerBlocks(StructureBlockRegistry registry)
	{
		registry.setBlockState("surface", Blocks.GRASS.getDefaultState());
		registry.setBlockState("upper", Blocks.DIRT.getDefaultState());
		registry.setBlockState("structure_primary", Blocks.PRISMARINE.getDefaultState());
		registry.setBlockState("structure_primary_decorative", Blocks.PRISMARINE.getDefaultState().withProperty(BlockPrismarine.VARIANT, BlockPrismarine.EnumType.BRICKS));
		registry.setBlockState("structure_secondary", MinestuckBlocks.frostBrick.getDefaultState());
		registry.setBlockState("structure_secondary_stairs", MinestuckBlocks.stairs.get(MinestuckBlocks.frostBrick).getDefaultState());
		registry.setBlockState("structure_secondary_decorative", MinestuckBlocks.chiseledFrostBrick.getDefaultState());
		registry.setBlockState("structure_planks", Blocks.PLANKS.getDefaultState().withProperty(BlockPlanks.VARIANT, BlockPlanks.EnumType.SPRUCE));
		registry.setBlockState("structure_planks_slab", Blocks.WOODEN_SLAB.getDefaultState().withProperty(BlockWoodSlab.VARIANT, BlockPlanks.EnumType.SPRUCE));
		registry.setBlockState("river", Blocks.ICE.getDefaultState());
		registry.setBlockState("light_block", Blocks.SEA_LANTERN.getDefaultState());
		registry.setBlockState("bucket1", Blocks.SNOW.getDefaultState());
		registry.setBlockState("bush", Blocks.TALLGRASS.getDefaultState().withProperty(BlockTallGrass.TYPE, BlockTallGrass.EnumType.FERN));
		registry.setBlockState("structure_wool_1", Blocks.WOOL.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.WHITE));
		registry.setBlockState("structure_wool_3", Blocks.WOOL.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.CYAN));
	}

	@Override
	public List<ILandDecorator> getDecorators()
	{
		ArrayList<ILandDecorator> list = new ArrayList<ILandDecorator>();
		list.add(new IceDecorator());
		list.add(new LayeredBlockDecorator(Blocks.SNOW_LAYER, true));
		list.add(new SpruceTreeDecorator(MinestuckBlocks.log.getDefaultState().withProperty(MSBlockLogVariant.VARIANT, MSBlockLogVariant.BlockType.FROST), MinestuckBlocks.leaves.getDefaultState().withProperty(MSBlockLeavesVariant.VARIANT, MSBlockLeavesVariant.BlockType.FROST).withProperty(MSBlockLeavesVariant.CHECK_DECAY, Boolean.valueOf(false)), MinestuckBiomes.mediumNormal));
		list.add(new SpruceTreeDecorator(MinestuckBlocks.log.getDefaultState().withProperty(MSBlockLogVariant.VARIANT, MSBlockLogVariant.BlockType.FROST), MinestuckBlocks.leaves.getDefaultState().withProperty(MSBlockLeavesVariant.VARIANT, MSBlockLeavesVariant.BlockType.FROST).withProperty(MSBlockLeavesVariant.CHECK_DECAY, Boolean.valueOf(false)), MinestuckBiomes.mediumRough));

		list.add(new SurfaceDecoratorVein(Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.COARSE_DIRT), 10, 32, MinestuckBiomes.mediumRough, MinestuckBiomes.mediumOcean));
		list.add(new SurfaceDecoratorVein(Blocks.ICE.getDefaultState(), 5, 8, MinestuckBiomes.mediumRough));
		list.add(new SurfaceDecoratorVein(Blocks.SNOW.getDefaultState(), 8, 16, MinestuckBiomes.mediumRough));
		list.add(new SurfaceDecoratorVein(Blocks.SNOW.getDefaultState(), 15, 16, MinestuckBiomes.mediumNormal));

		list.add(new UndergroundDecoratorVein(Blocks.PACKED_ICE.getDefaultState(), 2, 8, 64));
		list.add(new UndergroundDecoratorVein(Blocks.SNOW.getDefaultState(), 3, 16, 64));
		list.add(new UndergroundDecoratorVein(Blocks.DIRT.getDefaultState(), 3, 28, 64));
		list.add(new UndergroundDecoratorVein(Blocks.COAL_ORE.getDefaultState(), 13, 17, 64));
		list.add(new UndergroundDecoratorVein(Blocks.DIAMOND_ORE.getDefaultState(), 3, 6, 24));
		return list;
	}

	@Override
	public float getSkylightBase()
	{
		return 7 / 8F;
	}

	@Override
	public Vec3d getFogColor()
	{
		return fogColor;
	}

	@Override
	public Vec3d getSkyColor()
	{
		return new Vec3d(0.6D, 0.7D, 0.9D);
	}

	@Override
	public int getWeatherType()
	{
		return 1;
	}

	@Override
	public float getTemperature()
	{
		return 0.0F;
	}

	@Override
	public float getOceanChance()
	{
		return 1 / 4F;
	}

	@Override
	public EnumConsort getConsortType()
	{
		return EnumConsort.IGUANA;
	}

	@Override
	public String getPrimaryName()
	{
		return "frost";
	}

	@Override
	public String[] getNames()
	{
		return new String[]{"frost", "ice", "snow"};
	}
}