package com.mraof.minestuck.client;


import com.mraof.minestuck.capabilities.MinestuckCapabilities;
import com.mraof.minestuck.capabilities.caps.GodKeyStates;
import com.mraof.minestuck.network.MinestuckChannelHandler;
import com.mraof.minestuck.network.MinestuckPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

@SideOnly(Side.CLIENT)
public class MSGTKeyHandler
{
	public static KeyBinding[] keyBindings;
	private static boolean[] downs;

	public static void registerKeys()
	{
		keyBindings = new KeyBinding[GodKeyStates.Key.values().length];
		downs = new boolean[GodKeyStates.Key.values().length];

		keyBindings[GodKeyStates.Key.CLASS.ordinal()] = new KeyBinding("key.minestuckgodtier.classAction", Keyboard.KEY_J, "key.categories.minestuck");
		keyBindings[GodKeyStates.Key.ASPECT.ordinal()] = new KeyBinding("key.minestuckgodtier.aspectAction", Keyboard.KEY_H, "key.categories.minestuck");
		keyBindings[GodKeyStates.Key.UTIL.ordinal()] = new KeyBinding("key.minestuckgodtier.godTierUtil", Keyboard.KEY_K, "key.categories.minestuck");

		for (KeyBinding binding : keyBindings)
			ClientRegistry.registerKeyBinding(binding);
	}

	@SubscribeEvent
	public static void onInput(InputEvent event)
	{
		for(int i = 0; i < keyBindings.length; i++)
		{
			if (keyBindings[i].isKeyDown() ^ downs[i])
			{
				downs[i] = !downs[i];
				MinestuckChannelHandler.sendToServer(MinestuckPacket.makePacket(MinestuckPacket.Type.GOD_KEY_INPUT, i, downs[i]));
				if(Minecraft.getMinecraft().player != null)
					Minecraft.getMinecraft().player.getCapability(MinestuckCapabilities.GOD_KEY_STATES, null).updateKeyState(GodKeyStates.Key.values()[i], downs[i]);
			}
		}
	}
}
