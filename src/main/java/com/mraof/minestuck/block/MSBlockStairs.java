package com.mraof.minestuck.block;

import com.mraof.minestuck.util.IRegistryItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;
import net.minecraftforge.registries.IForgeRegistry;

public class MSBlockStairs extends BlockStairs implements IRegistryItem<Block>
{
	final String regName;

	public MSBlockStairs(IBlockState modelState, String name)
	{
		super(modelState);
		setUnlocalizedName(name);
		this.regName = IRegistryItem.unlocToReg(name);
	}

	@Override
	public void register(IForgeRegistry<Block> registry)
	{
		setRegistryName(regName);
		registry.register(this);
	}
}
