package com.mraof.minestuck.client.gui.captchalogue;

import net.minecraft.client.Minecraft;

import java.util.ArrayList;

public class CardGuiContainer extends ModusGuiContainer
{
	public CardGuiContainer()
	{
		super(null);
	}

	@Override
	public ArrayList<ModusGuiContainer> generateSubContainers()
	{
		containers.clear();
		width = 21;
		height = 26;
		return containers;
	}

	@Override
	public void draw(SylladexGuiHandler gui)
	{
		Minecraft.getMinecraft().getTextureManager().bindTexture(SylladexGuiHandler.CARD_TEXTURE);
		gui.drawTexturedModalRect(x, y,	0, 0, (int) getWidth(), (int) getHeight());
	}
}
