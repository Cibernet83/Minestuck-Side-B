package com.mraof.minestuck.item.properties.throwkind;

import com.mraof.minestuck.entity.EntityMSUThrowable;
import com.mraof.minestuck.item.IPropertyWeapon;
import com.mraof.minestuck.item.properties.WeaponProperty;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PropertyVariableItem extends WeaponProperty implements IPropertyThrowable
{
	int variations;

	public PropertyVariableItem(int variations)
	{
		this.variations = variations;
	}

	@Override
	public boolean onProjectileThrow(EntityMSUThrowable projectile, EntityLivingBase thrower, ItemStack stack)
	{
		ItemStack thrownStack = projectile.getStack();

		if(!thrownStack.hasTagCompound())
			thrownStack.setTagCompound(new NBTTagCompound());
		thrownStack.getTagCompound().setInteger("Variant", stack.getCount() % variations);

		return true;
	}

	@SubscribeEvent
	public static void onItemPickup(EntityItemPickupEvent event)
	{
		ItemStack stack = event.getItem().getItem();
		if(stack.hasTagCompound() && stack.getItem() instanceof IPropertyWeapon &&
				((IPropertyWeapon) stack.getItem()).hasProperty(PropertyVariableItem.class, stack))
		{
			stack.getTagCompound().removeTag("Variant");
			if(stack.getTagCompound().hasNoTags())
				stack.setTagCompound(null);
		}
	}

	public IItemPropertyGetter getPropertyOverride()
	{
		return ((stack, worldIn, entityIn) -> stack.hasTagCompound() && stack.getTagCompound().hasKey("Variant") ?
				stack.getTagCompound().getInteger("Variant") : stack.getCount() % variations);
	}
}
