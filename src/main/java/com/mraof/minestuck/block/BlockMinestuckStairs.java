package com.mraof.minestuck.block;

import com.mraof.minestuck.item.TabMinestuck;
import com.mraof.minestuck.util.IRegistryItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;
import net.minecraftforge.registries.IForgeRegistry;

public class BlockMinestuckStairs extends BlockStairs implements IRegistryItem<Block>
{
	private final String regName;

	public BlockMinestuckStairs(String name, IBlockState modelState)
	{
		super(modelState);
		setCreativeTab(TabMinestuck.instance);
		this.useNeighborBrightness = true;
		setUnlocalizedName(name);
		regName = IRegistryItem.unlocToReg(name);
		MSBlockBase.blocks.add(this);
	}

	@Override
	public void register(IForgeRegistry<Block> registry)
	{
		setRegistryName(regName);
		registry.register(this);
	}

}
