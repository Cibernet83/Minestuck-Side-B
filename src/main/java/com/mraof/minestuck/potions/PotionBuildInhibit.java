package com.mraof.minestuck.potions;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class PotionBuildInhibit extends MSPotionBase
{

    protected PotionBuildInhibit(String name, int liquidColorIn)
    {
        super(name, true, liquidColorIn);
    }

    @Override
    public void performEffect(EntityLivingBase entityLivingBaseIn, int amplifier)
    {
        if(!(entityLivingBaseIn instanceof EntityPlayer))
            return;

        EntityPlayer player = (EntityPlayer) entityLivingBaseIn;

        if(!player.isCreative())
        {
            player.capabilities.allowEdit = false;
        }
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return (duration % 5) == 0;
    }
}
