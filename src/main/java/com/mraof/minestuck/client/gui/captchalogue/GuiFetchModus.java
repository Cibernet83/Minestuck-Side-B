package com.mraof.minestuck.client.gui.captchalogue;

import com.mraof.minestuck.captchalogue.modus.Modus;
import com.mraof.minestuck.item.ItemModus;
import com.mraof.minestuck.network.MinestuckChannelHandler;
import com.mraof.minestuck.network.MinestuckPacket;
import com.mraof.minestuck.util.MinestuckUtils;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GuiFetchModus extends GuiScreen implements GuiYesNoCallback
{
	public static final ResourceLocation FETCH_MODUS_BORDER = new ResourceLocation("minestuck", "textures/gui/fetch_modus/fetch_modus_border_crafted.png");
	public static final ResourceLocation FETCH_MODUS_FACE = new ResourceLocation("minestuck", "textures/gui/fetch_modus/fetch_modus_face_crafted.png");
	public static final ResourceLocation FETCH_MODUS_BUTTONS = new ResourceLocation("minestuck", "textures/gui/fetch_modus/fetch_modus_buttons_crafted.png");
	private static final int GUI_WIDTH = 148, GUI_HEIGHT = 188;
	private static final int BUTTON_X = 39, BUTTON_Y = 34;
	private static final int BUTTON_WIDTH = 80, BUTTON_HEIGHT = 32;

	private final ItemStack modusStack;
	private final Modus modus;

	public GuiFetchModus(ItemStack modusStack)
	{
		this.modusStack = modusStack;
		this.modus = ((ItemModus)modusStack.getItem()).getModus();
	}

	@Override
	public void initGui()
	{

	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		this.drawDefaultBackground();

		int guiX = (width - GUI_WIDTH) / 2;
		int guiY = (height - GUI_HEIGHT) / 2;

		GlStateManager.pushMatrix();
		GlStateManager.translate(guiX, guiY, 0);

		mc.getTextureManager().bindTexture(FETCH_MODUS_BORDER);
		GlStateManager.color(1f, 1f, 1f);
		drawTexturedModalRect(0, 0, 0, 0, GUI_WIDTH, GUI_HEIGHT);

		mc.getTextureManager().bindTexture(FETCH_MODUS_FACE);
		MinestuckUtils.color(modus.getPrimaryColor());
		drawTexturedModalRect(0, 0, 0, 0, GUI_WIDTH, GUI_HEIGHT);

		mc.getTextureManager().bindTexture(FETCH_MODUS_BUTTONS);
		String eject = I18n.format("gui.ejectModusButton");
		if (MinestuckUtils.isPointInRegion(guiX + BUTTON_X, guiY + BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT, mouseX, mouseY))
		{
			MinestuckUtils.color(modus.getDarkerColor());
			drawTexturedModalRect(BUTTON_X, BUTTON_Y, 0, BUTTON_HEIGHT, BUTTON_WIDTH, BUTTON_HEIGHT);
			mc.fontRenderer.drawStringWithShadow(eject, BUTTON_X + (BUTTON_WIDTH - fontRenderer.getStringWidth(eject)) / 2f, BUTTON_Y + BUTTON_HEIGHT / 2f - 4, MinestuckUtils.multiply(modus.getTextColor(), 0.85f));

		}
		else
		{
			MinestuckUtils.color(modus.getLighterColor());
			drawTexturedModalRect(BUTTON_X, BUTTON_Y, 0, 0, BUTTON_WIDTH, BUTTON_HEIGHT);
			mc.fontRenderer.drawStringWithShadow(eject, BUTTON_X + (BUTTON_WIDTH - fontRenderer.getStringWidth(eject)) / 2f, BUTTON_Y + BUTTON_HEIGHT / 2f - 4, modus.getTextColor());

		}

		GlStateManager.color(1f, 1f, 1f);

		mc.fontRenderer.drawStringWithShadow(modus.getName(), 57, 15, modus.getTextColor());

		GlStateManager.popMatrix();
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
	{
		int guiX = (width - GUI_WIDTH) / 2;
		int guiY = (height - GUI_HEIGHT) / 2;

		if (mouseButton == 0)
		{
			if (MinestuckUtils.isPointInRegion(guiX + BUTTON_X, guiY + BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT, mouseX, mouseY))
			{
				mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
				mc.currentScreen = new GuiYesNo(this, I18n.format("gui.emptySylladex1"), I18n.format("gui.emptySylladex2"), 0);
				mc.currentScreen.setWorldAndResolution(mc, width, height);
			}
		}
	}

	@Override
	public void confirmClicked(boolean result, int id)
	{
		if(result)
			MinestuckChannelHandler.sendToServer(MinestuckPacket.makePacket(MinestuckPacket.Type.SYLLADEX_EMPTY_REQUEST));
		mc.currentScreen = this;
	}
}
