package com.mraof.minestuck.client.gui.captchalogue.sylladex;

import com.mraof.minestuck.captchalogue.captchalogueable.ICaptchalogueable;
import com.mraof.minestuck.captchalogue.modus.Modus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;

import static com.mraof.minestuck.client.gui.captchalogue.sylladex.MultiSylladexGuiContainerCyclone.QUADRANT;

@SideOnly(Side.CLIENT)
public class CardGuiContainer extends SylladexGuiContainer
{
	public static final int CARD_WIDTH = 21, CARD_HEIGHT = 26;

	private final ICaptchalogueable object;
	private final CardTextureIndex[] textureIndices;

	public CardGuiContainer(CardTextureIndex[] textureIndices, ICaptchalogueable object)
	{
		this.textureIndices = textureIndices;
		this.object = object;
		this.width = CARD_WIDTH;
		this.height = CARD_HEIGHT;
	}

	@Override
	public void update(int depth, float directionAngle, float partialTicks) { }

	public static void drawCard(GuiSylladex gui, CardTextureIndex[] textureIndices)
	{
		for (int i = 0; i < textureIndices.length; i++)
		{
			Minecraft.getMinecraft().getTextureManager().bindTexture(textureIndices[i].texture);
			gui.drawTexturedModalRect(i * CARD_WIDTH / textureIndices.length, 0,
									  textureIndices[i].index % 12 * CARD_WIDTH + i * CARD_WIDTH / textureIndices.length, textureIndices[i].index / 12 * CARD_HEIGHT,
									  CARD_WIDTH / textureIndices.length, CARD_HEIGHT);
		}
	}

	@Override
	public void draw(GuiSylladex gui, float mouseX, float mouseY, float partialTicks, boolean fetchable)
	{
		GlStateManager.pushMatrix();
		GlStateManager.translate(x, y, 0);

		if (fetchable)
			GlStateManager.color(1f, 1f, 1f);
		else
			GlStateManager.color(0.8f, 0.8f, 0.8f);

		drawCard(gui, textureIndices);

		if (object != null)
			object.draw(gui, this, mouseX, mouseY, partialTicks);

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

	@Override
	protected float getMaxVertexDistance(float angle)
	{
		angle = MathHelper.positiveModulo(angle, QUADRANT * 4);
		if (angle < QUADRANT)
			return height * MathHelper.cos(angle);
		else if (angle < QUADRANT * 2)
			return 0;
		else if (angle < QUADRANT * 3)
			return -width * MathHelper.sin(angle);
		else
			return height * MathHelper.cos(angle) - width * MathHelper.sin(angle);
	}

	@Override
	protected float getMinVertexDistance(float angle)
	{
		angle = MathHelper.positiveModulo(angle, QUADRANT * 4);
		if (angle < QUADRANT)
			return -width * MathHelper.sin(angle);
		else if (angle < QUADRANT * 2)
			return height * MathHelper.cos(angle) - width * MathHelper.sin(angle);
		else if (angle < QUADRANT * 3)
			return height * MathHelper.cos(angle);
		else
			return 0;
	}

	public CardTextureIndex[] getTextureIndices()
	{
		return textureIndices;
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
