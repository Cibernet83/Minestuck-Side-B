package com.cibernet.minestuckgodtier.badges.heroAspectUtil;

import com.cibernet.minestuckgodtier.capabilities.GodKeyStates;
import com.cibernet.minestuckgodtier.capabilities.MSGTCapabilities;
import com.cibernet.minestuckgodtier.capabilities.api.IBadgeEffects;
import com.cibernet.minestuckgodtier.capabilities.api.IGodTierData;
import com.cibernet.minestuckgodtier.client.particles.MSGTParticles;
import com.cibernet.minestuckgodtier.util.MSGTUtils;
import com.mraof.minestuck.util.EnumAspect;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class BadgeUtilMind extends BadgeHeroAspectUtil
{
	public BadgeUtilMind()
	{
		super(EnumAspect.MIND, new ItemStack(Items.GOLD_INGOT, 200));
	}

	@Override
	public boolean onBadgeTick(World world, EntityPlayer player, IBadgeEffects badgeEffects, GodKeyStates.KeyState state, int time)
	{
		if (state == GodKeyStates.KeyState.NONE)
			return false;

		if(!player.isCreative() && player.getFoodStats().getFoodLevel() < 1)
		{
			player.sendStatusMessage(new TextComponentTranslation("status.tooExhausted"), true);
			return false;
		}

		EntityLivingBase target = MSGTUtils.getTargetEntity(player);

		if (target instanceof EntityPlayer)
		{
			IGodTierData targetData = target.getCapability(MSGTCapabilities.GOD_TIER_DATA, null);

			int tickMod = (int)(4 + 0.4f * player.getCapability(MSGTCapabilities.GOD_TIER_DATA, null).getTempKarma());
			if ((targetData.getStaticKarma() != 0 || targetData.getTempKarma() != 0) && time % tickMod == 0)
			{
				if (targetData.getStaticKarma() != 0)
					targetData.setStaticKarma(targetData.getStaticKarma() + (targetData.getStaticKarma() > 0 ? -1 : 1));
				else
					targetData.setTempKarma(targetData.getTempKarma() + (targetData.getTempKarma() > 0 ? -1 : 1));

				if (time % (tickMod * 2) == 0)
					player.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel() - 1);

				badgeEffects.oneshotPowerParticles(MSGTParticles.ParticleType.BURST, EnumAspect.MIND, 4);
			}

		}

		badgeEffects.startPowerParticles(getClass(), MSGTParticles.ParticleType.AURA, EnumAspect.MIND, 2);

		return true;
	}
}
