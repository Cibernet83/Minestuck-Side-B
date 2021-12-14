package com.mraof.minestuck.item.armor;

import com.mraof.minestuck.Minestuck;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.translation.I18n;

import javax.annotation.Nullable;

public class ItemScarf extends MSArmorBase
{
	public ItemScarf(String name, ArmorMaterial materialIn, EntityEquipmentSlot equipmentSlot) {
		super(name, materialIn, equipmentSlot);
		setHasSubtypes(true);
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
	{
		if(isInCreativeTab(tab))
			for(int i = 0; i < EnumDyeColor.values().length; i++)
				items.add(new ItemStack(this, 1, i));
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return I18n.translateToLocal(this.getUnlocalizedNameInefficiently(stack) + "." + getDyeColor(stack).getUnlocalizedName() + ".name").trim();
	}

	public static EnumDyeColor getDyeColor(ItemStack stack)
	{
		if(stack.getItem() instanceof ItemScarf)
			return EnumDyeColor.byDyeDamage(stack.getMetadata());
		return EnumDyeColor.WHITE;
	}

	@Nullable
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type)
	{
		return Minestuck.MODID + ":textures/models/armor/scarf/" + getRegistryName().getResourcePath() + "_" + getDyeColor(stack).getDyeColorName() + ".png";
	}
}
