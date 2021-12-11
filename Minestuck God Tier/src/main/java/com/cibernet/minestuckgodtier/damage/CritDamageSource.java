package com.cibernet.minestuckgodtier.damage;

import com.cibernet.minestuckgodtier.MinestuckGodTier;
import net.minecraft.util.DamageSource;

public class CritDamageSource extends DamageSource implements IMSGTDamage
{
	boolean isCrit = false;
	boolean godproof = false;

	public CritDamageSource(String damageTypeIn)
	{
		super(MinestuckGodTier.MODID+"."+damageTypeIn);
	}

	@Override
	public CritDamageSource setCrit()
	{
		isCrit = true;
		return this;
	}

	@Override
	public boolean isCrit() {
		return isCrit;
	}

	@Override
	public CritDamageSource setGodproof()
	{
		godproof = true;
		return this;
	}

	@Override
	public boolean isGodproof() {
		return godproof;
	}
}
