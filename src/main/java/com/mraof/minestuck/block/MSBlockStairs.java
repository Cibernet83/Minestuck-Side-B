package com.mraof.minestuck.block;

import com.mraof.minestuck.item.MinestuckTabs;
import com.mraof.minestuck.item.block.MSItemBlock;
import com.mraof.minestuck.util.IRegistryItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;
import net.minecraftforge.registries.IForgeRegistry;

public class MSBlockStairs extends BlockStairs implements IRegistryBlock
{
	final String regName;

	public MSBlockStairs(String name, IBlockState modelState)
	{
		super(modelState);
		setUnlocalizedName(name);
		this.regName = IRegistryItem.unlocToReg(name);
		this.useNeighborBrightness = true;
		MinestuckBlocks.blocks.add(this);
		setCreativeTab(MinestuckTabs.minestuck);
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
