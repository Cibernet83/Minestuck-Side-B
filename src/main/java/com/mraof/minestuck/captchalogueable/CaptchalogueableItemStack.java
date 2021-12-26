package com.mraof.minestuck.captchalogueable;

import com.mraof.minestuck.client.gui.captchalogue.SylladexGuiHandler;
import com.mraof.minestuck.sylladex.BottomSylladex;
import com.mraof.minestuck.util.AlchemyUtils;
import com.mraof.minestuck.util.SylladexUtils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

public class CaptchalogueableItemStack implements ICaptchalogueable
{
	private ItemStack stack; // Should be final but readFromNBT

	public CaptchalogueableItemStack(@Nonnull ItemStack stack)
	{
		this.stack = stack;
	}

	public CaptchalogueableItemStack() { } // Needed for instantiation

	@Override
	public void grow(ICaptchalogueable other)
	{
		if (!isCompatibleWith(other))
			return;
		SylladexUtils.moveItemStack((ItemStack)other.getObject(), stack);
	}

	@Override
	public boolean isEmpty()
	{
		return stack.isEmpty();
	}

	@Override
	public boolean isCompatibleWith(ICaptchalogueable other)
	{
		if (!(other.getObject() instanceof ItemStack))
			return false;
		ItemStack otherStack = (ItemStack)other.getObject();
		return SylladexUtils.areItemStacksCompatible(stack, otherStack);
	}

	@Override
	public Object getObject()
	{
		return stack;
	}

	@Override
	public void eject(BottomSylladex fromSylladex, int cardIndex, EntityPlayer player)
	{
		if(fromSylladex != null && AlchemyUtils.isAppendable(stack))
			while (!stack.isEmpty()) // FIXME: Make these move up a stack when popping over the 256 card limit
			{
				(fromSylladex.autoBalanceNewCards ? SylladexUtils.getSylladex(player) : fromSylladex).addCard(AlchemyUtils.getCardContents(stack), player);
				stack.shrink(1);
			}
		if(!stack.isEmpty())
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
	public void readFromNBT(NBTTagCompound nbt)
	{
		stack = new ItemStack(nbt);
	}

	@Override
	public void draw(SylladexGuiHandler gui)
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

	@SideOnly(Side.CLIENT)
	public String getTextureKey()
	{
		return "item";
	}

	@Override
	public String getDisplayName() {
		return (stack.getCount() <= 1 ? "" : (stack.getCount() + "x ")) + stack.getDisplayName();
	}
}
