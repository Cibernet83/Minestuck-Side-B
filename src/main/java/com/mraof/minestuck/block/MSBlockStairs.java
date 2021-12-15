package com.mraof.minestuck.block;

import com.mraof.minestuck.item.MinestuckTabs;
import com.mraof.minestuck.util.IRegistryObject;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;
import net.minecraftforge.registries.IForgeRegistry;

public class MSBlockStairs extends BlockStairs implements IRegistryBlock
{
	final String regName;

	public MSBlockStairs(String name, IBlockState blockState)
	{
		super(blockState);setHardness(blockState.getBlockHardness(null, null));
		setResistance(blockState.getBlock().getExplosionResistance(null) * 5f/3f);
		setHarvestLevel(blockState.getBlock().getHarvestTool(blockState), blockState.getBlock().getHarvestLevel(blockState));
		setUnlocalizedName(name);
		this.regName = IRegistryObject.unlocToReg(name);
		this.useNeighborBrightness = true;
		setCreativeTab(MinestuckTabs.minestuck);
		MinestuckBlocks.blocks.add(this);
	}

	@Override
	public void register(IForgeRegistry<Block> registry)
	{
		setRegistryName(regName);
		registry.register(this);
	}
}
