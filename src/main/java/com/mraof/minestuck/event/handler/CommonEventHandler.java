package com.mraof.minestuck.event.handler;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.MinestuckConfig;
import com.mraof.minestuck.alchemy.GristSet;
import com.mraof.minestuck.alchemy.MinestuckGrist;
import com.mraof.minestuck.enchantments.MinestuckEnchantments;
import com.mraof.minestuck.event.AlchemizeItemEvent;
import com.mraof.minestuck.event.UnderlingSpoilsEvent;
import com.mraof.minestuck.item.*;
import com.mraof.minestuck.item.properties.PropertyRandomDamage;
import com.mraof.minestuck.item.properties.WeaponProperty;
import com.mraof.minestuck.item.weapon.ItemBeamBlade;
import com.mraof.minestuck.item.weapon.MSWeaponBase;
import com.mraof.minestuck.network.MinestuckNetwork;
import com.mraof.minestuck.network.MinestuckMessage;
import com.mraof.minestuck.potions.MinestuckPotions;
import com.mraof.minestuck.util.MinestuckUtils;
import com.mraof.minestuck.world.MinestuckDimensionHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.PlaySoundAtEntityEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.commons.lang3.ArrayUtils;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = Minestuck.MODID)
public class CommonEventHandler
{
	public static final IAttribute COOLED_ATTACK_STRENGTH = new RangedAttribute(null, Minestuck.MODID + ".cooledAttackStrength", 0, 0, 1).setDescription("Cooled Attack Strength");

	@SubscribeEvent
	public static void onPlayerTick(TickEvent.PlayerTickEvent event)
	{
		if(event.player.isSpectator())
		{
			event.player.capabilities.isFlying = true;
			event.player.capabilities.allowFlying = true;
		}

		if(event.player.getAttributeMap().getAttributeInstance(COOLED_ATTACK_STRENGTH) == null)
			event.player.getAttributeMap().registerAttribute(COOLED_ATTACK_STRENGTH);

		if(event.phase == TickEvent.Phase.START)
		{
			for (EntityEquipmentSlot slot : EntityEquipmentSlot.values())
			{
				ItemStack stack = event.player.getItemStackFromSlot(slot);
				AbstractAttributeMap attrMap = event.player.getAttributeMap();

				Multimap<String, AttributeModifier> modifiers =  stack.getAttributeModifiers(slot);

				if(stack.getItem() instanceof IPropertyWeapon)
				{
					List<WeaponProperty> properties = ((IPropertyWeapon) stack.getItem()).getProperties(stack);
					for (WeaponProperty p : properties)
						p.getAttributeModifiers(event.player, stack, modifiers);
				}

				for (Map.Entry<String, AttributeModifier> attr : modifiers.entries()) {
					IAttributeInstance attrInstance = attrMap.getAttributeInstanceByName(attr.getKey());

					if (attrInstance != null && attrInstance.hasModifier(attr.getValue()) && attrInstance.getModifier(attr.getValue().getID()).getAmount() != attr.getValue().getAmount()) {
						attrInstance.removeModifier(attr.getValue().getID());
						attrInstance.applyModifier(attr.getValue());
					}
					if(!attrInstance.hasModifier(attr.getValue()))
						attrInstance.applyModifier(attr.getValue());
				}
			}

			ItemStack stack = event.player.getHeldItemMainhand();
			if(event.player.getCooldownTracker().hasCooldown(stack.getItem()))
				event.player.resetCooldown();
		}
		else if(event.phase == TickEvent.Phase.END)
		{
			double str = getCooledAttackStrength(event.player);
			double currStr = event.player.getCooledAttackStrength(0.5f);

			if(str != currStr)
				event.player.getAttributeMap().getAttributeInstance(COOLED_ATTACK_STRENGTH).setBaseValue(currStr);
		}
	}

	@SubscribeEvent
	public static void onEquipChange(LivingEquipmentChangeEvent event)
	{
		if(event.getFrom().getItem() instanceof IPropertyWeapon)
		{
			HashMultimap<String, AttributeModifier> modifiers = HashMultimap.create();
			List<WeaponProperty> properties = ((IPropertyWeapon) event.getFrom().getItem()).getProperties(event.getFrom());
			for (WeaponProperty p : properties)
				p.getAttributeModifiers(event.getEntityLiving(), event.getFrom(), modifiers);
			event.getEntityLiving().getAttributeMap().removeAttributeModifiers(modifiers);
		}
	}

	@SubscribeEvent
	public static void playerJoinWorld(EntityJoinWorldEvent event)
	{
		if(event.getEntity() instanceof EntityPlayer && ((EntityPlayer)event.getEntity()).getAttributeMap().getAttributeInstance(COOLED_ATTACK_STRENGTH) == null)
			((EntityPlayer)event.getEntity()).getAttributeMap().registerAttribute(COOLED_ATTACK_STRENGTH);
	}

	@SubscribeEvent
	public static void onEntityAttacked(AttackEntityEvent event)
	{
		if(event.getEntityLiving() instanceof EntityPlayer && ((EntityPlayer) event.getEntityLiving()).getCooldownTracker().hasCooldown(event.getEntityLiving().getHeldItemMainhand().getItem()))
			event.setCanceled(true);
	}

	public static float getCooledAttackStrength(EntityPlayer player)
	{
		return (float) player.getAttributeMap().getAttributeInstance(COOLED_ATTACK_STRENGTH).getAttributeValue();
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void onClientTick(TickEvent.ClientTickEvent event)
	{
		if(Minecraft.getMinecraft().player == null)
			return;

		EntityPlayer player = Minecraft.getMinecraft().player;

		if((player.isPotionActive(MinestuckPotions.SKYHBOUND) && player.getActivePotionEffect(MinestuckPotions.SKYHBOUND).getDuration() >= 5)
				|| (player.isCreative() && player.isPotionActive(MinestuckPotions.EARTHBOUND) && player.getActivePotionEffect(MinestuckPotions.EARTHBOUND).getDuration() < 5))
			player.capabilities.allowFlying = true;
		if((player.isPotionActive(MinestuckPotions.EARTHBOUND) && player.getActivePotionEffect(MinestuckPotions.EARTHBOUND).getDuration() >= 5)
				|| (!player.isCreative() && player.isPotionActive(MinestuckPotions.SKYHBOUND) && player.getActivePotionEffect(MinestuckPotions.SKYHBOUND).getDuration() < 5))
		{
			player.capabilities.allowFlying = false;
			player.capabilities.isFlying = false;
		}

		if(!player.isCreative() && player.isPotionActive(MinestuckPotions.CREATIVE_SHOCK))
		{
			int duration = player.getActivePotionEffect(MinestuckPotions.CREATIVE_SHOCK).getDuration();
			if(duration >= 5)
				player.capabilities.allowEdit = false;
			else player.capabilities.allowEdit = !MinestuckUtils.getPlayerGameType(player).hasLimitedInteractions();
		}


		if(player.isSpectator())
		{
			player.capabilities.isFlying = true;
			player.capabilities.allowFlying = true;
		}
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void onItemTooltip(ItemTooltipEvent event)
	{
		ItemStack stack = event.getItemStack();

		boolean isRandom = false;
		float randValue = 0;

		if(stack.getItem() instanceof IPropertyWeapon && ((IPropertyWeapon) stack.getItem()).hasProperty(PropertyRandomDamage.class, stack))
		{
			PropertyRandomDamage prop = (PropertyRandomDamage) ((IPropertyWeapon) stack.getItem()).getProperty(PropertyRandomDamage.class, stack);
			randValue = prop.getMax() * prop.getMulitiplier();
			isRandom = true;
		}

		for (EntityEquipmentSlot entityequipmentslot : EntityEquipmentSlot.values())
		{
			Multimap<String, AttributeModifier> multimap = stack.getAttributeModifiers(entityequipmentslot);

			if (!multimap.isEmpty())
			{
				for (Map.Entry<String, AttributeModifier> entry : multimap.entries())
				{
					AttributeModifier attributemodifier = entry.getValue();
					double d0 = attributemodifier.getAmount();

					if (event.getEntityPlayer() != null)
					{
						if(attributemodifier.getID().equals(UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF")))
						{
							d0 = d0 + event.getEntityPlayer().getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getBaseValue();
							d0 = d0 + (double) EnchantmentHelper.getModifierForCreature(stack, EnumCreatureAttribute.UNDEFINED);
							double d1;
							if (attributemodifier.getOperation() != 1 && attributemodifier.getOperation() != 2)
								d1 = d0;
							else d1 = d0 * 100.0D;

							String attackString = (" " + I18n.translateToLocalFormatted("attribute.modifier.equals." + attributemodifier.getOperation(), ItemStack.DECIMALFORMAT.format(d1), I18n.translateToLocal("attribute.name." + entry.getKey())));

							if (isRandom)
							{
								String newAttackString = (" " + I18n.translateToLocalFormatted("attribute.modifier.equals." + attributemodifier.getOperation(), (ItemStack.DECIMALFORMAT.format(d1) + "-" + ItemStack.DECIMALFORMAT.format(d1+randValue)), I18n.translateToLocal("attribute.name." + entry.getKey())));

								if(event.getToolTip().contains(attackString))
									event.getToolTip().set(event.getToolTip().indexOf(attackString), newAttackString);
								else event.getToolTip().add(newAttackString);
								attackString = newAttackString;
							}

							if(MinestuckDimensionHandler.isLandDimension(event.getEntityPlayer().world.provider.getDimension()) && stack.getItem() instanceof MSWeaponBase && event.getToolTip().contains(attackString))
							{
								d0 = ((MSWeaponBase)stack.getItem()).getUnmodifiedAttackDamage(stack);
								if (attributemodifier.getOperation() != 1 && attributemodifier.getOperation() != 2)
									d1 = d0;
								else d1 = d0 * 100.0D;

								String newAttackString;
								if(isRandom)
									newAttackString = (" " + I18n.translateToLocalFormatted("attribute.modifier.equals." + attributemodifier.getOperation(), (ItemStack.DECIMALFORMAT.format(d1) + "-" + ItemStack.DECIMALFORMAT.format(d1+randValue)), I18n.translateToLocal("attribute.name.underling.attackDamage")));
								else newAttackString = (" " + I18n.translateToLocalFormatted("attribute.modifier.equals." + attributemodifier.getOperation(), (ItemStack.DECIMALFORMAT.format(d1)), I18n.translateToLocal("attribute.name.underling.attackDamage")));
								event.getToolTip().add(event.getToolTip().indexOf(attackString)+1, newAttackString);
							}
						}
					}

				}
			}
		}
	}

	@SubscribeEvent
	public static void onPotionRemove(PotionEvent.PotionRemoveEvent event)
	{
		onPotionEnd(event.getEntityLiving(), event.getPotion());
	}

	@SubscribeEvent
	public static void onPotionExpire(PotionEvent.PotionExpiryEvent expiryEvent)
	{
		onPotionEnd(expiryEvent.getEntityLiving(), expiryEvent.getPotionEffect().getPotion());
	}

	private static void onPotionEnd(EntityLivingBase entityLiving, Potion potion)
	{
		if(entityLiving instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) entityLiving;

			if(potion == MinestuckPotions.EARTHBOUND && player.isCreative())
			{
				MinestuckNetwork.sendTo(MinestuckMessage.makePacket(MinestuckMessage.Type.FLIGHT_EFFECT, potion == MinestuckPotions.EARTHBOUND), player);
				player.capabilities.allowFlying = true;
			}
			if(potion == MinestuckPotions.SKYHBOUND && !player.isCreative())
			{
				MinestuckNetwork.sendTo(MinestuckMessage.makePacket(MinestuckMessage.Type.FLIGHT_EFFECT, potion == MinestuckPotions.EARTHBOUND), player);
				player.capabilities.allowFlying = false;
				player.capabilities.isFlying = false;
			}
			if(!player.isCreative() && potion == MinestuckPotions.CREATIVE_SHOCK)
			{
				player.capabilities.allowEdit = !MinestuckUtils.getPlayerGameType(player).hasLimitedInteractions();
				MinestuckNetwork.sendTo(MinestuckMessage.makePacket(MinestuckMessage.Type.BUILD_INHIBIT_EFFECT), player);
			}
		}
	}

	@SubscribeEvent
	public static void onBreakSpeed(PlayerEvent.BreakSpeed event)
	{
		if(event.getEntityPlayer().isPotionActive(MinestuckPotions.CREATIVE_SHOCK))
			event.setNewSpeed(0);
	}

	@SubscribeEvent
	public static void onHarvestCheck(PlayerEvent.HarvestCheck event)
	{
		if(event.getEntityPlayer().isPotionActive(MinestuckPotions.CREATIVE_SHOCK))
			event.setCanHarvest(false);
	}

	@SubscribeEvent
	public static void onKnockback(LivingKnockBackEvent event)
	{
		if(event.getAttacker() instanceof EntityPlayer)
			event.setStrength(event.getStrength() + Math.max(0, 0.5f*(EnchantmentHelper.getMaxEnchantmentLevel(MinestuckEnchantments.SUPERPUNCH, (EntityLivingBase) event.getAttacker())-1)));
	}

	@SubscribeEvent
	public static void playSoundEvent(PlaySoundAtEntityEvent entityEvent)
	{
		if(SoundEvents.ITEM_ARMOR_EQUIP_GENERIC.equals(entityEvent.getSound()) && entityEvent.getEntity() instanceof EntityPlayer && ((EntityPlayer) entityEvent.getEntity()).getActiveItemStack().getItem() instanceof MSItemBase)
			entityEvent.setCanceled(true);
	}

	@SubscribeEvent
	public static void onBlockDrops(BlockEvent.HarvestDropsEvent event)
	{
		//pebbles
		if(!new ItemStack(event.getState().getBlock()).isEmpty() && ArrayUtils.contains(OreDictionary.getOreIDs(new ItemStack(event.getState().getBlock())), OreDictionary.getOreID("dirt")) &&
			event.getHarvester() != null && event.getHarvester().getHeldItemMainhand().isEmpty() && event.getHarvester().getRNG().nextFloat() < 0.4f)
				event.getDrops().add(new ItemStack(MinestuckItems.pebble, event.getHarvester().getRNG().nextInt(4)));

	}

	@SubscribeEvent
	public static void onRightClickEmpty(PlayerInteractEvent.RightClickItem event)
	{
		if(event.getItemStack().getItem() == Items.PAPER && event.getItemStack().getCount() == 1)
		{
			event.getEntityPlayer().setHeldItem(event.getHand(), new ItemStack(MinestuckItems.rolledUpPaper));
			event.getEntityPlayer().swingArm(event.getHand());
		}
	}

	public static Vec3d getVecFromRotation(float pitch, float yaw)
	{
		float f = MathHelper.cos(-yaw * 0.017453292F - (float)Math.PI);
		float f1 = MathHelper.sin(-yaw * 0.017453292F - (float)Math.PI);
		float f2 = -MathHelper.cos(-pitch * 0.017453292F);
		float f3 = MathHelper.sin(-pitch * 0.017453292F);
		return new Vec3d((double)(f1 * f2), (double)f3, (double)(f * f2)).normalize();
	}

	@SubscribeEvent
	public static void onAlchemize(AlchemizeItemEvent event)
	{
		if(event.getResultItem().getItem() instanceof ItemBeamBlade)
			ItemBeamBlade.changeState(event.getResultItem(), false);
	}

	@SubscribeEvent
	public static void onUnderlingDrops(UnderlingSpoilsEvent event)
	{
		event.getSpoils().scaleGrist((float) MinestuckConfig.gristDropsMultiplier);

		if(event.getUnderling().getRNG().nextFloat() <= 0.001f)
			event.getSpoils().addGrist(new GristSet(MinestuckGrist.zillium, 1));
	}
}
