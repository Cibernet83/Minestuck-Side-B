package com.mraof.minestuck.block;

import com.mraof.minestuck.tileentity.TileEntityMiniCruxtruder;
import com.mraof.minestuck.tileentity.TileEntityMiniTotemLathe;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockMiniCruxtruder extends BlockSburbMachine
{
	public BlockMiniCruxtruder()
	{
		super(MachineType.CRUXTRUDER,"miniCruxtruder");
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata)
	{
		return new TileEntityMiniCruxtruder();
	}
}
