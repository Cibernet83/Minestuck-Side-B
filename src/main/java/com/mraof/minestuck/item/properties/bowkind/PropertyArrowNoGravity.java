package com.mraof.minestuck.item.properties.bowkind;

import com.mraof.minestuck.entity.EntityMSUArrow;
import com.mraof.minestuck.item.properties.WeaponProperty;

public class PropertyArrowNoGravity extends WeaponProperty implements IPropertyArrow
{
	@Override
	public boolean hasGravity(EntityMSUArrow arrow)
	{
		return false;
	}

	@Override
	public void onProjectileUpdate(EntityMSUArrow arrow)
	{
		if (Math.abs(arrow.motionX) + Math.abs(arrow.motionY) + Math.abs(arrow.motionZ) < 0.01f)
			arrow.setDead();
	}
}
