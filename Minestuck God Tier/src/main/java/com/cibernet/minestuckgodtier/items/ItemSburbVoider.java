package com.cibernet.minestuckgodtier.items;

import com.mraof.minestuck.item.weapon.ItemWeapon;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class ItemSburbVoider extends ItemWeapon
{
    public ItemSburbVoider(int maxUses, double damageVsEntity, double weaponSpeed, int enchantability, String name) {
        super(maxUses, damageVsEntity, weaponSpeed, enchantability, name);
    }

    public ItemSburbVoider(ToolMaterial material, int maxUses, double damageVsEntity, double weaponSpeed, int enchantability, String name) {
        super(material, maxUses, damageVsEntity, weaponSpeed, enchantability, name);
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase player)
    {
        String modid = EntityRegistry.getEntry(target.getClass()).getRegistryName().getResourceDomain();
        if(modid.contains("minestuck") || modid.contains("fetchmodiplus"))
            target.setDead();

        return super.hitEntity(stack, target, player);
    }
}
