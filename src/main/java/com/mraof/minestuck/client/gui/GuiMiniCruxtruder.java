package com.mraof.minestuck.client.gui;

import com.mraof.minestuck.inventory.miniMachines.ContainerMiniCruxtruder;
import com.mraof.minestuck.tileentity.TileEntityMiniCruxtruder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiMiniCruxtruder extends GuiMiniSburbMachine
{
	public GuiMiniCruxtruder(InventoryPlayer inventoryPlayer, TileEntityMiniCruxtruder tileEntity)
	{
		super("cruxtruder", new ContainerMiniCruxtruder(inventoryPlayer, tileEntity), tileEntity);

		progressX = 82;
		progressY = 42;
		progressWidth = 10;
		progressHeight = 13;
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
		int width = progressWidth;
		int height = getScaledValue(te.progress, te.maxProgress, progressHeight);
		drawModalRectWithCustomSizedTexture(x + progressX, y + progressY + progressHeight-height, 0, progressHeight-height, width, height, progressWidth, progressHeight);
	}
}
