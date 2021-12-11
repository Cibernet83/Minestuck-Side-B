package com.cibernet.minestuckgodtier.badges.heroAspect;

import com.cibernet.minestuckgodtier.MinestuckGodTier;
import com.cibernet.minestuckgodtier.capabilities.GodKeyStates;
import com.cibernet.minestuckgodtier.capabilities.MSGTCapabilities;
import com.cibernet.minestuckgodtier.capabilities.api.IBadgeEffects;
import com.cibernet.minestuckgodtier.client.particles.MSGTParticles;
import com.cibernet.minestuckgodtier.util.EnumRole;
import com.cibernet.minestuckgodtier.util.MSGTUtils;
import com.mraof.minestuck.util.EnumAspect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BadgeActiveLife extends BadgeHeroAspect
{
	public BadgeActiveLife()
	{
		super(EnumAspect.LIFE, EnumRole.ACTIVE, EnumAspect.BLOOD);
	}

	@Override
	public boolean onBadgeTick(World world, EntityPlayer player, IBadgeEffects badgeEffects, GodKeyStates.KeyState state, int time)
	{
		if (state != GodKeyStates.KeyState.PRESS)
			return false;

		if(!player.isCreative() && player.getFoodStats().getFoodLevel() < 4)
		{
			player.sendStatusMessage(new TextComponentTranslation("status.tooExhausted"), true);
			return false;
		}

		EntityLivingBase target = MSGTUtils.getTargetEntity(player);

		if (target != null)
		{
			badgeEffects.startPowerParticles(getClass(), MSGTParticles.ParticleType.AURA, EnumAspect.LIFE, 10);

			target.attackEntityFrom(new LifeDamageSource(player), 4);
			player.addPotionEffect(new PotionEffect(MobEffects.HEALTH_BOOST, 400));
			player.heal(4);

			if(target.hasCapability(MSGTCapabilities.BADGE_EFFECTS, null))
				target.getCapability(MSGTCapabilities.BADGE_EFFECTS, null).oneshotPowerParticles(MSGTParticles.ParticleType.AURA, EnumAspect.LIFE, 5);

			if (!player.isCreative())
				player.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel() - 4);
		}
		else badgeEffects.startPowerParticles(getClass(), MSGTParticles.ParticleType.AURA, EnumAspect.LIFE, 5);

		return true;
	}

	public static class LifeDamageSource extends DamageSource
	{
		protected Entity damageSourceEntity;

		public LifeDamageSource(Entity damageSourceEntityIn)
		{
			super(MinestuckGodTier.MODID+".lifeforceLeech");
			this.damageSourceEntity = damageSourceEntityIn;
			setDamageBypassesArmor();
		}

		public Entity getTrueSource()
		{
			return this.damageSourceEntity;
		}

		public ITextComponent getDeathMessage(EntityLivingBase entityLivingBaseIn)
		{
			return  new TextComponentTranslation("death.attack." + this.damageType, new Object[] {entityLivingBaseIn.getDisplayName(), this.damageSourceEntity.getDisplayName()});
		}

		/**
		 * Gets the location from which the damage originates.
		 */
		@Nullable
		public Vec3d getDamageLocation()
		{
			return new Vec3d(this.damageSourceEntity.posX, this.damageSourceEntity.posY, this.damageSourceEntity.posZ);
		}
	}
}
