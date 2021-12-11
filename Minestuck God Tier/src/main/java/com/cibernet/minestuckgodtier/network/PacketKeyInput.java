package com.cibernet.minestuckgodtier.network;

import com.cibernet.minestuckgodtier.capabilities.GodKeyStates;
import com.cibernet.minestuckgodtier.capabilities.MSGTCapabilities;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;

import java.util.EnumSet;

public class PacketKeyInput extends MSGTPacket
{
	private GodKeyStates.Key key;
	private boolean pressed;

	@Override
	public MSGTPacket generatePacket(Object... args)
	{
		data.writeByte((int) args[0]);
		data.writeBoolean((boolean) args[1]);
		return this;
	}

	@Override
	public MSGTPacket consumePacket(ByteBuf data)
	{
		key = GodKeyStates.Key.values()[data.readByte()];
		pressed = data.readBoolean();
		return null;
	}

	@Override
	public void execute(EntityPlayer player)
	{
		player.getCapability(MSGTCapabilities.GOD_KEY_STATES, null).updateKeyState(key, pressed);
	}

	@Override
	public EnumSet<Side> getSenderSide()
	{
		return null;
	}
}
