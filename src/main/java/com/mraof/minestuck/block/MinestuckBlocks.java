package com.mraof.minestuck.block;

import com.mraof.minestuck.util.IRegistryItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemSlab;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.IStringSerializable;
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

//not sure if BlockSburbMachines have the thing - arid
//everything else with setUnlocalizedName() is using a forge / minecraft class - arid

public class MinestuckBlocks
{
	//Blocks
	public static final Block chessTile = new BlockChessTile();
	public static final Block skaiaPortal = new BlockSkaiaPortal(Material.PORTAL);
	
	public static final Block coloredDirt = new BlockColoredDirt();
	public static final Block cruxiteBlock = new MSBlockBase("cruxiteBlock",Material.ROCK, MapColor.LIGHT_BLUE).setHardness(3.0F);
	public static final Block uraniumBlock = new MSBlockBase("uraniumBlock",Material.ROCK, MapColor.LIME).setLightLevel(0.5F).setHardness(3.0F);
	public static final Block genericObject = new BlockFlamable("genericObject",Material.GOURD, MapColor.LIME, SoundType.WOOD).setHardness(1.0F);
	
	public static final Block blender = new BlockDecor("blender",SoundType.METAL);
	public static final Block chessboard = new BlockDecor("chessboard");
	public static final Block frogStatueReplica = new BlockDecor("frogStatueReplica");
	
	//public static final Block sburbMachine = new BlockSburbMachine();
	public static final Block miniAlchemiter = new BlockMiniAlchemiter();
	public static final Block miniTotemLathe = new BlockMiniTotemLathe();
	public static final Block miniPunchDesignix = new BlockMiniPunchDesignix();
	public static final Block miniCruxtruder = new BlockMiniCruxtruder();
	public static final Block crockerMachine = new BlockCrockerMachine();
	public static final Block blockComputerOff = new BlockComputerOff("computerStandard");
	public static final Block blockComputerOn = new BlockComputerOn();
	public static final Block blockLaptopOff = new BlockVanityLaptopOff("vanityLaptop").setCreativeTab(null);
	public static final Block blockLaptopOn = new BlockVanityLaptopOn("vanityLaptopOn");
	public static final Block transportalizer = new BlockTransportalizer();
	
	public static final Block punchDesignix = new BlockPunchDesignix();
	public static final BlockTotemLathe[] totemlathe = BlockTotemLathe.createBlocks();
	public static final BlockAlchemiter[] alchemiter = BlockAlchemiter.createBlocks();
	public static final Block cruxtruder = new BlockCruxtruder();
	public static final Block cruxtruderLid = new BlockCruxtruderLid();
	public static final Block holopad = new BlockHolopad();
	//public static final BlockJumperBlock[] jumperBlockExtension = BlockJumperBlock.createBlocks();
	//public static final BlockAlchemiterUpgrades[] alchemiterUpgrades = BlockAlchemiterUpgrades.createBlocks();
	
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
	public static final Block glowingPlanks = new BlockFlamable("glowingPlanks",Material.WOOD, MapColor.LIGHT_BLUE, SoundType.WOOD).setFireInfo(5, 20).setLightLevel(0.5F).setHardness(2.0F).setResistance(5.0F);
	public static final Block stone = new BlockMinestuckStone();
	public static final Block glowyGoop = new BlockGlowyGoop("glowyGoop");
	public static final Block coagulatedBlood = new BlockGoop("coagulatedBlood");
	public static final Block coarseStoneStairs = new BlockMinestuckStairs("stairsMinestuck.coarseStoneStairs", "coarse_stone_stairs", stone.getDefaultState().withProperty(BlockMinestuckStone.VARIANT, BlockMinestuckStone.BlockType.COARSE));
	public static final Block shadeBrickStairs = new BlockMinestuckStairs("stairsMinestuck.shadeBrickStairs", "shade_brick_stairs", stone.getDefaultState().withProperty(BlockMinestuckStone.VARIANT, BlockMinestuckStone.BlockType.SHADE_BRICK));
	public static final Block frostBrickStairs = new BlockMinestuckStairs("stairsMinestuck.frostBrickStairs", "frost_brick_stairs", stone.getDefaultState().withProperty(BlockMinestuckStone.VARIANT, BlockMinestuckStone.BlockType.FROST_BRICK));
	public static final Block castIronStairs = new BlockMinestuckStairs("stairsMinestuck.castIronStairs", "cast_iron_stairs", stone.getDefaultState().withProperty(BlockMinestuckStone.VARIANT, BlockMinestuckStone.BlockType.CAST_IRON));
	public static final Block myceliumBrickStairs = new BlockMinestuckStairs("stairsMinestuck.myceliumBrick", "mycelium_brick_stairs", stone.getDefaultState().withProperty(BlockMinestuckStone.VARIANT, BlockMinestuckStone.BlockType.MYCELIUM_BRICK));
	public static final Block log = new BlockMinestuckLog1("log");
	public static final Block leaves1 = new BlockMinestuckLeaves1();
	public static final Block planks = new BlockMinestuckPlanks();
	public static final Block frostPlanks = new BlockFrostPlanks();
	public static final Block aspectSapling = new BlockAspectSapling();
	public static final Block rainbowSapling = new BlockRainbowSapling();
	public static final Block aspectLog1 = new BlockAspectLog();
	public static final Block aspectLog2 = new BlockAspectLog2();
	public static final Block aspectLog3 = new BlockAspectLog3();
	public static final Block woodenCactus = new BlockCactusSpecial("woodenCactus",SoundType.WOOD, "axe").setHardness(1.0F).setResistance(2.5F);
	public static final Block sugarCube = new BlockFlamable("sugarCube",Material.SAND, MapColor.SNOW, SoundType.SAND).setHardness(0.4F);
	public static final Block rabbitSpawner = new BlockMobSpawner("rabbitSpawner");
	public static final Block appleCake = new BlockSimpleCake("appleCake",2, 0.5F, null);
	public static final Block blueCake = new BlockSimpleCake("blueCake",2, 0.3F, (EntityPlayer player) -> player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 150, 0)));
	public static final Block coldCake = new BlockSimpleCake("coldCake",2, 0.3F, (EntityPlayer player) -> {player.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 200, 1));player.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 200, 1));});
	public static final Block redCake = new BlockSimpleCake("redCake",2, 0.1F, (EntityPlayer player) -> player.heal(1));
	public static final Block hotCake = new BlockSimpleCake("hotCake",2, 0.1F, (EntityPlayer player) -> player.setFire(4));
	public static final Block reverseCake = new BlockSimpleCake("reverseCake",2, 0.1F, null);
	public static final Block fuchsiaCake = new BlockSimpleCake("fuchsiaCake",3, 0.5F, (EntityPlayer player) -> {player.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 350, 1));player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 200, 0));});
	public static final Block vein = new BlockVein("vein");
	public static final Block veinCorner = new BlockVeinCorner("veinCorner");
	public static final Block veinCornerInverted = new BlockVeinCorner("veinCornerInverted");
	
	public static final Block treatedPlanks = new BlockFlamable("treatedPlanks",Material.WOOD, MapColor.WOOD, SoundType.WOOD).setFireInfo(1, 0).setHardness(1.0F);
	
	public static final Block endLog = new BlockEndLog();
	public static final Block endLeaves = new BlockEndLeaves();
	public static final Block endPlanks = new BlockFlamable("endPlanks",Material.WOOD, MapColor.SAND, SoundType.WOOD).setFireInfo(1, 250).setHardness(1.0F);
	public static final Block endSapling = new BlockEndSapling();
	public static final Block endGrass = new BlockEndGrass();
	public static final Block coarseEndStone = new MSBlockBase("coarseEndStone",MapColor.SAND).setHardness(3.0F);
	
	public static final Block floweryMossStone = new MSBlockBase("floweryMossStone",MapColor.GRAY);
	public static final Block floweryMossBrick = new MSBlockBase("floweryMossBrick",MapColor.GRAY);
	public static final Block strawberry = new BlockStrawberry();
	public static final Block strawberryStem = new BlockMinestuckStem("strawberryStem", (BlockDirectional) strawberry).setCreativeTab(null);
	
	public static final Block deadLog = new BlockMinestuckLog("deadLog");
	public static final Block deadPlanks = new BlockFlamable("deadPlanks",Material.WOOD, MapColor.WOOD, SoundType.WOOD).setFireInfo(5, 5).setHardness(1.0F);
	public static final Block chalk = new MSBlockBase("chalk",MapColor.SNOW);
	public static final Block chalkBricks = new MSBlockBase("chalkBricks",MapColor.SNOW);
	public static final Block chalkChisel = new MSBlockBase ("chiseledChalkBricks",MapColor.SNOW);
	public static final Block chalkPolish = new MSBlockBase("polishedChalk",MapColor.SNOW);
	public static final Block pinkStoneSmooth = new MSBlockBase("pinkStone",MapColor.PINK);
	public static final Block pinkStoneBricks = new MSBlockBase("pinkStoneBricks",MapColor.PINK);
	public static final Block pinkStoneChisel = new MSBlockBase("pinkChiseledStone",MapColor.PINK);
	public static final Block pinkStoneCracked= new MSBlockBase("pinkCrackedStone",MapColor.PINK);
	public static final Block pinkStoneMossy = new MSBlockBase("pinkMossStoneBricks",MapColor.PINK);
	public static final Block pinkStonePolish = new MSBlockBase("pinkPolishedStone",MapColor.PINK);
	public static final Block denseCloud = new BlockDenseCloud();
	
	//Ores
	public static final Block oreCruxite = new BlockCruxiteOre();
	public static final BlockUraniumOre oreUranium = (BlockUraniumOre) new BlockUraniumOre();
	public static final Block coalOreNetherrack = new BlockVanillaOre("coalOreNetherrack",BlockVanillaOre.OreType.COAL);
	public static final Block coalOrePinkStone = new BlockVanillaOre("coalOrePinkStone",BlockVanillaOre.OreType.COAL);
	public static final Block ironOreEndStone = new BlockVanillaOre("ironOreEndStone",BlockVanillaOre.OreType.IRON);
	public static final Block ironOreSandstone = new BlockVanillaOre("ironOreSandstone",BlockVanillaOre.OreType.IRON);
	public static final Block ironOreSandstoneRed = new BlockVanillaOre("ironOreSandstoneRed",BlockVanillaOre.OreType.IRON);
	public static final Block goldOreSandstone = new BlockVanillaOre("goldOreSandstone",BlockVanillaOre.OreType.GOLD);
	public static final Block goldOreSandstoneRed = new BlockVanillaOre("goldOreSandstoneRed",BlockVanillaOre.OreType.GOLD);
	public static final Block goldOrePinkStone = new BlockVanillaOre("goldOrePinkStone",BlockVanillaOre.OreType.GOLD);
	public static final Block redstoneOreEndStone = new BlockVanillaOre("redstoneOreEndStone",BlockVanillaOre.OreType.REDSTONE);
	public static final Block quartzOreStone = new BlockVanillaOre("quartzOreStone",BlockVanillaOre.OreType.QUARTZ);
	public static final Block lapisOrePinkStone = new BlockVanillaOre("lapisOrePinkStone",BlockVanillaOre.OreType.LAPIS);
	public static final Block diamondOrePinkStone = new BlockVanillaOre("diamondOrePinkStone",BlockVanillaOre.OreType.DIAMOND);
	
	public static final Block uraniumCooker = new BlockUraniumCooker();
	
	public static final Block primedTnt = new BlockTNTSpecial("primedTnt", true, false, false);
	public static final Block unstableTnt = new BlockTNTSpecial("unstableTnt", false, true, false);
	public static final Block instantTnt = new BlockTNTSpecial("instantTnt", false, false, true);
	public static final Block woodenExplosiveButton = new BlockButtonSpecial("woodenButtonExplosive",true, true);
	public static final Block stoneExplosiveButton = new BlockButtonSpecial("stoneButtonExplosive",false, true);
	
	public static final BlockLayered layeredSand = (BlockLayered) new BlockLayered("layeredSand",Blocks.SAND.getDefaultState());
	public static final Block glowystoneWire = new BlockGlowystoneWire("glowystoneWire");

	public static final Fluid fluidOil = createFluid("oil", new ResourceLocation("minestuck", "blocks/oil_still"), new ResourceLocation("minestuck", "blocks/oil_flowing"), "tile.oil");
	public static final Fluid fluidBlood = createFluid("blood", new ResourceLocation("minestuck", "blocks/blood_still"), new ResourceLocation("minestuck", "blocks/blood_flowing"), "tile.blood");
	public static final Fluid fluidBrainJuice = createFluid("brain_juice", new ResourceLocation("minestuck", "blocks/brain_juice_still"), new ResourceLocation("minestuck", "blocks/brain_juice_flowing"), "tile.brainJuice");
	public static final Fluid fluidWatercolors = createFluid("watercolors", new ResourceLocation("minestuck", "blocks/watercolors_still"), new ResourceLocation("minestuck", "blocks/watercolors_flowing"), "tile.watercolors");
	public static final Fluid fluidEnder = createFluid("ender", new ResourceLocation("minestuck", "blocks/ender_still"), new ResourceLocation("minestuck", "blocks/ender_flowing"), "tile.ender");
	public static final Fluid fluidLightWater = createFluid("light_water", new ResourceLocation("minestuck", "blocks/light_water_still"), new ResourceLocation("minestuck", "blocks/light_water_flowing"), "tile.lightWater");
	
	public static final Block blockOil = new MSFluidBase("blockOil",fluidOil, Material.WATER){
		@SideOnly (Side.CLIENT)
		@Override
		public Vec3d getFogColor(World world, BlockPos pos, IBlockState state, Entity entity, Vec3d originalColor, float partialTicks)
		{
			return new Vec3d(0.0, 0.0, 0.0);
		}
	}.setLightOpacity(2);
	
	public static final Block blockBlood = new MSFluidBase("blockBlood",fluidBlood, Material.WATER){
		@SideOnly (Side.CLIENT)
		@Override
		public Vec3d getFogColor(World world, BlockPos pos, IBlockState state, Entity entity, Vec3d originalColor, float partialTicks)
		{
			return new Vec3d(0.8, 0.0, 0.0);
		}
	}.setLightOpacity(1);
	
	public static final Block blockBrainJuice = new MSFluidBase("blockBrainJuice",fluidBrainJuice, Material.WATER){
		@SideOnly (Side.CLIENT)
		@Override
		public Vec3d getFogColor(World world, BlockPos pos, IBlockState state, Entity entity, Vec3d originalColor, float partialTicks)
		{
			return new Vec3d(0.55, 0.25, 0.7);
		}
	}.setLightOpacity(1);
	
	public static final Block blockWatercolors = new MSFluidBase("blockWatercolors",fluidWatercolors, Material.WATER){
		@SideOnly (Side.CLIENT)
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
	
	public static final Block blockEnder = new BlockFluidEnder("blockEnder",fluidEnder, Material.WATER).setLightOpacity(1);

	public static final Block blockLightWater = new MSFluidBase("blockLightWater",fluidLightWater, Material.WATER){
		@SideOnly (Side.CLIENT)
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
		IForgeRegistry<Block> registry = event.getRegistry();

		for(EnumSlabStairMaterial material : EnumSlabStairMaterial.values())
		{
			material.getName();
		}

		//registry.register(chessTile.setRegistryName("chess_tile"));
		//registry.register(coloredDirt.setRegistryName("colored_dirt"));
		//registry.register(layeredSand.setRegistryName("layered_sand"));
		//registry.register(stone.setRegistryName("stone"));
		//registry.register(sugarCube.setRegistryName("sugar_cube"));
		
		//registry.register(log.setRegistryName("log"));
		//registry.register(leaves1.setRegistryName("leaves"));
		//registry.register(planks.setRegistryName("planks"));
		//registry.register(frostPlanks.setRegistryName("frost_planks"));
		//registry.register(aspectSapling.setRegistryName("aspect_sapling"));
		//registry.register(rainbowSapling.setRegistryName("rainbow_sapling"));
		//registry.register(aspectLog1.setRegistryName("aspect_log_1"));
		//registry.register(aspectLog2.setRegistryName("aspect_log_2"));
		//registry.register(aspectLog3.setRegistryName("aspect_log_3"));
		//registry.register(glowingLog.setRegistryName("glowing_log"));
		//registry.register(glowingPlanks.setRegistryName("glowing_planks"));
		//registry.register(glowingMushroom.setRegistryName("glowing_mushroom"));
		//registry.register(glowyGoop.setRegistryName("glowy_goop"));
		//registry.register(coagulatedBlood.setRegistryName("coagulated_blood"));
		//registry.register(petrifiedLog.setRegistryName("petrified_log"));
		//registry.register(petrifiedPoppy.setRegistryName("petrified_poppy"));
		//egistry.register(petrifiedGrass.setRegistryName("petrified_grass"));
		//registry.register(bloomingCactus.setRegistryName("blooming_cactus"));
		//registry.register(desertBush.setRegistryName("desert_bush"));
		//registry.register(woodenCactus.setRegistryName("wooden_cactus"));
		
		//registry.register(oreCruxite.setRegistryName("ore_cruxite"));
		//registry.register(oreUranium.setRegistryName("ore_uranium"));
		//registry.register(coalOreNetherrack.setRegistryName("coal_ore_netherrack"));
		//registry.register(coalOrePinkStone.setRegistryName("coal_ore_pink_stone"));
		//registry.register(ironOreEndStone.setRegistryName("iron_ore_end_stone"));
		//registry.register(ironOreSandstone.setRegistryName("iron_ore_sandstone"));
		//registry.register(ironOreSandstoneRed.setRegistryName("iron_ore_sandstone_red"));
		//registry.register(goldOreSandstone.setRegistryName("gold_ore_sandstone"));
		//registry.register(goldOreSandstoneRed.setRegistryName("gold_ore_sandstone_red"));
		//registry.register(goldOrePinkStone.setRegistryName("gold_ore_pink_stone"));
		//registry.register(redstoneOreEndStone.setRegistryName("redstone_ore_end_stone"));
		//registry.register(quartzOreStone.setRegistryName("quartz_ore_stone"));
		//registry.register(lapisOrePinkStone.setRegistryName("lapis_ore_pink_stone"));
		//registry.register(diamondOrePinkStone.setRegistryName("diamond_ore_pink_stone"));
		
		//registry.register(cruxiteBlock.setRegistryName("cruxite_block"));
		//registry.register(uraniumBlock.setRegistryName("uranium_block"));
		//registry.register(genericObject.setRegistryName("generic_object"));
		//registry.register(blockCruxiteDowel.setRegistryName("cruxite_dowel"));
		
		//registry.register(coarseStoneStairs.setRegistryName("coarse_stone_stairs"));
		//registry.register(shadeBrickStairs.setRegistryName("shade_brick_stairs"));
		//registry.register(frostBrickStairs.setRegistryName("frost_brick_stairs"));
		//registry.register(castIronStairs.setRegistryName("cast_iron_stairs"));
		//registry.register(myceliumBrickStairs.setRegistryName("mycelium_brick_stairs"));

		//registry.register(skaiaPortal.setRegistryName("skaia_portal"));
		//registry.register(returnNode.setRegistryName("return_node"));
		//registry.register(gate.setRegistryName("gate"));
		
		//registry.register(sburbMachine.setRegistryName("sburb_machine"));
		//registry.register(crockerMachine.setRegistryName("crocker_machine"));
		//registry.register(transportalizer.setRegistryName("transportalizer"));
		//registry.register(uraniumCooker.setRegistryName("uranium_cooker"));
		
		//registry.register(punchDesignix.setRegistryName("punch_designix"));
		//registry.register(totemlathe[0].setRegistryName("totem_lathe"));
		//registry.register(totemlathe[1].setRegistryName("totem_lathe2"));
		//registry.register(totemlathe[2].setRegistryName("totem_lathe3"));
		//registry.register(alchemiter[0].setRegistryName("alchemiter"));
		//registry.register(alchemiter[1].setRegistryName("alchemiter2"));
		//registry.register(cruxtruder.setRegistryName("cruxtruder"));
		//registry.register(cruxtruderLid.setRegistryName("cruxtruder_lid"));
		/*
		registry.register(holopad.setRegistryName("holopad"));
		//registry.register(jumperBlockExtension[0].setRegistryName("jumper_block_extension"));
		//registry.register(jumperBlockExtension[1].setRegistryName("jumper_block_extension2"));
		//registry.register(jumperBlockExtension[2].setRegistryName("jumper_block_extension3"));
		//registry.register(jumperBlockExtension[3].setRegistryName("jumper_block_extension4"));
		
		//registry.register(alchemiterUpgrades[0].setRegistryName("alchemiter_upgrade"));
		//registry.register(alchemiterUpgrades[1].setRegistryName("alchemiter_upgrade2"));
		//registry.register(alchemiterUpgrades[2].setRegistryName("alchemiter_upgrade3"));
		//registry.register(alchemiterUpgrades[3].setRegistryName("alchemiter_upgrade4"));
		*/
		//registry.register(blender.setRegistryName("blender"));
		//registry.register(chessboard.setRegistryName("chessboard"));
		//registry.register(frogStatueReplica.setRegistryName("frog_statue_replica"));
		
		//registry.register(blockComputerOff.setRegistryName("computer_standard"));
		//registry.register(blockComputerOn.setRegistryName("computer_standard_on"));
		//registry.register(blockLaptopOff.setRegistryName("vanity_laptop"));
		//registry.register(blockLaptopOn.setRegistryName("vanity_laptop_on"));
		
		//registry.register(blockGoldSeeds.setRegistryName("gold_seeds"));
		//registry.register(glowystoneWire.setRegistryName("glowystone_wire"));
		
		//registry.register(appleCake.setRegistryName("apple_cake"));
		//registry.register(blueCake.setRegistryName("blue_cake"));
		//registry.register(coldCake.setRegistryName("cold_cake"));
		//registry.register(redCake.setRegistryName("red_cake"));
		//registry.register(hotCake.setRegistryName("hot_cake"));
		//registry.register(reverseCake.setRegistryName("reverse_cake"));
		//registry.register(fuchsiaCake.setRegistryName("fuchsia_cake"));
		
		//registry.register(coarseEndStone.setRegistryName("coarse_end_stone"));
		//registry.register(endGrass.setRegistryName("end_grass"));
		//registry.register(endLog.setRegistryName("end_log"));
		//registry.register(endLeaves.setRegistryName("end_leaves"));
		//registry.register(endPlanks.setRegistryName("end_planks"));
		//registry.register(endSapling.setRegistryName("end_sapling"));
		
		//registry.register(treatedPlanks.setRegistryName("treated_planks"));
		//registry.register(floweryMossStone.setRegistryName("flowery_moss_stone"));
		//registry.register(floweryMossBrick.setRegistryName("flowery_moss_brick"));
		//registry.register(strawberry.setRegistryName("strawberry"));
		//registry.register(strawberryStem.setRegistryName("strawberry_stem"));
		
		//registry.register(deadLog.setRegistryName("dead_log"));
		//registry.register(deadPlanks.setRegistryName("dead_planks"));
		//registry.register(chalk.setRegistryName("chalk"));
		//registry.register(chalkBricks.setRegistryName("chalk_bricks"));
		//registry.register(chalkChisel.setRegistryName("chiseled_chalk_bricks"));
		//registry.register(chalkPolish.setRegistryName("polished_chalk"));
		//registry.register(pinkStoneSmooth.setRegistryName("pink_stone"));
		//registry.register(pinkStoneBricks.setRegistryName("pink_stone_bricks"));
		//registry.register(pinkStoneChisel.setRegistryName("pink_chiseled_stone"));
		//registry.register(pinkStoneCracked.setRegistryName("pink_cracked_stone"));
		//registry.register(pinkStoneMossy.setRegistryName("pink_moss_stone_bricks"));
		//registry.register(pinkStonePolish.setRegistryName("pink_polished_stone"));
		//registry.register(denseCloud.setRegistryName("dense_cloud"));
		
		//registry.register(vein.setRegistryName("vein"));
		//registry.register(veinCorner.setRegistryName("vein_corner"));
		//registry.register(veinCornerInverted.setRegistryName("vein_corner_inverted"));
		
		//registry.register(primedTnt.setRegistryName("primed_tnt"));
		//registry.register(unstableTnt.setRegistryName("unstable_tnt"));
		//registry.register(instantTnt.setRegistryName("instant_tnt"));
		//registry.register(woodenExplosiveButton.setRegistryName("wooden_button_explosive"));
		//registry.register(stoneExplosiveButton.setRegistryName("stone_button_explosive"));
		
		//registry.register(blockOil.setRegistryName("block_oil"));
		//registry.register(blockBlood.setRegistryName("block_blood"));
		//registry.register(blockBrainJuice.setRegistryName("block_brain_juice"));
		//registry.register(blockWatercolors.setRegistryName("block_watercolors"));
		//registry.register(blockEnder.setRegistryName("block_ender"));
		//registry.register(blockLightWater.setRegistryName("block_light_water"));
		
		//registry.register(rabbitSpawner.setRegistryName("rabbit_spawner"));

		/*
		for(EnumSlabStairMaterial material : EnumSlabStairMaterial.values())
		{
			registry.register(material.getSlab().setRegistryName(material.getName() + "_slab"));
			registry.register(material.getSlabFull().setRegistryName(material.getName() + "_slab_full"));
			registry.register(material.getStair().setRegistryName(material.getName() + "_stairs"));
		}
		*/

		//fluids
		/*liquidGrists = new Block[GristType.allGrists];
		gristFluids = new Fluid[GristType.allGrists];
		for(GristType grist : GristType.values()) {
			gristFluids[grist.getId()] = new Fluid(grist.getName(), new ResourceLocation("minestuck", "blocks/Liquid" + grist.getName() + "Still"), new ResourceLocation("minestuck", "blocks/Liquid" + grist.getName() + "Flowing"));
			FluidRegistry.registerFluid(gristFluids[grist.getId()]);
			liquidGrists[grist.getId()] = GameRegistry.register(new BlockFluidGrist(grist.getName(), gristFluids[grist.getId()], Material.WATER));
		}*/
		
		cruxiteBlock.setHarvestLevel("pickaxe", 0);
		uraniumBlock.setHarvestLevel("pickaxe", 1);
		coalOreNetherrack.setHarvestLevel("pickaxe", Blocks.COAL_ORE.getHarvestLevel(Blocks.COAL_ORE.getDefaultState()));
		coalOrePinkStone.setHarvestLevel("pickaxe", Blocks.COAL_ORE.getHarvestLevel(Blocks.COAL_ORE.getDefaultState()));
		ironOreEndStone.setHarvestLevel("pickaxe", Blocks.IRON_ORE.getHarvestLevel(Blocks.IRON_ORE.getDefaultState()));
		ironOreSandstone.setHarvestLevel("pickaxe", Blocks.IRON_ORE.getHarvestLevel(Blocks.IRON_ORE.getDefaultState()));
		ironOreSandstoneRed.setHarvestLevel("pickaxe", Blocks.IRON_ORE.getHarvestLevel(Blocks.IRON_ORE.getDefaultState()));
		goldOreSandstone.setHarvestLevel("pickaxe", Blocks.GOLD_ORE.getHarvestLevel(Blocks.GOLD_ORE.getDefaultState()));
		goldOreSandstoneRed.setHarvestLevel("pickaxe", Blocks.GOLD_ORE.getHarvestLevel(Blocks.GOLD_ORE.getDefaultState()));
		goldOrePinkStone.setHarvestLevel("pickaxe", Blocks.GOLD_ORE.getHarvestLevel(Blocks.GOLD_ORE.getDefaultState()));
		redstoneOreEndStone.setHarvestLevel("pickaxe", Blocks.REDSTONE_ORE.getHarvestLevel(Blocks.REDSTONE_ORE.getDefaultState()));
		quartzOreStone.setHarvestLevel("pickaxe", Blocks.QUARTZ_ORE.getHarvestLevel(Blocks.QUARTZ_ORE.getDefaultState()));
		lapisOrePinkStone.setHarvestLevel("pickaxe", Blocks.LAPIS_ORE.getHarvestLevel(Blocks.LAPIS_ORE.getDefaultState()));
		diamondOrePinkStone.setHarvestLevel("pickaxe", Blocks.DIAMOND_ORE.getHarvestLevel(Blocks.DIAMOND_ORE.getDefaultState()));
		petrifiedLog.setHarvestLevel("pickaxe", 0);

		//registry.register(hardStone.setRegistryName("hard_stone"));
		//registry.register(operandiBlock.setRegistryName("operandi_block"));
		//registry.register(operandiStone.setRegistryName("operandi_stone"));
		//registry.register(operandiGlass.setRegistryName("operandi_glass"));
		//registry.register(operandiLog.setRegistryName("operandi_log"));
		for(IRegistryItem<Block> block: MSBlockBase.blocks)
		{
			//System.out.println(((Block)block).getUnlocalizedName());
			block.register(registry);
		}

	}
	
	private static Fluid createFluid(String name, ResourceLocation still, ResourceLocation flowing, String unlocalizedName)
	{
		Fluid fluid = new Fluid(name, still, flowing);
		
		boolean useFluid = FluidRegistry.registerFluid(fluid);
		
		if(useFluid)
			fluid.setUnlocalizedName(unlocalizedName);
		else fluid = FluidRegistry.getFluid(name);
		
		return fluid;
	}
	
	public enum EnumSlabStairMaterial implements IStringSerializable
	{
		TREATED	(treatedPlanks.getDefaultState(),	"treated_planks"),
		RAINBOW	(planks.getDefaultState(),		"rainbow_planks"),
		END		(endPlanks.getDefaultState(),	"end_planks"),
		DEAD	(deadPlanks.getDefaultState(),	"dead_planks"),
		CHALK	(chalk.getDefaultState(),		"chalk"),
		CHALK_BRICK	(chalkBricks.getDefaultState(),	"chalk_bricks"),
		PINK_BRICK	(pinkStoneBricks.getDefaultState(),	"pink_stone_bricks");
		
		private final String regName;
		private final String unlocalizedName;
		
		private final Block stair;
		private final Block slab;
		private final Block slabF;
		private final ItemBlock slabItem;
		
		EnumSlabStairMaterial(IBlockState modelState, String name)
		{
			this.regName = name;
			String[] nameParts = name.split("_");
			StringBuilder unlocName = new StringBuilder(nameParts[0]);
			for(int i=1; i<nameParts.length; i++)
			{
				unlocName.append(nameParts[i].substring(0, 1).toUpperCase());
				unlocName.append(nameParts[i].substring(1));
			}
			this.unlocalizedName = unlocName.toString();
			
			stair = new BlockMinestuckStairs("stairMinestuck." + unlocalizedName, getName() + "_stairs",modelState);
			slab = new BlockMinestuckSlab("slabMinestuck." + unlocalizedName, getName() + "_slab", modelState, this, false);
			slabF = new BlockMinestuckSlab("slabMinestuckFull." + unlocalizedName, getName() + "_slab_full", modelState, this, true);
			
			if(modelState.getBlock().getHarvestLevel(modelState) >= 0)
			{
				slab .setHarvestLevel("pickaxe", modelState.getBlock().getHarvestLevel(modelState));
				slabF.setHarvestLevel("pickaxe", modelState.getBlock().getHarvestLevel(modelState));
			}

			slabItem = new ItemSlab(getSlab(), (BlockSlab) getSlab(), (BlockSlab) getSlabFull());
			slabItem.setUnlocalizedName("slabMinestuck." + name).setHasSubtypes(false);
		}
		
		public Block getStair()	{	return stair;	}
		public Block getSlab()	{	return slab;	}
		public Block getSlabFull()		{	return slabF;	}
		public ItemBlock getSlabItem()	{	return slabItem;}
		
		public String getUnlocalizedName()
		{
			return unlocalizedName;
		}
		
		@Override
		public String getName()
		{
			return regName;
		}
	}
}