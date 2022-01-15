package com.mraof.minestuck.block;

import com.mraof.minestuck.item.MinestuckTabs;
import net.minecraft.block.Block;
import net.minecraft.item.EnumDyeColor;
import net.minecraftforge.registries.IForgeRegistry;

public class BlockWoolTransportalizer extends BlockTransportalizer
{
	public EnumDyeColor color;

	public BlockWoolTransportalizer(EnumDyeColor color)
	{
		super();
		this.setCreativeTab(MinestuckTabs.minestuck);
		this.setUnlocalizedName("woolTransportalizer." + color.getUnlocalizedName());
		this.color = color;

		MinestuckBlocks.sleevedTransportalizers.put(color, this);
	}

	@Override
	public void register(IForgeRegistry<Block> registry)
	{
		setRegistryName(color.getName() + "_wool_transportalizer");
		registry.register(this);
	}
}
