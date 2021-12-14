package com.mraof.minestuck.block;

import com.mraof.minestuck.tileentity.TileEntityMiniTotemLathe;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockMiniTotemLathe extends BlockSburbMachine
{
	private static final AxisAlignedBB[] TOTEM_LATHE_AABB = {new AxisAlignedBB(0.0D, 0.0D, 5/16D, 1.0D, 1.0D, 11/16D), new AxisAlignedBB(5/16D, 0.0D, 0.0D, 11/16D, 1.0D, 1.0D)};

	public BlockMiniTotemLathe()
	{
		super("miniTotemLathe");
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata)
	{
		return new TileEntityMiniTotemLathe();
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return TOTEM_LATHE_AABB[state.getValue(FACING).getHorizontalIndex()];
	}
}
