package com.mraof.minestuck.potions;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public class PotionFlight extends MSPotionBase
{
	protected PotionFlight(String name, boolean isBadEffectIn, int liquidColorIn)
	{
		super(name, isBadEffectIn, liquidColorIn);
		if (!isBadEffectIn)
			setBeneficial();
	}

	@Override
	public void performEffect(EntityLivingBase entityLivingBaseIn, int amplifier)
	{
		if (!(entityLivingBaseIn instanceof EntityPlayer))
			return;

		EntityPlayer player = (EntityPlayer) entityLivingBaseIn;

		if (!player.isSpectator())
		{
			player.capabilities.allowFlying = !isBadEffect();
			if (isBadEffect())
				player.capabilities.isFlying = false;

			if (!player.world.isRemote)
				((EntityPlayerMP) player).clearElytraFlying();
		}
	}

	@Override
	public boolean isReady(int duration, int amplifier)
	{

		return (duration % 5) == 0;
	}
}
