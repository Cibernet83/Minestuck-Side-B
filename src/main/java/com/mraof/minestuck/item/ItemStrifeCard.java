package com.mraof.minestuck.item;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.client.gui.MinestuckGuiHandler;
import com.mraof.minestuck.strife.StrifePortfolioHandler;
import com.mraof.minestuck.strife.StrifeSpecibus;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ItemStrifeCard extends MSItemBase
{
	public ItemStrifeCard(String name)
	{
		super(name);
		setMaxStackSize(1);

		addPropertyOverride(new ResourceLocation(Minestuck.MODID, "assigned"), ((stack, worldIn, entityIn) -> hasSpecibus(stack) ? getStrifeSpecibus(stack).isAssigned() ? 1 : 0.5f : 0));
	}

	public static StrifeSpecibus getStrifeSpecibus(ItemStack stack)
	{
		if (hasSpecibus(stack))
		{
			StrifeSpecibus specibus = new StrifeSpecibus(stack.getTagCompound().getCompoundTag("StrifeSpecibus"));
			if (stack.hasDisplayName())
				specibus.setCustomName(stack.getDisplayName());
			return specibus;
		}
		return null;
	}

	public static boolean hasSpecibus(ItemStack stack)
	{
		return stack.hasTagCompound() && stack.getTagCompound().hasKey("StrifeSpecibus") && !stack.getTagCompound().getCompoundTag("StrifeSpecibus").hasNoTags();
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		super.addInformation(stack, worldIn, tooltip, flagIn);

		if (hasSpecibus(stack))
		{
			StrifeSpecibus specibus = getStrifeSpecibus(stack);

			if (specibus.isAssigned())
			{
				tooltip.add("(" + specibus.getKindAbstratus().getDisplayName() + ")");
				int size = specibus.getContents().size(), remaining = size;
				for (int i = 0; i < Math.min(size, 5); i++)
				{
					ItemStack itemstack = specibus.getContents().get(i);
					tooltip.add(String.format("%s x%d", itemstack.getDisplayName(), itemstack.getCount()));
					remaining--;
				}

				if (remaining > 0)
					tooltip.add(String.format(TextFormatting.ITALIC + I18n.translateToLocal("container.shulkerBox.more"), remaining));

			}
			else tooltip.add("(invalid data)");
		}
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
	{
		ItemStack stack = playerIn.getHeldItem(handIn);

		if (StrifePortfolioHandler.isFull(playerIn))
		{
			if (!worldIn.isRemote)
				playerIn.sendStatusMessage(new TextComponentTranslation("status.strife.portfolioFull"), true);
			return ActionResult.newResult(EnumActionResult.FAIL, stack);
		}

		if (hasSpecibus(stack))
		{
			StrifeSpecibus specibus = getStrifeSpecibus(stack);
			if (specibus.isAssigned())
				StrifePortfolioHandler.assignStrife(playerIn, handIn);
			else injectStrifeSpecibus(StrifeSpecibus.empty(), stack);
		}
		else
			playerIn.openGui(Minestuck.instance, MinestuckGuiHandler.GuiId.STRIFE_CARD.ordinal(), worldIn, (int) playerIn.posX, (int) playerIn.posY, (int) playerIn.posZ);

		return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
	}

	public static ItemStack injectStrifeSpecibus(StrifeSpecibus specibus, ItemStack stack)
	{
		if (specibus == null)
			return stack;
		if (!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		stack.getTagCompound().setTag("StrifeSpecibus", specibus.writeToNBT(new NBTTagCompound()));
		return stack;
	}
}
