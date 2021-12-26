package com.mraof.minestuck.badges;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.alchemy.GristHelper;
import com.mraof.minestuck.alchemy.GristSet;
import com.mraof.minestuck.alchemy.MinestuckGrist;
import com.mraof.minestuck.badges.heroAspect.*;
import com.mraof.minestuck.badges.heroAspectUtil.*;
import com.mraof.minestuck.badges.heroClass.*;
import com.mraof.minestuck.entity.EntityFrog;
import com.mraof.minestuck.item.MinestuckItems;
import com.mraof.minestuck.tracker.MinestuckPlayerTracker;
import com.mraof.minestuck.util.IdentifierHandler;
import com.mraof.minestuck.util.MinestuckPlayerData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Mod.EventBusSubscriber(modid = Minestuck.MODID)
public class MinestuckBadges
{
	public static ForgeRegistry<Badge> REGISTRY;

	public static final MasterBadge MASTER_BADGE_MIGHTY = new MasterBadge("masterBadgeMighty", 3, 80, 0.4f, 40);
	public static final MasterBadge MASTER_BADGE_BRAVE = new MasterBadge("masterBadgeBrave", 3, 80, 0.2f, 40);
	public static final MasterBadge MASTER_BADGE_WISE = new MasterBadge("masterBadgeWise", 3, 80, 0.4f, 60);

	public static final Badge GIFT_OF_GAB = new BadgeConsort("giftOfGab", 2);
	public static final Badge SKELETON_KEY = new BadgeLevel("skeletonKey", 3)
	{
		@Override
		public boolean canUnlock(World world, EntityPlayer player)
		{
			List<Entity> entityList = world.getEntitiesWithinAABB(EntitySkeleton.class, new AxisAlignedBB(player.getPosition()).grow(10));

			boolean canUnlock = !entityList.isEmpty();
			GristSet cost = new GristSet(MinestuckGrist.sulfur, 6000);

			canUnlock = canUnlock && Badge.findItem(player, new ItemStack(Item.REGISTRY.getObject(new ResourceLocation("fetchmodiplus:chasity_key")), 16), false);

			if(canUnlock)
			{
				Badge.findItem(player, new ItemStack(Item.REGISTRY.getObject(new ResourceLocation("fetchmodiplus:chasity_key")), 16), true);

				Entity skeleton = entityList.get(0);
				((WorldServer)player.world).spawnParticle(EnumParticleTypes.TOTEM, skeleton.posX, skeleton.posY+0.25, skeleton.posZ, 30, 1, 0, 0, 0.2);
				skeleton.setDead();

				return true;
			}
			return false;
		}

		@Override
		public String getUnlockRequirements() {
			return new TextComponentTranslation(getUnlocalizedName()+".unlock.fmp").getFormattedText();
		}
	};
	public static final Badge PATCH_OF_THE_HOARDER = new BadgeLevel("patchOfTheHoarder", 3)
	{
		@Override
		public boolean canUnlock(World world, EntityPlayer player)
		{
			GristSet cost = new GristSet(MinestuckGrist.shale, 5000);
			if(Badge.findItem(player, new ItemStack(MinestuckItems.captchaCard, 256), false) && GristHelper.canAfford(MinestuckPlayerData.getGristSet(player), cost))
			{
				Badge.findItem(player, new ItemStack(MinestuckItems.captchaCard, 256), true);

				IdentifierHandler.PlayerIdentifier pid = IdentifierHandler.encode(player);
				GristHelper.decrease(pid, cost);
				MinestuckPlayerTracker.updateGristCache(pid);
				return true;
			}
			return false;
		}

		@Override
		public boolean canDisable() {
			return false;
		}
	};

	public static final Badge HOARD_OF_THE_ALCHEMIZER = new BadgeGristHoard();
	public static final Badge BUILDER_BADGE = new BadgeBuilder();
	public static final Badge STRIFE_BADGE = new BadgeLevel("strifeBadge", 7)
	{
		private final ArrayList<Item> ZILLY_WEAPONS = new ArrayList<Item>()
		{{
			add(MinestuckItems.zillyhooHammer);
			add(MinestuckItems.zillywairCutlass);
			add(MinestuckItems.battleaxeOfZillywahoo);
			add(MinestuckItems.battlepickOfZillydew);
			add(MinestuckItems.battlesporkOfZillywut);
			add(MinestuckItems.gauntletOfZillywenn);
			add(MinestuckItems.katarsOfZillywhomst);
			add(MinestuckItems.scepterOfZillywuud);
			add(MinestuckItems.thistlesOfZillywitch);
		}};
		@Override
		public boolean canUnlock(World world, EntityPlayer player)
		{
			if(!Badge.findItem(player, new ItemStack(MinestuckItems.strifeCard, 2), false))
				return false;

			ArrayList<Item> weaponsUsed = new ArrayList<>();
			for(Item i : ZILLY_WEAPONS)
				if(weaponsUsed.size() <= 5)
				{
					if(Badge.findItem(player, new ItemStack(i), false))
						weaponsUsed.add(i);
				}
			else break;

			if(weaponsUsed.size() < 5)
				return false;

			Badge.findItem(player, new ItemStack(MinestuckItems.strifeCard, 2), true);
			for(Item i : weaponsUsed)
				Badge.findItem(player, new ItemStack(i), true);

			return super.canUnlock(world, player);
		}
	};


	public static final Badge REVENANTS_RETALIATION = new BadgeLevel("revenantsRetaliation", 4)
	{
		@Override
		public boolean canUnlock(World world, EntityPlayer player)
		{
			GristSet cost = new GristSet(MinestuckGrist.diamond, 10000);
			List<Entity> entityList = world.getEntitiesWithinAABB(EntityCreeper.class, new AxisAlignedBB(player.getPosition()).grow(10));

			if(GristHelper.canAfford(MinestuckPlayerData.getGristSet(player), cost) && !entityList.isEmpty())
			{
				Entity creeper = entityList.get(0);
				((WorldServer)player.world).spawnParticle(EnumParticleTypes.TOTEM, creeper.posX, creeper.posY+0.25, creeper.posZ, 30, 1, 0, 0, 0.2);
				creeper.setDead();
				IdentifierHandler.PlayerIdentifier pid = IdentifierHandler.encode(player);
				GristHelper.decrease(pid, cost);
				MinestuckPlayerTracker.updateGristCache(pid);

				return true;
			}
			return false;
		}
	};

	public static final Badge EFFECT_BUFF = new BadgeLevel("effectBuff", 4)
	{
		@Override
		public boolean canUnlock(World world, EntityPlayer player)
		{
			GristSet cost = new GristSet(MinestuckGrist.quartz, 5000);
			List<EntityFrog> entityList = world.getEntitiesWithinAABB(EntityFrog.class, new AxisAlignedBB(player.getPosition()).grow(10), (fog) -> fog.getType() == 6);

			if(GristHelper.canAfford(MinestuckPlayerData.getGristSet(player), cost) && entityList.size() >= 5)
			{
				for(int i = 0; i < 5; i++)
				{
					Entity frog = entityList.get(i);
					((WorldServer)player.world).spawnParticle(EnumParticleTypes.TOTEM, frog.posX, frog.posY+0.25, frog.posZ, 30, 1, 0, 0, 0.2);
					frog.setDead();
				}
				IdentifierHandler.PlayerIdentifier pid = IdentifierHandler.encode(player);
				GristHelper.decrease(pid, cost);
				MinestuckPlayerTracker.updateGristCache(pid);

				return true;
			}
			return false;
		}
	};
	public static final Badge KARMA = new BadgeLevel("karma", 5)
	{
		@Override
		public boolean canUnlock(World world, EntityPlayer player)
		{
			GristSet cost = new GristSet(MinestuckGrist.gold, 8000);
			if(Badge.findItem(player, new ItemStack(MinestuckItems.moonstone, 128), false) && GristHelper.canAfford(MinestuckPlayerData.getGristSet(player), cost))
			{
				Badge.findItem(player, new ItemStack(MinestuckItems.moonstone, 128), true);

				IdentifierHandler.PlayerIdentifier pid = IdentifierHandler.encode(player);
				GristHelper.decrease(pid, cost);
				MinestuckPlayerTracker.updateGristCache(pid);
				return true;
			}
			return false;
		}
	};

	public static final Badge BADGE_KNIGHT = new BadgeKnight();
	public static final Badge BADGE_SYLPH = new BadgeSylph();
	public static final Badge BADGE_PRINCE = new BadgePrince();
	public static final Badge BADGE_WITCH = new BadgeWitch();
	public static final Badge BADGE_THIEF = new BadgeThief();
	public static final Badge BADGE_MAGE = new BadgeMage();

	public static final Badge BADGE_SEER = new BadgeSeer();
	public static final Badge BADGE_ROGUE = new BadgeRogue();
	public static final Badge BADGE_BARD = new BadgeBard();
	public static final Badge BADGE_MAID = new BadgeMaid();
	public static final Badge BADGE_HEIR = new BadgeHeir();
	public static final Badge BADGE_PAGE = new BadgePage();

	public static final Badge BADGE_MUSE = new BadgeMuse();
	public static final Badge BADGE_LORD = new BadgeLord();

	public static final Badge BADGE_ACTIVE_SPACE = new BadgeActiveSpace();
	public static final Badge BADGE_PASSIVE_SPACE = new BadgePassiveSpace();
	public static final Badge BADGE_UTIL_SPACE = new BadgeUtilSpace();

	public static final Badge BADGE_ACTIVE_TIME = new BadgeActiveTime();
	public static final Badge BADGE_PASSIVE_TIME = new BadgePassiveTime();
	public static final Badge BADGE_UTIL_TIME = new BadgeUtilTime();

	public static final Badge BADGE_ACTIVE_BREATH = new BadgeActiveBreath();
	public static final Badge BADGE_PASSIVE_BREATH = new BadgePassiveBreath();
	public static final Badge BADGE_UTIL_BREATH = new BadgeUtilBreath();

	public static final Badge BADGE_ACTIVE_LIGHT = new BadgeActiveLight();
	public static final Badge BADGE_PASSIVE_LIGHT = new BadgePassiveLight();
	public static final Badge BADGE_UTIL_LIGHT = new BadgeUtilLight();

	public static final Badge BADGE_ACTIVE_HEART = new BadgeActiveHeart();
	public static final Badge BADGE_PASSIVE_HEART = new BadgePassiveHeart();
	public static final Badge BADGE_UTIL_HEART = new BadgeUtilHeart();

	public static final Badge BADGE_ACTIVE_DOOM = new BadgeActiveDoom();
	public static final Badge BADGE_PASSIVE_DOOM = new BadgePassiveDoom();
	public static final Badge BADGE_UTIL_DOOM = new BadgeUtilDoom();

	public static final Badge BADGE_ACTIVE_LIFE = new BadgeActiveLife();
	public static final Badge BADGE_PASSIVE_LIFE = new BadgePassiveLife();
	public static final Badge BADGE_UTIL_LIFE = new BadgeUtilLife();

	public static final Badge BADGE_ACTIVE_BLOOD = new BadgeActiveBlood();
	public static final Badge BADGE_PASSIVE_BLOOD = new BadgePassiveBlood();
	public static final Badge BADGE_UTIL_BLOOD = new BadgeUtilBlood();

	public static final Badge BADGE_ACTIVE_VOID = new BadgeActiveVoid();
	public static final Badge BADGE_PASSIVE_VOID = new BadgePassiveVoid();
	public static final Badge BADGE_UTIL_VOID = new BadgeUtilVoid();

	public static final Badge BADGE_ACTIVE_RAGE = new BadgeActiveRage();
	public static final Badge BADGE_PASSIVE_RAGE = new BadgePassiveRage();
	public static final Badge BADGE_UTIL_RAGE = new BadgeUtilRage();

	public static final Badge BADGE_ACTIVE_MIND = new BadgeActiveMind();
	public static final Badge BADGE_PASSIVE_MIND = new BadgePassiveMind();
	public static final Badge BADGE_UTIL_MIND = new BadgeUtilMind();

	public static final Badge BADGE_ACTIVE_HOPE = new BadgeActiveHope();
	public static final Badge BADGE_PASSIVE_HOPE = new BadgePassiveHope();
	public static final Badge BADGE_UTIL_HOPE = new BadgeUtilHope();

	public static final Badge BADGE_OVERLORD = new BadgeOverlord();

	@SubscribeEvent
	public static void missingMappings(RegistryEvent.MissingMappings<Badge> event)
	{
		HashMap<String, Badge> remaps = new HashMap<String, Badge>()
		{{
			put("wise_badge", MASTER_BADGE_WISE);
			put("mighty_badge", MASTER_BADGE_MIGHTY);
			put("brave_badge", MASTER_BADGE_BRAVE);

			put("effect_buff_badge", EFFECT_BUFF);
			put("karma_badge", KARMA);

			put("mechanical_light_badge", BADGE_UTIL_LIGHT);
		}};

		for(RegistryEvent.MissingMappings.Mapping<Badge> badge : event.getMappings())
		{
			String key = badge.key.getResourcePath();
			if(remaps.containsKey(key))
				badge.remap(remaps.get(key));
		}
	}

	@SubscribeEvent
	public static void registerBadges(RegistryEvent.Register<Badge> event)
	{
		IForgeRegistry<Badge> registry = event.getRegistry();

		registry.register(MASTER_BADGE_MIGHTY.setRegistryName("master_badge_mighty"));
		registry.register(MASTER_BADGE_BRAVE.setRegistryName("master_badge_brave"));
		registry.register(MASTER_BADGE_WISE.setRegistryName("master_badge_wise"));

		registry.register(GIFT_OF_GAB.setRegistryName("gift_of_gab"));
		registry.register(SKELETON_KEY.setRegistryName("skeleton_key_badge"));

		if(Minestuck.isTrophySlotsLoaded)
			registry.register(PATCH_OF_THE_HOARDER.setRegistryName("patch_of_the_hoarder"));

		registry.register(HOARD_OF_THE_ALCHEMIZER.setRegistryName("hoard_of_the_alchemizer"));
		registry.register(BUILDER_BADGE.setRegistryName("builder_badge"));
		registry.register(STRIFE_BADGE.setRegistryName("strife_badge"));
		registry.register(REVENANTS_RETALIATION.setRegistryName("revenants_retaliation"));
		registry.register(EFFECT_BUFF.setRegistryName("effect_buff"));
		registry.register(KARMA.setRegistryName("karma"));

		for (BadgeHeroClass badge : BadgeHeroClass.HERO_CLASS_BADGES)
			registry.register(badge.setRegistryName());

		for (BadgeHeroAspect badge : BadgeHeroAspect.HERO_ASPECT_BADGES)
			registry.register(badge.setRegistryName());

		for (BadgeHeroAspectUtil badge : BadgeHeroAspectUtil.HERO_ASPECT_UTIL_BADGES)
			registry.register(badge.setRegistryName());
	}

	@SubscribeEvent
	public static void onNewRegistry(RegistryEvent.NewRegistry event) // TODO: move to Badge
	{
		REGISTRY = (ForgeRegistry<Badge>) new RegistryBuilder<Badge>()
												  .setName(new ResourceLocation(Minestuck.MODID, "god_tier_badges"))
												  .setDefaultKey(new ResourceLocation(Minestuck.MODID))
												  .setType(Badge.class)
												  .create();
	}
}
