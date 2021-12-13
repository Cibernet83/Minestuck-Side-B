package com.mraof.minestuck.item.properties.throwkind;

import com.mraof.minestuck.entity.EntityMSUThrowable;
import com.mraof.minestuck.item.properties.WeaponProperty;
import net.minecraft.util.math.Vec3d;

public class PropertyBoomerang extends WeaponProperty implements IPropertyThrowable
{
	float angle;

	public PropertyBoomerang(float angle)
	{
		this.angle = angle;
	}

	public PropertyBoomerang()
	{
		this(8f);
	}

	@Override
	public void onProjectileUpdate(EntityMSUThrowable projectile)
	{
		Vec3d motionVec = new Vec3d(
				Math.cos(Math.toRadians(angle))*projectile.motionX - Math.sin(Math.toRadians(angle))*projectile.motionZ,
				projectile.motionY,
				Math.sin(Math.toRadians(angle))*projectile.motionX + Math.cos(Math.toRadians(angle))*projectile.motionZ);

		projectile.motionX = motionVec.x;
		projectile.motionY = motionVec.y;
		projectile.motionZ = motionVec.z;
	}
}
