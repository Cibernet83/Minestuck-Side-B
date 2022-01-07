package com.mraof.minestuck.client.gui;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.MinestuckConfig;
import com.mraof.minestuck.alchemy.GristSet;
import com.mraof.minestuck.client.util.GuiUtil;
import com.mraof.minestuck.inventory.ContainerGristWidget;
import com.mraof.minestuck.tileentity.TileEntityGristWidget;
import com.mraof.minestuck.util.MinestuckPlayerData;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.config.GuiButtonExt;

import java.io.IOException;
import java.util.List;

public class GuiGristWidget extends GuiMachine
{
	protected TileEntityGristWidget te;
	private ResourceLocation guiBackground;
	private ResourceLocation guiProgress;
	private int progressX;
	private int progressY;
	private int progressWidth;
	private int progressHeight;
	private int goX;
	private int goY;

	public GuiGristWidget(InventoryPlayer inventoryPlayer, TileEntityGristWidget tileEntity)
	{
		super(new ContainerGristWidget(inventoryPlayer, tileEntity), tileEntity);
		this.te = tileEntity;
		guiBackground = new ResourceLocation(Minestuck.MODID, "textures/gui/grist_widget.png");
		guiProgress = new ResourceLocation(Minestuck.MODID, "textures/gui/progress/grist_widget.png");

		progressX = 54;
		progressY = 23;
		progressWidth = 71;
		progressHeight = 10;
		goX = 72;
		goY = 31;
	}

	@Override
	public void initGui()
	{
		super.initGui();

		if (!te.isAutomatic())
		{
			goButton = new GuiButtonExt(1, (width - xSize) / 2 + goX, (height - ySize) / 2 + goY, 30, 12, te.overrideStop ? "STOP" : "GO");
			buttonList.add(goButton);
			if (MinestuckConfig.clientDisableGristWidget)
				goButton.enabled = false;
		}
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		fontRenderer.drawString(I18n.format("gui.widget.name"), 8, 6, 0xFFFFFF);
		//draws "Inventory" or your regional equivalent
		fontRenderer.drawString(I18n.format("container.inventory"), 8, ySize - 96 + 2, 0xFFFFFF);
		if (!te.getStackInSlot(0).isEmpty())
		{
			//Render grist requirements
			GristSet set = te.getGristWidgetResult();

			GuiUtil.drawGristBoard(set, GuiUtil.GristboardMode.GRIST_WIDGET, 9, 45, fontRenderer);

			int cost = te.getGristWidgetBoondollarValue();
			long has = MinestuckPlayerData.clientData.boondollars;
			String costText = GuiUtil.addSuffix(cost) + "£(" + GuiUtil.addSuffix(has) + ")";
			fontRenderer.drawString(costText, xSize - 9 - fontRenderer.getStringWidth(costText), ySize - 96 + 3, cost > has ? 0xFF0000 : 0x00FF00);

			List<String> tooltip = GuiUtil.getGristboardTooltip(set, mouseX - this.guiLeft, mouseY - this.guiTop, 9, 45, fontRenderer);
			if (tooltip != null)
				this.drawHoveringText(tooltip, mouseX - this.guiLeft, mouseY - this.guiTop, fontRenderer);
			else if (mouseY - guiTop >= ySize - 96 + 3 && mouseY - guiTop < ySize - 96 + 3 + fontRenderer.FONT_HEIGHT)
			{
				if (!GuiUtil.addSuffix(cost).equals(String.valueOf(cost)) && mouseX - guiLeft < xSize - 9 - fontRenderer.getStringWidth("£(" + GuiUtil.addSuffix(has) + ")")
							&& mouseX - guiLeft >= xSize - 9 - fontRenderer.getStringWidth(costText))
					drawHoveringText(String.valueOf(cost), mouseX - this.guiLeft, mouseY - this.guiTop);
				else if (!GuiUtil.addSuffix(has).equals(String.valueOf(has)) && mouseX - guiLeft < xSize - 9 - fontRenderer.getStringWidth(")")
								 && mouseX - guiLeft >= xSize - 9 - fontRenderer.getStringWidth(GuiUtil.addSuffix(has) + ")"))
					drawHoveringText(String.valueOf(has), mouseX - this.guiLeft, mouseY - this.guiTop);
			}
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

		//draw background
		this.mc.getTextureManager().bindTexture(guiBackground);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);

		//draw progress bar
		this.mc.getTextureManager().bindTexture(guiProgress);
		int width = getScaledValue(te.progress, te.maxProgress, progressWidth);
		int height = progressHeight;
		drawModalRectWithCustomSizedTexture(x + progressX, y + progressY, 0, 0, width, height, progressWidth, progressHeight);
	}

	@Override
	protected void mouseClicked(int par1, int par2, int par3) throws IOException
	{
		super.mouseClicked(par1, par2, par3);
		if (par3 == 1)
		{
			if (goButton != null && goButton.mousePressed(this.mc, par1, par2))
			{
				goButton.playPressSound(this.mc.getSoundHandler());
				this.actionPerformed(goButton);
			}
		}
	}

	/**
	 * Returns a number to be used in calculation of progress bar length.
	 *
	 * @param progress the progress done.
	 * @param max      The maximum amount of progress.
	 * @param imageMax The length of the progress bar image to scale to
	 * @return The length the progress bar should be shown to
	 */
	public int getScaledValue(int progress, int max, int imageMax)
	{
		return (int) ((float) imageMax * ((float) progress / (float) max));
	}

}