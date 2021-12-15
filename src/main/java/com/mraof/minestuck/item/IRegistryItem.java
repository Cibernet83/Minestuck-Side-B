package com.mraof.minestuck.item;

import com.mraof.minestuck.util.IRegistryObject;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

public interface IRegistryItem extends IRegistryObject<Item>
{
	default void registerModel()
	{
		Item item = (Item) this;
		if (Item.REGISTRY.getNameForObject(item) == null)
			throw new NullPointerException(item.getUnlocalizedName() + " was not registered!");
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
}
