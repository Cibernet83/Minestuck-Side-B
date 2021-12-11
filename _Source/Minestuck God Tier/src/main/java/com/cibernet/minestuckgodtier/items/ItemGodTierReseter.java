package com.cibernet.minestuckgodtier.items;

import com.cibernet.minestuckgodtier.util.MSGTUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemGodTierReseter extends Item
{
    public ItemGodTierReseter(String unlocName)
    {
        setUnlocalizedName(unlocName);
        setCreativeTab(MSGTItems.tab);
        setMaxStackSize(1);
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack)
    {
        return 60;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.BOW;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        playerIn.setActiveHand(handIn);
        return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }


    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving)
    {
        if(entityLiving instanceof EntityPlayer && MSGTUtils.resetGodTier((EntityPlayer)entityLiving))
        {
            stack.shrink(1);
            return stack;
        }

        return super.onItemUseFinish(stack, worldIn, entityLiving);
    }
}
