package com.mraof.minestuck.network;

import com.mraof.minestuck.capabilities.MinestuckCapabilities;
import com.mraof.minestuck.capabilities.caps.GodKeyStates;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;

import java.util.EnumSet;

public class PacketGodKeyInput extends MinestuckPacket
{
	private GodKeyStates.Key key;
	private boolean pressed;

	@Override
	public void generatePacket(Object... args)
	{
		data.writeByte((int) args[0]);
		data.writeBoolean((boolean) args[1]);

	}

	@Override
	public void consumePacket(ByteBuf data)
	{
		key = GodKeyStates.Key.values()[data.readByte()];
		pressed = data.readBoolean();
	}

	@Override
	public void execute(EntityPlayer player)
	{
		player.getCapability(MinestuckCapabilities.GOD_KEY_STATES, null).updateKeyState(key, pressed);
	}

	@Override
	public EnumSet<Side> getSenderSide()
	{
		return null;
	}
}
