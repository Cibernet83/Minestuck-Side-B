package com.mraof.minestuck.client.gui.captchalogue;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.captchalogue.captchalogueable.ICaptchalogueable;
import com.mraof.minestuck.captchalogue.sylladex.MultiSylladex;
import com.mraof.minestuck.client.settings.MinestuckKeyHandler;
import com.mraof.minestuck.network.MinestuckChannelHandler;
import com.mraof.minestuck.network.MinestuckPacket;
import com.mraof.minestuck.network.MinestuckPacket.Type;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.gui.GuiYesNoCallback;
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
	private ModusGuiContainer cardGuiContainer;
	
	/**
	 * Position of the map (the actual gui viewport)
	 */
	private float mapX, mapY;
	private float mapWidth = MAP_WIDTH, mapHeight = MAP_HEIGHT;
	private float cardsWidth, cardsHeight;
	/**
	 * The scrolling
	 */
	private float scroll = 1F;

	private int mousePosX, mousePosY;
	private boolean mousePressed;

	private GuiButton emptySylladex;
	
	public GuiSylladex(MultiSylladex sylladex)
	{
		updateSylladex(sylladex);
		this.mc = Minecraft.getMinecraft();
		this.itemRender = mc.getRenderItem();
		this.mapX = cardsWidth < MAP_WIDTH ? MAP_WIDTH / 2f - cardsWidth / 2f : MAP_WIDTH / 2f - 20;
		this.mapY = cardsHeight < MAP_HEIGHT ? MAP_HEIGHT / 2f - cardsHeight / 2f : MAP_HEIGHT / 2f - 20;
	}
	
	@Override
	public void initGui()
	{
		emptySylladex = new GuiButton(0, (width - GUI_WIDTH)/2 + 140, (height - GUI_HEIGHT)/2 + 175, 100, 20, I18n.format("gui.emptySylladexButton"));
		buttonList.add(emptySylladex);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		this.drawDefaultBackground();
		
		emptySylladex.x = (width - GUI_WIDTH)/2 + 140;
		emptySylladex.y = (height - GUI_HEIGHT)/2 + 175;
		
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
				mapX -= (mousePosX - mouseX) * scroll; // TODO: Figure out a way to cull things offscreen
				mapY -= (mousePosY - mouseY) * scroll;
			}
			mousePosX = mouseX;
			mousePosY = mouseY;
			mousePressed = true;
		}
		else
			mousePressed = false;

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
		
		cardGuiContainer.draw(this);

		// Finish map
		GlStateManager.popMatrix();
		
		mc.getTextureManager().bindTexture(SYLLADEX_FRAME);
		drawTexturedModalRect(0, 0, 0, 0, GUI_WIDTH, GUI_HEIGHT);
		
		mc.fontRenderer.drawString(I18n.format("gui.sylladex"), 15, 5, 0x404040);
		
		String str = sylladex.getName();
		mc.fontRenderer.drawString(str, GUI_WIDTH - mc.fontRenderer.getStringWidth(str) - 16, 5, 0x404040);

		GlStateManager.popMatrix();

		super.drawScreen(mouseX, mouseY, partialTicks);
		
		if(isMouseInContainer(mouseX, mouseY))
		{
			float translX = (mouseX - guiX - X_OFFSET) * scroll - mapX;
			float translY = (mouseY - guiY - Y_OFFSET) * scroll - mapY;
			ArrayList<Integer> hitSlots = cardGuiContainer.hit(translX, translY);
			if (hitSlots != null)
			{
				int[] slots = hitSlots.stream().mapToInt(Integer::intValue).toArray();
				ICaptchalogueable object = sylladex.peek(slots, 0);
				if (object != null)
					object.renderTooltip(this, mouseX, mouseY);
			}
		} // FIXME: fetchdeck inventory only saving on sync
	}
	
	@Override
	protected void mouseClicked(int xcor, int ycor, int mouseButton) throws IOException
	{
		if(isMouseInContainer(xcor, ycor))
		{
			float guiX = (width - GUI_WIDTH)/2f;
			float guiY = (height - GUI_HEIGHT)/2f;
			float translX = (xcor - guiX - X_OFFSET) * scroll - mapX;
			float translY = (ycor - guiY - Y_OFFSET) * scroll - mapY;
			ArrayList<Integer> hitSlots = cardGuiContainer.hit(translX, translY);
			if (hitSlots != null)
			{
				int[] slots = hitSlots.stream().mapToInt(Integer::intValue).toArray();
				MinestuckPacket packet = MinestuckPacket.makePacket(Type.SYLLADEX_FETCH, slots, mouseButton != 0);
				MinestuckChannelHandler.sendToServer(packet);
				return;
			}
		}
		super.mouseClicked(xcor, ycor, mouseButton);
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

	private boolean isMouseInContainer(int xcor, int ycor) {
		float xOffset = (this.width - 256) / 2f;
		float yOffset = (this.height - 202) / 2f;
		return xcor >= xOffset + 16 && xcor < xOffset + 16 + 224 && ycor >= yOffset + 17 && ycor < yOffset + 17 + 153;
	}

	public void updateSylladex(MultiSylladex sylladex)
	{
		this.sylladex = sylladex;
		this.cardGuiContainer = new ModusGuiContainer(sylladex);
		this.cardsWidth = cardGuiContainer.getWidth();
		this.cardsHeight = cardGuiContainer.getHeight();
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
