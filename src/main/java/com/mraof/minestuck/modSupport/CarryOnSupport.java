package com.mraof.minestuck.modSupport;

import com.mraof.minestuck.potions.MinestuckPotions;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import tschipp.carryon.client.keybinds.CarryOnKeybinds;

public class CarryOnSupport
{
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onEntityRightClick(PlayerInteractEvent.EntityInteract event)
    {
        EntityPlayer player = event.getEntityPlayer();

        if(player instanceof EntityPlayerMP)
        {
            ItemStack main = player.getHeldItemMainhand();
            ItemStack off = player.getHeldItemOffhand();

            if(!player.isCreative() && main.isEmpty() && off.isEmpty() && CarryOnKeybinds.isKeyPressed(player) && player.getActivePotionEffect(MinestuckPotions.CREATIVE_SHOCK) != null)
                event.setCanceled(true);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onBlockRightClick(PlayerInteractEvent.RightClickBlock event)
    {
        EntityPlayer player = event.getEntityPlayer();

        if(player instanceof EntityPlayerMP)
        {
            ItemStack main = player.getHeldItemMainhand();
            ItemStack off = player.getHeldItemOffhand();

            if(!player.isCreative() && main.isEmpty() && off.isEmpty() && CarryOnKeybinds.isKeyPressed(player) && player.getActivePotionEffect(MinestuckPotions.CREATIVE_SHOCK) != null)
                event.setCanceled(true);
        }
    }
}
