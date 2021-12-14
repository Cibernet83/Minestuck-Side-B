package com.mraof.minestuck.client.gui;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.inventory.miniMachines.ContainerMiniSburbMachine;
import com.mraof.minestuck.tileentity.TileEntityMiniSburbMachine;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.config.GuiButtonExt;

import java.io.IOException;

public abstract class GuiMiniSburbMachine extends GuiMachine
{

	protected final String name;
	protected final TileEntityMiniSburbMachine te;
	protected ResourceLocation guiBackground;
	protected ResourceLocation guiProgress;
	//private EntityPlayer player;
	protected int progressX;
	protected int progressY;
	protected int progressWidth;
	protected int progressHeight;
	protected int goX;
	protected int goY;

	public GuiMiniSburbMachine(String name, ContainerMiniSburbMachine container, TileEntityMiniSburbMachine tileEntity)
	{
		super(container, tileEntity);
		this.te = tileEntity;
		this.name = name;
		guiBackground = new ResourceLocation(Minestuck.MODID, "textures/gui/"+name+".png");
		guiProgress = new ResourceLocation(Minestuck.MODID, "textures/gui/progress/"+name+".png");
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
		fontRenderer.drawString(I18n.format("gui." + name + ".name"), 8, 6, 4210752);
		//draws "Inventory" or your regional equivalent
		fontRenderer.drawString(I18n.format("container.inventory"), 8, ySize - 96 + 2, 4210752);
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
		int height = getScaledValue(te.progress, te.maxProgress, progressHeight);
		drawModalRectWithCustomSizedTexture(x + progressX, y + progressY + progressHeight - height, 0, progressHeight - height, width, height, progressWidth, progressHeight);
	}

	@Override
	public void initGui()
	{
		super.initGui();

		if (!te.isAutomatic())
		{
			goButton = new GuiButtonExt(1, (width - xSize) / 2 + goX, (height - ySize) / 2 + goY, 30, 12, te.overrideStop ? "STOP" : "GO");
			buttonList.add(goButton);
		}
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
	 * Draws a box like drawModalRect, but with custom width and height values.
	 */
	public void drawCustomBox(int par1, int par2, int par3, int par4, int par5, int par6, int width, int height)
	{
		float f = 1 / (float) width;
		float f1 = 1 / (float) height;
		BufferBuilder render = Tessellator.getInstance().getBuffer();
		render.begin(7, DefaultVertexFormats.POSITION_TEX);
		render.pos(par1, par2 + par6, 0D).tex((par3) * f, (par4 + par6) * f1).endVertex();
		render.pos(par1 + par5, par2 + par6, this.zLevel).tex((par3 + par5) * f, (par4 + par6) * f1).endVertex();
		render.pos(par1 + par5, par2, this.zLevel).tex((par3 + par5) * f, (par4) * f1).endVertex();
		render.pos(par1, par2, this.zLevel).tex((par3) * f, (par4) * f1).endVertex();
		Tessellator.getInstance().draw();
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