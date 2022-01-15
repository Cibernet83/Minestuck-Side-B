package com.mraof.minestuck.entity.underling;

import com.mraof.minestuck.alchemy.Grist;
import com.mraof.minestuck.alchemy.GristHelper;
import com.mraof.minestuck.alchemy.GristSet;
import com.mraof.minestuck.entity.ai.EntityAIAttackOnCollideWithRate;
import com.mraof.minestuck.util.Echeladder;
import com.mraof.minestuck.util.MinestuckPlayerData;
import com.mraof.minestuck.util.MinestuckSoundHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;

//Makes non-stop ogre puns
public class EntityOgre extends EntityUnderling
{
	public EntityOgre(World world)
	{
		super(world);
		setSize(3.0F, 4.5F);
		this.stepHeight = 1.0F;
	}

	@Override
	protected void initEntityAI()
	{
		super.initEntityAI();
		EntityAIAttackOnCollideWithRate aiAttack = new EntityAIAttackOnCollideWithRate(this, .3F, 40, false);
		aiAttack.setDistanceMultiplier(1.2F);
		this.tasks.addTask(3, aiAttack);
	}

	@Override
	public void applyGristType(Grist type, boolean fullHeal)
	{
		super.applyGristType(type, fullHeal);
		this.experienceValue = (int) (5 * type.getPower() + 4);
	}

	@Override
	public GristSet getGristSpoils()
	{
		return GristHelper.getRandomDrop(getGristType(), 4);
	}

	@Override
	protected float getKnockbackResistance()
	{
		return 0.4F;
	}

	@Override
	protected double getWanderSpeed()
	{
		return 0.65;
	}

	@Override
	protected double getAttackDamage()
	{
		return getGristType().getPower() * 2.1 + 6;
	}

	@Override
	protected int getVitalityGel()
	{
		return rand.nextInt(3) + 3;
	}

	@Override
	protected String getUnderlingName()
	{
		return "ogre";
	}

	protected SoundEvent getAmbientSound()
	{
		return MinestuckSoundHandler.soundOgreAmbient;
	}

	@Override
	protected float getMaximumHealth()
	{
		return getGristType() != null ? 13F * getGristType().getPower() + 50 : 1;
	}

	@Override
	public void onDeath(DamageSource cause)
	{
		super.onDeath(cause);
		Entity entity = cause.getTrueSource();
		if (this.dead && !this.world.isRemote && getGristType() != null)
		{
			computePlayerProgress((int) (40 * getGristType().getPower() + 50));
			if (entity != null && entity instanceof EntityPlayerMP && !(entity instanceof FakePlayer))
			{
				//((EntityPlayerMP) entity).addStat(MinestuckAchievementHandler.killOgre);
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