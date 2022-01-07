package com.mraof.minestuck.item.properties;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.alchemy.GristRegistry;
import com.mraof.minestuck.alchemy.GristSet;
import com.mraof.minestuck.alchemy.MinestuckGrist;
import com.mraof.minestuck.event.LivingShockEvent;
import com.mraof.minestuck.event.handler.CommonEventHandler;
import com.mraof.minestuck.item.MinestuckItems;
import com.mraof.minestuck.network.MinestuckNetwork;
import com.mraof.minestuck.network.message.MessageResetCooldown;
import com.mraof.minestuck.util.MinestuckSoundHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.MinecraftForge;

import javax.annotation.Nullable;

public class PropertyShock extends WeaponProperty
{
	int stunTime;
	float stunDamage;
	float chance;
	boolean onCrit;

	public PropertyShock(int stunTime, float stunDamage, float chance, boolean onCrit)
	{
		this.stunDamage = stunDamage;
		this.stunTime = stunTime;
		this.chance = chance;
		this.onCrit = onCrit;
	}

	@Override
	public void onEntityHit(ItemStack stack, EntityLivingBase target, EntityLivingBase player)
	{
		if(!onCrit && (player.world.rand.nextFloat() <= chance) && (!(player instanceof EntityPlayer) || CommonEventHandler.getCooledAttackStrength(((EntityPlayer) player)) >= 1))
			shockTarget(player, target, stunTime, stunDamage);
	}

	@Override
	public float onCrit(ItemStack stack, EntityPlayer player, EntityLivingBase target, float damageModifier)
	{
		if(onCrit && (player.world.rand.nextFloat() <= chance) && (!(player instanceof EntityPlayer) || CommonEventHandler.getCooledAttackStrength(((EntityPlayer) player)) >= 1))
			shockTarget(player, target, stunTime, stunDamage);
		return super.onCrit(stack, player, target, damageModifier);
	}

	public static void shockTarget(EntityLivingBase player, EntityLivingBase target, int time, float damage)
	{
		LivingShockEvent event = new LivingShockEvent(target, player, time, damage);
		if((!target.isInWater() && target.getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem() == MinestuckItems.rubberBoots) || MinecraftForge.EVENT_BUS.post(event))
			return;

		time = event.getTime();
		damage = event.getDamage();

		if(target instanceof EntityPlayer && !target.world.isRemote)
		{
			((EntityPlayer) target).getCooldownTracker().setCooldown(target.getHeldItemMainhand().getItem(), time);
			if(player == null || target == player)
				((EntityPlayer) target).getCooldownTracker().setCooldown(target.getHeldItemOffhand().getItem(), time);
			((EntityPlayer) target).resetCooldown();
			MinestuckNetwork.sendTo(new MessageResetCooldown(), (EntityPlayer) target);
		}

		if(!player.world.isRemote)
			for(EnumHand hand : EnumHand.values())
			{
				GristSet heldGristCost = GristRegistry.getGristConversion(target.getHeldItem(hand));
				if(!target.getHeldItem(hand).isEmpty() && (player == null || player == target || (heldGristCost != null && (heldGristCost.getGrist(MinestuckGrist.rust) != 0 || heldGristCost.getGrist(MinestuckGrist.gold) != 0))))
				{
					EntityItem item = target.entityDropItem(target.getHeldItem(hand), target.getEyeHeight());
					item.setPickupDelay(20);
					target.setHeldItem(hand, ItemStack.EMPTY);
				}
			}

		target.playSound(MinestuckSoundHandler.shock, 1, Math.max(1, (target.world.rand.nextFloat() - target.world.rand.nextFloat()) * 0.2F + 1.0F));
		if(damage > 0)
		{
			target.hurtResistantTime = 0;
			target.attackEntityFrom(new ShockDamage(player), damage);
		}
		else
		{
			target.maxHurtTime = 10;
			target.hurtTime = target.maxHurtTime;
			target.limbSwingAmount = 1.5f;
			target.playSound(SoundEvents.ENTITY_PLAYER_HURT, 1, (target.world.rand.nextFloat() - target.world.rand.nextFloat()) * 0.2F + 1.0F);
		}
	}

	public static class ShockDamage extends EntityDamageSource
	{
		public ShockDamage(@Nullable Entity damageSourceEntityIn)
		{
			super(Minestuck.MODID + ".shock", damageSourceEntityIn);
			setDamageBypassesArmor();
		}

		@Override
		public ITextComponent getDeathMessage(EntityLivingBase entityLivingBaseIn)
		{
			if(getTrueSource() == null || getTrueSource().equals(entityLivingBaseIn))
				return new TextComponentTranslation("death.attack." + this.damageType + ".self", entityLivingBaseIn.getDisplayName());
			return super.getDeathMessage(entityLivingBaseIn);
		}
	}
}
