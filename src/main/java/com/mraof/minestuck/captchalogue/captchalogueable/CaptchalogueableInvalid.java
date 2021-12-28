package com.mraof.minestuck.captchalogue.captchalogueable;

import com.mraof.minestuck.captchalogue.sylladex.BottomSylladex;
import com.mraof.minestuck.client.gui.captchalogue.sylladex.GuiSylladex;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class CaptchalogueableInvalid implements ICaptchalogueable {
	@Override
	public void grow(ICaptchalogueable other) {

	}

	public CaptchalogueableInvalid()
	{

	}

	public CaptchalogueableInvalid(NBTTagCompound nbt)
	{

	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public boolean isCompatibleWith(ICaptchalogueable other) {
		return false;
	}

	@Override
	public void fetch(EntityPlayer player, boolean shrinkHand) {

	}

	@Override
	public void eject(BottomSylladex fromSylladex, EntityPlayer player) {

	}

	@Override
	public void eject(EntityPlayer player) {

	}

	@Override
	public ItemStack captchalogueIntoCardItem() {
		return ItemStack.EMPTY;
	}

	@Override
	public NBTTagCompound writeData() {
		return new NBTTagCompound();
	}

	@Override
	public void draw(GuiSylladex gui)
	{
		gui.drawTexturedModalRect(0, 0, 0, 0, 16, 16);
	}

	@Override
	public String getDisplayName() {
		return "INVALID";
	}

	@Override
	public ITextComponent getTextComponent() {
		return new TextComponentString("INVALID");
	}

	@Override
	public void renderTooltip(GuiSylladex gui, int x, int y) {

	}

	@Override
	public String getTextureKey() {
		return "invalid";
	}

	@Override
	public void drop(World world, double posX, double posY, double posZ) {

	}
}
