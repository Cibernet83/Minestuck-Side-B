package com.cibernet.minestuckgodtier.items;

import com.cibernet.minestuckgodtier.MinestuckGodTier;
import com.cibernet.minestuckgodtier.capabilities.MSGTCapabilities;
import com.cibernet.minestuckgodtier.client.gui.MSGTGuiHandler;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemSashKit extends Item
{
	public ItemSashKit(String name)
	{
		setMaxStackSize(1);
		setUnlocalizedName(name);
		setCreativeTab(MSGTItems.tab);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		super.addInformation(stack, worldIn, tooltip, flagIn);
		tooltip.add(I18n.format(getUnlocalizedName()+".tooltip"));
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
	{
		if(playerIn.getCapability(MSGTCapabilities.GOD_TIER_DATA, null).isGodTier())
		{
			BlockPos pos = playerIn.getPosition();
			if(worldIn.isRemote)
				playerIn.openGui(MinestuckGodTier.instance, MSGTGuiHandler.SASH, worldIn, pos.getX(), pos.getY(), pos.getZ());
			return ActionResult.newResult(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
		}
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
}
