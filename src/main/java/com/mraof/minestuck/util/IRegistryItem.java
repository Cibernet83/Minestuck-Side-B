package com.mraof.minestuck.util;

import com.mraof.minestuck.client.util.MinestuckModelManager;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

public interface IRegistryItem<T extends IForgeRegistryEntry<T>>
{
	void register(IForgeRegistry<T> registry);

	default void registerModel()
	{
		Item item;
		if(this instanceof Item)
			item = (Item) this;
		else return;

		if(item.getHasSubtypes())
			ModelLoader.setCustomMeshDefinition(item, new MinestuckModelManager.SubtypesItemDefinition(Item.REGISTRY.getNameForObject(item).toString()));
		else
			ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}

	static String unlocToReg(String unloc)
	{
		StringBuilder reg = new StringBuilder();
		for (int i = 0; i < unloc.length(); i++)
		{
			if (Character.isUpperCase(unloc.charAt(i)) && (
					(i > 0 && Character.isLowerCase(unloc.charAt(i - 1))) ||
							(i < unloc.length() - 1 && Character.isLowerCase(unloc.charAt(i + 1)))
			))
				reg.append('_');
			reg.append(unloc.charAt(i));
		}
		return reg.toString().toLowerCase();
	}
}
