package com.mraof.minestuck.network.message;

import com.mraof.minestuck.capabilities.MinestuckCapabilities;
import com.mraof.minestuck.capabilities.caps.GodKeyStates;
import com.mraof.minestuck.network.MinestuckMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;

public class MessageGodKeyInputRequest implements MinestuckMessage
{
	private GodKeyStates.Key key;
	private boolean pressed;

	private MessageGodKeyInputRequest() { }

	public MessageGodKeyInputRequest(GodKeyStates.Key key, boolean pressed)
	{
		this.key = key;
		this.pressed = pressed;
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeByte(key.ordinal());
		buf.writeBoolean(pressed);
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		key = GodKeyStates.Key.values()[buf.readByte()];
		pressed = buf.readBoolean();
	}

	@Override
	public void execute(EntityPlayer player)
	{
		player.getCapability(MinestuckCapabilities.GOD_KEY_STATES, null).updateKeyState(key, pressed);
	}

	@Override
	public Side toSide()
	{
		return Side.SERVER;
	}
}
