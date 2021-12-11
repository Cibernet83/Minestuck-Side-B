package com.cibernet.fetchmodiplus.items;

import com.cibernet.fetchmodiplus.entities.CrystalEightBallEntity;
import com.cibernet.fetchmodiplus.entities.EightBallEntity;
import com.cibernet.fetchmodiplus.registries.FMPSounds;
import com.mraof.minestuck.alchemy.AlchemyRecipes;
import com.mraof.minestuck.item.TabMinestuck;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.stats.StatList;
import net.minecraft.util.*;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import javax.annotation.Nullable;
import java.util.List;

public class EightBallItem extends BaseItem
{
	private final boolean isCrystal;

	public EightBallItem(String name, boolean isCrystal)
	{
		super(name);
		this.setCreativeTab(TabMinestuck.instance);
		setMaxStackSize(8);
		this.isCrystal = isCrystal;
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		super.addInformation(stack, worldIn, tooltip, flagIn);

		if (isCrystal && stack.hasTagCompound())
		{
			ItemStack storedStack = getStoredItem(stack);

			if(!storedStack.isEmpty())
			{
				String stackSize = storedStack.getCount() > 0 ? storedStack.getCount() + "x" : "";
				tooltip.add("(" + stackSize + getStoredItem(stack).getDisplayName() + ")");
			}
		}
	}

	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
	{
		ItemStack stack = playerIn.getHeldItem(handIn);

		worldIn.playSound((EntityPlayer)null, playerIn.posX, playerIn.posY, playerIn.posZ, FMPSounds.eightBallThrow, SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

		worldIn.playSound((EntityPlayer)null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

		playerIn.getCooldownTracker().setCooldown(this, 10);
		
		if (!worldIn.isRemote)
		{
			EntityThrowable ball = isCrystal ? new CrystalEightBallEntity(worldIn, playerIn, getStoredItem(stack)) : new EightBallEntity(worldIn, playerIn, getStoredItem(stack));
			ball.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.5F, 1.0F);
			worldIn.spawnEntity(ball);
		}

		if (!playerIn.capabilities.isCreativeMode)
			stack.shrink(1);

		playerIn.addStat(StatList.getObjectUseStats(this));
		return new ActionResult<>(EnumActionResult.SUCCESS, stack);
	}
}
