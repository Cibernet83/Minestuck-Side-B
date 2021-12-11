package com.cibernet.fetchmodiplus.entities;

import com.cibernet.fetchmodiplus.registries.FMPItems;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class OperandiSplashPotionEntity extends AbstractOperandiThrowableEntity
{
	public OperandiSplashPotionEntity(World worldIn)
	{
		super(worldIn);
	}
	
	public OperandiSplashPotionEntity(World worldIn, EntityLivingBase throwerIn, ItemStack stack)
	{
		super(worldIn, throwerIn, stack);
	}
	
	public OperandiSplashPotionEntity(World worldIn, double x, double y, double z, ItemStack stack)
	{
		super(worldIn, x, y, z, stack);
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
				this.world.spawnParticle(EnumParticleTypes.ITEM_CRACK, this.posX, this.posY+0.1f, this.posZ, ((double)this.rand.nextFloat() - 0.5D) * 0.08D, ((double)this.rand.nextFloat() - 0.5D) * 0.08D, ((double)this.rand.nextFloat() - 0.5D) * 0.08D, Item.getIdFromItem(FMPItems.operandiSplashPotion));
		}
	}
}
