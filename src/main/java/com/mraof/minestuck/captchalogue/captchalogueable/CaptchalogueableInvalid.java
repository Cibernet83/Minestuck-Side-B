package com.mraof.minestuck.captchalogue.captchalogueable;

import com.mraof.minestuck.client.gui.captchalogue.sylladex.GuiSylladex;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class CaptchalogueableInvalid implements ICaptchalogueable {
	public CaptchalogueableInvalid() { }

	public CaptchalogueableInvalid(NBTTagCompound nbt) { }

	@Override
	public void grow(ICaptchalogueable other) { }

	@Override
	public boolean isEmpty() {
		return true;
	}

	@Override
	public boolean isCompatibleWith(ICaptchalogueable other) {
		return false;
	}

	@Override
	public void fetch(EntityPlayer player) { }

	@Override
	public void eject(EntityPlayer player) { }

	@Override
	public ItemStack captchalogueIntoCardItem() {
		return ItemStack.EMPTY;
	}

	@Override
	public String getName()
	{
		return "INVALID";
	}

	@Override
	public NBTTagCompound writeData() {
		return new NBTTagCompound();
	}

	@Override
	public void draw(GuiSylladex gui, float mouseX, float mouseY, float partialTicks)
	{
		gui.drawTexturedModalRect(0, 0, 0, 0, 16, 16);
	}

	@Override
	public String getDisplayName()
	{
		return "INVALID";
	}

	@Override
	public ITextComponent getTextComponent() {
		return new TextComponentString("INVALID");
	}

	@Override
	public void renderTooltip(GuiSylladex gui, int x, int y) { }

	@Override
	public String getTextureKey() {
		return "invalid";
	}

	@Override
	public void drop(World world, double posX, double posY, double posZ) { }
}
