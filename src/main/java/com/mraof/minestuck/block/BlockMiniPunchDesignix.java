package com.mraof.minestuck.block;

import com.mraof.minestuck.tileentity.TileEntityMiniPunchDesignix;
import com.mraof.minestuck.tileentity.TileEntityMiniTotemLathe;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockMiniPunchDesignix extends BlockSburbMachine
{
	public BlockMiniPunchDesignix()
	{
		super(MachineType.PUNCH_DESIGNIX,"miniPunchDesignix");
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata)
	{
		return new TileEntityMiniPunchDesignix();
	}
}
