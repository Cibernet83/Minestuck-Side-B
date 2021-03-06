package com.mraof.minestuck.badges.heroAspectUtil;

import com.mraof.minestuck.capabilities.api.IBadgeEffects;
import com.mraof.minestuck.capabilities.caps.GodKeyStates;
import com.mraof.minestuck.client.particles.MinestuckParticles;
import com.mraof.minestuck.util.EnumAspect;
import com.mraof.minestuck.util.MinestuckUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class BadgeUtilHope extends BadgeHeroAspectUtil
{
	protected static final int ENERGY_USE = 4;

	public BadgeUtilHope()
	{
		super(EnumAspect.HOPE, new ItemStack(Items.MILK_BUCKET, 16));
	}

	@Override
	public boolean onBadgeTick(World world, EntityPlayer player, IBadgeEffects badgeEffects, GodKeyStates.KeyState state, int time)
	{
		if (!state.equals(GodKeyStates.KeyState.PRESS))
			return false;

		if (!player.isCreative() && player.getFoodStats().getFoodLevel() < ENERGY_USE)
		{
			player.sendStatusMessage(new TextComponentTranslation("status.tooExhausted"), true);
			return false;
		}

		EntityLivingBase target = MinestuckUtils.getTargetEntity(player);

		if (target == null || target.getActivePotionMap().isEmpty())
			target = player;

		if (!target.getActivePotionMap().isEmpty())
		{
			target.clearActivePotions();
			if (!player.isCreative())
				player.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel() - ENERGY_USE);

			badgeEffects.startPowerParticles(getClass(), MinestuckParticles.ParticleType.AURA, EnumAspect.HOPE, target != player ? 14 : 10);
		}

		return true;
	}
}
