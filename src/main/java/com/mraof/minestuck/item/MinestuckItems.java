package com.mraof.minestuck.item;

import com.mraof.minestuck.entity.EntityEightBall;
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
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;

import static com.mraof.minestuck.block.MinestuckBlocks.*;
import static com.mraof.minestuck.util.ModusStorage.getStoredItem;

import java.util.Arrays;

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
	public static final Item clawHammer = new ItemWeapon(131, 4.0D, -2.4D, 10, "clawHammer").setTool("pickaxe", 0, 1.0F);
	public static final Item sledgeHammer = new ItemWeapon(250, 6.0D, -2.8D, 8, "sledgeHammer").setTool("pickaxe", 2, 4.0F);
	public static final Item blacksmithHammer = new ItemWeapon(450, 7.0D, -2.8D, 10, "blacksmithHammer").setTool("pickaxe", 2, 3.5F);
	public static final Item pogoHammer = new ItemPogoWeapon(400, 7.0D, -2.8D, 8, "pogoHammer", 0.7).setTool("pickaxe", 1, 2.0F);
	public static final Item telescopicSassacrusher = new ItemWeapon(1024, 9.0D, -2.9D, 15, "telescopicSassacrusher").setTool("pickaxe", 2, 5.0F);
	public static final Item regiHammer = new ItemWeapon(812, 6.0D, -2.4D, 5, "regiHammer");
	public static final Item fearNoAnvil = new ItemPotionWeapon(2048, 10.0D, -2.8D, 12, "fearNoAnvil", new PotionEffect(MobEffects.SLOWNESS, 100, 3)).setTool("pickaxe", 3, 7.0F);
	public static final Item meltMasher = new ItemFireWeapon(1413, 10.5, -2.8, 20, "meltMasher", 25).setTool("pickaxe", 4, 12.0F);
	public static final Item qEHammerAxe = new ItemPogoFarmine(6114, 11.0D, -2.8D, 40, "qEHammerAxe", Integer.MAX_VALUE, 200, 0.7).setTool("pickaxe", 3, 9.0F).setTool("shovel", 3, 9.0F).setTool("sickle", 3, 7.0F).setTool("axe", 3, 12.0F);
	public static final Item dDEHammerAxe = new ItemSbahjEEEE(6114, 11.01D, -2.8D, 40, "dDEHammerAxe", 0.2);
	public static final Item zillyhooHammer = new ItemWeapon(3000, 11.0D, -2.8D, 30, "zillyhooHammer").setTool("pickaxe", 4, 15.0F);

	public static final Item popamaticVrillyhoo = new ItemRandomWeapon(3000, 8.0D, -2.8D, 30, "popamaticVrillyhoo").setTool("pickaxe", 4, 15.0F);
	public static final Item scarletZillyhoo = new ItemFireWeapon(2000, 11.0D, -2.8D, 16, "scarletZillyhoo", 50).setTool("pickaxe", 3, 4.0F);
	public static final Item mwrthwl = new ItemWeapon(2000, 10.5D, -2.8D, 16, "mwrthwl").setTool("pickaxe", 3, 4.0F);
	//blades
	public static final Item sord = new ItemSord(59, 2, -2.4D, 5, "sord");
	public static final Item cactusCutlass = new ItemWeapon(104, 4, -2.4D, 10, "cactaceaeCutlass").setTool("sword", 0, 15.0F);	//The sword tool is only used against webs, hence the high efficiency.
	public static final Item steakSword = new ItemConsumableWeapon(250, 4, -2.4D, 5, "steakSword", 8, 1F);
	public static final Item beefSword = new ItemConsumableWeapon(175, 2, -2.4D, 5, "beefSword", 3, 0.8F, 75);
	public static final Item irradiatedSteakSword = new ItemConsumableWeapon(150, 2, -2.4D, 5, "irradiatedSteakSword", 4, 0.4F, 25).setPotionEffect(new PotionEffect(MobEffects.WITHER, 100, 1), 0.9F);
	public static final Item katana = new ItemWeapon(250, 5, -2.4D, 15, "ninjaSword").setTool("sword", 0, 15.0F);
	public static final Item unbreakableKatana = new ItemWeapon(2200, 7, -2.4D, 20, "katana").setTool("sword", 0, 15.0F);    //Not actually unbreakable
	public static final Item firePoker = new ItemFireWeapon(250, 6, -2.4D, 15, "firePoker", 30).setTool("sword", 0, 15.0F);
	public static final Item hotHandle = new ItemFireWeapon(350, 5, -2.4D, 15, "hotHandle", 10).setTool("sword", 0, 15.0F);
	public static final Item caledscratch = new ItemWeapon(1561, 6, -2.4D, 30, "caledscratch").setTool("sword", 0, 15.0F);
	public static final Item caledfwlch = new ItemWeapon(1025, 6, -2.4D, 30, "caledfwlch").setTool("sword", 0, 15.0F);
	public static final Item royalDeringer = new ItemWeapon(1561, 7, -2.4D, 30, "royalDeringer").setTool("sword", 0, 15.0F);
	public static final Item claymore = new ItemWeapon(600, 7D, -2.6D, 15, "claymore").setTool("sword", 0, 15.0F);
	public static final Item zillywairCutlass = new ItemWeapon(2500, 8, -2.4D, 30, "zillywairCutlass").setTool("sword", 0, 15.0F);
	public static final Item regisword = new ItemWeapon(812, 6, -2.4D, 10, "regisword").setTool("sword", 0, 15.0F);
	public static final Item scarletRibbitar = new ItemWeapon(2000, 7, -2.4D, 30, "scarletRibbitar").setTool("sword", 0, 15.0F);
	public static final Item doggMachete = new ItemWeapon(1000, 5, -2.4D, 30, "doggMachete").setTool("sword", 0, 15.0F);
	public static final Item cobaltSabre = new ItemFireWeapon(300, 7, -2.4D, 10, "cobaltSabre", 30).setTool("sword", 0, 15.0F);
	public static final Item quantumSabre = new ItemPotionWeapon(toolUranium, 600, 8, -2.4D, 5, "quantumSabre", new PotionEffect(MobEffects.WITHER, 100, 1)).setTool("sword", 0, 15.0F);
	public static final Item shatterBeacon = new ItemPotionWeapon(1850, 10, -2.4D, 35, "shatterBeacon", null, false).setRandomPotionEffect().setTool("sword", 0, 15.0f);
	//axes
	public static final Item batleacks = new ItemSord(64, 4, -3.5D, 5, "batleacks");
	public static final Item copseCrusher = new ItemFarmine(400, 6.0D, -3.0D, 20, "copseCrusher", Integer.MAX_VALUE, 20).setTool("axe", 2, 6.0F);
	public static final Item battleaxe = new ItemWeapon(600, 10D, -3.0D, 15, "battleaxe").setTool("axe", 2, 3.0F);
	public static final Item blacksmithBane = new ItemWeapon(413, 9.0D, -3.0D, 15, "blacksmithBane").setTool("axe", 2, 6.0F);
	public static final Item scraxe = new ItemWeapon(500, 10.0D, -3.0D, 20, "scraxe").setTool("axe", 2, 7.0F);
	public static final Item qPHammerAxe = new ItemPogoFarmine(800, 8.0D, -3.0D, 30, "qPHammerAxe", Integer.MAX_VALUE, 50, 0.6).setTool("pickaxe", 1, 2.0F).setTool("axe", 2, 7.0F);
	public static final Item rubyCroak = new ItemWeapon(2000, 11.0D, -3.0D, 30, "rubyCroak").setTool("axe", 3, 8.0F);
	public static final Item hephaestusLumber = new ItemFireWeapon(3000, 11.0D, -3.0D, 30, "hephaestusLumber", 30).setTool("axe", 3, 9.0F);
	public static final Item qFHammerAxe = new ItemPogoFarmine(toolUranium, 2048, 11.0D, -3.0D, 0, "qFHammerAxe", Integer.MAX_VALUE, 100, 0.7).setTool("pickaxe", 2, 5.0F).setTool("axe", 3, 9.0F);
	//Dice
	public static final Item dice = new ItemWeapon(51, 6, 3, 6, "dice");
	public static final Item fluoriteOctet = new ItemWeapon(67, 15, 6, 8, "fluoriteOctet");
	//misc weapons
	public static final Item catClaws = new ItemDualWeapon(500, 4.0D, 1.0D, -1.5D, -1.0D, 6, "catclaws");
	//sickles
	public static final Item sickle = new ItemWeapon(220, 4.0D, -2.4D, 8, "sickle").setTool("sickle", 0, 1.5F);
	public static final Item homesSmellYaLater = new ItemWeapon(400, 5.5D, -2.4D, 10, "homesSmellYaLater").setTool("sickle", 0, 3.0F);
	public static final Item fudgeSickle = new ItemConsumableWeapon(450, 5.5D, -2.4D, 10, "fudgeSickle", 7, 0.6F).setTool("sickle", 0, 1.0F);
	public static final Item regiSickle = new ItemWeapon(812, 6.0D, -2.4D, 5, "regiSickle").setTool("sickle", 0, 4.0F);
	public static final Item clawSickle = new ItemWeapon(2048, 7.0D, -2.4D, 15, "clawSickle").setTool("sickle", 0, 4.0F);
	public static final Item clawOfNrubyiglith = new ItemHorrorterrorWeapon(1600, 9.5D, -2.4D, 15, "clawOfNrubyiglith").setTool("sickle", 0, 4.0F);
	public static final Item candySickle = new ItemCandyWeapon(96, 6.0D, -2.4D, 15, "candySickle").setTool("sickle", 0, 2.5F);
	//clubs
	public static final Item deuceClub = new ItemWeapon(1024, 2.5D, -2.2D, 15, "deuceClub");
	public static final Item nightClub = new ItemWeapon(600, 4.0D, -2.2D, 20, "nightClub");
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
	public static final Item upStick = new ItemWeapon(toolUranium, 1, 0.0D, 0.0D, 0, "upStick").setUnbreakable();	//Never runs out of uranium!
	//Spoons/forks
	public static final Item woodenSpoon = new ItemWeapon(59, 2.0D, -2.2D, 5, "woodenSpoon");
	public static final Item silverSpoon = new ItemWeapon(250, 2.5D, -2.2D, 12, "silverSpoon");
	public static final ItemSpork crockerSpork = (ItemSpork) new ItemSpork(512, 4.0D, -2.2D, 15, "crocker");
	public static final Item skaiaFork = new ItemWeapon(2048, 8.5D, -2.2D, 10, "skaiaFork");
	public static final Item fork = new ItemWeapon(100, 4.0D, -2.2D, 3, "fork");
	public static final Item spork = new ItemWeapon(120, 4.5D, -2.3D, 5, "spork");
	public static final Item goldenSpork = new ItemWeapon(45, 5D, -2.3D, 22, "goldenSpork");
	//Material tools
	public static final Item emeraldSword = new ItemSword(toolEmerald).setUnlocalizedName("swordEmerald").setCreativeTab(TabMinestuck.instance);
	public static final Item emeraldAxe = new ItemMinestuckAxe(toolEmerald, 9.0F, -3.0F).setUnlocalizedName("hatchetEmerald").setCreativeTab(TabMinestuck.instance);
	public static final Item emeraldPickaxe = new ItemMinestuckPickaxe(toolEmerald).setUnlocalizedName("pickaxeEmerald").setCreativeTab(TabMinestuck.instance);
	public static final Item emeraldShovel = new ItemSpade(toolEmerald).setUnlocalizedName("shovelEmerald").setCreativeTab(TabMinestuck.instance);
	public static final Item emeraldHoe = new ItemHoe(toolEmerald).setUnlocalizedName("hoeEmerald").setCreativeTab(TabMinestuck.instance);
	//Armor
	public static final Item prismarineHelmet = new ItemArmor(armorPrismarine, 0, EntityEquipmentSlot.HEAD).setUnlocalizedName("helmetPrismarine").setCreativeTab(TabMinestuck.instance);
	public static final Item prismarineChestplate = new ItemArmor(armorPrismarine, 0, EntityEquipmentSlot.CHEST).setUnlocalizedName("chestplatePrismarine").setCreativeTab(TabMinestuck.instance);
	public static final Item prismarineLeggings = new ItemArmor(armorPrismarine, 0, EntityEquipmentSlot.LEGS).setUnlocalizedName("leggingsPrismarine").setCreativeTab(TabMinestuck.instance);
	public static final Item prismarineBoots = new ItemArmor(armorPrismarine, 0, EntityEquipmentSlot.FEET).setUnlocalizedName("bootsPrismarine").setCreativeTab(TabMinestuck.instance);
	//Food
	public static final Item candy = new ItemMinestuckCandy();
	public static final Item beverage = new ItemMinestuckBeverage();
	public static final Item bugOnAStick = new net.minecraft.item.ItemFood(1, 0.1F, false).setUnlocalizedName("bugOnAStick").setCreativeTab(TabMinestuck.instance);
	public static final Item chocolateBeetle = new net.minecraft.item.ItemFood(3, 0.4F, false).setUnlocalizedName("chocolateBeetle").setCreativeTab(TabMinestuck.instance);
	public static final Item coneOfFlies = new net.minecraft.item.ItemFood(2, 0.1F, false).setUnlocalizedName("coneOfFlies").setCreativeTab(TabMinestuck.instance);
	public static final Item grasshopper = new net.minecraft.item.ItemFood(4, 0.5F, false).setUnlocalizedName("grasshopper").setCreativeTab(TabMinestuck.instance);
	public static final Item jarOfBugs = new net.minecraft.item.ItemFood(3, 0.2F, false).setUnlocalizedName("jarOfBugs").setCreativeTab(TabMinestuck.instance);
	public static final Item onion = new net.minecraft.item.ItemFood(2, 0.2F, false).setUnlocalizedName("onion").setCreativeTab(TabMinestuck.instance);
	public static final Item salad = new ItemSoup(1).setUnlocalizedName("salad").setCreativeTab(TabMinestuck.instance);
	public static final Item desertFruit = new net.minecraft.item.ItemFood(1, 0.1F, false).setUnlocalizedName("desertFruit").setCreativeTab(TabMinestuck.instance);
	public static final Item irradiatedSteak = new net.minecraft.item.ItemFood(4, 0.4F, true).setPotionEffect(new PotionEffect(MobEffects.WITHER, 100, 1), 0.9F).setUnlocalizedName("irradiatedSteak").setCreativeTab(TabMinestuck.instance);
	public static final Item rockCookie = new Item().setUnlocalizedName("rockCookie").setCreativeTab(TabMinestuck.instance);
	public static final Item fungalSpore = new net.minecraft.item.ItemFood(1, 0.2F, false).setPotionEffect(new PotionEffect(MobEffects.POISON, 60, 3), 0.7F).setUnlocalizedName("fungalSpore").setCreativeTab(TabMinestuck.instance);
	public static final Item sporeo = new net.minecraft.item.ItemFood(3, 0.4F, false).setUnlocalizedName("sporeo").setCreativeTab(TabMinestuck.instance);
	public static final Item morelMushroom = new net.minecraft.item.ItemFood(3, 0.9F, false).setUnlocalizedName("morelMushroom").setCreativeTab(TabMinestuck.instance);
	public static final Item frenchFry = new net.minecraft.item.ItemFood(1, 0.1F, false).setUnlocalizedName("frenchFry").setCreativeTab(TabMinestuck.instance);
	public static final Item strawberryChunk = new ItemMinestuckSeedFood(4, 0.5F).setUnlocalizedName("strawberryChunk").setCreativeTab(TabMinestuck.instance);
	public static final Item surpriseEmbryo = new ItemSurpriseEmbryo(3, 0.2F, false);
	public static final Item unknowableEgg = new ItemUnknowableEgg(3, 0.3F, false).setUnlocalizedName("unknowableEgg");
	//Other
	public static final Item goldenGrasshopper = new Item().setUnlocalizedName("goldenGrasshopper").setCreativeTab(TabMinestuck.instance);
	public static final Item bugNet = new ItemNet().setUnlocalizedName("net");
	public static final Item itemFrog = new ItemFrog().setUnlocalizedName("frog");
	public static final Item boondollars = new ItemBoondollars();
	public static final Item rawCruxite = new Item().setUnlocalizedName("rawCruxite").setCreativeTab(TabMinestuck.instance);
	public static final Item rawUranium = new Item().setUnlocalizedName("rawUranium").setCreativeTab(TabMinestuck.instance);
	public static final Item energyCore = new Item().setUnlocalizedName("energyCore").setCreativeTab(TabMinestuck.instance);
	//public static final Item chessboard = new Item().setUnlocalizedName("chessboard").setCreativeTab(TabMinestuck.instance);
	public static final ItemDowel cruxiteDowel = new ItemDowel(MinestuckBlocks.blockCruxiteDowel);
	public static final Item captchaCard = new ItemCaptchaCard();
	public static final Item cruxiteApple = new ItemCruxiteApple();
	public static final Item cruxitePotion = new ItemCruxitePotion();
	public static final Item disk = new ItemDisk();
	public static final Item grimoire = new ItemGrimoire().setUnlocalizedName("grimoire");
	public static final Item longForgottenWarhorn = new ItemLongForgottenWarhorn().setUnlocalizedName("longForgottenWarhorn");
	//public static final Item chessboard = new Item().setUnlocalizedName("chessboard").setMaxStackSize(1).setCreativeTab(TabMinestuck.instance);
	public static final Item minestuckBucket = new ItemMinestuckBucket();
	public static final Item obsidianBucket = new ItemObsidianBucket();
	public static final Item goldSeeds = new ItemGoldSeeds();
	public static final Item razorBlade = new ItemRazorBlade();
	public static final Item metalBoat = new ItemMetalBoat();
	public static final Item shunt = new ItemShunt();
	public static final Item captcharoidCamera = new ItemCaptcharoidCamera();
	public static final Item threshDvd = new Item().setUnlocalizedName("threshDvd").setMaxStackSize(1).setCreativeTab(TabMinestuck.instance);
	public static final Item gamebroMagazine = new Item().setUnlocalizedName("gamebroMagazine").setMaxStackSize(1).setCreativeTab(TabMinestuck.instance);
	public static final Item gamegrlMagazine = new Item().setUnlocalizedName("gamegrlMagazine").setMaxStackSize(1).setCreativeTab(TabMinestuck.instance);
	public static final Item crewPoster = new ItemHanging()
	{
		@Override
		public EntityHanging createEntity(World worldIn, BlockPos pos, EnumFacing facing, ItemStack stack, int meta)
		{
			return new EntityCrewPoster(worldIn, pos, facing);
		}
	}.setUnlocalizedName("crewPoster").setMaxStackSize(1).setCreativeTab(TabMinestuck.instance);
	public static final Item sbahjPoster = new ItemHanging()
	{
		@Override
		public EntityHanging createEntity(World worldIn, BlockPos pos, EnumFacing facing, ItemStack stack, int meta)
		{
			return new EntitySbahjPoster(worldIn, pos, facing);
		}
	}.setUnlocalizedName("sbahjPoster").setMaxStackSize(1).setCreativeTab(TabMinestuck.instance);
	
	public static final Item shopPoster = new ItemShopPoster()
	{
		@Override
		public EntityHanging createEntity(World worldIn, BlockPos pos, EnumFacing facing, ItemStack stack, int meta)
		{
			return new EntityShopPoster(worldIn, pos, facing, stack, meta);
		}
	}.setUnlocalizedName("shopPoster").setMaxStackSize(1).setCreativeTab(TabMinestuck.instance);

	public static final Item carvingTool = new Item().setUnlocalizedName("carvingTool").setMaxStackSize(1).setCreativeTab(TabMinestuck.instance);
	public static final Item crumplyHat = new Item().setUnlocalizedName("crumplyHat").setMaxStackSize(1).setCreativeTab(TabMinestuck.instance);
    public static final Item stoneEyeballs = new Item().setUnlocalizedName("stoneEyeballs").setCreativeTab(TabMinestuck.instance);
	public static final Item stoneSlab = new Item().setUnlocalizedName("stoneSlab").setCreativeTab(TabMinestuck.instance);
	public static final Item glowystoneDust = new ItemGlowystoneDust().setUnlocalizedName("glowystoneDust").setCreativeTab(TabMinestuck.instance);
	public static final Item fakeArms = new Item().setUnlocalizedName("fakeArms").setCreativeTab(null);
	//Music disks
	public static final Item recordEmissaryOfDance = new ItemMinestuckRecord("emissary", MinestuckSoundHandler.soundEmissaryOfDance).setUnlocalizedName("record");
	public static final Item recordDanceStab = new ItemMinestuckRecord("danceStab", MinestuckSoundHandler.soundDanceStabDance).setUnlocalizedName("record");
	public static final Item recordRetroBattle = new ItemMinestuckRecord("retroBattle",MinestuckSoundHandler.soundRetroBattleTheme).setUnlocalizedName("record");

	public static final Item stackModus = new Item().setUnlocalizedName("stackModus").setMaxStackSize(1).setCreativeTab(TabMinestuck.instance);
	public static final Item queueModus = new Item().setUnlocalizedName("queueModus").setMaxStackSize(1).setCreativeTab(TabMinestuck.instance);
	public static final Item queueStackModus = new Item().setUnlocalizedName("queueStackModus").setMaxStackSize(1).setCreativeTab(TabMinestuck.instance);
	public static final Item treeModus = new Item().setUnlocalizedName("treeModus").setMaxStackSize(1).setCreativeTab(TabMinestuck.instance);
	public static final Item hashmapModus = new Item().setUnlocalizedName("hashmapModus").setMaxStackSize(1).setCreativeTab(TabMinestuck.instance);
	public static final Item setModus = new Item().setUnlocalizedName("setModus").setMaxStackSize(1).setCreativeTab(TabMinestuck.instance);
	public static final Item wildMagicModus = new Item().setUnlocalizedName("wildMagicModus").setMaxStackSize(1).setCreativeTab(TabMinestuck.instance);
	public static final Item weightModus = new Item().setUnlocalizedName("weightModus").setMaxStackSize(1).setCreativeTab(TabMinestuck.instance);
	public static final Item bookModus = new Item().setUnlocalizedName("bookModus").setMaxStackSize(1).setCreativeTab(TabMinestuck.instance);
	public static final Item capitalistModus = new Item().setUnlocalizedName("capitalistModus").setMaxStackSize(1).setCreativeTab(TabMinestuck.instance);
	public static final Item modUs = new Item().setUnlocalizedName("modUs").setMaxStackSize(1).setCreativeTab(TabMinestuck.instance);
	public static final Item operandiModus = new Item().setUnlocalizedName("operandiModus").setMaxStackSize(1).setCreativeTab(TabMinestuck.instance);
	public static final Item onionModus = new Item().setUnlocalizedName("onionModus").setMaxStackSize(1).setCreativeTab(TabMinestuck.instance);
	public static final Item slimeModus = new Item().setUnlocalizedName("slimeModus").setMaxStackSize(1).setCreativeTab(TabMinestuck.instance);
	public static final Item popTartModus = new Item().setUnlocalizedName("popTartModus").setMaxStackSize(1).setCreativeTab(TabMinestuck.instance);
	public static final Item deckModus = new Item().setUnlocalizedName("deckModus").setMaxStackSize(1).setCreativeTab(TabMinestuck.instance);
	public static final Item hueModus = new Item().setUnlocalizedName("hueModus").setMaxStackSize(1).setCreativeTab(TabMinestuck.instance);
	public static final Item hueStackModus = new Item().setUnlocalizedName("hueStackModus").setMaxStackSize(1).setCreativeTab(TabMinestuck.instance);
	public static final Item chatModus = new Item().setUnlocalizedName("chatModus").setMaxStackSize(1).setCreativeTab(TabMinestuck.instance);
	public static final Item cycloneModus = new Item().setUnlocalizedName("cycloneModus").setMaxStackSize(1).setCreativeTab(TabMinestuck.instance);
	public static final Item energyModus = new Item().setUnlocalizedName("energyModus").setMaxStackSize(1).setCreativeTab(TabMinestuck.instance);
	public static final Item scratchAndSniffModus = new Item().setUnlocalizedName("scratchAndSniffModus").setMaxStackSize(1).setCreativeTab(TabMinestuck.instance);
	public static final Item eightBallModus = new Item().setUnlocalizedName("eightBallModus").setMaxStackSize(1).setCreativeTab(TabMinestuck.instance);
	public static final Item chasityModus = new Item().setUnlocalizedName("chasityModus").setMaxStackSize(1).setCreativeTab(TabMinestuck.instance);
	public static final Item jujuModus = new Item().setUnlocalizedName("jujuModus").setMaxStackSize(1).setCreativeTab(TabMinestuck.instance);
	public static final Item alcheModus = new Item().setUnlocalizedName("alcheModus").setMaxStackSize(1).setCreativeTab(TabMinestuck.instance);
	public static final Item arrayModus = new Item().setUnlocalizedName("arrayModus").setMaxStackSize(1).setCreativeTab(TabMinestuck.instance);
	public static final Item monsterModus = new Item().setUnlocalizedName("monsterModus").setMaxStackSize(1).setCreativeTab(TabMinestuck.instance);
	public static final Item walletModus = new Item().setUnlocalizedName("walletModus").setMaxStackSize(1).setCreativeTab(TabMinestuck.instance);
	public static final Item crystalBallModus = new Item().setUnlocalizedName("crystalBallModus").setMaxStackSize(1).setCreativeTab(TabMinestuck.instance);
	public static final Item hashchatModus = new Item().setUnlocalizedName("hashchatModus").setMaxStackSize(1).setCreativeTab(TabMinestuck.instance);
	public static final Item sacrificeModus = new Item().setUnlocalizedName("sacrificeModus").setMaxStackSize(1).setCreativeTab(TabMinestuck.instance);
	/*
	public static final Item memoryModus = new Item().setUnlocalizedName("memoryModus");
	public static final Item recipeModus = new Item().setUnlocalizedName("recipeModus");
	public static final Item bottledMsgModus = new Item().setUnlocalizedName("messageInABottleModus");
	public static final Item techHopModus = new Item().setUnlocalizedName("techHopModus");
	public static final Item encryptionModus = new Item().setUnlocalizedName("encryptionModus");
	public static final Item ouijaModus = new Item().setUnlocalizedName("ouijaModus");
	public static final Item bundleModus = new Item().setUnlocalizedName("bundleModus");
	public static final Item cakeModus = new Item().setUnlocalizedName("cakeModus");
	public static final Item cipherModus = new Item().setUnlocalizedName("cipherModus");
	*/

	public static final Item popTart = new ItemFood("popTart", 3, 0, false, PopTartModus.getConsumer());
	public static final Item eightBall = new ItemEightBall("eightBall", false);
	public static final Item popBall = new ItemFood("magicPopBalls", 6, 0.4f, false, ItemFood.getPopBallConsumer());
	public static final Item floatStone = new Item().setUnlocalizedName("floatStone").setMaxStackSize(1).setCreativeTab(TabMinestuck.instance);
	public static final Item energyCell = new Item().setUnlocalizedName("energyCell").setCreativeTab(TabMinestuck.instance);
	public static final Item captchalogueBook = new ItemCruxiteCaptchaBook("captchalogueBook");
	public static final Item chasityKey = new ItemCruxiteChasityKey("chasityKey");
	//public static final Item cruxiteBottle = new BaseItem("cruxiteBottle");
	public static final Item crystalEightBall = new ItemEightBall("crystalEightBall", true);
	public static final Item cruxiteGel = new ItemCruxite("cruxiteGel");
	public static final Item dragonGel = new Item().setUnlocalizedName("dragonGel").setCreativeTab(TabMinestuck.instance);
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

	public static final Item hardStone = new ItemBlock(MinestuckBlocks.hardStone).setUnlocalizedName("hardStone").setCreativeTab(TabMinestuck.instance);
	public static final Item walletEntityItem = new ItemWalletEntity("walletEntity");

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
		
		//hammers
		registry.register(clawHammer.setRegistryName("claw_hammer"));
		registry.register(sledgeHammer.setRegistryName("sledge_hammer"));
		registry.register(blacksmithHammer.setRegistryName("blacksmith_hammer"));
		registry.register(pogoHammer.setRegistryName("pogo_hammer"));
		registry.register(telescopicSassacrusher.setRegistryName("telescopic_sassacrusher"));
		registry.register(regiHammer.setRegistryName("regi_hammer"));
		registry.register(fearNoAnvil.setRegistryName("fear_no_anvil"));
		registry.register(meltMasher.setRegistryName("melt_masher"));
		registry.register(qEHammerAxe.setRegistryName("estrogen_empowered_everything_eradicator"));
		registry.register(dDEHammerAxe.setRegistryName("eeeeeeeeeeee"));
		registry.register(zillyhooHammer.setRegistryName("zillyhoo_hammer"));
		registry.register(popamaticVrillyhoo.setRegistryName("popamatic_vrillyhoo"));
		registry.register(scarletZillyhoo.setRegistryName("scarlet_zillyhoo"));
		registry.register(mwrthwl.setRegistryName("mwrthwl"));
		
		//blades
		registry.register(sord.setRegistryName("sord"));
		registry.register(cactusCutlass.setRegistryName("cactaceae_cutlass"));
		registry.register(steakSword.setRegistryName("steak_sword"));
		registry.register(beefSword.setRegistryName("beef_sword"));
		registry.register(irradiatedSteakSword.setRegistryName("irradiated_steak_sword"));
		registry.register(katana.setRegistryName("katana"));
		registry.register(unbreakableKatana.setRegistryName("unbreakable_katana"));
		registry.register(firePoker.setRegistryName("fire_poker"));
		registry.register(hotHandle.setRegistryName("too_hot_to_handle"));
		registry.register(caledscratch.setRegistryName("caledscratch"));
		registry.register(caledfwlch.setRegistryName("caledfwlch"));
		registry.register(royalDeringer.setRegistryName("royal_deringer"));
		registry.register(claymore.setRegistryName("claymore"));
		registry.register(zillywairCutlass.setRegistryName("cutlass_of_zillywair"));
		registry.register(regisword.setRegistryName("regisword"));
		registry.register(scarletRibbitar.setRegistryName("scarlet_ribbitar"));
		registry.register(doggMachete.setRegistryName("dogg_machete"));
		registry.register(cobaltSabre.setRegistryName("cobalt_sabre"));
		registry.register(quantumSabre.setRegistryName("quantum_sabre"));
		registry.register(shatterBeacon.setRegistryName("shatterbeacon"));

		//axes
		registry.register(batleacks.setRegistryName("batleacks"));
		registry.register(copseCrusher.setRegistryName("copse_crusher"));
		registry.register(battleaxe.setRegistryName("battleaxe"));
		registry.register(blacksmithBane.setRegistryName("blacksmith_bane"));
		registry.register(scraxe.setRegistryName("scraxe"));
		registry.register(qPHammerAxe.setRegistryName("piston_powered_pogo_axehammer"));
		registry.register(rubyCroak.setRegistryName("ruby_croak"));
		registry.register(hephaestusLumber.setRegistryName("hephaestus_lumberjack"));
		registry.register(qFHammerAxe.setRegistryName("fission_focused_fault_feller"));
		
		//Dice
		registry.register(dice.setRegistryName("dice"));
		registry.register(fluoriteOctet.setRegistryName("fluorite_octet"));
		//misc weapons
		registry.register(catClaws.setRegistryName("catclaws"));
		//sickles
		registry.register(sickle.setRegistryName("sickle"));
		registry.register(homesSmellYaLater.setRegistryName("homes_smell_ya_later"));
		registry.register(fudgeSickle.setRegistryName("fudgesickle"));
		registry.register(regiSickle.setRegistryName("regisickle"));
		registry.register(clawSickle.setRegistryName("claw_sickle"));
		registry.register(clawOfNrubyiglith.setRegistryName("claw_of_nrubyiglith"));
		registry.register(candySickle.setRegistryName("candy_sickle"));
		
		//clubs
		registry.register(deuceClub.setRegistryName("deuce_club"));
		registry.register(nightClub.setRegistryName("nightclub"));
		registry.register(pogoClub.setRegistryName("pogo_club"));
		registry.register(metalBat.setRegistryName("metal_bat"));
		registry.register(spikedClub.setRegistryName("spiked_club"));
		
		//canes
		registry.register(cane.setRegistryName("cane"));
		registry.register(ironCane.setRegistryName("iron_cane"));
		registry.register(spearCane.setRegistryName("spear_cane"));
		registry.register(paradisesPortabello.setRegistryName("paradises_portabello"));
		registry.register(regiCane.setRegistryName("regi_cane"));
		registry.register(dragonCane.setRegistryName("dragon_cane"));
		registry.register(pogoCane.setRegistryName("pogo_cane"));
		registry.register(upStick.setRegistryName("uranium_powered_stick"));
		//Spoons/forks
		registry.register(woodenSpoon.setRegistryName("wooden_spoon"));
		registry.register(silverSpoon.setRegistryName("silver_spoon"));
		registry.register(crockerSpork.setRegistryName("crocker_spork"));
		registry.register(skaiaFork.setRegistryName("skaia_fork"));
		registry.register(fork.setRegistryName("fork"));
		registry.register(spork.setRegistryName("spork"));
		registry.register(goldenSpork.setRegistryName("golden_spork"));

		registry.register(emeraldSword.setRegistryName("emerald_sword"));
		registry.register(emeraldAxe.setRegistryName("emerald_axe"));
		registry.register(emeraldPickaxe.setRegistryName("emerald_pickaxe"));
		registry.register(emeraldShovel.setRegistryName("emerald_shovel"));
		registry.register(emeraldHoe.setRegistryName("emerald_hoe"));
		
		//armor
		registry.register(prismarineHelmet.setRegistryName("prismarine_helmet"));
		registry.register(prismarineChestplate.setRegistryName("prismarine_chestplate"));
		registry.register(prismarineLeggings.setRegistryName("prismarine_leggings"));
		registry.register(prismarineBoots.setRegistryName("prismarine_boots"));
		
		//food
		registry.register(candy.setRegistryName("candy"));
		registry.register(beverage.setRegistryName("beverage"));
		registry.register(bugOnAStick.setRegistryName("bug_on_stick"));
		registry.register(chocolateBeetle.setRegistryName("chocolate_beetle"));
		registry.register(coneOfFlies.setRegistryName("cone_of_flies"));
		registry.register(grasshopper.setRegistryName("grasshopper"));
		registry.register(jarOfBugs.setRegistryName("jar_of_bugs"));
		registry.register(onion.setRegistryName("onion"));
		registry.register(salad.setRegistryName("salad"));
		registry.register(desertFruit.setRegistryName("desert_fruit"));
		registry.register(irradiatedSteak.setRegistryName("irradiated_steak"));
		registry.register(rockCookie.setRegistryName("rock_cookie"));
		registry.register(fungalSpore.setRegistryName("fungal_spore"));
		registry.register(sporeo.setRegistryName("sporeo"));
		registry.register(morelMushroom.setRegistryName("morel_mushroom"));
		registry.register(frenchFry.setRegistryName("french_fry"));
		registry.register(strawberryChunk.setRegistryName("strawberry_chunk"));
		registry.register(surpriseEmbryo.setRegistryName("surprise_embryo"));
		registry.register(unknowableEgg.setRegistryName("unknowable_egg"));

		//misc
		registry.register(goldenGrasshopper.setRegistryName("golden_grasshopper"));
		registry.register(bugNet.setRegistryName("net"));
		registry.register(itemFrog.setRegistryName("frog"));
		registry.register(boondollars.setRegistryName("boondollars"));
		registry.register(rawCruxite.setRegistryName("raw_cruxite"));
		registry.register(rawUranium.setRegistryName("raw_uranium"));
		registry.register(energyCore.setRegistryName("energy_core"));
		registry.register(captchaCard.setRegistryName("captcha_card"));
		registry.register(cruxiteApple.setRegistryName("cruxite_apple"));
		registry.register(cruxitePotion.setRegistryName("cruxite_potion"));
		registry.register(disk.setRegistryName("computer_disk"));
        //registry.register(chessboard.setRegistryName("chessboard"));
		registry.register(grimoire.setRegistryName("grimoire"));
		registry.register(longForgottenWarhorn.setRegistryName("long_forgotten_warhorn"));
		registry.register(minestuckBucket.setRegistryName("minestuck_bucket"));
		registry.register(obsidianBucket.setRegistryName("bucket_obsidian"));
		registry.register(goldSeeds.setRegistryName("gold_seeds"));
		registry.register(razorBlade.setRegistryName("razor_blade"));
		registry.register(metalBoat.setRegistryName("metal_boat"));
		registry.register(threshDvd.setRegistryName("thresh_dvd"));
		registry.register(gamebroMagazine.setRegistryName("gamebro_magazine"));
		registry.register(gamegrlMagazine.setRegistryName("gamegrl_magazine"));
		registry.register(crewPoster.setRegistryName("crew_poster"));
		registry.register(sbahjPoster.setRegistryName("sbahj_poster"));
		//registry.register(shopPoster.setRegistryName("shop_poster"));
		registry.register(carvingTool.setRegistryName("carving_tool"));
		registry.register(crumplyHat.setRegistryName("crumply_hat"));
		registry.register(stoneEyeballs.setRegistryName("stone_eyeballs"));
		registry.register(stoneSlab.setRegistryName("stone_slab"));
		registry.register(glowystoneDust.setRegistryName("glowystone_dust"));
		registry.register(fakeArms.setRegistryName("fake_arms"));
		//registry.register(shunt.setRegistryName("shunt"));
		registry.register(captcharoidCamera.setRegistryName("captcharoid_camera"));

		//Music disks
		registry.register(recordEmissaryOfDance.setRegistryName("record_emissary"));
		registry.register(recordDanceStab.setRegistryName("record_dance_stab"));
		registry.register(recordRetroBattle.setRegistryName("record_retro_battle"));

		registry.register(stackModus.setRegistryName("stack_modus"));
		registry.register(queueModus.setRegistryName("queue_modus"));
		registry.register(queueStackModus.setRegistryName("queue_stack_modus"));
		registry.register(hashmapModus.setRegistryName("hashmap_modus"));
		registry.register(treeModus.setRegistryName("tree_modus"));
		registry.register(setModus.setRegistryName("set_modus"));
		registry.register(wildMagicModus.setRegistryName("wild_magic_modus"));
		registry.register(weightModus.setRegistryName("weight_modus"));
		registry.register(bookModus.setRegistryName("book_modus"));
		registry.register(capitalistModus.setRegistryName("capitalist_modus"));
		registry.register(modUs.setRegistryName("mod_us"));
		registry.register(operandiModus.setRegistryName("operandi_modus"));
		registry.register(onionModus.setRegistryName("onion_modus"));
		registry.register(slimeModus.setRegistryName("slime_modus"));
		registry.register(popTartModus.setRegistryName("pop_tart_modus"));
		registry.register(deckModus.setRegistryName("deck_modus"));
		registry.register(hueModus.setRegistryName("hue_modus"));
		registry.register(hueStackModus.setRegistryName("hue_stack_modus"));
		registry.register(chatModus.setRegistryName("chat_modus"));
		registry.register(cycloneModus.setRegistryName("cyclone_modus"));
		registry.register(energyModus.setRegistryName("energy_modus"));
		registry.register(scratchAndSniffModus.setRegistryName("scratch_and_sniff_modus"));
		registry.register(eightBallModus.setRegistryName("eight_ball_modus"));
		registry.register(chasityModus.setRegistryName("chasity_modus"));
		registry.register(jujuModus.setRegistryName("juju_modus"));
		registry.register(alcheModus.setRegistryName("alchemodus"));
		registry.register(arrayModus.setRegistryName("array_modus"));
		registry.register(monsterModus.setRegistryName("monster_modus"));
		registry.register(walletModus.setRegistryName("wallet_modus"));
		registry.register(crystalBallModus.setRegistryName("crystal_ball_modus"));
		registry.register(hashchatModus.setRegistryName("hashchat_modus"));
		registry.register(sacrificeModus.setRegistryName("sacrifice_modus"));

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

		registry.register(popTart.setRegistryName("pop_tart"));
		registry.register(eightBall.setRegistryName("eight_ball"));
		registry.register(popBall.setRegistryName("magic_pop_balls"));
		registry.register(floatStone.setRegistryName("float_stone"));
		registry.register(energyCell.setRegistryName("energy_cell"));
		registry.register(captchalogueBook.setRegistryName("captchalogue_book"));
		registry.register(chasityKey.setRegistryName("chasity_key"));
		//registry.register(cruxiteBottle.setRegistryName("cruxite_bottle"));
		registry.register(crystalEightBall.setRegistryName("crystal_eight_ball"));
		registry.register(cruxiteGel.setRegistryName("cruxite_gel"));
		registry.register(dragonGel.setRegistryName("dragon_gel"));
		registry.register(cruxtruderGel.setRegistryName("cruxtruder_gel"));

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

		registry.register(operandiPickaxe.setRegistryName("operandi_pickaxe"));
		registry.register(operandiAxe.setRegistryName("operandi_axe"));
		registry.register(operandiShovel.setRegistryName("operandi_shovel"));
		registry.register(operandiHoe.setRegistryName("operandi_hoe"));
		registry.register(operandiSword.setRegistryName("operandi_sword"));
		registry.register(operandiHammer.setRegistryName("operandi_hammer"));
		registry.register(operandiClub.setRegistryName("operandi_club"));
		registry.register(operandiBattleaxe.setRegistryName("operandi_battleaxe"));
		registry.register(operandiApple.setRegistryName("operandi_apple"));
		registry.register(operandiPotion.setRegistryName("operandi_potion"));
		registry.register(operandiPopTart.setRegistryName("operandi_pop_tart"));
		registry.register(operandiEightBall.setRegistryName("operandi_eight_ball"));
		registry.register(operandiSplashPotion.setRegistryName("operandi_splash_potion"));
		registry.register(operandiHelmet.setRegistryName("operandi_helmet"));
		registry.register(operandiChestplate.setRegistryName("operandi_chestplate"));
		registry.register(operandiLeggings.setRegistryName("operandi_leggings"));
		registry.register(operandiBoots.setRegistryName("operandi_boots"));
		registry.register(operandiBlock.setRegistryName("operandi_block"));
		registry.register(operandiStone.setRegistryName("operandi_stone"));
		registry.register(operandiLog.setRegistryName("operandi_log"));
		registry.register(operandiGlass.setRegistryName("operandi_glass"));

		registry.register(hardStone.setRegistryName("hard_stone"));
		registry.register(walletEntityItem.setRegistryName("wallet_entity"));

		((ItemMinestuckBucket) minestuckBucket).addBlock(blockOil.getDefaultState());
		((ItemMinestuckBucket) minestuckBucket).addBlock(blockBlood.getDefaultState());
		((ItemMinestuckBucket) minestuckBucket).addBlock(blockBrainJuice.getDefaultState());
		((ItemMinestuckBucket) minestuckBucket).addBlock(blockWatercolors.getDefaultState());
		((ItemMinestuckBucket) minestuckBucket).addBlock(blockEnder.getDefaultState());
		((ItemMinestuckBucket) minestuckBucket).addBlock(blockLightWater.getDefaultState());

		/*for(Block block : liquidGrists)
		{
			minestuckBucket.addBlock(block.getDefaultState());
		}*/
		
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
