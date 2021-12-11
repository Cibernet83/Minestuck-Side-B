package com.cibernet.minestuckgodtier.badges.heroAspect;

import com.cibernet.minestuckgodtier.capabilities.GodKeyStates;
import com.cibernet.minestuckgodtier.capabilities.MSGTCapabilities;
import com.cibernet.minestuckgodtier.capabilities.api.IBadgeEffects;
import com.cibernet.minestuckgodtier.client.particles.MSGTParticles;
import com.cibernet.minestuckgodtier.damage.EntityCritDamageSource;
import com.cibernet.minestuckgodtier.util.EnumRole;
import com.cibernet.minestuckgodtier.util.MSGTUtils;
import com.mraof.minestuck.util.EnumAspect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.Collections;
import java.util.List;

public class BadgeActiveLight extends BadgeHeroAspect
{
	public BadgeActiveLight()
	{
		super(EnumAspect.LIGHT, EnumRole.ACTIVE, EnumAspect.DOOM);
	}

	@Override
	public boolean onBadgeTick(World world, EntityPlayer player, IBadgeEffects badgeEffects, GodKeyStates.KeyState state, int time)
	{
		if(state.equals(GodKeyStates.KeyState.PRESS))
		{
			EntityLivingBase target = MSGTUtils.getTargetEntity(player);
			if(target != null)
			{
				PotionEffect effect = new PotionEffect(MobEffects.GLOWING, 1200, 0);
				effect.setCurativeItems(Collections.emptyList());
				target.addPotionEffect(effect);
				target.getCapability(MSGTCapabilities.BADGE_EFFECTS, null).oneshotPowerParticles(MSGTParticles.ParticleType.AURA, EnumAspect.LIGHT, 10);
				return false;
			}
		}

        if(state == GodKeyStates.KeyState.NONE || time > 15)
            return false;

		if(!player.isCreative() && player.getFoodStats().getFoodLevel() < 8)
		{
			player.sendStatusMessage(new TextComponentTranslation("status.tooExhausted"), true);
			return false;
		}

		if(time > 13)
			badgeEffects.startPowerParticles(getClass(), MSGTParticles.ParticleType.BURST, EnumAspect.LIGHT, 20);
		else
			badgeEffects.startPowerParticles(getClass(), MSGTParticles.ParticleType.AURA, EnumAspect.LIGHT, 10);

		if(time == 15)
		{
			for(EntityLivingBase target : world.getEntities(EntityLivingBase.class, (entity) -> entity != player && entity.isPotionActive(MobEffects.GLOWING)))
			{
				if(player.isOnSameTeam(target))
					continue;

				EntityLightningBolt lightning = new EntityLightningBolt(world, target.posX, target.posY, target.posZ, true);
				world.spawnEntity(lightning);
				world.addWeatherEffect(lightning);

				List<Entity> list = lightning.world.getEntitiesWithinAABBExcludingEntity(lightning, new AxisAlignedBB(lightning.posX - 3.0D, lightning.posY - 3.0D, lightning.posZ - 3.0D,
						lightning.posX + 3.0D, lightning.posY + 6.0D + 3.0D, lightning.posZ + 3.0D));

				for (Entity entity : list)
					if (!ForgeEventFactory.onEntityStruckByLightning(entity, lightning))
					{
						entity.attackEntityFrom(new EntityCritDamageSource("lightningBolt", player), 10);
						entity.onStruckByLightning(lightning);
					}

				target.getCapability(MSGTCapabilities.BADGE_EFFECTS, null).oneshotPowerParticles(MSGTParticles.ParticleType.AURA, EnumAspect.LIGHT, 10);
				target.removePotionEffect(MobEffects.GLOWING);
			}
			if (!player.isCreative())
				player.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel() - 5);
		}

		return true;
	}
}
