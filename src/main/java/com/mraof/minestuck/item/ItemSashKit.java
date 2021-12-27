package com.mraof.minestuck.item;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.capabilities.MinestuckCapabilities;
import com.mraof.minestuck.client.gui.MinestuckGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemSashKit extends MSItemBase
{
	public ItemSashKit(String name)
	{
		super(name);
		setMaxStackSize(1);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
	{
		if(playerIn.getCapability(MinestuckCapabilities.GOD_TIER_DATA, null).isGodTier())
		{
			BlockPos pos = playerIn.getPosition();
			if(worldIn.isRemote)
				playerIn.openGui(Minestuck.instance, MinestuckGuiHandler.GuiId.SASH.ordinal(), worldIn, pos.getX(), pos.getY(), pos.getZ());
			return ActionResult.newResult(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
		}
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
}
