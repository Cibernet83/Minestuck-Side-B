package com.mraof.minestuck.item.block;

import com.mraof.minestuck.item.IRegistryItem;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

public class MSItemBlock extends ItemBlock implements IRegistryItem
{
	public MSItemBlock(Block block, int stackSize)
	{
		super(block);
		setMaxStackSize(stackSize);
	}

	public MSItemBlock(Block block)
	{
		this(block, 64);
	}

	@Override
	public void register(IForgeRegistry<Item> registry)
	{
		setRegistryName(block.getRegistryName());
		registry.register(this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerModel()
	{
		if(getHasSubtypes())
			ModelLoader.setCustomMeshDefinition(this, (stack -> new ModelResourceLocation(getRegistryName(), "inventory")));
		else
			ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
	}
}
