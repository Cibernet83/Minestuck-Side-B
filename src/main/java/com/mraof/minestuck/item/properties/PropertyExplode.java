package com.mraof.minestuck.item.properties;

import com.mraof.minestuck.event.handler.CommonEventHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class PropertyExplode extends WeaponProperty
{
	boolean onCrit;
	float chance;
	float strength;

	public PropertyExplode(float strength, float chance, boolean onCrit)
	{
		this.strength = strength;
		this.chance = chance;
		this.onCrit = onCrit;
	}

	@Override
	public void onEntityHit(ItemStack stack, EntityLivingBase target, EntityLivingBase player)
	{
		if(!onCrit && (player.world.rand.nextFloat() <= chance) && (!(player instanceof EntityPlayer) || CommonEventHandler.getCooledAttackStrength(((EntityPlayer) player)) >= 1))
		{
			target.hurtResistantTime = 0;
			target.world.createExplosion(null, target.posX, target.posY, target.posZ, strength, net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(player.world, player));
		}
	}

	@Override
	public float onCrit(ItemStack stack, EntityPlayer player, EntityLivingBase target, float damageModifier)
	{
		if(onCrit && (player.world.rand.nextFloat() <= chance) && (!(player instanceof EntityPlayer) || CommonEventHandler.getCooledAttackStrength(((EntityPlayer) player)) >= 1))
		{
			target.hurtResistantTime = 0;
			target.world.createExplosion(null, target.posX, target.posY, target.posZ, strength, net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(player.world, player));
		}
		return super.onCrit(stack, player, target, damageModifier);
	}


}
