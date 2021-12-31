package com.mraof.minestuck.client.gui.captchalogue.sylladex;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.captchalogue.captchalogueable.ICaptchalogueable;
import com.mraof.minestuck.captchalogue.sylladex.MultiSylladex;
import com.mraof.minestuck.client.MinestuckFontRenderer;
import com.mraof.minestuck.client.settings.MinestuckKeyHandler;
import com.mraof.minestuck.network.MinestuckChannelHandler;
import com.mraof.minestuck.network.MinestuckPacket;
import com.mraof.minestuck.network.MinestuckPacket.Type;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Mouse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SideOnly(Side.CLIENT)
public class GuiSylladex extends GuiScreen implements GuiYesNoCallback
{
	public static final ResourceLocation SYLLADEX_FRAME = new ResourceLocation("minestuck", "textures/gui/sylladex_frame.png");
	public static final ResourceLocation CARD_TEXTURE = new ResourceLocation("minestuck", "textures/gui/captcha_cards.png");
	public static final ResourceLocation EXTRAS = new ResourceLocation(Minestuck.MODID, "textures/gui/fmp_icons.png");
	private static final int GUI_WIDTH = 256, GUI_HEIGHT = 202;
	private static final int MAP_WIDTH = 224, MAP_HEIGHT = 153;
	private static final int X_OFFSET = 16, Y_OFFSET = 17;

	public RenderItem itemRender;
	private MultiSylladex sylladex;
	private MultiSylladexGuiContainer cardGuiContainer;
	
	/**
	 * Position of the map (the actual gui viewport)
	 */
	private float mapX, mapY;
	private float mapWidth = MAP_WIDTH, mapHeight = MAP_HEIGHT;
	/**
	 * The scrolling
	 */
	private float scroll = 1F;

	private int mousePosX, mousePosY;
	private boolean mousePressed;

	private GuiButton emptySylladex;

	private boolean updatedOnce;
	
	public GuiSylladex(MultiSylladex sylladex)
	{
		updateSylladex(sylladex);
		this.mc = Minecraft.getMinecraft();
		this.itemRender = mc.getRenderItem();
	}
	
	@Override
	public void initGui()
	{
		emptySylladex = new GuiButton(0, (width - GUI_WIDTH)/2 + 140, (height - GUI_HEIGHT)/2 + 175, 100, 20, I18n.format("gui.emptySylladexButton"));
		buttonList.add(emptySylladex);
	}
	
	@Override
	public void drawScreen(int mx, int my, float partialTicks)
	{
		cardGuiContainer.update(0, partialTicks);

		if (!updatedOnce)
		{
			mapX = cardGuiContainer.width < MAP_WIDTH ? (MAP_WIDTH - cardGuiContainer.width) / 2f : 20;
			mapY = cardGuiContainer.height < MAP_HEIGHT ? (MAP_HEIGHT - cardGuiContainer.height) / 2f : 20;
			updatedOnce = true;
		}

		this.drawDefaultBackground();
		
		int mouseWheel = Mouse.getDWheel();
		float prevScroll = scroll;
		
		if (mouseWheel < 0)
			this.scroll += 0.25F;
		else if (mouseWheel > 0)
			this.scroll -= 0.25F;
		this.scroll = MathHelper.clamp(this.scroll, 1.0F, 2.0F);
		
		if(prevScroll != scroll)
		{
			float i1 = mapX + mapWidth / 2; // TODO: Make scrolling revolve around the mouse pos
			float i2 = mapY + mapHeight / 2;
			mapWidth = Math.round(MAP_WIDTH*scroll);
			mapHeight = Math.round(MAP_HEIGHT*scroll);
			mapX = i1 - mapWidth/2;
			mapY = i2 - mapHeight/2;

			//mapX = MathHelper.clamp(mapX, 0, mapWidth - cardsWidth);
			//mapY = MathHelper.clamp(mapY, 0, mapHeight - cardsHeight);
		}

		// Upper-left corner of the gui in pixels
		int guiX = (width - GUI_WIDTH) / 2;
		int guiY = (height - GUI_HEIGHT) / 2;
		
		if(Mouse.isButtonDown(0))
		{
			if (mousePressed)
			{
				//mapX = MathHelper.clamp(mapX - (mousePosX - mouseX) * scroll, 0, mapWidth - cardsWidth);
				//mapY = MathHelper.clamp(mapY - (mousePosY - mouseY) * scroll, 0, mapHeight - cardsHeight);
				mapX -= (mousePosX - mx) * scroll; // TODO: Figure out a way to cull things offscreen
				mapY -= (mousePosY - my) * scroll;
			}
			mousePosX = mx;
			mousePosY = my;
			mousePressed = true;
		}
		else
			mousePressed = false;

		float mouseX = (mx - guiX - X_OFFSET) * scroll - mapX;
		float mouseY = (my - guiY - Y_OFFSET) * scroll - mapY;

		GlStateManager.pushMatrix();
		GlStateManager.translate(guiX, guiY, 0);

		// Prepare map
		GlStateManager.pushMatrix();
		GlStateManager.translate(X_OFFSET, Y_OFFSET, 0);
		GlStateManager.scale(1.0F / this.scroll, 1.0F / this.scroll, 1.0F);

		// Draw map
		drawRect(0, 0, (int) mapWidth, (int) mapHeight, 0xFF8B8B8B);

		GlStateManager.color(1F, 1F, 1F, 1F);
		GlStateManager.translate(mapX, mapY, 0);
		
		cardGuiContainer.draw(this, mouseX, mouseY, partialTicks);

		ArrayList<Integer> hitSlots = cardGuiContainer.hit(mouseX, mouseY);
		int[] slots = hitSlots == null ? new int[0] : hitSlots.stream().mapToInt(Integer::intValue).toArray();
		if (slots.length > 0)
			cardGuiContainer.drawPeek(slots, 0, this, mouseX, mouseY, partialTicks);

		// Finish map
		GlStateManager.popMatrix();
		
		mc.getTextureManager().bindTexture(SYLLADEX_FRAME);
		drawTexturedModalRect(0, 0, 0, 0, GUI_WIDTH, GUI_HEIGHT);

		MinestuckFontRenderer fontRenderer = MinestuckFontRenderer.lucidaConsoleSmall;
		fontRenderer.drawString(I18n.format("gui.sylladex"), 15, 5, 0x404040);
		
		String sylladexName = sylladex.getName().toLowerCase();
		fontRenderer.drawString(sylladexName, GUI_WIDTH - fontRenderer.getStringWidth(sylladexName) - 16, 5, 0x404040);

		GlStateManager.popMatrix();

		super.drawScreen(mx, my, partialTicks);
		
		if(slots.length > 0 && isMouseInContainer(mx, my))
		{
			ICaptchalogueable object = sylladex.peek(slots, 0);
			if (object != null)
				object.renderTooltip(this, mx, my);
		}
	}
	
	@Override
	protected void mouseClicked(int mx, int my, int mouseButton) throws IOException
	{
		if(isMouseInContainer(mx, my))
		{
			float guiX = (width - GUI_WIDTH)/2f;
			float guiY = (height - GUI_HEIGHT)/2f;
			float mouseX = (mx - guiX - X_OFFSET) * scroll - mapX;
			float mouseY = (my - guiY - Y_OFFSET) * scroll - mapY;
			ArrayList<Integer> hitSlots = cardGuiContainer.hit(mouseX, mouseY);
			if (hitSlots != null)
			{
				int[] slots = hitSlots.stream().mapToInt(Integer::intValue).toArray();
				MinestuckPacket packet = MinestuckPacket.makePacket(Type.SYLLADEX_FETCH, slots, mouseButton != 0);
				MinestuckChannelHandler.sendToServer(packet);
				return;
			}
		}
		super.mouseClicked(mx, my, mouseButton);
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException
	{
		super.keyTyped(typedChar, keyCode);
		if(MinestuckKeyHandler.instance.sylladexKey.isActiveAndMatches(keyCode))
			mc.displayGuiScreen(null);
	}

	@Override
	protected void actionPerformed(GuiButton button)
	{
		if(button == emptySylladex)
		{
			mc.currentScreen = new GuiYesNo(this, I18n.format("gui.emptySylladex1"), I18n.format("gui.emptySylladex2"), 0);
			mc.currentScreen.setWorldAndResolution(mc, width, height);
		}
	}
	
	@Override
	public void confirmClicked(boolean result, int id)
	{
		if(result)
			MinestuckChannelHandler.sendToServer(MinestuckPacket.makePacket(Type.SYLLADEX_EMPTY_REQUEST));
		mc.currentScreen = this;
		mousePressed = false;
	}
	
	@Override
	public boolean doesGuiPauseGame()
	{
		return false;
	}

	@Override
	public void renderToolTip(ItemStack stack, int x, int y)
	{
		super.renderToolTip(stack, x, y);
	}

	public void drawHoveringText(List<String> textLines, int x, int y) {
		super.drawHoveringText(textLines, x, y, fontRenderer);
	}

	private boolean isMouseInContainer(int xcor, int ycor) {
		float xOffset = (this.width - 256) / 2f;
		float yOffset = (this.height - 202) / 2f;
		return xcor >= xOffset + 16 && xcor < xOffset + 16 + 224 && ycor >= yOffset + 17 && ycor < yOffset + 17 + 153;
	}

	public void updateSylladex(MultiSylladex sylladex)
	{
		this.sylladex = sylladex;
		this.cardGuiContainer = sylladex.generateSubContainer(new int[sylladex.getModusLayers().length], 0, null);
	}
	
	/*public static class ModusSizeCard extends GuiCard
	{
		public int size;
		
		public ModusSizeCard(SylladexGuiHandler gui, int size, int xPos, int yPos, int textureIndex)
		{
			super(ItemStack.EMPTY, gui, -1, xPos, yPos, textureIndex);
			this.size = size;
		}
		
		@Override
		protected void drawTooltip(int mouseX, int mouseY) {}
		
		@Override
		protected void drawItem()
		{
			GlStateManager.color(1F, 1F, 1F, 1F);
			if(size > 1)
			{
				String stackSize = String.valueOf(size);
				int x = this.xPos - gui.mapX + 18 - gui.mc.fontRenderer.getStringWidth(stackSize);
				int y = this.yPos - gui.mapY + 15;
				if(x >= gui.mapWidth || y >= gui.mapHeight || x + gui.mc.fontRenderer.getStringWidth(stackSize) < 0 || y + gui.fontRenderer.FONT_HEIGHT < 0)
					return;
				GlStateManager.disableLighting();
				GlStateManager.disableDepth();
				GlStateManager.disableBlend();
				gui.mc.fontRenderer.drawStringWithShadow(stackSize, x, y, 0xC6C6C6);
				GlStateManager.enableLighting();
				GlStateManager.enableDepth();
				GlStateManager.enableBlend();
			}
		}
	}*/
}
