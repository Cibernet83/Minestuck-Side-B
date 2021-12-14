package com.mraof.minestuck.block;

import com.mraof.minestuck.tileentity.TileEntityMiniPunchDesignix;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockMiniPunchDesignix extends BlockSburbMachine
{
	private static final AxisAlignedBB[] PUNCH_DESIGNIX_AABB = {new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 5/8D), new AxisAlignedBB(3/8D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 3/8D, 1.0D, 1.0D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 5/8D, 1.0D, 1.0D)};

	public BlockMiniPunchDesignix()
	{
		super("miniPunchDesignix");
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata)
	{
		return new TileEntityMiniPunchDesignix();
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return PUNCH_DESIGNIX_AABB[state.getValue(FACING).getHorizontalIndex()];
	}
}
