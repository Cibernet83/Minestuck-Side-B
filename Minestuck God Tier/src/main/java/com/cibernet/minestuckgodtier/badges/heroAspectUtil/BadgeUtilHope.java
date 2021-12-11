package com.cibernet.minestuckgodtier.badges.heroAspectUtil;

import com.cibernet.minestuckgodtier.capabilities.GodKeyStates;
import com.cibernet.minestuckgodtier.capabilities.api.IBadgeEffects;
import com.cibernet.minestuckgodtier.client.particles.MSGTParticles;
import com.cibernet.minestuckgodtier.util.MSGTUtils;
import com.mraof.minestuck.util.EnumAspect;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BadgeUtilHope extends BadgeHeroAspectUtil
{
	public BadgeUtilHope()
	{
		super(EnumAspect.HOPE, new ItemStack(Items.MILK_BUCKET, 16));
	}

	protected static final int ENERGY_USE = 4;

	@Override
	public boolean onBadgeTick(World world, EntityPlayer player, IBadgeEffects badgeEffects, GodKeyStates.KeyState state, int time)
	{
		if(!state.equals(GodKeyStates.KeyState.PRESS))
			return false;

		if(!player.isCreative() && player.getFoodStats().getFoodLevel() < ENERGY_USE)
		{
			player.sendStatusMessage(new TextComponentTranslation("status.tooExhausted"), true);
			return false;
		}

		EntityLivingBase target = MSGTUtils.getTargetEntity(player);

		if(target == null || target.getActivePotionMap().isEmpty())
			target = player;

		if(!target.getActivePotionMap().isEmpty())
		{
			target.clearActivePotions();
			if (!player.isCreative())
				player.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel() - ENERGY_USE);

			badgeEffects.startPowerParticles(getClass(), MSGTParticles.ParticleType.AURA, EnumAspect.HOPE, target != player ? 14 : 10);
		}

		return true;
	}
}
