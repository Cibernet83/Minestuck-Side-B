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

	private int width;
	private int height;

	public MinestuckFontRenderer(GameSettings gameSettingsIn, ResourceLocation location, TextureManager textureManagerIn,
								 boolean unicode, int charWidth, int charHeight)
	{
		super(gameSettingsIn, location, textureManagerIn, unicode);
		width = charWidth;
		height = charHeight;
	}

	public MinestuckFontRenderer(GameSettings gameSettingsIn, ResourceLocation location, TextureManager textureManagerIn,
								 boolean unicode)
	{
		this(gameSettingsIn, location, textureManagerIn, unicode, 8, 8);
	}

	public static void registerFonts()
	{
		Minecraft mc = Minecraft.getMinecraft();
		lucidaConsoleSmall = new MinestuckFontRenderer(mc.gameSettings, new ResourceLocation(Minestuck.MODID, "textures/font/lucida_console_small.png"), mc.renderEngine, true, 4, 8);
	}

	@Override
	protected float renderUnicodeChar(char ch, boolean italic)
	{
		int i = glyphWidth[ch] & 255;

		if (i == 0)
			return 0.0F;
		else
		{
			int height = this.height * 2;
			int width = this.width * 2;
			bindTexture(locationFontTexture);
			int k = i >>> 4;
			int l = i & 15;
			float f1 = l + 1;
			float f2 = ch % 16 * width + k;
			float f3 = (ch & 255) / height * height;
			float f4 = f1 - k - 0.02F;
			float f5 = italic ? 1.0F : 0.0F;
			GlStateManager.glBegin(5);
			GlStateManager.glTexCoord2f(f2 / 256.0F, f3 / 256.0F);
			GlStateManager.glVertex3f(posX + f5, posY, 0.0F);
			GlStateManager.glTexCoord2f(f2 / 256.0F, (f3 + 15.98F) / 256.0F);
			GlStateManager.glVertex3f(posX - f5, posY + 7.99F, 0.0F);
			GlStateManager.glTexCoord2f((f2 + f4) / 256.0F, f3 / 256.0F);
			GlStateManager.glVertex3f(posX + f4 / 2.0F + f5, posY, 0.0F);
			GlStateManager.glTexCoord2f((f2 + f4) / 256.0F, (f3 + 15.98F) / 256.0F);
			GlStateManager.glVertex3f(posX + f4 / 2.0F - f5, posY + 7.99F, 0.0F);
			GlStateManager.glEnd();
			return (f1 - k) / 2.0F + 1.0F;
		}
	}
}
