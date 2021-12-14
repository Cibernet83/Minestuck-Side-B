package com.mraof.minestuck.block;

import com.mraof.minestuck.tileentity.TileEntityMiniTotemLathe;
import com.mraof.minestuck.tileentity.TileEntitySburbMachine;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockMiniTotemLathe extends BlockSburbMachine
{
	public BlockMiniTotemLathe()
	{
		super(MachineType.TOTEM_LATHE,"miniTotemLathe");
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata)
	{
		return new TileEntityMiniTotemLathe();
	}
}
