package com.cibernet.fetchmodiplus.entities;

import com.cibernet.fetchmodiplus.particles.FMPParticles;
import com.cibernet.fetchmodiplus.registries.FMPItems;
import com.cibernet.fetchmodiplus.registries.FMPSounds;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class AbstractOperandiThrowableEntity extends EntityThrowable
{
	protected ItemStack storedStack = ItemStack.EMPTY;
	
	public AbstractOperandiThrowableEntity(World worldIn)
	{
		super(worldIn);
	}
	
	public AbstractOperandiThrowableEntity(World worldIn, EntityLivingBase throwerIn, ItemStack stack)
	{
		super(worldIn, throwerIn);
		storedStack = stack;
	}
	
	public AbstractOperandiThrowableEntity(World worldIn, double x, double y, double z, ItemStack stack)
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
			world.playSound(null, thrower == null ? getPosition() : thrower.getPosition(), FMPSounds.operandiTaskComplete, SoundCategory.PLAYERS, 1, 1);
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