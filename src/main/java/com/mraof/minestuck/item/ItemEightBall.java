package com.mraof.minestuck.item;

import com.mraof.minestuck.captchalogue.captchalogueable.ICaptchalogueable;
import com.mraof.minestuck.entity.EntityCrystalEightBall;
import com.mraof.minestuck.entity.EntityEightBall;
import com.mraof.minestuck.util.MinestuckSounds;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.*;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

import static com.mraof.minestuck.util.ModusStorage.getStoredItem;

public class ItemEightBall extends MSItemBase
{
	private final boolean isCrystal;

	public ItemEightBall(String name, boolean isCrystal)
	{
		super(name);
		setCreativeTab(MinestuckTabs.minestuck);
		setMaxStackSize(8);
		this.isCrystal = isCrystal;
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		super.addInformation(stack, worldIn, tooltip, flagIn);

		if (isCrystal && stack.hasTagCompound())
		{
			ICaptchalogueable storedItem = getStoredItem(stack);

			if(storedItem != null)
				tooltip.add("(" + getStoredItem(stack).getDisplayName() + ")");
		}
	}

	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
	{
		ItemStack stack = playerIn.getHeldItem(handIn);

		worldIn.playSound((EntityPlayer)null, playerIn.posX, playerIn.posY, playerIn.posZ, MinestuckSounds.eightBallThrow, SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

		worldIn.playSound((EntityPlayer)null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

		playerIn.getCooldownTracker().setCooldown(this, 10);
		
		if (!worldIn.isRemote)
		{
			EntityThrowable ball = isCrystal ? new EntityCrystalEightBall(worldIn, playerIn, getStoredItem(stack)) : new EntityEightBall(worldIn, playerIn, getStoredItem(stack));
			ball.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.5F, 1.0F);
			worldIn.spawnEntity(ball);
		}

		if (!playerIn.capabilities.isCreativeMode)
			stack.shrink(1);

		playerIn.addStat(StatList.getObjectUseStats(this));
		return new ActionResult<>(EnumActionResult.SUCCESS, stack);
	}
}
