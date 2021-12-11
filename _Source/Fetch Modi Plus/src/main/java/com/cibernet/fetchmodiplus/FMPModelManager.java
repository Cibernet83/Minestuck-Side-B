package com.cibernet.fetchmodiplus;

import com.cibernet.fetchmodiplus.registries.FMPItems;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class FMPModelManager
{
	@SubscribeEvent
	public static void handleModelRegistry(ModelRegistryEvent event)
	{
		ItemModels();
	}
	
	private static void ItemModels()
	{
		for(Item item : FMPItems.items)
			register(item);
	}
	
	private static void register(Item item)
	{
		if(item.getHasSubtypes())
			ModelLoader.setCustomMeshDefinition(item, new SubtypesItemDefinition(Item.REGISTRY.getNameForObject(item).toString()));
		else ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(Item.REGISTRY.getNameForObject(item), "inventory"));
	}
	
	private static class SubtypesItemDefinition implements ItemMeshDefinition
	{
		private String name;
		SubtypesItemDefinition(String name)
		{
			this.name = name;
		}
		@Override
		public ModelResourceLocation getModelLocation(ItemStack stack)
		{
			return new ModelResourceLocation(name, "inventory");
		}
	}
}
