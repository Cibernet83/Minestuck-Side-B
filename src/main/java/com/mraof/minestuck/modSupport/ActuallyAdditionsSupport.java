package com.mraof.minestuck.modSupport;

import static com.mraof.minestuck.MinestuckConfig.oreMultiplier;
import static com.mraof.minestuck.alchemy.CombinationRegistry.Mode.MODE_AND;
import static com.mraof.minestuck.alchemy.CombinationRegistry.Mode.MODE_OR;

import com.mraof.minestuck.alchemy.*;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class ActuallyAdditionsSupport extends ModSupport
{
	@Override
	public void registerRecipes() throws Exception 
	{
		Item itemMisc = ForgeRegistries.ITEMS.getValue(new ResourceLocation("actuallyadditions", "item_misc")); //Black quartz = 5, canola = 13, blackDye = 17
		Item waterBowl = ForgeRegistries.ITEMS.getValue(new ResourceLocation("actuallyadditions", "item_water_bowl"));
		Item coffeeBeans = ForgeRegistries.ITEMS.getValue(new ResourceLocation("actuallyadditions", "item_coffee_beans"));
		Item coffeeSeeds = ForgeRegistries.ITEMS.getValue(new ResourceLocation("actuallyadditions", "item_coffee_seed"));
		Item flaxSeeds = ForgeRegistries.ITEMS.getValue(new ResourceLocation("actuallyadditions", "item_flax_seed"));
		Item itemFood = ForgeRegistries.ITEMS.getValue(new ResourceLocation("actuallyadditions", "item_food")); //Rice = 16
		Item worm = ForgeRegistries.ITEMS.getValue(new ResourceLocation("actuallyadditions", "item_worm"));
		Item solidXp = ForgeRegistries.ITEMS.getValue(new ResourceLocation("actuallyadditions", "item_solidified_experience"));
		Block blackLotus = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("actuallyadditions", "block_black_lotus"));
		Block blockMisc = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("actuallyadditions", "block_misc")); // Black quartz ore = 3
		
		GristRegistry.addGristConversion(new ItemStack(blockMisc, 1, 3), true, new GristSet(new Grist[] {MinestuckGrists.quartz, MinestuckGrists.tar, MinestuckGrists.build}, new int[] {4*oreMultiplier, 1*oreMultiplier, 4}));
		GristRegistry.addGristConversion(new ItemStack(blackLotus), false, new GristSet(new Grist[] {MinestuckGrists.tar, MinestuckGrists.iodine}, new int[] {4, 1}));
		GristRegistry.addGristConversion(new ItemStack(itemMisc, 1, 5), true, new GristSet(new Grist[] {MinestuckGrists.quartz, MinestuckGrists.tar}, new int[] {4, 1}));
		GristRegistry.addGristConversion(new ItemStack(itemMisc, 1, 13), true, new GristSet(new Grist[] {MinestuckGrists.tar, MinestuckGrists.amber}, new int[] {1, 4}));
		GristRegistry.addGristConversion(new ItemStack(itemMisc, 1, 17), true, new GristSet(new Grist[] {MinestuckGrists.tar}, new int[] {4}));
		GristRegistry.addGristConversion(new ItemStack(waterBowl), false, new GristSet(new Grist[] {MinestuckGrists.build, MinestuckGrists.cobalt}, new int[] {1, 4}));
		GristRegistry.addGristConversion(new ItemStack(itemFood, 1, 16), true, new GristSet(new Grist[] {MinestuckGrists.chalk, MinestuckGrists.iodine}, new int[] {2, 2}));
		GristRegistry.addGristConversion(new ItemStack(flaxSeeds), false, new GristSet(new Grist[] {MinestuckGrists.chalk, MinestuckGrists.iodine}, new int[] {1, 1}));
		GristRegistry.addGristConversion(new ItemStack(worm), false, new GristSet(new Grist[] {MinestuckGrists.amber, MinestuckGrists.iodine}, new int[] {5, 4}));
		GristRegistry.addGristConversion(new ItemStack(coffeeBeans), false, new GristSet(new Grist[] {MinestuckGrists.amber, MinestuckGrists.iodine}, new int[] {2, 6}));
		GristRegistry.addGristConversion(new ItemStack(coffeeSeeds), false, new GristSet(new Grist[] {MinestuckGrists.amber, MinestuckGrists.iodine}, new int[] {1, 3}));
		GristRegistry.addGristConversion(new ItemStack(solidXp), false, new GristSet(new Grist[] {MinestuckGrists.uranium, MinestuckGrists.ruby}, new int[] {1, 1}));

		CombinationRegistry.addCombination(new ItemStack(itemMisc, 1, 5), new ItemStack(Blocks.STONE, 1, 0), MODE_AND, true, true, new ItemStack(blockMisc, 1, 3));
		CombinationRegistry.addCombination(new ItemStack(Items.QUARTZ), new ItemStack(Items.DYE, 1, 0), MODE_OR, false, true, new ItemStack(itemMisc, 1, 5));
	}
}
