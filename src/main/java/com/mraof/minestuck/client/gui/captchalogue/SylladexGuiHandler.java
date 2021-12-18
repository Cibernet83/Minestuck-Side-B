package com.mraof.minestuck.client.gui.captchalogue;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.client.settings.MinestuckKeyHandler;
import com.mraof.minestuck.inventory.captchalouge.ICaptchalogueable;
import com.mraof.minestuck.inventory.captchalouge.ISylladex;
import com.mraof.minestuck.inventory.captchalouge.Modus;
import com.mraof.minestuck.network.MinestuckChannelHandler;
import com.mraof.minestuck.network.MinestuckPacket;
import com.mraof.minestuck.network.MinestuckPacket.Type;
import com.mraof.minestuck.network.PacketCaptchaDeck;
import com.mraof.minestuck.util.SylladexUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
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
public class SylladexGuiHandler extends GuiScreen implements GuiYesNoCallback
{
	public static final ResourceLocation SYLLADEX_FRAME = new ResourceLocation("minestuck", "textures/gui/sylladex_frame.png");
	public static final ResourceLocation CARD_TEXTURE = new ResourceLocation("minestuck", "textures/gui/captcha_cards.png");
	public static final ResourceLocation EXTRAS = new ResourceLocation(Minestuck.MODID, "textures/gui/fmp_icons.png");
	private static final int GUI_WIDTH = 256, GUI_HEIGHT = 202;
	private static final int MAP_WIDTH = 224, MAP_HEIGHT = 153;
	private static final int X_OFFSET = 16, Y_OFFSET = 17;
	private static final int CARD_WIDTH = 21, CARD_HEIGHT = 26;

	private RenderItem itemRender;
	private int maxWidth, maxHeight;
	private final ISylladex.Sylladex sylladex;
	private final ModusGuiContainer cardGuiContainer;
	
	/**
	 * Position of the map (the actual gui viewport)
	 */
	private int mapX, mapY;
	private int mapWidth = MAP_WIDTH, mapHeight = MAP_HEIGHT;
	/**
	 * The scrolling
	 */
	private float scroll = 1F;

	private int mousePosX, mousePosY;
	private boolean mousePressed;

	private GuiButton emptySylladex;
	
	public SylladexGuiHandler(ISylladex.Sylladex sylladex)
	{
		this.sylladex = sylladex;
		this.cardGuiContainer = new ModusGuiContainer(sylladex);
		this.itemRender = mc.getRenderItem();
		updateContainers();
	}
	
	@Override
	public void initGui()
	{
		emptySylladex = new GuiButton(0, (width - GUI_WIDTH)/2 + 140, (height - GUI_HEIGHT)/2 + 175, 100, 20, I18n.format("gui.emptySylladexButton"));
		buttonList.add(emptySylladex);
	}
	
	@Override
	public void drawScreen(int xcor, int ycor, float f)
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
			double i1 = mapX + ((double)mapWidth)/2;
			double i2 = mapY + ((double)mapHeight)/2;
			mapWidth = Math.round(MAP_WIDTH*scroll);
			mapHeight = Math.round(MAP_HEIGHT*scroll);
			mapX = (int) (i1 - ((double)mapWidth)/2);
			mapY = (int) (i2 - ((double)mapHeight)/2);

			mapX = Math.max(0, Math.min(maxWidth - mapWidth, mapX));
			mapY = Math.max(0, Math.min(maxHeight - mapHeight, mapY));
		}
		
		int xOffset = (width - GUI_WIDTH)/2;
		int yOffset = (height - GUI_HEIGHT)/2;
		
		if(Mouse.isButtonDown(0))
		{
			
			if(isMouseInContainer(xcor, ycor))
			{
				if(isMouseInContainer(mousePosX, mousePosY))
				{
					mapX = Math.max(0, Math.min(maxWidth - mapWidth, mapX + mousePosX - xcor));
					mapY = Math.max(0, Math.min(maxHeight - mapHeight, mapY + mousePosY - ycor));
				}
				mousePosX = xcor;
				mousePosY = ycor;
			}
			
		}
		else
		{
			mousePressed = false;
			mousePosX = -1;
			mousePosY = -1;
		}

		// Prepare map
		GlStateManager.pushMatrix();
		GlStateManager.translate((float)xOffset + X_OFFSET, (float)yOffset + Y_OFFSET, 0F);
		GlStateManager.scale(1.0F / this.scroll, 1.0F / this.scroll, 1.0F);

		// Draw map
		drawRect(0, 0, mapWidth, mapHeight, 0xFF8B8B8B);
		
		GlStateManager.color(1F, 1F, 1F, 1F);
		
		cardGuiContainer.draw(this);
		
		/*RenderHelper.enableGUIStandardItemLighting();
		GlStateManager.enableRescaleNormal();
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
		for(GuiCard card : visibleCards)
			card.drawItem();
		GlStateManager.disableDepth();
		RenderHelper.disableStandardItemLighting();
		GlStateManager.color(1F, 1F, 1F, 1F);*/

		// Finish map
		GlStateManager.popMatrix();
		
		mc.getTextureManager().bindTexture(SYLLADEX_FRAME);
		drawTexturedModalRect(xOffset, yOffset, 0, 0, GUI_WIDTH, GUI_HEIGHT);
		
		mc.fontRenderer.drawString(I18n.format("gui.sylladex"), xOffset + 15, yOffset + 5, 0x404040);
		
		String str = sylladex.getName();
		mc.fontRenderer.drawString(str, xOffset + GUI_WIDTH - mc.fontRenderer.getStringWidth(str) - 16, yOffset + 5, 0x404040);
		
		super.drawScreen(xcor, ycor, f);
		
		if(isMouseInContainer(xcor, ycor))
		{
			int translX = (int) ((xcor - xOffset - X_OFFSET) * scroll);
			int translY = (int) ((ycor - yOffset - Y_OFFSET) * scroll);
			for(GuiCard card : visibleCards)
				if(translX >= card.xPos + 2 - mapX && translX < card.xPos + 18 - mapX &&
						translY >= card.yPos + 7 - mapY && translY < card.yPos + 23 - mapY)
				{
					if(!item.isEmpty())
						gui.renderToolTip(item, mouseX, mouseY);
					break;
				}
		}
	}
	
	@Override
	protected void mouseClicked(int xcor, int ycor, int mouseButton) throws IOException
	{
		if(isMouseInContainer(xcor, ycor))
		{
			int xOffset = (width - GUI_WIDTH)/2;
			int yOffset = (height - GUI_HEIGHT)/2;
			int translX = (int) ((xcor - xOffset - X_OFFSET) * scroll);
			int translY = (int) ((ycor - yOffset - Y_OFFSET) * scroll);
			ArrayList<Integer> hitSlots = cardGuiContainer.hit(translX, translY);
			if (hitSlots != null)
			{
				int[] slots = hitSlots.stream().mapToInt(Integer::intValue).toArray();
				MinestuckPacket packet = MinestuckPacket.makePacket(Type.CAPTCHA, PacketCaptchaDeck.GET, slots, mouseButton != 0);
				MinestuckChannelHandler.sendToServer(packet);
				return;
			}
		}
		super.mouseClicked(xcor, ycor, mouseButton);
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException
	{
		if(button == emptySylladex)
		{
			mc.currentScreen = new GuiYesNo(this, I18n.format("gui.emptySylladex1"), I18n.format("gui.emptySylladex2"), 0);
			mc.currentScreen.setWorldAndResolution(mc, width, height);
		}
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException
	{
		super.keyTyped(typedChar, keyCode);
		if(MinestuckKeyHandler.instance.sylladexKey.isActiveAndMatches(keyCode))
			mc.displayGuiScreen(null);
	}
	
	@Override
	public void confirmClicked(boolean result, int id)
	{
		if(result)
			MinestuckChannelHandler.sendToServer(MinestuckPacket.makePacket(Type.CAPTCHA, PacketCaptchaDeck.GET, SylladexUtils.EMPTY_SYLLADEX, false));
		mc.currentScreen = this;
	}
	
	@Override
	public boolean doesGuiPauseGame()
	{
		return false;
	}

	protected boolean isMouseInContainer(int xcor, int ycor) {
		int xOffset = (this.width - 256) / 2;
		int yOffset = (this.height - 202) / 2;
		return xcor >= xOffset + 16 && xcor < xOffset + 16 + 224 && ycor >= yOffset + 17 && ycor < yOffset + 17 + 153;
	}

	public void updateContainers()
	{
		cardGuiContainer.generateSubContainers();
	}
	
	public static class GuiCard
	{
		protected SylladexGuiHandler gui;
		public ItemStack item;
		public int index;
		public int xPos, yPos;
		public int textureIndex;
		
		protected GuiCard()
		{
			item = ItemStack.EMPTY;
		}
		
		public GuiCard(ItemStack item, SylladexGuiHandler gui, int index, int xPos, int yPos, int textureIndex)
		{
			this.gui = gui;
			this.item = item;
			this.index = index;
			this.xPos = xPos;
			this.yPos = yPos;
			this.textureIndex = textureIndex;
		}
		
		public void onClick(int mouseButton)
		{
			int toSend = -1;
			if(this.item.isEmpty() && mouseButton == 1)
				toSend = SylladexUtils.EMPTY_CARD;
			else if(this.index != -1 && (mouseButton == 0 || mouseButton == 1))
				toSend = this.index;
			
			if(toSend != -1)
			{
				MinestuckPacket packet = MinestuckPacket.makePacket(Type.CAPTCHA, PacketCaptchaDeck.GET, toSend, mouseButton != 0);
				MinestuckChannelHandler.sendToServer(packet);
			}
		}

		protected void drawItemBackground()
		{
			gui.mc.getTextureManager().bindTexture(gui.getCardTexture(this));
			int minX = 0, maxX = CARD_WIDTH, minY = 0, maxY = CARD_HEIGHT;
			if(this.xPos + minX < gui.mapX)
				minX += gui.mapX - (this.xPos + minX);
			else if(this.xPos + maxX > gui.mapX + gui.mapWidth)
				maxX -= (this.xPos + maxX) - (gui.mapX + gui.mapWidth);
			if(this.yPos + minY < gui.mapY)
				minY += gui.mapY - (this.yPos + minY);
			else if(this.yPos + maxY > gui.mapY + gui.mapHeight)
				maxY -= (this.yPos + maxY) - (gui.mapY + gui.mapHeight);
			gui.drawTexturedModalRect(this.xPos + minX - gui.mapX, this.yPos + minY - gui.mapY,	//Gui pos
					getCardTextureX() + minX, getCardTextureY() + minY,	//Texture pos
					maxX - minX, maxY - minY);	//Size
		}
		
		protected void drawItem()
		{
			GlStateManager.color(1F, 1F, 1F, 1F);
			if(!this.item.isEmpty())
			{
				int x = this.xPos +2 - gui.mapX;
				int y = this.yPos +7 - gui.mapY;
				if(x >= gui.mapWidth || y >= gui.mapHeight || x + 16 < 0 || y + 16 < 0)
					return;
				gui.itemRender.renderItemAndEffectIntoGUI(item, x, y);
				if(item.getCount() > 1)
				{
					String stackSize = String.valueOf(item.getCount());
					GlStateManager.disableLighting();
					GlStateManager.disableDepth();
					GlStateManager.disableBlend();
					gui.mc.fontRenderer.drawStringWithShadow(stackSize, x + 16 - gui.mc.fontRenderer.getStringWidth(stackSize), y + 8, 0xC6C6C6);
					GlStateManager.enableLighting();
					GlStateManager.enableDepth();
					GlStateManager.enableBlend();
				}
				gui.itemRender.renderItemOverlayIntoGUI(gui.mc.fontRenderer, item, x, y, "");
			}
		}

		public int getCardTextureX()
		{
			return textureIndex % 12 * 21;
		}

		public int getCardTextureY()
		{
			return textureIndex / 12 * 26;
		}

		public ResourceLocation getCardTexture()
		{
			return CARD_TEXTURE;
		}
	}
	
	public static class ModusSizeCard extends GuiCard
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
		
	}
}
