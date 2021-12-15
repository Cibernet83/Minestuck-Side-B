package com.mraof.minestuck.item.properties.beams;

import com.mraof.minestuck.capabilities.Beam;
import com.mraof.minestuck.item.properties.WeaponProperty;
import net.minecraft.item.ItemStack;

public class PropertyBeamDeathMessage extends WeaponProperty implements IPropertyBeam
{
	String name;

	public PropertyBeamDeathMessage(String name)
	{
		this.name = name;
	}

	@Override
	public String beamDamageName(Beam beam, ItemStack stack, String name)
	{
		return name;
	}
}
