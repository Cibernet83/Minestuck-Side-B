package com.mraof.minestuck.item.properties.throwkind;

import com.mraof.minestuck.entity.EntityMSUThrowable;
import com.mraof.minestuck.item.properties.WeaponProperty;
import com.mraof.minestuck.item.properties.bowkind.PropertyHookshot;
import net.minecraft.entity.item.EntityItem;

public class PropertyPrjctleItemPull extends WeaponProperty implements IPropertyThrowable
{
	float radius;
	float strength;

	public PropertyPrjctleItemPull(float radius, float strength)
	{
		this.radius = radius;
		this.strength = strength;
	}

	@Override
	public void onProjectileUpdate(EntityMSUThrowable projectile)
	{
		for(EntityItem target : projectile.world.getEntitiesWithinAABB(EntityItem.class, projectile.getEntityBoundingBox().grow(radius)))
			PropertyHookshot.moveTowards(target, projectile, strength*2f);
	}
}
