package com.mraof.minestuck.item;

import com.mraof.minestuck.client.util.MinestuckModelManager;
import com.mraof.minestuck.util.IRegistryItem;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;

public class MSItemBase extends Item implements IRegistryItem<Item>
{
	private final String regName;
	public static final ArrayList<IRegistryItem<Item>> items = new ArrayList<>();
	public final boolean hasCustomModel;

	public MSItemBase(String name, CreativeTabs tab, int stackSize, boolean hasCustomModel)
	{
		regName = IRegistryItem.unlocToReg(name);
		setUnlocalizedName(name);
		setCreativeTab(tab);
		items.add(this);
		this.hasCustomModel = hasCustomModel;
	}

	public MSItemBase(String name)
	{
		this(name, TabMinestuck.instance, 64, false);
	}

	public MSItemBase(String name, boolean hasCustomModel)
	{
		this(name, TabMinestuck.instance, 64, hasCustomModel);
	}


	@Override
	public void register(IForgeRegistry<Item> registry)
	{
		setRegistryName(regName);
		registry.register(this);
	}

	@Override
	public void registerModel()
	{
		if(hasCustomModel)
			return;

		if(getHasSubtypes())
			ModelLoader.setCustomMeshDefinition(this, new MinestuckModelManager.SubtypesItemDefinition(Item.REGISTRY.getNameForObject(this).toString()));
		else
			ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
	}
}
