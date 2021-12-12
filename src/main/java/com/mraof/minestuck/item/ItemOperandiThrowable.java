package com.mraof.minestuck.item;

import com.mraof.minestuck.inventory.captchalouge.OperandiModus;
import com.mraof.minestuck.entity.EntityAbstractOperandiThrowable;
import com.mraof.minestuck.entity.EntityOperandiEightBall;
import com.mraof.minestuck.entity.EntityOperandiSplashPotion;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.*;
import net.minecraft.world.World;

import static com.mraof.minestuck.util.ModusStorage.getStoredItem;

public class ItemOperandiThrowable extends Item
{
	protected float projectileSpeed;
	protected float pitchOffset;
	protected SoundEvent thrownSound;
	
	public ItemOperandiThrowable(String name, float pitchOffset, float projSpeed, SoundEvent thrownSound)
	{
		setUnlocalizedName(name);
		setCreativeTab(TabMinestuck.instance);
		setMaxStackSize(1);
		OperandiModus.itemPool.add(this);
		
		this.projectileSpeed = projSpeed;
		this.pitchOffset = pitchOffset;
		this.thrownSound = thrownSound;
	}
	
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
	{
	}
	
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
	{
		ItemStack stack = playerIn.getHeldItem(handIn);
		
		if (!playerIn.capabilities.isCreativeMode)
			stack.shrink(1);
		worldIn.playSound(null, playerIn.posX, playerIn.posY, playerIn.posZ, thrownSound, SoundCategory.PLAYERS, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
		
		if (!worldIn.isRemote)
		{
			EntityAbstractOperandiThrowable proj = this.equals(MinestuckItems.operandiEightBall) ? new EntityOperandiEightBall(worldIn, playerIn, getStoredItem(stack)) : new EntityOperandiSplashPotion(worldIn, playerIn, getStoredItem(stack));
			proj.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, pitchOffset, projectileSpeed, 1.0F);
			worldIn.spawnEntity(proj);
		}
		
		playerIn.addStat(StatList.getObjectUseStats(this));
		return new ActionResult<>(EnumActionResult.SUCCESS, stack);
	}
}
