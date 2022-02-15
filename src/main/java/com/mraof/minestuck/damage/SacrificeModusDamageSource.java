package com.mraof.minestuck.damage;

import com.mraof.minestuck.Minestuck;
import net.minecraft.util.DamageSource;

public class SacrificeModusDamageSource extends DamageSource
{
	public SacrificeModusDamageSource()
	{
		super(Minestuck.MODID + ":sacrifice_modus");
		setDamageBypassesArmor();
	}
}
