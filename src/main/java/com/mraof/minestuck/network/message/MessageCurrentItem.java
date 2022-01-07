package com.mraof.minestuck.network.message;

import com.mraof.minestuck.network.MinestuckMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;

public class MessageCurrentItem implements MinestuckMessage
{
	private int currentItem;

	public MessageCurrentItem() { }

	public MessageCurrentItem(int currentItem)
	{
		this.currentItem = currentItem;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		currentItem = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(currentItem);
	}

	@Override
	public void execute(EntityPlayer player)
	{
		player.inventory.currentItem = currentItem;
	}

	@Override
	public Side toSide()
	{
		return Side.CLIENT;
	}
}
