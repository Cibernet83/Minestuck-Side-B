package com.mraof.minestuck.captchalogue.captchalogueable;

import com.mraof.minestuck.captchalogue.sylladex.BottomSylladex;
import com.mraof.minestuck.client.gui.captchalogue.sylladex.GuiSylladex;
import com.mraof.minestuck.item.MinestuckItems;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class CaptchalogueableGhost implements ICaptchalogueable
{
	public ICaptchalogueable parent;

	public CaptchalogueableGhost(ICaptchalogueable parent)
	{
		this.parent = parent;
	}

	public CaptchalogueableGhost(NBTTagCompound nbt)
	{
		this(ICaptchalogueable.readFromNBT(nbt.getCompoundTag("Parent")));
	}

	@Override
	public ICaptchalogueable getAlchemyComponent() {
		return parent;
	}

	@Override
	public void grow(ICaptchalogueable other)
	{

	}

	@Override
	public boolean isEmpty()
	{
		return true;
	}

	@Override
	public boolean isCompatibleWith(ICaptchalogueable other)
	{
		return false;
	}

	@Override
	public void fetch(EntityPlayer player)
	{

	}

	@Override
	public void eject(BottomSylladex fromSylladex, EntityPlayer player)
	{

	}

	@Override
	public void eject(EntityPlayer player) {


	}

	@Override
	public ItemStack captchalogueIntoCardItem()
	{
		return new ItemStack(MinestuckItems.captchaCard);
	}

	@Override
	public NBTTagCompound writeData()
	{
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setTag("Parent", ICaptchalogueable.writeToNBT(parent));
		return nbt;
	}

	@Override
	public void draw(GuiSylladex gui, int mouseX, int mouseY)
	{
		//TODO grayscale would be neat
		parent.draw(gui, 20, 20);
	}

	@Override
	public String getDisplayName()
	{
		return I18n.format("captchalogueable.ghost", parent.getDisplayName());
	}

	@Override
	public ITextComponent getTextComponent()
	{
		ITextComponent component = parent.getTextComponent();
		component.getStyle().setColor(TextFormatting.GRAY);
		return component;
	}

	@Override
	public void renderTooltip(GuiSylladex gui, int x, int y)
	{
		parent.renderTooltip(gui, x, y);
	}

	@Override
	public String getTextureKey()
	{
		return "ghost";
	}

	@Override
	public void drop(World world, double posX, double posY, double posZ)
	{

	}
}
