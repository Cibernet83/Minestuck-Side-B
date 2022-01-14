package com.mraof.minestuck.client.gui.captchalogue.sylladex;

import com.mraof.minestuck.captchalogue.captchalogueable.ICaptchalogueable;
import com.mraof.minestuck.captchalogue.modus.Modus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
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
		this.width = 21;
		this.height = 26;
	}

	@Override
	public void update(int depth, float partialTicks) { }

	@Override
	public void draw(GuiSylladex gui, float mouseX, float mouseY, float partialTicks, boolean fetchable)
	{
		int width = (int) this.width;
		int height = (int) this.height;

		GlStateManager.pushMatrix();
		GlStateManager.translate(x, y, 0);

		if (fetchable)
			GlStateManager.color(1f, 1f, 1f);
		else
			GlStateManager.color(0.8f, 0.8f, 0.8f);

		for (int i = 0; i < textureIndices.length; i++)
		{
			Minecraft.getMinecraft().getTextureManager().bindTexture(textureIndices[i].texture);
			gui.drawTexturedModalRect(i * width / textureIndices.length, 0,
					textureIndices[i].index % 12 * width + i * width / textureIndices.length, textureIndices[i].index / 12 * height,
					width / textureIndices.length, height);
		}

		if (object != null)
			object.draw(gui, mouseX, mouseY, partialTicks);

		GlStateManager.color(1f, 1f, 1f);
		GlStateManager.popMatrix();
	}

	@Override
	public void drawPeek(int[] slots, int index, GuiSylladex gui, float mouseX, float mouseY, float partialTicks, boolean fetchable)
	{
		draw(gui, mouseX, mouseY, partialTicks, fetchable);
	}

	@Override
	public ArrayList<Integer> hit(float x, float y)
	{
		x -= this.x;
		y -= this.y;

		if (x < 0 || x > width || y < 0 || y > height)
			return null;
		else
			return new ArrayList<>();
	}

	@Override
	public boolean isHitting(int[] slots, int index, float x, float y)
	{
		x -= this.x;
		y -= this.y;

		return !(x < 0 || x > width || y < 0 || y > height);
	}

	@Override
	public boolean isEmpty()
	{
		return false;
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
