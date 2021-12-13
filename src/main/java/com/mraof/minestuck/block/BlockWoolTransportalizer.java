package com.mraof.minestuck.block;

import com.mraof.minestuck.item.IRegistryItem;
import net.minecraft.item.EnumDyeColor;

public class BlockWoolTransportalizer extends BlockTransportalizer implements IRegistryItem
{
	public EnumDyeColor color;
	public BlockWoolTransportalizer(EnumDyeColor color)
	{
		super();
		this.setCreativeTab(TabMinestuckUniverse.main);
		this.setUnlocalizedName("woolTransportalizer."+color.getUnlocalizedName());
		this.color = color;

		MinestuckUniverseBlocks.sleevedTransportalizers.put(color, this);
	}

	@Override
	public void setRegistryName() {
		this.setRegistryName(color.getName()+"_wool_transportalizer");
	}
}
