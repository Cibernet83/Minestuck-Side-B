package com.cibernet.minestuckgodtier.blocks;

import com.cibernet.minestuckgodtier.items.MSGTItems;
import com.cibernet.minestuckgodtier.potions.MSGTPotions;
import com.cibernet.minestuckuniverse.blocks.BlockEffectBeacon;
import com.mraof.minestuck.util.EnumAspect;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.MapColor;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class MSGTBlocks
{
	public static final ArrayList<Block> blocks = new ArrayList<>();

	public static final Map<EnumAspect, BlockHeroStone> heroStones = new TreeMap<>();
	public static final Map<EnumAspect, BlockHeroStone> chiseledHeroStones = new TreeMap<>();
	public static final Map<EnumAspect, BlockHeroStoneWall> heroStoneWalls = new TreeMap<>();
	public static final Map<EnumAspect, BlockSpectralHeroStone> spectralHeroStones = new TreeMap<>();

	public static final BlockHeroStone wildcardHeroStone = new BlockHeroStone(null, false);
	public static final BlockHeroStone wildcardChiseledHeroStone = new BlockHeroStone(null, true);
	public static final BlockHeroStoneWall wildcardHeroStoneWall = new BlockHeroStoneWall(null);
	public static final BlockSpectralHeroStone wildcardSpectralHeroStone = new BlockSpectralHeroStone(null);
	public static final Block glowingHeroStone = new BlockGlowingHeroStone();
	public static final Block heroLockBeacon = new BlockEffectBeacon(MapColor.IRON, new PotionEffect(MSGTPotions.GOD_TIER_LOCK, 60, 2), "badge_inhibitor", "badgeInhibitor")
	{{
		blocks.add(this);
		setRegistryName("badge_inhibitor");
		MSGTItems.blocks.add(this);
	}};

	public static final Block glorb = new BlockGlorb();

	public static final Block prospitStone = new BlockStone(MapColor.YELLOW).setUnlocalizedName("prospitStone").setRegistryName("prospit_stone");
	public static final Block prospitSmoothstone = new BlockStone(MapColor.YELLOW).setUnlocalizedName("prospitSmoothstone").setRegistryName("prospit_smoothstone");
	public static final Block prospitBricks = new BlockStone(MapColor.YELLOW).setUnlocalizedName("prospitBricks").setRegistryName("prospit_bricks");
	public static final Block prospitBricksCracked = new BlockStone(MapColor.YELLOW).setUnlocalizedName("prospitBricksCracked").setRegistryName("cracked_prospit_bricks");
	public static final Block prospitBricksChiseled = new BlockStone(MapColor.YELLOW).setUnlocalizedName("prospitBricksChiseled").setRegistryName("chiseled_prospit_bricks");
	public static final Block prospitPillar = new BlockPillar(MapColor.YELLOW).setUnlocalizedName("prospitPillar").setRegistryName("prospit_pillar");

	public static final BlockSlab prospitStoneSlab = new BlockMSGTSlab.Half(MapColor.YELLOW, "prospitStoneSlab", "prospit_stone_slab");
	public static final BlockSlab prospitStoneDoubleSlab = new BlockMSGTSlab.Double(MapColor.YELLOW, "prospitStoneSlab", "prospit_stone_slab_double", prospitStoneSlab);
	public static final BlockSlab prospitSmoothstoneSlab = new BlockMSGTSlab.Half(MapColor.YELLOW, "prospitSmoothstoneSlab", "prospit_smoothstone_slab");
	public static final BlockSlab prospitSmoothstoneDoubleSlab = new BlockMSGTSlab.Double(MapColor.YELLOW, "prospitSmoothstoneSlab", "prospit_smoothstone_slab_double", prospitSmoothstoneSlab);
	public static final BlockSlab prospitBricksSlab = new BlockMSGTSlab.Half(MapColor.YELLOW, "prospitBricksSlab", "prospit_bricks_slab");
	public static final BlockSlab prospitBricksDoubleSlab = new BlockMSGTSlab.Double(MapColor.YELLOW, "prospitBricksSlab", "prospit_bricks_slab_double", prospitBricksSlab);

	public static final Block prospitStoneStairs = new BlockMSGTStairs(prospitStone.getDefaultState()).setUnlocalizedName("prospitStoneStairs").setRegistryName("prospit_stone_stairs");
	public static final Block prospitBricksStairs = new BlockMSGTStairs(prospitStone.getDefaultState()).setUnlocalizedName("prospitBricksStairs").setRegistryName("prospit_bricks_stairs");

	public static final Block prospitStoneWall = new BlockWall(MapColor.YELLOW).setUnlocalizedName("prospitStoneWall").setRegistryName("prospit_stone_wall");
	public static final Block prospitBricksWall = new BlockWall(MapColor.YELLOW).setUnlocalizedName("prospitBricksWall").setRegistryName("prospit_bricks_wall");

	public static final Block derseStone = new BlockStone(MapColor.PURPLE).setUnlocalizedName("derseStone").setRegistryName("derse_stone");
	public static final Block derseSmoothstone = new BlockStone(MapColor.PURPLE).setUnlocalizedName("derseSmoothstone").setRegistryName("derse_smoothstone");
	public static final Block derseBricks = new BlockStone(MapColor.PURPLE).setUnlocalizedName("derseBricks").setRegistryName("derse_bricks");
	public static final Block derseBricksCracked = new BlockStone(MapColor.PURPLE).setUnlocalizedName("derseBricksCracked").setRegistryName("cracked_derse_bricks");
	public static final Block derseBricksChiseled = new BlockStone(MapColor.PURPLE).setUnlocalizedName("derseBricksChiseled").setRegistryName("chiseled_derse_bricks");
	public static final Block dersePillar = new BlockPillar(MapColor.PURPLE).setUnlocalizedName("dersePillar").setRegistryName("derse_pillar");

	public static final BlockSlab derseStoneSlab = new BlockMSGTSlab.Half(MapColor.PURPLE, "derseStoneSlab", "derse_stone_slab");
	public static final BlockSlab derseStoneDoubleSlab = new BlockMSGTSlab.Double(MapColor.PURPLE, "derseStoneSlab", "derse_stone_slab_double", derseStoneSlab);
	public static final BlockSlab derseSmoothstoneSlab = new BlockMSGTSlab.Half(MapColor.PURPLE, "derseSmoothstoneSlab", "derse_smoothstone_slab");
	public static final BlockSlab derseSmoothstoneDoubleSlab = new BlockMSGTSlab.Double(MapColor.PURPLE, "derseSmoothstoneSlab", "derse_smoothstone_slab_double", derseSmoothstoneSlab);
	public static final BlockSlab derseBricksSlab = new BlockMSGTSlab.Half(MapColor.PURPLE, "derseBricksSlab", "derse_bricks_slab");
	public static final BlockSlab derseBricksDoubleSlab = new BlockMSGTSlab.Double(MapColor.PURPLE, "derseBricksSlab", "derse_bricks_slab_double", derseBricksSlab);

	public static final Block derseStoneStairs = new BlockMSGTStairs(derseStone.getDefaultState()).setUnlocalizedName("derseStoneStairs").setRegistryName("derse_stone_stairs");
	public static final Block derseBricksStairs = new BlockMSGTStairs(derseStone.getDefaultState()).setUnlocalizedName("derseBricksStairs").setRegistryName("derse_bricks_stairs");

	public static final Block derseStoneWall = new BlockWall(MapColor.PURPLE).setUnlocalizedName("derseStoneWall").setRegistryName("derse_stone_wall");
	public static final Block derseBricksWall = new BlockWall(MapColor.PURPLE).setUnlocalizedName("derseBricksWall").setRegistryName("derse_bricks_wall");

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event)
	{



		for(EnumAspect aspect : EnumAspect.values())
		{
			heroStones.put(aspect, new BlockHeroStone(aspect, false));
			chiseledHeroStones.put(aspect, new BlockHeroStone(aspect, true));
			heroStoneWalls.put(aspect, new BlockHeroStoneWall(aspect));
			//spectralHeroStones.put(aspect, new BlockSpectralHeroStone(aspect));
		}

		IForgeRegistry<Block> registry = event.getRegistry();

		blocks.forEach(block -> registry.register(block));

		/*
		heroStones.forEach((key, block) -> registry.registerBadgeEvents(block));
		registry.registerBadgeEvents(wildcardHeroStone);
		chiseledHeroStones.forEach((key, block) -> registry.registerBadgeEvents(block));
		registry.registerBadgeEvents(wildcardChiseledHeroStone);
		heroStoneWalls.forEach((key, block) -> registry.registerBadgeEvents(block));
		registry.registerBadgeEvents(wildcardHeroStoneWall);
		registry.registerBadgeEvents(glowingHeroStone);
		registry.registerBadgeEvents(heroLockBeacon);
		*/
	}

}
