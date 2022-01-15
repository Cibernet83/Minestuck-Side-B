package com.mraof.minestuck.entity;

import com.mraof.minestuck.captchalogue.captchalogueable.ICaptchalogueable;
import com.mraof.minestuck.util.MinestuckSounds;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public abstract class EntityAbstractOperandiThrowable extends EntityThrowable
{
	protected ICaptchalogueable storedStack;

	public EntityAbstractOperandiThrowable(World worldIn)
	{
		super(worldIn);
	}

	public EntityAbstractOperandiThrowable(World worldIn, EntityLivingBase throwerIn, ICaptchalogueable stack)
	{
		super(worldIn, throwerIn);
		storedStack = stack;
	}

	public EntityAbstractOperandiThrowable(World worldIn, double x, double y, double z, ICaptchalogueable stack)
	{
		super(worldIn, x, y, z);
		storedStack = stack;
	}

	public static void registerFixes(DataFixer fixer)
	{
		EntityThrowable.registerFixesThrowable(fixer, "EightBall");
	}

	/**
	 * Called when this EntityThrowable hits a block or entity.
	 */
	protected void onImpact(RayTraceResult result)
	{
		if (result.entityHit != null)
			result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 0F);

		if (!this.world.isRemote)
		{

			world.playSound(null, thrower == null ? getPosition() : thrower.getPosition(), MinestuckSounds.operandiTaskComplete, SoundCategory.PLAYERS, 1, 1);
			storedStack.drop(this);
			this.world.setEntityState(this, (byte) 3);
			this.setDead();
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound)
	{
		super.writeEntityToNBT(compound);

		compound.setTag("StoredItem", ICaptchalogueable.writeToNBT(storedStack));
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound)
	{
		super.readEntityFromNBT(compound);

		storedStack = ICaptchalogueable.readFromNBT(compound.getCompoundTag("StoredItem"));
	}
}