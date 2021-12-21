package com.mraof.minestuck.client.gui.captchalogue;

import com.mraof.minestuck.inventory.captchalouge.ICaptchalogueable;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;

@SideOnly(Side.CLIENT)
public class CardGuiContainer extends ModusGuiContainer
{
	private final ICaptchalogueable object;

	public CardGuiContainer(ICaptchalogueable object)
	{
		super(null);
		this.object = object;
		width = 21;
		height = 26;
	}

	@Override
	public void draw(SylladexGuiHandler gui)
	{
		Minecraft.getMinecraft().getTextureManager().bindTexture(SylladexGuiHandler.CARD_TEXTURE);
		gui.drawTexturedModalRect(x, y,	0, 0, (int) getWidth(), (int) getHeight());

		if (object != null)
			object.draw(gui);
	}

	@Override
	public ArrayList<Integer> hit(float x, float y)
	{
		return new ArrayList<>();
	}
}
