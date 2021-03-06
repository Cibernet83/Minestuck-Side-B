package com.mraof.minestuck.entity;

import com.mraof.minestuck.alchemy.Grist;
import com.mraof.minestuck.alchemy.GristHelper;
import com.mraof.minestuck.alchemy.GristSet;
import com.mraof.minestuck.entity.ai.EntityAIAttackOnCollideWithRate;
import com.mraof.minestuck.entity.underling.EntityUnderling;
import com.mraof.minestuck.util.Echeladder;
import com.mraof.minestuck.util.MinestuckPlayerData;
import com.mraof.minestuck.util.MinestuckSoundHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class EntityAcheron extends EntityUnderling
{
	public EntityAcheron(World world)
	{
		super(world);
		setSize(8.0F, 11.0F);
		this.stepHeight = 1.0F;
	}

	@Override
	protected void initEntityAI()
	{
		super.initEntityAI();
		EntityAIAttackOnCollideWithRate aiAttack = new EntityAIAttackOnCollideWithRate(this, .2F, 20, false);
		aiAttack.setDistanceMultiplier(4F);
		this.tasks.addTask(3, aiAttack);
	}

	@Override
	public void applyGristType(Grist type, boolean fullHeal)
	{
		super.applyGristType(type, fullHeal);
		this.experienceValue = (int) (4000 * type.getPower() + 700);
	}

	@Override
	public GristSet getGristSpoils()
	{
		return GristHelper.getRandomDrop(getGristType(), 20);
	}

	@Override
	protected float getKnockbackResistance()
	{
		return 0.8F;
	}

	@Override
	protected double getWanderSpeed()
	{
		return 0.55;
	}

	@Override
	protected double getAttackDamage()
	{
		return this.getGristType().getPower() * 10 + 20;
	}

	@Override
	protected int getVitalityGel()
	{
		return rand.nextInt(30) + 5;
	}

	@Override
	protected String getUnderlingName()
	{
		return "acheron";
	}

	protected SoundEvent getAmbientSound()
	{
		return MinestuckSoundHandler.soundOgreAmbient;
	}

	@Override
	protected float getMaximumHealth()
	{
		return getGristType() != null ? 500 * getGristType().getPower() + 1200 : 1;
	}

	@Override
	public void onDeath(DamageSource cause)
	{
		super.onDeath(cause);
		Entity entity = cause.getTrueSource();
		if (this.dead && !this.world.isRemote && getGristType() != null)
		{
			computePlayerProgress((int) (400 * getGristType().getPower() + 800));
			if (entity != null && entity instanceof EntityPlayerMP)
			{
				Echeladder ladder = MinestuckPlayerData.getData((EntityPlayerMP) entity).echeladder;
				ladder.checkBonus((byte) (Echeladder.UNDERLING_BONUS_OFFSET + 1));
			}
		}
	}

	protected SoundEvent getHurtSound(DamageSource damageSourceIn)
	{
		return MinestuckSoundHandler.soundOgreHurt;
	}

	protected SoundEvent getDeathSound()
	{
		return MinestuckSoundHandler.soundOgreDeath;
	}
}
