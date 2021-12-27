package com.mraof.minestuck.captchalogue.modus;

import com.mraof.minestuck.client.gui.captchalogue.CardGuiContainer;
import com.mraof.minestuck.client.gui.captchalogue.GuiSylladex;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModusArray extends Modus
{
	public ModusArray(String name)
	{
		super(name);
	}

	@SideOnly(Side.CLIENT)
	public CardGuiContainer.CardTextureIndex getCardTextureIndex(NBTTagCompound settings)
	{
		if (cardTextureIndex == null)
			cardTextureIndex = new CardGuiContainer.CardTextureIndex(GuiSylladex.CARD_TEXTURE, 42);
		return cardTextureIndex;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getPrimaryColor()
	{
		return 0x06B6FF;
	}

	/*@Override
	@SideOnly(Side.CLIENT)
	public int getDarkerColor()
	{
		return 0x1093D8;
	}*/

	@Override
	@SideOnly(Side.CLIENT)
	public int getTextColor()
	{
		return 0xFF9CAA;
	}
}
