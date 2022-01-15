package com.mraof.minestuck.block;

import com.mraof.minestuck.tileentity.TileEntityAutoWidget;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockAutoWidget extends MSBlockContainer
{
	public static final PropertyBool ACTIVE = PropertyBool.create("active");
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	protected static final AxisAlignedBB AABB = new AxisAlignedBB(0, 0, 0, 1, 10 / 16F, 1);

	protected BlockAutoWidget()
	{
		super("autoWidget", Material.IRON);
		setHarvestLevel("pickaxe", 0);
		setHardness(3.0F);

		this.setDefaultState(this.getDefaultState().withProperty(FACING, EnumFacing.NORTH).withProperty(ACTIVE, false));
	}

	public static void updateItem(boolean b, World world, BlockPos pos)
	{
		IBlockState oldState = world.getBlockState(pos);
		IBlockState newState = oldState.withProperty(ACTIVE, b);
		world.notifyBlockUpdate(pos, oldState, newState, 3);
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
	{
		TileEntityAutoWidget te = (TileEntityAutoWidget) worldIn.getTileEntity(pos);
		if (te != null) InventoryHelper.dropInventoryItems(worldIn, pos, te);
		super.breakBlock(worldIn, pos, state);
	}

	@Nullable
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityAutoWidget();
	}	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, FACING, ACTIVE);
	}

	public EnumBlockRenderType getRenderType(IBlockState state)
	{
		return EnumBlockRenderType.MODEL;
	}	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(FACING).getHorizontalIndex() + (state.getValue(ACTIVE) ? 0 : 4);
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta % 4)).withProperty(ACTIVE, meta > 3);
	}

	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		return this.getStateFromMeta(meta).withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
	{
		TileEntityAutoWidget te = (TileEntityAutoWidget) worldIn.getTileEntity(pos);
		if (te != null)
			state = state.withProperty(ACTIVE, !te.getStackInSlot(0).isEmpty());
		return state;
	}



	public boolean isFullCube(IBlockState state)
	{
		return false;
	}



	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return AABB;
	}
}
