package com.mraof.minestuck.item.properties;

import com.mraof.minestuck.entity.EntityMSUArrow;
import com.mraof.minestuck.entity.EntityMSUThrowable;
import com.mraof.minestuck.item.properties.bowkind.IPropertyArrow;
import com.mraof.minestuck.item.properties.throwkind.IPropertyThrowable;
import com.mraof.minestuck.entity.underling.EntityUnderling;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RayTraceResult;

public class PropertyCandyWeapon extends WeaponProperty implements IPropertyArrow, IPropertyThrowable
{
	@Override
	public void onEntityHit(ItemStack stack, EntityLivingBase target, EntityLivingBase player) {
		if(target instanceof EntityUnderling)
			((EntityUnderling) target).dropCandy = true;
	}

	@Override
	public boolean onEntityImpact(EntityMSUArrow arrow, RayTraceResult result)
	{
		if(result.entityHit instanceof EntityUnderling)
			((EntityUnderling) result.entityHit).dropCandy = true;
		return true;
	}

	@Override
	public boolean onEntityImpact(EntityMSUThrowable projectile, RayTraceResult result)
	{
		if(result.entityHit instanceof EntityUnderling)
			((EntityUnderling) result.entityHit).dropCandy = true;
		return true;
	}
}
