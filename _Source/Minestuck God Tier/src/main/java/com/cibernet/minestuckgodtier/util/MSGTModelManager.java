package com.cibernet.minestuckgodtier.util;

import com.cibernet.minestuckgodtier.MinestuckGodTier;
import com.cibernet.minestuckgodtier.blocks.MSGTBlocks;
import com.cibernet.minestuckgodtier.items.ItemGTArmor;
import com.cibernet.minestuckgodtier.items.MSGTItems;
import com.mraof.minestuck.util.EnumClass;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

import static com.cibernet.minestuckgodtier.items.MSGTItems.*;

public class MSGTModelManager
{
	@SubscribeEvent
    public static void handleModelRegistry(ModelRegistryEvent event)
    {
		register(skillReseter);
		register(tomeOfTheAncients, 0, "sealed_tome");
		register(tomeOfTheAncients, 1, "server_sealed_tome");
		register(tomeOfTheAncients, 2, "tome_of_the_ancients");
		register(denizenEye);
		register(sashKit);
		register(armorKit);

	    register(manipulatedMatter);

		registerArmor(gtHood);
		registerArmor(gtPants);
		registerArmor(gtShirt);
		registerArmor(gtShoes);

		MSGTItems.heroStoneShards.forEach((key, item) -> register(item));

		blocks.forEach(block -> register(block));
		register(MSGTBlocks.prospitStoneSlab);
		register(MSGTBlocks.prospitSmoothstoneSlab);
		register(MSGTBlocks.prospitBricksSlab);
		register(MSGTBlocks.derseStoneSlab);
		register(MSGTBlocks.derseSmoothstoneSlab);
		register(MSGTBlocks.derseBricksSlab);

		/*
		MSGTBlocks.heroStones.forEach((key, block) -> registerBadgeEvents(block));
		registerBadgeEvents(MSGTBlocks.wildcardHeroStone);
		MSGTBlocks.chiseledHeroStones.forEach((key, block) -> registerBadgeEvents(block));
		registerBadgeEvents(MSGTBlocks.wildcardChiseledHeroStone);
		MSGTBlocks.heroStoneWalls.forEach((key, block) -> registerBadgeEvents(block));
		registerBadgeEvents(MSGTBlocks.wildcardHeroStoneWall);
		registerBadgeEvents(MSGTBlocks.glowingHeroStone);
		registerBadgeEvents(MSGTBlocks.heroLockBeacon);
		*/
    }
	
	private static void register(Item item)
	{
		register(item, 0, item.getRegistryName());
	}

	private static void register(Item item, int meta, String modelName)
	{
		register(item, meta, new ResourceLocation(MinestuckGodTier.MODID, modelName));
	}

	private static void register(Item item, int meta, ResourceLocation modelName)
	{
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(modelName, "inventory"));
	}
	
	private static void register(Block block)
	{
		register(Item.getItemFromBlock(block));
	}
	
	private static void registerArmor(Item item)
	{
		String name = ((ItemGTArmor)item).getType();
		List<ResourceLocation> locs = new ArrayList<>();
		locs.add(new ResourceLocation(MinestuckGodTier.MODID,"god_tier_"+name));
		for(EnumClass i : EnumClass.values())
			locs.add(new ResourceLocation(MinestuckGodTier.MODID,i.getDisplayName().toLowerCase()+"_"+name));
		
		locs.add(new ResourceLocation(MinestuckGodTier.MODID,"rogue_hood_hidden"));
		locs.add(new ResourceLocation(MinestuckGodTier.MODID,"lord_shirt_hidden"));

		ModelLoader.registerItemVariants(item, locs.toArray(new ResourceLocation[] {}));
		ModelLoader.setCustomMeshDefinition(item, new ArmorDefinition());
	}
	
	/*
	private static void registerShirt()
	{
		String className = "witch";
		ModelLoaderRegistry.registerLoader(ModelItemShirt.LoaderDynBucket.INSTANCE);
		
		String name = ((ItemGTArmor)MSGTItems.gtShirt).getType();
		List<ResourceLocation> locs = new ArrayList<>();
		locs.add(new ResourceLocation(MinestuckGodTier.MODID,"god_tier_"+name));
		for(EnumClass i : EnumClass.values()) locs.add(new ResourceLocation(MinestuckGodTier.MODID,i.getDisplayName().toLowerCase()+"_"+name));
		ModelLoader.registerItemVariants(MSGTItems.gtShirt, locs.toArray(new ResourceLocation[] {}));
		ModelLoader.setCustomMeshDefinition(gtShirt, stack -> ArmorDefinition.instance.getModelLocation(stack));
	}
	*/
	private static class ArmorDefinition implements ItemMeshDefinition
	{
		@Override
		public ModelResourceLocation getModelLocation(ItemStack stack) 
		{
			NBTTagCompound nbt = stack.getTagCompound();
			
			
			String name = ((ItemGTArmor)stack.getItem()).getType();
			if(nbt == null) return new ModelResourceLocation(MinestuckGodTier.MODID+":god_tier_"+name, "inventory");
			
			String clss = EnumClass.getClassFromInt(nbt.getInteger("class")).getDisplayName().toLowerCase();
			return new ModelResourceLocation(MinestuckGodTier.MODID+":"+clss+"_"+name + (ItemGTArmor.getHideExtras(stack) ? "_hidden" : ""), "inventory");
		}
		
	}
}
