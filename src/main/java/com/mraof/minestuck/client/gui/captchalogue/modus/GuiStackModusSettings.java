package com.mraof.minestuck.client.gui.captchalogue.modus;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GuiStackModusSettings extends GuiModusSettings
{
	protected static final int FILO_BUTTON_X = 31, FILO_BUTTON_Y = 117, FIFO_GAP = 3;
	protected static final int FILO_BUTTON_WIDTH = EJECT_BUTTON_WIDTH / 2, FILO_BUTTON_HEIGHT = EJECT_BUTTON_HEIGHT / 2;

	private boolean fifo;

	public GuiStackModusSettings(ItemStack modusStack, ResourceLocation settingsGuiTexture, boolean fifo)
	{
		super(modusStack, settingsGuiTexture);
		this.fifo = fifo;
	}

	@Override
	public void initGui()
	{
		super.initGui();
		buttons.add(new ModusGuiButton(settingsGuiTexture, guiX + FILO_BUTTON_X, guiY + FILO_BUTTON_Y, EJECT_BUTTON_WIDTH, GUI_HEIGHT, FILO_BUTTON_WIDTH, FILO_BUTTON_HEIGHT, I18n.format("gui.fifo"), modus.getTextColor(), fifo)
		{
			@Override
			public void click()
			{

			}
		});
		buttons.add(new ModusGuiButton(settingsGuiTexture, guiX + FILO_BUTTON_X + FILO_BUTTON_WIDTH + FIFO_GAP, guiY + FILO_BUTTON_Y, EJECT_BUTTON_WIDTH, GUI_HEIGHT, FILO_BUTTON_WIDTH, FILO_BUTTON_HEIGHT, I18n.format("gui.filo"), modus.getTextColor(), !fifo)
		{
			@Override
			public void click()
			{

			}
		});
	}
}
