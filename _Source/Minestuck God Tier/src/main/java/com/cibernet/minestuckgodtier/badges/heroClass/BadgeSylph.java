package com.cibernet.minestuckgodtier.badges.heroClass;

import com.cibernet.minestuckgodtier.capabilities.GodKeyStates;
import com.cibernet.minestuckgodtier.capabilities.api.IBadgeEffects;
import com.cibernet.minestuckgodtier.client.particles.MSGTParticles;
import com.cibernet.minestuckgodtier.util.MSGTUtils;
import com.mraof.minestuck.util.EnumClass;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class BadgeSylph extends BadgeHeroClass
{
	public BadgeSylph() {
		super(EnumClass.SYLPH);
	}

	@Override
	public boolean onBadgeTick(World world, EntityPlayer player, IBadgeEffects badgeEffects, GodKeyStates.KeyState state, int time)
	{
		if(!player.isCreative() && player.getFoodStats().getFoodLevel() < 1)
		{
			player.sendStatusMessage(new TextComponentTranslation("status.tooExhausted"), true);
			return false;
		}

		if(!state.equals(GodKeyStates.KeyState.HELD))
			return false;

		EntityLivingBase target = MSGTUtils.getTargetEntity(player);

		if(target == null || !(target.getHealth() < target.getMaxHealth() || (target instanceof EntityPlayer && ((EntityPlayer) target).getFoodStats().needFood())))
			return false;

		badgeEffects.startPowerParticles(getClass(), MSGTParticles.ParticleType.AURA, EnumClass.SYLPH, 5);

		if((time % 10) == 0)
		{
			target.heal(2);

			if((time % 20) == 0 && target instanceof EntityPlayer && ((EntityPlayer) target).getFoodStats().needFood())
				((EntityPlayer) target).getFoodStats().addStats(1, 2);



			if (!player.isCreative())
				player.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel() - 1);
			((WorldServer)world).spawnParticle(EnumParticleTypes.HEART, target.posX + ((Math.random()-0.5)/2), target.posY+1.5, target.posZ + ((Math.random()-0.5)/2), 1, 1, 0, 0.5, 0);
		}

		return true;
	}
}