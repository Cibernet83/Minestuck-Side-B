package com.mraof.minestuck.client.gui;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.tileentity.TileEntityModusControlDeck;
import com.mraof.minestuck.util.MinestuckPlayerData;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

import java.io.IOException;

public class GuiModusControlDeck extends GuiScreen
{
	private static final ResourceLocation guiBackground = new ResourceLocation(Minestuck.MODID, "textures/gui/transportalizer.png");

	private static final int guiWidth = 126;
	private static final int guiHeight = 98;

	private final TileEntityModusControlDeck te;

	private GuiButton syncButton;
	private GuiTextField[] lengthFields;
	private int totalCards;
	private int bottomLength;

	public GuiModusControlDeck(TileEntityModusControlDeck te)
	{
		this.te = te;
	}

	@Override
	public void initGui()
	{
		int yOffset = (height / 2) - (guiHeight / 2);

		lengthFields = new GuiTextField[te.lengths.size()];
		for (int i = 0; i < te.lengths.size(); i++)
		{
			GuiTextField lengthField = new GuiTextField(0, fontRenderer, width / 2 - 20, yOffset + 25 + (lengthFields.length - 1 - i) * 30, 40, 20);
			lengthField.setMaxStringLength(4);
			lengthField.setFocused(false);
			lengthField.setText(String.valueOf(te.lengths.get(i)));
			lengthFields[i] = lengthField;
		}

		syncButton = new GuiButton(0, width / 2 - 20, yOffset + 50 + lengthFields.length * 30, 40, 20, I18n.format("gui.sync"));
		buttonList.add(syncButton);

		totalCards = MinestuckPlayerData.clientData.sylladex.getTotalSlots();
		recalculateBottomLength();
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
		fontRenderer.drawString(String.valueOf(bottomLength), (this.width / 2) - fontRenderer.getStringWidth(String.valueOf(bottomLength)) / 2, yOffset + 30 + lengthFields.length * 30, 0x404040);
		for (GuiTextField lengthField : lengthFields)
			lengthField.drawTextBox();
		super.drawScreen(x, y, f1);
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException
	{
		super.keyTyped(typedChar, keyCode);

		for (GuiTextField lengthField : lengthFields)
			if (lengthField.isFocused())
			{
				String prevValue = lengthField.getText();
				lengthField.textboxKeyTyped(typedChar, keyCode);

				if (!lengthField.getText().isEmpty())
				{
					try
					{
						int value = lengthField.getText().isEmpty() ? 0 : Integer.parseInt(lengthField.getText());
						int clampedValue = MathHelper.clamp(value, 1, 255);
						if (value != clampedValue)
							lengthField.setText(String.valueOf(clampedValue));
						recalculateBottomLength();
					}
					catch (NumberFormatException e)
					{
						lengthField.setText(prevValue);
					}
				}
			}
	}

	@Override
	protected void mouseClicked(int x, int y, int button) throws IOException
	{
		super.mouseClicked(x, y, button);
		for (GuiTextField lengthField : lengthFields)
			lengthField.mouseClicked(x, y, button);
	}

	@Override
	protected void actionPerformed(GuiButton button)
	{
		if(button.id == 0)
		{
			//TODO @Jade
			this.mc.displayGuiScreen(null);
		}
	}

	private void recalculateBottomLength()
	{
		float calculatedLength = totalCards;
		for (GuiTextField lengthField : lengthFields)
			calculatedLength /= (float) Integer.parseInt(lengthField.getText());
		bottomLength = MathHelper.ceil(calculatedLength);
	}
}
