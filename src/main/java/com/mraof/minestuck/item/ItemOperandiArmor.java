package com.mraof.minestuck.item;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.inventory.captchalouge.OperandiModus;
import com.mraof.minestuck.item.TabMinestuck;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.util.EnumHelper;

public class ItemOperandiArmor extends ItemArmor
{
	public static final ArmorMaterial MATERIAL = EnumHelper.addArmorMaterial("OPERANDI", Minestuck.MODID + ":operandi", 1, new int[] {1, 1, 1, 1}, -1, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 0);
	
	public ItemOperandiArmor(String name, EntityEquipmentSlot equipmentSlotIn)
	{
		super(MATERIAL, 5, equipmentSlotIn);
		
		setCreativeTab(TabMinestuck.instance);
		
		setUnlocalizedName(name);
		OperandiModus.itemPool.add(this);
	}
	
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
	{
	}
	
	@Override
	public boolean isRepairable()
	{
		return false;
	}
	
	@Override
	public boolean isEnchantable(ItemStack stack)
	{
		return false;
	}
	
	@Override
	public void setDamage(ItemStack stack, int damage)
	{
		if(damage > getMaxDamage(stack))
			super.setDamage(stack, damage);
	}
}
