package com.mraof.minestuck.item.operandi;

import com.mraof.minestuck.entity.EntityAbstractOperandiThrowable;
import com.mraof.minestuck.entity.EntityOperandiEightBall;
import com.mraof.minestuck.entity.EntityOperandiSplashPotion;
import com.mraof.minestuck.item.MSItemBase;
import com.mraof.minestuck.item.MinestuckItems;
import com.mraof.minestuck.item.MinestuckTabs;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.*;
import net.minecraft.world.World;

import static com.mraof.minestuck.util.ModusStorage.getStoredItem;

public class ItemOperandiThrowable extends MSItemBase //TODO cruxite artifact throwables
{
	protected float projectileSpeed;
	protected float pitchOffset;
	protected SoundEvent thrownSound;

	public ItemOperandiThrowable(String name, float pitchOffset, float projSpeed, SoundEvent thrownSound)
	{
		super(name);
		setCreativeTab(MinestuckTabs.minestuck);
		setMaxStackSize(1);
		//OperandiModus.itemPool.add(this);

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
