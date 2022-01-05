package com.mraof.minestuck.client.gui.captchalogue.modus;

import com.mraof.minestuck.captchalogue.modus.Modus;
import com.mraof.minestuck.client.MinestuckFontRenderer;
import com.mraof.minestuck.item.ItemModus;
import com.mraof.minestuck.network.MinestuckNetwork;
import com.mraof.minestuck.network.MinestuckMessage;
import com.mraof.minestuck.util.SylladexUtils;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;

public class GuiModusSettings extends GuiScreen implements GuiYesNoCallback
{
	protected static final int GUI_WIDTH = 145, GUI_HEIGHT = 185;
	protected static final int EJECT_BUTTON_X = 38, EJECT_BUTTON_Y = 37;
	protected static final int EJECT_BUTTON_WIDTH = 80, EJECT_BUTTON_HEIGHT = 32;

	protected final ItemStack modusStack;
	protected final Modus modus;
	protected final NBTTagCompound modusSettings;
	protected final ResourceLocation settingsGuiTexture;
	protected int guiX, guiY;

	protected final ArrayList<ModusGuiButton> buttons = new ArrayList<>();

	public GuiModusSettings(ItemStack modusStack, ResourceLocation settingsGuiTexture)
	{
		this.modusStack = modusStack;
		this.modus = ((ItemModus)modusStack.getItem()).getModus();
		this.modusSettings = SylladexUtils.getModusSettings(modusStack);
		this.settingsGuiTexture = settingsGuiTexture;
	}

	@Override
	public void initGui()
	{
		guiX = (width - GUI_WIDTH) / 2;
		guiY = (height - GUI_HEIGHT) / 2;

		buttons.add(new ModusGuiButton(settingsGuiTexture, guiX + EJECT_BUTTON_X, guiY + EJECT_BUTTON_Y, 0, GUI_HEIGHT, EJECT_BUTTON_WIDTH, EJECT_BUTTON_HEIGHT, I18n.format("gui.ejectModusButton"), modus.getTextColor())
		{
			@Override
			public void click()
			{
				mc.currentScreen = new GuiYesNo(GuiModusSettings.this, I18n.format("gui.emptySylladex1"), I18n.format("gui.emptySylladex2"), 0);
				mc.currentScreen.setWorldAndResolution(mc, width, height);
			}
		});
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		this.drawDefaultBackground();

		mc.getTextureManager().bindTexture(settingsGuiTexture);
		drawTexturedModalRect(guiX, guiY, 0, 0, GUI_WIDTH, GUI_HEIGHT);
		MinestuckFontRenderer.lucidaConsoleSmall.drawString(I18n.format("gui.fetchModus", modus.getName().toLowerCase()), guiX + 57, guiY + 18, modus.getTextColor());

		for (ModusGuiButton button : buttons)
			button.draw(this, mouseX, mouseY, partialTicks);
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
	{
		if (mouseButton == 0)
			for (int i = buttons.size() - 1; i >= 0; i--)
				if (buttons.get(i).hit(mouseX, mouseY))
				{
					buttons.get(i).onClick(this);
					break;
				}
	}

	@Override
	public void confirmClicked(boolean result, int id)
	{
		if(result)
			MinestuckNetwork.sendToServer(MinestuckMessage.makePacket(MinestuckMessage.Type.SYLLADEX_EMPTY_REQUEST));
		mc.currentScreen = this;
	}

	@Override
	public boolean doesGuiPauseGame()
	{
		return false;
	}
}
