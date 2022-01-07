package com.mraof.minestuck.entity;

import com.mraof.minestuck.captchalogue.captchalogueable.ICaptchalogueable;
import com.mraof.minestuck.item.MinestuckItems;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityOperandiSplashPotion extends EntityAbstractOperandiThrowable
{
	public EntityOperandiSplashPotion(World worldIn)
	{
		super(worldIn);
	}

	public EntityOperandiSplashPotion(World worldIn, EntityLivingBase throwerIn, ICaptchalogueable stack)
	{
		super(worldIn, throwerIn, stack);
	}

	@Override
	protected void onImpact(RayTraceResult result)
	{
		super.onImpact(result);
		this.world.playSound(null, getPosition(), SoundEvents.ENTITY_SPLASH_POTION_BREAK, SoundCategory.NEUTRAL, 1.0F, this.world.rand.nextFloat() * 0.1F + 0.9F);
	}

	/**
	 * Handler for {@link World#setEntityState}
	 */
	@SideOnly(Side.CLIENT)
	public void handleStatusUpdate(byte id)
	{
		if (id == 3)
		{
			for (int i = 0; i < 16; ++i)
				this.world.spawnParticle(EnumParticleTypes.ITEM_CRACK, this.posX, this.posY + 0.1f, this.posZ, ((double) this.rand.nextFloat() - 0.5D) * 0.08D, ((double) this.rand.nextFloat() - 0.5D) * 0.08D, ((double) this.rand.nextFloat() - 0.5D) * 0.08D, Item.getIdFromItem(MinestuckItems.operandiSplashPotion));
		}
	}
}
