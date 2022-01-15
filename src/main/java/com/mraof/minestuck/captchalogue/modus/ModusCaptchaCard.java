package com.mraof.minestuck.captchalogue.modus;

import com.mraof.minestuck.client.gui.captchalogue.modus.GuiModusSettings;
import com.mraof.minestuck.client.gui.captchalogue.sylladex.CardGuiContainer;
import com.mraof.minestuck.client.gui.captchalogue.sylladex.GuiSylladex;
import com.mraof.minestuck.item.ItemModus;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ModusCaptchaCard extends Modus
{
	public ModusCaptchaCard(String name)
	{
		super(name);
	}

	@Override
	public ItemModus makeItem()
	{
		return null;
	}

	@Override
	public CardGuiContainer.CardTextureIndex getNewCardTextureIndex(NBTTagCompound settings)
	{
		return new CardGuiContainer.CardTextureIndex(this, GuiSylladex.CARD_TEXTURE, 37);
	}

	@Override
	public int getPrimaryColor()
	{
		return 0xFF0000;
	}

	@Override
	public GuiModusSettings getSettingsGui(ItemStack modusStack)
	{
		return null;
	}

	@Override
	public int getTextColor()
	{
		return 0x000000;
	}
}
