package com.mraof.minestuck.captchalogue.captchalogueable;

import com.mraof.minestuck.captchalogue.sylladex.BottomSylladex;
import com.mraof.minestuck.client.gui.captchalogue.sylladex.CardGuiContainer;
import com.mraof.minestuck.client.gui.captchalogue.sylladex.GuiSylladex;
import com.mraof.minestuck.item.MinestuckItems;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CaptchalogueableGhost implements ICaptchalogueable
{
	public ICaptchalogueable parent;

	public CaptchalogueableGhost(NBTTagCompound nbt)
	{
		this(ICaptchalogueable.readFromNBT(nbt.getCompoundTag("Parent")));
	}

	public CaptchalogueableGhost(ICaptchalogueable parent)
	{
		this.parent = parent;
	}

	@Override
	public NBTTagCompound writeData()
	{
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setTag("Parent", ICaptchalogueable.writeToNBT(parent));
		return nbt;
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
	public void eject(BottomSylladex fromSylladex, int index, EntityPlayerMP player) { }

	@Override
	public void eject(EntityPlayerMP player) { }

	@Override
	public void drop(World world, double posX, double posY, double posZ) { }

	@Override
	public ItemStack captchalogueIntoCardItem()
	{
		return new ItemStack(MinestuckItems.captchaCard);
	}

	@Override
	public ICaptchalogueable getAlchemyComponent()
	{
		return parent;
	}

	@Override
	public String getName()
	{
		return "ghost of " + parent.getName();
	}

	@Override
	public String getDisplayName()
	{
		return I18n.translateToLocalFormatted("captchalogueable.ghost", parent.getDisplayName());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void draw(GuiSylladex gui, CardGuiContainer card, float mouseX, float mouseY, float partialTicks)
	{
		//TODO grayscale would be neat
		parent.draw(gui, card, 20, 20, partialTicks);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ITextComponent getTextComponent()
	{
		ITextComponent component = parent.getTextComponent();
		component.getStyle().setColor(TextFormatting.GRAY);
		return component;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void renderTooltip(GuiSylladex gui, int x, int y)
	{
		parent.renderTooltip(gui, x, y);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getTextureKey()
	{
		return "ghost";
	}
}
