package com.mraof.minestuck.block;

import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

public abstract class BlockLargeMachine extends MSBlockContainer
{
	public static final PropertyDirection DIRECTION = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

	public BlockLargeMachine(String name)
	{
		super(name, Material.ROCK);
		setHardness(2);
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return getDefaultState();
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
	{
		return BlockFaceShape.UNDEFINED;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	//keep the game from crashing when a machine is blown up
	@Override
	public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune)
	{

	}

	@Override
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.CUTOUT_MIPPED;
	}

	@Override
	public EnumPushReaction getMobilityFlag(IBlockState state)
	{
		return EnumPushReaction.BLOCK;
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, DIRECTION);
	}

	//keeps the blocks from dropping something
	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
	{
		return null;
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state)
	{
		return EnumBlockRenderType.MODEL;
	}

	public abstract Item getItemFromMachine();
}
