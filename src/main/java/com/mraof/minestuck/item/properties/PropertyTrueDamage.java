package com.mraof.minestuck.item.properties;

import com.mraof.minestuck.event.handlers.CommonEventHandler;
import com.mraof.minestuck.item.IPropertyWeapon;
import com.mraof.minestuck.item.weapon.MSUWeaponBase;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentDamage;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PropertyTrueDamage extends WeaponProperty implements IEnchantableProperty
{
	@SubscribeEvent
	public static void onEntityAttack(LivingDamageEvent event)
	{
		if(event.getSource().getImmediateSource() instanceof EntityLivingBase)
		{
			EntityLivingBase source = ((EntityLivingBase) event.getSource().getTrueSource());
			ItemStack stack = source.getHeldItemMainhand();

			if(stack.getItem() instanceof IPropertyWeapon && ((IPropertyWeapon) stack.getItem()).hasProperty(PropertyTrueDamage.class, stack))
			{
				float pow = source instanceof EntityPlayer ? CommonEventHandler.getCooledAttackStrength((EntityPlayer) source) : 1;
				event.setAmount((float) ((source.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getModifier(MSUWeaponBase.getAttackDamageUUID()).getAmount() +
						source.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getBaseValue()) * (0.2F + pow * pow * 0.8F)));
			}
		}
	}

	@Override
	public Boolean canEnchantWith(ItemStack stack, Enchantment enchantment)
	{
		return (enchantment instanceof EnchantmentDamage) ? false : null;
	}
}
