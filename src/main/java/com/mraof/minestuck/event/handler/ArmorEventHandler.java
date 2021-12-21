package com.mraof.minestuck.event.handler;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.item.MinestuckItems;
import com.mraof.minestuck.item.armor.ItemPogoBoots;
import com.mraof.minestuck.network.MinestuckChannelHandler;
import com.mraof.minestuck.network.MinestuckPacket;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.WorldServer;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber(modid = Minestuck.MODID)
public class ArmorEventHandler
{
	@SubscribeEvent
	public static void onEntityHurt(LivingDamageEvent event)
	{
		if(event.getEntityLiving().getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem().equals(MinestuckItems.spikedHelmet))
		{
			if(event.getEntityLiving().world.rand.nextInt(2) == 0 && event.getSource().getImmediateSource() != null)
				event.getSource().getImmediateSource().attackEntityFrom(DamageSource.causeThornsDamage(event.getEntityLiving()), 4);
		}
		if(event.getEntityLiving().getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem().equals(MinestuckItems.wizardHat) && event.getSource().isMagicDamage())
		{
			event.setAmount(event.getAmount()*0.5f);
			event.getEntityLiving().getItemStackFromSlot(EntityEquipmentSlot.HEAD).damageItem(1, event.getEntityLiving());
		}
		if(event.getEntityLiving().getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem().equals(MinestuckItems.archmageHat) && event.getSource().isMagicDamage())
		{
			event.setAmount(event.getAmount()*0.2f);
			event.getEntityLiving().getItemStackFromSlot(EntityEquipmentSlot.HEAD).damageItem(1, event.getEntityLiving());
		}
	}

	@SubscribeEvent
	public static void onFall(LivingFallEvent event)
	{
		Item footwear = event.getEntityLiving().getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem();
		if (footwear instanceof ItemPogoBoots) {
			EntityLivingBase entity = event.getEntityLiving();
			ItemPogoBoots item = (ItemPogoBoots) event.getEntityLiving().getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem();

			if (!entity.isSneaking())
			{
				double motion = Math.min(10, Math.sqrt(event.getDistance())*0.3)*item.power;

				if(motion > 0.115)
				{
					entity.motionY = motion;
					entity.motionX *= 1.5f;
					entity.motionZ /= 1.5f;

					entity.velocityChanged = true;
					entity.isAirBorne = true;
					entity.onGround = false;
					entity.fallDistance = 0;
					event.setDamageMultiplier(0);
				}

				if (item.isSolar() && entity.motionY > item.power*1.2f)
					entity.setFire(5);
			}
		}
		else if(footwear == MinestuckItems.rocketBoots)
			event.setDamageMultiplier(event.getDamageMultiplier()*0.4f);
	}

	@SubscribeEvent
	public static void onJump(LivingEvent.LivingJumpEvent event)
	{
		EntityLivingBase entity = event.getEntityLiving();
		Item footwear = entity.getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem();

		if (footwear instanceof ItemPogoBoots)
		{
			ItemPogoBoots item = (ItemPogoBoots) event.getEntityLiving().getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem();

			if (!entity.isSneaking())
				entity.motionY *= 0.25 + item.power;
		}
		else if(footwear == MinestuckItems.windWalkers)
			entity.motionY *= 2f;
		else if(footwear == MinestuckItems.airJordans || footwear == MinestuckItems.cobaltJordans)
		{
			for(int i = 0; i < 10; i++)
				entity.world.spawnParticle(EnumParticleTypes.CLOUD, entity.posX + entity.getRNG().nextFloat()-0.5f, entity.posY + entity.getRNG().nextFloat()*0.1f-0.05f, entity.posZ + entity.getRNG().nextFloat()-0.5f,
						0, 0, 0);

			if(entity.getRNG().nextFloat() < 0.3f)
				entity.getItemStackFromSlot(EntityEquipmentSlot.FEET).damageItem(1, entity);
		}
	}


	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void onInputUpdate(InputUpdateEvent event)
	{
		ItemStack footwear = event.getEntityPlayer().getItemStackFromSlot(EntityEquipmentSlot.FEET);
		if(!event.getEntityPlayer().isElytraFlying() && event.getMovementInput().jump && footwear.getItem() == MinestuckItems.rocketBoots)
		{
			event.getEntityPlayer().moveRelative(event.getMovementInput().moveStrafe*0.5f, 1, event.getMovementInput().moveForward*0.5f, 0.165f);
			event.getEntityPlayer().fallDistance *= 0.8f;
			MinestuckChannelHandler.sendToServer(MinestuckPacket.makePacket(MinestuckPacket.Type.ROCKET_BOOTS));
		}
	}

	@SubscribeEvent
	public static void onPlayerTick(TickEvent.PlayerTickEvent event)
	{

		Item footwear = event.player.getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem();
		if(footwear == MinestuckItems.airJordans || footwear == MinestuckItems.cobaltJordans)
			event.player.onGround = true;
		else if(footwear == MinestuckItems.windWalkers)
		{
			event.player.motionY *= 0.65 +0.3 * Math.max(0, Math.min(1, event.player.motionY*50f));
			event.player.fallDistance = 0;
			if(!event.player.world.isRemote && !event.player.onGround && event.player.motionY < 0)
				((WorldServer)event.player.world).spawnParticle(EnumParticleTypes.CLOUD, event.player.posX, event.player.posY, event.player.posZ, 1, 0.25D, 0.1D, 0.25D, 0.05D);

		}
	}
}
