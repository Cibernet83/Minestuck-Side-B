package com.cibernet.minestuckgodtier.events.handlers;

import com.cibernet.minestuckgodtier.MinestuckGodTier;
import com.cibernet.minestuckuniverse.items.MinestuckUniverseItems;
import com.mraof.minestuck.item.MinestuckItems;
import com.mraof.minestuck.world.storage.loot.MinestuckLoot;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.UUID;

public class CommonEventHandler
{

	private static final ArrayList<Item> MAGIC_ITEMS = new ArrayList<Item>()
	{{
		add(MinestuckUniverseItems.moonstoneChisel);
		add(MinestuckUniverseItems.needlewands);
		add(MinestuckUniverseItems.oglogothThorn);
		add(MinestuckUniverseItems.echidnaQuills);
	}};

	//Custom Weapon Damage types
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void onPlayerAttackHighest(LivingAttackEvent event)
	{
		if(!event.getSource().isMagicDamage() && event.getSource() instanceof EntityDamageSource && event.getSource().getTrueSource() instanceof EntityLivingBase)
		{
			if(((EntityLivingBase) event.getSource().getTrueSource()).getHeldItemMainhand().getItem().equals(MinestuckItems.cactusCutlass))
			{
				event.getEntityLiving().attackEntityFrom(DamageSource.CACTUS, event.getAmount());
				event.setCanceled(true);
				return;
			}
			if(MAGIC_ITEMS.contains(((EntityLivingBase)event.getSource().getTrueSource()).getHeldItemMainhand().getItem()) || (Item.REGISTRY.containsKey(new ResourceLocation("randomthings","spectresword")) &&
					((EntityLivingBase)event.getSource().getTrueSource()).getHeldItemMainhand().getItem().equals(Item.REGISTRY.getObject(new ResourceLocation("randomthings", "spectresword")))))
			{
				event.getEntityLiving().attackEntityFrom(DamageSource.causeIndirectMagicDamage(event.getSource().getTrueSource(), event.getSource().getTrueSource()), event.getAmount());
				event.setCanceled(true);
				return;
			}
		}
	}

	public static UUID CUEBALL_ATTACK_MODIFIER = UUID.fromString("02d7689b-b586-42be-a75c-d6c28c737a70");

	@SubscribeEvent(receiveCanceled = true, priority = EventPriority.LOWEST)
	public static void onPlayerAttackLowest(LivingAttackEvent event)
	{
		if(event.getSource() instanceof EntityDamageSource && event.getSource().getTrueSource() instanceof EntityLivingBase)
		{
			EntityLivingBase sauce = ((EntityLivingBase) event.getSource().getTrueSource());
			ItemStack stack = sauce.getHeldItemMainhand();
			if(stack.getItem().equals(MinestuckItems.caledfwlch) || stack.getItem().equals(MinestuckItems.mwrthwl))
			{
				event.setCanceled(false);
			}
		}
	}

	@SubscribeEvent(receiveCanceled = true, priority = EventPriority.LOWEST)
	public static void onPlayerAttackLowestButAsAPlayerEventThisTime(AttackEntityEvent event)
	{
		EntityLivingBase sauce = event.getEntityPlayer();
		ItemStack stack = sauce.getHeldItemMainhand();
		if(stack.getItem().equals(MinestuckItems.caledfwlch) || stack.getItem().equals(MinestuckItems.mwrthwl))
		{
			event.setCanceled(false);

			if (sauce.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getModifier(CUEBALL_ATTACK_MODIFIER) != null)
				sauce.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).removeModifier(CUEBALL_ATTACK_MODIFIER);

			float cooldown = ((EntityPlayer)sauce).getCooledAttackStrength(0);
			sauce.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).applyModifier(new AttributeModifier(CUEBALL_ATTACK_MODIFIER, "Cueball weapon modifier", 0.2F + cooldown * cooldown * 0.8F, 1));
		}
	}

	@SubscribeEvent(priority = EventPriority.LOWEST, receiveCanceled = true)
	public static void onLivingHurt(LivingHurtEvent event)
	{
		if(event.getSource() instanceof EntityDamageSource && event.getSource().getTrueSource() instanceof EntityLivingBase)
		{
			EntityLivingBase sauce = ((EntityLivingBase) event.getSource().getTrueSource());
			ItemStack stack = sauce.getHeldItemMainhand();


			//Cueball damage
			if(stack.getItem().equals(MinestuckItems.caledfwlch) || stack.getItem().equals(MinestuckItems.mwrthwl))
			{
				float dmg = 1; // Yeah it's a thing, starts at 1
				for (AttributeModifier modifier : stack.getAttributeModifiers(EntityEquipmentSlot.MAINHAND).get(SharedMonsterAttributes.ATTACK_DAMAGE.getName()))
					dmg += modifier.getAmount(); // It's type 0 and there's just the one of them so

				AttributeModifier mod = sauce.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getModifier(CUEBALL_ATTACK_MODIFIER);
				if (mod != null)
					dmg *= mod.getAmount();

				event.setAmount(dmg);
				event.setCanceled(false);
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.LOWEST, receiveCanceled = true)
	public static void onLivingDamage(LivingDamageEvent event)
	{
		if(event.getSource() instanceof EntityDamageSource && event.getSource().getTrueSource() instanceof EntityLivingBase)
		{
			EntityLivingBase sauce = ((EntityLivingBase) event.getSource().getTrueSource());
			ItemStack stack = sauce.getHeldItemMainhand();

			//Cueball damage
			if(stack.getItem().equals(MinestuckItems.caledfwlch) || stack.getItem().equals(MinestuckItems.mwrthwl))
			{
				float dmg = 1; // Yeah it's a thing, starts at 1
				for (AttributeModifier modifier : stack.getAttributeModifiers(EntityEquipmentSlot.MAINHAND).get(SharedMonsterAttributes.ATTACK_DAMAGE.getName()))
					 dmg += modifier.getAmount(); // It's type 0 and there's just the one of them so

				AttributeModifier mod = sauce.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getModifier(CUEBALL_ATTACK_MODIFIER);
				if (mod != null)
					dmg *= mod.getAmount();

				event.setAmount(dmg);
				event.setCanceled(false);
			}
		}
	}

	//kill xp and grist clumps
	@SubscribeEvent
	public static void onWorldTick(TickEvent.WorldTickEvent event)
	{
		/*
		event.world.getEntities(EntityGrist.class, e -> !e.isDead).forEach(grist ->
		{
			if(!grist.isDead)
			{
				List<EntityGrist> list = event.world.getEntitiesWithinAABB(EntityGrist.class, grist.getEntityBoundingBox().grow(10));
				if(list.size() > 80);
					grist.setDead();
			}
		});
		event.world.getEntities(EntityXPOrb.class, e -> !e.isDead).forEach(xpOrb ->
		{
			if(!xpOrb.isDead)
			{
				List<EntityXPOrb> list = event.world.getEntitiesWithinAABB(EntityXPOrb.class, xpOrb.getEntityBoundingBox().grow(10));
				if(list.size() > 80);
					xpOrb.setDead();
			}
		});
		*/
	}

	@SubscribeEvent
	public static void onLootInject(LootTableLoadEvent event)
	{
		//Inject Tome and RBT record into shop loot
		if(event.getName().equals(MinestuckLoot.CONSORT_GENERAL_STOCK))
		{
			LootPool inject = event.getLootTableManager().getLootTableFromLocation(new ResourceLocation(MinestuckGodTier.MODID, "inject/consort_general")).getPool("items");
			event.getTable().getPool("items").addEntry(inject.getEntry(MinestuckGodTier.MODID+":tome_of_the_ancients"));
			event.getTable().getPool("items").addEntry(inject.getEntry("minestuck:record_retro_battle"));
		}
	}
}
