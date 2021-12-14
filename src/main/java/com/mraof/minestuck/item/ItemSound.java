package com.mraof.minestuck.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class ItemSound extends MSItemBase
{
	protected SoundEvent sound;
	public ItemSound(String name, SoundEvent sound)
	{
		super(name);
		this.sound = sound;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn)
	{
		player.world.playSound(player, player.posX, player.posY, player.posZ, sound, SoundCategory.PLAYERS, 1.5F, 1.0F);
		player.swingArm(handIn);
		return ActionResult.newResult(EnumActionResult.SUCCESS, player.getHeldItem(handIn));
	}
}
