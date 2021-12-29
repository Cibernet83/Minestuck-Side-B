package com.mraof.minestuck.entity;

import com.mraof.minestuck.captchalogue.captchalogueable.ICaptchalogueable;
import com.mraof.minestuck.client.particles.MinestuckParticles;
import com.mraof.minestuck.item.MinestuckItems;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityEightBall extends EntityThrowable
{
	protected ICaptchalogueable storedStack;
	
	public EntityEightBall(World worldIn)
	{
		super(worldIn);
	}
	
	public EntityEightBall(World worldIn, EntityLivingBase throwerIn, ICaptchalogueable stack)
	{
		super(worldIn, throwerIn);
		storedStack = stack;
	}
	
	public EntityEightBall(World worldIn, double x, double y, double z, ICaptchalogueable stack)
	{
		super(worldIn, x, y, z);
		storedStack = stack;
	}
	
	public static void registerFixes(DataFixer fixer)
	{
		EntityThrowable.registerFixesThrowable(fixer, "EightBall");
	}
	
	/**
	 * Handler for {@link World#setEntityState}
	 */
	@SideOnly(Side.CLIENT)
	public void handleStatusUpdate(byte id)
	{
		if (id == 3)
		{
			for (int i = 0; i < 8; ++i)
				this.world.spawnParticle(EnumParticleTypes.ITEM_CRACK, this.posX, this.posY, this.posZ, ((double)this.rand.nextFloat() - 0.5D) * 0.08D, ((double)this.rand.nextFloat() - 0.5D) * 0.08D, ((double)this.rand.nextFloat() - 0.5D) * 0.08D, Item.getIdFromItem(MinestuckItems.eightBall));
			for (int i = 0; i < 16; ++i)
				MinestuckParticles.spawnInkParticle(posX, posY, posZ, 0, 0, 0, 0x0000FF, 2);
		}
	}
	
	/**
	 * Called when this EntityThrowable hits a block or entity.
	 */
	protected void onImpact(RayTraceResult result)
	{
		if (result.entityHit != null)
			result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 1F);
		
		if (!this.world.isRemote)
		{
			if (thrower != null && this.rand.nextInt(4) == 0)
			{
				int amp = 0;
				if(thrower.getActivePotionEffect(MobEffects.UNLUCK) != null)
					amp += thrower.getActivePotionEffect(MobEffects.UNLUCK).getAmplifier();
				if(amp > 4 )
					thrower.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 600, 1));
				else thrower.addPotionEffect(new PotionEffect(MobEffects.UNLUCK, 1200, amp));
			}

			float f3 = 1.5F;
			double k1 = MathHelper.floor(posX - (double)f3 - 1.0D);
			double l1 = MathHelper.floor(posX + (double)f3 + 1.0D);
			double i2 = MathHelper.floor(posY - (double)f3 - 1.0D);
			double i1 = MathHelper.floor(posY + (double)f3 + 1.0D);
			double j2 = MathHelper.floor(posZ - (double)f3 - 1.0D);
			double j1 = MathHelper.floor(posZ + (double)f3 + 1.0D);


			for(EntityLivingBase entity : this.world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(k1, i2, j2, l1, i1, j1)))
			{
				if(rand.nextInt(3) == 0)
				{
					entity.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 100, 0, false, false));
					if(entity instanceof EntityPlayer)
						((EntityPlayer) entity).sendStatusMessage(new TextComponentTranslation("status.eightBallBlind"), true);
				}
			}

			storedStack.drop(this);
			this.world.setEntityState(this, (byte)3);
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

	/*
	protected void spawnItem()
	{
		EntityItem item = new EntityItem(world, posX, posY, posZ, storedItem);
		item.motionY = (rand.nextGaussian() * 0.05000000074505806D + 0.20000000298023224D)/2.0;
		item.setDefaultPickupDelay();
		world.spawnEntity(item);
	}
	*/
}
