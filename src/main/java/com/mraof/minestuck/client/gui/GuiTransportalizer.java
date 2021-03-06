package com.mraof.minestuck.client.gui;

import com.mraof.minestuck.network.MinestuckNetwork;
import com.mraof.minestuck.network.message.MessageTransportalizerDestIdRequest;
import com.mraof.minestuck.tileentity.TileEntityTransportalizer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;

public class GuiTransportalizer extends GuiScreen
{
	private static final ResourceLocation guiBackground = new ResourceLocation("minestuck", "textures/gui/transportalizer.png");

	private static final int guiWidth = 126;
	private static final int guiHeight = 98;
	TileEntityTransportalizer te;
	private Minecraft mc;
	private GuiTextField destinationTextField;
	private GuiButton doneButton;

	public GuiTransportalizer(Minecraft mc, TileEntityTransportalizer te)
	{
		super();

		this.mc = mc;
		this.fontRenderer = mc.fontRenderer;
		this.te = te;
	}

	@Override
	public void drawScreen(int x, int y, float f1)
	{
		this.drawDefaultBackground();
		GlStateManager.color(1F, 1F, 1F, 1F);
		this.mc.getTextureManager().bindTexture(guiBackground);
		int yOffset = (this.height / 2) - (guiHeight / 2);
		this.drawTexturedModalRect((this.width / 2) - (guiWidth / 2), yOffset, 0, 0, guiWidth, guiHeight);
		fontRenderer.drawString(te.getId(), (this.width / 2) - fontRenderer.getStringWidth(te.getId()) / 2, yOffset + 10, te.getActive() ? 0x404040 : 0xFF0000);
		this.destinationTextField.drawTextBox();
		super.drawScreen(x, y, f1);
	}

	/**
	 * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
	 */
	@Override
	protected void keyTyped(char character, int key) throws IOException
	{
		super.keyTyped(character, key);
		this.destinationTextField.textboxKeyTyped(character, key);
		//this.doneBtn.enabled = this.commandTextField.getText().trim().length() > 0;

		//this.actionPerformed(this.doneBtn);
	}

	/**
	 * Called when the mouse is clicked.
	 */
	@Override
	protected void mouseClicked(int x, int y, int button) throws IOException
	{
		super.mouseClicked(x, y, button);
		this.destinationTextField.mouseClicked(x, y, button);
	}

	@Override
	protected void actionPerformed(GuiButton button)
	{
		if (button.id == 0 && this.destinationTextField.getText().length() == 4)
		{
			//Debug.print("Sending transportalizer packet with destination of " + this.destinationTextField.getText());
			MinestuckNetwork.sendToServer(new MessageTransportalizerDestIdRequest(te, this.destinationTextField.getText().toUpperCase()));
			this.mc.displayGuiScreen(null);
		}
	}

	@Override
	public void initGui()
	{
		int yOffset = (this.height / 2) - (guiHeight / 2);
		this.destinationTextField = new GuiTextField(0, this.fontRenderer, this.width / 2 - 20, yOffset + 25, 40, 20);
		this.destinationTextField.setMaxStringLength(4);
		this.destinationTextField.setFocused(true);
		this.destinationTextField.setText(this.te.getDestId());

		this.doneButton = new GuiButton(0, this.width / 2 - 20, yOffset + 50, 40, 20, I18n.format("gui.done"));
		this.buttonList.add(doneButton);
	}

}

