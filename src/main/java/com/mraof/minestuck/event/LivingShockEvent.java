package com.mraof.minestuck.event;

import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

@Cancelable
public class LivingShockEvent extends Event
{
	protected final EntityLivingBase target;
	protected final EntityLivingBase source;
	protected final int ogTime;
	protected final float ogDamage;
	protected int time;
	protected float damage;

	public LivingShockEvent(EntityLivingBase target, EntityLivingBase player, int time, float damage)
	{
		this.target = target;
		this.source = player;
		this.ogDamage = damage;
		this.damage = damage;
		this.ogTime = time;
		this.time = time;
	}

	@Override
	public boolean isCancelable()
	{
		return true;
	}

	public EntityLivingBase getSource()
	{
		return source;
	}

	public EntityLivingBase getTarget()
	{
		return target;
	}

	public float getDamage()
	{
		return damage;
	}

	public void setDamage(float damage)
	{
		this.damage = damage;
	}

	public float getOriginalDamage()
	{
		return ogDamage;
	}

	public int getTime()
	{
		return time;
	}

	public void setTime(int time)
	{
		this.time = time;
	}

	public int getOriginalTime()
	{
		return ogTime;
	}
}
