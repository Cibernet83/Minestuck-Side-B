package com.mraof.minestuck.block;

import com.mraof.minestuck.item.TabsMinestuck;
import com.mraof.minestuck.util.IRegistryItem;
import net.minecraft.block.Block;
import net.minecraft.item.EnumDyeColor;
import net.minecraftforge.registries.IForgeRegistry;

public class BlockWoolTransportalizer extends BlockTransportalizer implements IRegistryItem<Block>
{
	public EnumDyeColor color;
	public BlockWoolTransportalizer(EnumDyeColor color)
	{
		super();
		this.setCreativeTab(TabsMinestuck.minestuck);
		this.setUnlocalizedName("woolTransportalizer."+color.getUnlocalizedName());
		this.color = color;

		MinestuckBlocks.sleevedTransportalizers.put(color, this);
	}

	@Override
	public void register(IForgeRegistry<Block> registry)
	{
		setRegistryName(color.getName()+"_wool_transportalizer");
		registry.register(this);
	}
}
