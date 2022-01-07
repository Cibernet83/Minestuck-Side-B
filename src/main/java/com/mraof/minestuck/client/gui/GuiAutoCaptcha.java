package com.mraof.minestuck.client.gui;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.inventory.ContainerAutoCaptcha;
import com.mraof.minestuck.tileentity.TileEntityAutoCaptcha;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiAutoCaptcha extends GuiContainer
{
	private static final ResourceLocation TEXTURES = new ResourceLocation(Minestuck.MODID, "textures/gui/auto_captcha.png");
	private final InventoryPlayer player;
	private final TileEntityAutoCaptcha tileEntity;

	public GuiAutoCaptcha(InventoryPlayer player, TileEntityAutoCaptcha tileEntity)
	{
		super(new ContainerAutoCaptcha(player, tileEntity));
		this.player = player;
		this.tileEntity = tileEntity;
	}

	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}

	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		String s = this.tileEntity.getDisplayName().getUnformattedText();
		this.fontRenderer.drawString(s, this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 6, 4210752);
		this.fontRenderer.drawString(this.player.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(TEXTURES);
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);

		int l = this.getProgressScaled(24);
		this.drawTexturedModalRect(i + 79, j + 34, 176, 0, l + 1, 16);
	}

	private int getProgressScaled(int pixels)
	{
		int i = TileEntityAutoCaptcha.totalTime - this.tileEntity.getField(0);
		return i != 0 ? i * pixels / TileEntityAutoCaptcha.totalTime : 0;
	}
}
