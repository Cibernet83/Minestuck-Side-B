package com.cibernet.minestuckgodtier.badges.heroClass;

import com.cibernet.minestuckgodtier.capabilities.GodKeyStates;
import com.cibernet.minestuckgodtier.capabilities.MSGTCapabilities;
import com.cibernet.minestuckgodtier.capabilities.api.IBadgeEffects;
import com.cibernet.minestuckgodtier.client.particles.MSGTParticles;
import com.cibernet.minestuckgodtier.damage.EntityCritDamageSource;
import com.cibernet.minestuckgodtier.util.MSGTUtils;
import com.mraof.minestuck.util.EnumClass;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class BadgePrince extends BadgeHeroClass
{
	public BadgePrince()
	{
		super(EnumClass.PRINCE);
	}

	@Override
	public boolean onBadgeTick(World world, EntityPlayer player, IBadgeEffects badgeEffects, GodKeyStates.KeyState state, int time)
	{
		if(state == GodKeyStates.KeyState.NONE)
			return false;

		if(!player.isCreative() && player.getFoodStats().getFoodLevel() < 9)
		{
			if(state.equals(GodKeyStates.KeyState.HELD))
				player.sendStatusMessage(new TextComponentTranslation("status.tooExhausted"), true);
			return false;
		}

		if(state.equals(GodKeyStates.KeyState.RELEASED))
		{
			EntityLivingBase target = MSGTUtils.getTargetEntity(player);
			float dmg = 10 * Math.min(3.0f, Math.max(1.0f, time/40f));

			if(target != null)
			{
				target.getCapability(MSGTCapabilities.BADGE_EFFECTS, null).oneshotPowerParticles(MSGTParticles.ParticleType.AURA, EnumClass.PRINCE, 20);
				target.attackEntityFrom(new EntityCritDamageSource("princeDmg", player).setCrit(), dmg);
				if (!player.isCreative())
					player.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel() - 9);
			}

		}
		else if((int) (time % (120f / Math.max(time, 1f))) == 0)
			badgeEffects.startPowerParticles(getClass(), MSGTParticles.ParticleType.AURA, EnumClass.PRINCE, 2);

		return true;
	}
}
