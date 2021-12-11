package com.cibernet.fetchmodiplus.items;

import com.cibernet.fetchmodiplus.captchalogue.OperandiModus;
import com.cibernet.fetchmodiplus.entities.AbstractOperandiThrowableEntity;
import com.cibernet.fetchmodiplus.entities.OperandiEightBallEntity;
import com.cibernet.fetchmodiplus.entities.OperandiSplashPotionEntity;
import com.cibernet.fetchmodiplus.registries.FMPItems;
import com.mraof.minestuck.item.TabMinestuck;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class OperandiThrowableItem extends BaseItem
{
	protected float projectileSpeed;
	protected float pitchOffset;
	protected SoundEvent thrownSound;
	
	public OperandiThrowableItem(String name, float pitchOffset, float projSpeed, SoundEvent thrownSound)
	{
		super(name);
		this.setCreativeTab(TabMinestuck.instance);
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
			AbstractOperandiThrowableEntity proj = this.equals(FMPItems.operandiEightBall) ? new OperandiEightBallEntity(worldIn, playerIn, getStoredItem(stack)) : new OperandiSplashPotionEntity(worldIn, playerIn, getStoredItem(stack));
			proj.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, pitchOffset, projectileSpeed, 1.0F);
			worldIn.spawnEntity(proj);
		}
		
		playerIn.addStat(StatList.getObjectUseStats(this));
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
	}
}
