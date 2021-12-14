package com.mraof.minestuck.item.armor;

import net.minecraft.inventory.EntityEquipmentSlot;

public class ItemPogoBoots extends MSArmorBase
{
	boolean isSolar = false;
	public final float power;

	public ItemPogoBoots(String name, float power, ArmorMaterial material)
	{
		super(name, material, EntityEquipmentSlot.FEET);
		this.power = power;
	}

	public ItemPogoBoots(String name, float power, ArmorMaterial material, int maxUses)
	{
		super(name, material, EntityEquipmentSlot.FEET, maxUses);
		this.power = power;
	}



	public ItemPogoBoots setSolar()
	{
		this.isSolar = true;
		return this;
	}

	public boolean isSolar()
	{
		return isSolar;
	}
}
