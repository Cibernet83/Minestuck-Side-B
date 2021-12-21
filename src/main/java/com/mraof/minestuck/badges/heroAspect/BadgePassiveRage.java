package com.mraof.minestuck.badges.heroAspect;

import com.mraof.minestuck.capabilities.MinestuckCapabilities;
import com.mraof.minestuck.capabilities.api.IBadgeEffects;
import com.mraof.minestuck.capabilities.caps.GodKeyStates;
import com.mraof.minestuck.client.particles.MinestuckParticles;
import com.mraof.minestuck.damage.EntityCritDamageSource;
import com.mraof.minestuck.util.EnumAspect;
import com.mraof.minestuck.util.EnumRole;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class BadgePassiveRage extends BadgeHeroAspect
{
	public BadgePassiveRage() {
		super(EnumAspect.RAGE, EnumRole.PASSIVE, EnumAspect.MIND);
	}

	@Override
	public boolean onBadgeTick(World world, EntityPlayer player, IBadgeEffects badgeEffects, GodKeyStates.KeyState state, int time)
	{
		if(state == GodKeyStates.KeyState.NONE || time >= 25)
			return false;

		if(!player.isCreative() && player.getFoodStats().getFoodLevel() < 12)
		{
			player.sendStatusMessage(new TextComponentTranslation("status.tooExhausted"), true);
			return false;
		}

		if(time > 20)
			badgeEffects.startPowerParticles(getClass(), MinestuckParticles.ParticleType.BURST, EnumAspect.RAGE, 10);
		else
			badgeEffects.startPowerParticles(getClass(), MinestuckParticles.ParticleType.AURA, EnumAspect.RAGE, 10);

		if(time >= 24)
		{
			float dmg = Math.max(8, Math.abs(player.getCapability(MinestuckCapabilities.GOD_TIER_DATA, null).getTempKarma()));

			for(EntityLivingBase target : world.getEntitiesWithinAABB(EntityLivingBase.class, player.getEntityBoundingBox().grow(16), (entity) -> entity != player && (entity instanceof EntityPlayer || entity instanceof IMob)))
				if(!player.isOnSameTeam(target)) target.attackEntityFrom(new EntityCritDamageSource("vengefulOutburst", player).setCrit().setDamageBypassesArmor(), dmg);
			if (!player.isCreative() && !world.isRemote)
				player.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel() - 12);
		}

		return true;
	}
}