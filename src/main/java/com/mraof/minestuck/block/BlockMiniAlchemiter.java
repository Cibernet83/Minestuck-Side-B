package com.mraof.minestuck.block;

import com.mraof.minestuck.tileentity.TileEntityMiniAlchemiter;
import com.mraof.minestuck.tileentity.TileEntityMiniTotemLathe;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockMiniAlchemiter extends BlockSburbMachine
{
	public BlockMiniAlchemiter()
	{
		 super(MachineType.ALCHEMITER,"miniAlchemiter");
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata)
	{
		return new TileEntityMiniAlchemiter();
	}
}
