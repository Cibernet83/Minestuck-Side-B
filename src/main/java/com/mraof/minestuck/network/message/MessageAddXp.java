package com.mraof.minestuck.network.message;

import com.mraof.minestuck.network.MinestuckMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;

public class MessageAddXp implements MinestuckMessage
{
	private int levels;

	public MessageAddXp() { }

	public MessageAddXp(int levels)
	{
		this.levels = levels;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		levels = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(levels);
	}

	@Override
	public void execute(EntityPlayer player)
	{
		player.experienceLevel += levels;
	}

	@Override
	public Side toSide()
	{
		return Side.CLIENT;
	}
}
