package com.mraof.minestuck.captchalogue.modus;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.client.gui.captchalogue.sylladex.CardGuiContainer;
import com.mraof.minestuck.client.gui.captchalogue.modus.GuiModusSettings;
import com.mraof.minestuck.client.gui.captchalogue.sylladex.GuiSylladex;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModusArray extends Modus
{
	public ModusArray(String name)
	{
		super(name);
	}

	@SideOnly(Side.CLIENT)
	public CardGuiContainer.CardTextureIndex getNewCardTextureIndex(NBTTagCompound settings)
	{
		return new CardGuiContainer.CardTextureIndex(GuiSylladex.CARD_TEXTURE, 42);
	}

	@SideOnly(Side.CLIENT)
	public GuiModusSettings getSettingsGui(ItemStack modusStack)
	{
		return new GuiModusSettings(modusStack, new ResourceLocation(Minestuck.MODID, "textures/gui/fetch_modus/array_modus.png"));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getPrimaryColor()
	{
		return 0x06B6FF;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getDarkerColor()
	{
		return 0x1093D8;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getTextColor()
	{
		return 0xFF9CAA;
	}
}
