package com.mraof.minestuck.entity;

import com.mraof.minestuck.item.MinestuckItems;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityOperandiEightBall extends EntityAbstractOperandiThrowable
{
	public EntityOperandiEightBall(World worldIn)
	{
		super(worldIn);
	}
	
	public EntityOperandiEightBall(World worldIn, EntityLivingBase throwerIn, ItemStack stack)
	{
		super(worldIn, throwerIn, stack);
	}
	
	public EntityOperandiEightBall(World worldIn, double x, double y, double z, ItemStack stack)
	{
		super(worldIn, x, y, z, stack);
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
				this.world.spawnParticle(EnumParticleTypes.ITEM_CRACK, this.posX, this.posY, this.posZ, ((double)this.rand.nextFloat() - 0.5D) * 0.08D, ((double)this.rand.nextFloat() - 0.5D) * 0.08D, ((double)this.rand.nextFloat() - 0.5D) * 0.08D, Item.getIdFromItem(MinestuckItems.operandiEightBall));
		}
	}
}
