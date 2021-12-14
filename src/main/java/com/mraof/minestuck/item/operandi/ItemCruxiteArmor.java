package com.mraof.minestuck.item.operandi;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.inventory.captchalouge.OperandiModus;
import com.mraof.minestuck.item.MSArmorBase;
import com.mraof.minestuck.item.MinestuckItems;
import com.mraof.minestuck.item.TabMinestuck;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.util.EnumHelper;

public class ItemCruxiteArmor extends MSArmorBase implements ICruxiteArtifact
{
	public static final ArmorMaterial OPERANDI_MATERIAL = EnumHelper.addArmorMaterial("OPERANDI", Minestuck.MODID + ":operandi", 1, new int[] {1, 1, 1, 1}, -1, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 0);
	public static final ArmorMaterial CRUXITE_MATERIAL = EnumHelper.addArmorMaterial("CRUXITE", Minestuck.MODID + ":cruxite", 1, new int[] {1, 1, 1, 1}, -1, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 0);

	private final CruxiteArtifactTeleporter teleporter;

	public ItemCruxiteArmor(String name, EntityEquipmentSlot equipmentSlotIn, boolean isEntryArtifact)
	{
		super(isEntryArtifact ? CRUXITE_MATERIAL : OPERANDI_MATERIAL, 5, equipmentSlotIn, name, (isEntryArtifact ? CRUXITE_MATERIAL : OPERANDI_MATERIAL).getName() + "_layer_" + (equipmentSlotIn == EntityEquipmentSlot.LEGS ? "2" : "1"));
		
		setCreativeTab(TabMinestuck.instance);

		if(isEntryArtifact)
		{
			teleporter =  new CruxiteArtifactTeleporter();
			MinestuckItems.cruxiteArtifacts.add(this);
		}
		else
		{
			teleporter = null;
			OperandiModus.itemPool.add(this);
		}
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

	@Override
	public boolean isEntryArtifact() {
		return teleporter != null;
	}

	@Override
	public CruxiteArtifactTeleporter getTeleporter() {
		return teleporter;
	}

	@Override
	public boolean hasColor(ItemStack stack) {
		return isEntryArtifact();
	}
	@Override
	public boolean hasOverlay(ItemStack stack) {
		return isEntryArtifact();
	}

	@Override
	public int getColor(ItemStack stack) {
		return getCruxiteColor(stack);
	}
}
