package com.cibernet.fetchmodiplus.client.gui;

import com.cibernet.fetchmodiplus.FetchModiPlus;
import com.cibernet.fetchmodiplus.client.gui.captchalogue.CipherGuiHandler;
import com.mraof.minestuck.client.gui.GuiScreenMinestuck;
import com.mraof.minestuck.network.MinestuckChannelHandler;
import com.mraof.minestuck.network.MinestuckPacket;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

public class GuiCipherDecode extends GuiScreenMinestuck
{
	private static final ResourceLocation TEXTURES = new ResourceLocation(FetchModiPlus.MODID, "textures/gui/cipher_decode.png");
	private static final int guiWidth = 226, guiHeight = 98;
	
	private final CipherGuiHandler gui;
	private final int index;
	private final ItemStack stack;
	
	private GuiTextField decodeField;
	private GuiTextField encodedField;
	private GuiButton doneButton;
	
	public GuiCipherDecode(CipherGuiHandler gui, int index)
	{
		this.gui = gui;
		this.index = index;
		this.stack = gui.getCards().get(index).item;
	}
	
	@Override
	public void initGui()
	{
		super.initGui();
		Keyboard.enableRepeatEvents(true);
		int i = (this.width - this.guiWidth) / 2;
		int j = (this.height - this.guiHeight) / 2;
		this.decodeField = new GuiTextField(0, this.fontRenderer, i + 10, j + 61, 103, 12);
		this.decodeField.setTextColor(-1);
		this.decodeField.setDisabledTextColour(-1);
		this.decodeField.setEnableBackgroundDrawing(false);
		this.decodeField.setMaxStringLength(35);
		
		this.encodedField = new GuiTextField(0, this.fontRenderer, i + 10, j + 35, 103, 12);
		this.encodedField.setTextColor(-1);
		this.encodedField.setDisabledTextColour(-1);
		this.encodedField.setEnableBackgroundDrawing(false);
		this.encodedField.setMaxStringLength(35);
		this.encodedField.setText(CipherGuiHandler.encodeName(stack));
		
		this.doneButton = new GuiButton(0, (this.width - 256) / 2 + 15, (this.height - 202) / 2 + 175, 120, 20, "gui.done");
	}
	
	public void onGuiClosed()
	{
		super.onGuiClosed();
		Keyboard.enableRepeatEvents(false);
		mc.currentScreen = gui;
		mc.player.closeScreen();
	}
	
	@Override
	public void updateScreen()
	{
		super.updateScreen();
		
		if(encodedField != null && decodeField != null)
		this.encodedField.setCursorPosition(this.decodeField.getCursorPosition());
	}
	
	@Override
	public void drawScreen(int xcor, int ycor, float par3)
	{
		this.encodedField.setText(CipherGuiHandler.encodeName(stack));
		
		int xOffset = (width - guiWidth) / 2;
		int yOffset = (height - guiHeight) / 2;
		
		this.drawDefaultBackground();
		
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		
		this.mc.getTextureManager().bindTexture(TEXTURES);
		this.drawTexturedModalRect(xOffset, yOffset, 0, 0, guiWidth, guiHeight);
		
		super.drawScreen(xcor, ycor, par3);
		
		GlStateManager.color(1, 1, 1);
		GlStateManager.disableRescaleNormal();
		RenderHelper.disableStandardItemLighting();
		GlStateManager.disableLighting();
		GlStateManager.disableDepth();
		
		decodeField.drawTextBox();
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException
	{
		super.actionPerformed(button);
		
		if(button.equals(doneButton) && stack.getDisplayName().toLowerCase().equals(decodeField.getText().toLowerCase()))
			onClick();
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException
	{
		if (!this.decodeField.textboxKeyTyped(typedChar, keyCode))
			super.keyTyped(typedChar, keyCode);
	}
	
	
	protected void onClick()
	{
		int toSend = -1;
		if (this.index != -1)
			toSend = this.index;
		
		if (toSend != -1)
		{
			MinestuckPacket packet = MinestuckPacket.makePacket(MinestuckPacket.Type.CAPTCHA, new Object[]{3, toSend, false});
			MinestuckChannelHandler.sendToServer(packet);
		}
		gui.width = this.width;
		gui.height = this.height;
		mc.currentScreen = gui;
	}
	
	/**
	 * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
	 */
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
	{
		super.mouseClicked(mouseX, mouseY, mouseButton);
		this.decodeField.mouseClicked(mouseX, mouseY, mouseButton);
	}
	
}
