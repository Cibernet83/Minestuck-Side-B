package com.mraof.minestuck.client.gui;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.badges.Badge;
import com.mraof.minestuck.badges.MasterBadge;
import com.mraof.minestuck.badges.MinestuckBadges;
import com.mraof.minestuck.capabilities.MinestuckCapabilities;
import com.mraof.minestuck.capabilities.api.IGodTierData;
import com.mraof.minestuck.network.MinestuckNetwork;
import com.mraof.minestuck.network.message.MessageToggleBadgeRequest;
import com.mraof.minestuck.util.EnumAspect;
import com.mraof.minestuck.util.MinestuckPlayerData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.input.Keyboard;

import java.io.IOException;
import java.util.*;

public class GuiManageBadges extends GuiScreen
{
	public static final ResourceLocation TEXTURES = new ResourceLocation(Minestuck.MODID, "textures/gui/god_tier_meditation.png");
	public static final ResourceLocation CURSOR = new ResourceLocation(Minestuck.MODID, "textures/items/sash_kit.png");
	protected static final HashMap<EnumAspect, Integer> mainColors = new HashMap<EnumAspect, Integer>()
	{{
		put(EnumAspect.BREATH, 0x47E2FA);
		put(EnumAspect.LIGHT, 0xF6FA4E);
		put(EnumAspect.SPACE, 0x333333);
		put(EnumAspect.TIME, 0xFF2106);
		put(EnumAspect.LIFE, 0x72EB34);
		put(EnumAspect.VOID, 0x001856);
		put(EnumAspect.HEART, 0xBD1864);
		put(EnumAspect.HOPE, 0xFFDE55);
		put(EnumAspect.BLOOD, 0xB71015);
		put(EnumAspect.RAGE, 0x9C4DAC);
		put(EnumAspect.MIND, 0x06FFC9);
		put(EnumAspect.DOOM, 0x306800);
	}};
	private static final float BADGES_PER_ROW = 16;
	protected final ArrayList<Badge> badges = new ArrayList<>();
	protected final ArrayList<MasterBadge> masterBadges = new ArrayList<>();
	public EntityPlayer player;
	public Minecraft mc;
	public int xSize = 240;
	public int ySize = 256;
	public int guiLeft;
	public int guiTop;
	boolean mouseClicked = false;
	int clickTime = 0;
	boolean showExtra = false;
	private Badge hoveredBadge = null;

	public GuiManageBadges(EntityPlayer player)
	{
		this.player = player;
		this.mc = Minecraft.getMinecraft();
		this.fontRenderer = mc.fontRenderer;

		this.guiLeft = (this.width - this.xSize) / 2;
		this.guiTop = (this.height - this.ySize) / 2;

		setupBadges();
	}

	protected void setupBadges()
	{
		badges.clear();
		masterBadges.clear();

		for (Map.Entry<ResourceLocation, Badge> entry : MinestuckBadges.REGISTRY.getEntries())
		{
			if (Minecraft.getMinecraft().player.getCapability(MinestuckCapabilities.GOD_TIER_DATA, null).hasBadge(entry.getValue()))
			{
				if (entry.getValue() instanceof MasterBadge)
					masterBadges.add((MasterBadge) entry.getValue());
				else badges.add(entry.getValue());
			}
		}

		Collections.sort(badges, Comparator.comparingInt(Badge::getSortIndex));
		Collections.sort(masterBadges, Comparator.comparingInt(Badge::getSortIndex));
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		IGodTierData data = Minecraft.getMinecraft().player.getCapability(MinestuckCapabilities.GOD_TIER_DATA, null);

		drawDefaultBackground();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);


		int yOffset = this.height / 2 - ySize / 2;
		int xOffset = this.width / 2 - xSize / 2;

		//God Tier Title
		mc.getTextureManager().bindTexture(TEXTURES);
		setColor(0xFFFFFF);
		//drawTexturedModalRect(xOffset+xSize/2 - 88, yOffset+15, 0, 223, 176, 33);
		String gtTitle = data.getGodTierTitle(MinestuckPlayerData.clientData.title);
		//fontRenderer.drawString(gtTitle, xOffset+xSize/2 - fontRenderer.getStringWidth(gtTitle)/2, yOffset+30, 0xFFFFFF);

		yOffset = this.height / 2 - 22;
		boolean isOverlord = data.isBadgeActive(MinestuckBadges.BADGE_OVERLORD);

		EnumAspect aspect = MinestuckPlayerData.clientData.title.getHeroAspect();
		int mainColor = aspect == EnumAspect.SPACE ? 0xFAFAFA : mainColors.getOrDefault(aspect, 0x80FF20);

		//Sash
		mc.getTextureManager().bindTexture(new ResourceLocation(Minestuck.MODID, "textures/gui/sash_" + data.getLunarSway().toString().toLowerCase() + ".png"));
		float n = width / xSize;
		for (int i = 0; i <= n; i++)
			this.drawTexturedModalRect(xOffset + xSize * (i - n / 2f), yOffset - 38, 0, 0, xSize, 142);

		//Badges
		String badgesStr = I18n.format("gui.badgesLeft", data.getBadgesLeft());

		//fontRenderer.drawString(badgesStr, xOffset+xSize/2 - fontRenderer.getStringWidth(badgesStr)/2, yOffset-10, mainColor);
		setColor(0xFFFFFF);

		for (int i = 0; i < masterBadges.size(); i++)
		{
			MasterBadge badge = masterBadges.get(i);
			if (badge.isReadable(player.world, player) && (data.getMasterBadge() == null || data.getMasterBadge() == badge || isOverlord))
				mc.getTextureManager().bindTexture(badge.getTextureLocation());
			else
				mc.getTextureManager().bindTexture(new ResourceLocation(badge.getRegistryName().getResourceDomain(), "textures/gui/badge_locked.png"));
			if (!data.hasBadge(badge))
				GlStateManager.color(0.5f, 0.5f, 0.5f);
			drawScaledCustomSizeModalRect(xOffset + (xSize - masterBadges.size() * 22) / 2 + i * 22, yOffset - 23, 0, 0, 256, 256, 20, 20, 256, 256);
			GlStateManager.color(1, 1, 1);

			if (data.hasBadge(badge) && !data.isBadgeActive(badge))
			{
				mc.getTextureManager().bindTexture(new ResourceLocation(badge.getRegistryName().getResourceDomain(), "textures/gui/badge_disabled.png"));
				drawScaledCustomSizeModalRect(xOffset + (xSize - masterBadges.size() * 22) / 2 + i * 22, yOffset - 23, 0, 0, 256, 256, 20, 20, 256, 256);
			}
		}
		for (int i = 0; i < badges.size(); i++)
		{
			Badge badge = badges.get(i);
			if (badge.isReadable(player.world, player))
			{
				if (!data.hasBadge(badge))
					GlStateManager.color(0.5f, 0.5f, 0.5f);
				mc.getTextureManager().bindTexture(badge.getTextureLocation());
			}
			else
				mc.getTextureManager().bindTexture(new ResourceLocation(badge.getRegistryName().getResourceDomain(), "textures/gui/badge_locked.png"));

			int rows = (int) Math.max(2, Math.ceil(badges.size() / BADGES_PER_ROW));

			drawScaledCustomSizeModalRect(xOffset + (xSize - ((badges.size() + 1) / rows) * 22) / 2 + ((i) / rows) * 22, yOffset + (i % rows) * 22, 0, 0, 256, 256, 20, 20, 256, 256);
			GlStateManager.color(1, 1, 1);

			if (data.hasBadge(badge) && !data.isBadgeActive(badge))
			{
				mc.getTextureManager().bindTexture(new ResourceLocation(badge.getRegistryName().getResourceDomain(), "textures/gui/badge_disabled.png"));
				drawScaledCustomSizeModalRect(xOffset + (xSize - ((badges.size() + 1) / rows) * 22) / 2 + ((i) / rows) * 22, yOffset + (i % rows) * 22, 0, 0, 256, 256, 20, 20, 256, 256);
			}
		}

		//Cursor
		mc.getTextureManager().bindTexture(CURSOR);
		drawScaledCustomSizeModalRect(mouseX, mouseY, 0, 0, 256, 256, 16, 16, 256, 256);

		//Hovering Text
		for (int i = 0; i < masterBadges.size(); i++)
		{
			if (!isPointInRegion(mouseX, mouseY, xOffset + (xSize - masterBadges.size() * 22) / 2 + i * 22, yOffset - 23, 20, 20))
				continue;
			MasterBadge badge = masterBadges.get(i);
			hoveredBadge = badge;

			ArrayList<String> tooltip = new ArrayList<>();

			if (!badge.isReadable(player.world, player))
			{
				tooltip.add(TextFormatting.OBFUSCATED + badge.getDisplayName());
				tooltip.add(badge.getReadRequirements());
			}
			else if (data.getMasterBadge() == null && !isOverlord)
			{
				tooltip.add(badge.getDisplayName());
				if (showExtra)
					tooltip.add(badge.getDisplayTooltip());
				else
				{
					tooltip.add(badge.getUnlockRequirements());
					tooltip.add(I18n.format("gui.masterBadgeWarning"));
					tooltip.add(I18n.format("gui.showBadgeInfo"));
				}
			}
			else if (data.getMasterBadge() == badge || isOverlord)
			{
				tooltip.add(badge.getDisplayName());
				tooltip.add(badge.getDisplayTooltip());

				if (!data.isBadgeActive(badge))
					tooltip.add(I18n.format("gui.badge" + (data.isBadgeEnabled(badge) ? "Blocked" : "Disabled")));
			}

			if (!tooltip.isEmpty())
				drawHoveringText(tooltip, mouseX, mouseY);

		}

		int rows = (int) Math.max(2, Math.ceil(badges.size() / BADGES_PER_ROW));
		for (int i = 0; i < badges.size(); i++)
		{
			if (!isPointInRegion(mouseX, mouseY, xOffset + (xSize - ((badges.size() + 1) / rows) * 22) / 2 + ((i) / rows) * 22, yOffset + (i % rows) * 22, 20, 20))
				continue;

			ArrayList<String> tooltip = new ArrayList<>();
			Badge badge = badges.get(i);
			hoveredBadge = badge;
			if (!badge.isReadable(player.world, player))
			{
				tooltip.add(TextFormatting.OBFUSCATED + badge.getDisplayName());
				tooltip.add(badge.getReadRequirements());

			}
			else if (data.hasBadge(badge))
			{
				tooltip.add(badge.getDisplayName());
				tooltip.add(badge.getDisplayTooltip());
				if (!data.isBadgeActive(badge))
					tooltip.add(I18n.format("gui.badge" + (data.isBadgeEnabled(badge) ? "Blocked" : "Disabled")));
			}
			else
			{
				tooltip.add(badge.getDisplayName());
				if (showExtra)
					tooltip.add(badge.getDisplayTooltip());
				else
				{
					if (data.getBadgesLeft() > 0)
						tooltip.add(badge.getUnlockRequirements());
					else tooltip.add(I18n.format("gui.noBadgesLeft"));
					tooltip.add(I18n.format("gui.showBadgeInfo"));

				}
			}

			if (!tooltip.isEmpty())
				drawHoveringText(tooltip, mouseX, mouseY);
		}

		GlStateManager.disableLighting();
		GlStateManager.disableDepth();

		super.drawScreen(mouseX, mouseY, partialTicks);

		if (mouseClicked)
			clickTime++;
		else clickTime = 0;
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
	{
		super.mouseClicked(mouseX, mouseY, mouseButton);
		mouseClicked = true;
		IGodTierData data = Minecraft.getMinecraft().player.getCapability(MinestuckCapabilities.GOD_TIER_DATA, null);

		if (hoveredBadge instanceof MasterBadge)
		{
			if ((data.isBadgeActive(MinestuckBadges.BADGE_OVERLORD) || data.hasBadge(hoveredBadge) && data.getMasterBadge() != null))
				MinestuckNetwork.sendToServer(new MessageToggleBadgeRequest(hoveredBadge));
		}
		else
		{
			if (data.hasBadge(hoveredBadge))
				MinestuckNetwork.sendToServer(new MessageToggleBadgeRequest(hoveredBadge));
		}
	}

	@Override
	protected void mouseReleased(int mouseX, int mouseY, int state)
	{
		super.mouseReleased(mouseX, mouseY, state);
		mouseClicked = false;
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException
	{
		super.actionPerformed(button);
	}

	@Override
	public void initGui()
	{
		super.initGui();
	}

	@Override
	public void handleKeyboardInput() throws IOException
	{
		super.handleKeyboardInput();
		showExtra = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT);
	}

	@Override
	public void onGuiClosed()
	{
		super.onGuiClosed();
	}

	@Override
	public boolean doesGuiPauseGame()
	{
		return false;
	}

	protected void setColor(int hex)
	{
		float r = (float) ((hex & 16711680) >> 16) / 255.0F;
		float g = (float) ((hex & '\uff00') >> 8) / 255.0F;
		float b = (float) ((hex & 255) >> 0) / 255.0F;
		GlStateManager.color(r, g, b);
	}

	protected boolean isPointInRegion(int pointX, int pointY, int x, int y, int width, int height)
	{
		return pointX >= x && pointX <= x + width && pointY >= y && pointY <= y + height;
	}

	protected void renderBorderedText(int x, int y, String text, int color, int borderColor)
	{
		//XP Color: 80FF20
		fontRenderer.drawString(text, x + 1, y, borderColor);
		fontRenderer.drawString(text, x - 1, y, borderColor);
		fontRenderer.drawString(text, x, y + 1, borderColor);
		fontRenderer.drawString(text, x, y - 1, borderColor);
		fontRenderer.drawString(text, x, y, color);
	}
}
