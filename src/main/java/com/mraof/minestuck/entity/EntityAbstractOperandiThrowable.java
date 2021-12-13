package com.mraof.minestuck.entity;

import com.mraof.minestuck.util.MinestuckSounds;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public abstract class EntityAbstractOperandiThrowable extends EntityThrowable
{
	protected ItemStack storedStack = ItemStack.EMPTY;

	public EntityAbstractOperandiThrowable(World worldIn)
	{
		super(worldIn);
	}

	public EntityAbstractOperandiThrowable(World worldIn, EntityLivingBase throwerIn, ItemStack stack)
	{
		super(worldIn, throwerIn);
		storedStack = stack;
	}
	
	public EntityAbstractOperandiThrowable(World worldIn, double x, double y, double z, ItemStack stack)
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
			spawnItem();
			this.world.setEntityState(this, (byte)3);
			this.setDead();
		}
	}
	
	protected void spawnItem()
	{
		EntityItem item = new EntityItem(world, posX, posY, posZ, storedStack);
		item.motionY = (rand.nextGaussian() * 0.05000000074505806D + 0.20000000298023224D)/2.0;
		item.setDefaultPickupDelay();
		
		world.spawnEntity(item);
	}
}