package com.mraof.minestuck.client.gui;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.tileentity.TileEntityModusControlDeck;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;

public class GuiModusControlDeck extends GuiScreen
{
	private static final ResourceLocation guiBackground = new ResourceLocation(Minestuck.MODID, "textures/gui/transportalizer.png");

	private static final int guiWidth = 126;
	private static final int guiHeight = 98;


	private final TileEntityModusControlDeck te;

	private GuiButton syncButton;
	private GuiTextField lengthField;

	public GuiModusControlDeck(TileEntityModusControlDeck te)
	{
		this.te = te;
	}

	@Override
	public void initGui() {
		super.initGui();

		int yOffset = (this.height / 2) - (guiHeight / 2);
		this.lengthField = new GuiTextField(0, this.fontRenderer, this.width / 2 - 20, yOffset + 25, 40, 20);
		this.lengthField.setMaxStringLength(4);
		this.lengthField.setFocused(true);
		this.lengthField.setText(Integer.toString(te.length));

		this.syncButton = new GuiButton(0, this.width / 2 - 20, yOffset + 50, 40, 20, I18n.format("gui.sync"));
		buttonList.add(syncButton);
	}

	@Override
	public void drawScreen(int x, int y, float f1)
	{
		this.drawDefaultBackground();
		GlStateManager.color(1F, 1F, 1F, 1F);
		this.mc.getTextureManager().bindTexture(guiBackground);
		int yOffset = (this.height / 2) - (guiHeight / 2);
		this.drawTexturedModalRect((this.width / 2) - (guiWidth / 2), yOffset, 0, 0, guiWidth, guiHeight);

		String str = I18n.format("gui.modus_control_deck.name");
		fontRenderer.drawString(str, (this.width / 2) - fontRenderer.getStringWidth(str) / 2, yOffset + 10, 0x404040);
		this.lengthField.drawTextBox();
		super.drawScreen(x, y, f1);
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException
	{
		super.keyTyped(typedChar, keyCode);
		
		if((Character.digit(typedChar, 10) >= 0 || keyCode == 14) && lengthField != null)
		{
			int value;
			lengthField.textboxKeyTyped(typedChar, keyCode);
			try {value = lengthField.getText().isEmpty() ? 0 : Integer.parseInt(lengthField.getText()); }
			catch (NumberFormatException e) {value = Integer.MAX_VALUE;}

			if(String.valueOf(value).length() > lengthField.getMaxStringLength()-1)
				value = (int) (Math.pow(10,lengthField.getMaxStringLength()) -1);

			lengthField.setText(String.valueOf(value));
		}
	}

	@Override
	protected void mouseClicked(int x, int y, int button) throws IOException
	{
		super.mouseClicked(x, y, button);
		this.lengthField.mouseClicked(x, y, button);
	}

	@Override
	protected void actionPerformed(GuiButton button)
	{
		if(button.id == 0 && !lengthField.getText().equals("0"))
		{
			//TODO @Jade
			this.mc.displayGuiScreen(null);
		}
	}
}
