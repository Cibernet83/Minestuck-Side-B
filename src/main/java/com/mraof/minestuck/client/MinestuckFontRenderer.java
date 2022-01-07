package com.mraof.minestuck.client;

import com.mraof.minestuck.Minestuck;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.ResourceLocation;

public class MinestuckFontRenderer extends FontRenderer
{
	public static MinestuckFontRenderer lucidaConsoleSmall;

	private int width, height;
	private float textureSize;

	public MinestuckFontRenderer(GameSettings gameSettingsIn, ResourceLocation location, TextureManager textureManagerIn)
	{
		this(gameSettingsIn, location, textureManagerIn, 8, 8);
	}

	public MinestuckFontRenderer(GameSettings gameSettingsIn, ResourceLocation location, TextureManager textureManagerIn, int charWidth, int charHeight)
	{
		this(gameSettingsIn, location, textureManagerIn, charWidth, charHeight, 128);
	}

	public MinestuckFontRenderer(GameSettings gameSettingsIn, ResourceLocation location, TextureManager textureManagerIn, int charWidth, int charHeight, int textureSize)
	{
		super(gameSettingsIn, location, textureManagerIn, false);
		this.width = charWidth;
		this.height = charHeight;
		this.textureSize = textureSize;
	}

	public static void registerFonts()
	{
		Minecraft mc = Minecraft.getMinecraft();
		lucidaConsoleSmall = new MinestuckFontRenderer(mc.gameSettings, new ResourceLocation(Minestuck.MODID, "textures/font/lucida_console_small.png"), mc.renderEngine, 4, 7);
	}

	@Override
	protected float renderDefaultChar(int ch, boolean italic)
	{
		float texX = ch % 16 * width / textureSize;
		float texY = ch / 16 * height / textureSize;
		float texWidth = (width - 0.01F) / textureSize;
		float texHeight = (height - 0.01F) / textureSize;
		float italicShift = italic ? 1 : 0;

		bindTexture(locationFontTexture);
		GlStateManager.glBegin(5);

		GlStateManager.glTexCoord2f(texX, texY);
		GlStateManager.glVertex3f(posX + italicShift, posY, 0.0F);

		GlStateManager.glTexCoord2f(texX, texY + texHeight);
		GlStateManager.glVertex3f(posX - italicShift, posY + height, 0.0F);

		GlStateManager.glTexCoord2f(texX + texWidth, texY);
		GlStateManager.glVertex3f(posX + width + italicShift, posY, 0.0F);

		GlStateManager.glTexCoord2f(texX + texWidth, texY + texHeight);
		GlStateManager.glVertex3f(posX + width - italicShift, posY + height, 0.0F);

		GlStateManager.glEnd();

		return width;
	}

	@Override
	public int getCharWidth(char character)
	{
		if (character == 160) return 4; // forge: display nbsp as space. MC-2595
		if (character == 167) return -1;
		else if (character == ' ') return 4;
		else return width;
	}

	public int getCharWidth()
	{
		return width;
	}

	public int getCharHeight()
	{
		return height;
	}
}
