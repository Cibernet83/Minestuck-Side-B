package com.mraof.minestuck.item.properties.beams;

import com.mraof.minestuck.capabilities.beam.Beam;
import com.mraof.minestuck.item.properties.WeaponProperty;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

public class PropertyMagicBeam extends WeaponProperty implements IPropertyBeam
{
	@Override
	public DamageSource onEntityImpact(ItemStack stack, Beam beam, Entity entity, DamageSource damageSource)
	{
		return damageSource.setMagicDamage();
	}
}
