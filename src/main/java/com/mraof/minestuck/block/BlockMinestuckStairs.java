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

	public BlockMinestuckStairs(String unloc, String reg, IBlockState modelState)
	{
		super(modelState);
		setCreativeTab(TabMinestuck.instance);
		this.useNeighborBrightness = true;
		setUnlocalizedName(unloc);
		regName = reg;
		MSBlockBase.blocks.add(this);
	}

	public BlockMinestuckStairs(String name, IBlockState modelState)
	{
		this(name, IRegistryItem.unlocToReg(name), modelState);
	}

	@Override
	public void register(IForgeRegistry<Block> registry)
	{
		setRegistryName(regName);
		registry.register(this);
	}

}
