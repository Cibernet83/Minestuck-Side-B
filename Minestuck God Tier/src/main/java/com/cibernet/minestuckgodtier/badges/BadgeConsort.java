package com.cibernet.minestuckgodtier.badges;

import com.cibernet.minestuckgodtier.capabilities.MSGTCapabilities;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class BadgeConsort extends BadgeLevel
{
    public BadgeConsort(String unlocName, int requiredLevel) {
        super(unlocName, requiredLevel);
    }

    @Override
    public boolean canUse(World world, EntityPlayer player) {
        return true;
    }

    @Override
    public ResourceLocation getTextureLocation()
    {
        return new ResourceLocation(getRegistryName().getResourceDomain(), "textures/gui/badges/" + getRegistryName().getResourcePath() + "_" + (Minecraft.getMinecraft().player.getCapability(MSGTCapabilities.GOD_TIER_DATA, null).getConsortType() == null ? "salamander" : Minecraft.getMinecraft().player.getCapability(MSGTCapabilities.GOD_TIER_DATA, null).getConsortType().getName().toLowerCase()) + ".png");
    }
}
