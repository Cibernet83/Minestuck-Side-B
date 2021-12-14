package com.mraof.minestuck.item.properties.throwkind;

import com.mraof.minestuck.entity.EntityMSUThrowable;
import com.mraof.minestuck.item.properties.WeaponProperty;

public class PropertyThrowGravity extends WeaponProperty implements IPropertyThrowable
{
	float gravity;

	public PropertyThrowGravity(float gravity)
	{
		this.gravity = gravity;
	}

	@Override
	public float getGravity(EntityMSUThrowable projectile, float gravity)
	{
		return this.gravity*gravity;
	}
}
