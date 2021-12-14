package com.mraof.minestuck.event;

import com.mraof.minestuck.tileentity.TileEntityMiniSburbMachine;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class AlchemizeItemMinichemiterEvent extends AlchemizeItemEvent
{
	private TileEntityMiniSburbMachine alchemiter;

	public AlchemizeItemMinichemiterEvent(World world, ItemStack dowel, ItemStack originalResultItem, TileEntityMiniSburbMachine alchemiter)
	{
		super(world, dowel, originalResultItem);
		this.alchemiter = alchemiter;
	}

	public TileEntityMiniSburbMachine getAlchemiter()
	{
		return alchemiter;
	}
}
