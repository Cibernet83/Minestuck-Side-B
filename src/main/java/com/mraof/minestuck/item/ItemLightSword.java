package com.mraof.minestuck.item;

import com.mraof.minestuck.item.weapon.ItemPotionWeapon;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;

public class ItemLightSword extends ItemPotionWeapon
{
    public ItemLightSword(int maxUses, double damageVsEntity, double weaponSpeed, int enchantability, String name, PotionEffect effect, boolean potionEffectOnCriticalHit) {
        super(maxUses, damageVsEntity, weaponSpeed, enchantability, name, effect, potionEffectOnCriticalHit);
    }

    public ItemLightSword(int maxUses, double damageVsEntity, double weaponSpeed, int enchantability, String name, PotionEffect effect) {
        super(maxUses, damageVsEntity, weaponSpeed, enchantability, name, effect);
    }

    public ItemLightSword(ToolMaterial material, int maxUses, double damageVsEntity, double weaponSpeed, int enchantability, String name, PotionEffect effect) {
        super(material, maxUses, damageVsEntity, weaponSpeed, enchantability, name, effect);
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase player)
    {
        if(target.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD)
            target.attackEntityFrom(player instanceof EntityPlayer ? DamageSource.causePlayerDamage((EntityPlayer) player) : DamageSource.causeMobDamage(player), (float) getAttackDamage(stack));

        return super.hitEntity(stack, target, player);
    }
}
