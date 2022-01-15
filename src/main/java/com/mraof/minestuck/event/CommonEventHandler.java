package com.mraof.minestuck.event;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.MinestuckConfig;
import com.mraof.minestuck.block.MinestuckBlocks;
import com.mraof.minestuck.captchalogue.captchalogueable.ICaptchalogueable;
import com.mraof.minestuck.entity.underling.EntityUnderling;
import com.mraof.minestuck.item.MinestuckItems;
import com.mraof.minestuck.item.operandi.ItemCruxiteArmor;
import com.mraof.minestuck.network.skaianet.SburbHandler;
import com.mraof.minestuck.network.skaianet.SkaianetHandler;
import com.mraof.minestuck.util.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Mod.EventBusSubscriber(modid = Minestuck.MODID)
public class CommonEventHandler
{
	public static long lastDay;

	public static List<PostEntryTask> tickTasks = new ArrayList<PostEntryTask>();
	//Gets reset after AttackEntityEvent but before LivingHurtEvent, but is used in determining if it's a critical hit
	private static float cachedCooledAttackStrength = 0;

	@SubscribeEvent
	public static void onWorldTick(TickEvent.WorldTickEvent event)
	{
		if (event.phase == TickEvent.Phase.END)
		{

			if (!MinestuckConfig.hardMode && event.world.provider.getDimension() == 0)
			{
				long time = event.world.getWorldTime() / 24000L;
				if (time != lastDay)
				{
					lastDay = time;
					SkaianetHandler.resetGivenItems();
				}
			}

			Iterator<PostEntryTask> iter = tickTasks.iterator();
			while (iter.hasNext())
				if (iter.next().onTick(event.world.getMinecraftServer()))
					iter.remove();
		}
	}

	@SubscribeEvent(priority = EventPriority.LOWEST, receiveCanceled = false)
	public static void onEntityDeath(LivingDeathEvent event)
	{
		if (event.getEntity() instanceof IMob && event.getSource().getTrueSource() instanceof EntityPlayerMP && !(event.getSource().getTrueSource() instanceof FakePlayer))
		{
			EntityPlayerMP player = (EntityPlayerMP) event.getSource().getTrueSource();
			int exp = 0;
			if (event.getEntity() instanceof EntityZombie || event.getEntity() instanceof EntitySkeleton)
				exp = 6;
			else if (event.getEntity() instanceof EntityCreeper || event.getEntity() instanceof EntitySpider || event.getEntity() instanceof EntitySilverfish)
				exp = 5;
			else if (event.getEntity() instanceof EntityEnderman || event.getEntity() instanceof EntityBlaze || event.getEntity() instanceof EntityWitch || event.getEntity() instanceof EntityGuardian)
				exp = 12;
			else if (event.getEntity() instanceof EntitySlime)
				exp = ((EntitySlime) event.getEntity()).getSlimeSize() - 1;

			if (exp > 0)
				Echeladder.increaseProgress(player, exp);
		}
		if (event.getEntity() instanceof EntityPlayerMP && !(event.getSource().getTrueSource() instanceof FakePlayer))
			SburbHandler.stopEntry((EntityPlayerMP) event.getEntity());
	}

	@SubscribeEvent
	public static void onPlayerAttack(AttackEntityEvent event) //TODO merge into MSU's cooldown thing
	{
		cachedCooledAttackStrength = event.getEntityPlayer().getCooledAttackStrength(0.5F);
	}

	@SubscribeEvent(priority = EventPriority.NORMAL)
	public static void onEntityAttack(LivingHurtEvent event)
	{
		if (event.getSource().getTrueSource() != null)
		{
			Entity trueSource = event.getSource().getTrueSource();
			if (trueSource instanceof EntityPlayerMP && !(trueSource instanceof FakePlayer))
			{
				EntityPlayerMP player = (EntityPlayerMP) trueSource;
				if (event.getEntityLiving() instanceof EntityUnderling)
				{    //Increase damage to underling
					double modifier = MinestuckPlayerData.getData(player).echeladder.getUnderlingDamageModifier();
					event.setAmount((float) (event.getAmount() * modifier));
				}
			}
			else if (event.getEntityLiving() instanceof EntityPlayerMP && !(event.getEntityLiving() instanceof FakePlayer) && trueSource instanceof EntityUnderling)
			{    //Decrease damage to player
				EntityPlayerMP player = (EntityPlayerMP) event.getEntityLiving();
				double modifier = MinestuckPlayerData.getData(player).echeladder.getUnderlingProtectionModifier();
				event.setAmount((float) (event.getAmount() * modifier));
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.LOWEST, receiveCanceled = false)
	public static void onEntityDamage(LivingHurtEvent event)
	{
		if (event.getEntityLiving() instanceof EntityUnderling)
		{
			((EntityUnderling) event.getEntityLiving()).onEntityDamaged(event.getSource(), event.getAmount());
		}
	}

	@SubscribeEvent
	public static void playerChangedDimension(PlayerChangedDimensionEvent event)
	{
		SburbHandler.stopEntry(event.player);

		MinestuckPlayerData.getData(event.player).echeladder.resendAttributes(event.player);
	}

	/*
	@SubscribeEvent(priority=EventPriority.LOW, receiveCanceled=false)
	public static void onServerChat(ServerChatEvent event)
	{
		Modus modus = MinestuckPlayerData.getData(event.getPlayer()).modus;
		if(modus instanceof HashmapModus)
			((HashmapModus) modus).onChatMessage(event.getMessage());
	}
	*/

	//This functionality uses an event to maintain compatibility with mod items having hoe functionality but not extending ItemHoe, like TiCon mattocks.
	@SubscribeEvent
	public static void onPlayerUseHoe(UseHoeEvent event)
	{
		if (event.getWorld().getBlockState(event.getPos()).getBlock() == MinestuckBlocks.coarseEndStone)
		{
			event.getWorld().setBlockState(event.getPos(), Blocks.END_STONE.getDefaultState());
			event.getWorld().playSound(null, event.getPos(), SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
			event.setResult(Result.ALLOW);
		}
	}

	@SubscribeEvent
	public static void onGetItemBurnTime(FurnaceFuelBurnTimeEvent event)
	{
		if (event.getItemStack().getItem() == Item.getItemFromBlock(MinestuckBlocks.treatedPlanks))
			event.setBurnTime(50);    //Do not set this number to 0.
	}

	/*@SubscribeEvent
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
	public static void onPlayerTick(TickEvent.PlayerTickEvent event)
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
	}*/ // TODO: modi

	@SubscribeEvent
	public static void onEntityTick(TickEvent.WorldTickEvent event)
	{
		World world = event.world;
		List<Entity> mobsWithKeysList = StreamSupport.stream(world.loadedEntityList
																	 .spliterator(), false)
												.filter(entity -> entity instanceof EntityMob && (((EntityMob) entity).getHeldItemMainhand().getItem().equals(MinestuckItems.chasityKey) || ((EntityMob) entity).getHeldItemOffhand().getItem().equals(MinestuckItems.chasityKey)))
												.collect(Collectors.toList());
		for (Entity entity : mobsWithKeysList)
		{

			ItemStack stack = ((EntityMob) entity).getHeldItemMainhand();
			if (!stack.getItem().equals(MinestuckItems.chasityKey))
				stack = ((EntityMob) entity).getHeldItemOffhand();

			NBTTagCompound nbt = stack.getTagCompound();
			if (nbt == null)
			{
				nbt = new NBTTagCompound();
				stack.setTagCompound(nbt);
			}

			int glowTimer = nbt.getInteger("GlowTimer");

			if (glowTimer > 1200)
				entity.setGlowing(true);
			else nbt.setInteger("GlowTimer", glowTimer + 1);
		}
	}

	@SubscribeEvent
	public static void onLivingDamage(LivingDamageEvent event)
	{
		ItemStack operandiArmor = ItemStack.EMPTY;

		if (event.getAmount() < 1)
			return;

		for (EntityEquipmentSlot slot : EntityEquipmentSlot.values())
		{
			if (slot.getSlotType().equals(EntityEquipmentSlot.Type.ARMOR) && event.getEntityLiving().getItemStackFromSlot(slot).getItem() instanceof ItemCruxiteArmor)
			{
				operandiArmor = event.getEntityLiving().getItemStackFromSlot(slot);
				break;
			}
		}

		if (!operandiArmor.isEmpty())
		{
			ItemCruxiteArmor item = ((ItemCruxiteArmor) operandiArmor.getItem());
			ICaptchalogueable storedStack = ModusStorage.getStoredItem(operandiArmor);
			operandiArmor.damageItem(operandiArmor.getMaxDamage() + 1, event.getEntityLiving());

			if (event.getAmount() < event.getEntityLiving().getHealth())
			{
				if (item.isEntryArtifact() && (event.getEntityLiving() instanceof EntityPlayer))
					item.getTeleporter().onArtifactActivated((EntityPlayer) event.getEntityLiving());
				else
				{
					event.getEntityLiving().world.playSound(null, event.getEntityLiving().getPosition(), MinestuckSounds.operandiTaskComplete, SoundCategory.PLAYERS, 1, 1);

					if ((event.getEntityLiving() instanceof EntityPlayer))
						storedStack.fetch((EntityPlayer) event.getEntityLiving());
					else storedStack.drop(event.getEntity());
				}
			}
		}
	}
}