package com.mraof.minestuck.item;

import com.cibernet.splatcraft.items.ItemFilter;
import com.google.common.collect.ImmutableList;
import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.alchemy.GristType;
import com.mraof.minestuck.block.IRegistryBlock;
import com.mraof.minestuck.block.MinestuckBlocks;
import com.mraof.minestuck.client.model.armor.*;
import com.mraof.minestuck.client.renderer.entity.RenderThrowable;
import com.mraof.minestuck.enchantments.MSUEnchantments;
import com.mraof.minestuck.entity.EntityEightBall;
import com.mraof.minestuck.entity.item.EntityCrewPoster;
import com.mraof.minestuck.entity.item.EntitySbahjPoster;
import com.mraof.minestuck.inventory.captchalouge.PopTartModus;
import com.mraof.minestuck.item.armor.*;
import com.mraof.minestuck.item.block.ItemDowel;
import com.mraof.minestuck.item.block.MSItemBlock;
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
import com.mraof.minestuck.item.weapon.*;
import com.mraof.minestuck.util.*;
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
import vazkii.botania.common.block.ModBlocks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.mraof.minestuck.block.MinestuckBlocks.blocks;
import static com.mraof.minestuck.block.MinestuckBlocks.strawberryStem;
import static com.mraof.minestuck.util.ModusStorage.getStoredItem;

public class MinestuckItems
{
	public static final ArrayList<IRegistryItem<Item>> items = new ArrayList<>();
	public static final ArrayList<ItemModus> modi = new ArrayList<>();
	public static final ArrayList<ItemMinestuckRecord> records = new ArrayList<>();

	private static final PropertySoundOnHit.Value PITCH_NOTE = ((stack, target, player) -> (-player.rotationPitch + 90) / 90f);

	public static final Item.ToolMaterial toolUranium = EnumHelper.addToolMaterial("URANIUM", 3, 1220, 12.0F, 6.0F, 15);
	public static final Item.ToolMaterial toolEmerald = EnumHelper.addToolMaterial("EMERALD", 3, 1220, 12.0F, 4.0F, 12).setRepairItem(new ItemStack(Items.EMERALD));
	public static final ItemArmor.ArmorMaterial armorPrismarine = EnumHelper.addArmorMaterial("PRISMARINE", Minestuck.MODID+":prismarine", 20, new int[]{3, 7, 6, 2}, 15, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F);
	public static final ItemArmor.ArmorMaterial materialDiverHelmet = EnumHelper.addArmorMaterial("DIVER_HELMET", Minestuck.MODID+":diver_helmet", 120, new int[] {0, 0, 0, 3}, 5, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F);
	public static final ItemArmor.ArmorMaterial materialSpikedHelmet = EnumHelper.addArmorMaterial("SPIKED_HELMET", Minestuck.MODID+":spiked_diver_helmet", 230, new int[] {0, 0, 0, 6}, 5, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F);
	public static final ItemArmor.ArmorMaterial materialMetal = EnumHelper.addArmorMaterial("METAL", Minestuck.MODID+":metal", 200, new int[] {2, 0, 0, 4}, 5, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F);
	public static final ItemArmor.ArmorMaterial materialRubber = EnumHelper.addArmorMaterial("RUBBER", Minestuck.MODID+":rubber", 240, new int[] {1, 2, 3, 1}, 5, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F);
	public static final ItemArmor.ArmorMaterial materialSunShoes = EnumHelper.addArmorMaterial("SOLAR", Minestuck.MODID+":solar", 240, new int[] {3, 0, 0, 3}, 15, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 0.0F);
	public static final ItemArmor.ArmorMaterial materialWindWalkers = EnumHelper.addArmorMaterial("WIND_WALKERS", Minestuck.MODID+":sun_shoes", 240, new int[] {3, 0, 0, 3}, 20, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F);
	public static final ItemArmor.ArmorMaterial materialCobalt = EnumHelper.addArmorMaterial("COBALT", Minestuck.MODID+":cobalt", 640, new int[] {1, 0, 0, 2}, 20, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.1F);
	public static final ItemArmor.ArmorMaterial materialCloth = EnumHelper.addArmorMaterial("CLOTH", Minestuck.MODID+":cloth", -1, new int[] {0, 0, 0, 0}, 5, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F);

	//Tool Classes
	public static final MSToolClass toolSword = new MSToolClass("sword", Material.WEB).addEnchantments(EnumEnchantmentType.WEAPON);
	public static final MSToolClass toolGauntlet = new MSToolClass("gauntlet", Material.GLASS, Material.ICE, Material.PACKED_ICE).addEnchantments(Enchantments.SILK_TOUCH, Enchantments.FIRE_ASPECT, Enchantments.LOOTING, MSUEnchantments.SUPERPUNCH);
	public static final MSToolClass toolNeedles = new MSToolClass("needle", Material.CLOTH).addEnchantments(EnumEnchantmentType.WEAPON);
	public static final MSToolClass toolHammer = new MSToolClass("pickaxe", "pickaxe").addEnchantments(EnumEnchantmentType.WEAPON, EnumEnchantmentType.DIGGER);
	public static final MSToolClass toolClub = new MSToolClass("club").addEnchantments(EnumEnchantmentType.WEAPON);
	public static final MSToolClass toolClaws = new MSToolClass("claws", Material.PLANTS, Material.WEB).addEnchantments(EnumEnchantmentType.WEAPON);
	public static final MSToolClass toolCane = new MSToolClass("cane").addEnchantments(EnumEnchantmentType.WEAPON);
	public static final MSToolClass toolSickle = new MSToolClass("sickle", Material.GRASS, Material.PLANTS, Material.LEAVES).addEnchantments(EnumEnchantmentType.WEAPON);
	public static final MSToolClass toolSpoon = new MSToolClass("spoon", Material.GOURD).addEnchantments(EnumEnchantmentType.WEAPON);
	public static final MSToolClass toolFork = new MSToolClass("fork", Material.GRASS).addEnchantments(EnumEnchantmentType.WEAPON);

	public static final MSToolClass toolShovel = new MSToolClass("shovel", "shovel").addEnchantments(EnumEnchantmentType.DIGGER);
	public static final MSToolClass toolAxe = new MSToolClass("axe", "axe").addEnchantments(EnumEnchantmentType.WEAPON, EnumEnchantmentType.DIGGER).setDisablesShield();
	public static final MSToolClass toolPickaxe = new MSToolClass("pickaxe", "pickaxe").addEnchantments(EnumEnchantmentType.DIGGER);

	public static final MSToolClass toolSpork = new MSToolClass("spork", toolSpoon, toolFork);
	public static final MSToolClass toolHammaxe = new MSToolClass("hammaxe", toolHammer, toolAxe);

	//Block Swap Property Maps
	public static final BlockMetaPair.Map overgrowthTransforms = new BlockMetaPair.Map();

	//Material tools
	public static final Item emeraldSword = new ItemWeapon(1220, 8, -2.4, 12, "emeraldSword");
	public static final Item emeraldAxe = new ItemWeapon(1220, 8, -2.4, 12, "emeraldAxe");  //TODO turn into actual tools
	public static final Item emeraldPickaxe = new ItemWeapon(1220, 8, -2.4, 12, "emeraldPickaxe");
	public static final Item emeraldShovel = new ItemWeapon(1220, 8, -2.4, 12, "emeraldShovel");
	public static final Item emeraldHoe = new ItemWeapon(1220, 8, -2.4, 12, "emeraldHoe");
	//Armor
	public static final Item prismarineHelmet = new MSArmorBase("prismarineHelmet", armorPrismarine, EntityEquipmentSlot.HEAD, armorPrismarine.getDurability(EntityEquipmentSlot.HEAD), new ResourceLocation(Minestuck.MODID, "prismarine_layer_1"));
	public static final Item prismarineChestplate = new MSArmorBase("prismarineChestplate", armorPrismarine, EntityEquipmentSlot.CHEST, armorPrismarine.getDurability(EntityEquipmentSlot.CHEST), new ResourceLocation(Minestuck.MODID, "prismarine_layer_1"));
	public static final Item prismarineLeggings = new MSArmorBase("prismarineLeggings", armorPrismarine, EntityEquipmentSlot.LEGS, armorPrismarine.getDurability(EntityEquipmentSlot.LEGS), new ResourceLocation(Minestuck.MODID, "prismarine_layer_2"));
	public static final Item prismarineBoots = new MSArmorBase("prismarineBoots", armorPrismarine, EntityEquipmentSlot.FEET, armorPrismarine.getDurability(EntityEquipmentSlot.FEET), new ResourceLocation(Minestuck.MODID, "prismarine_layer_1"));
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

	public static final ArrayList<Item> cruxiteArtifacts = new ArrayList<>();
	public static final Item cruxiteApple = new ItemCruxiteFood("cruxiteApple", 0, 0, true);
	public static final Item cruxitePotion = new ItemCruxiteFood("cruxitePotion", 0, 0, EnumAction.DRINK, true);
	public static final Item cruxitePopTart = new ItemCruxiteFood("cruxitePopTart", 0, 0, true);
	public static final Item cruxitePickaxe = new ItemCruxiteTool("cruxitePickaxe", "pickaxe", 1.0F, -2.8F, 7.0f, 3, true);
	public static final Item cruxiteAxe = new ItemCruxiteTool("cruxiteAxe", "axe", 5.0f, -3.2F, 7.0f, 3, true);
	public static final Item cruxiteShovel = new ItemCruxiteTool("cruxiteShovel", "shovel", 1.5F, -3.0F, 7.0F, 3, true);
	public static final Item cruxiteHoe = new ItemCruxiteHoe("cruxiteHoe", true);
	public static final Item cruxiteSword = new ItemCruxiteWeapon("cruxiteSword", "", 4.0f, -2.4000000953674316f, 0f, 3, true);
	public static final Item cruxiteHammer = new ItemCruxiteWeapon("cruxiteHammer", "pickaxe", 5.0f, -2.45f, 7.0f, 4, true);
	public static final Item cruxiteClub = new ItemCruxiteWeapon("cruxiteClub", "club", 4.0F, -2.2F, 7.0f, 5, true);
	public static final Item cruxiteBattleaxe = new ItemCruxiteWeapon("cruxiteBattleaxe", "axe", 7.0F, -3.2F, 7.0f, 2, true);
	public static final Item cruxiteHelmet = new ItemCruxiteArmor("cruxiteHelmet", EntityEquipmentSlot.HEAD, true);
	public static final Item cruxiteChestplate = new ItemCruxiteArmor("cruxiteChestplate", EntityEquipmentSlot.CHEST, true);
	public static final Item cruxiteLeggings = new ItemCruxiteArmor("cruxiteLeggings", EntityEquipmentSlot.LEGS, true);
	public static final Item cruxiteBoots = new ItemCruxiteArmor("cruxiteBoots", EntityEquipmentSlot.FEET, true);

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
    public static final Item stoneEyeballs = new MSItemBase("stoneEyeballs");
	public static final Item stoneTablet = new ItemStoneTablet();
	public static final Item glowystoneDust = new ItemGlowystoneDust().setUnlocalizedName("glowystoneDust");
	public static final Item fakeArms = new MSItemBase("fakeArms").setCreativeTab(null);
	//Music disks
	public static final Item recordEmissaryOfDance = new ItemMinestuckRecord("emissary", MinestuckSoundHandler.soundEmissaryOfDance).setUnlocalizedName("record");
	public static final Item recordDanceStab = new ItemMinestuckRecord("danceStab", MinestuckSoundHandler.soundDanceStabDance).setUnlocalizedName("record");
	public static final Item recordRetroBattle = new ItemMinestuckRecord("retroBattle",MinestuckSoundHandler.soundRetroBattleTheme).setUnlocalizedName("record");

	public static final Item stackModus = new ItemModus("stackModus");
	public static final Item queueModus = new ItemModus("queueModus");
	public static final Item queueStackModus = new ItemModus("queueStackModus");
	public static final Item treeModus = new ItemModus("treeModus");
	public static final Item hashmapModus = new ItemModus("hashmapModus");
	public static final Item setModus = new ItemModus("setModus");
	public static final Item wildMagicModus = new ItemModus("wildMagicModus");
	public static final Item weightModus = new ItemModus("weightModus");
	public static final Item bookModus = new ItemModus("bookModus");
	public static final Item capitalistModus = new ItemModus("capitalistModus");
	public static final Item modUs = new ItemModus("modUs");
	public static final Item operandiModus = new ItemModus("operandiModus");
	public static final Item onionModus = new ItemModus("onionModus");
	public static final Item slimeModus = new ItemModus("slimeModus");
	public static final Item popTartModus = new ItemModus("popTartModus");
	public static final Item deckModus = new ItemModus("deckModus");
	public static final Item hueModus = new ItemModus("hueModus");
	public static final Item hueStackModus = new ItemModus("hueStackModus");
	public static final Item chatModus = new ItemModus("chatModus");
	public static final Item cycloneModus = new ItemModus("cycloneModus");
	public static final Item energyModus = new ItemModus("energyModus");
	public static final Item scratchAndSniffModus = new ItemModus("scratchAndSniffModus");
	public static final Item eightBallModus = new ItemModus("eightBallModus");
	public static final Item chasityModus = new ItemModus("chasityModus");
	public static final Item jujuModus = new ItemModus("jujuModus");
	public static final Item alcheModus = new ItemModus("alchemodus");
	public static final Item arrayModus = new ItemModus("arrayModus");
	public static final Item monsterModus = new ItemModus("monsterModus");
	public static final Item walletModus = new ItemModus("walletModus");
	public static final Item crystalBallModus = new ItemModus("crystalBallModus");
	public static final Item hashchatModus = new ItemModus("hashchatModus");
	public static final Item sacrificeModus = new ItemModus("sacrificeModus");
	/*
	public static final Item memoryModus = new ItemModus("memoryModus");
	public static final Item recipeModus = new ItemModus("recipeModus");
	public static final Item bottledMsgModus = new ItemModus("messageInABottleModus");
	public static final Item techHopModus = new ItemModus("techHopModus");
	public static final Item encryptionModus = new ItemModus("encryptionModus");
	public static final Item ouijaModus = new ItemModus("ouijaModus");
	public static final Item bundleModus = new ItemModus("bundleModus");
	public static final Item cakeModus = new ItemModus("cakeModus");
	public static final Item cipherModus = new ItemModus("cipherModus");
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

	public static final Item operandiPickaxe = new ItemCruxiteTool("operandiPickaxe", "pickaxe", 1.0F, -2.8F, 7.0f, 3, false);
	public static final Item operandiAxe = new ItemCruxiteTool("operandiAxe", "axe", 5.0f, -3.2F, 7.0f, 3, false);
	public static final Item operandiShovel = new ItemCruxiteTool("operandiShovel", "shovel", 1.5F, -3.0F, 7.0F, 3, false);
	public static final Item operandiHoe = new ItemCruxiteHoe("operandiHoe", false);
	public static final Item operandiSword = new ItemCruxiteWeapon("operandiSword", "", 4.0f, -2.4000000953674316f, 0f, 3, false);
	public static final Item operandiHammer = new ItemCruxiteWeapon("operandiHammer", "pickaxe", 5.0f, -2.45f, 7.0f, 4, false);
	public static final Item operandiClub = new ItemCruxiteWeapon("operandiClub", "club", 4.0F, -2.2F, 7.0f, 5, false);
	public static final Item operandiBattleaxe = new ItemCruxiteWeapon("operandiBattleaxe", "axe", 7.0F, -3.2F, 7.0f, 2, false);
	public static final Item operandiApple = new ItemCruxiteFood("operandiApple", 4, 0.15F, false);
	public static final Item operandiPotion = new ItemCruxiteFood("operandiPotion", 1, 0.4f, EnumAction.DRINK, false);
	public static final Item operandiPopTart = new ItemCruxiteFood("operandiPopTart", 3, 0, false);
	public static final Item operandiEightBall = new ItemOperandiThrowable("operandiEightBall", 0, 1.5f, MinestuckSounds.eightBallThrow);
	public static final Item operandiSplashPotion = new ItemOperandiThrowable("operandiSplashPotion", -20, 0.5f, SoundEvents.ENTITY_SPLASH_POTION_THROW);
	public static final Item operandiHelmet = new ItemCruxiteArmor("operandiHelmet", EntityEquipmentSlot.HEAD, false);
	public static final Item operandiChestplate = new ItemCruxiteArmor("operandiChestplate", EntityEquipmentSlot.CHEST, false);
	public static final Item operandiLeggings = new ItemCruxiteArmor("operandiLeggings", EntityEquipmentSlot.LEGS, false);
	public static final Item operandiBoots = new ItemCruxiteArmor("operandiBoots", EntityEquipmentSlot.FEET, false);
	public static final Item operandiBlock = new ItemOperandiBlock("operandiBlock", MinestuckBlocks.operandiBlock);
	public static final Item operandiStone = new ItemOperandiBlock("operandiStone", MinestuckBlocks.operandiStone);
	public static final Item operandiLog = new ItemOperandiBlock("operandiLog", MinestuckBlocks.operandiLog);
	public static final Item operandiGlass = new ItemOperandiBlock("operandiGlass", MinestuckBlocks.operandiGlass);

	public static final Item hardStone = new MSItemBlock(MinestuckBlocks.hardStone);
	public static final Item walletEntityItem = new ItemWalletEntity("walletEntity");

	public static final Item spaceSalt = new ItemSpaceSalt();
	public static final Item timetable = new ItemTimetable();
	public static final Item moonstone = new MSItemBase("moonstone");
	public static final Item moonstoneChisel = new ItemChisel("moonstone", 31);
	public static final Item zillystoneShard = new MSItemBase("zillystoneShard");
	public static final Item fluorite = new MSItemBase("fluorite");
	public static final Item battery = new MSItemBase("battery");
	public static final Item strifeCard = new ItemStrifeCard("strifeCard");
	public static final Item dungeonKey = new MSItemBase("dungeonKey");
	public static final Item laserPointer = new ItemBeamWeapon(-1, 0, 0, 0.01f, 0, 1, 1, "laserPointer").addProperties(new PropertyPotionBeam(new PotionEffect(MobEffects.BLINDNESS, 30, 0, false, false))).setRepairMaterials(new ItemStack(battery)).setCreativeTab(MinestuckTabs.minestuck);
	public static final Item whip = new ItemSound("whip", MinestuckSoundHandler.whipCrack);
	public static final Item sbahjWhip = new ItemSound("whipSbahj", MinestuckSoundHandler.whipCrock).setSecret();
	public static final Item unrealAir = new ItemUnrealAir("unrealAir");

	public static final Item tickingStopwatch = new MSItemBase("tickingStopwatch"){{
		addPropertyOverride(new ResourceLocation(Minestuck.MODID, "time"), ((stack, worldIn, entityIn) -> ((System.currentTimeMillis() - Minestuck.startTime)/1000f) % 60));
	}}.setMaxStackSize(1);
	public static final Item cueBall = new MSItemBase("cueBall")
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
	public static final Item ironMedallion = new MSItemBase("ironMedallion").setMaxStackSize(1);
	public static final Item returnMedallion = new ItemWarpMedallion("returnMedallion", ItemWarpMedallion.EnumTeleportType.RETURN, 80);
	public static final Item teleportMedallion = new ItemWarpMedallion("teleportMedallion", ItemWarpMedallion.EnumTeleportType.TRANSPORTALIZER, 80);
	public static final Item skaianMedallion = new ItemWarpMedallion("skaianMedallion", ItemWarpMedallion.EnumTeleportType.SKAIA, 80);

	//Weapons

	//Bladekind
	public static final Item sord = new MSWeaponBase(250, 3, -2.3, 1, "sord").setTool(toolSword, 0, 0).addProperties(new PropertySlippery());
	public static final Item cactusCutlass = new MSWeaponBase(746, 12.7, -2.3, 5, "cactusCutlass").setTool(toolSword, 1, 4);
	public static final Item beefSword = new MSWeaponBase(550, 7.2, -2.3, 4, "beefSword").setTool(toolSword, 1, 2).addProperties(new PropertyEdible(3, 0.3F, 75));
	public static final Item steakSword = new MSWeaponBase(550, 8, -2.3, 4, "steakSword").setTool(toolSword, 1, 2).addProperties(new PropertyEdible(8, 0.8F, 50));
	public static final Item irradiatedSteakSword = new MSWeaponBase(550, 8, -2.3, 3, "irradiatedSteakSword").setTool(toolSword, 1, 3).addProperties(new PropertyEdible(4, 0.4F, 25).setPotionEffect(0.9f, new PotionEffect(MobEffects.WITHER, 100, 1)));
	public static final Item firePoker = new MSWeaponBase(825, 10.5, -2.07, 10, "firePoker").setTool(toolSword, 2, 4).addProperties(new PropertyFire(30, 0.3f, false), new PropertyTipperDamage(0.8f, 1.2f, 1));
	public static final Item hotHandle = new MSWeaponBase(825, 12.0, -2.3, 10, "hotHandle").setTool(toolSword, 3, 3).addProperties(new PropertyFire(15, 1f, true));
	public static final Item royalDeringer = new MSWeaponBase(908, 13.2, -2.3, 14, "royalDeringer")
	{{
		addPropertyOverride(new ResourceLocation(Minestuck.MODID, "broken"), PropertyBreakableItem.getPropertyOverride());
	}}.setTool(toolSword, 3, 6).addProperties(new PropertyBreakableItem());
	public static final Item caledfwlch = new MSWeaponBase(1100, 16.0, -2.3, 16, "caledfwlch")
	{{
		addPropertyOverride(new ResourceLocation(Minestuck.MODID, "broken"), PropertyBreakableItem.getPropertyOverride());
	}}.setTool(toolSword, 4, 8).addProperties(new PropertyTrueDamage(), new PropertyBreakableItem());
	public static final Item caledscratch = new MSWeaponBase(1375, 20.0, -2.07, 20, "caledscratch")
	{{
		addPropertyOverride(new ResourceLocation(Minestuck.MODID, "broken"), PropertyBreakableItem.getPropertyOverride());
	}}.setTool(toolSword, 4, 10).addProperties(new PropertyXpMend(), new PropertyBreakableItem());
	public static final Item doggMachete = new MSWeaponBase(1513, 20.0, -2.3, 10, "doggMachete").setTool(toolSword, 4, 10).addProperties(new PropertyPotion(new PotionEffect(MobEffects.SLOWNESS, 200, 0), false, 0.4f), new PropertyKnockback(0.65f));
	public static final Item scarletRibbitar = new MSWeaponBase(1375, 22.0, -2.415, 18, "scarletRibbitar")
	{{
		addPropertyOverride(new ResourceLocation(Minestuck.MODID, "broken"), PropertyBreakableItem.getPropertyOverride());
	}}.setTool(toolSword, 4, 10).addProperties(new PropertyFire(30, 0.5f, true), new PropertyBreakableItem());
	public static final Item cobaltSabre = new MSWeaponBase(1210, 16.0, -2.07, 20, "cobaltSabre").setTool(toolSword, 4, 8).addProperties(new PropertyFire(8, 0.8f, true), new PropertyGristSetter(GristType.Cobalt));
	public static final Item zillywairCutlass = new MSWeaponBase(3300, 33.6, -2.3, 40, "zillywairCutlass").setTool(toolSword, 5, 10);
	public static final Item regisword = new MSWeaponBase(743, 12.0, -2.07, 8, "regisword").setTool(toolSword, 3, 6);
	public static final Item quantumSabre = new MSWeaponBase(880, 14.4, -2.3, 10, "quantumSabre").setTool(toolSword, 3, 6).addProperties(new PropertyPotion(new PotionEffect(MobEffects.WITHER, 100, 1), false, 0.6f));
	public static final Item shatterBeacon = new MSWeaponBase(1100, 34.0, -2.3, 14, "shatterBeacon").setTool(toolSword, 3, 8).addProperties(new PropertyPotion(false, 0.6f,
																																							   new PotionEffect(MobEffects.SPEED, 300, 0),
																																							   new PotionEffect(MobEffects.HASTE, 300, 0),
																																							   new PotionEffect(MobEffects.RESISTANCE, 300, 0),
																																							   new PotionEffect(MobEffects.JUMP_BOOST, 300, 0),
																																							   new PotionEffect(MobEffects.STRENGTH, 300, 0),
																																							   new PotionEffect(MobEffects.REGENERATION, 300, 1)
	));
	public static final Item claymore = new MSWeaponBase(660, 18.4, -2.76, 5, "claymore").setTool(toolSword, 3, 4);
	public static final Item katana = new MSWeaponBase(650, 8, -2.3, 6, "katana").setTool(toolSword, 1, 2);
	public static final Item unbreakableKatana = (new MSWeaponBase(-1, 24.0D, -2.07D, 20, "unbreakableKatana")).addProperties(new PropertySweep()).setTool(toolSword, 0, 15.0F);
	public static final Item bloodKatana = (new MSWeaponBase(880, 16.0D, -2.07D, 10, "bloodKatana")).addProperties(new PropertySweep(), new PropertyBloodBound()).setTool(toolSword, 0, 15.0F);
	public static final ItemBeamBlade batteryBeamBlade = new ItemBeamBlade(385, 8, -2.3, 30, "batteryBeamBlade").setTool(toolSword, 0, 15.0F);
	public static final ItemBeamBlade[] dyedBeamBlade = new ItemBeamBlade[] {
			new ItemBeamBlade(385, 8, -2.3, 30, "batteryBeamBladeWhite").setTool(toolSword, 0, 15.0F).setColor(EnumDyeColor.WHITE),
			new ItemBeamBlade(385, 8, -2.3, 30, "batteryBeamBladeOrange").setTool(toolSword, 0, 15.0F).setColor(EnumDyeColor.ORANGE),
			new ItemBeamBlade(385, 8, -2.3, 30, "batteryBeamBladeMagenta").setTool(toolSword, 0, 15.0F).setColor(EnumDyeColor.MAGENTA),
			new ItemBeamBlade(385, 8, -2.3, 30, "batteryBeamBladeLightBlue").setTool(toolSword, 0, 15.0F).setColor(EnumDyeColor.LIGHT_BLUE),
			new ItemBeamBlade(385, 8, -2.3, 30, "batteryBeamBladeYellow").setTool(toolSword, 0, 15.0F).setColor(EnumDyeColor.YELLOW),
			new ItemBeamBlade(385, 8, -2.3, 30, "batteryBeamBladeLime").setTool(toolSword, 0, 15.0F).setColor(EnumDyeColor.LIME),
			new ItemBeamBlade(385, 8, -2.3, 30, "batteryBeamBladePink").setTool(toolSword, 0, 15.0F).setColor(EnumDyeColor.PINK),
			new ItemBeamBlade(385, 8, -2.3, 30, "batteryBeamBladeGray").setTool(toolSword, 0, 15.0F).setColor(EnumDyeColor.GRAY),
			new ItemBeamBlade(385, 8, -2.3, 30, "batteryBeamBladeSilver").setTool(toolSword, 0, 15.0F).setColor(EnumDyeColor.SILVER),
			new ItemBeamBlade(385, 8, -2.3, 30, "batteryBeamBladeCyan").setTool(toolSword, 0, 15.0F).setColor(EnumDyeColor.CYAN),
			new ItemBeamBlade(385, 8, -2.3, 30, "batteryBeamBladePurple").setTool(toolSword, 0, 15.0F).setColor(EnumDyeColor.PURPLE),
			new ItemBeamBlade(385, 8, -2.3, 30, "batteryBeamBladeBlue").setTool(toolSword, 0, 15.0F).setColor(EnumDyeColor.BLUE),
			new ItemBeamBlade(385, 8, -2.3, 30, "batteryBeamBladeBrown").setTool(toolSword, 0, 15.0F).setColor(EnumDyeColor.BROWN),
			new ItemBeamBlade(385, 8, -2.3, 30, "batteryBeamBladeGreen").setTool(toolSword, 0, 15.0F).setColor(EnumDyeColor.GREEN),
			new ItemBeamBlade(385, 8, -2.3, 30, "batteryBeamBladeRed").setTool(toolSword, 0, 15.0F).setColor(EnumDyeColor.RED),
			new ItemBeamBlade(385, 8, -2.3, 30, "batteryBeamBladeBlack").setTool(toolSword, 0, 15.0F).setColor(EnumDyeColor.BLACK)};
	public static final Item lightbringer = new MSWeaponBase(1375, 20, -2.07, 32, "lightbringer").setTool(toolSword, 4, 10F).addProperties(new PropertySweep(), new PropertyFire(4, 0.8f, true), new PropertyLuckBasedDamage(0.5f), new PropertyMobTypeDamage(EnumCreatureAttribute.UNDEAD, 3));
	public static final Item cybersword = new MSWeaponBase(1650, 28.8, -2.07, 32, "cybersword").setTool(toolSword, 5, 8F).addProperties(new PropertySweep(), new PropertyShock(15, 8, 0.5f, false), new PropertyLightning(15, 1, true, false), new PropertyLuckBasedDamage(0.1f)).setCreativeTab(null);
	public static final Item crystallineRibbitar = new MSWeaponBase(3300, 16.4, -2.3, 24, "crystallineRibbitar").setTool(toolSword, 4, 6F).addProperties(new PropertySweep());
	public static final Item quantumEntangloporter = new MSWeaponBase(1430, 19.2, -2.3, 9, "quantumEntangloporter").setTool(toolSword, 3, 2F).addProperties(new PropertySweep(), new PropertyTeleBlock(Blocks.CACTUS, 20));
	public static final Item valorsEdge = new MSWeaponBase(1650, 26.4, -2.07, 28, "calamitySword").setTool(toolSword, 4, 4F).addProperties(new PropertySweep(), new PropertyCrowdDamage(0.3f, 3f, 20),
																																						 new PropertyLowHealthBoost(SharedMonsterAttributes.MOVEMENT_SPEED, "Calamity Boost", 1.2, 0.4, 0.4f, 2), new PropertyLowHealthBoost(SharedMonsterAttributes.ATTACK_SPEED, "Calamity Boost", 0.4, 0.2, 0.4f, 2));

	//Gauntletkind
	public static final Item fancyGlove = new MSWeaponBase(200, 1D, 0, 5, "fancyGlove").setTool(toolGauntlet, 0, 1);
	public static final Item spikedGlove = new MSWeaponBase(289, 5.7, -0.48, 8, "spikedGlove").setTool(toolGauntlet, 1, 1.4F);
	public static final Item cobbleBasher = new MSWeaponBase(360, 7.2, -0.66, 4, "cobbleBasher").setTool(toolGauntlet, 1, 4F);
	public static final Item fluoriteGauntlet = new MSWeaponBase(1000, 9, -0.54, 8, "fluoriteGauntlet").setTool(toolGauntlet, 4, 8).addProperties(new PropertyRandomDamage());
	public static final Item goldenGenesisGauntlet = new MSWeaponBase(1440, 23.1, -0.48D, 15, "goldenGenesisGauntlet").setTool(toolGauntlet, 2, 3F);
	public static final Item pogoFist = new MSWeaponBase(600, 7.2D, -0.6, 8, "pogoFist").setTool(toolGauntlet, 2, 4F).addProperties(new PropertyPogo(0.55D));
	public static final Item rocketFist = new MSWeaponBase(360, 7.2D, -0.54, 6, "rocketFist").setTool(toolGauntlet, 2, 4F).addProperties(new PropertyRocketDash(10, 15, 0.4f, 3));
	public static final Item jawbreaker = new MSWeaponBase(660, 6.5, -0.6D, 6, "jawbreaker").setTool(toolGauntlet, 3, 1.6F).addProperties(new PropertyCandyWeapon());
	public static final Item eldrichGauntlet = new MSWeaponBase(1200, 9.6, -0.72, 6, "eldrichGauntlet").setTool(toolGauntlet, 3, 6).addProperties(new PropertyEldrichBoost());
	public static final Item gauntletOfZillywenn = new MSWeaponBase(2400, 24.5, -0.78, 14, "gauntletOfZillywenn").setTool(toolGauntlet, 5, 8F);
	public static final Item gasterBlaster = new ItemWindUpBeam(640, 7.7, -0.66D, 0.05f, 10, 1.3f, 16, 0, 16, "gasterBlaster").setSounds(MinestuckSoundHandler.gasterBlasterCharge, MinestuckSoundHandler.gasterBlasterRelease).setTool(toolGauntlet, 6, 4F)
													 .setTool(toolGauntlet, 4, 8).addProperties(new PropertyPotionBeam(new PotionEffect(MobEffects.WITHER, 100, 0)), new PropertyBeamDeathMessage("sans"));
	public static final Item midasGlove = new MSWeaponBase(800, 8.7D, -0.57D, 14, "midasGlove").setTool(toolGauntlet, 3, 10F).addProperties(new PropertyGristSetter(GristType.Gold));

	//Needlekind
	public static final Item knittingNeedles = new ItemKnittingNeedles(128,1.6, -0.24, 1, "knittingNeedle").setTool(toolNeedles, 2, 1f);
	public static final Item pointySticks = new MSWeaponBase(300, 3.2, -0.3, 10, "pointyStick").setTool(toolNeedles, 1, 1f).addProperties(new PropertyDualWield()).addProperties(new PropertyTipperDamage(0.9f, 1.1f, 0), new PropertyMobTypeDamage(EnumCreatureAttribute.UNDEAD, 2)).setRepairMaterial("plankWood");
	public static final Item boneNeedles = new MSWeaponBase(225, 3.6, -0.3, 5, "boneNeedle").setTool(toolNeedles, 1, 1f).addProperties(new PropertyTipperDamage(0.7f, 1.25f, 0), new PropertyDualWield()).setRepairMaterial("bone");
	public static final Item drumstickNeedles = new MSWeaponBase(500, 4, -0.3, 5, "drumstickNeedles").setTool(toolNeedles, 2, 1f).addProperties(new PropertyTipperDamage(0.7f, 1.25f, 0), new PropertyDualWield(), new PropertySoundOnHit(SoundEvents.BLOCK_NOTE_BASEDRUM, PITCH_NOTE, ((stack, target, player) -> 3f)), new PropertySoundOnClick(new SoundEvent[] {SoundEvents.BLOCK_NOTE_BASEDRUM}, PITCH_NOTE, ((stack, target, player) -> 3f), true));
	public static final Item dragonBlades = new MSWeaponBase(500, 7.9, -0.36, 4, "dragonBlades").setTool(toolNeedles, 2, 4).addProperties(new PropertyDualWield(), new PropertyTipperDamage(0.8f, 1.3f, 0.1f)).setCreativeTab(null);
	public static final Item litGlitterBeamTransistor = new ItemBeamWeapon(700,5.5, -0.3, 0.1f, 20, 1, 72000, 40, 30, "litGlitterBeamTransistor").setTool(toolNeedles, 2, 4f).addProperties(new PropertyDualWield(), new PropertyTipperDamage(0.9f, 1.1f, 0), new PropertyRainbowBeam(), new PropertyBeamDeathMessage("rainbow"));
	public static final Item needlewands = new ItemBeamWeapon(488,6.5, -0.3, 0.05f, 10, 1, 60, "needlewand").setTool(toolNeedles, 3, 2f).addProperties(new PropertyDualWield(), new PropertyTipperDamage(0.95f, 1.15f, 0), new PropertyMagicBeam(), new PropertyBeamDeathMessage("magic"));
	public static final Item oglogothThorn = new ItemBeamWeapon(666,7.2, -0.3, 0.1f, 30, 0.7f, 5, 20, 80, "oglogothThorn").setTool(toolNeedles, 4, 3f).addProperties(new PropertyDualWield(), new PropertyTipperDamage(0.6f, 1.6f, 0.12f));
	public static final Item echidnaQuills = new MSWeaponBase(17.3, -0.3, 100, "echidnaQuill").setTool(toolNeedles, 10, 5f).addProperties(new PropertyDualWield(), new PropertyTipperDamage(1f, 1.2f, 0));
	public static final Item thistlesOfZillywitch = new MSWeaponBase(800, 9.1, -0.3, 40, "thistlesOfZillywitch").setTool(toolNeedles, 5, 10f).addProperties(new PropertyDualWield(), new PropertyTipperDamage(0.9f, 1.3f, 0));

	//Shieldkind
	public static final Item woodenDoorshield = new MSShieldBase(340, 12, 0.3f, 5, "woodenDoorshield").setRepairMaterial("plankWood");
	public static final Item ironDoorshield = new MSShieldBase(540, 8, 0.4f, 7, "ironDoorshield").setRepairMaterial("ingotIron");
	public static final Item clearShield = new MSShieldBase(180, 20, 0.25f, 5, "clearShield");
	public static final Item bladedShield = new MSShieldBase(300, 7, -1.2, 10, 0.32f, 6, "bladedShield");
	public static final Item shockerShell = new MSShieldBase(480, 14, 0.3f, 8, "shockerShell").setRepairMaterials(new ItemStack(battery)).addProperties(new PropertyElectric(10, 2, 0, false), new PropertyShieldShock(5, 2, 0.1f, 10, 4, 0.8f));
	public static final Item rocketRiotShield = new MSShieldBase(450, 6, 0.35f, 7, "rocketRiotShield").setRepairMaterial("gunpowder").addProperties(new PropertyRocketBoost(0.4f));
	public static final Item ejectorShield = new MSShieldBase(320, 7, 0.3f, 7, "ejectorShield").addProperties(new PropertyShieldEject(4f, 15));
	public static final Item firewall = new MSShieldBase(320, 7, 0.3f, 7, "firewall").addProperties(new PropertyShieldFire(10, 1000, 0.7f, 1f, true));
	public static final Item clarityWard = new MSShieldBase(410, 8, 0.25f, 12, "christopherShield");
	public static final Item obsidianShield = new MSShieldBase(2000, 0, -3, 12, 0.6f, 10, "obsidianShield").addProperties(new PropertyUseOnCooled(), new PropertyVisualParry());
	public static final Item windshield = new MSShieldBase(355, 18, 0.1f, 7, "windshield").addProperties(new PropertyShieldKnockback(2f, true), new PropertyShieldKnockback(0.3f, false));
	public static final Item wallOfThorns = new MSShieldBase(440, 10, 0.5f, 7, "wallOfThorns").addProperties(new PropertyShieldPotion(true, 1f, new PotionEffect(MobEffects.POISON, 400, 1)), new PropertyShieldPotion(false, 0.1f, new PotionEffect(MobEffects.POISON, 100, 0)));
	public static final Item hardRindHarvest = new MSShieldBase(320, 7, 0.4f, 6, "hardRindHarvest").addProperties(new PropertyEdible(2, 0.4f, 5).setPotionEffect(0.1f, new PotionEffect(MobEffects.INSTANT_HEALTH, 1, 0)));
	public static final Item nuclearNeglector = new MSShieldBase(480, 8, 0.15f, 8, "nuclearNeglector").addProperties(new PropertyShieldPotionNegative(new PotionEffect(MobEffects.WITHER, 60, 2)), new PropertyShieldPotion(true, 1, new PotionEffect(MobEffects.WITHER, 600, 2)));
	public static final Item livingShield = new MSShieldBase(465, 6, 0.0f, 8, "livingShield").addProperties(new PropertyShieldHeal(0.6f, true));
	public static final Item perfectAegis = new MSShieldBase(800, 3, 1f, 12, "perfectAegis").addProperties(new PropertyShieldDeflect(1, 5), new PropertyVisualParry());

	//Bowkind
	public static final MSBowBase.IIsArrow REGULAR_ARROWS = stack -> stack.getItem() == Items.ARROW;

	public static final MSBowBase energyBow = (MSBowBase) new MSBowBase(330, 2.1f, 18, 2.1f, 1.1f, 1, true, "energyBow").requireNoAmmo().addProperties(new PropertyLaserArrow());
	public static final MSBowBase infernoShot = (MSBowBase) new MSBowBase(385, 2, 24, 2, 0.9f, 1, true, "infernoShot").addProperties(new PropertyFlamingArrow(10, 0.9f));
	public static final MSBowBase icicleBow = (MSBowBase) new MSBowBase(230, 2, 30, 2, 0.7f, 1, true, "icicleBow").setArrowCheck(REGULAR_ARROWS).addProperties(new PropertyPotionArrow(new PotionEffect(MobEffects.SLOWNESS, 200, 2), 0.8f));
	public static final MSBowBase tempestBow = (MSBowBase) new MSBowBase(540, 2.3f, 16, 3.1f, 1.2f, 1, true, "tempestBow").addProperties(new PropertyHookshot(0.8f, 16, true, true, true), new PropertyLaserArrow());
	public static final MSBowBase shiverburnWing = (MSBowBase) new MSBowBase(390, 2.2f, 27, 2.2f, 0.8f, 1, true, "shiverburnWing").setArrowCheck(REGULAR_ARROWS).addProperties(new PropertyPotionArrow(new PotionEffect(MobEffects.SLOWNESS, 140, 2), 0.8f), new PropertyFlamingArrow(7, 0.9f));
	public static final MSBowBase magneticHookshot = (MSBowBase) new MSBowBase(680, 1f, 32, 4f, 0.8f, 1, true, "magneticHookshot").setArrowCheck(REGULAR_ARROWS).addProperties(new PropertyHookshot(1, 64));
	public static final MSBowBase wormholePiercer = (MSBowBase) new MSBowBase(640, 1, 35, 3.5f, 0.8f, 1, true, "wormholePiercer").setArrowCheck(REGULAR_ARROWS).addProperties(new PropertyTeleArrows(), new PropertyLaserArrow());
	public static final MSBowBase telegravitationalWarper = (MSBowBase) new MSBowBase(640, 3, 28, 2.9f, 0.8f, 1, true, "telegravitationalWarper").setArrowCheck(REGULAR_ARROWS).addProperties(new PropertyHookshot(0.4f, 16, false, false, true), new PropertyLaserArrow(), new PropertyGhostArrow());
	public static final MSBowBase crabbow = new MSBowBase(2048, 7, -1.8, 2.3f, 27, 1.95f, 1, 1, false, "crabbow");
	public static final ItemMechanicalCrossbow mechanicalCrossbow = new ItemMechanicalCrossbow(385, 1,"mechanicalCrossbow");
	public static final MSBowBase sweetBow = (MSBowBase) new MSBowBase(450, 1.8f, 20, 2.1f, 0.9f, 1, true, "sweetBow").addProperties(new PropertyCandyWeapon());
	public static final MSBowBase kingOfThePond = (MSBowBase) new MSBowBase(890, 2, 10, 5, 1.2f, 1, true, "kingOfThePond").addProperties(new PropertyFlamingArrow(20, 0.65f));
	public static final MSBowBase gildedGuidance = (MSBowBase) new MSBowBase(1210, 3.2f, 30, 0.0f, 0, 2, true, "gildedGuidance").requireNoAmmo().addProperties(new PropertyLaserArrow(), new PropertyGuidedArrow());
	public static final MSBowBase bowOfLight = (MSBowBase) new MSBowBase(2050, 4f, 24, 5, 0.3f, 3, true, "bowOfLight").requireNoAmmo().addProperties(new PropertyLaserArrow(), new PropertyArrowNoGravity(), new PropertyFlamingArrow(6, 0.9f), new PropertyPierce(0.1f));
	public static final MSBowBase theChancemaker = (MSBowBase) new MSBowBase(1280, 1f, 16, 3, 1.3f, 2, true, "theChancemaker").addProperties(new PropertyRandomDamage());
	public static final MSBowBase wisdomsPierce = (MSBowBase) new MSBowBase(1560, 3, 22, 4, 0.3f, 2, false, "calamityBow").addProperties(new PropertyPierce(0.4f), new PropertyLowHealthBoost(SharedMonsterAttributes.LUCK, "Calamity Boost", 2, 0.4, 0.5f, 1), new PropertyLowHealthDrawSpeed(0.5f, 0.2f));
	public static final MSBowBase wisdomsHookshot = (MSBowBase) new MSBowBase(1560, 0.5f, 22, 3, 0.3f, 2, true, "calamityHookshot").requireNoAmmo().addProperties(new PropertyHookshot(1, 128), new PropertyArrowNoGravity(), new PropertyLowHealthBoost(SharedMonsterAttributes.LUCK, "Calamity Boost", 2, 0.4, 0.5f, 1)).setCreativeTab(null);

	//Hammerkind
	public static final Item clawHammer = new MSWeaponBase(259, 6.4, -2.52, 1, "clawHammer").setTool(toolHammer, 1, 2).addProperties(new PropertyVMotionDamage(1.6f, 3));
	public static final Item sledgeHammer = new MSWeaponBase(575, 12.8, -2.8, 3, "sledgeHammer").setTool(toolHammer, 2, 4);
	public static final Item blacksmithHammer = new MSWeaponBase(575, 12.8, -2.8, 2, "blacksmithHammer").setTool(toolHammer, 1, 6);
	public static final Item pogoHammer = new MSWeaponBase(863, 19.2, -2.8, 4, "pogoHammer").setTool(toolHammer, 2, 4).addProperties(new PropertyPogo(0.7), new PropertyVMotionDamage(2f, 4));
	public static final Item telescopicSassacrusher = new MSWeaponBase(1610, 51.2, -3.64, 4, "telescopicSassacrusher").setTool(toolHammer, 8, 2).addProperties(new PropertyFarmine(100, 128), new PropertyVMotionDamage(1.8f, 10));
	public static final Item regiHammer = new MSWeaponBase(776, 19.2, -2.52, 4, "regiHammer").setTool(toolHammer, 3, 6);
	public static final Item fearNoAnvil = new MSWeaponBase(1725, 32, -2.66, 8, "fearNoAnvil").setTool(toolHammer, 3, 10).addProperties(new PropertyPotion(false, 0.5f, new PotionEffect(MobEffects.SLOWNESS, 400, 1), new PotionEffect(MobEffects.MINING_FATIGUE, 400, 2)), new PropertyVMotionDamage(1.6f, 3));
	public static final Item meltMasher = new MSWeaponBase(1265, 25.6, -2.8, 4, "meltMasher").setTool(toolHammer, 4, 8).addProperties(new PropertyAutoSmelt(), new PropertyFarmine(8, 5), new PropertyFire(4, 1, false));
	public static final Item zillyhooHammer = new MSWeaponBase(3450, 69.1, -2.94, 40, "zillyhooHammer").setTool(toolHammer, 5, 10);
	public static final Item scarletZillyhoo = new MSWeaponBase(2588, 49.9, -2.8, 60, "scarletZillyhoo").setTool(toolHammer, 5, 10).addProperties(new PropertyFire(10, 0.8f, true));
	public static final Item popamaticVrillyhoo = new MSWeaponBase(3019, 20, -2.66, 20, "popamaticVrillyhoo").setTool(toolHammer, 5, 12).addProperties(new PropertyRandomDamage(0, 7, 4));
	public static final Item mwrthwl = new MSWeaponBase(1725, 32, -2.8, 8, "mwrthwl").setTool(toolHammer, 4, 8).addProperties(new PropertyTrueDamage());
	public static final Item qPHammerAxe = new MSWeaponBase(1290, 28.8, -2.9, 4, "qPHammerAxe").setTool(toolHammaxe, 4, 6).addProperties(new PropertyPogo(0.6), new PropertyFarmine(25, 64));
	public static final Item qFHammerAxe = new MSWeaponBase(1209, 43.2, -3, 8, "qFHammerAxe").setTool(toolHammaxe, 4, 8).setRepairMaterials(new ItemStack(rawUranium)).addProperties(new PropertyPogo(0.7), new PropertyFarmine(25, 32), new PropertyPotion(true, 0.5f, new PotionEffect(MobEffects.WITHER, 200, 2)));
	public static final Item qEHammerAxe = new MSWeaponBase(1725, 38.4, -2.8, 12, "qEHammerAxe").setTool(toolHammer, 4, 6).addProperties(new PropertyPogo(0.8), new PropertyFarmine(25, 8));
	public static final Item dDEHammerAxe = new MSWeaponBase(1724, 38.4, -2.8, 12, "dDEHammerAxe").setTool(toolHammer, 0, 6).addProperties(new PropertyPogo(1), new PropertySoundOnHit(MinestuckSoundHandler.soundScreech, 1F, 1.5F));
	public static final Item loghammer = new MSWeaponBase(776, 12.8, -2.8, 7, "loghammer").setTool(toolHammer, 0, 3.0f).setRepairMaterial("logWood");
	public static final Item overgrownLoghammer = new MSWeaponBase(575, 19.2, -2.8, 7, "overgrownLoghammer").setTool(toolHammer, 0, 3.0f).setRepairMaterial("logWood").addProperties(new PropertyPlantMend());
	public static final Item glowingLoghammer = new MSWeaponBase(906, 19.2, -2.8, 7, "glowingLoghammer").setTool(toolHammer, 0, 3.0f).setRepairMaterials(new ItemStack(MinestuckBlocks.glowingLog)).addProperties(new PropertyPotion(new PotionEffect(MobEffects.GLOWING, 200, 0), false, 1));
	public static final Item midasMallet = new MSWeaponBase(1150, 26.9D, -2.94D, 15, "midasMallet").setTool(toolHammer, 3, 2f).addProperties(new PropertyGristSetter(GristType.Gold), new PropertyVMotionDamage(1.6f, 3));
	public static final Item aaaNailShocker = new MSWeaponBase(776, 19.2, -2.52, 10, "aaaNailShocker").setTool(toolHammer, 2, 3f).setRepairMaterials(new ItemStack(battery)).addProperties(new PropertyElectric(20, 0, 0.7f, true));
	public static final Item highVoltageStormCrusher = new MSWeaponBase(1150, 30.7, -2.8, 18, "highVoltageStormCrusher").setTool(toolHammer, 4, 3.0f).addProperties(new PropertyLightning(8, 1, true, false), new PropertyElectric(60, 8, -1, false), new PropertyVMotionDamage(1.6f, 3));
	public static final Item barrelsWarhammer = new MSWeaponBase(1438, 64, -2.8, 18, "calamityHammer").setTool(toolHammer, 4, 4.0f).addProperties(new PropertyRocketBoost(0.6f), new PropertyLowHealthBoost(SharedMonsterAttributes.ATTACK_DAMAGE, "Calamity Boost", 0.6, 0.1, 0.3f, 2), new PropertyVMotionDamage(1.6f, 3), new PropertyVMotionDamage(1.6f, 3));
	public static final Item stardustSmasher = new MSWeaponBase(1725, 44.8, -2.8, 20, "stardustSmasher").setTool(toolHammer, 20, 8.0f).addProperties(new PropertyMobTypeDamage(EnumCreatureAttribute.ARTHROPOD, 1000), new PropertyVMotionDamage(1.6f, 3));

	//Clawkind
	public static final Item catClaws = new ItemDualClaw(500, 4.0D, 1.0D, -1.5D, -1.0D, 6, "catclaws");
	public static final Item katars = new MSWeaponBase(248, 1.6, -0.65, 2, "katars").setTool(toolClaws, 2, 3).addProperties(new PropertySweep(), new PropertyDualWield());
	public static final Item diamondKatars = new MSWeaponBase(900, 2.5, -0.65, 6, "diamondKatars").setTool(toolClaws, 2, 3).addProperties(new PropertySweep(), new PropertyDualWield());
	public static final Item actionClaws = new ItemDualClaw(608, 3.9D, 0.0D, -0.55D, -0.0D, 6, "actionClaws").setTool(toolClaws, 2, 3).addProperties(new PropertyActionBuff(200, 2.5));
	public static final Item candyCornClaws = new ItemDualClaw(743, 4.8, 0.0D, -0.65D, -0.0D, 6, "candyCornClaws").setTool(toolClaws, 2, 3).addProperties(new PropertyCandyWeapon());
	public static final Item sneakyDaggers = new ItemDualClaw(900, 5.5, 0.0D, -0.61D, -0.0D, 7, "sneakyDaggers").setTool(toolClaws, 2, 3).addProperties(new PropertySneaky(1.2f, 1.1f, 1.6f));
	public static final Item rocketKatars = new MSWeaponBase(585, 6.4, -0.6, 8, "rocketKatars").setTool(toolClaws, 2, 5).setRepairMaterial("gunpowder").addProperties(new PropertySweep(), new PropertyDualWield(), new PropertyRocketDash(3, 20, 0.3f, 2.5f));
	public static final Item blizzardCutters = new ItemDualClaw(810, 6.4, 0,-0.65, 0, 8, "blizzardCutters").setTool(toolClaws, 3, 3).addProperties(new PropertyPotion(new PotionEffect(MobEffects.SLOWNESS, 400, 0), false, 0.4f), new PropertyKnockback(2.2f));
	public static final Item thunderbirdTalons = new MSWeaponBase(1125, 8.8, -0.65, 18, "thunderbirdTalons").setTool(toolClaws, 5, 3).setRepairMaterials(new ItemStack(battery)).addProperties(new PropertySweep(), new PropertyDualWield(), new PropertyShock(10, 3, 0.4f, true), new PropertyKnockback(1.8f));
	public static final Item archmageDaggers = new ItemBeamWeapon(1350, 9.6,-0.68, 0.05f, 15, 1, 60, 10, 18, "archmageDaggers").setTool(toolClaws, 5, 3).addProperties(new PropertySweep(), new PropertyDualWield(), new PropertyMagicBeam(), new PropertyBeamDeathMessage("magic"));
	public static final Item katarsOfZillywhomst = new ItemDualClaw(2700, 15.3, 0,-0.65, 0, 40, "katarsOfZillywhomst").setTool(toolClaws, 5, 10).addProperties(new PropertySweep(), new PropertyDualWield());
	public static final Item bladesOfTheWarrior = new MSWeaponBase(1080, 6.4, -0.65, 24, "christopherClaws").setTool(toolClaws, 4, 3).addProperties(new PropertySweep(), new PropertyDualWield()).setCreativeTab(null);

	//Canekind
	public static final Item staffOfOvergrowth = new MSWeaponBase(1040, 14.4, -2, 20, "staffOfOvergrowth").setTool(toolCane, 3, 2).addProperties(new PropertyBlockSwap(overgrowthTransforms, 1), new PropertyPotion(new PotionEffect(MobEffects.POISON, 400, 1), false, 0.4f));
	public static final Item atomicIrradiator = new MSWeaponBase(900, 18, -2, 20, "atomicIrradiator").setTool(toolCane, 3, 3).addProperties(new PropertyPotion(new PotionEffect(MobEffects.WITHER, 400, 1), true, 0.6f), new PropertyGristSetter(GristType.Uranium));
	public static final Item goldCane = new MSWeaponBase(600, 12, -2, 18, "goldCane").setTool(toolCane, 2, 5).setRepairMaterial("ingotGold");
	public static final Item goldenCuestaff = new MSWeaponBase(1000, 20, -2, 32, "goldenCuestaff").setTool(toolCane, 2, 6);
	public static final Item scepterOfZillywuud = new MSWeaponBase(2400, 43.2, -2, 32, "scepterOfZillywuud").setTool(toolCane, 5, 10);
	public static final Item cane = new MSWeaponBase(200, 4, -2, 1, "cane").setTool(toolCane, 0, 1);
	public static final Item ironCane = new MSWeaponBase(480, 8, -2, 3, "ironCane").setTool(toolCane, 1, 2);
	public static final Item spearCane = new MSWeaponBase(660, 13.2, -2, 3, "spearCane").setTool(toolCane, 2, 3).addProperties(new PropertyTipperDamage(0.9f, 1.1f, 1));
	public static final Item paradisesPortabello = new MSWeaponBase(540, 12, -2, 2, "paradisesPortabello").setTool(toolCane, 2, 2).addProperties(new PropertyEdible(4, 2, 5));
	public static final Item regiCane = new MSWeaponBase(540, 12, -1.8, 5, "regiCane").setTool(toolCane, 3, 4).addProperties(new PropertyTipperDamage(0.8f, 1.1f, 1));
	public static final Item dragonCane = new MSWeaponBase(1000, 24, -2, 7, "dragonCane").setTool(toolCane, 4, 3);
	public static final Item pogoCane = new MSWeaponBase(600, 12, -2, 5, "pogoCane").setTool(toolCane, 2, 4).addProperties(new PropertyPogo(0.6));
	public static final Item upStick = new MSWeaponBase(-1, 6.4, -2, 5, "upStick").setTool(toolCane, 1, 3).addProperties(new PropertyPotion(true, 1, new PotionEffect(MobEffects.WITHER, 20, 2)));

	//Clubkind
	public static final Item rubyContrabat = new MSWeaponBase(1200, 14.1, -1.98, 22, "rubyContrabat").setTool(toolClub, 3, 4.0f).addProperties(new PropertySweep(), new PropertyGristSetter(GristType.Ruby), new PropertyProjectileDeflect(0.5f, 4));
	public static final Item homeRunBat = new MSWeaponBase(3240, 16.9, -3.9, 10, "homeRunBat").setTool(toolClub, 5, 2.0f).addProperties(new PropertySweep(), new PropertyKnockback(15), new PropertySoundOnHit(MinestuckSoundHandler.homeRunBat, 1, 1.2f), new PropertyProjectileDeflect(1, 10));
	public static final Item dynamiteStick = new MSWeaponBase(1050, 17.6, -2.2, 8, "dynamiteStick").setTool(toolClub, 1, 2.0f).addProperties(new PropertySweep(), new PropertyExplode(2.5f, 1, true), new PropertyProjectileDeflect(0.6f, 4));
	public static final Item nightmareMace = new MSWeaponBase(1200, 14.1, -2.09, 8, "nightmareMace").setTool(toolClub, 3, 3.0f).addProperties(new PropertySweep(), new PropertyHungry(3, 4, true), new PropertyProjectileDeflect(0.3f, 8),
																																								new PropertyPotion(new PotionEffect(MobEffects.BLINDNESS, 200, 0), false, 0.2f), new PropertyPotion(new PotionEffect(MobEffects.NAUSEA, 200, 0), false, 0.2f), new PropertyPotion(new PotionEffect(MobEffects.WITHER, 100, 1), true, 0.2f));
	public static final Item cranialEnder = new MSWeaponBase(1800, 17.6, -2.2, 8, "cranialEnder").setTool(toolClub, 5, 2.0f).addProperties(new PropertySweep(), new PropertyExplode(0.5f, 0.2f, true), new PropertyPotion(new PotionEffect(MobEffects.NAUSEA, 100, 0), true, 0.7f), new PropertyProjectileDeflect(0.6f, 3));
	public static final Item badaBat = new MSWeaponBase(8035, 15.9, -3.9, 10, "badaBat").setTool(toolClub, 5, 14.0f).addProperties(new PropertySweep(), new PropertyKnockback(15), new PropertySoundOnHit(MinestuckSoundHandler.homeRunBat, 1, 1.2f), new PropertySoundOnClick(MinestuckSoundHandler.bada, 1, 1.2f), new PropertyProjectileDeflect(1, 10)).setCreativeTab(null);
	public static final Item deuceClub = new MSWeaponBase(270, 3.5, -2.2, 1, "deuceClub").setTool(toolClub, 0, 1).addProperties(new PropertyProjectileDeflect(0.1f, 1));
	public static final Item metalBat = new MSWeaponBase(720, 7.1, -2.42, 4, "metalBat").setTool(toolClub, 2, 4).addProperties(new PropertyProjectileDeflect(0.25f, 2));
	public static final Item pogoClub = new MSWeaponBase(900, 10, -2.2, 6, "pogoClub").setTool(toolClub, 2, 6).addProperties(new PropertyProjectileDeflect(0.4f, 1.5f), new PropertyPogo(0.5f));
	public static final Item spikedClub = new MSWeaponBase(900, 11.1, -2.2, 6, "spikedClub").setTool(toolClub, 3, 3).addProperties(new PropertyProjectileDeflect(0.15f, 1.5f));
	public static final Item nightClub = new MSWeaponBase(1320, 11.2, -2.2, 6, "nightClub").setTool(toolClub, 3, 3).addProperties(new PropertyProjectileDeflect(0.2f, 3), new PropertyDaytimeDamage(false, 1.4f));

	//Dicekind TODO
	public static final Item dice = new MSItemBase("dice").setCreativeTab(MinestuckTabs.minestuck);
	public static final Item fluoriteOctet = new MSItemBase("fluoriteOctet").setCreativeTab(MinestuckTabs.minestuck);

	//Throwkind
	public static final MSThrowableBase yarnBall = new ItemYarnBall("yarnBall");
	public static final MSThrowableBase wizardbeardYarn = new MSThrowableBase("wizardbeardYarn").addProperties(new PropertyMagicDamagePrjctle(6));
	public static final Item dragonCharge = new ItemDragonCharge("dragonCharge");
	public static final MSThrowableBase throwingStar = new MSThrowableBase(8, 0, 32, "throwingStar").addProperties(new PropertyDamagePrjctle(4), new PropertyThrowGravity(0.7f), new PropertyBreakableProjectile(0.7f));
	public static final MSThrowableBase goldenStar = new MSThrowableBase(4, 0, 64, "goldenStar").addProperties(new PropertyDamagePrjctle(2), new PropertyThrowGravity(0.4f), new PropertyBreakableProjectile(0.9f));
	public static final MSThrowableBase suitarang = new MSThrowableBase(8, 0, 32, "suitarang").addProperties(new PropertyDamagePrjctle(6), new PropertyThrowGravity(0.7f), new PropertyBreakableProjectile(0.5f), new PropertyVariableItem(4));
	public static final MSThrowableBase psionicStar = new MSThrowableBase(10, 0, 16, "psionicStar").setSize(3).addProperties(new PropertyDamagePrjctle(12, true), new PropertyThrowGravity(0.7f), new PropertyBreakableProjectile(0.2f));
	public static final MSThrowableBase boomerang = new MSThrowableBase(10, 0, 1, 1f, 2, -0.5, "boomerang"){{setMaxDamage(64);}}.addProperties(new PropertyDamagePrjctle(5), new PropertyThrowGravity(0.6f), new PropertyBoomerang());
	public static final MSThrowableBase markedBoomerang = new MSThrowableBase(10, 0, 1, 1f, 2, -0.5, "markedBoomerang"){{setMaxDamage(64);}}.addProperties(new PropertyDamagePrjctle(5), new PropertyThrowGravity(0.6f), new PropertyBoomerang());
	public static final MSThrowableBase redHotRang = new MSThrowableBase(12, 0, 1, 1f, 4, -0.5, "redHotRang"){{setMaxDamage(80);}}.addProperties(new PropertyDamagePrjctle(7), new PropertyThrowGravity(0.6f), new PropertyBoomerang(), new PropertyFirePrjctle(5, false));
	public static final MSThrowableBase tornadoGlaive = new MSThrowableBase(8, 0, 1, 1f, 6, -1f, "tornadoGlaive"){{setMaxDamage(550);}}.setSize(2).addProperties(new PropertyDamagePrjctle(8), new PropertyPrjctleItemPull(16, 0.5f), new PropertyBoomerang(), new PropertyThrowGravity(0.4f));
	public static final MSThrowableBase hotPotato = new MSThrowableBase(0, 5, 16, "hotPotato").addProperties(new PropertyDamagePrjctle(10), new PropertyFirePrjctle(10, true));

	//Rockkind
	public static final MSThrowableBase pebble = new MSThrowableBase(0, 0, 16, 1.4f, 0, 0, "pebble").addProperties(new PropertyDamagePrjctle(2), new PropertyThrowGravity(1.5f));
	public static final MSThrowableBase rock = new MSThrowableBase(10, 5, 16, 1.2f, 5, -2.7, "rock").addProperties(new PropertyDamagePrjctle(8), new PropertyThrowGravity(2.5f));
	public static final Item bigRock = new ItemBigRock("bigRock");

	//Sicklekind
	public static final Item sickle = new MSWeaponBase(275, 3.6, -2.4, 1, "sickle").setTool(toolSickle, 1, 2).addProperties(new PropertySweep(2), new PropertyFarmine(1, 500));
	public static final Item homesSmellYaLater = new MSWeaponBase(990, 11.9, -2.4, 10, "homesSmellYaLater").setTool(toolSickle, 2, 3).addProperties(new PropertySweep(3f), new PropertyFarmine(2, 500));
	public static final Item fudgeSickle = new MSWeaponBase(880, 17.3, -2.64, 6, "fudgeSickle").setTool(toolSickle, 2, 2).addProperties(new PropertySweep(2.5f), new PropertyEdible(6, 1, 10));
	public static final Item candySickle = new MSWeaponBase(908, 10.8, -2.4, 5, "candySickle").setTool(toolSickle, 3, 4).addProperties(new PropertySweep(3), new PropertyFarmine(2, 500), new PropertyCandyWeapon());
	public static final Item regiSickle = new MSWeaponBase(743, 10.8, -2.16, 8, "regiSickle").setTool(toolSickle, 3, 4).addProperties(new PropertySweep(3), new PropertyFarmine(3, 500));
	public static final Item clawSickle = new MSWeaponBase(1375, 23.5, -2.64, 8, "clawSickle").setTool(toolSickle, 3, 3).addProperties(new PropertySweep(5), new PropertyFarmine(2, 500));
	public static final Item clawOfNrubyiglith = new MSWeaponBase(1650, 23.5, -2.4, 12, "clawOfNrubyiglith").setTool(toolSickle, 4, 3).addProperties(new PropertySweep(3.5f), new PropertyWhisperingTerror(0.15f));
	public static final Item hereticusAurum = new MSWeaponBase(110, 15.9, -2.16, 32, "hereticusAurum").setTool(toolSickle, 5, 4).addProperties(new PropertySweep(3), new PropertyFarmine(5, 500));

	//Spoonkind
	public static final Item woodenSpoon = new MSWeaponBase(300, 3.2, -2, 1, "woodenSpoon").setTool(toolSpoon, 0, 2).addProperties(new PropertyHungerSpeed(1.2f));
	public static final Item silverSpoon = new MSWeaponBase(600, 6.4, -1.88, 8, "silverSpoon").setTool(toolSpoon, 2, 4).addProperties(new PropertyHungerSpeed(1.1f));
    public static final Item crockerSpoon = new MSWeaponBase(900, 9.6, -2.2, 6, "crockerSpoon").setTool(toolSpoon, 4, 8).addProperties(new PropertyHungerSpeed(1.2f));
	public static final Item fancySpoon = new MSWeaponBase(1200, 4.5, -2, 4, "fancySpoon").setTool(toolSpoon, 3, 3).addProperties(new PropertyHungerSpeed(1.2f));

	//Forkkind
	public static final Item fork = new MSWeaponBase(225, 3.9, -2.2, 1, "fork").setTool(toolFork, 0, 2).addProperties(new PropertyHungerSpeed(1.2f));
	public static final Item skaiaFork = new MSWeaponBase(1080, 18.3, -2.42, 10, "skaiaFork").setTool(toolFork, 3, 6).addProperties(new PropertyTipperDamage(0.6f, 1.3f, 0.8f)).addProperties(new PropertyHungerSpeed(1.25f));
	public static final Item crockerFork = new MSWeaponBase(600, 11.5, -2, 6, "crockerFork").setTool(toolFork, 4, 8).addProperties(new PropertyTipperDamage(0.6f, 1.3f, 0.8f), new PropertyHungerSpeed(1.2f)).setCreativeTab(null);
	public static final Item quartzFork = new MSWeaponBase(405, 9.1, -2, 4, "quartzFork").setTool(toolFork, 3, 3).addProperties(new PropertyTipperDamage(0.6f, 1.3f, 0.8f), new PropertyHungerSpeed(1.2f));

	//Spoon|Forkkind
	public static final Item spork = new MSWeaponBase(525, 7.1, -2.2, 8, "spork").setTool(toolSpork, 2, 4).addProperties(new PropertyHungerSpeed(1.2f));
	public static final Item goldenSpork = new MSWeaponBase(788, 9.5, -1.98, 8, "goldenSpork").setTool(toolSpork, 1, 8).addProperties(new PropertyHungerSpeed(1.3f));

	//Axekind
	public static final Item copseCrusher = new MSWeaponBase(500, 16, -2.85, 2, "copseCrusher").setTool(toolAxe, 4, 4).addProperties(new PropertyFarmine(100, 64));
	public static final Item battleaxe = new MSWeaponBase(600, 32, -3.15, 5, "battleaxe").setTool(toolAxe, 3, 2);
	public static final Item batleacks = new MSWeaponBase(750, 24, -3, 1, "batleacks").setTool(toolAxe, 2, 2).addProperties(new PropertySlippery());
	public static final Item blacksmithBane = new MSWeaponBase(750, 26.4, -3.03, 8, "blacksmithBane").setTool(toolAxe, 3, 3);
	public static final Item scraxe = new MSWeaponBase(675, 24, -3, 7, "scraxe").setTool(toolAxe, 3, 5).addProperties(new PropertySoundOnHit(SoundEvents.BLOCK_NOTE_GUITAR, PITCH_NOTE, ((stack, target, player) -> 3f)), new PropertySoundOnClick(new SoundEvent[]{SoundEvents.BLOCK_NOTE_GUITAR}, PITCH_NOTE, ((stack, target, player) -> 3f), false));
	public static final Item hephaestusLumber = new MSWeaponBase(750, 28, -3, 9, "hephaestusLumber").setTool(toolAxe, 4, 3).addProperties(new PropertyAutoSmelt(), new PropertyFarmine(100, 32), new PropertyFire(20, 1, true), new PropertyFire(2, 1, false));
	public static final Item rubyCroak = new MSWeaponBase(1000, 32, -2.7, 10, "rubyCroak").setTool(toolAxe, 3, 3).addProperties(new PropertyFire(10, 0.8f, false));
	public static final Item battleaxeOfZillywahoo = new MSWeaponBase(3000, 86.4, -3, 40, "battleaxeOfZillywahoo").setTool(toolAxe, 5, 10);

	//Misc.
	public static final Item diamondSickle = new MSWeaponBase(1650, 5.5, -2.4, 32, "diamondSickle").setTool(toolSickle, 3, 4).addProperties(new PropertySweep(3), new PropertyFarmine(2, 500));
	public static final Item gravediggerShovel = new MSWeaponBase(720, 16, -3, 8, "gravediggerShovel").setTool(toolShovel, 3, 4).addProperties(new PropertyMobTypeDamage(EnumCreatureAttribute.UNDEAD, 3));
	public static final Item battlesporkOfZillywut = new MSWeaponBase(3150, 37.9, -2, 40, "battlesporkOfZillywut").setTool(toolSpork, 5, 10).addProperties(new PropertyHungerSpeed(1.2f));
	public static final Item battlepickOfZillydew = new MSWeaponBase(780, 16, -2.8, 40, "battlepickOfZillydew").setTool(toolPickaxe, 5, 10);
	public static final Item rolledUpPaper = new MSWeaponBase(200, 3, 0, 1, "rolledUpPaper");
	public static final Item yesterdaysNews = new MSWeaponBase(450, 7, 0, 1, "yesterdaysNews").addProperties(new PropertyPotion(new PotionEffect(MobEffects.MINING_FATIGUE, 200, 1), false, 0.5f), new PropertyPotion(new PotionEffect(MobEffects.SLOWNESS, 200, 1), false, 0.5f), new PropertyFire(3, 0.7f, true));

	//Armor
	public static final MSArmorBase diverHelmet = new ItemDiverHelmet(materialDiverHelmet, 0, EntityEquipmentSlot.HEAD, "diverHelmet");
	public static final MSArmorBase spikedHelmet = new MSArmorBase("spikedDiverHelmet", materialSpikedHelmet, EntityEquipmentSlot.HEAD);
	public static final MSArmorBase cruxtruderHat = new MSArmorBase("cruxtruderHelmet", materialMetal, EntityEquipmentSlot.HEAD);
	public static final MSArmorBase frogHat = new MSArmorBase("frogHat", materialCloth, EntityEquipmentSlot.HEAD);
	public static final MSArmorBase wizardHat = new MSArmorBase("wizardHat", materialCloth, EntityEquipmentSlot.HEAD, 40);
	public static final MSArmorBase archmageHat = new MSArmorBase("archmageHat", materialCloth, EntityEquipmentSlot.HEAD, 500);
	public static final MSArmorBase cozySweater = new ItemWitherproofArmor("cozySweater", materialCloth, EntityEquipmentSlot.CHEST, 100);
	public static final MSArmorBase scarf = new ItemScarf("scarf", materialCloth, EntityEquipmentSlot.HEAD);
	public static final MSArmorBase rubberBoots = new MSArmorBase("rubberBoots", materialRubber, EntityEquipmentSlot.FEET);
	public static final MSArmorBase bunnySlippers = new MSArmorBase("bunnySlippers", materialCloth,  EntityEquipmentSlot.FEET);
	public static final MSArmorBase moonShoes = new ItemPogoBoots("moonShoes", 1.1f, materialRubber, 0);
	public static final MSArmorBase sunShoes = new ItemPogoBoots("solarShoes", 1.6f, materialSunShoes, 0).setSolar();
	public static final MSArmorBase rocketBoots = new MSArmorBase("rocketBoots", materialSunShoes, EntityEquipmentSlot.FEET, 850).setRepairMaterial("gunpowder");
	public static final MSArmorBase windWalkers = new MSArmorBase("windWalkers", materialWindWalkers, EntityEquipmentSlot.FEET, 283);
	public static final MSArmorBase airJordans = new MSArmorBase("airJordans", materialRubber, EntityEquipmentSlot.FEET, 230);
	public static final MSArmorBase cobaltJordans = new MSArmorBase("airJordansCobalt", materialCobalt, EntityEquipmentSlot.FEET, 480);
	public static final MSArmorBase crumplyHat = new MSArmorBase("crumplyHat", materialCloth, EntityEquipmentSlot.HEAD);

	//Support
	public static Item splatcraftCruxiteFilter = null;

	@SubscribeEvent
	public static void onMissingRegistries(RegistryEvent.MissingMappings<Item> event)
	{
		List<String> integratedMods = Arrays.asList("minestuck", "minestuckuniverse", "fetchmodiplus", "minestuckgodtier");

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
		IForgeRegistry<Item> registry = event.getRegistry();

		armorPrismarine.repairMaterial = new ItemStack(Items.PRISMARINE_SHARD);
		toolUranium.setRepairItem(new ItemStack(rawUranium));
		ItemWeapon.addToolMaterial("pickaxe", Arrays.asList(Material.IRON, Material.ANVIL, Material.ROCK));
		ItemWeapon.addToolMaterial("axe", Arrays.asList(Material.WOOD, Material.PLANTS, Material.VINE));
		ItemWeapon.addToolMaterial("shovel", Arrays.asList(Material.SNOW, Material.CRAFTED_SNOW, Material.CLAY, Material.GRASS, Material.GROUND, Material.SAND));
		ItemWeapon.addToolMaterial("sword", Arrays.asList(Material.WEB));
		ItemWeapon.addToolMaterial("sickle", Arrays.asList(Material.WEB, Material.LEAVES, Material.PLANTS, Material.VINE));

		((ItemMinestuckSeedFood) strawberryChunk).setPlant(strawberryStem.getDefaultState());

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

		if(Minestuck.isSplatcraftLodaded)
		{
			splatcraftCruxiteFilter = new ItemFilter("cruxiteFilter", "cruxite_filter", false).setCreativeTab(MinestuckTabs.minestuck);
			registry.register(splatcraftCruxiteFilter);
		}

		for (IRegistryBlock block : blocks)
		{
			MSItemBlock itemBlock = block.getItemBlock();
			if (itemBlock != null)
				items.add(itemBlock);
		}

		for (IRegistryItem<Item> item : items)
			item.register(registry);

		for (ItemModus modus : modi)
			OreDictionary.registerOre("modus", modus);

		for (ItemMinestuckRecord record : records)
			OreDictionary.registerOre("record", record);
	}

	private static void registerItemBlock(IForgeRegistry<Item> registry, ItemBlock item)
	{
		item.setRegistryName(item.getBlock().getRegistryName());
		registry.register(item);
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
		overgrowthTransforms.put(Blocks.LOG, 4, MinestuckBlocks.log, 0);
		overgrowthTransforms.put(Blocks.LOG, 8, MinestuckBlocks.log, 8);
		overgrowthTransforms.put(Blocks.LOG, 12, MinestuckBlocks.log, 12);


		// TODO: uncomment
		//if(Minestuck.isMSGTLoaded && fearNoAnvil instanceof MSUWeaponBase)
		//	((MSUWeaponBase)fearNoAnvil).addProperties(new PropertyPotion(true, 0.1f, new PotionEffect(Potion.REGISTRY.getObject(new ResourceLocation("minestuckgodtier", "time_stop")), 20, 0)));

		wisdomsPierce.addProperties(new PropertyInnocuousDouble(wisdomsHookshot, true, false, false));
		wisdomsHookshot.addProperties(new PropertyInnocuousDouble(wisdomsPierce, true, false, false));
		((MSWeaponBase)clarityWard).addProperties(new PropertyInnocuousDouble(bladesOfTheWarrior, true, false, true));
		((MSWeaponBase)bladesOfTheWarrior).addProperties(new PropertyInnocuousDouble(clarityWard, true, true, false));
		((MSWeaponBase)dragonBlades).addProperties(new PropertyInnocuousDouble(Item.REGISTRY.getObject(new ResourceLocation(Minestuck.MODID, "dragon_cane")), false, false, true));
		((MSWeaponBase)crockerFork).addProperties(new PropertyInnocuousDouble(Item.REGISTRY.getObject(new ResourceLocation(Minestuck.MODID, "crocker_spork")), false, false, false));

		if(Minestuck.isBotaniaLoaded)
		{
			overgrowthTransforms.put(ModBlocks.livingrock, 1, ModBlocks.livingrock, 2);
			overgrowthTransforms.put(ModBlocks.livingwood, 1, ModBlocks.livingwood, 2);
			overgrowthTransforms.put(ModBlocks.dreamwood, 1, ModBlocks.dreamwood, 2);
		}

		suitarang.addPropertyOverride(new ResourceLocation(Minestuck.MODID, "variant"),
									  ((stack, worldIn, entityIn) -> ((PropertyVariableItem) suitarang.getProperty(PropertyVariableItem.class, stack)).getPropertyOverride().apply(stack, worldIn, entityIn)));
		valorsEdge.addPropertyOverride(new ResourceLocation(Minestuck.MODID, "awakened"),
									   ((stack, worldIn, entityIn) -> ((PropertyLowHealthBoost)((MSWeaponBase)valorsEdge).getProperty(PropertyLowHealthBoost.class, stack)).getPropertyOverride().apply(stack, worldIn, entityIn)));
		barrelsWarhammer.addPropertyOverride(new ResourceLocation(Minestuck.MODID, "awakened"),
											 ((stack, worldIn, entityIn) -> ((PropertyLowHealthBoost)((MSWeaponBase)barrelsWarhammer).getProperty(PropertyLowHealthBoost.class, stack)).getPropertyOverride().apply(stack, worldIn, entityIn)));
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
