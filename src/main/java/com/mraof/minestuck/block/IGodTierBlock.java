package com.mraof.minestuck.block;

import com.mraof.minestuck.util.EnumAspect;

public interface IGodTierBlock
{
	EnumAspect getAspect();

	default boolean canGodTier()
	{
		return true;
	}
}
