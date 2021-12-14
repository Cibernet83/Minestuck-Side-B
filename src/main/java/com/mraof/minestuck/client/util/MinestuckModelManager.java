package com.mraof.minestuck.client.util;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.MinestuckConfig;
import com.mraof.minestuck.alchemy.GristType;
import com.mraof.minestuck.block.*;
import com.mraof.minestuck.entity.EntityFrog;
import com.mraof.minestuck.item.ItemBoondollars;
import com.mraof.minestuck.item.ItemMetalBoat;
import com.mraof.minestuck.item.ItemMinestuckBeverage;
import com.mraof.minestuck.util.IRegistryItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCactus;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockTNT;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Collections;

import static com.mraof.minestuck.block.MinestuckBlocks.returnNode;
import static com.mraof.minestuck.block.MinestuckBlocks.*;
import static com.mraof.minestuck.item.MinestuckItems.*;

@SideOnly(Side.CLIENT)
public class MinestuckModelManager
{
	@SubscribeEvent
	public static void handleModelRegistry(ModelRegistryEvent event)
	{
		itemModels();
		blockModels();
	}
	
	private static void itemModels()
	{
		//sheathed weapons TODO
		
		//meta variants
		register(disk, 0, "disk_client");
		register(disk, 1, "disk_server");


		for(int type = 0; type <= EntityFrog.maxTypes(); type++)
			register(itemFrog, type, "frog_"+type);

		register(minestuckBucket, 0, "bucket_oil");
		register(minestuckBucket, 1, "bucket_blood");
		register(minestuckBucket, 2, "bucket_brain_juice");
		register(minestuckBucket, 3, "bucket_watercolors");
		register(minestuckBucket, 4, "bucket_ender");
		register(minestuckBucket, 5, "bucket_light_water");

		for(int i = 0; i < ItemMetalBoat.NAMES.length; i++)
			register(metalBoat, i, "boat_" + ItemMetalBoat.NAMES[i]);

		register(candy, 0, "candy_corn");
		register(candy, GristType.REGISTRY.getID(GristType.Amber) + 1, "amber_gummy_worm");
		register(candy, GristType.REGISTRY.getID(GristType.Amethyst) + 1, "amethyst_hard_candy");
		register(candy, GristType.REGISTRY.getID(GristType.Artifact) + 1, "artifact_war_head");
		register(candy, GristType.REGISTRY.getID(GristType.Build) + 1, "build_gushers");
		register(candy, GristType.REGISTRY.getID(GristType.Caulk) + 1, "caulk_pretzel");
		register(candy, GristType.REGISTRY.getID(GristType.Chalk) + 1, "chalk_candy_cigarette");
		register(candy, GristType.REGISTRY.getID(GristType.Cobalt) + 1, "cobalt_gum");
		register(candy, GristType.REGISTRY.getID(GristType.Diamond) + 1, "diamond_mint");
		register(candy, GristType.REGISTRY.getID(GristType.Garnet) + 1, "garnet_twix");
		register(candy, GristType.REGISTRY.getID(GristType.Gold) + 1, "gold_candy_ribbon");
		register(candy, GristType.REGISTRY.getID(GristType.Iodine) + 1, "iodine_licorice");
		register(candy, GristType.REGISTRY.getID(GristType.Marble) + 1, "marble_jawbreaker");
		register(candy, GristType.REGISTRY.getID(GristType.Mercury) + 1, "mercury_sixlets");
		register(candy, GristType.REGISTRY.getID(GristType.Quartz) + 1, "quartz_jelly_bean");
		register(candy, GristType.REGISTRY.getID(GristType.Ruby) + 1, "ruby_lollipop");
		register(candy, GristType.REGISTRY.getID(GristType.Rust) + 1, "rust_gummy_eye");
		register(candy, GristType.REGISTRY.getID(GristType.Shale) + 1, "shale_peep");
		register(candy, GristType.REGISTRY.getID(GristType.Sulfur) + 1, "sulfur_candy_apple");
		register(candy, GristType.REGISTRY.getID(GristType.Tar) + 1, "tar_black_licorice");
		register(candy, GristType.REGISTRY.getID(GristType.Uranium) + 1, "uranium_gummy_bear");
		register(candy, GristType.REGISTRY.getID(GristType.Zillium) + 1, "zillium_skittles");

		for(int i = 0; i < ItemMinestuckBeverage.NAMES.length; i++)
			register(beverage, i, ItemMinestuckBeverage.NAMES[i]);


		//Misc Renderers

		ModelLoader.registerItemVariants(stoneTablet, new ResourceLocation(Minestuck.MODID, "stone_tablet"), new ResourceLocation(Minestuck.MODID, "stone_tablet_written"));
		ModelLoader.setCustomMeshDefinition(stoneTablet, new StoneSlabDefinition());

		ModelLoader.registerItemVariants(boondollars, new ResourceLocation(Minestuck.MODID, "boondollars0"), new ResourceLocation(Minestuck.MODID, "boondollars1"), new ResourceLocation(Minestuck.MODID, "boondollars2"),
				new ResourceLocation(Minestuck.MODID, "boondollars3"), new ResourceLocation(Minestuck.MODID, "boondollars4"), new ResourceLocation(Minestuck.MODID, "boondollars5"), new ResourceLocation(Minestuck.MODID, "boondollars6"));
		ModelLoader.setCustomMeshDefinition(boondollars, new BoondollarsDefinition());

		ModelLoader.registerItemVariants(cruxiteDowel, new ResourceLocation(Minestuck.MODID, "dowel_uncarved"), new ResourceLocation(Minestuck.MODID, "dowel_carved"));
		ModelLoader.setCustomMeshDefinition(cruxiteDowel, new CruxiteDowelDefinition());

		ModelLoader.registerItemVariants(captchaCard, new ResourceLocation(Minestuck.MODID, "card_empty"), new ResourceLocation(Minestuck.MODID, "card_full"), new ResourceLocation(Minestuck.MODID, "card_punched"), new ResourceLocation(Minestuck.MODID, "card_ghost"));
		ModelLoader.setCustomMeshDefinition(captchaCard, new CaptchaCardDefinition());

		ModelLoader.registerItemVariants(shunt, new ResourceLocation(Minestuck.MODID, "shunt_empty"), new ResourceLocation(Minestuck.MODID, "shunt_full"));
		ModelLoader.setCustomMeshDefinition(shunt, new ShuntDefinition());

		//3D Models
		if(MinestuckConfig.oldItemModels)
		{
			register(zillyhooHammer, 0, "zillyhoo_hammer_old");
			register(fearNoAnvil, 0, "fear_no_anvil_old");
		} else
		{
			register(zillyhooHammer);
			register(fearNoAnvil);
		}

		//everything else
		for (IRegistryItem<Item> item : items)
			item.registerModel();
	}
	
	private static void blockModels()
	{
		for(BlockChessTile.BlockType type : BlockChessTile.BlockType.values())
			register(chessTile, type.ordinal(), "chesstile_"+type.name);
		//register(skaiaPortal); // FIXME: ????????
		register(transportalizer);
		register(blockComputerOff);
		register(oreCruxite, 0, "cruxite_stone");
		register(oreCruxite, 1, "cruxite_netherrack");
		register(oreCruxite, 2, "cruxite_cobblestone");
		register(oreCruxite, 3, "cruxite_sandstone");
		register(oreCruxite, 4, "cruxite_sandstone_red");
		register(oreCruxite, 5, "cruxite_end_stone");
		register(oreCruxite, 6, "cruxite_pink_stone");
		register(oreUranium, 0, "uranium_stone");
		register(oreUranium, 1, "uranium_netherrack");
		register(oreUranium, 2, "uranium_cobblestone");
		register(oreUranium, 3, "uranium_sandstone");
		register(oreUranium, 4, "uranium_sandstone_red");
		register(oreUranium, 5, "uranium_end_stone");
		register(oreUranium, 6, "uranium_pink_stone");
		register(cruxiteBlock);
		register(uraniumBlock);
		register(genericObject);
		register(coalOreNetherrack);
		register(ironOreEndStone);
		register(ironOreSandstone);
		register(ironOreSandstoneRed);
		register(goldOreSandstone);
		register(goldOreSandstoneRed);
		register(redstoneOreEndStone);
		register(quartzOreStone);
		register(coalOrePinkStone);
		register(lapisOrePinkStone);
		register(goldOrePinkStone);
		register(diamondOrePinkStone);
		for(BlockColoredDirt.BlockType type : BlockColoredDirt.BlockType.values())
			register(coloredDirt, type.ordinal(), "colored_dirt_"+type.name);
		register(layeredSand);

		register(miniAlchemiter,0, "machine_alchemiter");
		register(miniTotemLathe,0, "machine_lathe");
		register(miniCruxtruder,0, "machine_cruxtruder");
		register(miniPunchDesignix,0, "machine_designix");

			//register(sburbMachine, type.ordinal(), "machine_"+type.getName());

		for(BlockCrockerMachine.MachineType type : BlockCrockerMachine.MachineType.values())
			register(crockerMachine, type.ordinal(), "machine_"+type.getName());
		register(punchDesignix);
		register(totemlathe[0]);
		register(alchemiter[0]);
		/*register(holopad);
		register(jumperBlockExtension[0]);*/
		register(blender);
		register(cruxtruder);
		register(cruxtruderLid);
		register(petrifiedGrass);
		register(petrifiedPoppy);
		register(petrifiedLog);
		register(bloomingCactus);
		register(desertBush);
		register(glowingMushroom);
		register(glowingLog);
		register(glowingPlanks);
		register(frostPlanks);
		for(BlockMinestuckStone.BlockType type : BlockMinestuckStone.BlockType.values())
			register(stone, type.getMetadata(), type.getName());
		register(glowyGoop);
		register(coagulatedBlood);
		register(coarseStoneStairs);
		register(shadeBrickStairs);
		register(frostBrickStairs);
		register(castIronStairs);
		register(myceliumBrickStairs);
		register(frogStatueReplica);
		register(chessboard);

		for(BlockMinestuckLogVariant.BlockType type : BlockMinestuckLogVariant.BlockType.values())
			register(log, type.ordinal(), type.getName()+"_log");
		
		for(BlockMinestuckPlanks.BlockType type : BlockMinestuckPlanks.BlockType.values())
			register(planks, type.ordinal(), type.getName()+"_planks");
		
		for(BlockMinestuckLeavesVariant.BlockType type : BlockMinestuckLeavesVariant.BlockType.values())
			register(leaves1, type.ordinal(), type.getName()+"_leaves");
		
		for(BlockAspectSapling.BlockType type : BlockAspectSapling.BlockType.values())
			register(aspectSapling, type.ordinal(), type.getName()+"_sapling");
		
		register(rainbowSapling);
		
		for(BlockAspectLog.BlockType type : BlockAspectLog.BlockType.values())
			register(aspectLog1, type.ordinal(), type.getName()+"_log");
		for(BlockAspectLog2.BlockType type : BlockAspectLog2.BlockType.values())
			register(aspectLog2, type.ordinal(), type.getName()+"_log");
		for(BlockAspectLog3.BlockType type : BlockAspectLog3.BlockType.values())
			register(aspectLog3, type.ordinal(), type.getName()+"_log");
		
		for(BlockVanityLaptopOff.BlockType type : BlockVanityLaptopOff.BlockType.values())
			register(blockLaptopOff, type.ordinal(), type.getName()+"_computer");
		
		register(woodenCactus);
		register(sugarCube);
		register(appleCake);
		register(blueCake);
		register(coldCake);
		register(redCake);
		register(hotCake);
		register(reverseCake);
		register(fuchsiaCake);
		
		register(floweryMossBrick);
		register(floweryMossStone);
		register(treatedPlanks);
		register(coarseEndStone);
		register(endLog);
		register(endLeaves);
		register(endPlanks);
		register(endSapling);
		register(endGrass);
		register(strawberry);
		
		register(deadLog);
		register(deadPlanks);
		register(chalk);
		register(chalkBricks);
		register(chalkChisel);
		register(chalkPolish);
		register(pinkStoneSmooth);
		register(pinkStoneBricks);
		register(pinkStoneChisel);
		register(pinkStoneMossy);
		register(pinkStoneCracked);
		register(pinkStonePolish);
		register(denseCloud, 0, "dense_cloud");
		register(denseCloud, 1, "dense_cloud_bright");
		
		register(vein);
		register(veinCorner);
		register(veinCornerInverted);

		register(primedTnt);
		register(unstableTnt);
		register(instantTnt);
		register(woodenExplosiveButton);
		register(stoneExplosiveButton);
		
		register(uraniumCooker);

		for (MSBlockSlab slab : slabs.values())
		{
			ModelLoader.setCustomStateMapper(slab, (new StateMap.Builder()).ignore(MSBlockSlab.dummy).build());
			ModelLoader.setCustomStateMapper(slab.fullSlab, (new StateMap.Builder()).ignore(BlockSlab.HALF, MSBlockSlab.dummy).build());
		}


		ModelLoader.setCustomStateMapper(blockOil, (new StateMap.Builder()).ignore(BlockFluidBase.LEVEL).build());
		ModelLoader.setCustomStateMapper(blockBlood, (new StateMap.Builder()).ignore(BlockFluidBase.LEVEL).build());
		ModelLoader.setCustomStateMapper(blockBrainJuice, (new StateMap.Builder()).ignore(BlockFluidBase.LEVEL).build());
		ModelLoader.setCustomStateMapper(blockWatercolors, (new StateMap.Builder()).ignore(BlockFluidBase.LEVEL).build());
		ModelLoader.setCustomStateMapper(blockEnder, (new StateMap.Builder()).ignore(BlockFluidBase.LEVEL).build());
		ModelLoader.setCustomStateMapper(blockLightWater, (new StateMap.Builder()).ignore(BlockFluidBase.LEVEL).build());
		
		ModelLoader.setCustomStateMapper(primedTnt, (new StateMap.Builder()).ignore(BlockTNT.EXPLODE).build());
		ModelLoader.setCustomStateMapper(unstableTnt, (new StateMap.Builder()).ignore(BlockTNT.EXPLODE).build());
		ModelLoader.setCustomStateMapper(instantTnt, (new StateMap.Builder()).ignore(BlockTNT.EXPLODE).build());
		ModelLoader.setCustomStateMapper(log, (new StateMap.Builder()).withName(BlockMinestuckLogVariant.VARIANT).withSuffix("_log").build());
		ModelLoader.setCustomStateMapper(leaves1, (new StateMap.Builder()).withName(BlockMinestuckLeavesVariant.VARIANT).withSuffix("_leaves").build());
		ModelLoader.setCustomStateMapper(aspectSapling, (new StateMap.Builder()).withName(BlockAspectSapling.VARIANT).withSuffix("_sapling").build());
		ModelLoader.setCustomStateMapper(aspectLog1, (new StateMap.Builder()).withName(BlockAspectLog.VARIANT).withSuffix("_log").build());
		ModelLoader.setCustomStateMapper(aspectLog2, (new StateMap.Builder()).withName(BlockAspectLog2.VARIANT).withSuffix("_log").build());
		ModelLoader.setCustomStateMapper(aspectLog3, (new StateMap.Builder()).withName(BlockAspectLog3.VARIANT).withSuffix("_log").build());
		ModelLoader.setCustomStateMapper(woodenCactus, new StateMap.Builder().ignore(BlockCactus.AGE).build());
		ModelLoader.setCustomStateMapper(returnNode, (Block block) -> Collections.emptyMap());
		ModelLoader.setCustomStateMapper(gate, (Block block) -> Collections.emptyMap());
		ModelLoader.setCustomStateMapper(rabbitSpawner, (Block block) -> Collections.emptyMap());
		ModelLoader.setCustomStateMapper(strawberryStem, (new StateMap.Builder()).build());
	}
	
	private static void register(Item item)
	{
		if(item.getHasSubtypes())
			ModelLoader.setCustomMeshDefinition(item, new SubtypesItemDefinition(Item.REGISTRY.getNameForObject(item).toString()));
		else
			ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
	
	private static void register(Item item, int meta, String modelResource)
	{
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(Minestuck.MODID + ":" + modelResource, "inventory"));
	}
	
	private static void register(Block block)
	{
		Item item = Item.getItemFromBlock(block);
		if(item == Items.AIR)
			throw new IllegalArgumentException("That block doesn't have an item, and this method is only intended for blocks with a connected itemblock.");
		register(item);
	}
	
	private static void register(Block block, int meta, String modelResource)
	{
		Item item = Item.getItemFromBlock(block);
		if(item == Items.AIR)
			throw new IllegalArgumentException("That block doesn't have an item, and this method is only intended for blocks with a connected itemblock.");
		register(item, meta, modelResource);
	}

	public static class SubtypesItemDefinition implements ItemMeshDefinition
	{
		private String name;
		public SubtypesItemDefinition(String name)
		{
			this.name = name;
		}
		@Override
		public ModelResourceLocation getModelLocation(ItemStack stack)
		{
			return new ModelResourceLocation(name, "inventory");
		}
	}

	
	private static class CruxiteDowelDefinition implements ItemMeshDefinition
	{
		@Override
		public ModelResourceLocation getModelLocation(ItemStack stack)
		{
			return new ModelResourceLocation(Minestuck.MODID + ":" + (stack.hasTagCompound() && stack.getTagCompound().hasKey("contentID") ? "dowel_carved" : "dowel_uncarved"), "inventory");
		}
	}
	
	private static class ColoredItemDefinition implements ItemMeshDefinition
	{
		private String name;
		ColoredItemDefinition(String name)
		{
			this.name = name;
		}
		@Override
		public ModelResourceLocation getModelLocation(ItemStack stack)
		{
			if(stack.getMetadata() == 0)
				return new ModelResourceLocation(name, "inventory");
			else return new ModelResourceLocation(name + "_blank", "inventory");
		}
	}
	
	private static class CaptchaCardDefinition implements ItemMeshDefinition
	{
		@Override
		public ModelResourceLocation getModelLocation(ItemStack stack)
		{
			NBTTagCompound nbt = stack.getTagCompound();
			String str;
			if(nbt != null && nbt.hasKey("contentID"))
			{
				if(nbt.getBoolean("punched") && !(Item.REGISTRY.getObject(new ResourceLocation(nbt.getString("contentID"))) == Item.getItemFromBlock(genericObject)))
					str = "card_punched";
				else if(nbt.getInteger("contentSize") <= 0) str = "card_ghost";
				else str = "card_full";
			}
			else str = "card_empty";
			return new ModelResourceLocation(Minestuck.MODID+":" + str, "inventory");
		}
	}

	private static class ShuntDefinition implements ItemMeshDefinition
	{
		@Override
		public ModelResourceLocation getModelLocation(ItemStack stack)
		{
			NBTTagCompound nbt = stack.getTagCompound();
			String str;
			if(nbt != null && nbt.hasKey("contentID"))
				str = "shunt_full";

			else str = "shunt_empty";
			return new ModelResourceLocation(Minestuck.MODID+":" + str, "inventory");
		}
	}

	private static class StoneSlabDefinition implements ItemMeshDefinition
	{
		@Override
		public ModelResourceLocation getModelLocation(ItemStack stack)
		{
			NBTTagCompound nbt = stack.getTagCompound();
			String str = "stone_tablet";
			if(nbt != null && nbt.hasKey("text") && nbt.getString("text") != "")
				str += "_written";

			return new ModelResourceLocation(Minestuck.MODID+":" + str, "inventory");
		}
	}

	private static class BoondollarsDefinition implements ItemMeshDefinition
	{
		@Override
		public ModelResourceLocation getModelLocation(ItemStack stack)
		{
			int size = ItemBoondollars.getCount(stack);
			String str;
			if(size < 5)
				str = "boondollars0";
			else if(size < 15)
				str = "boondollars1";
			else if(size < 50)
				str = "boondollars2";
			else if(size < 100)
				str = "boondollars3";
			else if(size < 250)
				str = "boondollars4";
			else if(size < 1000)
				str = "boondollars5";
			else str = "boondollars6";
			
			return new ModelResourceLocation(Minestuck.MODID+":" + str, "inventory");
		}
	}
}
