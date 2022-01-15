package com.mraof.minestuck.client.gui;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.capabilities.MinestuckCapabilities;
import com.mraof.minestuck.capabilities.api.IStrifeData;
import com.mraof.minestuck.client.MSKeyHandler;
import com.mraof.minestuck.event.handler.StrifeEventHandler;
import com.mraof.minestuck.network.MinestuckNetwork;
import com.mraof.minestuck.network.message.MessageStrifeIndicesRequest;
import com.mraof.minestuck.network.message.MessageStrifeRetrieveRequest;
import com.mraof.minestuck.network.message.MessageStrifeSwapOffhandRequest;
import com.mraof.minestuck.strife.StrifeSpecibus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Arrays;
import java.util.LinkedList;

@SideOnly(Side.CLIENT)
public class GuiStrifeSwitcher extends Gui
{

	private static final ResourceLocation WIDGETS = new ResourceLocation(Minestuck.MODID, "textures/gui/icons.png");
	private static final String iconsLoc = "textures/gui/strife_specibus/icons/";

	public static boolean showSwitcher = false;
	public static boolean offhandMode = false;
	public static int selSpecibus = -1;
	public static int selWeapon = 0;
	private static boolean strifeDown;

	@SubscribeEvent
	public static void renderEvent(TickEvent.RenderTickEvent event)
	{
		Minecraft mc = Minecraft.getMinecraft();
		ScaledResolution scaledresolution = new ScaledResolution(mc);
		int screenWidth = scaledresolution.getScaledWidth();
		int screenHeight = scaledresolution.getScaledHeight();

		if (!showSwitcher || mc.player == null)
			return;

		IStrifeData cap = mc.player.getCapability(MinestuckCapabilities.STRIFE_DATA, null);

		boolean isDown = offhandMode ? MSKeyHandler.swapOffhandStrifeKey.isKeyDown() : MSKeyHandler.strifeKey.isKeyDown();

		if (isDown != strifeDown && isDown)
		{
			selSpecibus = cap.getSelectedSpecibusIndex();
			selWeapon = cap.getSelectedWeaponIndex();
		}

		int selSpecibusIndex = selSpecibus;
		int selWeaponIndex = selWeapon;
		StrifeSpecibus[] portfolio = cap.getNonEmptyPortfolio();

		if (canUseAbstrataSwitcher() && (selSpecibusIndex < 0 || selSpecibusIndex > cap.getPortfolio().length ||
												 cap.getPortfolio()[selSpecibusIndex] == null || (!cap.getPortfolio()[selSpecibusIndex].getKindAbstratus().isFist() && cap.getPortfolio()[selSpecibusIndex].getContents().isEmpty())))
		{
			selSpecibusIndex = portfolio.length <= 0 ? -1 : cap.getSpecibusIndex(portfolio[0]);
			cap.setSelectedSpecibusIndex(selSpecibusIndex);
		}


		if (isDown != strifeDown)
		{
			strifeDown = isDown;
			if (!strifeDown)
			{
				showSwitcher = false;

				if (selSpecibusIndex >= 0)
				{
					EnumHand hand = StrifeEventHandler.isStackAssigned(mc.player.getHeldItemOffhand()) ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND;
					if (!offhandMode)
					{
						cap.setSelectedWeaponIndex(selWeaponIndex);
						cap.setSelectedSpecibusIndex(selSpecibusIndex);
						MinestuckNetwork.sendToServer(new MessageStrifeIndicesRequest(mc.player));
					}
					if (!(mc.player.isSneaking() && canUseAbstrataSwitcher()))
					{
						if (offhandMode)
							MinestuckNetwork.sendToServer(new MessageStrifeSwapOffhandRequest(selSpecibus, selWeapon));
						else
							MinestuckNetwork.sendToServer(new MessageStrifeRetrieveRequest(cap.getSelectedWeaponIndex(), false, hand));
					}
				}
			}
		}

		if (portfolio.length <= 0)
			return;

		if (selSpecibusIndex < 0)
			return;

		if (mc.player.isSneaking() && canUseAbstrataSwitcher())
		{
			if (portfolio.length > 0)
			{
				int toDisplay = (int) Math.min(5, Math.ceil((portfolio.length - 1) / 2f) * 2);
				for (int i = -(toDisplay / 2); i <= (toDisplay / 2); i++)
				{
					int index = (i + portfolio.length + Arrays.asList(portfolio).indexOf(cap.getPortfolio()[selSpecibusIndex])) % portfolio.length;
					StrifeSpecibus specibus = portfolio[index];
					if (specibus == null || specibus.getKindAbstratus() == null)
						continue;

					if (i == 0)
					{
						mc.getTextureManager().bindTexture(WIDGETS);
						drawScaledCustomSizeModalRect(18 * i + screenWidth / 2 - 11, screenHeight * 3 / 4 - 3, 112, 0, 22, 22, 22, 22, 256, 256);
						String str = specibus.getDisplayName();
						mc.fontRenderer.drawString(str, screenWidth / 2 - mc.fontRenderer.getStringWidth(str) / 2, screenHeight * 3 / 4 - 14, 0x00AB54, true);
					}

					ResourceLocation loc = specibus.getKindAbstratus().getRegistryName();
					mc.getTextureManager().bindTexture(new ResourceLocation(loc.getResourceDomain(), iconsLoc + loc.getResourcePath() + ".png"));
					drawScaledCustomSizeModalRect(20 * i + screenWidth / 2 - 8, screenHeight * 3 / 4, 0, 0, 16, 16, 16, 16, 16, 16);

				}
			}
		}
		else
		{
			StrifeSpecibus selSpecibus = cap.getPortfolio()[selSpecibusIndex];

			if (selSpecibus == null)
				selSpecibus = cap.getPortfolio()[0];
			if (selSpecibus == null)
				return;

			LinkedList<ItemStack> strifeDeck = selSpecibus.getContents();

			if (!strifeDeck.isEmpty())
			{
				int toDisplay = (int) Math.min(5, Math.ceil((strifeDeck.size() - 1) / 2f) * 2);
				for (int i = -(toDisplay / 2); i <= (toDisplay / 2); i++)
				{
					int index = (i + strifeDeck.size() + selWeaponIndex) % strifeDeck.size();
					ItemStack stack = strifeDeck.get(index);
					if (stack == null)
						continue;

					if (i == 0)
					{
						mc.getTextureManager().bindTexture(WIDGETS);
						drawScaledCustomSizeModalRect(screenWidth / 2 - 11, screenHeight * 3 / 4 - 3, offhandMode ? 134 : 112, 0, 22, 22, 22, 22, 256, 256);
						String str = stack.getDisplayName();
						mc.fontRenderer.drawString(str, screenWidth / 2 - mc.fontRenderer.getStringWidth(str) / 2, screenHeight * 3 / 4 - 14, 0x00AB54, true);
					}

					if (cap.isArmed() && offhandMode && index == cap.getSelectedWeaponIndex())
					{
						GlStateManager.color(1, 1, 1);
						mc.getTextureManager().bindTexture(WIDGETS);
						drawScaledCustomSizeModalRect(i * 20 + screenWidth / 2 - 11, screenHeight * 3 / 4 - 3, 156, 0, 22, 22, 22, 22, 256, 256);
					}

					renderItem(mc, 20 * i + screenWidth / 2 - 8, screenHeight * 3 / 4, event.renderTickTime, mc.player, stack);
				}
			}
		}
	}

	protected static boolean canUseAbstrataSwitcher()
	{
		return Minecraft.getMinecraft().player.getCapability(MinestuckCapabilities.STRIFE_DATA, null).abstrataSwitcherUnlocked();
	}

	protected static void renderItem(Minecraft mc, int x, int y, float partialTicks, EntityPlayer player, ItemStack stack)
	{
		if (!stack.isEmpty())
		{
			float f = (float) stack.getAnimationsToGo() - partialTicks;

			if (f > 0.0F)
			{
				GlStateManager.pushMatrix();
				float f1 = 1.0F + f / 5.0F;
				GlStateManager.translate((float) (x + 8), (float) (y + 12), 0.0F);
				GlStateManager.scale(1.0F / f1, (f1 + 1.0F) / 2.0F, 1.0F);
				GlStateManager.translate((float) (-(x + 8)), (float) (-(y + 12)), 0.0F);
			}

			mc.getRenderItem().renderItemAndEffectIntoGUI(player, stack, x, y);

			if (f > 0.0F)
			{
				GlStateManager.popMatrix();
			}
		}
	}

	@SubscribeEvent
	public static void onInput(InputEvent event)
	{
		if (showSwitcher && Minecraft.getMinecraft().player != null)
		{
			int scroll = (MSKeyHandler.strifeSelectorRightKey.isKeyDown() ? 1 : 0) - (MSKeyHandler.strifeSelectorLeftKey.isKeyDown() ? 1 : 0);

			IStrifeData cap = Minecraft.getMinecraft().player.getCapability(MinestuckCapabilities.STRIFE_DATA, null);
			StrifeSpecibus[] nonNullPortfolio = cap.getNonEmptyPortfolio();

			if (nonNullPortfolio.length > 0)
			{
				if (Minecraft.getMinecraft().player.isSneaking() && canUseAbstrataSwitcher())
				{
					if (selSpecibus < 0 || cap.getPortfolio()[selSpecibus] == null)
						selSpecibus = 0;
					else
					{
						StrifeSpecibus selectedSpecibus = cap.getPortfolio()[selSpecibus];
						int i = 0;
						for (int j = 0; j < nonNullPortfolio.length; j++)
							if (nonNullPortfolio[j] == selectedSpecibus)
							{
								i = j;
								break;
							}
						i = (int) ((i + Math.signum(-scroll) + nonNullPortfolio.length) % nonNullPortfolio.length);
						selSpecibus = (cap.getSpecibusIndex(nonNullPortfolio[i]));
					}
				}
				else if (selSpecibus >= 0 && cap.getPortfolio()[selSpecibus] != null)
				{
					int deckSize = cap.getPortfolio()[selSpecibus].getContents().size();
					selWeapon = ((int) ((selWeapon + Math.signum(scroll) + deckSize) % deckSize));
				}
			}
		}
	}

	@SubscribeEvent
	public static void scrollEvent(MouseEvent event)
	{
		if (showSwitcher && Minecraft.getMinecraft().player != null)
		{
			IStrifeData cap = Minecraft.getMinecraft().player.getCapability(MinestuckCapabilities.STRIFE_DATA, null);
			StrifeSpecibus[] nonNullPortfolio = cap.getNonEmptyPortfolio();

			if (nonNullPortfolio.length > 0)
			{
				if (Minecraft.getMinecraft().player.isSneaking() && canUseAbstrataSwitcher())
				{
					if (selSpecibus < 0 || cap.getPortfolio()[selSpecibus] == null)
						selSpecibus = 0;
					else
					{
						StrifeSpecibus selectedSpecibus = cap.getPortfolio()[selSpecibus];
						int i = 0;
						for (int j = 0; j < nonNullPortfolio.length; j++)
							if (nonNullPortfolio[j] == selectedSpecibus)
							{
								i = j;
								break;
							}
						i = (int) ((i + Math.signum(-event.getDwheel()) + nonNullPortfolio.length) % nonNullPortfolio.length);
						selSpecibus = (cap.getSpecibusIndex(nonNullPortfolio[i]));
					}
				}
				else if (selSpecibus >= 0 && cap.getPortfolio()[selSpecibus] != null)
				{
					int deckSize = cap.getPortfolio()[selSpecibus].getContents().size();
					selWeapon = ((int) ((selWeapon + Math.signum(-event.getDwheel()) + deckSize) % deckSize));
				}
			}

			event.setCanceled(true);
		}
	}

}
