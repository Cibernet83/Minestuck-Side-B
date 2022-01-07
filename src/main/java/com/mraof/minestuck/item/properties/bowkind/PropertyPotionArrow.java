package com.mraof.minestuck.item.properties.bowkind;

import com.mraof.minestuck.entity.EntityMSUArrow;
import com.mraof.minestuck.item.properties.WeaponProperty;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.RayTraceResult;

public class PropertyPotionArrow extends WeaponProperty implements IPropertyArrow
{
	PotionEffect effect;
	float chance;

	public PropertyPotionArrow(PotionEffect effect, float chance)
	{
		this.effect = effect;
		this.chance = chance;
	}

	@Override
	public boolean onEntityImpact(EntityMSUArrow arrow, RayTraceResult result)
	{
		if (!arrow.world.isRemote && arrow.world.rand.nextFloat() <= chance && result.entityHit instanceof EntityLivingBase)
			((EntityLivingBase) result.entityHit).addPotionEffect(new PotionEffect(effect.getPotion(), effect.getDuration(), effect.getAmplifier(), effect.getIsAmbient(), effect.doesShowParticles()));

		return true;
	}
}
