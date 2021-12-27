package com.mraof.minestuck.client.gui.captchalogue.modus;

import com.mraof.minestuck.client.MinestuckFontRenderer;
import com.mraof.minestuck.util.MinestuckUtils;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;

public abstract class ModusGuiButton
{
	private final ResourceLocation texture;
	private final int x, y, textureX, textureY, width, height;
	private final String text;
	private final int textColor;
	private boolean down;

	public ModusGuiButton(ResourceLocation texture, int x, int y, int textureX, int textureY, int width, int height, String text, int textColor, boolean down)
	{
		this.texture = texture;
		this.x = x;
		this.y = y;
		this.textureX = textureX;
		this.textureY = textureY;
		this.width = width;
		this.height = height;
		this.text = text;
		this.textColor = textColor;
		this.down = down;
	}

	public ModusGuiButton(ResourceLocation texture, int x, int y, int textureX, int textureY, int width, int height, String text, int textColor)
	{
		this(texture, x, y, textureX, textureY, width, height, text, textColor, false);
	}

	public void draw(GuiModusSettings gui, int mouseX, int mouseY, float partialTicks)
	{
		boolean hit = down || hit(mouseX, mouseY);
		MinestuckFontRenderer fontRenderer = MinestuckFontRenderer.lucidaConsoleSmall;

		gui.mc.getTextureManager().bindTexture(texture);
		GlStateManager.color(1f, 1f,1f, 1f);
		gui.drawTexturedModalRect(x, y, textureX, hit ? textureY + height : textureY, width, height);

		fontRenderer.drawString(text, x + (width - fontRenderer.getStringWidth(text)) / 2, y + (height - fontRenderer.getCharHeight()) / 2, hit ? MinestuckUtils.multiply(textColor, 0.85f) : textColor);
	}

	public boolean hit(int mouseX, int mouseY)
	{
		return MinestuckUtils.isPointInRegion(x, y, width, height, mouseX, mouseY);
	}

	public abstract void click();

	public void onClick(GuiModusSettings gui)
	{
		if (!down)
		{
			gui.mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
			click();
		}
	}

	public boolean getDown()
	{
		return down;
	}

	public void setDown(boolean down)
	{
		this.down = down;
	}
}
