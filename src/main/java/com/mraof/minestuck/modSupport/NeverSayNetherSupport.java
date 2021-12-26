package com.mraof.minestuck.modSupport;

import com.mraof.minestuck.alchemy.GristRegistry;
import com.mraof.minestuck.alchemy.GristSet;
import com.mraof.minestuck.alchemy.Grist;

import com.mraof.minestuck.alchemy.MinestuckGrist;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class NeverSayNetherSupport extends ModSupport
{
	
	@Override
	public void registerRecipes() throws Exception
	{
		Item dust = ((Item) (Class.forName("com.debbie.nsn.items.ModItems").getField("daedalean_dustItem").get(null)));
		Item quartz = ((Item) (Class.forName("com.debbie.nsn.items.ModItems").getField("daedalean_quartzItem").get(null)));
		Block ore = ((Block) (Class.forName("com.debbie.nsn.blocks.ModBlocks").getField("daedalean_oreBlock").get(null)));
		
		GristRegistry.addGristConversion(new ItemStack(dust), new GristSet(MinestuckGrist.build, 1));
		GristRegistry.addGristConversion(new ItemStack(quartz), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.quartz}, new int[]{1, 1}));
		GristRegistry.addGristConversion(ore, new GristSet(MinestuckGrist.build, 5));
	}
	
}
