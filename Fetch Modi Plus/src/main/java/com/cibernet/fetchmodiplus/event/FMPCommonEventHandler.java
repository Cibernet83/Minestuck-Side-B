package com.cibernet.fetchmodiplus.event;

import com.cibernet.fetchmodiplus.captchalogue.CommunistModus;
import com.cibernet.fetchmodiplus.captchalogue.CycloneModus;
import com.cibernet.fetchmodiplus.captchalogue.JujuModus;
import com.cibernet.fetchmodiplus.items.BaseItem;
import com.cibernet.fetchmodiplus.items.OperandiArmorItem;
import com.cibernet.fetchmodiplus.registries.FMPItems;
import com.cibernet.fetchmodiplus.registries.FMPSounds;
import com.mraof.minestuck.inventory.captchalouge.Modus;
import com.mraof.minestuck.util.IdentifierHandler;
import com.mraof.minestuck.util.MinestuckPlayerData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class FMPCommonEventHandler
{
	@SubscribeEvent
	public static void onPlayerLogIn(PlayerEvent.PlayerLoggedInEvent event)
	{
		if(!event.player.world.isRemote && MinestuckPlayerData.getData(IdentifierHandler.encode(event.player)) != null)
		{
			MinestuckPlayerData.PlayerData data = MinestuckPlayerData.getData(IdentifierHandler.encode(event.player));
			if(data.modus instanceof JujuModus)
				((JujuModus) data.modus).sendUpdateToClients();
			else if(data.modus instanceof CommunistModus)
				((CommunistModus)data.modus).sendUpdate(event.player);
		}
	}
	@SubscribeEvent
	public static void playerTickEvent(TickEvent.PlayerTickEvent event)
	{
		if(event.phase != TickEvent.Phase.START || MinestuckPlayerData.getData(IdentifierHandler.encode(event.player)) == null)
			return;
		
		Modus modus = MinestuckPlayerData.getData(IdentifierHandler.encode(event.player)).modus;
		
		if(modus instanceof CycloneModus)
			((CycloneModus) modus).cycle();
		
		if(!event.player.world.isRemote)
		{
			if(modus instanceof JujuModus)
				((JujuModus) modus).checkUnlink(event.player);
		}
	}
	
	@SubscribeEvent
	public static void onEntityTick(TickEvent.WorldTickEvent event)
	{
		World world = event.world;
		List<Entity> mobsWithKeysList = StreamSupport.stream(world.loadedEntityList
				.spliterator(), false)
				.filter(entity -> entity instanceof EntityMob && (((EntityMob) entity).getHeldItemMainhand().getItem().equals(FMPItems.chasityKey) || ((EntityMob) entity).getHeldItemOffhand().getItem().equals(FMPItems.chasityKey)))
						.collect(Collectors.toList());
		for(Entity entity : mobsWithKeysList)
		{
			
			ItemStack stack = ((EntityMob) entity).getHeldItemMainhand();
			if(!stack.getItem().equals(FMPItems.chasityKey))
				stack = ((EntityMob)entity).getHeldItemOffhand();
				
			NBTTagCompound nbt = stack.getTagCompound();
			if(nbt == null)
			{
				nbt = new NBTTagCompound();
				stack.setTagCompound(nbt);
			}
			
			int glowTimer = nbt.getInteger("GlowTimer");
			
			if(glowTimer > 1200)
				entity.setGlowing(true);
			else nbt.setInteger("GlowTimer", glowTimer+1);
		}
	}
	
	@SubscribeEvent
	public static void onLivingDamage(LivingDamageEvent event)
	{
		ItemStack operandiArmor = ItemStack.EMPTY;
		
		if(event.getAmount() < 1)
			return;
		
		for(EntityEquipmentSlot slot : EntityEquipmentSlot.values())
		{
			if(slot.getSlotType().equals(EntityEquipmentSlot.Type.ARMOR) && event.getEntityLiving().getItemStackFromSlot(slot).getItem() instanceof OperandiArmorItem)
			{
				operandiArmor = event.getEntityLiving().getItemStackFromSlot(slot);
				break;
			}
		}
		
		if(!operandiArmor.isEmpty())
		{
			ItemStack storedStack = BaseItem.getStoredItem(operandiArmor);
			operandiArmor.damageItem(operandiArmor.getMaxDamage()+1, event.getEntityLiving());
			
			if(event.getAmount() < event.getEntityLiving().getHealth())
			{
				event.getEntityLiving().world.playSound(null, event.getEntityLiving().getPosition(), FMPSounds.operandiTaskComplete, SoundCategory.PLAYERS, 1, 1);
				
				if((event.getEntityLiving() instanceof EntityPlayer) && !((EntityPlayer) event.getEntityLiving()).addItemStackToInventory(storedStack))
					((EntityPlayer) event.getEntityLiving()).dropItem(storedStack, true);
			}
		}
	}
}
