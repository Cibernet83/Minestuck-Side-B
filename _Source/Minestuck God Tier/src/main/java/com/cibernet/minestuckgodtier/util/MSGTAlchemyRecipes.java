package com.cibernet.minestuckgodtier.util;

import com.cibernet.minestuckgodtier.blocks.MSGTBlocks;
import com.cibernet.minestuckgodtier.items.MSGTItems;
import com.cibernet.Minestuck.alchemy.MSUAlchemyRecipes;
import com.cibernet.Minestuck.items.MinestuckItems;
import com.mraof.minestuck.alchemy.CombinationRegistry;
import com.mraof.minestuck.alchemy.GristRegistry;
import com.mraof.minestuck.alchemy.GristSet;
import com.mraof.minestuck.alchemy.GristType;
import com.mraof.minestuck.entity.consort.ConsortRewardHandler;
import com.mraof.minestuck.item.MinestuckItems;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class MSGTAlchemyRecipes
{
	public static void registerRecipes()
	{
		GristRegistry.addGristConversion(new ItemStack(MSGTItems.denizenEye), new GristSet(new GristType[]{GristType.Build, GristType.Mercury, GristType.Uranium, GristType.Amethyst,
				GristType.Diamond, GristType.Quartz, GristType.Zillium, GristType.Artifact}, new int[]{10000, 5000, 5000, 5000, 2500, 2500, 1000, 1000}));

		CombinationRegistry.addCombination(new ItemStack(MSGTItems.tomeOfTheAncients, 1, 2), new ItemStack(Items.ENDER_EYE),
				CombinationRegistry.Mode.MODE_OR, true, false, new ItemStack(MSGTItems.denizenEye));

		ConsortRewardHandler.registerPrice(new ItemStack(MSGTItems.tomeOfTheAncients), 10000, 10000);
		ConsortRewardHandler.registerPrice(new ItemStack(MinestuckItems.candy, 1, 21), 500, 600);

		for(Block block : MSGTBlocks.chiseledHeroStones.values())
		{
			OreDictionary.registerOre("chiseled_hero_stone", block);
			OreDictionary.registerOre("hero_stone", block);
		}
		for(Block block : MSGTBlocks.heroStones.values())
			OreDictionary.registerOre("hero_stone", block);

		CombinationRegistry.addCombination("chiseled_hero_stone", new ItemStack(MinestuckItems.knittingNeedles), false, CombinationRegistry.Mode.MODE_OR, new ItemStack(MSGTItems.sashKit));
		GristRegistry.addGristConversion(new ItemStack(MSGTItems.sashKit), new GristSet(new GristType[]{GristType.Build, GristType.Gold, GristType.Zillium, GristType.Artifact}, new int[] {1080, 400, 100, 100}));
		CombinationRegistry.addCombination("chiseled_hero_stone", new ItemStack(MinestuckItems.ironMedallion), false, CombinationRegistry.Mode.MODE_AND, new ItemStack(MSGTItems.skillReseter));
		GristRegistry.addGristConversion(new ItemStack(MSGTItems.skillReseter), new GristSet(new GristType[]{GristType.Build, GristType.Gold, GristType.Diamond, GristType.Zillium, GristType.Artifact}, new int[] {1080, 200, 200, 100, 100}));
	}
}
