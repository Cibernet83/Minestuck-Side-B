package com.mraof.minestuck.item;

import com.mraof.minestuck.util.BlockMetaPair;
import com.mraof.minestuck.util.MSUModelManager;
import com.cibernet.splatcraft.items.ItemFilter;
import com.google.common.collect.ImmutableList;
import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.alchemy.GristType;
import com.mraof.minestuck.client.model.armor.*;
import com.mraof.minestuck.client.renderer.entity.RenderThrowable;
import com.mraof.minestuck.enchantments.MSUEnchantments;
import com.mraof.minestuck.entity.EntityEightBall;
import com.mraof.minestuck.item.armor.*;
import com.mraof.minestuck.item.operandi.*;
import com.mraof.minestuck.item.properties.*;
import com.mraof.minestuck.item.properties.beams.PropertyBeamDeathMessage;
import com.mraof.minestuck.item.properties.beams.PropertyMagicBeam;
import com.mraof.minestuck.item.properties.beams.PropertyPotionBeam;
import com.mraof.minestuck.item.properties.beams.PropertyRainbowBeam;
import com.mraof.minestuck.item.properties.bowkind.*;
import com.mraof.minestuck.item.properties.clawkind.PropertyActionBuff;
import com.mraof.minestuck.item.properties.shieldkind.*;
import com.mraof.minestuck.item.properties.throwkind.*;
import com.mraof.minestuck.util.IRegistryItem;
import com.mraof.minestuck.util.MinestuckSounds;
import com.mraof.minestuck.block.*;
import com.mraof.minestuck.entity.item.EntityCrewPoster;
import com.mraof.minestuck.entity.item.EntitySbahjPoster;
import com.mraof.minestuck.inventory.captchalouge.PopTartModus;
import com.mraof.minestuck.item.block.*;
import com.mraof.minestuck.item.weapon.*;
import com.mraof.minestuck.util.MinestuckSoundHandler;

import com.mraof.minestuck.util.Pair;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IPosition;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.*;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;

import static com.mraof.minestuck.block.MinestuckBlocks.*;
import static com.mraof.minestuck.util.ModusStorage.getStoredItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import com.mraof.minestuck.block.BlockAspectLog;
import com.mraof.minestuck.block.BlockAspectLog2;
import com.mraof.minestuck.block.BlockAspectLog3;
import com.mraof.minestuck.block.BlockAspectSapling;
import vazkii.botania.common.block.ModBlocks;

public class MinestuckItems
{
	private static final PropertySoundOnHit.Value PITCH_NOTE = ((stack, target, player) -> (-player.rotationPitch + 90) / 90f);
	public static final ArrayList<Block> itemBlocks = new ArrayList<>();

	public static final Item.ToolMaterial toolUranium = EnumHelper.addToolMaterial("URANIUM", 3, 1220, 12.0F, 6.0F, 15);
	public static final Item.ToolMaterial toolEmerald = EnumHelper.addToolMaterial("EMERALD", 3, 1220, 12.0F, 4.0F, 12).setRepairItem(new ItemStack(Items.EMERALD));
	public static final ItemArmor.ArmorMaterial armorPrismarine = EnumHelper.addArmorMaterial("PRISMARINE", "minestuck:prismarine", 20, new int[]{3, 7, 6, 2}, 15, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F);
	public static final ItemArmor.ArmorMaterial materialDiverHelmet = EnumHelper.addArmorMaterial("DIVER_HELMET", "minestuck:diver_helmet", 120, new int[] {0, 0, 0, 3}, 5, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F);
	public static final ItemArmor.ArmorMaterial materialSpikedHelmet = EnumHelper.addArmorMaterial("SPIKED_HELMET", "minestuck:spiked_diver_helmet", 230, new int[] {0, 0, 0, 6}, 5, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F);
	public static final ItemArmor.ArmorMaterial materialMetal = EnumHelper.addArmorMaterial("METAL", "minestuck:metal", 200, new int[] {2, 0, 0, 4}, 5, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F);
	public static final ItemArmor.ArmorMaterial materialRubber = EnumHelper.addArmorMaterial("RUBBER", "minestuck:rubber", 240, new int[] {1, 2, 3, 1}, 5, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F);
	public static final ItemArmor.ArmorMaterial materialSunShoes = EnumHelper.addArmorMaterial("SOLAR", "minestuck:solar", 240, new int[] {3, 0, 0, 3}, 15, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 0.0F);
	public static final ItemArmor.ArmorMaterial materialWindWalkers = EnumHelper.addArmorMaterial("WIND_WALKERS", "minestuck:sun_shoes", 240, new int[] {3, 0, 0, 3}, 20, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F);
	public static final ItemArmor.ArmorMaterial materialCobalt = EnumHelper.addArmorMaterial("COBALT", "minestuck:cobalt", 640, new int[] {1, 0, 0, 2}, 20, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.1F);
	public static final ItemArmor.ArmorMaterial materialCloth = EnumHelper.addArmorMaterial("CLOTH", "minestuck:cloth", -1, new int[] {0, 0, 0, 0}, 5, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F);

	//Tool Classes
	public static final MSUToolClass toolSword = new MSUToolClass("sword", Material.WEB).addEnchantments(EnumEnchantmentType.WEAPON);
	public static final MSUToolClass toolGauntlet = new MSUToolClass("gauntlet", Material.GLASS, Material.ICE, Material.PACKED_ICE).addEnchantments(Enchantments.SILK_TOUCH, Enchantments.FIRE_ASPECT, Enchantments.LOOTING, MSUEnchantments.SUPERPUNCH);
	public static final MSUToolClass toolNeedles = new MSUToolClass("needle", Material.CLOTH).addEnchantments(EnumEnchantmentType.WEAPON);
	public static final MSUToolClass toolHammer = new MSUToolClass("pickaxe", "pickaxe").addEnchantments(EnumEnchantmentType.WEAPON, EnumEnchantmentType.DIGGER);
	public static final MSUToolClass toolClub = new MSUToolClass("club").addEnchantments(EnumEnchantmentType.WEAPON);
	public static final MSUToolClass toolClaws = new MSUToolClass("claws", Material.PLANTS, Material.WEB).addEnchantments(EnumEnchantmentType.WEAPON);
	public static final MSUToolClass toolCane = new MSUToolClass("cane").addEnchantments(EnumEnchantmentType.WEAPON);
	public static final MSUToolClass toolSickle = new MSUToolClass("sickle", Material.GRASS, Material.PLANTS, Material.LEAVES).addEnchantments(EnumEnchantmentType.WEAPON);
	public static final MSUToolClass toolSpoon = new MSUToolClass("spoon", Material.GOURD).addEnchantments(EnumEnchantmentType.WEAPON);
	public static final MSUToolClass toolFork = new MSUToolClass("fork", Material.GRASS).addEnchantments(EnumEnchantmentType.WEAPON);

	public static final MSUToolClass toolShovel = new MSUToolClass("shovel", "shovel").addEnchantments(EnumEnchantmentType.DIGGER);
	public static final MSUToolClass toolAxe = new MSUToolClass("axe", "axe").addEnchantments(EnumEnchantmentType.WEAPON, EnumEnchantmentType.DIGGER).setDisablesShield();
	public static final MSUToolClass toolPickaxe = new MSUToolClass("pickaxe", "pickaxe").addEnchantments(EnumEnchantmentType.DIGGER);

	public static final MSUToolClass toolSpork = new MSUToolClass("spork", toolSpoon, toolFork);
	public static final MSUToolClass toolHammaxe = new MSUToolClass("hammaxe", toolHammer, toolAxe);

	//Block Swap Property Maps
	public static final BlockMetaPair.Map overgrowthTransforms = new BlockMetaPair.Map();

	//Material tools
	public static final Item emeraldSword = new ItemWeapon(1220, 8, -2.4, 12, "emeraldSword");
	public static final Item emeraldAxe = new ItemWeapon(1220, 8, -2.4, 12, "emeraldAxe");  //TODO turn into actual tools
	public static final Item emeraldPickaxe = new ItemWeapon(1220, 8, -2.4, 12, "emeraldPickaxe");
	public static final Item emeraldShovel = new ItemWeapon(1220, 8, -2.4, 12, "emeraldShovel");
	public static final Item emeraldHoe = new ItemWeapon(1220, 8, -2.4, 12, "emeraldHoe");
	//Armor
	public static final Item prismarineHelmet = new MSArmorBase(armorPrismarine, 0, EntityEquipmentSlot.HEAD, "prismarineHelmet");
	public static final Item prismarineChestplate = new MSArmorBase(armorPrismarine, 0, EntityEquipmentSlot.CHEST, "prismarineChestplate");
	public static final Item prismarineLeggings = new MSArmorBase(armorPrismarine, 0, EntityEquipmentSlot.LEGS, "prismarineLeggings");
	public static final Item prismarineBoots = new MSArmorBase(armorPrismarine, 0, EntityEquipmentSlot.FEET, "prismarineBoots");
	//Food
	public static final Item candy = new ItemMinestuckCandy();
	public static final Item beverage = new ItemMinestuckBeverage();
	public static final Item bugOnAStick = new ItemFood("bugOnStick", 1, 0.1F, false);
	public static final Item chocolateBeetle = new ItemFood("chocolateBeetle", 3, 0.4F, false);
	public static final Item coneOfFlies = new ItemFood("coneOfFlies", 2, 0.1F, false);
	public static final Item grasshopper = new ItemFood("grasshopper", 4, 0.5F, false);
	public static final Item jarOfBugs = new ItemFood("jarOfBugs", 3, 0.2F, false);
	public static final Item onion = new ItemFood("onion", 2, 0.2F, false);
	public static final Item salad = new ItemFood("salad", 1, 0.6f, false); //TODO don't eat the bowl >:(
	public static final Item desertFruit = new ItemFood("desertFruit", 1, 0.1F, false);
	public static final Item irradiatedSteak = new ItemFood("irradiatedSteak", 4, 0.4F, true).setPotionEffect(new PotionEffect(MobEffects.WITHER, 100, 1), 0.9F);
	public static final Item rockCookie = new MSItemBase("rockCookie");
	public static final Item fungalSpore = new ItemFood("fungalSpore", 1, 0.2F, false).setPotionEffect(new PotionEffect(MobEffects.POISON, 60, 3), 0.7F);
	public static final Item sporeo = new ItemFood("sporeo", 3, 0.4F, false);
	public static final Item morelMushroom = new ItemFood("morelMushroom", 3, 0.9F, false);
	public static final Item frenchFry = new ItemFood("frenchFry", 1, 0.1F, false);
	public static final Item strawberryChunk = new ItemMinestuckSeedFood(4, 0.5F, "strawberryChunk");
	public static final Item surpriseEmbryo = new ItemSurpriseEmbryo(3, 0.2F, false);
	public static final Item unknowableEgg = new ItemUnknowableEgg(3, 0.3F, false, "unknowableEgg");
	//Other
	public static final Item goldenGrasshopper = new MSItemBase("goldenGrasshopper");
	public static final Item bugNet = new ItemNet().setUnlocalizedName("net");
	public static final Item itemFrog = new ItemFrog().setUnlocalizedName("frog");
	public static final Item boondollars = new ItemBoondollars();
	public static final Item rawCruxite = new MSItemBase("rawCruxite");
	public static final Item rawUranium = new MSItemBase("rawUranium");
	public static final Item energyCore = new MSItemBase("energyCore");
	public static final ItemDowel cruxiteDowel = new ItemDowel(MinestuckBlocks.blockCruxiteDowel);
	public static final Item captchaCard = new ItemCaptchaCard();
	public static final Item cruxiteApple = new ItemCruxiteApple();
	public static final Item cruxitePotion = new ItemCruxitePotion();
	public static final Item disk = new ItemDisk();
	public static final Item grimoire = new ItemGrimoire();
	public static final Item longForgottenWarhorn = new ItemLongForgottenWarhorn();
	public static final ItemMinestuckBucket minestuckBucket = new ItemMinestuckBucket();
	public static final Item obsidianBucket = new ItemObsidianBucket();
	public static final Item goldSeeds = new ItemGoldSeeds();
	public static final Item razorBlade = new ItemRazorBlade();
	public static final Item metalBoat = new ItemMetalBoat();
	public static final Item shunt = new ItemShunt();
	public static final Item captcharoidCamera = new ItemCaptcharoidCamera();
	public static final Item threshDvd = new MSItemBase("threshDvd").setMaxStackSize(1);
	public static final Item gamebroMagazine = new MSItemBase("gamebroMagazine").setMaxStackSize(1);
	public static final Item gamegrlMagazine = new MSItemBase("gamegrlMagazine").setMaxStackSize(1);
	public static final Item crewPoster = new ItemHanging("crewPoster")
	{
		@Override
		public EntityHanging createEntity(World worldIn, BlockPos pos, EnumFacing facing, ItemStack stack, int meta)
		{
			return new EntityCrewPoster(worldIn, pos, facing);
		}
	}.setMaxStackSize(1);
	public static final Item sbahjPoster = new ItemHanging("sbahjPoster")
	{
		@Override
		public EntityHanging createEntity(World worldIn, BlockPos pos, EnumFacing facing, ItemStack stack, int meta)
		{
			return new EntitySbahjPoster(worldIn, pos, facing);
		}
	}.setMaxStackSize(1);

	/*
	public static final Item shopPoster = new ItemShopPoster()
	{
		@Override
		public EntityHanging createEntity(World worldIn, BlockPos pos, EnumFacing facing, ItemStack stack, int meta)
		{
			return new EntityShopPoster(worldIn, pos, facing, stack, meta);
		}
	}.setMaxStackSize(1);
	*/

	public static final Item carvingTool = new MSItemBase("carvingTool").setMaxStackSize(1);
	public static final MSUArmorBase crumplyHat = new MSUArmorBase(materialCloth, 0, EntityEquipmentSlot.HEAD, "crumplyHat", "minestuck:crumply_hat");
    public static final Item stoneEyeballs = new MSItemBase("stoneEyeballs");
	public static final Item stoneSlab = new MSItemBase("stoneSlab");
	public static final Item glowystoneDust = new ItemGlowystoneDust().setUnlocalizedName("glowystoneDust");
	public static final Item fakeArms = new MSItemBase("fakeArms").setCreativeTab(null);
	//Music disks
	public static final Item recordEmissaryOfDance = new ItemMinestuckRecord("emissary", MinestuckSoundHandler.soundEmissaryOfDance).setUnlocalizedName("record");
	public static final Item recordDanceStab = new ItemMinestuckRecord("danceStab", MinestuckSoundHandler.soundDanceStabDance).setUnlocalizedName("record");
	public static final Item recordRetroBattle = new ItemMinestuckRecord("retroBattle",MinestuckSoundHandler.soundRetroBattleTheme).setUnlocalizedName("record");

	public static final Item stackModus = new MSItemBase("stackModus").setMaxStackSize(1);
	public static final Item queueModus = new MSItemBase("queueModus").setMaxStackSize(1);
	public static final Item queueStackModus = new MSItemBase("queueStackModus").setMaxStackSize(1);
	public static final Item treeModus = new MSItemBase("treeModus").setMaxStackSize(1);
	public static final Item hashmapModus = new MSItemBase("hashmapModus").setMaxStackSize(1);
	public static final Item setModus = new MSItemBase("setModus").setMaxStackSize(1);
	public static final Item wildMagicModus = new MSItemBase("wildMagicModus").setMaxStackSize(1);
	public static final Item weightModus = new MSItemBase("weightModus").setMaxStackSize(1);
	public static final Item bookModus = new MSItemBase("bookModus").setMaxStackSize(1);
	public static final Item capitalistModus = new MSItemBase("capitalistModus").setMaxStackSize(1);
	public static final Item modUs = new MSItemBase("modUs").setMaxStackSize(1);
	public static final Item operandiModus = new MSItemBase("operandiModus").setMaxStackSize(1);
	public static final Item onionModus = new MSItemBase("onionModus").setMaxStackSize(1);
	public static final Item slimeModus = new MSItemBase("slimeModus").setMaxStackSize(1);
	public static final Item popTartModus = new MSItemBase("popTartModus").setMaxStackSize(1);
	public static final Item deckModus = new MSItemBase("deckModus").setMaxStackSize(1);
	public static final Item hueModus = new MSItemBase("hueModus").setMaxStackSize(1);
	public static final Item hueStackModus = new MSItemBase("hueStackModus").setMaxStackSize(1);
	public static final Item chatModus = new MSItemBase("chatModus").setMaxStackSize(1);
	public static final Item cycloneModus = new MSItemBase("cycloneModus").setMaxStackSize(1);
	public static final Item energyModus = new MSItemBase("energyModus").setMaxStackSize(1);
	public static final Item scratchAndSniffModus = new MSItemBase("scratchAndSniffModus").setMaxStackSize(1);
	public static final Item eightBallModus = new MSItemBase("eightBallModus").setMaxStackSize(1);
	public static final Item chasityModus = new MSItemBase("chasityModus").setMaxStackSize(1);
	public static final Item jujuModus = new MSItemBase("jujuModus").setMaxStackSize(1);
	public static final Item alcheModus = new MSItemBase("alchemodus").setMaxStackSize(1);
	public static final Item arrayModus = new MSItemBase("arrayModus").setMaxStackSize(1);
	public static final Item monsterModus = new MSItemBase("monsterModus").setMaxStackSize(1);
	public static final Item walletModus = new MSItemBase("walletModus").setMaxStackSize(1);
	public static final Item crystalBallModus = new MSItemBase("crystalBallModus").setMaxStackSize(1);
	public static final Item hashchatModus = new MSItemBase("hashchatModus").setMaxStackSize(1);
	public static final Item sacrificeModus = new MSItemBase("sacrificeModus").setMaxStackSize(1);
	/*
	public static final Item memoryModus = new MSItemBase("memoryModus");
	public static final Item recipeModus = new MSItemBase("recipeModus");
	public static final Item bottledMsgModus = new MSItemBase("messageInABottleModus");
	public static final Item techHopModus = new MSItemBase("techHopModus");
	public static final Item encryptionModus = new MSItemBase("encryptionModus");
	public static final Item ouijaModus = new MSItemBase("ouijaModus");
	public static final Item bundleModus = new MSItemBase("bundleModus");
	public static final Item cakeModus = new MSItemBase("cakeModus");
	public static final Item cipherModus = new MSItemBase("cipherModus");
	*/

	public static final Item popTart = new ItemFood("popTart", 3, 0, false, PopTartModus.getConsumer());
	public static final Item eightBall = new ItemEightBall("eightBall", false);
	public static final Item popBall = new ItemFood("magicPopBalls", 6, 0.4f, false, ItemFood.getPopBallConsumer());
	public static final Item floatStone = new MSItemBase("floatStone").setMaxStackSize(1);
	public static final Item energyCell = new MSItemBase("energyCell");
	public static final Item captchalogueBook = new ItemCruxiteCaptchaBook("captchalogueBook");
	public static final Item chasityKey = new ItemCruxiteChasityKey("chasityKey");
	//public static final Item cruxiteBottle = new BaseItem("cruxiteBottle");
	public static final Item crystalEightBall = new ItemEightBall("crystalEightBall", true);
	public static final Item cruxiteGel = new ItemCruxite("cruxiteGel");
	public static final Item dragonGel = new MSItemBase("dragonGel");
	public static final Item cruxtruderGel = new ItemCruxtruderGel("cruxtruderGel");

	public static final Item operandiPickaxe = new ItemOperandiTool("operandiPickaxe", "pickaxe", 1.0F, -2.8F, 7.0f, 3);
	public static final Item operandiAxe = new ItemOperandiTool("operandiAxe", "axe", 5.0f, -3.2F, 7.0f, 3);
	public static final Item operandiShovel = new ItemOperandiTool("operandiShovel", "shovel", 1.5F, -3.0F, 7.0F, 3);
	public static final Item operandiHoe = new ItemOperandiHoe("operandiHoe");
	public static final Item operandiSword = new ItemOperandiWeapon("operandiSword", "", 4.0f, -2.4000000953674316f, 0f, 3);
	public static final Item operandiHammer = new ItemOperandiWeapon("operandiHammer", "pickaxe", 5.0f, -2.45f, 7.0f, 4);
	public static final Item operandiClub = new ItemOperandiWeapon("operandiClub", "club", 4.0F, -2.2F, 7.0f, 5);
	public static final Item operandiBattleaxe = new ItemOperandiWeapon("operandiBattleaxe", "axe", 7.0F, -3.2F, 7.0f, 2);
	public static final Item operandiApple = new ItemOperandiFood("operandiApple", 4, 0.15F);
	public static final Item operandiPotion = new ItemOperandiFood("operandiPotion", 1, 0.4f, EnumAction.DRINK);
	public static final Item operandiPopTart = new ItemOperandiFood("operandiPopTart", 3, 0);
	public static final Item operandiEightBall = new ItemOperandiThrowable("operandiEightBall", 0, 1.5f, MinestuckSounds.eightBallThrow);
	public static final Item operandiSplashPotion = new ItemOperandiThrowable("operandiSplashPotion", -20, 0.5f, SoundEvents.ENTITY_SPLASH_POTION_THROW);
	public static final Item operandiHelmet = new ItemOperandiArmor("operandiHelmet", EntityEquipmentSlot.HEAD);
	public static final Item operandiChestplate = new ItemOperandiArmor("operandiChestplate", EntityEquipmentSlot.CHEST);
	public static final Item operandiLeggings = new ItemOperandiArmor("operandiLeggings", EntityEquipmentSlot.LEGS);
	public static final Item operandiBoots = new ItemOperandiArmor("operandiBoots", EntityEquipmentSlot.FEET);
	public static final Item operandiBlock = new ItemOperandiBlock("operandiBlock", MinestuckBlocks.operandiBlock);
	public static final Item operandiStone = new ItemOperandiBlock("operandiStone", MinestuckBlocks.operandiStone);
	public static final Item operandiLog = new ItemOperandiBlock("operandiLog", MinestuckBlocks.operandiLog);
	public static final Item operandiGlass = new ItemOperandiBlock("operandiGlass", MinestuckBlocks.operandiGlass);

	public static final Item hardStone = new MSBlockItem(MinestuckBlocks.hardStone);
	public static final Item walletEntityItem = new ItemWalletEntity("walletEntity");

	public static final Item spaceSalt = new ItemSpaceSalt();
	public static final Item timetable = new ItemTimetable();
	public static final Item moonstone = new MSUItemBase("moonstone");
	public static final Item moonstoneChisel = new ItemChisel("moonstone", 31);
	public static final Item zillystoneShard = new MSUItemBase("zillystone_shard", "zillystoneShard");
	public static final Item fluorite = new MSUItemBase("fluorite");
	public static final Item battery = new MSUItemBase("battery", "battery");
	public static final Item strifeCard = new ItemStrifeCard("strife_card", "strifeCard");
	public static final Item dungeonKey = new MSUItemBase("dungeon_key", "dungeonKey");
	public static final Item laserPointer = new ItemBeamWeapon(-1, 0, 0, 0.01f, 0, 1, 1, "laser_pointer", "laserPointer").addProperties(new PropertyPotionBeam(new PotionEffect(MobEffects.BLINDNESS, 30, 0, false, false))).setRepairMaterials(new ItemStack(battery)).setCreativeTab(TabsMinestuck.minestuck);
	public static final Item whip = new ItemSound("whip", "whip", MSUSoundHandler.whipCrack);
	public static final Item sbahjWhip = new ItemSound("whip_sbahj", "whipSbahj", MSUSoundHandler.whipCrock).setSecret();
	public static final Item unrealAir = new ItemUnrealAir("unreal_air", "unrealAir");

	public static final Item tickingStopwatch = new MSUItemBase("ticking_stopwatch", "tickingStopwatch"){{
		addPropertyOverride(new ResourceLocation(Minestuck.MODID, "time"), ((stack, worldIn, entityIn) -> ((System.currentTimeMillis() - Minestuck.startTime)/1000f) % 60));
	}}.setMaxStackSize(1);
	public static final Item cueBall = new MSUItemBase("cue_ball", "cueBall")
	{
		@Override
		public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {}

		@Override
		public boolean onEntityItemUpdate(EntityItem entityItem)
		{
			entityItem.setEntityInvulnerable(true);
			entityItem.setGlowing(true);
			entityItem.setNoDespawn();

			if(entityItem.posY < 1)
			{
				entityItem.setPosition(entityItem.getPosition().getX(), 260, entityItem.getPosition().getZ());
				entityItem.motionY = entityItem.world.rand.nextDouble()*1.2;
			}

			return super.onEntityItemUpdate(entityItem);
		}

	}.setSecret().setMaxStackSize(1);

	//Ghost Items
	public static final Item returnNode = new ItemGhost("return_node_ghost_item", MinestuckBlocks.returnNode);
	public static final Item travelGate = new ItemGhost("travel_gate_ghost_item", MinestuckBlocks.gate);
	public static final Item netherPortal = new ItemGhost("nether_portal_ghost_item", Blocks.PORTAL);
	public static final Item endPortal = new ItemGhost("end_portal_ghost_item", Blocks.END_PORTAL);
	public static final Item endGateway = new ItemGhost("end_gateway_ghost_item", Blocks.END_GATEWAY);
	public static final Item sun = new ItemGhost("sun_ghost_item");
	public static final Item greenSun = new ItemGhost("green_sun_ghost_item");
	public static final Item moon = new ItemGhost("moon_ghost_item");
	public static final Item skaia = new ItemGhost("skaia_ghost_item");
	public static final Item lightning = new ItemGhost("lightning_ghost_item");

	//Medallions
	public static final Item ironMedallion = new MSUItemBase("iron_medallion", "ironMedallion").setMaxStackSize(1);
	public static final Item returnMedallion = new ItemWarpMedallion("returnMedallion", "return_medallion", ItemWarpMedallion.EnumTeleportType.RETURN, 80);
	public static final Item teleportMedallion = new ItemWarpMedallion("teleportMedallion", "teleport_medallion", ItemWarpMedallion.EnumTeleportType.TRANSPORTALIZER, 80);
	public static final Item skaianMedallion = new ItemWarpMedallion("skaianMedallion", "skaian_medallion", ItemWarpMedallion.EnumTeleportType.SKAIA, 80);

	//Weapons

	//Bladekind
	public static final Item sord = new MSUWeaponBase(250, 3, -2.3, 1, "sord").setTool(toolSword, 0, 0).addProperties(new PropertySlippery());
	public static final Item cactusCutlass = new MSUWeaponBase(746, 12.7, -2.3, 5, "cactusCutlass").setTool(toolSword, 1, 4);
	public static final Item beefSword = new MSUWeaponBase(550, 7.2, -2.3, 4, "beefSword").setTool(toolSword, 1, 2).addProperties(new PropertyEdible(3, 0.3F, 75));
	public static final Item steakSword = new MSUWeaponBase(550, 8, -2.3, 4, "steakSword").setTool(toolSword, 1, 2).addProperties(new PropertyEdible(8, 0.8F, 50));
	public static final Item irradiatedSteakSword = new MSUWeaponBase(550, 8, -2.3, 3, "irradiatedSteakSword").setTool(toolSword, 1, 3).addProperties(new PropertyEdible(4, 0.4F, 25).setPotionEffect(0.9f, new PotionEffect(MobEffects.WITHER, 100, 1)));
	public static final Item firePoker = new MSUWeaponBase(825, 10.5, -2.07, 10, "firePoker").setTool(toolSword, 2, 4).addProperties(new PropertyFire(30, 0.3f, false), new PropertyTipperDamage(0.8f, 1.2f, 1));
	public static final Item hotHandle = new MSUWeaponBase(825, 12.0, -2.3, 10, "hotHandle").setTool(toolSword, 3, 3).addProperties(new PropertyFire(15, 1f, true));
	public static final Item royalDeringer = new MSUWeaponBase(908, 13.2, -2.3, 14, "royalDeringer")
	{{
		addPropertyOverride(new ResourceLocation(Minestuck.MODID, "broken"), PropertyBreakableItem.getPropertyOverride());
	}}.setTool(toolSword, 3, 6).addProperties(new PropertyBreakableItem());
	public static final Item caledfwlch = new MSUWeaponBase(1100, 16.0, -2.3, 16, "caledfwlch")
	{{
		addPropertyOverride(new ResourceLocation(Minestuck.MODID, "broken"), PropertyBreakableItem.getPropertyOverride());
	}}.setTool(toolSword, 4, 8).addProperties(new PropertyTrueDamage(), new PropertyBreakableItem());
	public static final Item caledscratch = new MSUWeaponBase(1375, 20.0, -2.07, 20, "caledscratch")
	{{
		addPropertyOverride(new ResourceLocation(Minestuck.MODID, "broken"), PropertyBreakableItem.getPropertyOverride());
	}}.setTool(toolSword, 4, 10).addProperties(new PropertyXpMend(), new PropertyBreakableItem());
	public static final Item doggMachete = new MSUWeaponBase(1513, 20.0, -2.3, 10, "doggMachete").setTool(toolSword, 4, 10).addProperties(new PropertyPotion(new PotionEffect(MobEffects.SLOWNESS, 200, 0), false, 0.4f), new PropertyKnockback(0.65f));
	public static final Item scarletRibbitar = new MSUWeaponBase(1375, 22.0, -2.415, 18, "scarletRibbitar")
	{{
		addPropertyOverride(new ResourceLocation(Minestuck.MODID, "broken"), PropertyBreakableItem.getPropertyOverride());
	}}.setTool(toolSword, 4, 10).addProperties(new PropertyFire(30, 0.5f, true), new PropertyBreakableItem());
	public static final Item cobaltSabre = new MSUWeaponBase(1210, 16.0, -2.07, 20, "cobaltSabre").setTool(toolSword, 4, 8).addProperties(new PropertyFire(8, 0.8f, true), new PropertyGristSetter(GristType.Cobalt));
	public static final Item zillywairCutlass = new MSUWeaponBase(3300, 33.6, -2.3, 40, "zillywairCutlass").setTool(toolSword, 5, 10);
	public static final Item regisword = new MSUWeaponBase(743, 12.0, -2.07, 8, "regisword").setTool(toolSword, 3, 6);
	public static final Item quantumSabre = new MSUWeaponBase(880, 14.4, -2.3, 10, "quantumSabre").setTool(toolSword, 3, 6).addProperties(new PropertyPotion(new PotionEffect(MobEffects.WITHER, 100, 1), false, 0.6f));
	public static final Item shatterBeacon = new MSUWeaponBase(1100, 34.0, -2.3, 14, "shatterBeacon").setTool(toolSword, 3, 8).addProperties(new PropertyPotion(false, 0.6f,
																																							  new PotionEffect(MobEffects.SPEED, 300, 0),
																																							  new PotionEffect(MobEffects.HASTE, 300, 0),
																																							  new PotionEffect(MobEffects.RESISTANCE, 300, 0),
																																							  new PotionEffect(MobEffects.JUMP_BOOST, 300, 0),
																																							  new PotionEffect(MobEffects.STRENGTH, 300, 0),
																																							  new PotionEffect(MobEffects.REGENERATION, 300, 1)
	));
	public static final Item claymore = new MSUWeaponBase(660, 18.4, -2.76, 5, "claymore").setTool(toolSword, 3, 4);
	public static final Item katana = new MSUWeaponBase(650, 8, -2.3, 6, "katana").setTool(toolSword, 1, 2);
	public static final Item trueUnbreakableKatana = (new MSUWeaponBase(-1, 24.0D, -2.07D, 20, "true_unbreakable_katana", "unbreakableKatana")).addProperties(new PropertySweep()).setTool(toolSword, 0, 15.0F);
	public static final Item bloodKatana = (new MSUWeaponBase(880, 16.0D, -2.07D, 10, "blood_katana", "bloodKatana")).addProperties(new PropertySweep(), new PropertyBloodBound()).setTool(toolSword, 0, 15.0F);
	public static final ItemBeamBlade batteryBeamBlade = new ItemBeamBlade(385, 8, -2.3, 30, "battery_beam_blade", "batteryBeamBlade").setTool(toolSword, 0, 15.0F);
	public static final ItemBeamBlade[] dyedBeamBlade = new ItemBeamBlade[] {
			new ItemBeamBlade(385, 8, -2.3, 30, "battery_beam_blade_white", "batteryBeamBlade").setTool(toolSword, 0, 15.0F).setColor(EnumDyeColor.WHITE),
			new ItemBeamBlade(385, 8, -2.3, 30, "battery_beam_blade_orange", "batteryBeamBlade").setTool(toolSword, 0, 15.0F).setColor(EnumDyeColor.ORANGE),
			new ItemBeamBlade(385, 8, -2.3, 30, "battery_beam_blade_magenta", "batteryBeamBlade").setTool(toolSword, 0, 15.0F).setColor(EnumDyeColor.MAGENTA),
			new ItemBeamBlade(385, 8, -2.3, 30, "battery_beam_blade_light_blue", "batteryBeamBlade").setTool(toolSword, 0, 15.0F).setColor(EnumDyeColor.LIGHT_BLUE),
			new ItemBeamBlade(385, 8, -2.3, 30, "battery_beam_blade_yellow", "batteryBeamBlade").setTool(toolSword, 0, 15.0F).setColor(EnumDyeColor.YELLOW),
			new ItemBeamBlade(385, 8, -2.3, 30, "battery_beam_blade_lime", "batteryBeamBlade").setTool(toolSword, 0, 15.0F).setColor(EnumDyeColor.LIME),
			new ItemBeamBlade(385, 8, -2.3, 30, "battery_beam_blade_pink", "batteryBeamBlade").setTool(toolSword, 0, 15.0F).setColor(EnumDyeColor.PINK),
			new ItemBeamBlade(385, 8, -2.3, 30, "battery_beam_blade_gray", "batteryBeamBlade").setTool(toolSword, 0, 15.0F).setColor(EnumDyeColor.GRAY),
			new ItemBeamBlade(385, 8, -2.3, 30, "battery_beam_blade_silver", "batteryBeamBlade").setTool(toolSword, 0, 15.0F).setColor(EnumDyeColor.SILVER),
			new ItemBeamBlade(385, 8, -2.3, 30, "battery_beam_blade_cyan", "batteryBeamBlade").setTool(toolSword, 0, 15.0F).setColor(EnumDyeColor.CYAN),
			new ItemBeamBlade(385, 8, -2.3, 30, "battery_beam_blade_purple", "batteryBeamBlade").setTool(toolSword, 0, 15.0F).setColor(EnumDyeColor.PURPLE),
			new ItemBeamBlade(385, 8, -2.3, 30, "battery_beam_blade_blue", "batteryBeamBlade").setTool(toolSword, 0, 15.0F).setColor(EnumDyeColor.BLUE),
			new ItemBeamBlade(385, 8, -2.3, 30, "battery_beam_blade_brown", "batteryBeamBlade").setTool(toolSword, 0, 15.0F).setColor(EnumDyeColor.BROWN),
			new ItemBeamBlade(385, 8, -2.3, 30, "battery_beam_blade_green", "batteryBeamBlade").setTool(toolSword, 0, 15.0F).setColor(EnumDyeColor.GREEN),
			new ItemBeamBlade(385, 8, -2.3, 30, "battery_beam_blade_red", "batteryBeamBlade").setTool(toolSword, 0, 15.0F).setColor(EnumDyeColor.RED),
			new ItemBeamBlade(385, 8, -2.3, 30, "battery_beam_blade_black", "batteryBeamBlade").setTool(toolSword, 0, 15.0F).setColor(EnumDyeColor.BLACK)};
	public static final Item lightbringer = new MSUWeaponBase(1375, 20, -2.07, 32, "lightbringer", "lightbringer").setTool(toolSword, 4, 10F).addProperties(new PropertySweep(), new PropertyFire(4, 0.8f, true), new PropertyLuckBasedDamage(0.5f), new PropertyMobTypeDamage(EnumCreatureAttribute.UNDEAD, 3));
	public static final Item cybersword = new MSUWeaponBase(1650, 28.8, -2.07, 32, "cybersword", "cybersword").setTool(toolSword, 5, 8F).addProperties(new PropertySweep(), new PropertyShock(15, 8, 0.5f, false), new PropertyLightning(15, 1, true, false), new PropertyLuckBasedDamage(0.1f)).setCreativeTab(null);
	public static final Item crystallineRibbitar = new MSUWeaponBase(3300, 16.4, -2.3, 24, "crystalline_ribbitar", "crystallineRibbitar").setTool(toolSword, 4, 6F).addProperties(new PropertySweep());
	public static final Item quantumEntangloporter = new MSUWeaponBase(1430, 19.2, -2.3, 9, "quantum_entangloporter", "quantumEntangloporter").setTool(toolSword, 3, 2F).addProperties(new PropertySweep(), new PropertyTeleBlock(Blocks.CACTUS, 20));
	public static final Item valorsEdge = new MSUWeaponBase(1650, 26.4, -2.07, 28, "valor_edge", "calamitySword").setTool(toolSword, 4, 4F).addProperties(new PropertySweep(), new PropertyCrowdDamage(0.3f, 3f, 20),
																																						  new PropertyLowHealthBoost(SharedMonsterAttributes.MOVEMENT_SPEED, "Calamity Boost", 1.2, 0.4, 0.4f, 2), new PropertyLowHealthBoost(SharedMonsterAttributes.ATTACK_SPEED, "Calamity Boost", 0.4, 0.2, 0.4f, 2));

	//Gauntletkind
	public static final Item fancyGlove = new MSUWeaponBase(200, 1D, 0, 5, "fancy_glove", "fancyGlove").setTool(toolGauntlet, 0, 1);
	public static final Item spikedGlove = new MSUWeaponBase(289, 5.7, -0.48, 8, "spiked_glove", "spikedGlove").setTool(toolGauntlet, 1, 1.4F);
	public static final Item cobbleBasher = new MSUWeaponBase(360, 7.2, -0.66, 4, "cobble_basher","cobbleBasher").setTool(toolGauntlet, 1, 4F);
	public static final Item fluoriteGauntlet = new MSUWeaponBase(1000, 9, -0.54,  8, "fluorite_gauntlet", "fluoriteGauntlet").setTool(toolGauntlet, 4, 8).addProperties(new PropertyRandomDamage());
	public static final Item goldenGenesisGauntlet = new MSUWeaponBase(1440, 23.1, -0.48D, 15, "golden_genesis_gauntlet","goldenGenesisGauntlet").setTool(toolGauntlet, 2, 3F);
	public static final Item pogoFist = new MSUWeaponBase(600, 7.2D, -0.6, 8, "pogo_fist", "pogoFist").setTool(toolGauntlet, 2, 4F).addProperties(new PropertyPogo(0.55D));
	public static final Item rocketFist = new MSUWeaponBase(360, 7.2D, -0.54, 6, "rocket_powered_fist", "rocketFist").setTool(toolGauntlet, 2, 4F).addProperties(new PropertyRocketDash(10, 15, 0.4f, 3));
	public static final Item jawbreaker = new MSUWeaponBase(660, 6.5, -0.6D, 6, "jawbreaker", "jawbreaker").setTool(toolGauntlet, 3, 1.6F).addProperties(new PropertyCandyWeapon());
	public static final Item eldrichGauntlet = new MSUWeaponBase(1200, 9.6, -0.72, 6, "eldritch_gauntlet", "eldrichGauntlet").setTool(toolGauntlet, 3, 6).addProperties(new PropertyEldrichBoost());
	public static final Item gauntletOfZillywenn = new MSUWeaponBase(2400, 24.5, -0.78, 14, "gauntlet_of_zillywenn","gauntletOfZillywenn").setTool(toolGauntlet, 5, 8F);
	public static final Item gasterBlaster = new ItemWindUpBeam(640, 7.7, -0.66D, 0.05f, 10, 1.3f, 16, 0, 16, "gaster_blaster","gasterBlaster").setSounds(MSUSoundHandler.gasterBlasterCharge, MSUSoundHandler.gasterBlasterRelease).setTool(toolGauntlet, 6, 4F)
													 .setTool(toolGauntlet, 4, 8).addProperties(new PropertyPotionBeam(new PotionEffect(MobEffects.WITHER, 100, 0)), new PropertyBeamDeathMessage("sans"));
	public static final Item midasGlove = new MSUWeaponBase(800, 8.7D, -0.57D, 14, "midas_glove","midasGlove").setTool(toolGauntlet, 3, 10F).addProperties(new PropertyGristSetter(GristType.Gold));

	//Needlekind
	public static final Item knittingNeedles = new ItemKnittingNeedles(128,1.6, -0.24, 1, "knitting_needle", "knittingNeedle").setTool(toolNeedles, 2, 1f);
	public static final Item pointySticks = new MSUWeaponBase(300,3.2, -0.3, 10, "pointy_stick", "pointyStick").setTool(toolNeedles, 1, 1f).addProperties(new PropertyDualWield()).addProperties(new PropertyTipperDamage(0.9f, 1.1f, 0), new PropertyMobTypeDamage(EnumCreatureAttribute.UNDEAD, 2)).setRepairMaterial("plankWood");
	public static final Item boneNeedles = new MSUWeaponBase(225,3.6, -0.3, 5, "bone_needle", "boneNeedle").setTool(toolNeedles, 1, 1f).addProperties(new PropertyTipperDamage(0.7f, 1.25f, 0), new PropertyDualWield()).setRepairMaterial("bone");
	public static final Item drumstickNeedles = new MSUWeaponBase(500,4, -0.3, 5, "drumstick_needles", "drumstickNeedles").setTool(toolNeedles, 2, 1f).addProperties(new PropertyTipperDamage(0.7f, 1.25f, 0), new PropertyDualWield(), new PropertySoundOnHit(SoundEvents.BLOCK_NOTE_BASEDRUM, PITCH_NOTE, ((stack, target, player) -> 3f)), new PropertySoundOnClick(new SoundEvent[] {SoundEvents.BLOCK_NOTE_BASEDRUM}, PITCH_NOTE, ((stack, target, player) -> 3f), true));
	public static final Item dragonBlades = new MSUWeaponBase(500, 7.9, -0.36, 4, "dragon_blade", "dragonBlades").setTool(toolNeedles, 2, 4).addProperties(new PropertyDualWield(), new PropertyTipperDamage(0.8f, 1.3f, 0.1f)).setCreativeTab(null);
	public static final Item litGlitterBeamTransistor = new ItemBeamWeapon(700,5.5, -0.3, 0.1f, 20, 1, 72000, 40, 30, "lit_glitter_beam_transistor", "litGlitterBeamTransistor").setTool(toolNeedles, 2, 4f).addProperties(new PropertyDualWield(), new PropertyTipperDamage(0.9f, 1.1f, 0), new PropertyRainbowBeam(), new PropertyBeamDeathMessage("rainbow"));
	public static final Item needlewands = new ItemBeamWeapon(488,6.5, -0.3, 0.05f, 10, 1, 60, "needlewand", "needlewand").setTool(toolNeedles, 3, 2f).addProperties(new PropertyDualWield(), new PropertyTipperDamage(0.95f, 1.15f, 0), new PropertyMagicBeam(), new PropertyBeamDeathMessage("magic"));
	public static final Item oglogothThorn = new ItemBeamWeapon(666,7.2, -0.3, 0.1f, 30, 0.7f, 5, 20, 80, "thorn_of_oglogoth", "oglogothThorn").setTool(toolNeedles, 4, 3f).addProperties(new PropertyDualWield(), new PropertyTipperDamage(0.6f, 1.6f, 0.12f));
	public static final Item echidnaQuills = new MSUWeaponBase(17.3, -0.3, 100, "quill_of_echidna", "echidnaQuill").setTool(toolNeedles, 10, 5f).addProperties(new PropertyDualWield(), new PropertyTipperDamage(1f, 1.2f, 0));
	public static final Item thistlesOfZillywitch = new MSUWeaponBase(800, 9.1,  -0.3, 40, "thistles_of_zillywitch", "thistlesOfZillywitch").setTool(toolNeedles, 5, 10f).addProperties(new PropertyDualWield(), new PropertyTipperDamage(0.9f, 1.3f, 0));

	//Shieldkind
	public static final Item woodenDoorshield = new MSUShieldBase(340, 12, 0.3f, 5, "wooden_doorshield", "woodenDoorshield").setRepairMaterial("plankWood");
	public static final Item ironDoorshield = new MSUShieldBase(540, 8, 0.4f, 7, "iron_doorshield", "ironDoorshield").setRepairMaterial("ingotIron");
	public static final Item clearShield = new MSUShieldBase(180, 20, 0.25f, 5, "clear_shield", "clearShield");
	public static final Item bladedShield = new MSUShieldBase(300, 7, -1.2, 10, 0.32f, 6, "bladed_shield", "bladedShield");
	public static final Item shockerShell = new MSUShieldBase(480, 14, 0.3f, 8, "shocker_shell", "shockerShell").setRepairMaterials(new ItemStack(battery)).addProperties(new PropertyElectric(10, 2, 0, false), new PropertyShieldShock(5, 2, 0.1f, 10, 4, 0.8f));
	public static final Item rocketRiotShield = new MSUShieldBase(450, 6, 0.35f, 7, "rocket_riot_shield", "rocketRiotShield").setRepairMaterial("gunpowder").addProperties(new PropertyRocketBoost(0.4f));
	public static final Item ejectorShield = new MSUShieldBase(320, 7, 0.3f, 7, "ejector_shield", "ejectorShield").addProperties(new PropertyShieldEject(4f, 15));
	public static final Item firewall = new MSUShieldBase(320, 7, 0.3f, 7, "firewall", "firewall").addProperties(new PropertyShieldFire(10, 1000, 0.7f, 1f, true));
	public static final Item clarityWard = new MSUShieldBase(410, 8, 0.25f, 12, "clarity_ward", "christopherShield");
	public static final Item obsidianShield = new MSUShieldBase(2000, 0, -3, 12, 0.6f, 10, "obsidian_shield", "obsidianShield").addProperties(new PropertyUseOnCooled(), new PropertyVisualParry());
	public static final Item windshield = new MSUShieldBase(355, 18, 0.1f, 7, "windshield", "windshield").addProperties(new PropertyShieldKnockback(2f, true), new PropertyShieldKnockback(0.3f, false));
	public static final Item wallOfThorns = new MSUShieldBase(440, 10, 0.5f, 7, "wall_of_thorns", "wallOfThorns").addProperties(new PropertyShieldPotion(true, 1f, new PotionEffect(MobEffects.POISON, 400, 1)), new PropertyShieldPotion(false, 0.1f, new PotionEffect(MobEffects.POISON, 100, 0)));
	public static final Item hardRindHarvest = new MSUShieldBase(320, 7, 0.4f, 6, "hard_rind_harvest", "hardRindHarvest").addProperties(new PropertyEdible(2, 0.4f, 5).setPotionEffect(0.1f, new PotionEffect(MobEffects.INSTANT_HEALTH, 1, 0)));
	public static final Item nuclearNeglector = new MSUShieldBase(480, 8, 0.15f, 8, "nuclear_neglector", "nuclearNeglector").addProperties(new PropertyShieldPotionNegative(new PotionEffect(MobEffects.WITHER, 60, 2)), new PropertyShieldPotion(true, 1, new PotionEffect(MobEffects.WITHER, 600, 2)));
	public static final Item livingShield = new MSUShieldBase(465, 6, 0.0f, 8, "living_shield", "livingShield").addProperties(new PropertyShieldHeal(0.6f, true));
	public static final Item perfectAegis = new MSUShieldBase(800, 3, 1f, 12, "perfect_aegis", "perfectAegis").addProperties(new PropertyShieldDeflect(1, 5), new PropertyVisualParry());

	//Bowkind
	public static final MSUBowBase.IIsArrow REGULAR_ARROWS = stack -> stack.getItem() == Items.ARROW;

	public static final MSUBowBase energyBow = (MSUBowBase) new MSUBowBase(330, 2.1f, 18, 2.1f, 1.1f, 1, true, "energy_bow", "energyBow").requireNoAmmo().addProperties(new PropertyLaserArrow());
	public static final MSUBowBase infernoShot = (MSUBowBase) new MSUBowBase(385, 2, 24, 2, 0.9f, 1, true, "inferno_shot", "infernoShot").addProperties(new PropertyFlamingArrow(10, 0.9f));
	public static final MSUBowBase icicleBow = (MSUBowBase) new MSUBowBase(230, 2, 30, 2, 0.7f, 1, true, "icicle_bow", "icicleBow").setArrowCheck(REGULAR_ARROWS).addProperties(new PropertyPotionArrow(new PotionEffect(MobEffects.SLOWNESS, 200, 2), 0.8f));
	public static final MSUBowBase tempestBow = (MSUBowBase) new MSUBowBase(540, 2.3f, 16, 3.1f, 1.2f, 1, true, "tempest_bow", "tempestBow").addProperties(new PropertyHookshot(0.8f, 16, true, true, true), new PropertyLaserArrow());
	public static final MSUBowBase shiverburnWing = (MSUBowBase) new MSUBowBase(390, 2.2f, 27, 2.2f, 0.8f, 1, true, "shiverburn_wing", "shiverburnWing").setArrowCheck(REGULAR_ARROWS).addProperties(new PropertyPotionArrow(new PotionEffect(MobEffects.SLOWNESS, 140, 2), 0.8f), new PropertyFlamingArrow(7, 0.9f));
	public static final MSUBowBase magneticHookshot = (MSUBowBase) new MSUBowBase(680, 1f, 32, 4f, 0.8f, 1, true, "magnetic_hookshot", "magneticHookshot").setArrowCheck(REGULAR_ARROWS).addProperties(new PropertyHookshot(1, 64));
	public static final MSUBowBase wormholePiercer = (MSUBowBase) new MSUBowBase(640, 1, 35, 3.5f, 0.8f, 1, true, "wormhole_piercer", "wormholePiercer").setArrowCheck(REGULAR_ARROWS).addProperties(new PropertyTeleArrows(), new PropertyLaserArrow());
	public static final MSUBowBase telegravitationalWarper = (MSUBowBase) new MSUBowBase(640, 3, 28, 2.9f, 0.8f, 1, true, "telegravitational_warper", "telegravitationalWarper").setArrowCheck(REGULAR_ARROWS).addProperties(new PropertyHookshot(0.4f, 16, false, false, true), new PropertyLaserArrow(), new PropertyGhostArrow());
	public static final MSUBowBase crabbow = new MSUBowBase(2048, 7, -1.8, 2.3f, 27, 1.95f, 1, 1, false, "crabbow", "crabbow");
	public static final ItemMechanicalCrossbow mechanicalCrossbow = new ItemMechanicalCrossbow(385, 1,"mechanical_crossbow", "mechanicalCrossbow");
	public static final MSUBowBase sweetBow = (MSUBowBase) new MSUBowBase(450, 1.8f, 20, 2.1f, 0.9f, 1, true, "sweet_bow", "sweetBow").addProperties(new PropertyCandyWeapon());
	public static final MSUBowBase kingOfThePond = (MSUBowBase) new MSUBowBase(890, 2, 10, 5, 1.2f, 1, true, "king_of_the_pond", "kingOfThePond").addProperties(new PropertyFlamingArrow(20, 0.65f));
	public static final MSUBowBase gildedGuidance = (MSUBowBase) new MSUBowBase(1210, 3.2f, 30, 0.0f, 0, 2, true, "gilded_guidance", "gildedGuidance").requireNoAmmo().addProperties(new PropertyLaserArrow(), new PropertyGuidedArrow());
	public static final MSUBowBase bowOfLight = (MSUBowBase) new MSUBowBase(2050, 4f, 24, 5, 0.3f, 3, true, "bow_of_light", "bowOfLight").requireNoAmmo().addProperties(new PropertyLaserArrow(), new PropertyArrowNoGravity(), new PropertyFlamingArrow(6, 0.9f), new PropertyPierce(0.1f));
	public static final MSUBowBase theChancemaker = (MSUBowBase) new MSUBowBase(1280, 1f, 16, 3, 1.3f, 2, true, "the_chancemaker", "theChancemaker").addProperties(new PropertyRandomDamage());
	public static final MSUBowBase wisdomsPierce = (MSUBowBase) new MSUBowBase(1560, 3, 22, 4, 0.3f, 2, false, "wisdom_pierce", "calamityBow").addProperties(new PropertyPierce(0.4f), new PropertyLowHealthBoost(SharedMonsterAttributes.LUCK, "Calamity Boost", 2, 0.4, 0.5f, 1), new PropertyLowHealthDrawSpeed(0.5f, 0.2f));
	public static final MSUBowBase wisdomsHookshot = (MSUBowBase) new MSUBowBase(1560, 0.5f, 22, 3, 0.3f, 2, true, "wisdom_hookshot", "calamityHookshot").requireNoAmmo().addProperties(new PropertyHookshot(1, 128), new PropertyArrowNoGravity(), new PropertyLowHealthBoost(SharedMonsterAttributes.LUCK, "Calamity Boost", 2, 0.4, 0.5f, 1)).setCreativeTab(null);

	//Hammerkind
	public static final Item clawHammer = new MSUWeaponBase(259, 6.4, -2.52, 1, "claw_hammer", "clawHammer").setTool(toolHammer, 1, 2).addProperties(new PropertyVMotionDamage(1.6f, 3));
	public static final Item sledgeHammer = new MSUWeaponBase(575, 12.8, -2.8, 3, "sledge_hammer", "clawHammer").setTool(toolHammer, 2, 4);
	public static final Item blacksmithHammer = new MSUWeaponBase(575, 12.8, -2.8, 2, "blacksmith_hammer", "blacksmithHammer").setTool(toolHammer, 1, 6);
	public static final Item pogoHammer = new MSUWeaponBase(863, 19.2, -2.8, 4, "pogo_hammer", "pogoHammer").setTool(toolHammer, 2, 4).addProperties(new PropertyPogo(0.7), new PropertyVMotionDamage(2f, 4));
	public static final Item telescopicSassacrusher = new MSUWeaponBase(1610, 51.2, -3.64, 4, "telescopicSassacrusher").setTool(toolHammer, 8, 2).addProperties(new PropertyFarmine(100, 128), new PropertyVMotionDamage(1.8f, 10));
	public static final Item regiHammer = new MSUWeaponBase(776, 19.2, -2.52, 4, "regiHammer").setTool(toolHammer, 3, 6);
	public static final Item fearNoAnvil = new MSUWeaponBase(1725, 32, -2.66, 8, "fearNoAnvil").setTool(toolHammer, 3, 10).addProperties(new PropertyPotion(false, 0.5f, new PotionEffect(MobEffects.SLOWNESS, 400, 1), new PotionEffect(MobEffects.MINING_FATIGUE, 400, 2)), new PropertyVMotionDamage(1.6f, 3));
	public static final Item meltMasher = new MSUWeaponBase(1265, 25.6, -2.8, 4, "meltMasher").setTool(toolHammer, 4, 8).addProperties(new PropertyAutoSmelt(), new PropertyFarmine(8, 5), new PropertyFire(4, 1, false));
	public static final Item zillyhooHammer = new MSUWeaponBase(3450, 69.1, -2.94, 40, "zillyhooHammer").setTool(toolHammer, 5, 10);
	public static final Item scarletZillyhoo = new MSUWeaponBase(2588, 49.9, -2.8, 60, "scarletZillyhoo").setTool(toolHammer, 5, 10).addProperties(new PropertyFire(10, 0.8f, true));
	public static final Item popamaticVrillyhoo = new MSUWeaponBase(3019, 20, -2.66, 20, "popamaticVrillyhoo").setTool(toolHammer, 5, 12).addProperties(new PropertyRandomDamage(0, 7, 4));
	public static final Item mwrthwl = new MSUWeaponBase(1725, 32, -2.8, 8, "mwrthwl").setTool(toolHammer, 4, 8).addProperties(new PropertyTrueDamage());
	public static final Item qPHammerAxe = new MSUWeaponBase(1290, 28.8, -2.9, 4, "qPHammerAxe").setTool(toolHammaxe, 4, 6).addProperties(new PropertyPogo(0.6), new PropertyFarmine(25, 64));
	public static final Item qFHammerAxe = new MSUWeaponBase(1209, 43.2, -3, 8, "qFHammerAxe").setTool(toolHammaxe, 4, 8).setRepairMaterials(new ItemStack(rawUranium)).addProperties(new PropertyPogo(0.7), new PropertyFarmine(25, 32), new PropertyPotion(true, 0.5f, new PotionEffect(MobEffects.WITHER, 200, 2)));
	public static final Item qEHammerAxe = new MSUWeaponBase(1725, 38.4, -2.8, 12, "qEHammerAxe").setTool(toolHammer, 4, 6).addProperties(new PropertyPogo(0.8), new PropertyFarmine(25, 8));
	public static final Item dDEHammerAxe = new MSUWeaponBase(1724, 38.4, -2.8, 12, "dDEHammerAxe").setTool(toolHammer, 0, 6).addProperties(new PropertyPogo(1), new PropertySoundOnHit(MinestuckSoundHandler.soundScreech, 1F, 1.5F));
	public static final Item loghammer = new MSUWeaponBase(776, 12.8, -2.8, 7, "loghammer", "loghammer").setTool(toolHammer, 0, 3.0f).setRepairMaterial("logWood");
	public static final Item overgrownLoghammer = new MSUWeaponBase(575, 19.2, -2.8, 7, "overgrown_loghammer", "overgrownLoghammer").setTool(toolHammer, 0, 3.0f).setRepairMaterial("logWood").addProperties(new PropertyPlantMend());
	public static final Item glowingLoghammer = new MSUWeaponBase(906, 19.2, -2.8, 7, "glowing_loghammer", "glowingLoghammer").setTool(toolHammer, 0, 3.0f).setRepairMaterials(new ItemStack(MinestuckBlocks.glowingLog)).addProperties(new PropertyPotion(new PotionEffect(MobEffects.GLOWING, 200, 0), false, 1));
	public static final Item midasMallet = new MSUWeaponBase(1150, 26.9D, -2.94D, 15, "midas_mallet", "midasMallet").setTool(toolHammer, 3, 2f).addProperties(new PropertyGristSetter(GristType.Gold), new PropertyVMotionDamage(1.6f, 3));
	public static final Item aaaNailShocker = new MSUWeaponBase(776, 19.2, -2.52, 10,"aaa_nail_shocker", "aaaNailShocker").setTool(toolHammer, 2, 3f).setRepairMaterials(new ItemStack(battery)).addProperties(new PropertyElectric(20, 0, 0.7f, true));
	public static final Item highVoltageStormCrusher = new MSUWeaponBase(1150, 30.7, -2.8, 18, "high_voltage_storm_crusher", "highVoltageStormCrusher").setTool(toolHammer, 4, 3.0f).addProperties(new PropertyLightning(8, 1, true, false), new PropertyElectric(60, 8, -1, false), new PropertyVMotionDamage(1.6f, 3));
	public static final Item barrelsWarhammer = new MSUWeaponBase(1438, 64, -2.8, 18, "barrel_warhammer", "calamityHammer").setTool(toolHammer, 4, 4.0f).addProperties(new PropertyRocketBoost(0.6f), new PropertyLowHealthBoost(SharedMonsterAttributes.ATTACK_DAMAGE, "Calamity Boost", 0.6, 0.1, 0.3f, 2), new PropertyVMotionDamage(1.6f, 3), new PropertyVMotionDamage(1.6f, 3));
	public static final Item stardustSmasher = new MSUWeaponBase(1725, 44.8, -2.8, 20, "stardust_smasher", "stardustSmasher").setTool(toolHammer, 20, 8.0f).addProperties(new PropertyMobTypeDamage(EnumCreatureAttribute.ARTHROPOD, 1000), new PropertyVMotionDamage(1.6f, 3));

	//Clawkind
	public static final Item katars = new MSUWeaponBase(248, 1.6, -0.65, 2, "katars", "katars").setTool(toolClaws, 2, 3).addProperties(new PropertySweep(), new PropertyDualWield());
	public static final Item diamondKatars = new MSUWeaponBase(900, 2.5, -0.65, 6, "diamond_katars", "diamondKatars").setTool(toolClaws, 2, 3).addProperties(new PropertySweep(), new PropertyDualWield());
	public static final Item actionClaws = new ItemDualClaw(608, 3.9D, 0.0D, -0.55D, -0.0D, 6, "actionClaws", "action_claws").setTool(toolClaws, 2, 3).addProperties(new PropertyActionBuff(200, 2.5));
	public static final Item candyCornClaws = new ItemDualClaw(743, 4.8, 0.0D, -0.65D, -0.0D, 6, "candyCornClaws","candy_corn_claws").setTool(toolClaws, 2, 3).addProperties(new PropertyCandyWeapon());
	public static final Item sneakyDaggers = new ItemDualClaw(900, 5.5, 0.0D, -0.61D, -0.0D, 7, "sneakyDaggers","sneaky_daggers").setTool(toolClaws, 2, 3).addProperties(new PropertySneaky(1.2f, 1.1f, 1.6f));
	public static final Item rocketKatars = new MSUWeaponBase(585, 6.4, -0.6, 8, "rocket_katars", "rocketKatars").setTool(toolClaws, 2, 5).setRepairMaterial("gunpowder").addProperties(new PropertySweep(), new PropertyDualWield(), new PropertyRocketDash(3, 20, 0.3f, 2.5f));
	public static final Item blizzardCutters = new ItemDualClaw(810, 6.4, 0,-0.65, 0, 8, "blizzardCutters", "blizzard_cutters").setTool(toolClaws, 3, 3).addProperties(new PropertyPotion(new PotionEffect(MobEffects.SLOWNESS, 400, 0), false, 0.4f), new PropertyKnockback(2.2f));
	public static final Item thunderbirdTalons = new MSUWeaponBase(1125, 8.8,-0.65, 18, "thunderbird_talons", "thunderbirdTalons").setTool(toolClaws, 5, 3).setRepairMaterials(new ItemStack(battery)).addProperties(new PropertySweep(), new PropertyDualWield(), new PropertyShock(10, 3, 0.4f, true), new PropertyKnockback(1.8f));
	public static final Item archmageDaggers = new ItemBeamWeapon(1350, 9.6,-0.68, 0.05f, 15, 1, 60, 10, 18, "archmage_daggers", "archmageDaggers").setTool(toolClaws, 5, 3).addProperties(new PropertySweep(), new PropertyDualWield(), new PropertyMagicBeam(), new PropertyBeamDeathMessage("magic"));
	public static final Item katarsOfZillywhomst = new ItemDualClaw(2700, 15.3, 0,-0.65, 0, 40, "katarsOfZillywhomst", "katars_of_zillywhomst").setTool(toolClaws, 5, 10).addProperties(new PropertySweep(), new PropertyDualWield());
	public static final Item bladesOfTheWarrior = new MSUWeaponBase(1080, 6.4,-0.65, 24, "blades_of_the_warrior", "christopherClaws").setTool(toolClaws, 4, 3).addProperties(new PropertySweep(), new PropertyDualWield()).setCreativeTab(null);

	//Canekind
	public static final Item staffOfOvergrowth = new MSUWeaponBase(1040, 14.4, -2, 20, "staff_of_overgrowth", "staffOfOvergrowth").setTool(toolCane, 3, 2).addProperties(new PropertyBlockSwap(overgrowthTransforms, 1), new PropertyPotion(new PotionEffect(MobEffects.POISON, 400, 1), false, 0.4f));
	public static final Item atomicIrradiator = new MSUWeaponBase(900, 18, -2, 20, "atomic_irradiator", "atomicIrradiator").setTool(toolCane,3, 3).addProperties(new PropertyPotion(new PotionEffect(MobEffects.WITHER, 400, 1), true, 0.6f), new PropertyGristSetter(GristType.Uranium));
	public static final Item goldCane = new MSUWeaponBase(600, 12, -2, 18, "gold_cane", "goldCane").setTool(toolCane, 2, 5).setRepairMaterial("ingotGold");
	public static final Item goldenCuestaff = new MSUWeaponBase(1000, 20, -2, 32, "golden_cuestaff", "goldenCuestaff").setTool(toolCane, 2, 6);
	public static final Item scepterOfZillywuud = new MSUWeaponBase(2400, 43.2, -2, 32, "scepter_of_zillywuud", "scepterOfZillywuud").setTool(toolCane, 5, 10);
	public static final Item cane = new MSUWeaponBase(200, 4, -2, 1, "cane").setTool(toolCane, 0, 1);
	public static final Item ironCane = new MSUWeaponBase(480, 8, -2, 3, "ironCane").setTool(toolCane, 1, 2);
	public static final Item spearCane = new MSUWeaponBase(660, 13.2, -2, 3, "spearCane").setTool(toolCane, 2, 3).addProperties(new PropertyTipperDamage(0.9f, 1.1f, 1));
	public static final Item paradisesPortabello = new MSUWeaponBase(540, 12, -2, 2, "paradisesPortabello").setTool(toolCane, 2, 2).addProperties(new PropertyEdible(4, 2, 5));
	public static final Item regiCane = new MSUWeaponBase(540, 12, -1.8, 5, "regiCane").setTool(toolCane, 3, 4).addProperties(new PropertyTipperDamage(0.8f, 1.1f, 1));
	public static final Item dragonCane = new MSUWeaponBase(1000, 24, -2, 7, "dragonCane").setTool(toolCane, 4, 3);
	public static final Item pogoCane = new MSUWeaponBase(600, 12, -2, 5, "pogoCane").setTool(toolCane, 2, 4).addProperties(new PropertyPogo(0.6));
	public static final Item upStick = new MSUWeaponBase(-1, 6.4, -2, 5, "upStick").setTool(toolCane, 1, 3).addProperties(new PropertyPotion(true, 1, new PotionEffect(MobEffects.WITHER, 20, 2)));

	//Clubkind
	public static final Item rubyContrabat = new MSUWeaponBase(1200, 14.1, -1.98, 22, "ruby_contrabat", "rubyContrabat").setTool(toolClub, 3, 4.0f).addProperties(new PropertySweep(), new PropertyGristSetter(GristType.Ruby), new PropertyProjectileDeflect(0.5f, 4));
	public static final Item homeRunBat = new MSUWeaponBase(3240, 16.9, -3.9, 10, "home_run_bat", "homeRunBat").setTool(toolClub, 5, 2.0f).addProperties(new PropertySweep(), new PropertyKnockback(15), new PropertySoundOnHit(MSUSoundHandler.homeRunBat, 1, 1.2f), new PropertyProjectileDeflect(1, 10));
	public static final Item dynamiteStick = new MSUWeaponBase(1050, 17.6, -2.2, 8, "dynamite_stick", "dynamiteStick").setTool(toolClub, 1, 2.0f).addProperties(new PropertySweep(), new PropertyExplode(2.5f, 1, true), new PropertyProjectileDeflect(0.6f, 4));
	public static final Item nightmareMace = new MSUWeaponBase(1200, 14.1, -2.09, 8, "nightmare_mace", "nightmareMace").setTool(toolClub, 3, 3.0f).addProperties(new PropertySweep(), new PropertyHungry(3, 4, true), new PropertyProjectileDeflect(0.3f, 8), 
																																								 new PropertyPotion(new PotionEffect(MobEffects.BLINDNESS, 200, 0), false, 0.2f), new PropertyPotion(new PotionEffect(MobEffects.NAUSEA, 200, 0), false, 0.2f), new PropertyPotion(new PotionEffect(MobEffects.WITHER, 100, 1), true, 0.2f));
	public static final Item cranialEnder = new MSUWeaponBase(1800, 17.6, -2.2, 8, "cranial_ender", "cranialEnder").setTool(toolClub, 5, 2.0f).addProperties(new PropertySweep(), new PropertyExplode(0.5f, 0.2f, true), new PropertyPotion(new PotionEffect(MobEffects.NAUSEA, 100, 0), true, 0.7f), new PropertyProjectileDeflect(0.6f, 3));
	public static final Item badaBat = new MSUWeaponBase(8035, 15.9, -3.9, 10, "bada_bat", "badaBat").setTool(toolClub, 5, 14.0f).addProperties(new PropertySweep(), new PropertyKnockback(15), new PropertySoundOnHit(MSUSoundHandler.homeRunBat, 1, 1.2f), new PropertySoundOnClick(MSUSoundHandler.bada, 1, 1.2f), new PropertyProjectileDeflect(1, 10)).setCreativeTab(null);
	public static final Item deuceClub = new MSUWeaponBase(270, 3.5, -2.2, 1, "deuceClub").setTool(toolClub, 0, 1).addProperties(new PropertyProjectileDeflect(0.1f, 1));
	public static final Item metalBat = new MSUWeaponBase(720, 7.1, -2.42, 4, "metalBat").setTool(toolClub, 2, 4).addProperties(new PropertyProjectileDeflect(0.25f, 2));
	public static final Item pogoClub = new MSUWeaponBase(900, 10, -2.2, 6, "pogoClub").setTool(toolClub, 2, 6).addProperties(new PropertyProjectileDeflect(0.4f, 1.5f), new PropertyPogo(0.5f));
	public static final Item spikedClub = new MSUWeaponBase(900, 11.1, -2.2, 6, "spikedClub").setTool(toolClub, 3, 3).addProperties(new PropertyProjectileDeflect(0.15f, 1.5f));
	public static final Item nightClub = new MSUWeaponBase(1320, 11.2, -2.2, 6, "nightClub").setTool(toolClub, 3, 3).addProperties(new PropertyProjectileDeflect(0.2f, 3), new PropertyDaytimeDamage(false, 1.4f));

	//Dicekind TODO
	public static final Item dice = new MSUItemBase("dice", "dice").setCreativeTab(TabsMinestuck.minestuck);
	public static final Item fluoriteOctet = new MSUItemBase("fluorite_octet", "fluoriteOctet").setCreativeTab(TabsMinestuck.minestuck);

	//Throwkind
	public static final MSUThrowableBase yarnBall = new ItemYarnBall("yarn_ball", "yarnBall");
	public static final MSUThrowableBase wizardbeardYarn = new MSUThrowableBase("wizardbeard_yarn", "wizardbeardYarn").addProperties(new PropertyMagicDamagePrjctle(6));
	public static final Item dragonCharge = new ItemDragonCharge("dragon_charge", "dragonCharge");
	public static final MSUThrowableBase throwingStar = new MSUThrowableBase(8, 0, 32, "throwing_star", "throwingStar").addProperties(new PropertyDamagePrjctle(4), new PropertyThrowGravity(0.7f), new PropertyBreakableProjectile(0.7f));
	public static final MSUThrowableBase goldenStar = new MSUThrowableBase(4, 0, 64, "golden_star", "goldenStar").addProperties(new PropertyDamagePrjctle(2), new PropertyThrowGravity(0.4f), new PropertyBreakableProjectile(0.9f));
	public static final MSUThrowableBase suitarang = new MSUThrowableBase(8, 0, 32, "suitarang", "suitarang").addProperties(new PropertyDamagePrjctle(6), new PropertyThrowGravity(0.7f), new PropertyBreakableProjectile(0.5f), new PropertyVariableItem(4));
	public static final MSUThrowableBase psionicStar = new MSUThrowableBase(10, 0, 16, "psionic_star", "psionicStar").setSize(3).addProperties(new PropertyDamagePrjctle(12, true), new PropertyThrowGravity(0.7f), new PropertyBreakableProjectile(0.2f));
	public static final MSUThrowableBase boomerang = new MSUThrowableBase(10, 0, 1, 1f, 2, -0.5, "boomerang", "boomerang"){{setMaxDamage(64);}}.addProperties(new PropertyDamagePrjctle(5), new PropertyThrowGravity(0.6f), new PropertyBoomerang());
	public static final MSUThrowableBase markedBoomerang = new MSUThrowableBase(10, 0, 1, 1f, 2, -0.5, "marked_boomerang", "markedBoomerang"){{setMaxDamage(64);}}.addProperties(new PropertyDamagePrjctle(5), new PropertyThrowGravity(0.6f), new PropertyBoomerang());
	public static final MSUThrowableBase redHotRang = new MSUThrowableBase(12, 0, 1, 1f, 4, -0.5, "red_hot_rang", "redHotRang"){{setMaxDamage(80);}}.addProperties(new PropertyDamagePrjctle(7), new PropertyThrowGravity(0.6f), new PropertyBoomerang(), new PropertyFirePrjctle(5, false));
	public static final MSUThrowableBase tornadoGlaive = new MSUThrowableBase(8, 0, 1, 1f, 6, -1f, "tornado_glaive", "tornadoGlaive"){{setMaxDamage(550);}}.setSize(2).addProperties(new PropertyDamagePrjctle(8), new PropertyPrjctleItemPull(16, 0.5f), new PropertyBoomerang(), new PropertyThrowGravity(0.4f));
	public static final MSUThrowableBase hotPotato = new MSUThrowableBase(0, 5, 16, "hot_potato", "hotPotato").addProperties(new PropertyDamagePrjctle(10), new PropertyFirePrjctle(10, true));

	//Rockkind
	public static final MSUThrowableBase pebble = new MSUThrowableBase(0, 0, 16, 1.4f, 0, 0, "pebble", "pebble").addProperties(new PropertyDamagePrjctle(2), new PropertyThrowGravity(1.5f));
	public static final MSUThrowableBase rock = new MSUThrowableBase(10, 5, 16, 1.2f, 5, -2.7, "rock", "rock").addProperties(new PropertyDamagePrjctle(8), new PropertyThrowGravity(2.5f));
	public static final Item bigRock = new ItemBigRock("big_rock", "bigRock");

	//Sicklekind
	public static final Item sickle = new MSUWeaponBase(275, 3.6, -2.4, 1, "sickle").setTool(toolSickle, 1, 2).addProperties(new PropertySweep(2), new PropertyFarmine(1, 500));
	public static final Item homesSmellYaLater = new MSUWeaponBase(990, 11.9, -2.4, 10, "homesSmellYaLater").setTool(toolSickle, 2, 3).addProperties(new PropertySweep(3f), new PropertyFarmine(2, 500));
	public static final Item fudgeSickle = new MSUWeaponBase(880, 17.3, -2.64, 6, "fudgeSickle").setTool(toolSickle, 2, 2).addProperties(new PropertySweep(2.5f), new PropertyEdible(6, 1, 10));
	public static final Item candySickle = new MSUWeaponBase(908, 10.8, -2.4, 5, "candySickle").setTool(toolSickle, 3, 4).addProperties(new PropertySweep(3), new PropertyFarmine(2, 500), new PropertyCandyWeapon());
	public static final Item regiSickle = new MSUWeaponBase(743, 10.8, -2.16, 8, "regiSickle").setTool(toolSickle, 3, 4).addProperties(new PropertySweep(3), new PropertyFarmine(3, 500));
	public static final Item clawSickle = new MSUWeaponBase(1375, 23.5, -2.64, 8, "clawSickle").setTool(toolSickle, 3, 3).addProperties(new PropertySweep(5), new PropertyFarmine(2, 500));
	public static final Item clawOfNrubyiglith = new MSUWeaponBase(1650, 23.5, -2.4, 12, "clawOfNrubyiglith").setTool(toolSickle, 4, 3).addProperties(new PropertySweep(3.5f), new PropertyWhisperingTerror(0.15f));
	public static final Item hereticusAurum = new MSUWeaponBase(110, 15.9, -2.16, 32, "hereticus_aurum", "hereticusAurum").setTool(toolSickle, 5, 4).addProperties(new PropertySweep(3), new PropertyFarmine(5, 500));
	
	//Spoonkind
	public static final Item woodenSpoon = new MSUWeaponBase(300, 3.2, -2, 1, "woodenSpoon").setTool(toolSpoon, 0, 2).addProperties(new PropertyHungerSpeed(1.2f));
	public static final Item silverSpoon = new MSUWeaponBase(600, 6.4, -1.88, 8, "silverSpoon").setTool(toolSpoon, 2, 4).addProperties(new PropertyHungerSpeed(1.1f));
    public static final Item crockerSpoon = new MSUWeaponBase(900, 9.6, -2.2, 6, "crockerSpoon").setTool(toolSpoon, 4, 8).addProperties(new PropertyHungerSpeed(1.2f));
	public static final Item fancySpoon = new MSUWeaponBase(1200, 4.5, -2, 4, "fancy_spoon", "fancySpoon").setTool(toolSpoon, 3, 3).addProperties(new PropertyHungerSpeed(1.2f));
	
	//Forkkind
	public static final Item fork = new MSUWeaponBase(225, 3.9, -2.2, 1, "fork").setTool(toolFork, 0, 2).addProperties(new PropertyHungerSpeed(1.2f));
	public static final Item skaiaFork = new MSUWeaponBase(1080, 18.3, -2.42, 10, "skaiaFork").setTool(toolFork, 3, 6).addProperties(new PropertyTipperDamage(0.6f, 1.3f, 0.8f)).addProperties(new PropertyHungerSpeed(1.25f));
	public static final Item crockerFork = new MSUWeaponBase(600, 11.5, -2, 6, "crocker_fork", "crockerFork").setTool(toolFork, 4, 8).addProperties(new PropertyTipperDamage(0.6f, 1.3f, 0.8f), new PropertyHungerSpeed(1.2f)).setCreativeTab(null);
	public static final Item quartzFork = new MSUWeaponBase(405, 9.1, -2, 4, "quartz_fork", "quartzFork").setTool(toolFork, 3, 3).addProperties(new PropertyTipperDamage(0.6f, 1.3f, 0.8f), new PropertyHungerSpeed(1.2f));

	//Spoon|Forkkind
	public static final Item spork = new MSUWeaponBase(525, 7.1, -2.2, 8, "spork").setTool(toolSpork, 2, 4).addProperties(new PropertyHungerSpeed(1.2f));
	public static final Item goldenSpork = new MSUWeaponBase(788, 9.5, -1.98, 8, "goldenSpork").setTool(toolSpork, 1, 8).addProperties(new PropertyHungerSpeed(1.3f));

	//Axekind
	public static final Item copseCrusher = new MSUWeaponBase(500, 16, -2.85, 2, "copseCrusher").setTool(toolAxe, 4, 4).addProperties(new PropertyFarmine(100, 64));
	public static final Item battleaxe = new MSUWeaponBase(600, 32, -3.15, 5, "battleaxe").setTool(toolAxe, 3, 2);
	public static final Item batleacks = new MSUWeaponBase(750, 24, -3, 1, "batleacks").setTool(toolAxe, 2, 2).addProperties(new PropertySlippery());
	public static final Item blacksmithBane = new MSUWeaponBase(750, 26.4, -3.03, 8, "blacksmithBane").setTool(toolAxe, 3, 3);
	public static final Item scraxe = new MSUWeaponBase(675, 24, -3, 7, "scraxe").setTool(toolAxe, 3, 5).addProperties(new PropertySoundOnHit(SoundEvents.BLOCK_NOTE_GUITAR, PITCH_NOTE, ((stack, target, player) -> 3f)), new PropertySoundOnClick(new SoundEvent[]{SoundEvents.BLOCK_NOTE_GUITAR}, PITCH_NOTE, ((stack, target, player) -> 3f), false));
	public static final Item hephaestusLumber = new MSUWeaponBase(750, 28, -3, 9, "hephaestusLumber").setTool(toolAxe, 4, 3).addProperties(new PropertyAutoSmelt(), new PropertyFarmine(100, 32), new PropertyFire(20, 1, true), new PropertyFire(2, 1, false));
	public static final Item rubyCroak = new MSUWeaponBase(1000, 32, -2.7, 10, "rubyCroak").setTool(toolAxe, 3, 3).addProperties(new PropertyFire(10, 0.8f, false));
	public static final Item battleaxeOfZillywahoo = new MSUWeaponBase(3000, 86.4, -3, 40, "battleaxe_of_zillywahoo", "battleaxeOfZillywahoo").setTool(toolAxe, 5, 10);

	//Misc.
	public static final Item diamondSickle = new MSUWeaponBase(1650, 5.5, -2.4, 32, "diamond_sickle", "diamondSickle").setTool(toolSickle, 3, 4).addProperties(new PropertySweep(3), new PropertyFarmine(2, 500));
	public static final Item gravediggerShovel = new MSUWeaponBase(720, 16, -3, 8, "gravedigger_shovel", "gravediggerShovel").setTool(toolShovel, 3, 4).addProperties(new PropertyMobTypeDamage(EnumCreatureAttribute.UNDEAD, 3));
	public static final Item battlesporkOfZillywut = new MSUWeaponBase(3150, 37.9, -2, 40, "battlespork_of_zillywut", "battlesporkOfZillywut").setTool(toolSpork, 5, 10).addProperties(new PropertyHungerSpeed(1.2f));
	public static final Item battlepickOfZillydew = new MSUWeaponBase(780, 16, -2.8, 40, "battlepick_of_zillydew", "battlepickOfZillydew").setTool(toolPickaxe, 5, 10);
	public static final Item rolledUpPaper = new MSUWeaponBase(200, 3, 0, 1, "rolled_up_paper", "rolledUpPaper");
	public static final Item yesterdaysNews = new MSUWeaponBase(450, 7, 0, 1, "yesterdays_news", "yesterdaysNews").addProperties(new PropertyPotion(new PotionEffect(MobEffects.MINING_FATIGUE, 200, 1), false, 0.5f), new PropertyPotion(new PotionEffect(MobEffects.SLOWNESS, 200, 1), false, 0.5f), new PropertyFire(3, 0.7f, true));

	//Armor
	public static final MSUArmorBase diverHelmet = new ItemDiverHelmet(materialDiverHelmet, 0, EntityEquipmentSlot.HEAD, "diverHelmet", "diver_helmet");
	public static final MSUArmorBase spikedHelmet = new MSUArmorBase(materialSpikedHelmet,0,EntityEquipmentSlot.HEAD,"spikedDiverHelmet", "spiked_diver_helmet");
	public static final MSUArmorBase cruxtruderHat = new MSUArmorBase(materialMetal,0,EntityEquipmentSlot.HEAD,"cruxtruderHelmet", "cruxtruder_helmet");
	public static final MSUArmorBase frogHat = new MSUArmorBase(materialCloth,0,EntityEquipmentSlot.HEAD,"frogHat", "frog_hat");
	public static final MSUArmorBase wizardHat = new MSUArmorBase(40, materialCloth,0,EntityEquipmentSlot.HEAD,"wizardHat", "wizard_hat");
	public static final MSUArmorBase archmageHat = new MSUArmorBase(500, materialCloth,0,EntityEquipmentSlot.HEAD,"archmageHat", "archmage_hat");
	public static final MSUArmorBase cozySweater = new ItemWitherproofArmor(60, materialCloth, 0, EntityEquipmentSlot.CHEST, "cozySweater", "cozy_sweater");
	public static final MSUArmorBase scarf = new ItemScarf(materialCloth, 0, EntityEquipmentSlot.HEAD, "scarf", "scarf");
	public static final MSUArmorBase rubberBoots = new MSUArmorBase(materialRubber,0,EntityEquipmentSlot.FEET,"rubberBoots", "rubber_boots");
	public static final MSUArmorBase bunnySlippers = new MSUArmorBase(materialCloth,0,EntityEquipmentSlot.FEET,"bunnySlippers", "bunny_slippers");
	public static final MSUArmorBase moonShoes = new ItemPogoBoots(1.1f, materialRubber, 0, "moonShoes", "moon_shoes");
	public static final MSUArmorBase sunShoes = new ItemPogoBoots(1.6f, materialSunShoes,0,"solarShoes", "solar_shoes").setSolar();
	public static final MSUArmorBase rocketBoots = new MSUArmorBase(850, materialSunShoes,0,EntityEquipmentSlot.FEET,"rocketBoots", "rocket_boots").setRepairMaterial("gunpowder");
	public static final MSUArmorBase windWalkers = new MSUArmorBase(283, materialWindWalkers,0,EntityEquipmentSlot.FEET,"windWalkers", "wind_walkers");
	public static final MSUArmorBase airJordans = new MSUArmorBase(230, materialRubber,0,EntityEquipmentSlot.FEET,"airJordans", "air_jordans");
	public static final MSUArmorBase cobaltJordans = new MSUArmorBase(480, materialCobalt,0,EntityEquipmentSlot.FEET,"airJordansCobalt", "air_jordans_cobalt");

	//Support
	public static Item splatcraftCruxiteFilter = new MSUItemBase("cruxite_filter", "cruxiteFilter"){
		@Override
		public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		}
	};

	@SubscribeEvent
	public static void onMissingRegistries(RegistryEvent.MissingMappings<Item> event)
	{
		ArrayList<String> integratedMods = new ArrayList<>();
		integratedMods.add("minestuck");
		integratedMods.add("minestuckuniverse");
		integratedMods.add("fetchmodiplus");
		integratedMods.add("minestuckgodtier");

		for(RegistryEvent.MissingMappings.Mapping<Item> entry : ImmutableList.copyOf(event.getAllMappings().stream().filter(e -> integratedMods.contains(e.key.getResourceDomain())).collect(Collectors.toList())))
		{
			if(entry.key.getResourcePath().equals("crocker_spork"))
				entry.remap(crockerSpoon);
			else
			{
				System.out.println(entry.key + " " + Item.REGISTRY.getObject(new ResourceLocation(Minestuck.MODID, entry.key.getResourcePath())));
				if(Item.REGISTRY.getObject(new ResourceLocation(Minestuck.MODID, entry.key.getResourcePath())) != null)
					entry.remap(Item.REGISTRY.getObject(new ResourceLocation(Minestuck.MODID, entry.key.getResourcePath())));
			}
		}
	}

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event)
	{
		armorPrismarine.repairMaterial = new ItemStack(Items.PRISMARINE_SHARD);
		((ItemMinestuckSeedFood) strawberryChunk).setPlant(strawberryStem.getDefaultState());

		IForgeRegistry<Item> registry = event.getRegistry();
		registerItemBlock(registry, new ItemMultiTexture(chessTile, chessTile, new String[]{"black", "white", "darkgrey", "lightgrey"}));
		registerItemBlock(registry, new ItemBlock(skaiaPortal));
		
		registerItemBlock(registry, new ItemMultiTexture(oreCruxite, oreCruxite, new String[0])
		{
			@Override
			public String getUnlocalizedName(ItemStack stack)
			{
				return block.getUnlocalizedName();
			}
		});
		registerItemBlock(registry, new ItemMultiTexture(oreUranium, oreUranium, new String[0])
		{
			@Override
			public String getUnlocalizedName(ItemStack stack)
			{
				return block.getUnlocalizedName();
			}
		});
		registerItemBlock(registry, new ItemBlock(coalOreNetherrack));
		registerItemBlock(registry, new ItemBlock(ironOreEndStone));
		registerItemBlock(registry, new ItemBlock(ironOreSandstone));
		registerItemBlock(registry, new ItemBlock(ironOreSandstoneRed));
		registerItemBlock(registry, new ItemBlock(goldOreSandstone));
		registerItemBlock(registry, new ItemBlock(goldOreSandstoneRed));
		registerItemBlock(registry, new ItemBlock(redstoneOreEndStone));
		registerItemBlock(registry, new ItemBlock(quartzOreStone));
		registerItemBlock(registry, new ItemBlock(coalOrePinkStone));
		registerItemBlock(registry, new ItemBlock(goldOrePinkStone));
		registerItemBlock(registry, new ItemBlock(diamondOrePinkStone));
		registerItemBlock(registry, new ItemBlock(lapisOrePinkStone));

		registerItemBlock(registry, new ItemBlockCraftingTab(cruxiteBlock, CreativeTabs.BUILDING_BLOCKS));
		registerItemBlock(registry, new ItemBlockCraftingTab(uraniumBlock, CreativeTabs.BUILDING_BLOCKS));
		registerItemBlock(registry, new ItemBlock(genericObject));
		registerItemBlock(registry, new ItemSburbMachine(sburbMachine));
		registerItemBlock(registry, new ItemMultiTexture(crockerMachine, crockerMachine,
				(ItemStack input) -> BlockCrockerMachine.MachineType.values()[input.getItemDamage() % BlockCrockerMachine.MachineType.values().length].getUnlocalizedName()));
		registerItemBlock(registry, new ItemBlock(blockComputerOff));
		registerItemBlock(registry, new ItemTransportalizer(transportalizer));
		
		registerItemBlock(registry, new ItemPunchDesignix(punchDesignix));
		registerItemBlock(registry, new ItemTotemLathe(totemlathe[0]));
		registerItemBlock(registry, new ItemAlchemiter(alchemiter[0]));
		registerItemBlock(registry, new ItemCruxtruder(cruxtruder));
		registerItemBlock(registry, new ItemBlock(cruxtruderLid));
		registerItemBlock(registry, cruxiteDowel);
		/*registerItemBlock(registry, new ItemBlock(holopad));
		registerItemBlock(registry, new ItemJumperBlock(jumperBlockExtension[0]));*/
		registerItemBlock(registry, new ItemBlock(blender));
		registerItemBlock(registry, new ItemBlock(chessboard));
		registerItemBlock(registry, new ItemBlock(frogStatueReplica));

		registerItemBlock(registry, new ItemBlockLayered(layeredSand));
		registerItemBlock(registry, new ItemMultiTexture(coloredDirt, coloredDirt,
				(ItemStack input) -> BlockColoredDirt.BlockType.values()[input.getItemDamage() % BlockColoredDirt.BlockType.values().length].getName()));
		registerItemBlock(registry, new ItemBlock(petrifiedLog));
		registerItemBlock(registry, new ItemBlock(petrifiedPoppy));
		registerItemBlock(registry, new ItemBlock(petrifiedGrass));
		registerItemBlock(registry, new ItemBlock(bloomingCactus));
		registerItemBlock(registry, new ItemBlock(desertBush));
		registerItemBlock(registry, new ItemBlock(glowingMushroom));
		registerItemBlock(registry, new ItemBlock(glowingLog));
		registerItemBlock(registry, new ItemBlockCraftingTab(glowingPlanks, CreativeTabs.BUILDING_BLOCKS));
		registerItemBlock(registry, new ItemBlock(frostPlanks));
		registerItemBlock(registry, new ItemMultiTexture(stone, stone,
				(ItemStack input) -> BlockMinestuckStone.BlockType.getFromMeta(input.getMetadata()).getUnlocalizedName()));
		registerItemBlock(registry, new ItemBlock(glowyGoop));
		registerItemBlock(registry, new ItemBlock(coagulatedBlood));
		registerItemBlock(registry, new ItemBlockCraftingTab(coarseStoneStairs, CreativeTabs.BUILDING_BLOCKS));
		registerItemBlock(registry, new ItemBlockCraftingTab(shadeBrickStairs, CreativeTabs.BUILDING_BLOCKS));
		registerItemBlock(registry, new ItemBlockCraftingTab(frostBrickStairs, CreativeTabs.BUILDING_BLOCKS));
		registerItemBlock(registry, new ItemBlockCraftingTab(castIronStairs, CreativeTabs.BUILDING_BLOCKS));
		registerItemBlock(registry, new ItemBlockCraftingTab(myceliumBrickStairs, CreativeTabs.BUILDING_BLOCKS));
		

		registerItemBlock(registry, new ItemBlock(vein));
		registerItemBlock(registry, new ItemBlock(veinCorner));
		registerItemBlock(registry, new ItemBlock(veinCornerInverted));

		registerItemBlock(registry, new ItemMultiTexture(log, log,
				(ItemStack input) -> BlockMinestuckLog1.BlockType.values()[input.getItemDamage() % BlockMinestuckLog1.BlockType.values().length].getUnlocalizedName()));
		registerItemBlock(registry, new ItemMultiTexture(leaves1, leaves1,
				(ItemStack input) -> BlockMinestuckLeaves1.BlockType.values()[input.getItemDamage() % BlockMinestuckLeaves1.BlockType.values().length].getUnlocalizedName()));
		registerItemBlock(registry, new ItemMultiTexture(planks, planks,
				(ItemStack input) -> BlockMinestuckPlanks.BlockType.values()[Math.min(input.getItemDamage(), BlockMinestuckPlanks.BlockType.values().length - 1)].getUnlocalizedName()));
				//Temporarily changed to this mechanism for handling larger inputs so that any leftover blocks at value 15 will convert properly.
		
		registerItemBlock(registry, new ItemMultiTexture(aspectSapling, aspectSapling,
				(ItemStack input) -> BlockAspectSapling.BlockType.values()[input.getItemDamage() % BlockAspectSapling.BlockType.values().length].getUnlocalizedName()));
		
		registerItemBlock(registry, new ItemBlock(rainbowSapling));
		
		registerItemBlock(registry, new ItemMultiTexture(aspectLog1, aspectLog1,
				(ItemStack input) -> BlockAspectLog.BlockType.values()[input.getItemDamage() % BlockAspectLog.BlockType.values().length].getUnlocalizedName()));
		registerItemBlock(registry, new ItemMultiTexture(aspectLog2, aspectLog2,
				(ItemStack input) -> BlockAspectLog2.BlockType.values()[input.getItemDamage() % BlockAspectLog2.BlockType.values().length].getUnlocalizedName()));
		registerItemBlock(registry, new ItemMultiTexture(aspectLog3, aspectLog3,
				(ItemStack input) -> BlockAspectLog3.BlockType.values()[input.getItemDamage() % BlockAspectLog3.BlockType.values().length].getUnlocalizedName()));
		
		registerItemBlock(registry, new ItemMultiTexture(blockLaptopOff, blockLaptopOff,
				(ItemStack input) -> BlockVanityLaptopOff.BlockType.values()[input.getItemDamage() % BlockVanityLaptopOff.BlockType.values().length].getUnlocalizedName()));
		
		registerItemBlock(registry, new ItemBlock(woodenCactus));
		registerItemBlock(registry, new ItemBlock(sugarCube));
		registerItemBlock(registry, new ItemBlock(appleCake)).setMaxStackSize(1);
		registerItemBlock(registry, new ItemBlock(blueCake)).setMaxStackSize(1);
		registerItemBlock(registry, new ItemBlock(coldCake)).setMaxStackSize(1);
		registerItemBlock(registry, new ItemBlock(redCake)).setMaxStackSize(1);
		registerItemBlock(registry, new ItemBlock(hotCake)).setMaxStackSize(1);
		registerItemBlock(registry, new ItemBlock(reverseCake)).setMaxStackSize(1);
		registerItemBlock(registry, new ItemBlock(fuchsiaCake)).setMaxStackSize(1);
		
		registerItemBlock(registry, new ItemBlock(floweryMossBrick));
		registerItemBlock(registry, new ItemBlock(floweryMossStone));
		registerItemBlock(registry, new ItemBlock(treatedPlanks));
		registerItemBlock(registry, new ItemBlock(coarseEndStone));
		registerItemBlock(registry, new ItemBlock(endLog));
		registerItemBlock(registry, new ItemBlock(endLeaves));
		registerItemBlock(registry, new ItemBlock(endPlanks));
		registerItemBlock(registry, new ItemBlock(endSapling));
		registerItemBlock(registry, new ItemBlock(endGrass));
		registerItemBlock(registry, new ItemBlock(strawberry));
		registerItemBlock(registry, new ItemBlock(deadLog));
		registerItemBlock(registry, new ItemBlock(deadPlanks));
		registerItemBlock(registry, new ItemBlock(chalk));
		registerItemBlock(registry, new ItemBlock(chalkBricks));
		registerItemBlock(registry, new ItemBlock(chalkChisel));
		registerItemBlock(registry, new ItemBlock(chalkPolish));
		registerItemBlock(registry, new ItemBlock(pinkStoneSmooth));
		registerItemBlock(registry, new ItemBlock(pinkStoneBricks));
		registerItemBlock(registry, new ItemBlock(pinkStoneChisel));
		registerItemBlock(registry, new ItemBlock(pinkStoneCracked));
		registerItemBlock(registry, new ItemBlock(pinkStoneMossy));
		registerItemBlock(registry, new ItemBlock(pinkStonePolish));
		registerItemBlock(registry, new ItemBlock(denseCloud) {
			@Override
			public int getMetadata(int damage)
			{
				return damage;
			}
		});

		for(EnumSlabStairMaterial mat : EnumSlabStairMaterial.values())
		{
			registerItemBlock(registry, new ItemBlock(mat.getStair()));
			registerItemBlock(registry, mat.getSlabItem());
		}

		registerItemBlock(registry, new ItemBlock(primedTnt));
		registerItemBlock(registry, new ItemBlock(unstableTnt));
		registerItemBlock(registry, new ItemBlock(instantTnt));
		registerItemBlock(registry, new ItemBlock(woodenExplosiveButton));
		registerItemBlock(registry, new ItemBlock(stoneExplosiveButton));
		
		registerItemBlock(registry, new ItemBlock(uraniumCooker));

		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(MinestuckItems.eightBall, new BehaviorProjectileDispense()
		{
			/**
			 * Return the projectile entity spawned by this dispense behavior.
			 */
			protected IProjectile getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn)
			{
				return new EntityEightBall(worldIn, position.getX(), position.getY(), position.getZ(), getStoredItem(stackIn));
			}
		});

		minestuckBucket.addBlock(blockOil.getDefaultState());
		minestuckBucket.addBlock(blockBlood.getDefaultState());
		minestuckBucket.addBlock(blockBrainJuice.getDefaultState());
		minestuckBucket.addBlock(blockWatercolors.getDefaultState());
		minestuckBucket.addBlock(blockEnder.getDefaultState());
		minestuckBucket.addBlock(blockLightWater.getDefaultState());

		/*for(Block block : liquidGrists)
		{
			minestuckBucket.addBlock(block.getDefaultState());
		}*/

		minestuckBucket.addBlock(blockOil.getDefaultState());
		minestuckBucket.addBlock(blockBlood.getDefaultState());
		minestuckBucket.addBlock(blockBrainJuice.getDefaultState());
		minestuckBucket.addBlock(blockWatercolors.getDefaultState());
		minestuckBucket.addBlock(blockEnder.getDefaultState());
		minestuckBucket.addBlock(blockLightWater.getDefaultState());

		if(Minestuck.isSplatcraftLodaded)
			splatcraftCruxiteFilter = new ItemFilter("cruxiteFilter", "cruxite_filter", false).setCreativeTab(TabsMinestuck.minestuck);

		for (IRegistryItem<Item> item : MSItemBase.items)
			item.register(registry);

		OreDictionary.registerOre("modus", stackModus);
		OreDictionary.registerOre("modus", queueModus);
		OreDictionary.registerOre("modus", queueStackModus);
		OreDictionary.registerOre("modus", treeModus);
		OreDictionary.registerOre("modus", hashmapModus);
		OreDictionary.registerOre("modus", setModus);
		OreDictionary.registerOre("modus", wildMagicModus);
		OreDictionary.registerOre("modus", weightModus);
		OreDictionary.registerOre("modus", bookModus);
		OreDictionary.registerOre("modus", capitalistModus);
		OreDictionary.registerOre("modus", modUs);
		OreDictionary.registerOre("modus", operandiModus);
		OreDictionary.registerOre("modus", onionModus);
		OreDictionary.registerOre("modus", slimeModus);
		OreDictionary.registerOre("modus", popTartModus);
		OreDictionary.registerOre("modus", deckModus);
		OreDictionary.registerOre("modus", hueModus);
		OreDictionary.registerOre("modus", hueStackModus);
		OreDictionary.registerOre("modus", chatModus);
		OreDictionary.registerOre("modus", cycloneModus);
		OreDictionary.registerOre("modus", energyModus);
		OreDictionary.registerOre("modus", scratchAndSniffModus);
		OreDictionary.registerOre("modus", eightBallModus);
		OreDictionary.registerOre("modus", chasityModus);
		OreDictionary.registerOre("modus", jujuModus);
		OreDictionary.registerOre("modus", alcheModus);
		OreDictionary.registerOre("modus", arrayModus);
		OreDictionary.registerOre("modus", monsterModus);
		OreDictionary.registerOre("modus", walletModus);
		OreDictionary.registerOre("modus", crystalBallModus);
		OreDictionary.registerOre("modus", hashchatModus);
		OreDictionary.registerOre("modus", sacrificeModus);

		OreDictionary.registerOre("record", MinestuckItems.recordDanceStab);
		OreDictionary.registerOre("record", MinestuckItems.recordEmissaryOfDance);
		OreDictionary.registerOre("record", MinestuckItems.recordRetroBattle);

		toolUranium.setRepairItem(new ItemStack(rawUranium));
		ItemWeapon.addToolMaterial("pickaxe", Arrays.asList(Material.IRON, Material.ANVIL, Material.ROCK));
		ItemWeapon.addToolMaterial("axe", Arrays.asList(Material.WOOD, Material.PLANTS, Material.VINE));
		ItemWeapon.addToolMaterial("shovel", Arrays.asList(Material.SNOW, Material.CRAFTED_SNOW, Material.CLAY, Material.GRASS, Material.GROUND, Material.SAND));
		ItemWeapon.addToolMaterial("sword", Arrays.asList(Material.WEB));
		ItemWeapon.addToolMaterial("sickle", Arrays.asList(Material.WEB, Material.LEAVES, Material.PLANTS, Material.VINE));

		//Blocks
		registerItemBlocks(registry);
	}

	private static Item registerItemBlock(IForgeRegistry<Item> registry, ItemBlock item)
	{
		registry.register(item.setRegistryName(item.getBlock().getRegistryName()));
		return item;
	}

	public static final void registerItemBlocks(IForgeRegistry<Item> registry)
	{
		for(Block block : itemBlocks)
		{
			ItemBlock item = (block instanceof BlockCustomTransportalizer || block instanceof BlockTransportalizer)
							 ? new ItemTransportalizer(block) : new ItemBlock(block);
			item.setRegistryName(item.getBlock().getRegistryName());
		}
	}

	public static final void setPostInitVariables()
	{
		overgrowthTransforms.put(Blocks.DIRT, 0, Blocks.GRASS, 0);
		overgrowthTransforms.put(Blocks.COBBLESTONE, Blocks.MOSSY_COBBLESTONE);
		overgrowthTransforms.put(Blocks.STONEBRICK, 0, Blocks.STONEBRICK, 1);
		overgrowthTransforms.put(Blocks.MONSTER_EGG, 2, Blocks.MONSTER_EGG, 3);
		overgrowthTransforms.put(Blocks.COBBLESTONE_WALL, 0, Blocks.COBBLESTONE_WALL, 1);
		overgrowthTransforms.put(Blocks.END_STONE, MinestuckBlocks.endGrass);
		overgrowthTransforms.put(MinestuckBlocks.pinkStoneBricks, MinestuckBlocks.pinkStoneMossy);
		overgrowthTransforms.put(Blocks.LOG, 0, MinestuckBlocks.log, 4);
		overgrowthTransforms. put(Blocks.LOG, 4, MinestuckBlocks.log, 0);
		overgrowthTransforms.put(Blocks.LOG, 8, MinestuckBlocks.log, 8);
		overgrowthTransforms.put(Blocks.LOG, 12, MinestuckBlocks.log, 12);


		if(Minestuck.isMSGTLoaded && fearNoAnvil instanceof MSUWeaponBase)
			((MSUWeaponBase)fearNoAnvil).addProperties(new PropertyPotion(true, 0.1f, new PotionEffect(Potion.REGISTRY.getObject(new ResourceLocation("minestuckgodtier", "time_stop")), 20, 0)));

		wisdomsPierce.addProperties(new PropertyInnocuousDouble(wisdomsHookshot, true, false, false));
		wisdomsHookshot.addProperties(new PropertyInnocuousDouble(wisdomsPierce, true, false, false));
		((MSUWeaponBase)clarityWard).addProperties(new PropertyInnocuousDouble(bladesOfTheWarrior, true, false, true));
		((MSUWeaponBase)bladesOfTheWarrior).addProperties(new PropertyInnocuousDouble(clarityWard, true, true, false));
		((MSUWeaponBase)dragonBlades).addProperties(new PropertyInnocuousDouble(Item.REGISTRY.getObject(new ResourceLocation(Minestuck.MODID, "dragon_cane")), false, false, true));
		((MSUWeaponBase)crockerFork).addProperties(new PropertyInnocuousDouble(Item.REGISTRY.getObject(new ResourceLocation(Minestuck.MODID, "crocker_spork")), false, false, false));

		if(Minestuck.isBotaniaLoaded)
		{
			overgrowthTransforms.put(ModBlocks.livingrock, 1, ModBlocks.livingrock, 2);
			overgrowthTransforms.put(ModBlocks.livingwood, 1, ModBlocks.livingwood, 2);
			overgrowthTransforms.put(ModBlocks.dreamwood, 1, ModBlocks.dreamwood, 2);
		}

		suitarang.addPropertyOverride(new ResourceLocation(Minestuck.MODID, "variant"),
									  ((stack, worldIn, entityIn) -> ((PropertyVariableItem) suitarang.getProperty(PropertyVariableItem.class, stack)).getPropertyOverride().apply(stack, worldIn, entityIn)));
		valorsEdge.addPropertyOverride(new ResourceLocation(Minestuck.MODID, "awakened"),
									   ((stack, worldIn, entityIn) -> ((PropertyLowHealthBoost)((MSUWeaponBase)valorsEdge).getProperty(PropertyLowHealthBoost.class, stack)).getPropertyOverride().apply(stack, worldIn, entityIn)));
		barrelsWarhammer.addPropertyOverride(new ResourceLocation(Minestuck.MODID, "awakened"),
											 ((stack, worldIn, entityIn) -> ((PropertyLowHealthBoost)((MSUWeaponBase)barrelsWarhammer).getProperty(PropertyLowHealthBoost.class, stack)).getPropertyOverride().apply(stack, worldIn, entityIn)));
		wisdomsPierce.addPropertyOverride(new ResourceLocation(Minestuck.MODID, "awakened"),
										  ((stack, worldIn, entityIn) -> ((PropertyLowHealthBoost) wisdomsPierce.getProperty(PropertyLowHealthBoost.class, stack)).getPropertyOverride().apply(stack, worldIn, entityIn)));
		wisdomsHookshot.addPropertyOverride(new ResourceLocation(Minestuck.MODID, "awakened"),
											((stack, worldIn, entityIn) -> ((PropertyLowHealthBoost) wisdomsHookshot.getProperty(PropertyLowHealthBoost.class, stack)).getPropertyOverride().apply(stack, worldIn, entityIn)));
		dragonBlades.addPropertyOverride(new ResourceLocation(Minestuck.MODID, "offhand"), ((stack, worldIn, entityIn) -> entityIn != null && entityIn.getHeldItemOffhand().equals(stack) ? 1 : 0));
		bladesOfTheWarrior.addPropertyOverride(new ResourceLocation(Minestuck.MODID, "left"), ((stack, worldIn, entityIn) -> entityIn != null &&
																																	 entityIn.getHeldItem(entityIn.getPrimaryHand() == EnumHandSide.LEFT ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND).equals(stack) ? 1 : 0));

		energyBow.setArrowTexture("energy_arrow");
		sweetBow.setCustomArrowTexture();
		icicleBow.setCustomArrowTexture();
		shiverburnWing.setCustomArrowTexture();
		magneticHookshot.setCustomArrowTexture();
		tempestBow.setCustomArrowTexture();
		wormholePiercer.setCustomArrowTexture();
		telegravitationalWarper.setArrowTexture("gravity_arrow");
		gildedGuidance.setCustomArrowTexture();
		bowOfLight.setArrowTexture("light_arrow");
		wisdomsHookshot.setArrowTexture("energy_arrow");
		theChancemaker.setArrowTexture("the_chancemaker");

		((IBeamStats)needlewands).setCustomBeamTexture();
		((IBeamStats)oglogothThorn).setBeamTexture("eldrich_beam");
		((IBeamStats) gasterBlaster).setBeamTexture("clear_beam");
		((IBeamStats) litGlitterBeamTransistor).setBeamTexture("clear_beam");
		((IBeamStats) laserPointer).setBeamTexture("laser_beam");
	}

	@SideOnly(Side.CLIENT)
	public static final void setClientsideVariables()
	{
		diverHelmet.setArmorModel(new ModelDiverHelmet());
		spikedHelmet.setArmorModel(new ModelSpikedHelmet());
		cruxtruderHat.setArmorModel(new ModelCruxtruderHat());
		frogHat.setArmorModel(new ModelFrogHat());
		wizardHat.setArmorModel(new ModelWizardHat());
		archmageHat.setArmorModel(new ModelArchmageHat());
		scarf.setArmorModel(new ModelScarf());
		crumplyHat.setArmorModel(new ModelCrumplyHat());

		for(ItemBeamBlade blade : dyedBeamBlade)
			registerItemCustomRender(blade, new MSUModelManager.DualWeaponDefinition("dyed_battery_beam_blade", "dyed_battery_beam_blade_off"));
		registerItemCustomRender(batteryBeamBlade, new MSUModelManager.DualWeaponDefinition("battery_beam_blade", "battery_beam_blade_off"));
		registerItemCustomRender(yarnBall, new MSUModelManager.DyedItemDefinition("yarn_ball"));
		registerItemCustomRender(scarf, new MSUModelManager.DyedItemDefinition("scarf"));
		registerItemCustomRender(actionClaws, new MSUModelManager.DualWeaponDefinition("action_claws_drawn", "action_claws_sheathed"));
		registerItemCustomRender(candyCornClaws, new MSUModelManager.DualWeaponDefinition("candy_corn_claws_drawn", "candy_corn_claws_sheathed"));
		registerItemCustomRender(sneakyDaggers, new MSUModelManager.DualWeaponDefinition("sneaky_daggers_drawn", "sneaky_daggers_sheathed"));
		registerItemCustomRender(blizzardCutters, new MSUModelManager.DualWeaponDefinition("blizzard_cutters_drawn", "blizzard_cutters_sheathed"));
		registerItemCustomRender(katarsOfZillywhomst, new MSUModelManager.DualWeaponDefinition("katars_of_zillywhomst_drawn", "katars_of_zillywhomst_sheathed"));

		RenderThrowable.IRenderProperties THROW_STAR_ROTATION = ((entity, partialTicks) ->
		{
			GlStateManager.rotate(90, 1, 0, 0);
			GlStateManager.rotate((entity.ticksExisted+partialTicks) * -(float)Math.PI*10f, 0, 0, 1);
		});

		throwingStar.setRenderProperties(THROW_STAR_ROTATION);
		goldenStar.setRenderProperties(THROW_STAR_ROTATION);
		psionicStar.setRenderProperties(THROW_STAR_ROTATION);
		suitarang.setRenderProperties(THROW_STAR_ROTATION);
		boomerang.setRenderProperties(THROW_STAR_ROTATION);
		markedBoomerang.setRenderProperties(THROW_STAR_ROTATION);
		redHotRang.setRenderProperties(THROW_STAR_ROTATION);
		tornadoGlaive.setRenderProperties(THROW_STAR_ROTATION);
	}

	@SideOnly(Side.CLIENT)
	private static Item registerItemCustomRender(Item item, MSUModelManager.CustomItemMeshDefinition customMesh)
	{
		MSUModelManager.customItemModels.add(new Pair<>(item, customMesh));
		return item;
	}
}
