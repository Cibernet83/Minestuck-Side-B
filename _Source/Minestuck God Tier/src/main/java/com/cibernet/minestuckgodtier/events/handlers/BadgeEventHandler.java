package com.cibernet.minestuckgodtier.events.handlers;

import com.cibernet.minestuckgodtier.MinestuckGodTier;
import com.cibernet.minestuckgodtier.api.LocksSupport;
import com.cibernet.minestuckgodtier.badges.BadgeBuilder;
import com.cibernet.minestuckgodtier.badges.MSGTBadges;
import com.cibernet.minestuckgodtier.badges.heroAspect.*;
import com.cibernet.minestuckgodtier.badges.heroAspectUtil.*;
import com.cibernet.minestuckgodtier.badges.heroClass.BadgeHeir;
import com.cibernet.minestuckgodtier.badges.heroClass.BadgeMuse;
import com.cibernet.minestuckgodtier.badges.heroClass.BadgeOverlord;
import com.cibernet.minestuckgodtier.capabilities.GodKeyStates;
import com.cibernet.minestuckgodtier.capabilities.MSGTCapabilities;
import com.cibernet.minestuckgodtier.capabilities.api.IGodKeyStates;
import com.cibernet.minestuckgodtier.capabilities.api.IGodTierData;
import com.cibernet.minestuckgodtier.client.particles.MSGTParticles;
import com.cibernet.minestuckgodtier.network.MSGTChannelHandler;
import com.cibernet.minestuckgodtier.network.MSGTPacket;
import com.cibernet.minestuckgodtier.potions.MSGTPotions;
import com.cibernet.Minestuck.blocks.BlockDungeonDoor;
import com.cibernet.Minestuck.events.WeaponAssignedEvent;
import com.mraof.minestuck.potions.MSUPotions;
import com.mraof.minestuck.alchemy.GristAmount;
import com.mraof.minestuck.alchemy.GristHelper;
import com.mraof.minestuck.alchemy.GristSet;
import com.mraof.minestuck.entity.item.EntityGrist;
import com.mraof.minestuck.entity.underling.EntityUnderling;
import com.mraof.minestuck.tracker.MinestuckPlayerTracker;
import com.mraof.minestuck.util.EnumAspect;
import com.mraof.minestuck.util.IdentifierHandler;
import com.mraof.minestuck.util.MinestuckPlayerData;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockEndPortalFrame;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.TreeMap;

public class BadgeEventHandler
{

	public static final TreeMap<EnumAspect, PotionEffect> NEGATIVE_EFFECTS = new TreeMap<EnumAspect, PotionEffect>()
	{{
		put(EnumAspect.BREATH, new PotionEffect(MobEffects.SLOWNESS, 1200, 2));
		put(EnumAspect.SPACE, new PotionEffect(MSUPotions.EARTHBOUND, 400, 0));
		put(EnumAspect.BLOOD, new PotionEffect(MSGTPotions.BLEEDING, 1000, 2));
		put(EnumAspect.RAGE, new PotionEffect(MobEffects.WEAKNESS, 1200, 4));
		put(EnumAspect.HOPE, new PotionEffect(MSGTPotions.GOD_TIER_LOCK, 600, 0));
		put(EnumAspect.DOOM, new PotionEffect(MobEffects.WITHER, 600, 3));
		put(EnumAspect.HEART, new PotionEffect(MobEffects.HUNGER, 1000, 3));
		put(EnumAspect.LIFE, new PotionEffect(MobEffects.POISON, 1200, 4));
		put(EnumAspect.VOID, new PotionEffect(MobEffects.GLOWING, 1200, 2));
		put(EnumAspect.LIGHT, new PotionEffect(MobEffects.UNLUCK, 1200, 7));
		put(EnumAspect.TIME, new PotionEffect(MSGTPotions.TIME_STOP, 100, 0));
		put(EnumAspect.MIND, new PotionEffect(MSGTPotions.MIND_CONFUSION, 300, 0));
	}};

	public static void registerBadgeEvents()
	{
		MinecraftForge.EVENT_BUS.register(BadgeEventHandler.class);
		MinecraftForge.EVENT_BUS.register(BadgeActiveBlood.class);
		MinecraftForge.EVENT_BUS.register(BadgeActiveHeart.class);
		MinecraftForge.EVENT_BUS.register(BadgeActiveMind.class);
		MinecraftForge.EVENT_BUS.register(BadgeActiveTime.class);
		MinecraftForge.EVENT_BUS.register(BadgeActiveVoid.class);
		MinecraftForge.EVENT_BUS.register(BadgeUtilBlood.class);
		MinecraftForge.EVENT_BUS.register(BadgeUtilBreath.class);
		MinecraftForge.EVENT_BUS.register(BadgeUtilRage.class);
		MinecraftForge.EVENT_BUS.register(BadgeUtilVoid.class);
		MinecraftForge.EVENT_BUS.register(BadgeHeir.class);
		MinecraftForge.EVENT_BUS.register(BadgeMuse.class);
		MinecraftForge.EVENT_BUS.register(BadgeOverlord.class);
		MinecraftForge.EVENT_BUS.register(BadgeUtilSpace.class);
		MinecraftForge.EVENT_BUS.register(BadgeBuilder.class);
	}

	@SubscribeEvent
	public static void canStrifeEvent(WeaponAssignedEvent event)
	{
		EntityLivingBase player = event.getPlayer();
		IGodTierData cap = player.getCapability(MSGTCapabilities.GOD_TIER_DATA, null);

		if(cap == null)
			return;

		if(cap.isBadgeActive(MSGTBadges.STRIFE_BADGE))
			event.setCheckResult(true);
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void onLivingDamage(LivingDamageEvent event)
	{
		if (event.getEntity().world.isRemote)
			return;

		EntityPlayer sourcePlayer = event.getSource().getTrueSource() instanceof EntityPlayer ? (EntityPlayer) event.getSource().getTrueSource() : null;
		IGodTierData sourceData   = sourcePlayer != null ? sourcePlayer.getCapability(MSGTCapabilities.GOD_TIER_DATA, null) : null;
		EntityPlayer targetPlayer = event.getEntityLiving() instanceof  EntityPlayer ? (EntityPlayer) event.getEntityLiving() : null;
		IGodTierData targetData   = targetPlayer != null ? targetPlayer.getCapability(MSGTCapabilities.GOD_TIER_DATA, null) : null;

		// Superblock
		if(!event.getSource().isUnblockable() && targetPlayer != null)
		{
			if(targetData.isBadgeActive(MSGTBadges.MASTER_BADGE_BRAVE) && (event.getEntity().world.rand.nextDouble()*100 < MSGTBadges.MASTER_BADGE_BRAVE.getStatNumber(targetPlayer)))
			{
				MSGTChannelHandler.sendToTrackingAndSelf(MSGTPacket.makePacket(MSGTPacket.Type.SEND_PARTICLE, MSGTParticles.ParticleType.AURA, 0x0094FF, 20, targetPlayer), targetPlayer);
				event.setAmount(0);
			}
		}

		// Crit
		if(sourcePlayer != null && sourceData.isBadgeActive(MSGTBadges.MASTER_BADGE_MIGHTY))
		{
			if(event.getEntity().world.rand.nextDouble()*100 < MSGTBadges.MASTER_BADGE_MIGHTY.getStatNumber(sourcePlayer))
			{
				MSGTChannelHandler.sendToTrackingAndSelf(MSGTPacket.makePacket(MSGTPacket.Type.SEND_PARTICLE, MSGTParticles.ParticleType.AURA, 0xF80000, 20, sourcePlayer), sourcePlayer);
				event.setAmount(event.getAmount()*2);
			}
		}
	}

	// Wise Badge
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void onLivingUpdate(LivingEvent.LivingUpdateEvent event)
	{
		EntityLivingBase entity = event.getEntityLiving();
		if(!entity.world.isRemote && entity.hasCapability(MSGTCapabilities.BADGE_EFFECTS, null))
			entity.getCapability(MSGTCapabilities.BADGE_EFFECTS, null).setPrevPos(new Vec3d(entity.posX, entity.posY, entity.posZ));
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void onLivingDrops(LivingDropsEvent event)
	{
		if(event.getSource().getTrueSource() instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) event.getSource().getTrueSource();
			IGodTierData data = player.getCapability(MSGTCapabilities.GOD_TIER_DATA, null);
			EntityLivingBase entity = event.getEntityLiving();

			if(data.isBadgeActive(MSGTBadges.MASTER_BADGE_WISE) && (event.getEntity().world.rand.nextDouble()*100 < MSGTBadges.MASTER_BADGE_WISE.getStatNumber(player)))
			{
				MSGTChannelHandler.sendToTracking(MSGTPacket.makePacket(MSGTPacket.Type.SEND_PARTICLE, MSGTParticles.ParticleType.AURA, 0x00D54E, 20, entity.posX, entity.posY, entity.posZ), entity);

				for(EntityItem item : event.getDrops())
					item.getItem().setCount(Math.min(item.getItem().getCount()*4, item.getItem().getMaxStackSize()));
			}

		}
	}

	@SubscribeEvent
	public static void onGetLooting(LootingLevelEvent event)
	{
		if(event.getDamageSource().getTrueSource() instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) event.getDamageSource().getTrueSource();
			IGodTierData data = player.getCapability(MSGTCapabilities.GOD_TIER_DATA, null);

			if(data.isBadgeActive(MSGTBadges.MASTER_BADGE_WISE))
				event.setLootingLevel((int) Math.floor(event.getLootingLevel() + MSGTBadges.MASTER_BADGE_WISE.getStatNumber(player)/2));
		}
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void onLivingDeath(LivingDeathEvent event)
	{
		if (event.getEntity().world.isRemote)
			return;

		if(event.getSource().getTrueSource() instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) event.getSource().getTrueSource();
			IGodTierData data = player.getCapability(MSGTCapabilities.GOD_TIER_DATA, null);

			if(event.getEntityLiving() instanceof EntityUnderling
					&& data.isBadgeActive(MSGTBadges.MASTER_BADGE_WISE))
			{
				EntityUnderling underling = (EntityUnderling) event.getEntityLiving();

				if (!underling.world.isRemote)
				{
					GristSet grist = underling.getGristSpoils().scaleGrist(1 + MSGTBadges.MASTER_BADGE_WISE.getStatNumber(player));

					if((event.getEntity().world.rand.nextDouble()*100 < MSGTBadges.MASTER_BADGE_WISE.getStatNumber(player)))
					{
						grist = grist.scaleGrist(5);
						MSGTChannelHandler.sendToTracking(MSGTPacket.makePacket(MSGTPacket.Type.SEND_PARTICLE, MSGTParticles.ParticleType.AURA, 0x00D54E, 20, underling.posX, underling.posY, underling.posZ), underling);
					}

					if (grist == null) {
						return;
					}

					if (underling.fromSpawner) {
						grist.scaleGrist(0.5F);
					}

					Iterator var2;
					GristAmount gristType;
					if (!underling.dropCandy) {
						var2 = grist.getArray().iterator();

						while(var2.hasNext()) {
							gristType = (GristAmount)var2.next();
							underling.world.spawnEntity(new EntityGrist(underling.world, underling.posX + underling.getRNG().nextDouble() * (double)underling.width - (double)(underling.width / 2.0F), underling.posY,
									underling.posZ + underling.getRNG().nextDouble() * (double)underling.width - (double)(underling.width / 2.0F), gristType));
						}
					} else {
						var2 = grist.getArray().iterator();

						while(var2.hasNext()) {
							gristType = (GristAmount)var2.next();
							int candy = (gristType.getAmount() + 2) / 4;
							int gristAmount = gristType.getAmount() - candy * 2;
							ItemStack candyItem = gristType.getType().getCandyItem();
							candyItem.setCount(candy);
							if (candy > 0) {
								underling.world.spawnEntity(new EntityItem(underling.world, underling.posX + underling.getRNG().nextDouble() * (double)underling.width - (double)(underling.width / 2.0F), underling.posY,
										underling.posZ + underling.getRNG().nextDouble() * (double)underling.width - (double)(underling.width / 2.0F), candyItem));
							}

							if (gristAmount > 0) {
								underling.world.spawnEntity(new EntityGrist(underling.world, underling.posX + underling.getRNG().nextDouble() * (double)underling.width - (double)(underling.width / 2.0F), underling.posY,
										underling.posZ + underling.getRNG().nextDouble() * (double)underling.width - (double)(underling.width / 2.0F), new GristAmount(gristType.getType(), gristAmount)));
							}
						}
					}
				}
			}
		}
	}

	private static int blockCount = 0;

	@SubscribeEvent
	public static void onBlockInteract(PlayerInteractEvent.RightClickBlock event)
	{
		if (event.getEntity().world.isRemote)
			return;

		// Skeleton Key Badge
		if(event.getHand() == EnumHand.OFF_HAND || !event.getItemStack().isEmpty())
			return;

		if(event.getEntityPlayer().getCapability(MSGTCapabilities.GOD_TIER_DATA, null).isBadgeActive(MSGTBadges.SKELETON_KEY))
		{
			IBlockState state = event.getWorld().getBlockState(event.getPos());
			Block block = state.getBlock();
			World world = event.getWorld();
			BlockPos pos = event.getPos();

			if(block instanceof BlockDoor && state.getMaterial() == Material.IRON)
			{
				BlockPos blockpos = state.getValue(BlockDoor.HALF) == BlockDoor.EnumDoorHalf.LOWER ? pos : pos.down();
				IBlockState iblockstate = pos.equals(blockpos) ? state : world.getBlockState(blockpos);

				if (iblockstate.getBlock() == block)
				{
					state = iblockstate.cycleProperty(BlockDoor.OPEN);
					world.setBlockState(blockpos, state, 10);
					world.markBlockRangeForRenderUpdate(blockpos, pos);
					world.playEvent(state.getValue(BlockDoor.OPEN) ? 1005 : 1011, pos, 0);
				}
			}
			else if(block instanceof BlockTrapDoor && state.getMaterial() == Material.IRON)
			{
				state = state.cycleProperty(BlockTrapDoor.OPEN);
				world.setBlockState(pos, state, 2);
				world.playEvent(state.getValue(BlockTrapDoor.OPEN) ? 1037 : 1036, pos, 0);
			}
			else if(block instanceof BlockDungeonDoor)
				activateDoor(event.getWorld(), event.getPos());
			else if(block instanceof BlockEndPortalFrame)
				activateEndPortal(event.getWorld(), event.getPos(), state, event.getWorld().rand);
			else if(block.equals(Blocks.OBSIDIAN) && event.getFace().equals(EnumFacing.UP) && event.getWorld().provider.getDimensionType().getId() > 0 && Blocks.PORTAL.trySpawnPortal(event.getWorld(), event.getPos().up()))
				world.playSound(event.getEntityPlayer(), pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, world.rand.nextFloat() * 0.4F + 0.8F);
			else if(MinestuckGodTier.isLocksLoaded)
			{
				LocksSupport.useKey(world, pos, event.getEntityPlayer());
			}
		}

	}

	private static void activateEndPortal(World worldIn, BlockPos pos, IBlockState iblockstate, Random itemRand)
	{
		if(iblockstate.getValue(BlockEndPortalFrame.EYE))
			return;

		worldIn.setBlockState(pos, iblockstate.withProperty(BlockEndPortalFrame.EYE, Boolean.valueOf(true)), 2);
		worldIn.updateComparatorOutputLevel(pos, Blocks.END_PORTAL_FRAME);

		for (int i = 0; i < 16; ++i)
		{
			double d0 = (double)((float)pos.getX() + (5.0F + itemRand.nextFloat() * 6.0F) / 16.0F);
			double d1 = (double)((float)pos.getY() + 0.8125F);
			double d2 = (double)((float)pos.getZ() + (5.0F + itemRand.nextFloat() * 6.0F) / 16.0F);
			double d3 = 0.0D;
			double d4 = 0.0D;
			double d5 = 0.0D;
			worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2, 0.0D, 0.0D, 0.0D);
		}

		worldIn.playSound(null, pos, SoundEvents.BLOCK_END_PORTAL_FRAME_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
		BlockPattern.PatternHelper blockpattern$patternhelper = BlockEndPortalFrame.getOrCreatePortalShape().match(worldIn, pos);

		if (blockpattern$patternhelper != null)
		{
			BlockPos blockpos = blockpattern$patternhelper.getFrontTopLeft().add(-3, 0, -3);

			for (int j = 0; j < 3; ++j)
			{
				for (int k = 0; k < 3; ++k)
				{
					worldIn.setBlockState(blockpos.add(j, 0, k), Blocks.END_PORTAL.getDefaultState(), 2);
				}
			}

			worldIn.playBroadcastSound(1038, blockpos.add(1, 0, 1), 0);
		}
	}

	private static void activateDoor(World world, BlockPos pos)
	{
		world.destroyBlock(pos, false);
		if (blockCount < 100) {
			EnumFacing[] var2 = EnumFacing.values();
			int var3 = var2.length;

			for(int var4 = 0; var4 < var3; ++var4) {
				EnumFacing direction = var2[var4];
				IBlockState state = world.getBlockState(pos.offset(direction));
				if (state.getBlock() instanceof BlockDungeonDoor) {
					++blockCount;
					activateDoor(world, pos.offset(direction));
				}
			}

		}
	}

	private static final int HOARD_THRESHOLD = 10000;

	@SubscribeEvent
	public static void onPlayerTick(TickEvent.PlayerTickEvent event)
	{
		if (event.player.world.isRemote)
			return;

		// Hoard of the Alchemizer Badge
		IGodTierData gtData = event.player.getCapability(MSGTCapabilities.GOD_TIER_DATA, null);
		if(gtData.isBadgeActive(MSGTBadges.HOARD_OF_THE_ALCHEMIZER) && gtData.getGristHoard() != null && !GristHelper.canAfford(MinestuckPlayerData.getGristSet(event.player), new GristSet(gtData.getGristHoard(), HOARD_THRESHOLD)))
		{
			IdentifierHandler.PlayerIdentifier pid = IdentifierHandler.encode(event.player);
			GristHelper.setGrist(pid, gtData.getGristHoard(), HOARD_THRESHOLD);
			MinestuckPlayerTracker.updateGristCache(pid);
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void onPlayerRightClick(PlayerInteractEvent.RightClickItem event)
	{
		IGodKeyStates cap = event.getEntityPlayer().getCapability(MSGTCapabilities.GOD_KEY_STATES, null);

		if(cap.getKeyState(GodKeyStates.Key.ASPECT) != GodKeyStates.KeyState.NONE || cap.getKeyState(GodKeyStates.Key.CLASS) != GodKeyStates.KeyState.NONE
				|| cap.getKeyState(GodKeyStates.Key.UTIL) != GodKeyStates.KeyState.NONE)
			event.setCanceled(true);

	}
}
