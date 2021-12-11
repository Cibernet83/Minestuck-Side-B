package com.cibernet.fetchmodiplus.registries;

import com.cibernet.fetchmodiplus.FetchModiPlus;
import com.mraof.minestuck.alchemy.CombinationRegistry;
import static com.mraof.minestuck.alchemy.CombinationRegistry.Mode.*;

import com.mraof.minestuck.alchemy.GristRegistry;
import com.mraof.minestuck.alchemy.GristSet;
import com.mraof.minestuck.alchemy.GristType;
import static com.mraof.minestuck.alchemy.GristType.*;

import com.mraof.minestuck.block.MinestuckBlocks;
import com.mraof.minestuck.item.MinestuckItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class FMPAlchemy
{
	public static final void registerGristConversionRecipes()
	{
		GristRegistry.addGristConversion(new ItemStack(FMPItems.hardStone), new GristSet(new GristType[] {Build}, new int[] {4}));
		GristRegistry.addGristConversion(new ItemStack(FMPItems.floatStone), new GristSet(new GristType[] {Build}, new int[] {400}));
		GristRegistry.addGristConversion(new ItemStack(FMPItems.energyCell), new GristSet(new GristType[] {Gold, Rust, Uranium}, new int[] {20, 10, 10}));
		GristRegistry.addGristConversion(new ItemStack(FMPItems.popBall), new GristSet(new GristType[] {Iodine, Amber, Shale}, new int[] {8, 5, 2}));
		GristRegistry.addGristConversion(new ItemStack(FMPItems.dragonGel), new GristSet(new GristType[] {Build, Sulfur, Uranium, Zillium}, new int[] {1010, 500, 742, 1525}));
		
		GristRegistry.addGristConversion(new ItemStack(FMPItems.operandiBlock), new GristSet(new GristType[] {Build, Garnet}, new int[] {1, 1}));
		GristRegistry.addGristConversion(new ItemStack(FMPItems.operandiStone), new GristSet(new GristType[] {Build, Garnet}, new int[] {1, 1}));
		GristRegistry.addGristConversion(new ItemStack(FMPItems.operandiLog), new GristSet(new GristType[] {Build, Garnet}, new int[] {1, 1}));
		GristRegistry.addGristConversion(new ItemStack(FMPItems.operandiGlass), new GristSet(new GristType[] {Build, Garnet}, new int[] {1, 1}));
		
		GristRegistry.addGristConversion(new ItemStack(FMPItems.cycloneModus), new GristSet(new GristType[] {Build}, new int[] {16}));
		GristRegistry.addGristConversion(new ItemStack(FMPItems.wildMagicModus), new GristSet(new GristType[] {Build, Garnet, Amethyst}, new int[] {310, 32, 65}));
		GristRegistry.addGristConversion(new ItemStack(FMPItems.capitalistModus), new GristSet(new GristType[] {Build, Gold, Diamond}, new int[] {270, 55, 5}));
		GristRegistry.addGristConversion(new ItemStack(FMPItems.deckModus), new GristSet(new GristType[] {Build, Ruby}, new int[] {250, 52}));
		GristRegistry.addGristConversion(new ItemStack(FMPItems.popTartModus), new GristSet(new GristType[] {Build, Chalk, Iodine}, new int[] {320, 34, 20}));
		GristRegistry.addGristConversion(new ItemStack(FMPItems.eightBallModus), new GristSet(new GristType[] {Build, Mercury, Cobalt}, new int[] {320, 16, 38}));
		GristRegistry.addGristConversion(new ItemStack(FMPItems.eightBallModus), new GristSet(new GristType[] {Build, Mercury, Cobalt}, new int[] {320, 16, 38}));
		GristRegistry.addGristConversion(new ItemStack(FMPItems.scratchAndSniffModus), new GristSet(new GristType[] {Build, Amethyst, Cobalt}, new int[] {260, 40, 20}));
		GristRegistry.addGristConversion(new ItemStack(FMPItems.chasityModus), new GristSet(new GristType[] {Build, Rust, Quartz}, new int[] {210, 36, 2}));
		GristRegistry.addGristConversion(new ItemStack(FMPItems.slimeModus), new GristSet(new GristType[] {Build, Caulk}, new int[] {340, 28}));
		GristRegistry.addGristConversion(new ItemStack(FMPItems.onionModus), new GristSet(new GristType[] {Build, Iodine, Amber}, new int[] {210, 30, 13}));
		GristRegistry.addGristConversion(new ItemStack(FMPItems.chatModus), new GristSet(new GristType[] {Build, Garnet, Artifact}, new int[] {310, 32, 10}));
		GristRegistry.addGristConversion(new ItemStack(FMPItems.hueModus), new GristSet(new GristType[] {Build, Cobalt, Amber, Amethyst, Ruby}, new int[] {290, 3, 3, 3, 3}));
		GristRegistry.addGristConversion(new ItemStack(FMPItems.hueStackModus), new GristSet(new GristType[] {Build, Cobalt, Amber, Amethyst, Ruby, Zillium}, new int[] {585, 12, 12, 12, 12, 1}));
		GristRegistry.addGristConversion(new ItemStack(FMPItems.jujuModus), new GristSet(new GristType[] {Build, Zillium}, new int[] {604, 100}));
		GristRegistry.addGristConversion(new ItemStack(FMPItems.modUs), new GristSet(new GristType[] {Build, Gold, Ruby}, new int[] {707, 128, 128}));
		GristRegistry.addGristConversion(new ItemStack(FMPItems.alcheModus), new GristSet(new GristType[] {Build, Uranium, Zillium}, new int[] {2500, 640, 4}));
		GristRegistry.addGristConversion(new ItemStack(FMPItems.operandiModus), new GristSet(new GristType[] {Build, Garnet, Shale}, new int[] {340, 24, 33}));
		GristRegistry.addGristConversion(new ItemStack(FMPItems.weightModus), new GristSet(new GristType[] {Build, Rust}, new int[] {32, 10}));
		GristRegistry.addGristConversion(new ItemStack(FMPItems.energyModus), new GristSet(new GristType[] {Build, Gold, Uranium}, new int[] {180, 32, 21}));
		GristRegistry.addGristConversion(new ItemStack(FMPItems.bookModus), new GristSet(new GristType[] {Build, Iodine, Chalk}, new int[] {980, 18, 26}));


		GristRegistry.addGristConversion(new ItemStack(FMPItems.arrayModus), new GristSet(new GristType[] {Build}, new int[] {350}));
		GristRegistry.addGristConversion(new ItemStack(FMPItems.walletModus), new GristSet(new GristType[] {Build, Iodine, Diamond, Zillium, Artifact}, new int[] {3000, 540, 20, 4050, 110}));
		GristRegistry.addGristConversion(new ItemStack(FMPItems.crystalBallModus), new GristSet(new GristType[] {Build, Iodine, Diamond, Zillium, Artifact, Cobalt, Iodine}, new int[] {4000, 800, 40, 10000, 500, 800, 400}));
		GristRegistry.addGristConversion(new ItemStack(FMPItems.monsterModus), new GristSet(new GristType[] {Build, Rust, Chalk}, new int[] {230, 24, 17}));

		/*
		GristRegistry.addGristConversion(new ItemStack(FMPItems.memoryModus), new GristSet(new GristType[] {Build, Amethyst, Chalk}, new int[] {140, 22, 8}));
		GristRegistry.addGristConversion(new ItemStack(FMPItems.recipeModus), new GristSet(new GristType[] {Build, Garnet, Ruby}, new int[] {180, 12, 8}));
		GristRegistry.addGristConversion(new ItemStack(FMPItems.bottledMsgModus), new GristSet(new GristType[] {Build, Quartz}, new int[] {250, 7}));
		GristRegistry.addGristConversion(new ItemStack(FMPItems.techHopModus), new GristSet(new GristType[] {Build, Caulk, Diamond}, new int[] {280, 26, 4}));
		GristRegistry.addGristConversion(new ItemStack(FMPItems.encryptionModus), new GristSet(new GristType[] {Build, Artifact, Uranium}, new int[] {240, 16, 8}));
		GristRegistry.addGristConversion(new ItemStack(FMPItems.ouijaModus), new GristSet(new GristType[] {Build, Amber, Amethyst}, new int[] {200, 12, 23}));
		GristRegistry.addGristConversion(new ItemStack(FMPItems.bundleModus), new GristSet(new GristType[] {Build, Iodine, Chalk}, new int[] {160, 16, 12}));
		GristRegistry.addGristConversion(new ItemStack(FMPItems.cakeModus), new GristSet(new GristType[] {Build, Amber, Chalk}, new int[] {210, 18, 32}));
		GristRegistry.addGristConversion(new ItemStack(FMPItems.cipherModus), new GristSet(new GristType[] {Build, Rust, Amber}, new int[] {190, 22, 26}));
		*/

		GristRegistry.addGristConversion(new ItemStack(FMPItems.hashchatModus), new GristSet(new GristType[] {Build, Garnet, Artifact}, new int[] {510, 64, 10}));
		GristRegistry.addGristConversion(new ItemStack(FMPItems.sacrificeModus), new GristSet(new GristType[] {Build, Rust, Tar}, new int[] {210, 45, 8}));

		if(FetchModiPlus.isChiselLoaded)
			registerChiselRecipes();
		if(FetchModiPlus.isMysticalWorldLoaded)
			registerMysticalWorldRecipes();
		if(FetchModiPlus.isBOPLoaded)
			registerBOPRecipes();
		if(FetchModiPlus.isMekanismLoaded)
			GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("mekanism", "ingot")), 1, 1), true, new GristSet(new GristType[] {Mercury, Rust}, new int[] {8, 12}));
		if(FetchModiPlus.isCyclicLoaded)
		{
			GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("cyclicmagic", "crystalized_amber"))), false, new GristSet(new GristType[] {Amber}, new int[] {20}));
			GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("cyclicmagic", "crystalized_obsidian"))), false, new GristSet(new GristType[] {Tar, Cobalt, Amber, Diamond}, new int[] {40, 20, 40, 8}));
		}
		if(FetchModiPlus.isVampirismLoaded)
		{
			GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("vampirism", "blood_bottle"))), false, new GristSet(new GristType[] {Garnet, Iodine}, new int[] {16, 16}));
			GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("vampirism", "vampire_blood_bottle"))), false, new GristSet(new GristType[] {Garnet, Iodine, Ruby}, new int[] {20, 8, 4}));
			GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("vampirism", "vampirism_flower"))), false, new GristSet(new GristType[] {Iodine, Shale, Garnet}, new int[] {2, 4, 2}));
		}
		if(FetchModiPlus.isIndustrialForegoingLoaded)
			GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("industrialforegoing", "plastic"))), false, new GristSet(new GristType[] {Build, Quartz}, new int[] {500, 1}));

	}
	
	public static final void registerCombinationRecipes()
	{
		CombinationRegistry.addCombination(new ItemStack(MinestuckItems.rawCruxite), new ItemStack(Items.EMERALD), MODE_OR, new ItemStack(MinestuckItems.boondollars));
		
		CombinationRegistry.addCombination(new ItemStack(Blocks.STONE), new ItemStack(Blocks.OBSIDIAN), MODE_OR, new ItemStack(FMPItems.hardStone));
		CombinationRegistry.addCombination(new ItemStack(Blocks.STONE), new ItemStack(Blocks.BEDROCK), MODE_AND, new ItemStack(FMPItems.hardStone));
		CombinationRegistry.addCombination(new ItemStack(MinestuckBlocks.cruxiteBlock), new ItemStack(Items.FEATHER), MODE_AND, new ItemStack(FMPItems.floatStone));
		CombinationRegistry.addCombination(new ItemStack(Items.ENDER_EYE), new ItemStack(Items.BOOK), MODE_OR, new ItemStack(FMPItems.eightBall));
		CombinationRegistry.addCombination(new ItemStack(FMPItems.eightBall), new ItemStack(FMPItems.popTart), MODE_AND, new ItemStack(FMPItems.popBall));
		CombinationRegistry.addCombination(new ItemStack(FMPItems.cruxiteGel), new ItemStack(Items.DRAGON_BREATH), MODE_OR, false, false, new ItemStack(FMPItems.dragonGel));
		CombinationRegistry.addCombination(new ItemStack(FMPItems.dragonGel), new ItemStack(FMPItems.popBall), MODE_AND, Zillium.getCandyItem());
		
		CombinationRegistry.addCombination(new ItemStack(MinestuckItems.grimoire), new ItemStack(MinestuckItems.modusCard), MODE_OR, true, false, new ItemStack(FMPItems.wildMagicModus));
		CombinationRegistry.addCombination(new ItemStack(Items.BOOK), new ItemStack(MinestuckItems.modusCard), MODE_OR, true, false, new ItemStack(FMPItems.bookModus));
		CombinationRegistry.addCombination(new ItemStack(Items.WRITABLE_BOOK), new ItemStack(MinestuckItems.modusCard), MODE_AND, true, false, new ItemStack(FMPItems.bookModus));
		CombinationRegistry.addCombination(new ItemStack(Items.WRITTEN_BOOK), new ItemStack(MinestuckItems.modusCard), MODE_AND, true, false, new ItemStack(FMPItems.bookModus));
		CombinationRegistry.addCombination(new ItemStack(Items.ENCHANTED_BOOK), new ItemStack(MinestuckItems.modusCard), MODE_AND, true, false, new ItemStack(FMPItems.bookModus));
		CombinationRegistry.addCombination(new ItemStack(MinestuckItems.boondollars), new ItemStack(MinestuckItems.modusCard), MODE_OR, true, false, new ItemStack(FMPItems.capitalistModus));
		CombinationRegistry.addCombination(new ItemStack(MinestuckItems.captchaCard), new ItemStack(MinestuckItems.modusCard, 1, 5), MODE_AND, new ItemStack(FMPItems.deckModus));
		CombinationRegistry.addCombination(new ItemStack(Items.CAKE), new ItemStack(MinestuckItems.modusCard), MODE_AND, true, false, new ItemStack(FMPItems.popTartModus));
		CombinationRegistry.addCombination(new ItemStack(FMPItems.eightBall), new ItemStack(MinestuckItems.modusCard), MODE_OR, true, false, new ItemStack(FMPItems.eightBallModus));
		CombinationRegistry.addCombination(new ItemStack(FMPItems.bookModus), new ItemStack(Items.ENDER_EYE), MODE_OR, true, false, new ItemStack(FMPItems.eightBallModus));
		CombinationRegistry.addCombination(new ItemStack(Items.DYE), new ItemStack(MinestuckItems.modusCard, 1, 1), MODE_OR, false, true, new ItemStack(FMPItems.hueModus));
		CombinationRegistry.addCombination(new ItemStack(Items.DYE), new ItemStack(MinestuckItems.modusCard, 1, 2), MODE_OR, false, true, new ItemStack(FMPItems.hueStackModus));
		CombinationRegistry.addCombination(new ItemStack(FMPItems.hueModus), new ItemStack(MinestuckItems.modusCard, 1, 0), MODE_AND, new ItemStack(FMPItems.hueStackModus));
		CombinationRegistry.addCombination(new ItemStack(MinestuckBlocks.blockComputerOff), new ItemStack(MinestuckItems.modusCard, 1, 5), MODE_OR, false, true, new ItemStack(FMPItems.chatModus));
		CombinationRegistry.addCombination(new ItemStack(Items.ITEM_FRAME), new ItemStack(MinestuckItems.modusCard, 1, 4), MODE_AND, false, true, new ItemStack(FMPItems.chatModus));
		CombinationRegistry.addCombination(new ItemStack(FMPItems.deckModus), new ItemStack(FMPItems.hueModus), MODE_AND, new ItemStack(FMPItems.scratchAndSniffModus));
		CombinationRegistry.addCombination(new ItemStack(Items.APPLE), new ItemStack(MinestuckItems.modusCard, 1, 5), MODE_OR, new ItemStack(FMPItems.scratchAndSniffModus));
		CombinationRegistry.addCombination(new ItemStack(Blocks.LEVER), new ItemStack(FMPItems.operandiModus), MODE_AND, new ItemStack(FMPItems.chasityModus));
		CombinationRegistry.addCombination(new ItemStack(Blocks.IRON_TRAPDOOR), new ItemStack(MinestuckItems.modusCard), MODE_OR, true, false, new ItemStack(FMPItems.chasityModus));
		CombinationRegistry.addCombination(new ItemStack(Blocks.TRAPDOOR), new ItemStack(FMPItems.weightModus), MODE_OR, true, false, new ItemStack(FMPItems.chasityModus));
		CombinationRegistry.addCombination(new ItemStack(Items.SLIME_BALL), new ItemStack(MinestuckItems.modusCard, 1, 5), MODE_AND,true, false, new ItemStack(FMPItems.slimeModus));
		CombinationRegistry.addCombination(new ItemStack(FMPItems.cycloneModus), new ItemStack(MinestuckItems.onion), MODE_OR, new ItemStack(FMPItems.onionModus));
		CombinationRegistry.addCombination(new ItemStack(FMPItems.cycloneModus), new ItemStack(MinestuckItems.modusCard, 1, 3), MODE_AND, new ItemStack(FMPItems.onionModus));
		CombinationRegistry.addCombination(new ItemStack(FMPItems.energyCell), new ItemStack(MinestuckItems.modusCard, 4), MODE_OR, false, true,  new ItemStack(FMPItems.energyModus));
		CombinationRegistry.addCombination(new ItemStack(FMPItems.weightModus), new ItemStack(MinestuckItems.energyCore), MODE_AND,  new ItemStack(FMPItems.energyModus));
		CombinationRegistry.addCombination(new ItemStack(FMPItems.eightBallModus), new ItemStack(FMPItems.popTartModus), MODE_AND, new ItemStack(FMPItems.operandiModus));
		CombinationRegistry.addCombination(new ItemStack(Items.REDSTONE), new ItemStack(MinestuckItems.modusCard, 1, 4), MODE_AND, new ItemStack(FMPItems.operandiModus));
		CombinationRegistry.addCombination(new ItemStack(Blocks.IRON_BLOCK), new ItemStack(MinestuckItems.modusCard), MODE_OR, true, false, new ItemStack(FMPItems.weightModus));
		CombinationRegistry.addCombination(new ItemStack(Blocks.CHEST), new ItemStack(FMPItems.jujuModus), MODE_AND, new ItemStack(FMPItems.modUs));
		CombinationRegistry.addCombination(new ItemStack(MinestuckBlocks.alchemiter[0]), new ItemStack(MinestuckItems.modusCard), MODE_OR, true, false, new ItemStack(FMPItems.alcheModus));
		CombinationRegistry.addCombination(new ItemStack(MinestuckBlocks.sburbMachine, 1, 3), new ItemStack(MinestuckItems.modusCard), MODE_OR, true, false, new ItemStack(FMPItems.alcheModus));



		CombinationRegistry.addCombination(new ItemStack(MinestuckItems.rawCruxite), new ItemStack(MinestuckItems.modusCard), MODE_AND, false, false, new ItemStack(FMPItems.arrayModus));
		CombinationRegistry.addCombination(new ItemStack(FMPItems.arrayModus), new ItemStack(Items.LEATHER), MODE_AND, false, false, new ItemStack(FMPItems.walletModus));
		CombinationRegistry.addCombination(new ItemStack(FMPItems.walletModus), new ItemStack(FMPItems.eightBallModus), MODE_AND, false, false, new ItemStack(FMPItems.crystalBallModus));
		CombinationRegistry.addCombination(new ItemStack(FMPItems.walletModus), new ItemStack(FMPItems.eightBall), MODE_AND, false, false, new ItemStack(FMPItems.crystalBallModus));
		CombinationRegistry.addCombination(new ItemStack(MinestuckItems.modusCard), new ItemStack(FMPItems.crystalEightBall), MODE_OR, false, false, new ItemStack(FMPItems.crystalBallModus));
		CombinationRegistry.addCombination(new ItemStack(FMPItems.arrayModus), new ItemStack(FMPItems.crystalEightBall), MODE_OR, false, false, new ItemStack(FMPItems.crystalBallModus));
		CombinationRegistry.addCombination(new ItemStack(MinestuckItems.modusCard), new ItemStack(Items.ROTTEN_FLESH), MODE_OR, false, false, new ItemStack(FMPItems.monsterModus));
		CombinationRegistry.addCombination(new ItemStack(MinestuckItems.modusCard), new ItemStack(Items.BONE), MODE_OR, false, false, new ItemStack(FMPItems.monsterModus));

		/*
		CombinationRegistry.addCombination(new ItemStack(Items.DYE, 1, 5), new ItemStack(FMPItems.deckModus), MODE_OR, true, true, new ItemStack(FMPItems.memoryModus));
		CombinationRegistry.addCombination(new ItemStack(MinestuckItems.minestuckBucket, 1, 2), new ItemStack(MinestuckItems.modusCard, 1, 5), MODE_OR, true, true, new ItemStack(FMPItems.memoryModus));
		CombinationRegistry.addCombination(new ItemStack(MinestuckItems.crockerSpork), new ItemStack(MinestuckItems.modusCard), MODE_AND, false, false, new ItemStack(FMPItems.recipeModus));
		CombinationRegistry.addCombination(new ItemStack(Items.GLASS_BOTTLE), new ItemStack(MinestuckItems.modusCard), MODE_OR, false, false, new ItemStack(FMPItems.bottledMsgModus));
		CombinationRegistry.addCombination(new ItemStack(FMPItems.cruxiteBottle), new ItemStack(MinestuckItems.modusCard), MODE_OR, false, false, new ItemStack(FMPItems.bottledMsgModus));
		CombinationRegistry.addCombination(new ItemStack(Blocks.JUKEBOX), new ItemStack(MinestuckItems.modusCard, 1, 4), MODE_OR, false, true, new ItemStack(FMPItems.techHopModus));
		CombinationRegistry.addCombination(new ItemStack(FMPItems.chasityModus), new ItemStack(MinestuckBlocks.blockComputerOff), MODE_AND, false, true, new ItemStack(FMPItems.encryptionModus));
		CombinationRegistry.addCombination(new ItemStack(Blocks.IRON_TRAPDOOR), new ItemStack(MinestuckItems.modusCard, 1, 4), MODE_OR, false, true, new ItemStack(FMPItems.encryptionModus));
		CombinationRegistry.addCombination(new ItemStack(FMPItems.chatModus), new ItemStack(MinestuckItems.grimoire), MODE_AND, false, false, new ItemStack(FMPItems.ouijaModus));
		CombinationRegistry.addCombination(new ItemStack(MinestuckItems.modusCard, 1, 2), new ItemStack(Items.RABBIT_HIDE), MODE_OR, true, false, new ItemStack(FMPItems.bundleModus));
		CombinationRegistry.addCombination(new ItemStack(Items.CAKE), new ItemStack(MinestuckItems.modusCard), MODE_OR, true, false, new ItemStack(FMPItems.cakeModus));
		CombinationRegistry.addCombination(new ItemStack(FMPItems.popTartModus), new ItemStack(FMPItems.bundleModus), MODE_AND, false, false, new ItemStack(FMPItems.cakeModus));
		CombinationRegistry.addCombination(new ItemStack(FMPItems.popTart), new ItemStack(FMPItems.bundleModus), MODE_AND, false, false, new ItemStack(FMPItems.cakeModus));
		CombinationRegistry.addCombination(new ItemStack(FMPItems.encryptionModus), new ItemStack(FMPItems.bookModus), MODE_OR, false, false, new ItemStack(FMPItems.cipherModus));
		*/

		CombinationRegistry.addCombination(new ItemStack(MinestuckItems.modusCard, 1, 4), new ItemStack(FMPItems.chatModus), MODE_OR, true, false, new ItemStack(FMPItems.hashchatModus));
		CombinationRegistry.addCombination(new ItemStack(FMPItems.monsterModus), new ItemStack(MinestuckItems.minestuckBucket, 1, 1), MODE_OR, false, true, new ItemStack(FMPItems.sacrificeModus));

		if(FetchModiPlus.isMSULoaded)
		{
			CombinationRegistry.addCombination(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("minestuckuniverse", "zillystone_shard"))),
					new ItemStack(MinestuckItems.modusCard), MODE_AND, true, false, new ItemStack(FMPItems.jujuModus));
			//CombinationRegistry.addCombination(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("minestuckuniverse", "moonstone"))),
			//		new ItemStack(FMPItems.slimeModus), MODE_AND, true, false, new ItemStack(FMPItems.monsterModus));
		}
		else CombinationRegistry.addCombination(Zillium.getCandyItem(), new ItemStack(MinestuckItems.modusCard, 5), MODE_AND, true, false, new ItemStack(FMPItems.jujuModus));
	}

	private static final void registerMysticalWorldRecipes()
	{
		String modid = "mysticalworld";
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(modid, "amethyst_gem"))), false, new GristSet(new GristType[] {Amethyst}, new int[] {18}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(modid, "mud_block"))), false, new GristSet(new GristType[] {Build}, new int[] {2}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(modid, "carapace"))), false, new GristSet(new GristType[] {Shale, Iodine}, new int[] {8, 5}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(modid, "venison"))), false, new GristSet(new GristType[] {Iodine}, new int[] {12}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(modid, "cooked_venison"))), false, new GristSet(new GristType[] {Tar, Iodine}, new int[] {1, 12}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(modid, "antlers"))), false, new GristSet(new GristType[] {Chalk, Build}, new int[] {12, 2}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(modid, "pelt"))), false, new GristSet(new GristType[] {Chalk, Iodine}, new int[] {3, 3}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(modid, "raw_squid"))), false, new GristSet(new GristType[] {Cobalt, Iodine}, new int[] {1, 8}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(modid, "cooked_squid"))), false, new GristSet(new GristType[] {Tar, Iodine}, new int[] {1, 8}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(modid, "aubergine"))), false, new GristSet(new GristType[] {Amethyst, Amber}, new int[] {2, 2}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(modid, "cooked_aubergine"))), false, new GristSet(new GristType[] {Amethyst, Amber, Tar}, new int[] {2, 2, 1}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(modid, "silk_cocoon"))), false, new GristSet(new GristType[] {Chalk}, new int[] {8}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(modid, "silk_thread"))), false, new GristSet(new GristType[] {Chalk}, new int[] {1}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(modid, "gall_apple"))), false, new GristSet(new GristType[] {Shale, Iodine}, new int[] {2, 2}));
	}

	private static final void registerChiselRecipes()
	{
		String modid = "chisel";
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(modid, "planks-oak"))), false, new GristSet(new GristType[] {Build}, new int[] {2}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(modid, "planks-birch"))), false, new GristSet(new GristType[] {Build}, new int[] {2}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(modid, "planks-spruce"))), false, new GristSet(new GristType[] {Build}, new int[] {2}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(modid, "planks-jungle"))), false, new GristSet(new GristType[] {Build}, new int[] {2}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(modid, "planks-acacia"))), false, new GristSet(new GristType[] {Build}, new int[] {2}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(modid, "planks-dark-oak"))), false, new GristSet(new GristType[] {Build}, new int[] {2}));

		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(modid, "marble"))), false, new GristSet(new GristType[] {Build, Marble}, new int[] {1, 1}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(modid, "marble1"))), false, new GristSet(new GristType[] {Build, Marble}, new int[] {1, 1}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(modid, "marble2"))), false, new GristSet(new GristType[] {Build, Marble}, new int[] {1, 1}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(modid, "marblepillar"))), false, new GristSet(new GristType[] {Build, Marble}, new int[] {1, 1}));

		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(modid, "glass"))), false, new GristSet(new GristType[] {Build}, new int[] {1}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(modid, "stonebrick"))), false, new GristSet(new GristType[] {Build}, new int[] {2}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(modid, "cobblestone"))), false, new GristSet(new GristType[] {Build}, new int[] {2}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(modid, "cobblestone1"))), false, new GristSet(new GristType[] {Build}, new int[] {2}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(modid, "cobblestone2"))), false, new GristSet(new GristType[] {Build}, new int[] {2}));
	}

	private static final void registerBOPRecipes()
	{
		//dirt
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "grass")), 1, 0), new GristSet(new GristType[] {Build, Caulk}, new int[] {4, 3}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "grass")), 1, 1), new GristSet(new GristType[] {Build}, new int[] {2}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "grass")), 1, 2), new GristSet(new GristType[] {Build}, new int[] {2}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "grass")), 1, 3), new GristSet(new GristType[] {Build}, new int[] {2}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "grass")), 1, 4), new GristSet(new GristType[] {Build}, new int[] {2}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "grass")), 1, 5), new GristSet(new GristType[] {Build}, new int[] {2}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "grass")), 1, 6), new GristSet(new GristType[] {Build, Tar}, new int[] {2, 1}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "grass")), 1, 7), new GristSet(new GristType[] {Build}, new int[] {2}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "grass")), 1, 8), new GristSet(new GristType[] {Build, Tar, Iodine, Ruby}, new int[] {2, 1, 2, 2}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "dirt"))), false, new GristSet(new GristType[] {Build}, new int[] {2}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "mudball"))), false, new GristSet(new GristType[] {Build, Cobalt}, new int[] {1, 1}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "mud_brick"))), false, new GristSet(new GristType[] {Build}, new int[] {1}));

		//sand
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "white_sand"))), false, new GristSet(new GristType[] {Build, Chalk}, new int[] {1, 1}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "dried_sand"))), new GristSet(new GristType[] {Build}, new int[] {1}));

		//Flowers
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "flower_0")), 1, 0), true, new GristSet(new GristType[] {Iodine, Chalk, Tar}, new int[] {1, 6, 2}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "flower_0")), 1, 1), true, new GristSet(new GristType[] {Iodine, Amber, Amethyst}, new int[] {2, 4, 4}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "flower_0")), 1, 2), true, new GristSet(new GristType[] {Iodine, Uranium, Tar}, new int[] {2, 2, 8}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "flower_0")), 1, 3), true, new GristSet(new GristType[] {Iodine, Amber, Amethyst, Tar}, new int[] {1, 4, 4, 2}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "flower_0")), 1, 4), true, new GristSet(new GristType[] {Iodine, Chalk, Amethyst}, new int[] {1, 4, 4}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "flower_0")), 1, 5), true, new GristSet(new GristType[] {Iodine, Garnet, Amber}, new int[] {1, 4, 4}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "flower_0")), 1, 6), true, new GristSet(new GristType[] {Iodine, Chalk, Garnet}, new int[] {1, 4, 4}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "flower_0")), 1, 7), true, new GristSet(new GristType[] {Iodine, Amethyst, Garnet}, new int[] {1, 2, 6}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "flower_0")), 1, 8), true, new GristSet(new GristType[] {Iodine, Amethyst, Garnet}, new int[] {1, 4, 4}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "flower_0")), 1, 9), true, new GristSet(new GristType[] {Iodine, Chalk}, new int[] {1, 8}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "flower_0")), 1, 10), true, new GristSet(new GristType[] {Iodine, Caulk, Tar}, new int[] {1, 2, 8}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "flower_0")), 1, 11), true, new GristSet(new GristType[] {Iodine, Ruby}, new int[] {1, 8}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "flower_0")), 1, 12), true, new GristSet(new GristType[] {Iodine, Chalk, Tar}, new int[] {1, 2, 6}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "flower_0")), 1, 13), true, new GristSet(new GristType[] {Iodine, Chalk, Garnet}, new int[] {1, 4, 4}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "flower_0")), 1, 14), true, new GristSet(new GristType[] {Iodine, Chalk}, new int[] {1, 8}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "flower_0")), 1, 15), true, new GristSet(new GristType[] {Iodine, Amber, Garnet, Sulfur}, new int[] {1, 4, 4, 3}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "flower_1")), 1, 0), true, new GristSet(new GristType[] {Iodine, Amethyst, Garnet}, new int[] {1, 4, 4}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "flower_1")), 1, 1), true, new GristSet(new GristType[] {Iodine, Amber}, new int[] {1, 8}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "flower_1")), 1, 2), true, new GristSet(new GristType[] {Iodine, Amethyst}, new int[] {1, 8}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "flower_1")), 1, 3), true, new GristSet(new GristType[] {Iodine, Chalk, Garnet}, new int[] {2, 4, 4}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "flower_1")), 1, 4), true, new GristSet(new GristType[] {Iodine, Chalk, Cobalt}, new int[] {2, 4, 4}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "flower_1")), 1, 5), true, new GristSet(new GristType[] {Iodine, Garnet}, new int[] {2, 8}));

		//Plant Things
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "log_0"))), false, new GristSet(new GristType[] {Build}, new int[] {8}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "log_1"))), false, new GristSet(new GristType[] {Build}, new int[] {8}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "log_2"))), false, new GristSet(new GristType[] {Build}, new int[] {8}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "log_3"))), false, new GristSet(new GristType[] {Build}, new int[] {8}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "log_4"))), false, new GristSet(new GristType[] {Build}, new int[] {8}));

		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "leaves_0"))), false, new GristSet(new GristType[] {Build}, new int[] {1}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "leaves_1"))), false, new GristSet(new GristType[] {Build}, new int[] {1}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "leaves_2"))), false, new GristSet(new GristType[] {Build}, new int[] {1}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "leaves_3"))), false, new GristSet(new GristType[] {Build}, new int[] {1}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "leaves_4"))), false, new GristSet(new GristType[] {Build}, new int[] {1}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "leaves_5"))), false, new GristSet(new GristType[] {Build}, new int[] {1}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "leaves_6"))), false, new GristSet(new GristType[] {Build}, new int[] {1}));

		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "sapling_0"))), false, new GristSet(new GristType[] {Build}, new int[] {16}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "sapling_1"))), false, new GristSet(new GristType[] {Build}, new int[] {16}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "sapling_2"))), false, new GristSet(new GristType[] {Build}, new int[] {16}));

		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "plant_0"))), false, new GristSet(new GristType[] {Build}, new int[] {1}));

		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "plant_1")), 1, 0), new GristSet(new GristType[] {Build}, new int[] {1}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "plant_1")), 1, 1), new GristSet(new GristType[] {Build, Caulk}, new int[] {1, 1}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "plant_1")), 1, 2), new GristSet(new GristType[] {Build}, new int[] {1}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "plant_1")), 1, 3), new GristSet(new GristType[] {Build, Iodine}, new int[] {1, 2}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "plant_1")), 1, 4), new GristSet(new GristType[] {Build, Chalk}, new int[] {1, 1}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "plant_1")), 1, 4), new GristSet(new GristType[] {Build, Chalk}, new int[] {1, 1}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "plant_1")), 1, 5), new GristSet(new GristType[] {Build}, new int[] {1}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "plant_1")), 1, 6), new GristSet(new GristType[] {Amber, Iodine}, new int[] {3, 1}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "plant_1")), 1, 7), new GristSet(new GristType[] {Build}, new int[] {1}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "plant_1")), 1, 8), new GristSet(new GristType[] {Build}, new int[] {1}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "plant_1")), 1, 9), new GristSet(new GristType[] {Build}, new int[] {1}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "plant_1")), 1, 10), new GristSet(new GristType[] {Garnet, Shale}, new int[] {4, 1}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "plant_1")), 1, 11), new GristSet(new GristType[] {Build}, new int[] {1}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "bamboo"))), new GristSet(new GristType[] {Build}, new int[] {2}));

		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "double_plant")), 1, 0), true, new GristSet(new GristType[] {Iodine, Amethyst, Chalk}, new int[] {1, 4, 4}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "double_plant")), 1, 1), true, new GristSet(new GristType[] {Build, Chalk}, new int[] {2, 2}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "double_plant")), 1, 2), true, new GristSet(new GristType[] {Iodine, Sulfur}, new int[] {1, 3}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "double_plant")), 1, 3), true, new GristSet(new GristType[] {Iodine, Build}, new int[] {1, 3}));

		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "waterlily"))), false, new GristSet(new GristType[] {Amber, Iodine}, new int[] {4, 1}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "coral"))), false, new GristSet(new GristType[] {Cobalt, Amber, Iodine}, new int[] {1, 4, 1}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "seaweed"))), false, new GristSet(new GristType[] {Cobalt, Amber, Iodine}, new int[] {1, 4, 1}));

		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "willow_vine"))), false, new GristSet(new GristType[] {Build, Amber}, new int[] {2, 1}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "ivy"))), false, new GristSet(new GristType[] {Build, Amber}, new int[] {2, 1}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "bramble_plant"))), false, new GristSet(new GristType[] {Build}, new int[] {6}));

		//Mushrooms
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "mushroom")), 1, 0), true, new GristSet(new GristType[] {Iodine}, new int[] {5}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "mushroom")), 1, 1), true, new GristSet(new GristType[] {Iodine, Chalk}, new int[] {2, 3}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "mushroom")), 1, 2), true, new GristSet(new GristType[] {Iodine, Cobalt}, new int[] {2, 3}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "mushroom")), 1, 3), true, new GristSet(new GristType[] {Iodine, Uranium}, new int[] {2, 3}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "mushroom")), 1, 4), true, new GristSet(new GristType[] {Iodine}, new int[] {5}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "mushroom")), 1, 5), true, new GristSet(new GristType[] {Iodine, Shale}, new int[] {3, 2}));

		//Fruit
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "berry")), 1), false, new GristSet(new GristType[] {Amber}, new int[] {1}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "pear")), 1), false, new GristSet(new GristType[] {Amber, Shale}, new int[] {3, 2}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "persimmon")), 1), false, new GristSet(new GristType[] {Amber, Iodine}, new int[] {3, 1}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "peach")), 1), false, new GristSet(new GristType[] {Amber, Caulk}, new int[] {3, 1}));

		//Misc.
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "hive")), 1, 0), true, new GristSet(new GristType[] {Build}, new int[] {4}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "hive")), 1, 2), true, new GristSet(new GristType[] {Build}, new int[] {4}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "honeycomb")), 1), false, new GristSet(new GristType[] {Build}, new int[] {1}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "filled_honeycomb")), 1), false, new GristSet(new GristType[] {Amber, Build}, new int[] {1, 1}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "record_wanderer")), 1), false, new GristSet(new GristType[] {Build, Caulk, Quartz, Mercury}, new int[] {15, 8, 5, 5}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "jar_filled")), 1, 0), true, new GristSet(new GristType[] {Build, Amber}, new int[] {2, 8}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "jar_filled")), 1, 1), true, new GristSet(new GristType[] {Build, Sulfur, Cobalt}, new int[] {2, 4, 4}));

		//Dyes
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "blue_dye")), 1), false, new GristSet(new GristType[] {Amethyst}, new int[] {4}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "black_dye")), 1), false, new GristSet(new GristType[] {Tar}, new int[] {4}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "brown_dye")), 1), false, new GristSet(new GristType[] {Amber, Iodine}, new int[] {1, 3}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "green_dye")), 1), false, new GristSet(new GristType[] {Amber, Iodine}, new int[] {3, 1}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "white_dye")), 1), false, new GristSet(new GristType[] {Chalk}, new int[] {2}));

		//cold blocks
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "hard_ice"))), new GristSet(new GristType[] {Build, Cobalt}, new int[] {10, 6}));

		//warm blocks
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "ash"))), new GristSet(new GristType[] {Tar}, new int[] {1}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "fleshchunk"))), new GristSet(new GristType[] {Iodine, Rust}, new int[] {1, 1}));

		//Gemstones
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "crystal_shard"))), new GristSet(new GristType[] {Amethyst, Tar}, new int[] {6, 4}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "gem")), 1, 0), true, new GristSet(new GristType[] {Amethyst, Uranium}, new int[] {12, 6}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "gem")), 1, 1), true, new GristSet(new GristType[] {Ruby}, new int[] {12}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "gem")), 1, 2), true, new GristSet(new GristType[] {Mercury, Rust}, new int[] {6, 6}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "gem")), 1, 3), true, new GristSet(new GristType[] {Diamond, Amber}, new int[] {6, 6}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "gem")), 1, 4), true, new GristSet(new GristType[] {Chalk, Amethyst}, new int[] {3, 9}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "gem")), 1, 5), true, new GristSet(new GristType[] {Amethyst, Quartz}, new int[] {6, 6}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "gem")), 1, 6), true, new GristSet(new GristType[] {Cobalt}, new int[] {12}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "gem")), 1, 7), true, new GristSet(new GristType[] {Amber}, new int[] {12}));

		//Alchemy Recipes
		CombinationRegistry.addCombination(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "brown_dye"))), new ItemStack(Items.WHEAT_SEEDS), MODE_OR, false, false, new ItemStack(Items.DYE, 1, 3));
		CombinationRegistry.addCombination(new ItemStack(Items.QUARTZ), new ItemStack(Items.DYE, 1, 4), MODE_OR, false, true, new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "gem")), 1, 5));
		CombinationRegistry.addCombination(new ItemStack(Blocks.ICE), new ItemStack(Blocks.RED_FLOWER, 1, 1), MODE_AND, false, true, new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "flower_1")), 1, 4));
		CombinationRegistry.addCombination(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "sapling_1")), 1, 0), new ItemStack(Blocks.RED_FLOWER, 1, 0), MODE_OR, true, true, new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "flower_1")), 1, 5));


	}
}
