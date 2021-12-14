package com.mraof.minestuck.item.properties;

import com.mraof.minestuck.alchemy.GristAmount;
import com.mraof.minestuck.alchemy.GristSet;
import com.mraof.minestuck.entity.EntityMSUArrow;
import com.mraof.minestuck.entity.EntityMSUThrowable;
import com.mraof.minestuck.event.UnderlingSpoilsEvent;
import com.mraof.minestuck.item.IPropertyWeapon;
import com.mraof.minestuck.item.properties.bowkind.IPropertyArrow;
import com.mraof.minestuck.item.properties.throwkind.IPropertyThrowable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PropertyCandyWeapon extends WeaponProperty implements IPropertyArrow, IPropertyThrowable
{
	@SubscribeEvent
	public static void onUnderlingDrops(UnderlingSpoilsEvent event)
	{
		DamageSource source = event.getSource();

		if(source.getImmediateSource() instanceof EntityLivingBase)
		{
			ItemStack stack = ((EntityLivingBase) source.getImmediateSource()).getHeldItemMainhand();
			if(!(stack.getItem() instanceof  IPropertyWeapon && ((IPropertyWeapon) stack.getItem()).hasProperty(PropertyCandyWeapon.class, stack)))
				return;
		}
		else if(source.getImmediateSource() instanceof EntityMSUArrow)
		{
			ItemStack stack = ((EntityMSUArrow) source.getImmediateSource()).getBowStack();
			if(!(stack.getItem() instanceof  IPropertyWeapon && ((IPropertyWeapon) stack.getItem()).hasProperty(PropertyCandyWeapon.class, stack)))
				return;
		} else if(source.getImmediateSource() instanceof EntityMSUThrowable)
		{
			ItemStack stack = ((EntityMSUThrowable) source.getImmediateSource()).getStack();
			if(!(stack.getItem() instanceof  IPropertyWeapon && ((IPropertyWeapon) stack.getItem()).hasProperty(PropertyCandyWeapon.class, stack)))
				return;
		}

		GristSet newSpoils = new GristSet();
		for(GristAmount gristType : event.getSpoils().getArray())
		{
			int candy = (gristType.getAmount() + 2)/4;
			int gristAmount = gristType.getAmount() - candy*2;
			ItemStack candyItem = gristType.getType().getCandyItem();
			candyItem.setCount(candy);
			if(candy > 0)
				event.getDrops().add(candyItem);
			newSpoils.addGrist(gristType.getType(), gristAmount);
		}
		event.setSpoils(newSpoils);
	}
}
