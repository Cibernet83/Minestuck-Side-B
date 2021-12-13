package com.mraof.minestuck.item.properties.throwkind;

import com.mraof.minestuck.entity.EntityMSUThrowable;
import com.mraof.minestuck.item.properties.WeaponProperty;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;

public class PropertyMagicDamagePrjctle extends WeaponProperty implements IPropertyThrowable
{
	float damage;

	public PropertyMagicDamagePrjctle(float damage)
	{
		this.damage = damage;
	}

	@Override
	public boolean onEntityImpact(EntityMSUThrowable throwable, RayTraceResult result)
	{
		result.entityHit.attackEntityFrom(DamageSource.causeIndirectMagicDamage(throwable, throwable.getThrower()), damage);
		return true;
	}
}
