package com.mraof.minestuck.block;

import com.mraof.minestuck.item.MinestuckTabs;
import com.mraof.minestuck.util.IRegistryObject;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraftforge.registries.IForgeRegistry;

public abstract class MSBlockContainer extends BlockContainer implements IRegistryBlock
{
	private final String regName;

	public MSBlockContainer(String name, CreativeTabs tab, Material material, MapColor mapColor)
	{
		super(material, mapColor);
		setUnlocalizedName(name);
		regName = IRegistryObject.unlocToReg(name);
		setCreativeTab(tab);
		MinestuckBlocks.blocks.add(this);
	}

	public MSBlockContainer(String name, Material material, MapColor mapColor)
	{
		this(name, MinestuckTabs.minestuck, material, mapColor);
	}

	public MSBlockContainer(String name, Material material)
	{
		this(name, material, material.getMaterialMapColor());
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public void register(IForgeRegistry<Block> registry)
	{
		setRegistryName(regName);
		registry.register(this);
	}
}
