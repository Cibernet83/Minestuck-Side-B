package com.mraof.minestuck.item.properties.shieldkind;

import com.mraof.minestuck.item.properties.WeaponProperty;
import com.mraof.minestuck.item.weapon.MSUShieldBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class PropertyVisualParry extends WeaponProperty implements IPropertyShield
{
	@Override
	public boolean isAbilityActive(ItemStack stack, World world, EntityLivingBase player) {
		return stack.getItem() instanceof MSUShieldBase && ((MSUShieldBase) stack.getItem()).isParrying(stack);
	}
}
