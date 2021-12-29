package com.mraof.minestuck.entity;

import com.mraof.minestuck.captchalogue.captchalogueable.ICaptchalogueable;
import com.mraof.minestuck.item.MinestuckItems;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityCrystalEightBall extends EntityThrowable
{
	protected ICaptchalogueable storedItem;

	public EntityCrystalEightBall(World worldIn)
	{
		super(worldIn);
	}

	public EntityCrystalEightBall(World worldIn, EntityLivingBase throwerIn, ICaptchalogueable stored)
	{
		super(worldIn, throwerIn);
		storedItem = stored;
	}

	public EntityCrystalEightBall(World worldIn, double x, double y, double z, ICaptchalogueable stored)
	{
		super(worldIn, x, y, z);
		storedItem = stored;
	}
	
	public static void registerFixes(DataFixer fixer)
	{
		EntityThrowable.registerFixesThrowable(fixer, "CrystalEightBall");
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
				this.world.spawnParticle(EnumParticleTypes.ITEM_CRACK, this.posX, this.posY, this.posZ, ((double)this.rand.nextFloat() - 0.5D) * 0.08D, ((double)this.rand.nextFloat() - 0.5D) * 0.08D, ((double)this.rand.nextFloat() - 0.5D) * 0.08D, Item.getIdFromItem(MinestuckItems.crystalEightBall));
			//for (int i = 0; i < 16; ++i)
			//	FMPParticles.spawnInkParticle(posX, posY, posZ, 0, 0, 0, 0x0000FF, 2);
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
			try
			{
				if (thrower != null && this.rand.nextInt(4) == 0)
				{
					int amp = 0;
					if(thrower.getActivePotionEffect(MobEffects.UNLUCK) != null)
						amp += thrower.getActivePotionEffect(MobEffects.UNLUCK).getAmplifier();
					if(amp > 4 )
						thrower.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 1200, 1));
					else thrower.addPotionEffect(new PotionEffect(MobEffects.UNLUCK, 2400, amp));
				}
			} catch (Throwable t)
			{
				t.printStackTrace();
			}

			storedItem.drop(this);
			this.world.setEntityState(this, (byte)3);
			this.setDead();
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound)
	{
		super.writeEntityToNBT(compound);

		compound.setTag("StoredItem", ICaptchalogueable.writeToNBT(storedItem));
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound)
	{
		super.readEntityFromNBT(compound);

		storedItem = ICaptchalogueable.readFromNBT(compound.getCompoundTag("StoredItem"));
	}
}
