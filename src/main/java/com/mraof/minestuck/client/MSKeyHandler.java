package com.mraof.minestuck.client;

import com.mraof.minestuck.capabilities.MinestuckCapabilities;
import com.mraof.minestuck.client.gui.GuiStrifeSwitcher;
import com.mraof.minestuck.event.handler.StrifeEventHandler;
import com.mraof.minestuck.network.MinestuckNetwork;
import com.mraof.minestuck.network.message.MessageStrifeAssignRequest;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.EnumHand;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

@SideOnly(Side.CLIENT)
public class MSKeyHandler
{
	public static final KeyBinding strifeKey = new KeyBinding("key.strife", Keyboard.KEY_V, "key.categories.minestuck");
	public static final KeyBinding strifeSelectorLeftKey = new KeyBinding("key.strifeSelectorLeft", Keyboard.KEY_NONE, "key.categories.minestuck");
	public static final KeyBinding strifeSelectorRightKey = new KeyBinding("key.strifeSelectorRight", Keyboard.KEY_NONE, "key.categories.minestuck");
	public static final KeyBinding swapOffhandStrifeKey = new KeyBinding("key.swapOffhandStrife", Keyboard.KEY_NONE, "key.categories.minestuck");
	private static Boolean offhandMode = null;

	public static void register()
	{
		MinecraftForge.EVENT_BUS.register(MSKeyHandler.class);
		ClientRegistry.registerKeyBinding(strifeKey);
		ClientRegistry.registerKeyBinding(strifeSelectorLeftKey);
		ClientRegistry.registerKeyBinding(strifeSelectorRightKey);
		ClientRegistry.registerKeyBinding(swapOffhandStrifeKey);
	}

	@SubscribeEvent
	public static void onInput(InputEvent event)
	{
		EntityPlayerSP player = Minecraft.getMinecraft().player;
		if (player == null)
			return;

		boolean strifeKeyDown = strifeKey.isKeyDown();
		boolean swapStrifeKeyDown = swapOffhandStrifeKey.isKeyDown();

		boolean strifePressed = offhandMode == null ? (strifeKeyDown || swapStrifeKeyDown) : offhandMode ? swapStrifeKeyDown : strifeKeyDown;
		if (strifePressed)
		{
			if (offhandMode == null)
			{
				offhandMode = !(!swapStrifeKeyDown && strifeKeyDown);
				GuiStrifeSwitcher.offhandMode = offhandMode;
				if (offhandMode || (player.getHeldItemMainhand().isEmpty() || StrifeEventHandler.isStackAssigned(player.getHeldItemMainhand()) || StrifeEventHandler.isStackAssigned(player.getHeldItemOffhand())))
					GuiStrifeSwitcher.showSwitcher = true;
				else if (!player.getHeldItemMainhand().isEmpty() && !player.getCapability(MinestuckCapabilities.STRIFE_DATA, null).isArmed())
				{
					MinestuckNetwork.sendToServer(new MessageStrifeAssignRequest(EnumHand.MAIN_HAND));
				}
			}
		}
		else
			offhandMode = null;
	}
}
