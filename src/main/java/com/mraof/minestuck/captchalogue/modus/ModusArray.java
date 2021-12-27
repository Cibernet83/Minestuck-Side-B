package com.mraof.minestuck.captchalogue.modus;

import com.mraof.minestuck.client.gui.captchalogue.CardGuiContainer;
import com.mraof.minestuck.client.gui.captchalogue.SylladexGuiHandler;
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
			cardTextureIndex = new CardGuiContainer.CardTextureIndex(SylladexGuiHandler.CARD_TEXTURE, 42);
		return cardTextureIndex;
	}
}
