package com.mraof.minestuck.util;

import com.mraof.minestuck.client.util.MinestuckModelManager;
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
			if (Item.REGISTRY.getNameForObject(item) == null)
				throw new NullPointerException(item.getUnlocalizedName() + " was not registered!");
			else
				ModelLoader.setCustomMeshDefinition(item, new MinestuckModelManager.SubtypesItemDefinition(Item.REGISTRY.getNameForObject(item).toString()));
		else
			ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}

	static String unlocToReg(String unloc)
	{
		String unlocc = unloc;
		/*if(unloc.indexOf('.') < 0 && unloc.indexOf('.') < unloc.length())
		{
			unlocc = unlocc.substring(unloc.indexOf('.')+1);
		}*/
		StringBuilder reg = new StringBuilder();
		for (int i = 0; i < unlocc.length(); i++)
		{
			if (Character.isUpperCase(unlocc.charAt(i)) && (
					(i > 0 && Character.isLowerCase(unlocc.charAt(i - 1))) ||
							(i < unlocc.length() - 1 && Character.isLowerCase(unlocc.charAt(i + 1)))
			))
				reg.append('_');
			reg.append(unlocc.charAt(i));
		}
		return reg.toString().toLowerCase();
	}
}
