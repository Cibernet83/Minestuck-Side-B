package com.mraof.minestuck.item.properties.bowkind;

import com.mraof.minestuck.entity.EntityMSUArrow;
import com.mraof.minestuck.item.properties.WeaponProperty;
import net.minecraft.util.math.RayTraceResult;

public class PropertyGhostArrow extends WeaponProperty implements IPropertyArrow
{
	@Override
	public boolean onEntityImpact(EntityMSUArrow arrow, RayTraceResult result)
	{
		return false;
	}
}
