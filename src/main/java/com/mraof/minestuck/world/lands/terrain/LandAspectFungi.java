package com.mraof.minestuck.world.lands.terrain;

import com.mraof.minestuck.block.MinestuckBlocks;
import com.mraof.minestuck.entity.consort.EnumConsort;
import com.mraof.minestuck.world.biome.MinestuckBiomes;
import com.mraof.minestuck.world.lands.decorator.*;
import com.mraof.minestuck.world.lands.structure.GateStructureMushroom;
import com.mraof.minestuck.world.lands.structure.IGateStructure;
import com.mraof.minestuck.world.lands.structure.blocks.StructureBlockRegistry;
import net.minecraft.block.BlockColored;
import net.minecraft.block.BlockStoneBrick;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.gen.feature.WorldGenBigMushroom;

import java.util.ArrayList;
import java.util.List;

public class LandAspectFungi extends TerrainLandAspect
{
	static Vec3d fogColor = new Vec3d(0.69D, 0.76D, 0.61D);
	static Vec3d skyColor = new Vec3d(0.69D, 0.76D, 0.61D);

	@Override
	public void registerBlocks(StructureBlockRegistry registry)
	{
		registry.setBlockState("upper", Blocks.MYCELIUM.getDefaultState());
		registry.setBlockState("ocean", Blocks.WATER.getDefaultState());
		registry.setBlockState("structure_primary_decorative", Blocks.STONEBRICK.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.MOSSY));
		registry.setBlockState("structure_primary_stairs", Blocks.STONE_BRICK_STAIRS.getDefaultState());
		registry.setBlockState("structure_secondary", MinestuckBlocks.myceliumBrick.getDefaultState());
		registry.setBlockState("structure_secondary_decorative", Blocks.STONEBRICK.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.CHISELED));
		registry.setBlockState("structure_secondary_stairs", MinestuckBlocks.stairs.get(MinestuckBlocks.myceliumBrick).getDefaultState());
		registry.setBlockState("village_path", Blocks.GRASS_PATH.getDefaultState());
		registry.setBlockState("light_block", MinestuckBlocks.glowyGoop.getDefaultState());
		registry.setBlockState("torch", Blocks.REDSTONE_TORCH.getDefaultState());
		registry.setBlockState("mushroom_1", Blocks.RED_MUSHROOM.getDefaultState());
		registry.setBlockState("mushroom_2", Blocks.BROWN_MUSHROOM.getDefaultState());
		registry.setBlockState("bush", Blocks.BROWN_MUSHROOM.getDefaultState());
		registry.setBlockState("structure_wool_1", Blocks.WOOL.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.LIME));
		registry.setBlockState("structure_wool_3", Blocks.WOOL.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.GRAY));
	}

	@Override
	public List<ILandDecorator> getDecorators()
	{
		ArrayList<ILandDecorator> list = new ArrayList<ILandDecorator>();
		list.add(new SurfaceDecoratorRegistryVein("slime", 30, 30, MinestuckBiomes.mediumNormal));
		list.add(new SurfaceDecoratorRegistryVein("slime", 70, 30, MinestuckBiomes.mediumRough));
		list.add(new SurfaceMushroomGenerator(Blocks.BROWN_MUSHROOM, true, 10, 64, MinestuckBiomes.mediumRough));
		list.add(new SurfaceMushroomGenerator(Blocks.BROWN_MUSHROOM, true, 10, 64, MinestuckBiomes.mediumNormal));
		list.add(new SurfaceMushroomGenerator(Blocks.RED_MUSHROOM, true, 10, 64, MinestuckBiomes.mediumRough));
		list.add(new SurfaceMushroomGenerator(Blocks.RED_MUSHROOM, true, 10, 64, MinestuckBiomes.mediumNormal));
		list.add(new WorldGenDecorator(new WorldGenBigMushroom(), 200, 0.6F, MinestuckBiomes.mediumNormal));
		list.add(new WorldGenDecorator(new WorldGenBigMushroom(), 120, 0.6F, MinestuckBiomes.mediumRough));

		list.add(new UndergroundDecoratorVein(Blocks.GRAVEL.getDefaultState(), 8, 33, 256));
		list.add(new UndergroundDecoratorVein(Blocks.IRON_ORE.getDefaultState(), 24, 9, 64));
		list.add(new UndergroundDecoratorVein(Blocks.REDSTONE_ORE.getDefaultState(), 12, 8, 32));

		return list;
	}

	@Override
	public Vec3d getFogColor()
	{
		return fogColor;
	}

	@Override
	public Vec3d getSkyColor()
	{
		return skyColor;
	}

	@Override
	public int getWeatherType()
	{
		return 1;
	}

	@Override
	public IGateStructure getGateStructure()
	{
		return new GateStructureMushroom();
	}

	@Override
	public EnumConsort getConsortType()
	{
		return EnumConsort.SALAMANDER;
	}

	@Override
	public String getPrimaryName()
	{
		return "fungi";
	}

	@Override
	public String[] getNames()
	{
		return new String[]{"fungi", "dank", "must", "mold", "mildew", "mycelium"};
	}

}
