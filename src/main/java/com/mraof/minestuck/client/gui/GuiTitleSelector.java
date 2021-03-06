package com.mraof.minestuck.client.gui;

import com.mraof.minestuck.network.MinestuckNetwork;
import com.mraof.minestuck.network.message.MessageTitleRequest;
import com.mraof.minestuck.util.EnumAspect;
import com.mraof.minestuck.util.EnumClass;
import com.mraof.minestuck.util.Title;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.config.GuiButtonExt;

import java.io.IOException;

public class GuiTitleSelector extends GuiScreen
{
	private static final ResourceLocation guiBackground = new ResourceLocation("minestuck", "textures/gui/title_selector.png");
	private static final int guiWidth = 186, guiHeight = 157;
	private EnumClass currentClass;
	private EnumAspect currentAspect;
	private GuiButton[] classButtons = new GuiButton[12], aspectButtons = new GuiButton[12];
	private GuiButton selectButton;

	private Title previous;
	private boolean sendPacket = true;

	public GuiTitleSelector(Title title)
	{
		previous = title;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		selectButton.enabled = currentClass != null && currentAspect != null;

		int xOffset = (width - guiWidth) / 2;
		int yOffset = (height - guiHeight) / 2;

		this.drawDefaultBackground();

		this.mc.getTextureManager().bindTexture(guiBackground);
		this.drawTexturedModalRect(xOffset, yOffset, 0, 0, guiWidth, guiHeight);

		String message = previous == null ? I18n.format("gui.selectTitle") : I18n.format("gui.selectTitle.used", previous.getTitleName());
		mc.fontRenderer.drawString(message, (this.width / 2) - mc.fontRenderer.getStringWidth(message) / 2, yOffset + 12, 0x404040);

		message = I18n.format("title.format", "", "");
		mc.fontRenderer.drawString(message, (this.width / 2) - mc.fontRenderer.getStringWidth(message) / 2, yOffset + 56 - mc.fontRenderer.FONT_HEIGHT / 2, 0x404040);

		super.drawScreen(mouseX, mouseY, partialTicks);

	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException
	{
		if (button.id >= 0 && button.id < 12)    //class
		{
			if (currentClass != null)
				classButtons[currentClass.ordinal()].enabled = true;
			int id = button.id;
			currentClass = EnumClass.values()[id];
			button.enabled = false;

		}
		else if (button.id >= 12 && button.id < 24)    //aspect
		{
			if (currentAspect != null)
				aspectButtons[currentAspect.ordinal()].enabled = true;
			int id = button.id - 12;
			currentAspect = EnumAspect.values()[id];
			button.enabled = false;

		}
		else if (button.id == -1)    //select
		{
			MinestuckNetwork.sendToServer(new MessageTitleRequest(currentClass, currentAspect));
			sendPacket = false;
			mc.displayGuiScreen(null);
		}
	}

	@Override
	public void initGui()
	{
		for (int i = 0; i < 12; i++)
		{
			GuiButton button = new GuiButtonExt(i, (width - guiWidth) / 2 + 4 + (i % 2) * 40, (height - guiHeight) / 2 + 24 + (i / 2) * 16, 40, 16, EnumClass.values()[i].getDisplayName());
			buttonList.add(button);
			classButtons[i] = button;
		}
		for (int i = 0; i < 12; i++)
		{
			GuiButton button = new GuiButtonExt(12 + i, (width - guiWidth) / 2 + 102 + (i % 2) * 40, (height - guiHeight) / 2 + 24 + (i / 2) * 16, 40, 16, EnumAspect.values()[i].getDisplayName());
			buttonList.add(button);
			aspectButtons[i] = button;
		}
		selectButton = new GuiButtonExt(-1, (width - guiWidth) / 2 + 63, (height - guiHeight) / 2 + 128, 60, 20, "Select");
		buttonList.add(selectButton);
	}

	@Override
	public void onGuiClosed()
	{
		if (sendPacket)
			MinestuckNetwork.sendToServer(new MessageTitleRequest());
	}

}