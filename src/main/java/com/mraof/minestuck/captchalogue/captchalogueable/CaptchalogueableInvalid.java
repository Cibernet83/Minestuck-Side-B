package com.mraof.minestuck.captchalogue.captchalogueable;

import com.mraof.minestuck.client.gui.captchalogue.sylladex.CardGuiContainer;
import com.mraof.minestuck.client.gui.captchalogue.sylladex.GuiSylladex;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class CaptchalogueableInvalid implements ICaptchalogueable
{
	public CaptchalogueableInvalid() { }

	public CaptchalogueableInvalid(NBTTagCompound nbt) { }

	@Override
	public NBTTagCompound writeData()
	{
		return new NBTTagCompound();
	}

	@Override
	public void grow(ICaptchalogueable other) { }

	@Override
	public boolean isEmpty()
	{
		return true;
	}

	@Override
	public void empty() { }

	@Override
	public boolean isCompatibleWith(ICaptchalogueable other)
	{
		return false;
	}

	@Override
	public boolean isLooselyCompatibleWith(ICaptchalogueable other)
	{
		return false;
	}

	@Override
	public void fetch(EntityPlayerMP player) { }

	@Override
	public void eject(EntityPlayerMP player) { }

	@Override
	public void drop(World world, double posX, double posY, double posZ) { }

	@Override
	public ItemStack captchalogueIntoCardItem()
	{
		return ItemStack.EMPTY;
	}

	@Override
	public String getName()
	{
		return "INVALID";
	}

	@Override
	public String getDisplayName()
	{
		return "INVALID";
	}

	@Override
	public void draw(GuiSylladex gui, CardGuiContainer card, float mouseX, float mouseY, float partialTicks)
	{
		gui.drawTexturedModalRect(0, 0, 0, 0, 16, 16);
	}

	@Override
	public ITextComponent getTextComponent()
	{
		return new TextComponentString("INVALID");
	}

	@Override
	public void renderTooltip(GuiSylladex gui, int x, int y) { }

	@Override
	public String getTextureKey()
	{
		return "invalid";
	}
}
