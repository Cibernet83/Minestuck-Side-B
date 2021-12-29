package com.mraof.minestuck.client.gui.captchalogue.sylladex;

import com.mraof.minestuck.captchalogue.captchalogueable.ICaptchalogueable;
import com.mraof.minestuck.captchalogue.modus.Modus;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;

@SideOnly(Side.CLIENT)
public class CardGuiContainer extends SylladexGuiContainer
{
	private final ICaptchalogueable object;
	private final CardTextureIndex[] textureIndices;

	public CardGuiContainer(CardTextureIndex[] textureIndices, ICaptchalogueable object)
	{
		this.textureIndices = textureIndices;
		this.object = object;
		right = 21;
		bottom = 26;
	}

	@Override
	public void draw(GuiSylladex gui) // TODO: Darken unusable cards
	{
		int width = (int) getWidth();
		int height = (int) getHeight();

		for (int i = 0; i < textureIndices.length; i++)
		{
			Minecraft.getMinecraft().getTextureManager().bindTexture(textureIndices[i].texture);
			gui.drawTexturedModalRect(x  + i * width / textureIndices.length, y,
					textureIndices[i].index % 12 * width + i * width / textureIndices.length, textureIndices[i].index / 12 * height,
					width / textureIndices.length, height);
		}

		if (object != null)
			object.draw(gui);
	}

	@Override
	public ArrayList<Integer> hit(float x, float y)
	{
		x -= this.x;
		y -= this.y;

		if (x < left || x > right || y < top || y > bottom)
			return null;
		else
			return new ArrayList<>();
	}

	public static class CardTextureIndex
	{
		public final Modus modus;
		public final ResourceLocation texture;
		public final int index;
		public CardTextureIndex(Modus modus, ResourceLocation texture, int index)
		{
			this.modus = modus;
			this.texture = texture;
			this.index = index;
		}
	}
}
