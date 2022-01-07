package com.mraof.minestuck.block;

import com.mraof.minestuck.item.MinestuckTabs;
import com.mraof.minestuck.item.block.MSItemBlock;
import com.mraof.minestuck.util.IRegistryObject;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraftforge.registries.IForgeRegistry;

public class MSBlockPillar extends BlockRotatedPillar implements IRegistryBlock
{
	private final String regName;

	public MSBlockPillar(String name, MapColor color)
	{
		super(Material.ROCK, color);
		setUnlocalizedName(name);
		regName = IRegistryObject.unlocToReg(name);
		setCreativeTab(MinestuckTabs.minestuck);
		MinestuckBlocks.blocks.add(this);
	}

	public Block setHarvestLevelChain(String toolClass, int level)
	{
		setHarvestLevel(toolClass, level);
		return this;
	}

	@Override
	public void register(IForgeRegistry<Block> registry)
	{
		setRegistryName(regName);
		registry.register(this);
	}

	@Override
	public MSItemBlock getItemBlock()
	{
		return new MSItemBlock(this);
	}
}
