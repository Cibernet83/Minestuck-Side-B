package com.mraof.minestuck.event;

import com.mraof.minestuck.alchemy.GristSet;
import com.mraof.minestuck.entity.underling.EntityUnderling;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.fml.common.eventhandler.Event;

import java.util.ArrayList;

public class UnderlingSpoilsEvent extends Event
{
	private final EntityUnderling underling;
	private final DamageSource source;
	private final GristSet originalSpoils;
	private GristSet spoils;
	private ArrayList<ItemStack> drops = new ArrayList<>();

	public UnderlingSpoilsEvent(EntityUnderling underling, DamageSource source, GristSet spoils)
	{
		this.underling = underling;
		this.source = source;
		this.originalSpoils = spoils;
		this.spoils = spoils;
	}

	public UnderlingSpoilsEvent(EntityUnderling underling, DamageSource source, GristSet spoils, ArrayList<ItemStack> drops)
	{
		this(underling, source, spoils);
		this.drops.addAll(drops);
	}

	@Override
	public boolean isCancelable() {
		return false;
	}

	public EntityUnderling getUnderling() {
		return underling;
	}

	public DamageSource getSource() {return source;}

	public GristSet getOriginalSpoils() {
		return originalSpoils;
	}

	public GristSet getSpoils() {
		return spoils;
	}

	public void setSpoils(GristSet spoils) {
		this.spoils = spoils;
	}

	public ArrayList<ItemStack> getDrops()
	{
		return drops;
	}
}
