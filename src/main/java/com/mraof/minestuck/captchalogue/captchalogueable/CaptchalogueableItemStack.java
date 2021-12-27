package com.mraof.minestuck.captchalogue.captchalogueable;

import com.mraof.minestuck.captchalogue.sylladex.BottomSylladex;
import com.mraof.minestuck.client.gui.captchalogue.sylladex.GuiSylladex;
import com.mraof.minestuck.util.AlchemyUtils;
import com.mraof.minestuck.util.SylladexUtils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

public class CaptchalogueableItemStack implements ICaptchalogueable
{
	private final ItemStack stack;

	public CaptchalogueableItemStack(@Nonnull ItemStack stack)
	{
		this.stack = stack;
	}

	public CaptchalogueableItemStack(NBTTagCompound nbt)
	{
		stack = new ItemStack(nbt);
	}

	public ItemStack getStack()
	{
		return stack;
	}

	@Override
	public void grow(ICaptchalogueable other)
	{
		if (!isCompatibleWith(other))
			return;
		SylladexUtils.moveItemStack(((CaptchalogueableItemStack)other).getStack(), stack);
	}

	@Override
	public boolean isEmpty()
	{
		return stack.isEmpty();
	}

	@Override
	public boolean isCompatibleWith(ICaptchalogueable other)
	{
		if (!(other instanceof CaptchalogueableItemStack))
			return false;
		ItemStack otherStack = ((CaptchalogueableItemStack)other).getStack();
		return SylladexUtils.areItemStacksCompatible(stack, otherStack);
	}

	@Override
	public void fetch(EntityPlayer player)
	{
		ItemStack handStack = player.getHeldItemMainhand();
		if(handStack.isEmpty())
			player.setHeldItem(EnumHand.MAIN_HAND, stack);
		else if (!player.addItemStackToInventory(stack))
			SylladexUtils.launchItem(player, stack);
	}

	@Override
	public void eject(BottomSylladex fromSylladex, EntityPlayer player)
	{
		if(fromSylladex != null && AlchemyUtils.isAppendable(stack))
			while (!stack.isEmpty()) // FIXME: Make these move up a stack when popping over the 256 card limit
			{
				(fromSylladex.autoBalanceNewCards ? SylladexUtils.getSylladex(player) : fromSylladex).addCard(AlchemyUtils.getCardContents(stack), player);
				stack.shrink(1);
			}
		if(!stack.isEmpty())
			eject(player);
	}

	@Override
	public void eject(EntityPlayer player)
	{
		SylladexUtils.launchItem(player, stack);
	}

	@Override
	public ItemStack captchalogueIntoCardItem()
	{
		return AlchemyUtils.createCard(stack, false);
	}

	@Override
	public NBTTagCompound writeToNBT()
	{
		return stack.writeToNBT(new NBTTagCompound());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void draw(GuiSylladex gui)
	{
		if(!stack.isEmpty())
		{
			int x = 2;
			int y = 7;

			RenderHelper.enableGUIStandardItemLighting();
			GlStateManager.enableRescaleNormal();
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
			gui.itemRender.renderItemAndEffectIntoGUI(stack, x, y);
			gui.itemRender.renderItemOverlayIntoGUI(gui.mc.fontRenderer, stack, x, y, stack.getCount() == 1 ? "" : String.valueOf(stack.getCount()));
			GlStateManager.disableDepth();
			RenderHelper.disableStandardItemLighting();
			GlStateManager.color(1F, 1F, 1F, 1F);
		}
	}

	@Override
	public String getDisplayName() {
		return (stack.getCount() <= 1 ? "" : (stack.getCount() + "x ")) + stack.getDisplayName();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void renderTooltip(GuiSylladex gui, int x, int y)
	{
		gui.renderToolTip(stack, x, y);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getTextureKey()
	{
		return "item";
	}
}
