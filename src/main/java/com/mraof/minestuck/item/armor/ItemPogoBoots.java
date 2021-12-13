package com.mraof.minestuck.item.armor;

import net.minecraft.inventory.EntityEquipmentSlot;

public class ItemPogoBoots extends MSArmorBase
{
	boolean isSolar = false;
	public final float power;

	public ItemPogoBoots(float power, ArmorMaterial materialIn, int renderIndexIn, String name)
	{
		super(materialIn, renderIndexIn, EntityEquipmentSlot.FEET, name);
		this.power = power;
	}

	public ItemPogoBoots(float power, int maxUses, ArmorMaterial materialIn, int renderIndexIn, String name)
	{
		super(maxUses, materialIn, renderIndexIn, EntityEquipmentSlot.FEET, name);
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
