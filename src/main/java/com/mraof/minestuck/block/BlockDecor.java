package com.mraof.minestuck.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockDecor extends MSBlockBase
{
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

	private final AxisAlignedBB AABB;

	protected BlockDecor(String name, AxisAlignedBB aabb)
	{
		this(name, SoundType.STONE, aabb);
	}


	protected BlockDecor(String name, SoundType sound, AxisAlignedBB aabb)
	{
		super(name, Material.ROCK);
		setSoundType(sound);
		setHardness(0.5f);
		this.AABB = aabb;
	}

	@Override
	public boolean isFullBlock(IBlockState state)
	{
		return false;
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	public IBlockState getStateFromMeta(int meta)
	{
		return getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta % 4));
	}

	public int getMetaFromState(IBlockState state)
	{
		return (state.getValue(FACING)).getHorizontalIndex();
	}

	/**
	 * Returns the blockstate with the given rotation from the passed blockstate. If inapplicable, returns the passed
	 * blockstate.
	 */
	public IBlockState withRotation(IBlockState state, Rotation rot)
	{
		return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
	}

	/**
	 * Returns the blockstate with the given mirror of the passed blockstate. If inapplicable, returns the passed
	 * blockstate.
	 */
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
	{
		return state.withProperty(FACING, mirrorIn.mirror(state.getValue(FACING)));
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state)
	{
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return modifyAABBForDirection(state.getValue(FACING), AABB);
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.CUTOUT;
	}

	/**
	 * Checks if this block can be placed exactly at the given position.
	 */
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
	{
		return worldIn.getBlockState(pos.down()).isFullCube();
	}

	/**
	 * Called by ItemBlocks just before a block is actually set in the world, to allow for adjustments to the
	 * IBlockstate
	 */
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing());
	}

	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, FACING);
	}

	public static AxisAlignedBB modifyAABBForDirection(EnumFacing facing, AxisAlignedBB bb)
	{
		AxisAlignedBB out = null;
		switch (facing.ordinal())
		{
			case 2:    //North
				out = new AxisAlignedBB(bb.minX, bb.minY, bb.minZ, bb.maxX, bb.maxY, bb.maxZ);
				break;
			case 3:    //South
				out = new AxisAlignedBB(1 - bb.maxX, bb.minY, 1 - bb.maxZ, 1 - bb.minX, bb.maxY, 1 - bb.minZ);
				break;
			case 4:    //West
				out = new AxisAlignedBB(bb.minZ, bb.minY, 1 - bb.maxX, bb.maxZ, bb.maxY, 1 - bb.minX);
				break;
			case 5:    //East
				out = new AxisAlignedBB(1 - bb.maxZ, bb.minY, bb.minX, 1 - bb.minZ, bb.maxY, bb.maxX);
				break;
		}
		return out;
	}

}
