package com.mraof.minestuck.item.properties.throwkind;

import com.mraof.minestuck.entity.EntityMSUThrowable;
import com.mraof.minestuck.item.properties.WeaponProperty;
import net.minecraft.util.math.RayTraceResult;

public class PropertyFirePrjctle extends WeaponProperty implements IPropertyThrowable
{
	boolean visualFire;
	int fireSeconds;

	public PropertyFirePrjctle(int fireTime, boolean visualFire)
	{
		this.fireSeconds = fireTime;
		this.visualFire = visualFire;
	}

	@Override
	public void onProjectileUpdate(EntityMSUThrowable projectile)
	{
		if(visualFire && !projectile.isBurning() && !projectile.isInWater())
			projectile.setFire(1000);
	}

	@Override
	public boolean onEntityImpact(EntityMSUThrowable projectile, RayTraceResult result)
	{
		if(!projectile.isInWater())
			result.entityHit.setFire(fireSeconds);
		return true;
	}
}
