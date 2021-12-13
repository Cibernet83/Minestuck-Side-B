package com.mraof.minestuck.block;

import com.mraof.minestuck.item.TabsMinestuck;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;

public class BlockMinestuckStairs extends BlockStairs
{
	public BlockMinestuckStairs(IBlockState modelState)
	{
		super(modelState);
		setCreativeTab(TabsMinestuck.minestuck);
		this.useNeighborBrightness = true;
	}

}
