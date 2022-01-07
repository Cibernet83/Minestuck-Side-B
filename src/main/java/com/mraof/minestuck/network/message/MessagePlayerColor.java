package com.mraof.minestuck.network.message;

import com.mraof.minestuck.network.MinestuckMessage;
import com.mraof.minestuck.util.ColorCollector;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;

public class MessagePlayerColor implements MinestuckMessage
{
	private int color = -2;

	public MessagePlayerColor() { }

	public MessagePlayerColor(int color)
	{
		this.color = color;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		color = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(color);
	}

	@Override
	public void execute(EntityPlayer player)
	{
		if (color == -2)
		{
			ColorCollector.playerColor = -1;
			ColorCollector.displaySelectionGui = true;
		}
		else
			ColorCollector.playerColor = color;
	}

	@Override
	public Side toSide()
	{
		return Side.CLIENT;
	}
}