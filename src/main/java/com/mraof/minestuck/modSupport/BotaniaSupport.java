package com.mraof.minestuck.modSupport;

import com.mraof.minestuck.alchemy.CombinationRegistry;
import com.mraof.minestuck.alchemy.GristRegistry;
import com.mraof.minestuck.alchemy.GristSet;
import com.mraof.minestuck.alchemy.MinestuckGrist;
import com.mraof.minestuck.block.MinestuckBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.recipe.RecipeManaInfusion;
import vazkii.botania.common.block.ModBlocks;
import vazkii.botania.common.item.ModItems;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import static com.mraof.minestuck.alchemy.CombinationRegistry.Mode.MODE_AND;
import static com.mraof.minestuck.alchemy.CombinationRegistry.Mode.MODE_OR;
import static com.mraof.minestuck.alchemy.MinestuckGrist.build;

public class BotaniaSupport
{

	public static Hashtable<List<Object>, GristSet> gristCosts;

	public static void generateGristCosts()
	{
		Iterator<RecipeManaInfusion> infusionIter = BotaniaAPI.manaInfusionRecipes.iterator();

		while (infusionIter.hasNext())
		{
			RecipeManaInfusion recipe = infusionIter.next();
			ItemStack out = recipe.getOutput();

			ItemStack in = (recipe.getInput() instanceof ItemStack) ? (ItemStack) recipe.getInput() : OreDictionary.getOres((String) recipe.getInput()).get(0);

			if (recipe.getCatalyst() != null)
				continue;

			if (out.getItem() != ModItems.manaBottle && out.getItem() != Item.getItemFromBlock(MinestuckBlocks.magicBlock))
			{
				CombinationRegistry.addCombination(in, new ItemStack(MinestuckBlocks.gristBlocks.get(MinestuckGrist.mana)), CombinationRegistry.Mode.MODE_AND, false, false, out);
				CombinationRegistry.addCombination(in, new ItemStack(ModItems.manaBottle), CombinationRegistry.Mode.MODE_OR, false, false, out);
			}
		}

		for (int i = 0; i < 6; i++)
		{
			ItemStack seeds = new ItemStack(ModItems.grassSeeds, 1, i + 3);
			ItemStack grass = new ItemStack(ModBlocks.altGrass, 1, i);
			GristRegistry.addGristConversion(grass, true, new GristSet(build, 2).addGrist(GristRegistry.getGristConversion(seeds)));
			CombinationRegistry.addCombination(new ItemStack(Blocks.DIRT), seeds, MODE_OR, false, true, grass);
			CombinationRegistry.addCombination(new ItemStack(Blocks.GRASS), seeds, MODE_AND, false, true, grass);
		}
	}
}
