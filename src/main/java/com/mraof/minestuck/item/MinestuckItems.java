package com.mraof.minestuck.item;

import com.google.common.collect.ImmutableList;
import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.entity.EntityEightBall;
import com.mraof.minestuck.item.operandi.*;
import com.mraof.minestuck.util.IRegistryItem;
import com.mraof.minestuck.util.MinestuckSounds;
import com.mraof.minestuck.block.*;
import com.mraof.minestuck.entity.item.EntityCrewPoster;
import com.mraof.minestuck.entity.item.EntitySbahjPoster;
import com.mraof.minestuck.entity.item.EntityShopPoster;
import com.mraof.minestuck.inventory.captchalouge.PopTartModus;
import com.mraof.minestuck.item.block.*;
import com.mraof.minestuck.item.weapon.*;
import com.mraof.minestuck.util.MinestuckSoundHandler;

import net.minecraft.block.BlockDispenser;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.IProjectile;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
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

public class MinestuckItems
{
	/**
	 * Use TabMinestuck.instance instead
	 */
	@Deprecated
	public static final CreativeTabs tabMinestuck = TabMinestuck.instance;

	public static final Item.ToolMaterial toolEmerald = EnumHelper.addToolMaterial("EMERALD", 3, 1220, 12.0F, 4.0F, 12).setRepairItem(new ItemStack(Items.EMERALD));
	public static final ItemArmor.ArmorMaterial armorPrismarine = EnumHelper.addArmorMaterial("PRISMARINE", "minestuck:prismarine", 20, new int[]{3, 7, 6, 2}, 15, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F);
	;
	public static final Item.ToolMaterial toolUranium = EnumHelper.addToolMaterial("URANIUM", 3, 1220, 12.0F, 6.0F, 15);
	//hammers
	public static final Item clawHammer = new ItemWeapon(131, 4.0D, -2.4D, 10, "clawHammer" ).setTool("pickaxe", 0, 1.0F);
	public static final Item sledgeHammer = new ItemWeapon(250, 6.0D, -2.8D, 8, "sledgeHammer").setTool("pickaxe", 2, 4.0F);
	public static final Item blacksmithHammer = new ItemWeapon(450, 7.0D, -2.8D, 10, "blacksmithHammer").setTool("pickaxe", 2, 3.5F);
	public static final Item pogoHammer = new ItemPogoWeapon(400, 7.0D, -2.8D, 8, "pogoHammer", 0.7).setTool("pickaxe", 1, 2.0F);
	public static final Item telescopicSassacrusher = new ItemWeapon(1024, 9.0D, -2.9D, 15, "telescopicSassacrusher").setTool("pickaxe", 2, 5.0F);
	public static final Item regiHammer = new ItemWeapon(812, 6.0D, -2.4D, 5, "regiHammer");
	public static final Item fearNoAnvil = new ItemPotionWeapon(2048, 10.0D, -2.8D, 12, "fearNoAnvil", new PotionEffect(MobEffects.SLOWNESS, 100, 3), true).setTool("pickaxe", 3, 7.0F);
	public static final Item meltMasher = new ItemFireWeapon(1413, 10.5, -2.8, 20, "meltMasher", 25).setTool("pickaxe", 4, 12.0F);
	public static final Item qEHammerAxe = new ItemPogoFarmine(6114, 11.0D, -2.8D, 40, "estrogenEmpoweredEverythingEradicator", Integer.MAX_VALUE, 200, 0.7).setTool("pickaxe", 3, 9.0F).setTool("shovel", 3, 9.0F).setTool("sickle", 3, 7.0F).setTool("axe", 3, 12.0F);
	public static final Item dDEHammerAxe = new ItemSbahjEEEE(6114, 11.01D, -2.8D, 40, "eeeeeeeeeeee", 0.2);
	public static final Item zillyhooHammer = new ItemWeapon(3000, 11.0D, -2.8D, 30, "zillyhooHammer", true).setTool("pickaxe", 4, 15.0F);

	public static final Item popamaticVrillyhoo = new ItemRandomWeapon(3000, 8.0D, -2.8D, 30, "popamaticVrillyhoo").setTool("pickaxe", 4, 15.0F);
	public static final Item scarletZillyhoo = new ItemFireWeapon(2000, 11.0D, -2.8D, 16, "scarletZillyhoo", 50).setTool("pickaxe", 3, 4.0F);
	public static final Item mwrthwl = new ItemWeapon(2000, 10.5D, -2.8D, 16, "mwrthwl").setTool("pickaxe", 3, 4.0F);
	//blades
	public static final Item sord = new ItemSord(59, 2, -2.4D, 5, "sord");
	public static final Item cactusCutlass = new ItemWeapon(104, 4, -2.4D, 10, "cactaceaeCutlass").setTool("sword", 0, 15.0F);	//The sword tool is only used against webs, hence the high efficiency.
	public static final Item steakSword = new ItemConsumableWeapon(250, 4, -2.4D, 5, "steakSword", 8, 1F);
	public static final Item beefSword = new ItemConsumableWeapon(175, 2, -2.4D, 5, "beefSword", 3, 0.8F, 75);
	public static final Item irradiatedSteakSword = new ItemConsumableWeapon(150, 2, -2.4D, 5, "irradiatedSteakSword", 4, 0.4F, 25).setPotionEffect(new PotionEffect(MobEffects.WITHER, 100, 1), 0.9F);
	public static final Item katana = new ItemWeapon(250, 5, -2.4D, 15, "katana").setTool("sword", 0, 15.0F);
	public static final Item unbreakableKatana = new ItemWeapon(2200, 7, -2.4D, 20, "unbreakableKatana").setTool("sword", 0, 15.0F);    //Not actually unbreakable
	public static final Item firePoker = new ItemFireWeapon(250, 6, -2.4D, 15, "firePoker", 30).setTool("sword", 0, 15.0F);
	public static final Item hotHandle = new ItemFireWeapon(350, 5, -2.4D, 15, "tooHotToHandle", 10).setTool("sword", 0, 15.0F);
	public static final Item caledscratch = new ItemWeapon(1561, 6, -2.4D, 30, "caledscratch").setTool("sword", 0, 15.0F);
	public static final Item caledfwlch = new ItemWeapon(1025, 6, -2.4D, 30, "caledfwlch").setTool("sword", 0, 15.0F);
	public static final Item royalDeringer = new ItemWeapon(1561, 7, -2.4D, 30, "royalDeringer").setTool("sword", 0, 15.0F);
	public static final Item claymore = new ItemWeapon(600, 7D, -2.6D, 15, "claymore").setTool("sword", 0, 15.0F);
	public static final Item zillywairCutlass = new ItemWeapon(2500, 8, -2.4D, 30, "cutlass_of_zillywair").setTool("sword", 0, 15.0F);
	public static final Item regisword = new ItemWeapon(812, 6, -2.4D, 10, "regisword").setTool("sword", 0, 15.0F);
	public static final Item scarletRibbitar = new ItemWeapon(2000, 7, -2.4D, 30, "scarletRibbitar").setTool("sword", 0, 15.0F);
	public static final Item doggMachete = new ItemWeapon(1000, 5, -2.4D, 30, "doggMachete").setTool("sword", 0, 15.0F);
	public static final Item cobaltSabre = new ItemFireWeapon(300, 7, -2.4D, 10, "cobaltSabre", 30).setTool("sword", 0, 15.0F);
	public static final Item quantumSabre = new ItemPotionWeapon(toolUranium, 600, 8, -2.4D, 5, "quantumSabre", new PotionEffect(MobEffects.WITHER, 100, 1)).setTool("sword", 0, 15.0F);
	public static final Item shatterBeacon = new ItemPotionWeapon(1850, 10, -2.4D, 35, "shatterbeacon", null, false).setRandomPotionEffect().setTool("sword", 0, 15.0f);
	//axes
	public static final Item batleacks = new ItemSord(64, 4, -3.5D, 5, "batleacks");
	public static final Item copseCrusher = new ItemFarmine(400, 6.0D, -3.0D, 20, "copseCrusher", Integer.MAX_VALUE, 20).setTool("axe", 2, 6.0F);
	public static final Item battleaxe = new ItemWeapon(600, 10D, -3.0D, 15, "battleaxe").setTool("axe", 2, 3.0F);
	public static final Item blacksmithBane = new ItemWeapon(413, 9.0D, -3.0D, 15, "blacksmithBane").setTool("axe", 2, 6.0F);
	public static final Item scraxe = new ItemWeapon(500, 10.0D, -3.0D, 20, "scraxe").setTool("axe", 2, 7.0F);
	public static final Item qPHammerAxe = new ItemPogoFarmine(800, 8.0D, -3.0D, 30, "pistonPoweredPogoAxehammer", Integer.MAX_VALUE, 50, 0.6).setTool("pickaxe", 1, 2.0F).setTool("axe", 2, 7.0F);
	public static final Item rubyCroak = new ItemWeapon(2000, 11.0D, -3.0D, 30, "rubyCroak").setTool("axe", 3, 8.0F);
	public static final Item hephaestusLumber = new ItemFireWeapon(3000, 11.0D, -3.0D, 30, "hephaestusLumberjack", 30).setTool("axe", 3, 9.0F);
	public static final Item qFHammerAxe = new ItemPogoFarmine(toolUranium, 2048, 11.0D, -3.0D, 0, "fissionFocusedFaultFeller", Integer.MAX_VALUE, 100, 0.7).setTool("pickaxe", 2, 5.0F).setTool("axe", 3, 9.0F);
	//Dice
	public static final Item dice = new ItemWeapon(51, 6, 3, 6, "dice");
	public static final Item fluoriteOctet = new ItemWeapon(67, 15, 6, 8, "fluoriteOctet");
	//misc weapons
	public static final Item catClaws = new ItemDualWeapon(500, 4.0D, 1.0D, -1.5D, -1.0D, 6, "catclaws");
	//sickles
	public static final Item sickle = new ItemWeapon(220, 4.0D, -2.4D, 8, "sickle").setTool("sickle", 0, 1.5F);
	public static final Item homesSmellYaLater = new ItemWeapon(400, 5.5D, -2.4D, 10, "homesSmellYaLater").setTool("sickle", 0, 3.0F);
	public static final Item fudgeSickle = new ItemConsumableWeapon(450, 5.5D, -2.4D, 10, "fudgesickle", 7, 0.6F).setTool("sickle", 0, 1.0F);
	public static final Item regiSickle = new ItemWeapon(812, 6.0D, -2.4D, 5, "regisickle").setTool("sickle", 0, 4.0F);
	public static final Item clawSickle = new ItemWeapon(2048, 7.0D, -2.4D, 15, "clawSickle").setTool("sickle", 0, 4.0F);
	public static final Item clawOfNrubyiglith = new ItemHorrorterrorWeapon(1600, 9.5D, -2.4D, 15, "clawOfNrubyiglith").setTool("sickle", 0, 4.0F);
	public static final Item candySickle = new ItemCandyWeapon(96, 6.0D, -2.4D, 15, "candySickle").setTool("sickle", 0, 2.5F);
	//clubs
	public static final Item deuceClub = new ItemWeapon(1024, 2.5D, -2.2D, 15, "deuceClub");
	public static final Item nightClub = new ItemWeapon(600, 4.0D, -2.2D, 20, "nightclub");
	public static final Item pogoClub = new ItemPogoWeapon(600, 3.5D, -2.2D, 15, "pogoClub", 0.5);
	public static final Item metalBat = new ItemWeapon(750, 5.0D, -2.2D, 5, "metalBat");
	public static final Item spikedClub = new ItemWeapon(500, 5.5D, -2.2D, 5, "spikedClub");
	//canes
	public static final Item cane = new ItemWeapon(100, 2.0D, -2.0D, 15, "cane");
	public static final Item ironCane = new ItemWeapon(450, 3.5D, -2.0D, 10, "ironCane");
	public static final Item spearCane = new ItemWeapon(300, 5.0D, -2.0D, 13, "spearCane");
	public static final Item paradisesPortabello = new ItemWeapon(175, 3.0D, -2.0D, 10, "paradisesPortabello");
	public static final Item regiCane = new ItemWeapon(812, 6.0D, -2.0D, 7, "regiCane");
	public static final Item dragonCane = new ItemWeapon(300, 6.5D, -2.0D, 20, "dragonCane");
	public static final Item pogoCane = new ItemPogoWeapon(500, 3.0D, -2.0D, 15, "pogoCane", 0.6);
	public static final Item upStick = new ItemWeapon(toolUranium, 1, 0.0D, 0.0D, 0, "uraniumPoweredStick").setUnbreakable();	//Never runs out of uranium!
	//Spoons/forks
	public static final Item woodenSpoon = new ItemWeapon(59, 2.0D, -2.2D, 5, "woodenSpoon");
	public static final Item silverSpoon = new ItemWeapon(250, 2.5D, -2.2D, 12, "silverSpoon");
	public static final Item crockerSpoon = new ItemWeapon(512, 4.0D, -2.2D, 15, "crockerSpoon");
	public static final Item crockerFork = new ItemWeapon(512, 4.0D, -2.2D, 15, "crockerFork");
	public static final Item skaiaFork = new ItemWeapon(2048, 8.5D, -2.2D, 10, "skaiaFork");
	public static final Item fork = new ItemWeapon(100, 4.0D, -2.2D, 3, "fork");
	public static final Item spork = new ItemWeapon(120, 4.5D, -2.3D, 5, "spork");
	public static final Item goldenSpork = new ItemWeapon(45, 5D, -2.3D, 22, "goldenSpork");
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
	public static final Item crumplyHat = new MSItemBase("crumplyHat").setMaxStackSize(1);
    public static final Item stoneEyeballs = new MSItemBase("stoneEyeballs");
	public static final Item stoneTablet = new ItemStoneTablet();
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
			else if(entry.key.getResourcePath().equals("modus_card"))
				entry.remap(stackModus);
			else
			{
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

		toolUranium.setRepairItem(new ItemStack(rawUranium));
		ItemWeapon.addToolMaterial("pickaxe", Arrays.asList(Material.IRON, Material.ANVIL, Material.ROCK));
		ItemWeapon.addToolMaterial("axe", Arrays.asList(Material.WOOD, Material.PLANTS, Material.VINE));
		ItemWeapon.addToolMaterial("shovel", Arrays.asList(Material.SNOW, Material.CRAFTED_SNOW, Material.CLAY, Material.GRASS, Material.GROUND, Material.SAND));
		ItemWeapon.addToolMaterial("sword", Arrays.asList(Material.WEB));
		ItemWeapon.addToolMaterial("sickle", Arrays.asList(Material.WEB, Material.LEAVES, Material.PLANTS, Material.VINE));
	}

	private static Item registerItemBlock(IForgeRegistry<Item> registry, ItemBlock item)
	{
		registry.register(item.setRegistryName(item.getBlock().getRegistryName()));
		return item;
	}
}
