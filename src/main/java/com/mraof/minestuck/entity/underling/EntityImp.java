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

public class EntityImp extends EntityUnderling
{
	public EntityImp(World world)
	{
		super(world);
		setSize(0.75F, 1.5F);
	}

	@Override
	protected void initEntityAI()
	{
		super.initEntityAI();
		EntityAIAttackOnCollideWithRate aiAttack = new EntityAIAttackOnCollideWithRate(this, .4F, 20, false);
		this.tasks.addTask(3, aiAttack);
	}

	@Override
	public void applyGristType(Grist type, boolean fullHeal)
	{
		super.applyGristType(type, fullHeal);
		this.experienceValue = (int) (3 * type.getPower() + 1);
	}

	@Override
	public GristSet getGristSpoils()
	{
		return GristHelper.getRandomDrop(getGristType(), 1);
	}

	@Override
	protected float getKnockbackResistance()
	{
		return 0;
	}

	@Override
	protected double getWanderSpeed()
	{
		return 0.6;
	}

	@Override
	protected double getAttackDamage()
	{
		return Math.ceil(this.getGristType().getPower() + 1);
	}

	@Override
	protected int getVitalityGel()
	{
		return rand.nextInt(3) + 1;
	}

	@Override
	protected String getUnderlingName()
	{
		return "imp";
	}

	protected SoundEvent getAmbientSound()
	{
		return MinestuckSoundHandler.soundImpAmbient;
	}

	@Override
	protected float getMaximumHealth()
	{
		return getGristType() != null ? 8 * getGristType().getPower() + 6 : 1;
	}

	@Override
	public void onDeath(DamageSource cause)
	{
		super.onDeath(cause);
		Entity entity = cause.getTrueSource();
		if (this.dead && !this.world.isRemote && getGristType() != null)
		{
			computePlayerProgress((int) (2 + 3 * getGristType().getPower()));
			if (entity != null && entity instanceof EntityPlayerMP && !(entity instanceof FakePlayer))
			{
				Echeladder ladder = MinestuckPlayerData.getData((EntityPlayerMP) entity).echeladder;
				ladder.checkBonus((Echeladder.UNDERLING_BONUS_OFFSET));
			}
		}
	}

	protected SoundEvent getHurtSound(DamageSource damageSourceIn)
	{
		return MinestuckSoundHandler.soundImpHurt;
	}

	protected SoundEvent getDeathSound()
	{
		return MinestuckSoundHandler.soundImpDeath;
	}
}