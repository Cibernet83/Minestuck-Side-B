package com.mraof.minestuck.item;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class ItemRazorBlade extends MSItemBase
{
	public ItemRazorBlade()
	{
		super("razorBlade");
		this.setCreativeTab(MinestuckTabs.minestuck);
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
	{
		if (attacker instanceof EntityPlayer)
		{
			if (!((EntityPlayer) attacker).capabilities.isCreativeMode)
			{
				EntityItem razor = new EntityItem(attacker.world, attacker.posX, attacker.posY, attacker.posZ, stack.copy());
				if (!attacker.world.isRemote)
				{
					razor.getItem().setCount(1);
					razor.setPickupDelay(40);
					attacker.world.spawnEntity(razor);
					stack.shrink(1);
					ITextComponent message = new TextComponentTranslation("item.razorBlade.mishandle");
					attacker.sendMessage(message);
				}
				attacker.setHealth(attacker.getHealth() - 1);
			}
		}
		return super.hitEntity(stack, target, attacker);
	}

	@Override
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving)
	{
		if (entityLiving instanceof EntityPlayer)
		{
			if (!((EntityPlayer) entityLiving).capabilities.isCreativeMode)
			{
				EntityItem razor = new EntityItem(entityLiving.world, entityLiving.posX, entityLiving.posY, entityLiving.posZ, stack.copy());
				if (!entityLiving.world.isRemote)
				{
					razor.getItem().setCount(1);
					razor.setPickupDelay(40);
					entityLiving.world.spawnEntity(razor);
					stack.shrink(1);
					ITextComponent message = new TextComponentTranslation("item.razorBlade.mishandle");
					entityLiving.sendMessage(message);
				}
				entityLiving.setHealth(entityLiving.getHealth() - 1);
			}
		}
		return super.onBlockDestroyed(stack, worldIn, state, pos, entityLiving);
	}
}
