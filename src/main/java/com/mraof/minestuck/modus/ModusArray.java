package com.mraof.minestuck.modus;

import com.mraof.minestuck.client.gui.captchalogue.CardGuiContainer;
import com.mraof.minestuck.client.gui.captchalogue.SylladexGuiHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModusArray extends Modus
{
	public ModusArray(String name)
	{
		super(name);
	}

	@SideOnly(Side.CLIENT)
	public CardGuiContainer.CardTextureIndex getCardTextureIndex()
	{
		if (cardTextureIndex == null)
			cardTextureIndex = new CardGuiContainer.CardTextureIndex(SylladexGuiHandler.CARD_TEXTURE, 42);
		return cardTextureIndex;
	}
}
