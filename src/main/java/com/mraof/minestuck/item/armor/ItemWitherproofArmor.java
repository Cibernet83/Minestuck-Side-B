package com.mraof.minestuck.item.armor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemWitherproofArmor extends MSArmorBase
{
    public ItemWitherproofArmor(int maxUses, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn, String name) {
        super(maxUses, materialIn, renderIndexIn, equipmentSlotIn, name);
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack)
    {
        super.onArmorTick(world, player, itemStack);
        if(player.isPotionActive(MobEffects.WITHER))
        {
            player.removeActivePotionEffect(MobEffects.WITHER);
            itemStack.damageItem(1, player);
        }
    }
}
