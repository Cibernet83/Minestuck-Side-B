package com.mraof.minestuck.client.gui;

import com.mraof.minestuck.alchemy.Grist;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.config.GuiButtonExt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class GuiGristSelector extends GuiScreenMinestuck
{
	private static final ResourceLocation guiGristcache = new ResourceLocation("minestuck", "textures/gui/grist_cache.png");

	private static final int guiWidth = 226, guiHeight = 190;

	private final IGristSelectable owner;
	private int page = 0;
	private GuiButtonExt previousButton;
	private GuiButtonExt nextButton;

	public GuiGristSelector(IGristSelectable owner)
	{
		this.owner = owner;
	}

	@Override
	public void drawScreen(int xcor, int ycor, float par3)
	{
		int xOffset = (width - guiWidth) / 2;
		int yOffset = (height - guiHeight) / 2;

		this.drawDefaultBackground();

		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

		this.mc.getTextureManager().bindTexture(guiGristcache);
		this.drawTexturedModalRect(xOffset, yOffset, 0, 0, guiWidth, guiHeight);

		String cacheMessage = I18n.format("gui.selectGrist");
		mc.fontRenderer.drawString(cacheMessage, (this.width / 2) - mc.fontRenderer.getStringWidth(cacheMessage) / 2, yOffset + 12, 0x404040);
		super.drawScreen(xcor, ycor, par3);

		GlStateManager.color(1, 1, 1);
		GlStateManager.disableRescaleNormal();
		RenderHelper.disableStandardItemLighting();
		GlStateManager.disableLighting();
		GlStateManager.disableDepth();

		this.drawGrist(xOffset, yOffset, xcor, ycor, page);

/*		if (tooltip != -1)
			if(tooltip % 2 == 0)
				drawHoveringText(Arrays.asList(I18n.format("grist.format", GristType.values()[tooltip/2].getDisplayName())),
						xcor, ycor, fontRenderer);
			else drawHoveringText(Arrays.asList(String.valueOf(clientGrist.getGrist(GristType.values()[tooltip/2]))), xcor, ycor, fontRenderer);*/
	}

	@Override
	protected void mouseClicked(int xcor, int ycor, int mouseButton) throws IOException
	{
		super.mouseClicked(xcor, ycor, mouseButton);
		if (mouseButton == 0)
		{
			int xOffset = (width - guiWidth) / 2;
			int yOffset = (height - guiHeight) / 2;

			List<Grist> types = new ArrayList<>(Grist.REGISTRY.getValues());
			Collections.sort(types);
			types = types.stream().skip(page * rows * columns).limit(rows * columns).collect(Collectors.toList());

			int offset = 0;
			for (Grist type : types)
			{
				int row = offset / columns;
				int column = offset % columns;
				int gristXOffset = xOffset + gristIconX + (gristDisplayXOffset * column - column);
				int gristYOffset = yOffset + gristIconY + (gristDisplayYOffset * row - row);
				if (isPointInRegion(gristXOffset, gristYOffset, 16, 16, xcor, ycor))
				{
					owner.select(type);

					if (mc.currentScreen != this)
						mc.currentScreen.setWorldAndResolution(mc, width, height);

					break;
				}
				offset++;
			}
		}
	}

	protected boolean isPointInRegion(int regionX, int regionY, int regionWidth, int regionHeight, int pointX, int pointY)
	{
		return pointX >= regionX && pointX < regionX + regionWidth && pointY >= regionY && pointY < regionY + regionHeight;
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException
	{
		int maxPage = (Grist.REGISTRY.getValues().size() - 1) / (rows * columns);
		if (button == previousButton && page > 0)
		{
			page--;
			if (page == 0)
			{
				this.buttonList.remove(previousButton);
			}
			if (!this.buttonList.contains(nextButton))
			{
				this.addButton(nextButton);
			}
		}
		else if (button == nextButton && page < maxPage)
		{
			page++;
			if (page == maxPage)
			{
				this.buttonList.remove(nextButton);
			}
			if (!this.buttonList.contains(previousButton))
			{
				this.addButton(previousButton);
			}
		}
	}

	/**
	 * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
	 * window resizes, the buttonList is cleared beforehand.
	 */
	@Override
	public void initGui()
	{
		super.initGui();
		int xOffset = (width - guiWidth) / 2;
		int yOffset = (height - guiHeight) / 2;
		this.previousButton = new GuiButtonExt(1, (this.width) + 8, yOffset + 8, 16, 16, "<");
		this.nextButton = new GuiButtonExt(2, xOffset + guiWidth - 24, yOffset + 8, 16, 16, ">");
		if (Grist.REGISTRY.getValues().size() > rows * columns)
		{
			this.buttonList.add(this.nextButton);
		}
	}

	@Override
	public void onGuiClosed()
	{
		owner.cancel();
		mc.player.closeScreen();
	}
}
