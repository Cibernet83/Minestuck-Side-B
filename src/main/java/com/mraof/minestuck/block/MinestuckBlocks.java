package com.mraof.minestuck.block;

import com.mraof.minestuck.alchemy.GristType;
import com.mraof.minestuck.item.MinestuckTabs;
import com.mraof.minestuck.potions.MinestuckPotions;
import com.mraof.minestuck.util.EnumAspect;
import com.mraof.minestuck.util.IRegistryObject;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.*;

public class MinestuckBlocks
{
	public static final ArrayList<IRegistryBlock> blocks = new ArrayList<>();
	public static final TreeMap<EnumDyeColor, BlockWoolTransportalizer> sleevedTransportalizers = new TreeMap<>();
	public static final List<IBlockState> fluids = new ArrayList<>();
	public static final Map<Block, MSBlockStairs> stairs = new HashMap<>();
	public static final Map<Block, MSBlockSlab> slabs = new HashMap<>();
	public static final Map<Block, MSBlockWall> walls = new HashMap<>();
	public static final Map<EnumAspect, BlockHeroStone> heroStones = new TreeMap<>();
	public static final Map<EnumAspect, BlockHeroStone> chiseledHeroStones = new TreeMap<>();
	public static final Map<EnumAspect, BlockHeroStoneWall> heroStoneWalls = new TreeMap<>();
	public static final Map<EnumAspect, BlockSpectralHeroStone> spectralHeroStones = new TreeMap<>();
	public static final Map<EnumAspect, Block> aspectLogs = new TreeMap<>();
	public static final Map<EnumAspect, Block> aspectPlanks = new TreeMap<>();
	public static final Map<EnumAspect, Block> aspectSaplings = new TreeMap<>();

	//Alchemy
	public static final Block punchDesignix = new BlockPunchDesignix();
	public static final BlockTotemLathe[] totemlathe = BlockTotemLathe.createBlocks();
	public static final BlockAlchemiter[] alchemiter = BlockAlchemiter.createBlocks();
	public static final Block cruxtruder = new BlockCruxtruder();
	public static final Block cruxtruderLid = new BlockCruxtruderLid();
	public static final BlockHolopad holopad = new BlockHolopad();

	public static final Block miniAlchemiter = new BlockMiniAlchemiter();
	public static final Block miniTotemLathe = new BlockMiniTotemLathe();
	public static final Block miniPunchDesignix = new BlockMiniPunchDesignix();
	public static final Block miniCruxtruder = new BlockMiniCruxtruder();

	//Computers
	public static final Block blockSburbComputer = new BlockComputer("sburbComputer", new AxisAlignedBB(1/16D, 0.0D, 1/16D, 15/16D, 3/16D, 15/16D), new AxisAlignedBB(0.5/16D, 0.5D/16, 11.8/16D, 15.5/16D, 9.5/16D, 12.4/16D));
	public static final Block blockLaptop = new BlockComputer("laptop", new AxisAlignedBB(1/32D, 0.0D, 7/32D, 31/32D, 0.5/16D, 24.8/32D), new AxisAlignedBB(0.5/16D, 0.5D/16, 11.8/16D, 15.5/16D, 9.5/16D, 12.4/16D));
	public static final Block blockHubtop = new BlockComputer("hubtop", new AxisAlignedBB(1/32D, 0.0D, 7/32D, 31/32D, 0.5/16D, 24.8/32D), new AxisAlignedBB(0.5/16D, 0.5D/16, 11.8/16D, 15.5/16D, 9.5/16D, 12.4/16D));
	public static final Block blockCrockertop = new BlockComputer("crockertop", new AxisAlignedBB(1/32D, 0.0D, 7/32D, 31/32D, 0.5/16D, 24.8/32D), new AxisAlignedBB(0.5/16D, 0.5D/16, 11.8/16D, 15.5/16D, 9.5/16D, 12.4/16D));
	public static final Block blockLunchtop = new BlockComputer("lunchtop", new AxisAlignedBB(5/16d, 0, 5/16d, 11/16d, 3.5/16d, 10/16d));

	//Machines
	public static final Block modusControlDeck = new BlockModusControlDeck();
	public static final Block uraniumCooker = new BlockUraniumCooker();
	public static final Block gristWidget = new BlockGristWidget();
	public static final BlockMachineChasis machineChasis = new BlockMachineChasis();
	public static final BlockGristHopper gristHopper = new BlockGristHopper();
	public static final BlockAutoWidget autoWidget = new BlockAutoWidget();
	public static final BlockAutoCaptcha autoCaptcha = new BlockAutoCaptcha();
	public static final BlockPorkhollowAtm porkhollowAtm = new BlockPorkhollowAtm();
	public static final BlockBoondollarRegister boondollarRegister = new BlockBoondollarRegister();

	//Transportalizers
	public static final BlockTransportalizer transportalizer = new BlockTransportalizer();
	public static final BlockWoolTransportalizer whiteWoolTransportalizer = new BlockWoolTransportalizer(EnumDyeColor.WHITE);
	public static final BlockWoolTransportalizer orangeWoolTransportalizer = new BlockWoolTransportalizer(EnumDyeColor.ORANGE);
	public static final BlockWoolTransportalizer magentaWoolTransportalizer = new BlockWoolTransportalizer(EnumDyeColor.MAGENTA);
	public static final BlockWoolTransportalizer lightBlueWoolTransportalizer = new BlockWoolTransportalizer(EnumDyeColor.LIGHT_BLUE);
	public static final BlockWoolTransportalizer yellowWoolTransportalizer = new BlockWoolTransportalizer(EnumDyeColor.YELLOW);
	public static final BlockWoolTransportalizer limeWoolTransportalizer = new BlockWoolTransportalizer(EnumDyeColor.LIME);
	public static final BlockWoolTransportalizer pinkWoolTransportalizer = new BlockWoolTransportalizer(EnumDyeColor.PINK);
	public static final BlockWoolTransportalizer grayWoolTransportalizer = new BlockWoolTransportalizer(EnumDyeColor.GRAY);
	public static final BlockWoolTransportalizer silverWoolTransportalizer = new BlockWoolTransportalizer(EnumDyeColor.SILVER);
	public static final BlockWoolTransportalizer cyanWoolTransportalizer = new BlockWoolTransportalizer(EnumDyeColor.CYAN);
	public static final BlockWoolTransportalizer purpleWoolTransportalizer = new BlockWoolTransportalizer(EnumDyeColor.PURPLE);
	public static final BlockWoolTransportalizer blueWoolTransportalizer = new BlockWoolTransportalizer(EnumDyeColor.BLUE);
	public static final BlockWoolTransportalizer brownWoolTransportalizer = new BlockWoolTransportalizer(EnumDyeColor.BROWN);
	public static final BlockWoolTransportalizer greenWoolTransportalizer = new BlockWoolTransportalizer(EnumDyeColor.GREEN);
	public static final BlockWoolTransportalizer redWoolTransportalizer = new BlockWoolTransportalizer(EnumDyeColor.RED);
	public static final BlockWoolTransportalizer blackWoolTransportalizer = new BlockWoolTransportalizer(EnumDyeColor.BLACK);
	public static final BlockCustomTransportalizer rubyRedTransportalizer = new BlockRedTransportalizer();
	public static final BlockCustomTransportalizer goldenTransportalizer = new BlockGoldTransportalizer();
	public static final BlockCustomTransportalizer platinumTransportalizer = new BlockPlatinumTransportalizer();
	public static final BlockParadoxTransportalizer paradoxTransportalizer = new BlockParadoxTransportalizer();


	public static final Block genericObject = new BlockFlamable("genericObject", Material.GOURD, MapColor.LIME, SoundType.WOOD).setHardness(1.0F);
	public static final MSBlockBase magicBlock = new MSBlockBase("magicBlock", Material.CLAY);
	public static final MSBlockBase uniqueObject = new MSBlockBase("uniqueObject", Material.CORAL, MapColor.DIAMOND);
	public static final MSBlockBase artifact = new BlockArtifact("artifact", Material.FIRE, MapColor.PURPLE);

	public static final Block cruxiteBlock = new MSBlockBase("cruxiteBlock", Material.ROCK, MapColor.LIGHT_BLUE).setHarvestLevelChain("pickaxe", 0).setHardness(3.0F);
	public static final Block uraniumBlock = new MSBlockBase("uraniumBlock", Material.ROCK, MapColor.LIME).setHarvestLevelChain("pickaxe", 1).setLightLevel(0.5F).setHardness(3.0F);
	public static final MSBlockBase fluoriteBlock = (MSBlockBase) new MSBlockBase("fluoriteBlock", Material.ROCK, MapColor.LAPIS).setHardness(3.0F).setResistance(5.0F);
	public static final MSBlockBase smoothIron = (MSBlockBase) new MSBlockBase("smoothIron", Material.IRON).setHardness(3.0F);

	public static final MSBlockBase sbahjBedrock = (MSBlockBase) new MSBlockBase("sbahjBedrock", Material.CAKE).setResistance(0F).setHardness(-1F);
	public static final MSBlockBase zillyStone = (MSBlockBase) new MSBlockBase("zillystone", Material.ROCK).setResistance(999F).setHardness(5.5F);

	//Decor
	public static final Block blender = new BlockDecor("blender", SoundType.METAL);
	public static final Block chessboard = new BlockDecor("chessboard");
	public static final Block frogStatueReplica = new BlockDecor("frogStatueReplica");
	public static final BlockDecor wizardStatue = new BlockDecor("wizardStatue");
	public static final MSBlockBase netherReactorCore = (MSBlockBase) new MSBlockBase("netherReactorCore", Material.ROCK).setHardness(30.0F).setResistance(6);
	public static final MSBlockBase sbahjTree = new BlockSbahjTree("kringlefucker");
	public static final BlockLayered layeredSand = new BlockLayered("layeredSand", Blocks.SAND.getDefaultState());
	public static final Block glowystoneWire = new BlockGlowystoneWire("glowystoneWire");

	//Technical
	public static final Block skaiaPortal = new BlockSkaiaPortal(Material.PORTAL).setCreativeTab(null);
	public static final Block blockCruxiteDowel = new BlockCruxtiteDowel();
	public static final Block returnNode = new BlockReturnNode("returnNode");
	public static final Block gate = new BlockGate("gate");
	public static final Block blockGoldSeeds = new BlockGoldSeeds();

	//Logs
	public static final Block petrifiedLog = new BlockPetrifiedLog("logPetrified");
	public static final Block glowingLog = new BlockGlowingLog();
	public static final Block endLog = new BlockEndLog();
	public static final Block deadLog = new MSBlockLog("deadLog");
	public static final Block log = new MSBlockLogVariant("log");

	//Planks
	public static final Block glowingPlanks = new BlockFlamable("glowingPlanks", Material.WOOD, MapColor.LIGHT_BLUE, SoundType.WOOD).setFireInfo(5, 20).setLightLevel(0.5F).setHardness(2.0F).setResistance(5.0F);
	public static final Block rainbowPlanks = new BlockFlamable("rainbowPlanks", Material.WOOD, MapColor.WOOD, SoundType.WOOD).setFireInfo(5, 20).setHardness(2.0F);
	public static final Block frostPlanks = new MSBlockBase("frostPlanks", Material.WOOD).setSoundType(SoundType.WOOD).setHardness(2.0f);
	public static final Block treatedPlanks = slabs(stairs(new BlockFlamable("treatedPlanks", Material.WOOD, MapColor.WOOD, SoundType.WOOD).setFireInfo(1, 0).setHardness(1.0F)));
	public static final Block endPlanks = slabs(stairs(new BlockFlamable("endPlanks", Material.WOOD, MapColor.SAND, SoundType.WOOD).setFireInfo(1, 250).setHardness(1.0F)));
	public static final Block deadPlanks = slabs(stairs(new BlockFlamable("deadPlanks", Material.WOOD, MapColor.WOOD, SoundType.WOOD).setFireInfo(5, 5).setHardness(1.0F)));

	//Leaves
	public static final Block leaves = new MSBlockLeavesVariant();
	public static final Block endLeaves = new BlockEndLeaves();

	//Saplings
	public static final Block rainbowSapling = new BlockRainbowSapling();
	public static final Block endSapling = new BlockEndSapling();

	//Foliage
	public static final Block petrifiedPoppy = new BlockPetrifiedFlora("petrifiedPoppy");
	public static final Block petrifiedGrass = new BlockPetrifiedFlora("petrifiedGrass");
	public static final Block bloomingCactus = new BlockDesertFlora("bloomingCactus");
	public static final Block desertBush = new BlockDesertBush("desertBush");
	public static final Block glowingMushroom = new BlockGlowingMushroom();

	//Terrain Blocks
	public static final Block hardStone = new BlockHardStone("hardStone");
	public static final Block chessTile = new BlockChessTile();
	public static final Block coloredDirt = new BlockColoredDirt();
	public static final Block coarseStone = slabs(stairs(new MSBlockBase("coarseStone").setHardness(2).setResistance(10)));
	public static final Block chiseledCoarseStone = new MSBlockBase("coarseStoneChiseled").setHardness(2).setResistance(10);
	public static final Block shadeBrick = slabs(stairs(new MSBlockBase("shadeBrick", MapColor.BLUE).setHardness(1.5f).setResistance(10)));
	public static final Block shadeBrickSmooth = new MSBlockBase("shadeBrickSmooth", MapColor.BLUE).setHardness(1.5f).setResistance(10);
	public static final Block frostBrick = slabs(stairs(new MSBlockBase("frostBrick", MapColor.ICE).setHardness(1.5f).setResistance(10)));
	public static final Block frostTile = new MSBlockBase("frostTile", MapColor.ICE).setHardness(1.5f).setResistance(10);
	public static final Block chiseledFrostBrick = new MSBlockBase("frostBrickChiseled", MapColor.ICE).setHardness(1.5f).setResistance(10);
	public static final Block castIron = slabs(stairs(new MSBlockBase("castIron", Material.IRON).setHardness(3).setResistance(10)));
	public static final Block chiseledCastIron = new MSBlockBase("castIronChiseled", Material.IRON).setHardness(3).setResistance(10);
	public static final Block blackStone = new MSBlockBase("blackStone", MapColor.BLACK).setHardness(2.5f).setResistance(10);
	public static final Block myceliumBrick = slabs(stairs(new MSBlockBase("myceliumBrick", MapColor.MAGENTA).setHardness(1.5f).setResistance(10)));
	public static final Block endGrass = new BlockEndGrass();
	public static final Block coarseEndStone = new MSBlockBase("coarseEndStone", MapColor.SAND).setHardness(3.0F);
	public static final Block floweryMossStone = new MSBlockBase("floweryMossStone", MapColor.GRAY);
	public static final Block floweryMossBrick = new MSBlockBase("floweryMossBrick", MapColor.GRAY);
	public static final Block chalk = slabs(stairs(new MSBlockBase("chalk", MapColor.SNOW)));
	public static final Block chalkBricks = slabs(stairs(new MSBlockBase("chalkBrick", MapColor.SNOW)));
	public static final Block chalkChisel = new MSBlockBase("chalkBrickChiseled", MapColor.SNOW);
	public static final Block chalkPolish = new MSBlockBase("polishedChalk", MapColor.SNOW);
	public static final Block pinkStoneSmooth = new MSBlockBase("pinkStone", MapColor.PINK);
	public static final Block pinkStoneBricks = slabs(stairs(new MSBlockBase("pinkStoneBrick", MapColor.PINK)));
	public static final Block pinkStoneChisel = new MSBlockBase("pinkStoneChiseled", MapColor.PINK);
	public static final Block pinkStoneCracked = new MSBlockBase("pinkCrackedStone", MapColor.PINK);
	public static final Block pinkStoneMossy = new MSBlockBase("pinkMossStoneBrick", MapColor.PINK);
	public static final Block pinkStonePolish = new MSBlockBase("pinkPolishedStone", MapColor.PINK);
	public static final Block denseCloud = new BlockDenseCloud();
	public static final Block woodenCactus = new BlockCactusSpecial("woodenCactus", SoundType.WOOD, "axe").setHardness(1.0F).setResistance(2.5F);
	public static final Block glowyGoop = new BlockGlowyGoop("glowyGoop");
	public static final Block coagulatedBlood = new BlockGoop("coagulatedBlood");

	//Cakes
	public static final Block sugarCube = new BlockFlamable("sugarCube", Material.SAND, MapColor.SNOW, SoundType.SAND).setHardness(0.4F);
	public static final Block appleCake = new BlockSimpleCake("appleCake", 2, 0.5F, null);
	public static final Block blueCake = new BlockSimpleCake("blueCake", 2, 0.3F, (EntityPlayer player) -> player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 150, 0)));
	public static final Block coldCake = new BlockSimpleCake("coldCake", 2, 0.3F, (EntityPlayer player) ->
	{
		player.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 200, 1));
		player.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 200, 1));
	});
	public static final Block redCake = new BlockSimpleCake("redCake", 2, 0.1F, (EntityPlayer player) -> player.heal(1));
	public static final Block hotCake = new BlockSimpleCake("hotCake", 2, 0.1F, (EntityPlayer player) -> player.setFire(4));
	public static final Block reverseCake = new BlockSimpleCake("reverseCake", 2, 0.1F, null);
	public static final Block fuchsiaCake = new BlockSimpleCake("fuchsiaCake", 3, 0.5F, (EntityPlayer player) ->
	{
		player.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 350, 1));
		player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 200, 0));
	});

	public static final Block strawberry = new BlockStrawberry();
	public static final Block strawberryStem = new MSBlockStem("strawberryStem", (BlockDirectional) strawberry).setCreativeTab(null);

	//Ores
	public static final Block oreCruxite = new BlockCruxiteOre();
	public static final Block oreUranium = new BlockUraniumOre();
	public static final Block coalOreNetherrack = new BlockVanillaOre("coalOreNetherrack", Blocks.COAL_ORE);
	public static final Block coalOrePinkStone = new BlockVanillaOre("coalOrePinkStone", Blocks.COAL_ORE);
	public static final Block ironOreEndStone = new BlockVanillaOre("ironOreEndStone", Blocks.IRON_ORE);
	public static final Block ironOreSandstone = new BlockVanillaOre("ironOreSandstone", Blocks.IRON_ORE);
	public static final Block ironOreSandstoneRed = new BlockVanillaOre("ironOreSandstoneRed", Blocks.IRON_ORE);
	public static final Block goldOreSandstone = new BlockVanillaOre("goldOreSandstone", Blocks.GOLD_ORE);
	public static final Block goldOreSandstoneRed = new BlockVanillaOre("goldOreSandstoneRed", Blocks.GOLD_ORE);
	public static final Block goldOrePinkStone = new BlockVanillaOre("goldOrePinkStone", Blocks.GOLD_ORE);
	public static final Block redstoneOreEndStone = new BlockVanillaOre("redstoneOreEndStone", Blocks.REDSTONE_ORE);
	public static final Block quartzOreStone = new BlockVanillaOre("quartzOreStone", Blocks.QUARTZ_ORE);
	public static final Block lapisOrePinkStone = new BlockVanillaOre("lapisOrePinkStone", Blocks.LAPIS_ORE);
	public static final Block diamondOrePinkStone = new BlockVanillaOre("diamondOrePinkStone", Blocks.DIAMOND_ORE);
	public static final MSBlockBase fluoriteOre = (MSBlockBase) new MSBlockBase("fluoriteOre", Material.ROCK).setHardness(3.0F).setResistance(5.0F);
	public static final MSBlockBase moonstoneOre = (MSBlockBase) new MSBlockBase("moonstoneOre", Material.IRON).setHardness(3.0F).setResistance(5.0F);

	//TNT
	public static final Block primedTnt = new BlockTNTSpecial("primedTnt", true, false, false);
	public static final Block unstableTnt = new BlockTNTSpecial("unstableTnt", false, true, false);
	public static final Block instantTnt = new BlockTNTSpecial("instantTnt", false, false, true);
	public static final Block woodenExplosiveButton = new BlockButtonSpecial("woodenButtonExplosive", true, true);
	public static final Block stoneExplosiveButton = new BlockButtonSpecial("stoneButtonExplosive", false, true);

	public static final MSBlockBase dungeonDoor = new BlockDungeonDoor("dungeonDoor");
	public static final MSBlockBase dungeonDoorKeyhole = new BlockDungeonDoor("dungeonDoorKeyhole");

	//Grist Blocks
	public static final MSBlockBase gristBlockAmber = new BlockGrist(GristType.Amber);
	public static final MSBlockBase gristBlockAmethyst = new BlockGrist(GristType.Amethyst);
	public static final MSBlockBase gristBlockArtifact = new BlockGrist(GristType.Artifact);
	public static final MSBlockBase gristBlockBuild = new BlockGrist(GristType.Build);
	public static final MSBlockBase gristBlockCaulk = new BlockGrist(GristType.Caulk);
	public static final MSBlockBase gristBlockChalk = new BlockGrist(GristType.Chalk);
	public static final MSBlockBase gristBlockCobalt = new BlockGrist(GristType.Cobalt);
	public static final MSBlockBase gristBlockDiamond = new BlockGrist(GristType.Diamond);
	public static final MSBlockBase gristBlockGarnet = new BlockGrist(GristType.Garnet);
	public static final MSBlockBase gristBlockGold = new BlockGrist(GristType.Gold);
	public static final MSBlockBase gristBlockIodine = new BlockGrist(GristType.Iodine);
	public static final MSBlockBase gristBlockMarble = new BlockGrist(GristType.Marble);
	public static final MSBlockBase gristBlockMercury = new BlockGrist(GristType.Mercury);
	public static final MSBlockBase gristBlockQuartz = new BlockGrist(GristType.Quartz);
	public static final MSBlockBase gristBlockRuby = new BlockGrist(GristType.Ruby);
	public static final MSBlockBase gristBlockRust = new BlockGrist(GristType.Rust);
	public static final MSBlockBase gristBlockShale = new BlockGrist(GristType.Shale);
	public static final MSBlockBase gristBlockSulfur = new BlockGrist(GristType.Sulfur);
	public static final MSBlockBase gristBlockTar = new BlockGrist(GristType.Tar);
	public static final MSBlockBase gristBlockUranium = new BlockGrist(GristType.Uranium);
	public static final MSBlockBase gristBlockZillium = new BlockGrist(GristType.Zillium);
	public static final MSBlockBase gristBlockVis = new BlockGrist(GristType.Vis);
	public static final MSBlockBase gristBlockMana = new BlockGrist(GristType.Mana);

	//Effect Beacons
	public static final MSBlockBase dungeonShield = new BlockEffectBeacon("dungeonShield", MapColor.ADOBE, new PotionEffect(MinestuckPotions.CREATIVE_SHOCK, 40, 0));
	public static final MSBlockBase flightBeacon = new BlockEffectBeacon("flightBeacon", MapColor.ADOBE, new PotionEffect(MinestuckPotions.SKYHBOUND, 40, 0));
	public static final MSBlockBase flightInhibitor = new BlockEffectBeacon("flightInhibitor", MapColor.ADOBE, new PotionEffect(MinestuckPotions.EARTHBOUND, 40, 0));
	public static final Block heroLockBeacon = new BlockEffectBeacon("badgeInhibitor", MapColor.IRON, new PotionEffect(MinestuckPotions.GOD_TIER_LOCK, 60, 2));

	//God Tier
	public static final BlockHeroStone wildcardHeroStone = new BlockHeroStone(null, false);
	public static final BlockHeroStone wildcardChiseledHeroStone = new BlockHeroStone(null, true);
	public static final BlockHeroStoneWall wildcardHeroStoneWall = new BlockHeroStoneWall(wildcardHeroStone.getDefaultState());
	public static final BlockSpectralHeroStone wildcardSpectralHeroStone = new BlockSpectralHeroStone(null);
	public static final Block glowingHeroStone = new BlockGlowingHeroStone();

	public static final Block glorb = new BlockGlorb();

	//Lunar Blocks
	public static final Block prospitStone = walls(slabs(stairs(new MSBlockBase("prospitStone", MapColor.YELLOW).setHarvestLevelChain("pickaxe", 3).setHardness(20.0F).setResistance(2000.0F))));
	public static final Block prospitStoneSmooth = slabs(new MSBlockBase("prospitStoneSmooth", MapColor.YELLOW).setHarvestLevelChain("pickaxe", 3).setHardness(20.0F).setResistance(2000.0F));
	public static final Block prospitBrick = walls(slabs(stairs(new MSBlockBase("prospitBrick", MapColor.YELLOW).setHarvestLevelChain("pickaxe", 3).setHardness(20.0F).setResistance(2000.0F))));
	public static final Block prospitBrickCracked = new MSBlockBase("prospitBrickCracked", MapColor.YELLOW).setHarvestLevelChain("pickaxe", 3).setHardness(20.0F).setResistance(2000.0F);
	public static final Block prospitBrickChiseled = new MSBlockBase("prospitBrickChiseled", MapColor.YELLOW).setHarvestLevelChain("pickaxe", 3).setHardness(20.0F).setResistance(2000.0F);
	public static final Block prospitPillar = new MSBlockPillar("prospitPillar", MapColor.YELLOW).setHarvestLevelChain("pickaxe", 3).setHardness(20.0F).setResistance(2000.0F);

	public static final Block derseStone = walls(slabs(stairs(new MSBlockBase("derseStone", MapColor.PURPLE).setHarvestLevelChain("pickaxe", 3).setHardness(20.0F).setResistance(2000.0F))));
	public static final Block derseStoneSmooth = slabs(new MSBlockBase("derseStoneSmooth", MapColor.PURPLE).setHarvestLevelChain("pickaxe", 3).setHardness(20.0F).setResistance(2000.0F));
	public static final Block derseBrick = walls(slabs(stairs(new MSBlockBase("derseBrick", MapColor.PURPLE).setHarvestLevelChain("pickaxe", 3).setHardness(20.0F).setResistance(2000.0F))));
	public static final Block derseBrickCracked = new MSBlockBase("derseBrickCracked", MapColor.PURPLE).setHarvestLevelChain("pickaxe", 3).setHardness(20.0F).setResistance(2000.0F);
	public static final Block derseBrickChiseled = new MSBlockBase("derseBrickChiseled", MapColor.PURPLE).setHarvestLevelChain("pickaxe", 3).setHardness(20.0F).setResistance(2000.0F);
	public static final Block dersePillar = new MSBlockPillar("dersePillar", MapColor.PURPLE).setHarvestLevelChain("pickaxe", 3).setHardness(20.0F).setResistance(2000.0F);

	//Fetch Modi Containers
	public static final Block operandiBlock = new BlockOperandi("operandiBlock", 1.0f, 0, Material.GOURD, "");
	public static final Block operandiGlass = new BlockOperandiGlass("operandiGlass", 0.5f, 0, Material.GLASS, "");
	public static final Block operandiStone = new BlockOperandi("operandiStone", 3.0f, 6.5f, Material.IRON, "pickaxe");
	public static final Block operandiLog = new BlockOperandiLog("operandiLog", 2.0f, 0, BlockOperandi.LOG, "axe");

	//Fluids
	public static final Fluid fluidOil = createFluid("oil", new ResourceLocation("minestuck", "blocks/oil_still"), new ResourceLocation("minestuck", "blocks/oil_flowing"), "tile.oil");
	public static final Fluid fluidBlood = createFluid("blood", new ResourceLocation("minestuck", "blocks/blood_still"), new ResourceLocation("minestuck", "blocks/blood_flowing"), "tile.blood");
	public static final Fluid fluidBrainJuice = createFluid("brain_juice", new ResourceLocation("minestuck", "blocks/brain_juice_still"), new ResourceLocation("minestuck", "blocks/brain_juice_flowing"), "tile.brainJuice");
	public static final Fluid fluidWatercolors = createFluid("watercolors", new ResourceLocation("minestuck", "blocks/watercolors_still"), new ResourceLocation("minestuck", "blocks/watercolors_flowing"), "tile.watercolors");
	public static final Fluid fluidEnder = createFluid("ender", new ResourceLocation("minestuck", "blocks/ender_still"), new ResourceLocation("minestuck", "blocks/ender_flowing"), "tile.ender");
	public static final Fluid fluidLightWater = createFluid("light_water", new ResourceLocation("minestuck", "blocks/light_water_still"), new ResourceLocation("minestuck", "blocks/light_water_flowing"), "tile.lightWater");

	public static final Block blockOil = new MSFluidBase("oil", fluidOil, Material.WATER)
	{
		@SideOnly(Side.CLIENT)
		@Override
		public Vec3d getFogColor(World world, BlockPos pos, IBlockState state, Entity entity, Vec3d originalColor, float partialTicks)
		{
			return new Vec3d(0.0, 0.0, 0.0);
		}
	}.setLightOpacity(2);

	public static final Block blockBlood = new MSFluidBase("blood", fluidBlood, Material.WATER)
	{
		@SideOnly(Side.CLIENT)
		@Override
		public Vec3d getFogColor(World world, BlockPos pos, IBlockState state, Entity entity, Vec3d originalColor, float partialTicks)
		{
			return new Vec3d(0.8, 0.0, 0.0);
		}
	}.setLightOpacity(1);

	public static final Block blockBrainJuice = new MSFluidBase("brainJuice", fluidBrainJuice, Material.WATER)
	{
		@SideOnly(Side.CLIENT)
		@Override
		public Vec3d getFogColor(World world, BlockPos pos, IBlockState state, Entity entity, Vec3d originalColor, float partialTicks)
		{
			return new Vec3d(0.55, 0.25, 0.7);
		}
	}.setLightOpacity(1);

	public static final Block blockWatercolors = new MSFluidBase("watercolors", fluidWatercolors, Material.WATER)
	{
		@SideOnly(Side.CLIENT)
		@Override
		public Vec3d getFogColor(World world, BlockPos pos, IBlockState state, Entity entity, Vec3d originalColor, float partialTicks)
		{
			Vec3d newColor = new Vec3d(0.0, 20.0, 30.0);
			newColor = newColor.rotateYaw((float) (entity.posX / 2.0));
			newColor = newColor.rotatePitch((float) (entity.posZ / 2.0));
			newColor = newColor.rotateYaw((float) (entity.posY));
			newColor = newColor.normalize();
			newColor = new Vec3d(newColor.x % 1.0, newColor.y % 1.0, newColor.z % 1.0);

			return newColor;
		}
	}.setLightOpacity(1);

	public static final Block blockEnder = new BlockFluidEnder("ender", fluidEnder, Material.WATER).setLightOpacity(1);

	public static final Block blockLightWater = new MSFluidBase("lightWater", fluidLightWater, Material.WATER)
	{
		@SideOnly(Side.CLIENT)
		@Override
		public Vec3d getFogColor(World world, BlockPos pos, IBlockState state, Entity entity, Vec3d originalColor, float partialTicks)
		{
			return new Vec3d(0.2, 0.3, 1.0);
		}
	}.setLightOpacity(1);

	//public static final Block[] liquidGrists;
	//public static final Fluid[] gristFluids;

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event)
	{
		slabs(Blocks.BEDROCK);
		stairs(Blocks.BEDROCK);

		for(EnumAspect aspect : EnumAspect.values())
			generateHeroStones(aspect);

		IForgeRegistry<Block> registry = event.getRegistry();
		for (IRegistryObject<Block> block : blocks)
			block.register(registry);
	}

	private static Fluid createFluid(String name, ResourceLocation still, ResourceLocation flowing, String unlocalizedName)
	{
		Fluid fluid = new Fluid(name, still, flowing);

		boolean useFluid = FluidRegistry.registerFluid(fluid);

		if (useFluid)
			fluid.setUnlocalizedName(unlocalizedName);
		else fluid = FluidRegistry.getFluid(name);

		return fluid;
	}

	private static Block stairs(Block block)
	{
		IBlockState blockState = block.getDefaultState();
		String name = blockState.getBlock().getUnlocalizedName().substring(5); // unloc name starts with an extra "tile."
		stairs.put(block, new MSBlockStairs(name + "Stairs", blockState));
		return block;
	}

	private static Block slabs(Block block)
	{
		IBlockState blockState = block.getDefaultState();
		String name = blockState.getBlock().getUnlocalizedName().substring(5); // unloc name starts with an extra "tile."
		slabs.put(block, new MSBlockSlab(name + "Slab", blockState));
		return block;
	}

	private static Block walls(Block block)
	{
		IBlockState blockState = block.getDefaultState();
		String name = blockState.getBlock().getUnlocalizedName().substring(5); // unloc name starts with an extra "tile."
		walls.put(block, new MSBlockWall(name + "Wall", blockState));
		return block;
	}

	private static void generateHeroStones(EnumAspect aspect)
	{
		heroStones.put(aspect, new BlockHeroStone(aspect, false));
		chiseledHeroStones.put(aspect, new BlockHeroStone(aspect, true));
		heroStoneWalls.put(aspect, new BlockHeroStoneWall(heroStones.get(aspect).getDefaultState()));
		spectralHeroStones.put(aspect, new BlockSpectralHeroStone(aspect));

		aspectPlanks.put(aspect, new BlockFlamable("aspectPlanks"+aspect.getName().toLowerCase().replaceFirst(aspect.getName().charAt(0)+"", aspect.getName().toUpperCase().charAt(0)+""),
				Material.WOOD, MapColor.WOOD, SoundType.WOOD).setFireInfo(5, 20).setHardness(2.0F).setCreativeTab(MinestuckTabs.godTier));
		aspectLogs.put(aspect, new BlockFlamable("logAspect"+aspect.getName().toLowerCase().replaceFirst(aspect.getName().charAt(0)+"", aspect.getName().toUpperCase().charAt(0)+""),
				Material.WOOD, MapColor.WOOD, SoundType.WOOD).setFireInfo(5, 20).setHardness(2.0F).setCreativeTab(MinestuckTabs.godTier));
		aspectSaplings.put(aspect, new BlockAspectSapling(aspect));

	}
}