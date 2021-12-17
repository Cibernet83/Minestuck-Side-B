package com.mraof.minestuck.world;

import com.mraof.minestuck.block.MinestuckBlocks;
import com.mraof.minestuck.entity.consort.EnumConsort;
import com.mraof.minestuck.world.lands.decorator.ILandDecorator;
import com.mraof.minestuck.world.lands.structure.blocks.StructureBlockRegistry;
import com.mraof.minestuck.world.lands.terrain.TerrainLandAspect;
import net.minecraft.block.BlockColored;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.BlockStoneSlab;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.Vec3d;
import thaumcraft.api.blocks.BlocksTC;

import java.util.ArrayList;
import java.util.List;

public class LandAspectThaum extends TerrainLandAspect
{
    static Vec3d fogColor = new Vec3d(0.0D, 0.4D, 0.2D);
    static Vec3d skyColor = new Vec3d(155/255, 120/255, 200/255);

    @Override
    public void registerBlocks(StructureBlockRegistry registry)
    {
        registry.setBlockState("surface", BlocksTC.grassAmbient.getDefaultState());
        registry.setBlockState("upper", Blocks.CONCRETE.getDefaultState());
        registry.setBlockState("ground", Blocks.END_STONE.getDefaultState());
        registry.setBlockState("ocean", MinestuckBlocks.blockEnder.getDefaultState());
        registry.setBlockState("structure_primary", Blocks.END_BRICKS.getDefaultState());
        registry.setBlockState("structure_primary_decorative", Blocks.PURPUR_PILLAR.getDefaultState().withProperty(BlockRotatedPillar.AXIS, EnumFacing.Axis.Y));
        registry.setBlockState("structure_primary_stairs", Blocks.PURPUR_STAIRS.getDefaultState());
        registry.setBlockState("structure_secondary", Blocks.PURPUR_BLOCK.getDefaultState());
        registry.setBlockState("structure_secondary_stairs", Blocks.STONE_BRICK_STAIRS.getDefaultState());
        registry.setBlockState("fall_fluid", MinestuckBlocks.blockEnder.getDefaultState());
        registry.setBlockState("structure_planks", Blocks.BRICK_BLOCK.getDefaultState());
        registry.setBlockState("structure_planks_slab", Blocks.STONE_SLAB.getDefaultState().withProperty(BlockStoneSlab.VARIANT, BlockStoneSlab.EnumType.BRICK));
        registry.setBlockState("village_path", MinestuckBlocks.coarseEndStone.getDefaultState());
        registry.setBlockState("village_fence", Blocks.NETHER_BRICK_FENCE.getDefaultState());
        registry.setBlockState("structure_wool_1", Blocks.WOOL.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.GREEN));
        registry.setBlockState("structure_wool_3", Blocks.WOOL.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.PURPLE));
    }

    @Override
    public List<ILandDecorator> getDecorators()
    {
        ArrayList<ILandDecorator> list = new ArrayList();
        return list;
    }

    @Override
    public Vec3d getFogColor()
    {
        return fogColor;
    }

    @Override
    public EnumConsort getConsortType()
    {
        return EnumConsort.TURTLE;
    }

    @Override
    public String getPrimaryName()
    {
        return "vis";
    }

    @Override
    public String[] getNames()
    {
        return new String[] {"vis", "magic", "thaumaturgy"};
    }
}
