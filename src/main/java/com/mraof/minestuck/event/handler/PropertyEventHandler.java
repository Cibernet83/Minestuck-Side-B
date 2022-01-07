package com.mraof.minestuck.event.handler;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.item.IPropertyWeapon;
import com.mraof.minestuck.item.MinestuckItems;
import com.mraof.minestuck.item.properties.WeaponProperty;
import com.mraof.minestuck.item.weapon.MSShieldBase;
import com.mraof.minestuck.network.MinestuckNetwork;
import com.mraof.minestuck.network.message.MessageLeftClickEmpty;
import com.mraof.minestuck.util.MinestuckSoundHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashMap;
import java.util.List;

@Mod.EventBusSubscriber(modid = Minestuck.MODID)
public class PropertyEventHandler
{
	@SubscribeEvent
	public static void onCrit(CriticalHitEvent event)
	{
		ItemStack stack = event.getEntityPlayer().getHeldItemMainhand();

		if(!event.getTarget().world.isRemote && event.isVanillaCritical() && stack.getItem() instanceof IPropertyWeapon && event.getTarget() instanceof EntityLivingBase)
		{
			List<WeaponProperty> propertyList = ((IPropertyWeapon) stack.getItem()).getProperties(stack);
			for(WeaponProperty p : propertyList)
				event.setDamageModifier(p.onCrit(event.getEntityPlayer().getHeldItemMainhand(), event.getEntityPlayer(), (EntityLivingBase) event.getTarget(), event.getDamageModifier()));
		}
	}

	@SubscribeEvent
	public static void onLivingHurt(LivingHurtEvent event)
	{
		if(!(event.getSource() instanceof CustomDamageSource) && event.getSource().getImmediateSource() instanceof EntityLivingBase && event.getSource().getDamageType() == "player")
		{
			ItemStack stack = ((EntityLivingBase) event.getSource().getImmediateSource()).getHeldItemMainhand();

			if(stack.getItem() instanceof IPropertyWeapon)
			{
				List<WeaponProperty> propertyList = ((IPropertyWeapon) stack.getItem()).getProperties(stack);
				for(WeaponProperty p : propertyList)
					event.setAmount(p.damageAgainstEntity(stack, (EntityLivingBase) event.getSource().getImmediateSource(), event.getEntityLiving(), event.getAmount()));
			}
		}
	}

	public static final HashMap<Item, CustomDamageSource> CUSTOM_DAMAGE = new HashMap<Item, CustomDamageSource>()
	{{
		put(MinestuckItems.cactusCutlass, new CustomDamageSource("cactus"));
		put(MinestuckItems.needlewands, (CustomDamageSource) new CustomDamageSource("magic").setDamageBypassesArmor().setMagicDamage());
		put(MinestuckItems.oglogothThorn, (CustomDamageSource) new CustomDamageSource("magic").setDamageBypassesArmor().setMagicDamage());
		put(MinestuckItems.archmageDaggers, (CustomDamageSource) new CustomDamageSource("magic").setDamageBypassesArmor().setMagicDamage());
		put(MinestuckItems.gasterBlaster, new CustomDamageSource("sans"));
		put(MinestuckItems.sbahjWhip, new CustomDamageSource("sbahj"));
		put(MinestuckItems.sord, new CustomDamageSource("sbahj"));
		put(MinestuckItems.batleacks, new CustomDamageSource("sbahj"));
	}};

	public static class CustomDamageSource extends EntityDamageSource
	{
		public CustomDamageSource(String damageTypeIn)
		{
			super(damageTypeIn, null);
		}

		public CustomDamageSource setEntitySource(Entity entitySource)
		{
			damageSourceEntity = entitySource;
			return this;
		}

		public ITextComponent getDeathMessage(EntityLivingBase entityLivingBaseIn)
		{
			ItemStack itemstack = this.damageSourceEntity instanceof EntityLivingBase ? ((EntityLivingBase)this.damageSourceEntity).getHeldItemMainhand() : ItemStack.EMPTY;
			String s = "death.attack." + Minestuck.MODID + "." + this.damageType;
			String s1 = s + ".item";
			return !itemstack.isEmpty() && itemstack.hasDisplayName() && I18n.canTranslate(s1) ? new TextComponentTranslation(s1, new Object[] {entityLivingBaseIn.getDisplayName(), this.damageSourceEntity.getDisplayName(), itemstack.getTextComponent()}) : new TextComponentTranslation(s, new Object[] {entityLivingBaseIn.getDisplayName(), this.damageSourceEntity.getDisplayName()});
		}

	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void onLeftClickEmpty(PlayerInteractEvent.LeftClickEmpty event)
	{
		MinestuckNetwork.sendToServer(new MessageLeftClickEmpty());
	}

	@SubscribeEvent
	public static void onAttack(LivingAttackEvent event)
	{
		if(!(event.getSource() instanceof CustomDamageSource) && event.getSource().getImmediateSource() instanceof EntityLivingBase && event.getSource().getDamageType() == "player")
		{
			ItemStack stack = ((EntityLivingBase) event.getSource().getImmediateSource()).getHeldItemMainhand();

			if(CUSTOM_DAMAGE.containsKey(stack.getItem()))
			{
				event.setCanceled(true);
				event.getEntity().attackEntityFrom(CUSTOM_DAMAGE.get(stack.getItem()).setEntitySource(event.getSource().getImmediateSource()), event.getAmount());
			}
		}

		if(!event.getEntityLiving().isEntityInvulnerable(event.getSource()) && !event.getEntity().world.isRemote)
		{
			ItemStack stack = event.getEntityLiving().getActiveItemStack();

			if(stack.getItem().isShield(stack, event.getEntityLiving()) && stack.getItem() instanceof MSShieldBase && (!(event.getEntityLiving() instanceof EntityPlayer) || !((EntityPlayer) event.getEntityLiving()).getCooldownTracker().hasCooldown(stack.getItem())))
				((MSShieldBase) stack.getItem()).onHitWhileShielding(stack, event.getEntityLiving(), event.getSource(), event.getAmount(), MSShieldBase.canBlockDamageSource(event.getEntityLiving(), event.getSource()));

			stack = event.getEntityLiving().getHeldItemMainhand().getItem() instanceof MSShieldBase ? event.getEntityLiving().getHeldItemMainhand()
																									: event.getEntityLiving().getHeldItemOffhand().getItem() instanceof MSShieldBase ? event.getEntityLiving().getHeldItemOffhand() : ItemStack.EMPTY;


			boolean isStunned = false;
			if(event.getEntityLiving() instanceof EntityPlayer)
				isStunned = ((EntityPlayer) event.getEntityLiving()).getCooldownTracker().hasCooldown(stack.getItem());

			if(!stack.isEmpty() && !isStunned && !event.getSource().isUnblockable() && ((MSShieldBase)stack.getItem()).isParrying(stack) && ((MSShieldBase)stack.getItem()).onShieldParry(stack, event.getEntityLiving(), event.getSource(), event.getAmount()))
			{
				if (!event.getSource().isProjectile())
				{
					Entity entity = event.getSource().getImmediateSource();
					damageShield(event.getEntityLiving(), stack, 1);

					if (entity instanceof EntityLivingBase)
						((EntityLivingBase)entity).knockBack(event.getEntityLiving(), 0.5F, event.getEntityLiving().posX - entity.posX, event.getEntityLiving().posZ - entity.posZ);
				}

				EntityLivingBase player = event.getEntityLiving();
				player.world.playSound(null, player.posX, player.posY, player.posZ, MinestuckSoundHandler.shieldParry, SoundCategory.PLAYERS, 1.0F, 0.8F + event.getEntity().world.rand.nextFloat() * 0.4F);
				event.setCanceled(true);
			}
		}
	}

	protected static void damageShield(EntityLivingBase entity, ItemStack stack, float damage)
	{
		if (entity instanceof EntityPlayer && damage >= 3.0F && stack.getItem().isShield(stack, entity))
		{
			ItemStack copyBeforeUse = stack.copy();
			int i = 1 + MathHelper.floor(damage);
			stack.damageItem(i, entity);

			if (stack.isEmpty())
			{
				EnumHand enumhand = entity.getHeldItemMainhand().equals(stack) ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND;
				net.minecraftforge.event.ForgeEventFactory.onPlayerDestroyItem((EntityPlayer) entity, copyBeforeUse, enumhand);
				entity.setItemStackToSlot(enumhand ==  EnumHand.MAIN_HAND ? EntityEquipmentSlot.MAINHAND : EntityEquipmentSlot.OFFHAND, ItemStack.EMPTY);

				entity.resetActiveHand();
				entity.playSound(SoundEvents.ITEM_SHIELD_BREAK, 0.8F, 0.8F + entity.world.rand.nextFloat() * 0.4F);
			}
		}
	}
}
