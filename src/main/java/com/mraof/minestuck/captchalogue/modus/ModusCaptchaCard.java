package com.mraof.minestuck.captchalogue.modus;

import com.mraof.minestuck.client.gui.captchalogue.modus.GuiModusSettings;
import com.mraof.minestuck.client.gui.captchalogue.sylladex.CardGuiContainer;
import com.mraof.minestuck.client.gui.captchalogue.sylladex.GuiSylladex;
import com.mraof.minestuck.item.ItemModus;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
	@SideOnly(Side.CLIENT)
	public CardGuiContainer.CardTextureIndex getNewCardTextureIndex(NBTTagCompound settings)
	{
		return new CardGuiContainer.CardTextureIndex(this, GuiSylladex.CARD_TEXTURE, 37);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiModusSettings getSettingsGui(ItemStack modusStack)
	{
		return null;
	}
}
