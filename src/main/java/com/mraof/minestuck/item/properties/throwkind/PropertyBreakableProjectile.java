package com.mraof.minestuck.item.properties.throwkind;

import com.mraof.minestuck.entity.EntityMSUThrowable;
import com.mraof.minestuck.item.properties.WeaponProperty;
import net.minecraft.util.math.RayTraceResult;

public class PropertyBreakableProjectile extends WeaponProperty implements IPropertyThrowable
{
	float chance;

	public PropertyBreakableProjectile(float chance)
	{
		this.chance = chance;
	}

	@Override
	public boolean dropOnImpact(EntityMSUThrowable projectile, RayTraceResult result)
	{
		return projectile.world.rand.nextFloat() <= chance;
	}
}
