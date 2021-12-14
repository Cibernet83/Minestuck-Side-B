package com.mraof.minestuck.block;

import com.mraof.minestuck.alchemy.GristType;
import com.mraof.minestuck.potions.MSUPotions;
import com.mraof.minestuck.util.IRegistryItem;
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

	//Blocks
	public static final Block chessTile = new BlockChessTile();
	public static final Block skaiaPortal = new BlockSkaiaPortal(Material.PORTAL);

	public static final Block coloredDirt = new BlockColoredDirt();
	public static final Block cruxiteBlock = new MSBlockBase("cruxiteBlock", Material.ROCK, MapColor.LIGHT_BLUE).setHarvestLevelChain("pickaxe", 0).setHardness(3.0F);
	public static final Block uraniumBlock = new MSBlockBase("uraniumBlock", Material.ROCK, MapColor.LIME).setHarvestLevelChain("pickaxe", 1).setLightLevel(0.5F).setHardness(3.0F);
	public static final Block genericObject = new BlockFlamable("genericObject", Material.GOURD, MapColor.LIME, SoundType.WOOD).setHardness(1.0F);

	public static final Block blender = new BlockDecor("blender", SoundType.METAL);
	public static final Block chessboard = new BlockDecor("chessboard");
	public static final Block frogStatueReplica = new BlockDecor("frogStatueReplica");

	public static final Block miniAlchemiter = new BlockMiniAlchemiter();
	public static final Block miniTotemLathe = new BlockMiniTotemLathe();
	public static final Block miniPunchDesignix = new BlockMiniPunchDesignix();
	public static final Block miniCruxtruder = new BlockMiniCruxtruder();
	public static final Block crockerMachine = new BlockCrockerMachine();
	public static final Block blockComputerOff = new BlockComputerOff("computerStandard");
	public static final Block blockComputerOn = new BlockComputerOn();
	public static final Block blockLaptopOff = new BlockVanityLaptopOff("vanityLaptop").setCreativeTab(null);
	public static final Block blockLaptopOn = new BlockVanityLaptopOn("vanityLaptopOn");

	public static final Block punchDesignix = new BlockPunchDesignix();
	public static final BlockTotemLathe[] totemlathe = BlockTotemLathe.createBlocks();
	public static final BlockAlchemiter[] alchemiter = BlockAlchemiter.createBlocks();
	public static final Block cruxtruder = new BlockCruxtruder();
	public static final Block cruxtruderLid = new BlockCruxtruderLid();

	public static final Block blockCruxiteDowel = new BlockCruxtiteDowel();
	public static final Block blockGoldSeeds = new BlockGoldSeeds();
	public static final Block returnNode = new BlockReturnNode("returnNode");
	public static final Block gate = new BlockGate("gate");
	public static final Block petrifiedLog = new BlockPetrifiedLog("petrifiedLog");
	public static final Block petrifiedPoppy = new BlockPetrifiedFlora("petrifiedPoppy");
	public static final Block petrifiedGrass = new BlockPetrifiedFlora("petrifiedGrass");
	public static final Block bloomingCactus = new BlockDesertFlora("bloomingCactus");
	public static final Block desertBush = new BlockDesertBush("desertBush");
	public static final Block glowingMushroom = new BlockGlowingMushroom();
	public static final Block glowingLog = new BlockGlowingLog();
	public static final Block glowingPlanks = new BlockFlamable("glowingPlanks", Material.WOOD, MapColor.LIGHT_BLUE, SoundType.WOOD).setFireInfo(5, 20).setLightLevel(0.5F).setHardness(2.0F).setResistance(5.0F);
	public static final Block stone = new BlockMinestuckStone();
	public static final Block glowyGoop = new BlockGlowyGoop("glowyGoop");
	public static final Block coagulatedBlood = new BlockGoop("coagulatedBlood");
	public static final Block coarseStoneStairs = new MSBlockStairs("coarseStoneStairs", stone.getDefaultState().withProperty(BlockMinestuckStone.VARIANT, BlockMinestuckStone.BlockType.COARSE));
	public static final Block shadeBrickStairs = new MSBlockStairs("shadeBrickStairs", stone.getDefaultState().withProperty(BlockMinestuckStone.VARIANT, BlockMinestuckStone.BlockType.SHADE_BRICK));
	public static final Block frostBrickStairs = new MSBlockStairs("frostBrickStairs", stone.getDefaultState().withProperty(BlockMinestuckStone.VARIANT, BlockMinestuckStone.BlockType.FROST_BRICK));
	public static final Block castIronStairs = new MSBlockStairs("castIronStairs", stone.getDefaultState().withProperty(BlockMinestuckStone.VARIANT, BlockMinestuckStone.BlockType.CAST_IRON));
	public static final Block myceliumBrickStairs = new MSBlockStairs("myceliumBrickStairs", stone.getDefaultState().withProperty(BlockMinestuckStone.VARIANT, BlockMinestuckStone.BlockType.MYCELIUM_BRICK));
	public static final Block log = new BlockMinestuckLogVariant("log");
	public static final Block leaves1 = new BlockMinestuckLeavesVariant();
	public static final Block planks = generateSlabsAndStairs(new BlockMinestuckPlanks());
	public static final Block frostPlanks = new BlockFrostPlanks();
	public static final Block aspectSapling = new BlockAspectSapling();
	public static final Block rainbowSapling = new BlockRainbowSapling();
	public static final Block aspectLog1 = new BlockAspectLog();
	public static final Block aspectLog2 = new BlockAspectLog2();
	public static final Block aspectLog3 = new BlockAspectLog3();
	public static final Block woodenCactus = new BlockCactusSpecial("woodenCactus", SoundType.WOOD, "axe").setHardness(1.0F).setResistance(2.5F);
	public static final Block sugarCube = new BlockFlamable("sugarCube", Material.SAND, MapColor.SNOW, SoundType.SAND).setHardness(0.4F);
	public static final Block rabbitSpawner = new BlockMobSpawner("rabbitSpawner");
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
	public static final Block vein = new BlockVein("vein");
	public static final Block veinCorner = new BlockVeinCorner("veinCorner");
	public static final Block veinCornerInverted = new BlockVeinCorner("veinCornerInverted");

	public static final Block treatedPlanks = generateSlabsAndStairs(new BlockFlamable("treatedPlanks", Material.WOOD, MapColor.WOOD, SoundType.WOOD).setFireInfo(1, 0).setHardness(1.0F));

	public static final Block endLog = new BlockEndLog();
	public static final Block endLeaves = new BlockEndLeaves();
	public static final Block endPlanks = generateSlabsAndStairs(new BlockFlamable("endPlanks", Material.WOOD, MapColor.SAND, SoundType.WOOD).setFireInfo(1, 250).setHardness(1.0F));
	public static final Block endSapling = new BlockEndSapling();
	public static final Block endGrass = new BlockEndGrass();
	public static final Block coarseEndStone = new MSBlockBase("coarseEndStone", MapColor.SAND).setHardness(3.0F);

	public static final Block floweryMossStone = new MSBlockBase("floweryMossStone", MapColor.GRAY);
	public static final Block floweryMossBrick = new MSBlockBase("floweryMossBrick", MapColor.GRAY);
	public static final Block strawberry = new BlockStrawberry();
	public static final Block strawberryStem = new BlockMinestuckStem("strawberryStem", (BlockDirectional) strawberry).setCreativeTab(null);

	public static final Block deadLog = new BlockMinestuckLog("deadLog");
	public static final Block deadPlanks = generateSlabsAndStairs(new BlockFlamable("deadPlanks", Material.WOOD, MapColor.WOOD, SoundType.WOOD).setFireInfo(5, 5).setHardness(1.0F));
	public static final Block chalk = generateSlabsAndStairs(new MSBlockBase("chalk", MapColor.SNOW));
	public static final Block chalkBricks = generateSlabsAndStairs(new MSBlockBase("chalkBricks", MapColor.SNOW));
	public static final Block chalkChisel = new MSBlockBase("chiseledChalkBricks", MapColor.SNOW);
	public static final Block chalkPolish = new MSBlockBase("polishedChalk", MapColor.SNOW);
	public static final Block pinkStoneSmooth = new MSBlockBase("pinkStone", MapColor.PINK);
	public static final Block pinkStoneBricks = generateSlabsAndStairs(new MSBlockBase("pinkStoneBricks", MapColor.PINK));
	public static final Block pinkStoneChisel = new MSBlockBase("pinkChiseledStone", MapColor.PINK);
	public static final Block pinkStoneCracked = new MSBlockBase("pinkCrackedStone", MapColor.PINK);
	public static final Block pinkStoneMossy = new MSBlockBase("pinkMossStoneBricks", MapColor.PINK);
	public static final Block pinkStonePolish = new MSBlockBase("pinkPolishedStone", MapColor.PINK);
	public static final Block denseCloud = new BlockDenseCloud();

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

	public static final Block uraniumCooker = new BlockUraniumCooker();

	public static final Block primedTnt = new BlockTNTSpecial("primedTnt", true, false, false);
	public static final Block unstableTnt = new BlockTNTSpecial("unstableTnt", false, true, false);
	public static final Block instantTnt = new BlockTNTSpecial("instantTnt", false, false, true);
	public static final Block woodenExplosiveButton = new BlockButtonSpecial("woodenButtonExplosive", true, true);
	public static final Block stoneExplosiveButton = new BlockButtonSpecial("stoneButtonExplosive", false, true);

	public static final BlockLayered layeredSand = new BlockLayered("layeredSand", Blocks.SAND.getDefaultState());
	public static final Block glowystoneWire = new BlockGlowystoneWire("glowystoneWire");

	public static final MSBlockBase dungeonDoor = new BlockDungeonDoor("dungeonDoor");
	public static final MSBlockBase dungeonDoorKeyhole = new BlockDungeonDoor("dungeonDoorKeyhole");

	public static final MSBlockBase magicBlock = new MSBlockBase("magicBlock", Material.CLAY);
	public static final BlockDecor wizardStatue = new BlockDecor("wizardStatue");
	public static final MSBlockBase netherReactorCore = (MSBlockBase) new MSBlockBase("netherReactorCore", Material.ROCK).setHardness(30.0F).setResistance(6);
	public static final MSBlockBase sbahjTree = new BlockSbahjTree("kringlefucker");
	public static final MSBlockBase fluoriteBlock = (MSBlockBase) new MSBlockBase("fluoriteBlock", Material.ROCK, MapColor.LAPIS).setHardness(3.0F).setResistance(5.0F);
	public static final MSBlockBase fluoriteOre = (MSBlockBase) new MSBlockBase("fluoriteOre", Material.ROCK).setHardness(3.0F).setResistance(5.0F);
	public static final MSBlockBase moonstoneOre = (MSBlockBase) new MSBlockBase("moonstoneOre", Material.IRON).setHardness(3.0F).setResistance(5.0F);

	public static final MSBlockBase sbahjBedrock = (MSBlockBase) new MSBlockBase("sbahjBedrock", Material.CAKE).setResistance(0F).setHardness(-1F);
	public static final MSBlockBase zillyStone = (MSBlockBase) new MSBlockBase("zillystone", Material.ROCK).setResistance(999F).setHardness(5.5F);
	public static final MSBlockBase smoothIron = (MSBlockBase) new MSBlockBase("smoothIron", Material.IRON).setHardness(3.0F);

	public static final BlockHolopad holopad = new BlockHolopad();

	public static final BlockMachineChasis machineChasis = new BlockMachineChasis();
	public static final BlockGristHopper gristHopper = new BlockGristHopper();
	public static final BlockAutoWidget autoWidget = new BlockAutoWidget();
	public static final BlockAutoCaptcha autoCaptcha = new BlockAutoCaptcha();
	public static final BlockPorkhollowAtm porkhollowAtm = new BlockPorkhollowAtm();
	public static final BlockBoondollarRegister boondollarRegister = new BlockBoondollarRegister();

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

	public static final MSBlockBase dungeonShield = new BlockEffectBeacon(MapColor.ADOBE, new PotionEffect(MSUPotions.CREATIVE_SHOCK, 40, 0), "dungeonShield");
	public static final MSBlockBase flightBeacon = new BlockEffectBeacon(MapColor.ADOBE, new PotionEffect(MSUPotions.SKYHBOUND, 40, 0), "flightBeacon");
	public static final MSBlockBase flightInhibitor = new BlockEffectBeacon(MapColor.ADOBE, new PotionEffect(MSUPotions.EARTHBOUND, 40, 0), "flightInhibitor");

	public static final MSBlockBase uniqueObject = new MSBlockBase("uniqueObject", Material.CORAL, MapColor.DIAMOND);
	public static final MSBlockBase artifact = new BlockArtifact("artifact", Material.FIRE, MapColor.PURPLE);

	public static final MSBlockBase thaumChasis = new MSBlockBase("thaumChasis", Material.IRON);

	public static final Fluid fluidOil = createFluid("oil", new ResourceLocation("minestuck", "blocks/oil_still"), new ResourceLocation("minestuck", "blocks/oil_flowing"), "tile.oil");
	public static final Fluid fluidBlood = createFluid("blood", new ResourceLocation("minestuck", "blocks/blood_still"), new ResourceLocation("minestuck", "blocks/blood_flowing"), "tile.blood");
	public static final Fluid fluidBrainJuice = createFluid("brain_juice", new ResourceLocation("minestuck", "blocks/brain_juice_still"), new ResourceLocation("minestuck", "blocks/brain_juice_flowing"), "tile.brainJuice");
	public static final Fluid fluidWatercolors = createFluid("watercolors", new ResourceLocation("minestuck", "blocks/watercolors_still"), new ResourceLocation("minestuck", "blocks/watercolors_flowing"), "tile.watercolors");
	public static final Fluid fluidEnder = createFluid("ender", new ResourceLocation("minestuck", "blocks/ender_still"), new ResourceLocation("minestuck", "blocks/ender_flowing"), "tile.ender");
	public static final Fluid fluidLightWater = createFluid("light_water", new ResourceLocation("minestuck", "blocks/light_water_still"), new ResourceLocation("minestuck", "blocks/light_water_flowing"), "tile.lightWater");

	public static final Block blockOil = new MSFluidBase("blockOil", fluidOil, Material.WATER)
	{
		@SideOnly(Side.CLIENT)
		@Override
		public Vec3d getFogColor(World world, BlockPos pos, IBlockState state, Entity entity, Vec3d originalColor, float partialTicks)
		{
			return new Vec3d(0.0, 0.0, 0.0);
		}
	}.setLightOpacity(2);

	public static final Block blockBlood = new MSFluidBase("blockBlood", fluidBlood, Material.WATER)
	{
		@SideOnly(Side.CLIENT)
		@Override
		public Vec3d getFogColor(World world, BlockPos pos, IBlockState state, Entity entity, Vec3d originalColor, float partialTicks)
		{
			return new Vec3d(0.8, 0.0, 0.0);
		}
	}.setLightOpacity(1);

	public static final Block blockBrainJuice = new MSFluidBase("blockBrainJuice", fluidBrainJuice, Material.WATER)
	{
		@SideOnly(Side.CLIENT)
		@Override
		public Vec3d getFogColor(World world, BlockPos pos, IBlockState state, Entity entity, Vec3d originalColor, float partialTicks)
		{
			return new Vec3d(0.55, 0.25, 0.7);
		}
	}.setLightOpacity(1);

	public static final Block blockWatercolors = new MSFluidBase("blockWatercolors", fluidWatercolors, Material.WATER)
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

	public static final Block blockEnder = new BlockFluidEnder("blockEnder", fluidEnder, Material.WATER).setLightOpacity(1);

	public static final Block blockLightWater = new MSFluidBase("blockLightWater", fluidLightWater, Material.WATER)
	{
		@SideOnly(Side.CLIENT)
		@Override
		public Vec3d getFogColor(World world, BlockPos pos, IBlockState state, Entity entity, Vec3d originalColor, float partialTicks)
		{
			return new Vec3d(0.2, 0.3, 1.0);
		}
	}.setLightOpacity(1);

	public static final Block hardStone = new BlockHardStone("hardStone");
	public static final Block operandiBlock = new BlockOperandi("operandiBlock", 1.0f, 0, Material.GOURD, "");
	public static final Block operandiGlass = new BlockOperandiGlass("operandiGlass", 0.5f, 0, Material.GLASS, "");
	public static final Block operandiStone = new BlockOperandi("operandiStone", 3.0f, 6.5f, Material.IRON, "pickaxe");
	public static final Block operandiLog = new BlockOperandiLog("operandiLog", 2.0f, 0, BlockOperandi.LOG, "axe");

	//public static final Block[] liquidGrists;
	//public static final Fluid[] gristFluids;

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event)
	{
		generateSlabsAndStairs(Blocks.BEDROCK);

		IForgeRegistry<Block> registry = event.getRegistry();
		for (IRegistryItem<Block> block : blocks)
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

	private static Block generateSlabsAndStairs(Block block)
	{
		IBlockState blockState = block.getDefaultState();
		String name = blockState.getBlock().getUnlocalizedName().substring(5); // unloc name starts with an extra "tile."
		stairs.put(block, new MSBlockStairs(name + "Stairs", blockState));
		slabs.put(block, new MSBlockSlab(name + "Slab", blockState));
		return block;
	}
}